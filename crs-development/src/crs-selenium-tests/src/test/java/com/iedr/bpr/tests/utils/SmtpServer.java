package com.iedr.bpr.tests.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import com.iedr.bpr.tests.utils.email.ActualEmailSummary;

public class SmtpServer {

    private static final SmtpServer INSTANCE = new SmtpServer();
    private SimpleSmtpServer server;

    private SmtpServer() {}

    public static SmtpServer getServer() {
        return INSTANCE;
    }

    public void start() {
        SimpleSmtpServer smtp = null;
        smtp = SimpleSmtpServer.start(5000);
        if (smtp == null || smtp.isStopped()) {
            throw new RuntimeException("Couldn't start SMTP server");
        }
        server = smtp;
    }

    public boolean isStopped() {
        return server.isStopped();
    }

    public void stop() {
        server.stop();
    }

    public void restart() {
        stop();
        start();
    }

    public Set<ActualEmailSummary> getEmailSummaries() {
        Set<ActualEmailSummary> emails = new HashSet<ActualEmailSummary>();
        @SuppressWarnings("unchecked")
        Iterator<SmtpMessage> emailsIt = server.getReceivedEmail();
        SmtpMessage email;
        while (emailsIt.hasNext()) {
            email = emailsIt.next();
            ActualEmailSummary summary = new ActualEmailSummary(email);
            // TODO: for now ignore scheduler job failures
            if (!summary.subject.equals("Scheduler job finished with errors")) {
                emails.add(summary);
            }
        }
        return emails;
    }

}
