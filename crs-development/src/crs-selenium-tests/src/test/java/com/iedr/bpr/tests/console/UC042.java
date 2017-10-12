package com.iedr.bpr.tests.console;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import com.iedr.bpr.tests.IgnoredBrowsers;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.InvoicePage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC042 extends SeleniumTest {

    public UC042(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc042_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return null;
    }

    @Test
    @IgnoredBrowsers({Browser.IE})
    // Retrieving cookies from IE is broken, which prevents us from downloading the invoice file using the HTTP-based
    // FileDownloader.
    public void test_uc042_sc01_registrar() throws Exception {
        User user = this.registrar;
        List<String> invoiceTypes = Arrays.asList("pdf", "xml");
        test_view_invoice(user, invoiceTypes);
    }

    @Test
    public void test_uc042_sc02() throws IOException, MessagingException, SQLException {
        User user = this.registrar;
        List<String> invoiceTypes = Arrays.asList("pdf", "xml");
        emails.add(emailSummaryGenerator.getInvoiceRequestedEmail(user));
        test_send_invoice(user, invoiceTypes);
    }

    @Test
    @IgnoredBrowsers({Browser.IE})
    // Retrieving cookies from IE is broken, which prevents us from downloading the invoice file using the HTTP-based
    // FileDownloader.
    public void test_uc042_qa14221() throws Exception {
        User user = this.direct;
        List<String> invoiceTypes = Arrays.asList("pdf", "xml");
        test_view_invoice(user, invoiceTypes);
    }

    @Test
    public void test_uc042_nosc01() throws SQLException {
        // UC#042: Access Invoice - View invoice history - Direct
        User user = this.direct;
        test_view_old_invoice(user);
    }

    @Test
    public void test_uc042_nosc02() throws SQLException, ParseException {
        // UC#042: Access Invoice - View invoice history - Registrar
        User user = this.registrar;
        test_view_old_invoice(user);
    }

    private void test_view_invoice(User user, List<String> invoiceTypes) throws Exception {
        String domainName = "uc042.ie";
        String invoiceNumber = generateInvoice(user, domainName);
        InvoicePage ip = new InvoicePage();
        ip.view();
        ip.checkInvoiceTypes(invoiceNumber, invoiceTypes);
        for (String invoiceType : invoiceTypes) {
            viewInvoice(invoiceNumber, invoiceType, ip);
        }
    }

    private void test_view_old_invoice(User user) throws SQLException {
        String domainName = "uc042.ie";
        String invoiceNumber = generateInvoice(user, domainName);
        Calendar cal = Calendar.getInstance();
        cal.set(2013, 10, 1); // November 2013
        db().setReservationHistSettledDate(db().getReservationHistId(domainName), cal.getTime());
        InvoicePage ip = new InvoicePage();
        ip.view();
        ip.selectMonth(11, 2013);
        assertTrue("Invoice not found", ip.invoiceVisible(invoiceNumber));
    }

    private String generateInvoice(User user, String domainName) throws SQLException {
        console().login(user);
        registerDomain(user, domainName, PredefinedPayments.VALID_CREDIT_CARD);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        scheduler().runJob(SchedulerJob.INVOICING);
        smtpServer.restart();
        return db().getSettledInvoiceNumber(domainName);
    }

    private void registerDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails, 1, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
    }

    private void viewInvoice(String invoiceNumber, String invoiceType, InvoicePage ip) throws Exception {
        String invoice = ip.viewInvoice(invoiceNumber, invoiceType);

        if (invoiceType == "pdf") {
            assertTrue(invoice, invoice.contains("<pdf:PDFVersion>"));
        } else {
            assertTrue(invoice, invoice.contains("<invoice"));
            assertTrue(invoice, invoice.contains(String.format("<number>%s</number>", invoiceNumber)));
        }
    }

    public void test_send_invoice(User user, List<String> invoiceTypes)
            throws IOException, MessagingException, SQLException {
        String domainName = "uc042.ie";
        InvoicePage ip = new InvoicePage();
        String invoiceNumber = generateInvoice(user, domainName);
        ip.view();
        assertTrue("Sending invoice was unsuccessful", ip.sendInvoice(invoiceNumber));
        checkEmails(emails, invoiceNumber, invoiceTypes);
    }

    private void checkEmails(Set<ExpectedEmailSummary> expectedEmails, String invoiceNumber, List<String> invoiceTypes)
            throws MessagingException, IOException {
        Set<ActualEmailSummary> actualEmails = super.checkAndResetEmails(expectedEmails);
        for (ActualEmailSummary email : actualEmails) {
            checkAttachments(email, invoiceNumber, invoiceTypes);
        }
    }

    private void checkAttachments(ActualEmailSummary email, String invoiceNumber, List<String> invoiceTypes)
            throws MessagingException, IOException {
        Set<String> fileNames = new TreeSet<String>();
        for (String invoiceType : invoiceTypes) {
            fileNames.add(invoiceNumber + "." + invoiceType);
        }
        Properties props = new Properties();
        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session, new ByteArrayInputStream(email.bytes));

        assertTrue(message.getContentType().contains("multipart"));
        Multipart multiPart = (Multipart) message.getContent();
        assertEquals(invoiceTypes.size() + 1, multiPart.getCount());
        for (int i = 0; i < multiPart.getCount(); i++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                assertTrue(fileNames.contains(part.getFileName()));
                fileNames.remove(part.getFileName());
            }
        }
        assertTrue(fileNames.isEmpty());
    }
}
