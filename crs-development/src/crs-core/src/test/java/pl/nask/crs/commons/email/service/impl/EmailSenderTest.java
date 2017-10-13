package pl.nask.crs.commons.email.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.MimeUtility;

import mockit.Mock;
import mockit.MockUp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.Email;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(locations = {"classpath:commons-config-test.xml", "classpath:email-test-config.xml"})
public class EmailSenderTest extends AbstractTransactionalTestNGSpringContextTests {

    private String fileEncoding;
    private String mimeCharset;

    @Resource
    EmailSenderImpl emailSender;

    @Resource
    String emailSenderAdditionlBcc;

    @BeforeTest
    public void spoilDefaultCharset() {
        fileEncoding = System.getProperty("file.encoding");
        mimeCharset = Charset.defaultCharset().toString();
        System.setProperty("file.encoding", "ISO8859_1");
        System.setProperty("mail.mime.charset", "ISO8859_1");
    }

    @AfterTest
    public void fixDefaultCharset() {
        System.setProperty("file.encoding", fileEncoding);
        System.setProperty("mail.mime.charset", mimeCharset);
    }

    private Email email = new Email();
    {
        email.setSubject("subject");
        email.setText("test");
        email.setFrom("from@from.from");
        email.setToList(Arrays.asList("to@to.to"));
        email.setCcList(Arrays.asList("cc@cc.cc"));
        email.setBccList(Arrays.asList("bcc@bcc.bcc"));
    }

    public static final class LogBccEmailSender extends MockUp<EmailSenderImpl> {

        public List<String> bccArguments = new ArrayList<String>();

        public LogBccEmailSender(EmailSenderImpl instance) {
            super(instance);
        }

        @Mock
        private void send(int emailTemplateId, String from, String to, String cc, String bcc, String subject,
                String body, boolean isHtml, List<File> files) {
            bccArguments.add(bcc);
        }

    }

    public static final class LogMessageEmailSender extends MockUp<EmailSenderImpl> {

        public Message message;

        public LogMessageEmailSender(EmailSenderImpl instance) {
            super(instance);
        }

        @Mock
        private void sendMessage(Session session, Message message) throws IOException, MessagingException {
            // Simulate message being written to stream.
            message.writeTo(System.out);
            this.message = message;
        }

    }

    @Test
    public void sendEmailWithAdditionalBccTest() throws Exception {
        // This is to test CRS-583. Due to a bug, emailSenderAdditionlBcc address was added as many times, as
        // emailSender.sendEmail(email) was called.
        List<String> expectedBccList = new ArrayList<>(email.getBccList());
        expectedBccList.add(emailSenderAdditionlBcc);
        String expectedBcc = StringUtils.join(expectedBccList, ", ");

        LogBccEmailSender mockedSender = new LogBccEmailSender(emailSender);
        for (int i = 0; i < 10; i++) {
            emailSender.sendEmail(email);
            assertEquals(mockedSender.bccArguments.size(), i + 1);
            assertEquals(mockedSender.bccArguments.get(i), expectedBcc,
                    String.format("Expected different bcc on attempt number %s", i + 1));
        }
    }

    @Test
    public void sendEmailUtf8CharactersHtml() throws Exception {
        testSendingEmailWithUtf8CharactersInBody(true);
    }

    @Test
    public void sendEmailUtf8CharactersNonHtml() throws Exception {
        testSendingEmailWithUtf8CharactersInBody(false);
    }

    @Test
    public void sendEmailUtf8CharactersInSubject() throws Exception {
        LogMessageEmailSender mockedSender = new LogMessageEmailSender(emailSender);
        String emailSubject = "Text with couple of non-ASCII characters: \u00a0\u0b33\u201c\u201d";
        email.setSubject(emailSubject);
        emailSender.sendEmail(email);
        assertEquals(mockedSender.message.getSubject(), emailSubject);
    }

    @Test
    public void sendEmailWithInternationalAddresses() throws Exception {
        LogMessageEmailSender mockedSender = new LogMessageEmailSender(emailSender);
        Email testEmail = new Email();
        testEmail.setSubject("subject");
        testEmail.setText("Test content");
        testEmail.setFrom("from@iędr.ie");
        testEmail.setToList(Arrays.asList("to.address1@iędr.ie", "to.address2@iędr.ie"));
        testEmail.setCcList(Arrays.asList("cc.address1@iędr.ie", "cc.address2@iędr.ie"));
        testEmail.setBccList(Arrays.asList("bcc.address1@iędr.ie", "bcc.address2@iędr.ie"));
        emailSender.sendEmail(testEmail);

        String from = mockedSender.message.getHeader("From")[0];
        String tos = mockedSender.message.getHeader("To")[0];
        String ccs = mockedSender.message.getHeader("Cc")[0];
        String bccs = mockedSender.message.getHeader("Bcc")[0];
        assertEquals(from, "from@xn--idr-sra.ie");
        assertEquals(tos, "to.address1@xn--idr-sra.ie, to.address2@xn--idr-sra.ie");
        assertEquals(ccs, "cc.address1@xn--idr-sra.ie, cc.address2@xn--idr-sra.ie");
        assertEquals(bccs, "bcc.address1@xn--idr-sra.ie, bcc.address2@xn--idr-sra.ie, \r\n\tadditionalBcc@test.test");
    }

    private void testSendingEmailWithUtf8CharactersInBody(boolean isHtml) throws Exception {
        LogMessageEmailSender mockedSender = new LogMessageEmailSender(emailSender);
        String startsEmailWith = "Text with couple of non-ASCII characters: ";
        String emailText = startsEmailWith + "\u00a0\u0b33\u201c\u201d";
        email.setText(emailText);
        email.setHtml(isHtml);
        emailSender.sendEmail(email);

        Multipart contentMultipart = (Multipart) mockedSender.message.getContent();
        BodyPart content = contentMultipart.getBodyPart(0);
        String contentType = String.format("text/%s; charset=UTF-8", isHtml ? "html" : "plain");
        assertEquals(content.getContentType(), contentType);
        assertEquals(MimeUtility.getEncoding(content.getDataHandler()), "quoted-printable");
        assertEquals(Arrays.asList(content.getHeader("Content-Transfer-Encoding")), Arrays.asList("quoted-printable"));
        // Changed assertion here as there was discrepancy in assertion where e.g. java.lang.AssertionError: expected 
        // [Text with couple of non-ASCII characters: ????] but found [Text with couple of non-ASCII characters: ???????????]
        StringWriter writer = new StringWriter();
	IOUtils.copy(content.getInputStream(), writer, "UTF-8");
	String convertedString = writer.toString();

	assertTrue(IOUtils.toString(content.getInputStream()).startsWith(startsEmailWith));
	assertEquals(emailText, convertedString);
    }
}
