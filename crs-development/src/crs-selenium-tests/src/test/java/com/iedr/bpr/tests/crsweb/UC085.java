package com.iedr.bpr.tests.crsweb;

import java.io.*;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.pages.crsweb.DocumentsPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.verifier.ReceivedEmailsVerifier;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UC085 extends SeleniumTest {

    private String documentsEmailAddress;
    private String documentsFrom;

    public UC085(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc085_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc085_data.sql";
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.documentsEmailAddress = config.getProperty("documentsemailaddress");
        documentsFrom = "notareal@email.com";
    }

    @Test
    public void test_uc085_sc01() throws MessagingException, IOException {
        List<String> domains = Arrays.asList("uc085-with-ticket-a.ie", "uc085-with-ticket-b.ie");
        List<Attachment> attachments = Collections.singletonList(getResourceAttachment("document.png", "image/png"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.BASIC);
        checkBasicResponse(response, domains, attachments, "%s: OK, file saved");
        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(1, domains);
    }

    @Test
    public void test_uc085_sc02() throws MessagingException, IOException {
        List<String> domains = Arrays.asList("uc085-with-ticket-a.ie", "uc085-with-ticket-b.ie");
        List<Attachment> attachments = Collections.singletonList(getRandomAttachment(5 * 1024 * 1024 + 1,
                "document.png", "image/png"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.BASIC);
        checkBasicResponse(response, domains, attachments, "%s: File too big, discarded");
        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(0, domains);
    }

    @Test
    public void test_uc085_sc03() throws Exception {
        List<String> domains = Arrays.asList("uc085-with-ticket-a.ie", "uc085-with-ticket-b.ie");
        List<Attachment> attachments = Collections.singletonList(getTextAttachment("A,B,C", "document.csv",
                "text/csv"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.BASIC);
        checkBasicResponse(response, domains, attachments, "%s: Wrong file type, discarded");
        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(0, domains);
    }

    @Test
    public void test_uc085_sc04() throws Exception {
        String domainName = "uc085-with-ticket-a.ie";
        List<String> domains = Collections.singletonList(domainName);
        List<Attachment> attachments = Collections.singletonList(getResourceAttachment("document.png", "image/png"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        waitForEmailReceived();

        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.viewReviseAndEditTicket(domainName);
        assertEquals("YES", wd().findElement(By.className("showDocs")).getText());
        crsweb().clickElement(By.cssSelector(".showDocs-icon img"));
        crsweb().waitForElementPresent(By.id("documentRow"));
        assertEquals(domainName, wd().findElement(By.className("documents-name")).getText());

        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(1, domains);
    }

    @Test
    public void test_uc085_sc05() throws Exception {
        String domainName = "uc085-with-ticket-a.ie";
        List<String> domains = Collections.singletonList(domainName);
        List<Attachment> attachments = Collections.singletonList(getResourceAttachment("document.png", "image/png"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        waitForEmailReceived();

        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        // This verifies that user can find the assigned document via Documents tab.
        checkAssignedDocumentsCount(1, domains);
    }

    @Test
    public void test_uc085_sc10() throws MessagingException {
        List<String> domains = Arrays.asList("uc085-with-ticket-a.ie", "uc085-with-ticket-b.ie");
        List<Attachment> attachments = new ArrayList<>();
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.FAILURE);
        assertTrue(StringUtils.join(response.to, ", "), response.to.contains(documentsFrom));
        String message = "No files were uploaded, required at least one attachment";
        assertTrue(response.text, response.text.contains(message));
        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(0, domains);
    }

    @Test
    public void test_uc085_sc11() throws Exception {
        List<String> domains = Collections.singletonList("not-known.ie");
        List<Attachment> attachments = Collections.singletonList(getResourceAttachment("document.png", "image/png"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        sendDocuments(domains, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.BASIC);
        checkBasicResponse(response, domains, attachments, "%s: OK, file saved");
        checkUnknownDomainsInResponse(response, domains);
        assertEquals(newDocumentsCount + 1, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(0, domains);
    }

    @Test
    public void test_uc085_sc12() throws Exception {
        List<Attachment> attachments = Collections.singletonList(getResourceAttachment("document.png", "image/png"));
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        String to = documentsEmailAddress;
        String from = documentsFrom;
        String subject = "";
        String domainName = "uc085-with-ticket.ie";
        String body = String.format("Domains: \n%s", domainName);
        sendEmail(to, from, subject, body, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.PARSE_FAILURE);
        checkAttachmentsInResponse(response, attachments, "%s: OK, file saved");
        assertEquals(newDocumentsCount + 1, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(0, Collections.singletonList(domainName));
    }

    @Test
    public void test_uc085_nosc01() throws Exception {
        // UC#085: Sending an email with encoding
        List<String> domains = Arrays.asList("uc085-with-ticket-a.ie", "uc085-with-ticket-b.ie");

        String longNonAsciiFileName = "d" + "\u00f6" + "c" + "\u00fc" + "m" + "\u00eb" + "nt"
                + "-ithastobereallyreallyreallylongtoserveourpurpose.png";
        String encoded = MimeUtility.encodeWord(longNonAsciiFileName, "UTF-8", "Q");
        Attachment attachmentToSend = getTextAttachment("", encoded, "image/png");
        Attachment attachmentToCheck = getTextAttachment("", longNonAsciiFileName, "image/png");

        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();

        sendDocuments(domains, Collections.singletonList(attachmentToSend));
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.BASIC);
        checkAttachmentsInResponse(response, Collections.singletonList(attachmentToCheck), "%s: OK, file saved");
        assertEquals(newDocumentsCount, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(1, domains);
    }

    @Test
    public void test_uc085_nosc02() throws Exception {
        // UC#085: Sending an email with attachment(s), some domains known, some unknown
        DocumentsPage dp = new DocumentsPage(this.internal);
        int newDocumentsCount = dp.getNewDocumentsCount();
        String nonExistingDomain = "non-existing-domain.ie";
        String existingDomain = "uc085-with-ticket-a.ie";
        List<String> domains = Arrays.asList(nonExistingDomain, existingDomain);
        List<Attachment> attachments = Collections.singletonList(getResourceAttachment("document.png", "image/png"));
        sendDocuments(domains, attachments);
        ActualEmailSummary response = getDocumentsUploadReport(UploadReportType.BASIC);
        checkBasicResponse(response, domains, attachments, "%s: OK, file saved");
        checkUnknownDomainsInResponse(response, Collections.singletonList(nonExistingDomain));
        assertEquals(newDocumentsCount + 1, dp.getNewDocumentsCount());
        checkAssignedDocumentsCount(1, Collections.singletonList(existingDomain));
        checkAssignedDocumentsCount(0, Collections.singletonList(nonExistingDomain));
    }

    private Attachment getResourceAttachment(String fileName, String mimeType) throws IOException {
        InputStream is = getClass().getResourceAsStream("/documentToUpload.png");
        DataSource source = new ByteArrayDataSource(is, mimeType);
        return new Attachment(source, fileName);
    }

    private Attachment getRandomAttachment(long size, String fileName, String mimeType) throws IOException {
        File temp = File.createTempFile("randomFile", null);
        temp.deleteOnExit();
        String path = temp.getAbsolutePath();
        RandomAccessFile f = new RandomAccessFile(path, "rw");
        try {
            f.setLength(size);
        } finally {
            f.close();
        }
        InputStream is = new FileInputStream(path);
        DataSource source = new ByteArrayDataSource(is, mimeType);
        return new Attachment(source, fileName);
    }

    private Attachment getTextAttachment(String text, String fileName, String mimeType) throws IOException {
        InputStream is = new ByteArrayInputStream(text.getBytes());
        DataSource source = new ByteArrayDataSource(is, mimeType);
        return new Attachment(source, fileName);
    }

    private void sendDocuments(List<String> domains, List<Attachment> attachments) throws MessagingException {
        String to = documentsEmailAddress;
        String from = documentsFrom;
        String subject = "";
        String body = String.format("Domains: %s", StringUtils.join(domains, ","));
        sendEmail(to, from, subject, body, attachments);
    }

    private void sendEmail(String to, String from, String subject, String body, List<Attachment> attachments)
            throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", config.getProperty("documentssmtpserver"));
        properties.setProperty("mail.smtp.port", config.getProperty("documentssmtpport"));
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        if (attachments.isEmpty()) {
            message.setText(body);
        } else {
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBody = new MimeBodyPart();
            messageBody.setText(body);
            multipart.addBodyPart(messageBody);
            for (Attachment attachment : attachments) {
                MimeBodyPart messageAssignment = new MimeBodyPart();
                messageAssignment.setDataHandler(new DataHandler(attachment.source));
                messageAssignment.setFileName(attachment.fileName);
                multipart.addBodyPart(messageAssignment);
            }
            message.setContent(multipart);
        }
        Transport.send(message);
    }

    private ActualEmailSummary getDocumentsUploadReport(UploadReportType type) {
        waitForEmailReceived();
        ExpectedEmailSummary expectedEmail;
        switch (type) {
            case BASIC:
                expectedEmail = emailSummaryGenerator.getDocumentUploadReportBasicEmail(documentsFrom);
                break;
            case PARSE_FAILURE:
                expectedEmail = emailSummaryGenerator.getDocumentUploadReportParseFailureEmail(documentsFrom);
                break;
            case FAILURE:
                expectedEmail = emailSummaryGenerator.getDocumentUploadReportGeneralFailureEmail(documentsFrom);
                break;
            default:
                throw new RuntimeException();
        }
        emails.add(expectedEmail);
        Set<ActualEmailSummary> actualEmails = checkAndResetEmails(new HashSet<>(emails));
        assertEquals(1, actualEmails.size());
        ReceivedEmailsVerifier rev = new ReceivedEmailsVerifier(emails, actualEmails);
        return rev.getActualEmail(expectedEmail);
    }

    private void checkBasicResponse(ActualEmailSummary response, List<String> domains, List<Attachment> attachments,
            String attachmentComment) {
        assertTrue(response.to.contains(documentsFrom));
        String domainsLine = String.format("\"%s\"", StringUtils.join(domains, "\", \""));
        assertTrue(response.text, response.text.contains(domainsLine));
        checkAttachmentsInResponse(response, attachments, attachmentComment);
    }

    private void checkAttachmentsInResponse(ActualEmailSummary response, List<Attachment> attachments,
            String attachmentComment) {
        for (Attachment attachment : attachments) {
            String attachmentLine = String.format(attachmentComment, attachment.fileName);
            assertTrue(response.text, response.text.contains(attachmentLine));
        }
    }

    private void checkUnknownDomainsInResponse(ActualEmailSummary response, List<String> unknownDomains) {
        assertTrue(response.text, response.text.contains(
                String.format("Please note that the following domains listed in your email could not be identified: %s",
                        StringUtils.join(unknownDomains, ", "))));
    }

    private void checkAssignedDocumentsCount(int count, List<String> domains) {
        DocumentsPage dp = new DocumentsPage(this.internal);
        for (String domain : domains) {
            dp.viewDocumentsList(domain, true);
            assertEquals(count, dp.getResultsCount());
        }
    }

    private class Attachment {
        public DataSource source;
        public String fileName;

        public Attachment(DataSource source, String fileName) {
            this.source = source;
            this.fileName = fileName;
        }
    }

    private enum UploadReportType {
        BASIC, PARSE_FAILURE, FAILURE
    }

}
