package com.iedr.bpr.tests.utils.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.dumbster.smtp.SmtpMessage;

public class ActualEmailSummary extends EmailSummary {

    public String body;
    public byte[] bytes;
    public SmtpMessage smtpMessage;

    public ActualEmailSummary(SmtpMessage email) {
        String templateId = email.getHeaderValue("X-Template-Id");
        this.id = templateId == null ? 0 : Integer.valueOf(templateId);
        this.to = getHeaderAsSet(email, "To");
        this.cc = getHeaderAsSet(email, "Cc");
        this.body = email.getBody();
        this.bytes = email.toString().getBytes();
        this.smtpMessage = email;
        try {
            parseBytes(this.bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getHeaderAsSet(SmtpMessage email, String headerName) {
        String header = email.getHeaderValue(headerName);
        return EmailUtils.getValueAsSet(header);
    }

    private void parseBytes(byte[] bytes) throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session, new ByteArrayInputStream(bytes));
        this.subject = getSubject(message);
        this.text = getText(message);
    }

    private String getSubject(MimeMessage message) throws MessagingException {
        return message.getSubject().replace("\n", "").replace("\r", "");
    }

    private String getText(MimeMessage message) throws MessagingException, IOException {
        Object contentObject = message.getContent();
        String result = null;
        if (contentObject instanceof Multipart) {
            Multipart content = (Multipart) contentObject;
            int count = content.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart part = content.getBodyPart(i);
                if (part.isMimeType("text/plain") || part.isMimeType("text/html")) {
                    result = (String) part.getContent();
                }
            }
        } else {
            result = (String) contentObject;
        }
        if (result != null) {
            result = result.replaceAll("<br ?/>", "\n");
        }
        return result;
    }
}
