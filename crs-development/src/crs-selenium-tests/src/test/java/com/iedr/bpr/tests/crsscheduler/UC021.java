package com.iedr.bpr.tests.crsscheduler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage;
import com.iedr.bpr.tests.pages.console.PaymentForDomainsPage.Domain;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.sun.mail.util.BASE64DecoderStream;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.scheduler;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC021 extends SeleniumTest {

    private float initialDepositAmount;

    public UC021(Browser browser) {
        super(browser);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        initialDepositAmount = db().getDepositAmount(this.registrar.login);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        db().setDepositAmount(this.registrar.login, initialDepositAmount);
        super.tearDown();
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsscheduler/uc021_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsscheduler/uc021_data.sql";
    }

    @Test
    public void uc021_qa12142() throws SQLException, MessagingException, IOException {
        User user = this.direct;
        Domain domain = new Domain("uc021-qa12142.ie", 1);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        testPaymentInvoicing(user, domain, paymentDetails, false);
    }

    @Test
    public void uc021_qa12143() throws SQLException, MessagingException, IOException {
        User user = this.registrar;
        Domain domain = new Domain("uc021-qa12143.ie", 1);
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        testPaymentInvoicing(user, domain, paymentDetails, false);
    }

    @Test
    public void uc021_qa12144() throws SQLException, MessagingException, IOException {
        User user = this.registrar;
        Domain domain = new Domain("uc021-qa12144.ie", 1);
        PaymentMethod method = PaymentMethod.ADP;
        scheduler().runJob(SchedulerJob.AUTORENEWAL);
        scheduler().runJob(SchedulerJob.INVOICING);
        invoiceEmailReceived(user, domain, method);
    }

    @Test
    public void uc021_qa12147() throws SQLException, MessagingException, IOException {
        User user = this.direct;
        Domain domain = new Domain("uc021-qa12147.ie", 1);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        testPaymentInvoicing(user, domain, paymentDetails, true);
    }

    @Test
    public void uc021_qa12148() throws SQLException {
        User user = this.direct;
        User gaining = this.registrar;
        Domain domain = new Domain("uc021-qa12148.ie", 1);
        testPaymentNotPossible(user, gaining, domain);
    }

    @Test
    public void uc021_qa12149() throws SQLException {
        User user = this.registrarNonVat;
        User gaining = this.registrar;
        Domain domain = new Domain("uc021-qa12149.ie", 1);
        testPaymentNotPossible(user, gaining, domain);
    }

    @Test
    public void uc021_qa121411() throws SQLException, MessagingException, IOException {
        User user = this.registrar;
        Domain domain = new Domain("uc021-qa121411.ie", 1);
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        testPaymentInvoicing(user, domain, paymentDetails, false);
    }

    @Test
    public void uc021_qa12145() throws SQLException, MessagingException, IOException {
        User user = this.registrar;
        Domain domain = new Domain("uc021-qa12145.ie", 1);
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        testPaymentInvoicing(user, domain, paymentDetails, false);
    }

    @Test
    public void uc021_nosc01() throws SQLException {
        // UC#021: Generate Invoices and Receipts - Invoice Failure: Credit-card
        // transaction
        User user = this.registrar;
        String domainName = "uc021-nosc01.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        Map<Integer, Integer> initialCounts = getInvoiceFailureEmailsCount();
        registerDomain(user, domainName, paymentDetails);
        invalidateTransactionAuthcode(domainName);
        Map<Integer, Integer> finalCounts = getInvoiceFailureEmailsCount();
        assertEquals(getEmailsCountErrorMessage(initialCounts, finalCounts, 25), 1,
                finalCounts.get(25) - initialCounts.get(25));
        // TODO: This should be uncommented once CTD-38 is taken care of.
        // assertEquals(getEmailsCountErrorMessage(initialCounts, finalCounts, 60), 0,
        //         finalCounts.get(60) - initialCounts.get(60));
    }

    @Test
    public void uc021_nosc02() throws SQLException {
        // UC#021: Generate Invoices and Receipts - Invoice Failure: Deposit
        // transaction
        User user = this.registrar;
        String domainName = "uc021-nosc01.ie";
        PaymentDetails paymentDetails = PredefinedPayments.DEPOSIT_PAYMENT_DETAILS;
        db().setDepositAmount(user.login, 0);
        Map<Integer, Integer> initialCounts = getInvoiceFailureEmailsCount();
        db().setDepositAmount(user.login, initialDepositAmount);
        registerDomain(user, domainName, paymentDetails);
        db().setDepositAmount(user.login, 0);
        Map<Integer, Integer> finalCounts = getInvoiceFailureEmailsCount();
        assertEquals(getEmailsCountErrorMessage(initialCounts, finalCounts, 60), 1,
                finalCounts.get(60) - initialCounts.get(60));
        assertEquals(getEmailsCountErrorMessage(initialCounts, finalCounts, 25), 0,
                finalCounts.get(25) - initialCounts.get(25));
    }

    private void testPaymentInvoicing(User user, Domain domain, PaymentDetails paymentDetails, boolean nrp)
            throws SQLException, MessagingException, IOException {
        List<Domain> domains = Arrays.asList(domain);
        console().login(user);
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, nrp, 0);
        pfd.payForDomainsSuccess(user, domain.name, paymentDetails);
        scheduler().runJob(SchedulerJob.INVOICING);
        assertTrue(invoiceEmailReceived(user, domain, paymentDetails.getMethod()));
    }

    private void testPaymentNotPossible(User user, User gaining, Domain domain) throws SQLException {
        console().login(gaining);
        DomainTransferUtils.transferDomain(domain.name, gaining, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);

        console().login(user);
        List<Domain> domains = Arrays.asList(domain);
        PaymentForDomainsPage pfd = new PaymentForDomainsPage(domains, false, 0);
        pfd.openPaymentScreen(user, domain.name, PaymentMethod.CARD);
        assertTrue(console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        assertEquals(String.format("Operation \"Pay Online\" cannot be performed for the domain: %s (Transfer pending)",
                domain.name), alert.getText());
        alert.dismiss();
    }

    private void registerDomain(User user, String domainName, PaymentDetails paymentDetails) throws SQLException {
        console().login(user);
        DomainRegistrationUtils.registerDomain(domainName, user, paymentDetails);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
    }

    private void invalidateTransactionAuthcode(String domainName) throws SQLException {
        int reservationId = db().getReservationId(domainName);
        int transactionId = db().getReservationTransactionId(reservationId);
        db().invalidateTransactionRealexAuthcode(transactionId);
    }

    private boolean invoiceEmailReceived(User user, Domain domain, PaymentMethod method)
            throws MessagingException, IOException, SQLException {
        ExpectedEmailSummary invoiceEmail = emailSummaryGenerator.getInvoiceEmail(user, method);
        emails.add(invoiceEmail);
        Set<ActualEmailSummary> received = partiallyCheckAndResetEmails(emails);
        for (ActualEmailSummary e : received) {
            if (invoiceEmail.id == e.id && pdfWithDomainAttached(e, domain)) {
                return true;
            }
        }
        return false;
    }

    private boolean pdfWithDomainAttached(ActualEmailSummary email, Domain domain)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session, new ByteArrayInputStream(email.bytes));
        if (!message.getContentType().contains("multipart")) {
            return false;
        }

        Multipart multiPart = (Multipart) message.getContent();
        for (int i = 0; i < multiPart.getCount(); i++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
            boolean isPdf = Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())
                    && part.getContentType().contains("application/octet-stream");
            if (isPdf) {
                BASE64DecoderStream content = (BASE64DecoderStream) part.getContent();
                String text = getPdfText(content);
                if (text.contains(domain.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getPdfText(BASE64DecoderStream content) throws IOException {
        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        try {
            parser = new PDFParser(content);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            String parsedText = pdfStripper.getText(pdDoc);
            return parsedText;
        } finally {
            if (cosDoc != null) {
                cosDoc.close();
            }
            if (pdDoc != null) {
                pdDoc.close();
            }
        }
    }

    private String getEmailsCountErrorMessage(Map<Integer, Integer> initialCounts, Map<Integer, Integer> finalCounts,
            int eId) {
        int difference = finalCounts.get(eId) - initialCounts.get(eId);
        return String.format("Got %s unexpected E_ID=%s emails while executing Invoicing " + "job for this domain.",
                difference, eId);
    }

    private Map<Integer, Integer> getInvoiceFailureEmailsCount() throws SQLException {
        ExpectedEmailSummary ccEmail = emailSummaryGenerator.getInvoiceFailureEmail(PaymentMethod.CARD);
        ExpectedEmailSummary adpEmail = emailSummaryGenerator.getInvoiceFailureEmail(PaymentMethod.ADP);
        return countEmailsFromJob(SchedulerJob.INVOICING, Arrays.asList(ccEmail, adpEmail));
    }

}
