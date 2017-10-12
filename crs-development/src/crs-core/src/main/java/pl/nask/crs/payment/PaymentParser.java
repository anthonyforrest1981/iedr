package pl.nask.crs.payment;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.exceptions.ParserException;

public class PaymentParser {
    // thread safe
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();

    public static String toXML(ExtendedPaymentRequest paymentRequest) {
        Document doc = newDocument();

        Element reqElement = doc.createElement("request");

        reqElement.setAttribute("timestamp", paymentRequest.getTimestamp());
        switch (paymentRequest.getType()) {
            case TYPE_AUTH:
                reqElement.setAttribute("type", "auth");
                break;
            case TYPE_SETTLE:
                reqElement.setAttribute("type", "settle");
                break;
            case TYPE_VOID:
                reqElement.setAttribute("type", "void");
        }

        conditionallyAddTextElement(doc, reqElement, "merchantid", paymentRequest.getMerchantId());
        conditionallyAddTextElement(doc, reqElement, "account", paymentRequest.getAccount());
        conditionallyAddTextElement(doc, reqElement, "orderid", paymentRequest.getOrderId());

        if (paymentRequest.getType().equals(TransactionType.TYPE_AUTH)) {
            if (paymentRequest.getAmountInLowestCurrencyUnit() != null) {
                Element amount = doc.createElement("amount");
                amount.setTextContent(paymentRequest.getAmountInLowestCurrencyUnit().toString());
                if (paymentRequest.getCurrency() != null) {
                    amount.setAttribute("currency", paymentRequest.getCurrency());
                }
                reqElement.appendChild(amount);
            }

            Element card = doc.createElement("card");
            conditionallyAddTextElement(doc, card, "number", paymentRequest.getCardNumber());
            conditionallyAddTextElement(doc, card, "expdate", paymentRequest.getCardExpDate());
            conditionallyAddTextElement(doc, card, "chname", paymentRequest.getCardHolderName());
            conditionallyAddTextElement(doc, card, "type", paymentRequest.getCardType());

            if (!Validator.isEmpty(paymentRequest.getCvnNumber())) {
                Element cvn = doc.createElement("cvn");
                Element number = doc.createElement("number");
                number.setTextContent(paymentRequest.getCvnNumber());
                cvn.appendChild(number);
                String cvnPresInd = (paymentRequest.getCvnPresenceIndicator() == null) ? null : paymentRequest
                        .getCvnPresenceIndicator().toString();
                conditionallyAddTextElement(doc, cvn, "presind", cvnPresInd);
                card.appendChild(cvn);
            }
            reqElement.appendChild(card);

            Element autosettle = doc.createElement("autosettle");
            autosettle.setAttribute("flag", "0");
            reqElement.appendChild(autosettle);

            Element tssinfo = doc.createElement("tssinfo");
            Element address = doc.createElement("address");
            address.setAttribute("type", "billing");
            Element country = doc.createElement("country");
            country.setTextContent("ie");
            address.appendChild(country);
            tssinfo.appendChild(address);
            reqElement.appendChild(tssinfo);
        } else {
            conditionallyAddTextElement(doc, reqElement, "pasref", paymentRequest.getPasref());
            conditionallyAddTextElement(doc, reqElement, "authcode", paymentRequest.getAuthcode());
        }

        Element mdf5hash = doc.createElement("md5hash");
        mdf5hash.setTextContent(paymentRequest.getMd5Hash());
        reqElement.appendChild(mdf5hash);

        doc.appendChild(reqElement);

        return asXML(doc);
    }

    private static Document newDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            // should never happen
            throw new IllegalStateException("Could not create a document builder", e);
        }
    }

    private static String asXML(Document doc) {
        try {

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "false");
            DOMSource source = new DOMSource(doc);

            StreamResult outputTarget = new StreamResult(new StringWriter());
            transformer.transform(source, outputTarget);
            return outputTarget.getWriter().toString();
        } catch (TransformerException e) {
            // should never hapen...
            throw new IllegalStateException("Could not generate XML for the payment request", e);
        }
    }

    private static void conditionallyAddTextElement(Document doc, Element reqElement, String elementName, String content) {
        if (content != null) {
            Element el = doc.createElement(elementName);
            el.setTextContent(content);
            reqElement.appendChild(el);
        }
    }

    public static PaymentResponse fromXML(String response) throws ParserException {
        PaymentResponse res = new PaymentResponse();
        org.w3c.dom.Document doc = null;
        try {
            doc = stringToDom(response);
        } catch (SAXException e) {
            throw new ParserException(e);
        } catch (ParserConfigurationException e) {
            throw new ParserException(e);
        } catch (IOException e) {
            throw new ParserException(e);
        }
        Node first = doc.getFirstChild();
        NodeList nodes = first.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeName().equals("result")) {
                res.setResult(node.getTextContent());
                continue;
            }
            if (node.getNodeName().equals("message")) {
                res.setMessage(node.getTextContent());
                continue;
            }
            if (node.getNodeName().equals("authcode")) {
                res.setAuthcode(node.getTextContent());
                continue;
            }
            if (node.getNodeName().equals("pasref")) {
                res.setPasref(node.getTextContent());
                continue;
            }
            if (node.getNodeName().equals("cardissuer")) {
                NodeList subNodes = node.getChildNodes();
                for (int si = 0; si < subNodes.getLength(); si++) {
                    Node subNode = subNodes.item(si);
                    if (subNode.getNodeName().equals("bank")) {
                        res.setBank(subNode.getTextContent());
                        continue;
                    }
                    if (subNode.getNodeName().equals("country")) {
                        res.setCountry(subNode.getTextContent());
                    }
                }
            }
        }
        return res;
    }

    private static org.w3c.dom.Document stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }
}
