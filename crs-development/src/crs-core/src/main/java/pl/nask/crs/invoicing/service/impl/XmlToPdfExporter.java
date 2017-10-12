package pl.nask.crs.invoicing.service.impl;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.fop.apps.*;
import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.log4j.Logger;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import pl.nask.crs.commons.config.*;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.payment.Invoice;

public class XmlToPdfExporter implements InvoiceExporter {

    private static final Logger LOGGER = Logger.getLogger(XmlToPdfExporter.class);
    private final ApplicationConfig applicationConfig;
    private final FopFactory fopFactory;

    public XmlToPdfExporter(ApplicationConfig applicationConfig) throws IOException, SAXException, URISyntaxException {
        this.applicationConfig = applicationConfig;
        fopFactory = createFopFactory();
    }

    private FopFactory createFopFactory() throws IOException, SAXException, URISyntaxException {
        InputStream fopConfigStream = getClass().getResourceAsStream("/fopConfig.xml");
        FopConfParser fopConfParser = new FopConfParser(fopConfigStream, new URI("/"), new InvoiceResourceResolver());
        FopFactory fopFactory = fopConfParser.getFopFactoryBuilder().build();
        return fopFactory;
    }

    @Override
    public void export(Invoice invoice) throws ExportException {
        try {
            File originalXmlFile = getSourceFile(invoice);
            InputStream invoiceInputStream = getInvoiceInputStream(originalXmlFile);
            File pdfFile = getTargetFile(invoice);
            transform(invoiceInputStream, pdfFile, originalXmlFile);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void transform(InputStream xmlFileStream, File pdfFile, File originalXmlFile)
            throws FOPException, TransformerException, IOException {
        LOGGER.info("Input: XML (" + originalXmlFile + ")");
        LOGGER.info("Output: PDF (" + pdfFile + ")");
        LOGGER.info("Transforming...");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        InputStream xsltFile = getClass().getResourceAsStream("/xml2PdfTemplate.xsl");
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile))) {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            Result res = new SAXResult(fop.getDefaultHandler());
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            transformer.transform(new StreamSource(xmlFileStream), res);
        }

        stopWatch.stop();

        LOGGER.info("Success! Export took " + stopWatch.toString());
        if (stopWatch.getTime() > 2 * 60 * 1000) {
            LOGGER.warn("Export took more than 2 minutes!");
        }
    }

    private InputStream getInvoiceInputStream(File invoiceXml) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(invoiceXml);
        String invoiceString = document.asXML();
        invoiceString = clearInvoiceTagAttributes(invoiceString);
        InputStream ret = IOUtils.toInputStream(invoiceString, StandardCharsets.UTF_8);
        return ret;
    }

    private String clearInvoiceTagAttributes(String invoiceString) {
        return invoiceString.replaceAll("<invoice[^>]*>", "<invoice>");
    }

    private File getTargetFile(Invoice invoice) {
        ExportConfiguration cfg = applicationConfig.getPdfInvoiceExportConfig();
        String formattedName = NameFormatter
                .getFormattedName(invoice.getInvoiceNumber(), NameFormatter.NamePostfix.pdf);
        TargetFileInfo fileInfo = cfg.fileConfig(formattedName, invoice.getInvoiceDate());
        File file = fileInfo.getTargetFile(true);
        return file;
    }

    private File getSourceFile(Invoice invoice) throws PdfExportException {
        InvoiceExportConfiguration cfg = (InvoiceExportConfiguration) applicationConfig.getXmlInvoiceExportConfig();
        String formattedName = NameFormatter
                .getFormattedName(invoice.getInvoiceNumber(), NameFormatter.NamePostfix.xml);
        TargetFileInfo srcFile = cfg.archiveFileConfig(formattedName, invoice.getInvoiceDate());
        File xmlFile = srcFile.getTargetFile(false);
        if (!xmlFile.exists()) {
            throw new PdfExportException("Source file does not exist: " + srcFile);
        }
        return xmlFile;
    }

    private class InvoiceResourceResolver implements ResourceResolver {

        private final ResourceResolver defaultResolver = ResourceResolverFactory.createDefaultResourceResolver();

        @Override
        public Resource getResource(URI uri) throws IOException {
            String uriString = uri.toString();
            if (getClass().getResource(uriString) == null) {
                LOGGER.warn(String.format("Resource %s not found in application resources. "
                        + "Falling back to default resolver.", uriString));
                return defaultResolver.getResource(uri);
            } else {
                return new Resource(getClass().getResourceAsStream(uriString));
            }
        }

        @Override
        public OutputStream getOutputStream(URI uri) throws IOException {
            return defaultResolver.getOutputStream(uri);
        }
    }

}
