package pl.nask.crs.invoicing;

import java.io.*;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.invoicing.service.impl.InvoiceXMLExporter;
import pl.nask.crs.invoicing.service.impl.XmlToPdfExporter;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.dao.InvoiceDAO;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(locations = {"/invoicing-config.xml"})
public class Xml2PdfTest extends AbstractContextAwareTest {
    private ByteArrayOutputStream errStream;
    private PrintStream currentPrintStream;
    private PrintStream testPrintStream;

    @Autowired
    private XmlToPdfExporter invoicePdfExporter;
    @Autowired
    private InvoiceXMLExporter invoiceXmlExporter;
    @Autowired
    private InvoiceDAO invoiceDAO;

    @BeforeMethod
    public void setUp() throws ExportException {
        currentPrintStream = System.err;
        errStream = new ByteArrayOutputStream();
        testPrintStream = new PrintStream(errStream);
        System.setErr(testPrintStream);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        System.setErr(currentPrintStream);
        testPrintStream.close();
    }

    @Test
    public void testFullXmlToPdfExport() throws Exception {
        Invoice invoice = invoiceDAO.get(996);
        invoiceXmlExporter.export(invoice);
        invoicePdfExporter.export(invoice);
        assertEquals(errStream.toString(), "");
    }

    @Test
    public void testXml2PdfTemplate() throws Exception {
        transformPreparedXml("xmlFile.xml");
    }

    // in a normal case this should take few (about 4) seconds.
    @Test(timeOut = 10 * 1000)
    public void testXml2PdfTemplateLargeInvoice() throws Exception {
        transformPreparedXml("largeXmlFile.xml");
    }

    private void transformPreparedXml(String xmlFileName) throws TransformerException, IOException, FOPException {
        File outDir = new File("target", "xml2PdfOut");
        outDir.mkdirs();
        InputStream invoiceInputStream = getClass().getResourceAsStream("/" + xmlFileName);
        File pdfFile = new File(outDir, xmlFileName + ".pdf");
        invoicePdfExporter.transform(invoiceInputStream, pdfFile, new File(xmlFileName));
        assertEquals(errStream.toString(), "");
    }
}
