package pl.nask.crs.commons.email.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.mail.smtp.SMTPTransport;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Kasia Fulara, Jakub Laszkiewicz
 */
public class EmailSenderImpl implements EmailSender {

    private final static Logger LOG = Logger.getLogger(EmailSenderImpl.class);

    private String mailer;
    private String mailhost;
    private Integer port;
    private String userName;
    private String userPassword;
    private boolean ssl;
    private boolean tls;
    private long timeout = 10000;
    private MailcapCommandMap mailCapCommandMap;

    //TODO move it to properties
    private String specialBccField;

    public EmailSenderImpl(String mailer, String mailhost, Integer port, String userName, String userPassword) {
        this(mailer, mailhost, port, userName, userPassword, false, false);
    }

    public EmailSenderImpl(String mailer, String mailhost, Integer port, String userName, String userPassword,
            String specialBccField) {
        this(mailer, mailhost, port, userName, userPassword, false, false);
        this.specialBccField = specialBccField;
    }

    public EmailSenderImpl(String mailer, String mailhost, String userName, String userPassword) {
        this(mailer, mailhost, null, userName, userPassword, false, false);
    }

    public EmailSenderImpl(String mailer, String mailhost, String userName, String userPassword, String specialBccField) {
        this(mailer, mailhost, null, userName, userPassword, false, false);
        this.specialBccField = specialBccField;
    }

    public EmailSenderImpl(String mailer, String mailhost, Integer port, String userName, String userPassword,
            boolean ssl, boolean tls) {
        Validator.assertNotEmpty(mailhost, "mailhost");
        Validator.assertNotNull(ssl, "ssl");
        Validator.assertNotNull(tls, "tls");
        this.mailer = mailer;
        this.mailhost = mailhost;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
        this.ssl = ssl;
        this.tls = tls;
        this.mailCapCommandMap = this.createNewCommandMap();
    }

    private MailcapCommandMap createNewCommandMap() {
        MailcapCommandMap map = new MailcapCommandMap();
        addMailcapToMap("text/plain", com.sun.mail.handlers.text_plain.class.getName(), false, map);
        addMailcapToMap("text/html", com.sun.mail.handlers.text_html.class.getName(), false, map);
        addMailcapToMap("text/xml", com.sun.mail.handlers.text_xml.class.getName(), false, map);
        addMailcapToMap("multipart/*", com.sun.mail.handlers.multipart_mixed.class.getName(), true, map);
        addMailcapToMap("message/rfc822", com.sun.mail.handlers.message_rfc822.class.getName(), false, map);
        return map;
    }

    private void addMailcapToMap(String mimeType, String handler, boolean fallback, MailcapCommandMap map) {
        String mailcap = String.format("%s;;x-java-content-handler=%s", mimeType, handler);
        if (fallback) {
            mailcap += ";x-java-fallback-entry=true";
        }
        map.addMailcap(mailcap);
    }

    /**
     * JNDI workaround (problem with null entry value)
     *
     * @param port
     */
    public void setPort(String port) {
        if (Validator.isEmpty(port)) {
            this.port = null;
        } else {
            this.port = Integer.valueOf(port);
        }
    }

    private String getProtocol() {
        return ssl ? "smtps" : "smtp";
    }

    private List<String> getEmailExtendedBcc(Email email) {
        List<String> bccList = new ArrayList<>(email.getBccList());
        if (specialBccField != null && !bccList.contains(specialBccField)) {
            bccList.add(specialBccField);
        }
        return bccList;
    }

    public void sendEmail(Email email) throws IllegalArgumentException, EmailSendingException {
        String parsedTo = createAddresseeList(email.getToList());
        String parsedCC = createAddresseeList(email.getCcList());
        String parsedBcc = createAddresseeList(getEmailExtendedBcc(email));
        String from = punifyEmailAddress(email.getFrom());
        try {
            send(email.getTemplateId(), from, parsedTo, parsedCC, parsedBcc, email.getSubject(), email.getText(),
                    email.isHtml(), email.getAttachments());
        } catch (MessagingException e) {
            Logger.getLogger(EmailSenderImpl.class).error(e);
            throw new EmailSendingException("Mail params : mailer=" + mailer + ", mailhost=" + mailhost + ", username="
                    + userName + ", userPassword=" + userPassword, e);
        } catch (FileNotFoundException e) {
            Logger.getLogger(EmailSenderImpl.class).error(e);
            throw new EmailSendingException(e);
        }
        LOG.debug("Sent email: " + email);
    }

    private void send(int emailTemplateId, String from, String to, String cc, String bcc, String subject, String body,
            boolean isHtml, List<File> files) throws MessagingException, FileNotFoundException {
        // Before each email make sure, the right MailcapCommandMap is set.
        CommandMap.setDefaultCommandMap(this.mailCapCommandMap);
        Session session = init(from);
        MimeMessage message = createMessage(session, emailTemplateId, from, to, cc, bcc, subject);
        Multipart multipart = new MimeMultipart();
        addTextMessage(multipart, body, isHtml);
        addAttachments(multipart, files);
        message.setContent(multipart);
        sendMessage(session, message);
    }

    private void addTextMessage(final Multipart multipart, String body, boolean isHtml) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        if (isHtml) {
            bodyPart.setText(body, "UTF-8", "html");
        } else {
            bodyPart.setText(body, "UTF-8");
        }
        multipart.addBodyPart(bodyPart);
    }

    private void addAttachments(final Multipart multipart, List<File> files)
            throws MessagingException, FileNotFoundException {
        if (!Validator.isEmpty(files)) {
            BodyPart bodyPart = null;
            for (File file : files) {
                if (!file.isFile()) {
                    throw new FileNotFoundException(file.getAbsolutePath());
                }
                bodyPart = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(file);
                bodyPart.setDataHandler(new DataHandler(fileDataSource));
                bodyPart.setFileName(file.getName());
                multipart.addBodyPart(bodyPart);
            }
        }
    }

    private String punifyEmailAddress(final String email) {
        String[] parts = email.split("@");
        String punifiedHost = IDN.toASCII(parts[1]);
        return parts[0] + "@" + punifiedHost;
    }

    private String createAddresseeList(List<String> addresseeList) {
        final List<String> punifiedEmails = Lists.transform(addresseeList, new Function<String, String>() {
            @Override
            public String apply(final String email) {
                return punifyEmailAddress(email);
            }
        });
        return StringUtils.join(punifiedEmails, ", ");
    }

    private void sendMessage(Session session, Message message) throws MessagingException {
        SMTPTransport transport = (SMTPTransport) session.getTransport(getProtocol());
        try {
            if (isUser()) {
                if (port != null)
                    transport.connect(mailhost, port, userName, userPassword);
                else
                    transport.connect(mailhost, userName, userPassword);
            } else
                transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            transport.close();
        }
    }

    private MimeMessage createMessage(Session session, int emailTemplate, String from, String to, String cc,
            String bcc, String subject) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to, true));
        message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, true));
        message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, true));
        message.setSubject(subject, "UTF-8");
        message.setHeader("X-Template-Id", Integer.toString(emailTemplate));
        message.setSentDate(new Date());
        return message;
    }

    private Session init(String from) {
        Properties props = System.getProperties();
        props.put("mail." + getProtocol() + ".host", mailhost);
        if (from != null) {
            props.put("mail." + getProtocol() + ".from", from);
        }
        if (port != null) {
            props.put("mail." + getProtocol() + ".port", port.toString());
        }
        if (isUser()) {
            props.put("mail." + getProtocol() + ".auth", "true");
        }
        props.put("mail." + getProtocol() + ".starttls.enable", String.valueOf(tls));
        if (!props.containsKey("mail.smtp.connectiontimeout")) {
            props.put("mail.smtp.connectiontimeout", String.valueOf(timeout));
        }
        Session session = Session.getInstance(props);
        return session;
    }

    private boolean isUser() {
        return userName != null || userPassword != null;
    }

    @Override
    public String toString() {
        return "EmailSender[mailhost=" + mailhost + "]";
    }
}
