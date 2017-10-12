package pl.nask.crs.iedrapi;

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.google.common.base.Joiner;

import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.CommandSyntaxErrorException;

import ie.domainregistry.ieapi_1.CommandType;
import ie.domainregistry.ieapi_1.IeapiType;

class MyUnmarshaller {

    private final JAXBContext jaxbCtx;

    private List<String> schemaFilesList = new ArrayList<>();

    private boolean validating;

    MyUnmarshaller() throws JAXBException {
        List<String> schemas = Arrays.asList(
                "ie.domainregistry.ieapi_1",
                "ie.domainregistry.ieapi_account_1",
                "ie.domainregistry.ieapi_contact_1",
                "ie.domainregistry.ieapi_domain_1",
                "ie.domainregistry.ieapi_ticket_1",
                "ie.domainregistry.ieapi_registranttransferbuyrequest_1",
                "ie.domainregistry.ieapi_registranttransfersellrequest_1",
                "ie.domainregistry.ieapicom_1",
                "ietf.params.xml.ns.secdns_1"
        );
        jaxbCtx = JAXBContext.newInstance(Joiner.on(":").join(schemas));
    }

    void setValidating(boolean validating) {
        this.validating = validating;
    }

    CommandType unmarshal(String xmlInput, ValidationCallback validationCallback)
            throws CommandSyntaxErrorException, CommandFailed {
        if (xmlInput == null) {
            throw new CommandSyntaxErrorException();
        }
        XMLStreamReader xmlStreamReader;
        Unmarshaller unmarshaller;
        try {
            xmlStreamReader = createXmlStreamReader(xmlInput);
            unmarshaller = createUnmarshaller(validationCallback);
        } catch (XMLStreamException | SAXException | JAXBException | URISyntaxException e) {
            throw new CommandFailed(e);
        }
        return unmarshal(xmlStreamReader, unmarshaller);
    }

    private XMLStreamReader createXmlStreamReader(String xmlInput) throws XMLStreamException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        return xif.createXMLStreamReader(new StringReader(xmlInput));
    }

    private Unmarshaller createUnmarshaller(ValidationCallback validationCallback)
            throws SAXException, JAXBException, URISyntaxException {
        Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
        if (validating) {
            enableSchemaValidation(unmarshaller);
        }
        unmarshaller.setEventHandler(new UnmarshallerValidationEventHandler(validationCallback));
        return unmarshaller;
    }

    @SuppressWarnings("unchecked")
    private CommandType unmarshal(XMLStreamReader reader, Unmarshaller unmarshaller)
            throws CommandSyntaxErrorException {
        try {
            JAXBElement<IeapiType> c = (JAXBElement<IeapiType>) unmarshaller.unmarshal(reader);
            if (c.getValue().getCommand() == null) {
                throw new CommandSyntaxErrorException();
            }
            return c.getValue().getCommand();
        } catch (JAXBException e) {
            throw new CommandSyntaxErrorException(e);
        }
    }

    private void enableSchemaValidation(Unmarshaller um) throws SAXException, URISyntaxException {
        um.setSchema(getSchema());
    }

    private Schema getSchema() throws SAXException, URISyntaxException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return schemaFactory.newSchema(toStreamSources(schemaFilesList));
    }

    void setSchemaFilesList(List<String> schemaFileList) {
        this.schemaFilesList = schemaFileList;
    }

    private Source[] toStreamSources(Collection<String> filesList) throws URISyntaxException {
        List<Source> sourcesList = new ArrayList<>();
        for (String file : filesList) {
            URI uri = getClass().getResource(file).toURI();
            sourcesList.add(new StreamSource(uri.toString()));
        }
        Source[] ret = new Source[] {};
        ret = sourcesList.toArray(ret);
        return ret;
    }
}
