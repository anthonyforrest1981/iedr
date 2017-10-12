package pl.nask.crs.commons.email.service.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingAttempt;
import pl.nask.crs.commons.email.service.EmailSendingException;

public class CachingEmailSender implements EmailSender {
    private final static Logger LOG = Logger.getLogger(CachingEmailSender.class);

    private EmailQueue emailQueue;
    private ApplicationConfig applicationConfig;

    public CachingEmailSender(EmailQueue emailCache, ApplicationConfig applicationConfig) {
        this.emailQueue = emailCache;
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void sendEmail(Email email) throws IllegalArgumentException, EmailSendingException {
        LOG.info("Queued email: " + email);
        queue(email);
    }

    private void queue(Email email) {
        int attemptLimit = applicationConfig.getEmailAttemptLimit();
        try {
            emailQueue.queue(new EmailSendingAttempt(email, attemptLimit));
        } catch (InterruptedException e) {
            LOG.error("Interrupted while adding an email to the wait queue (this email will not be send): " + email);
        }
    }

    @Override
    public String toString() {
        return "CachingEmailSender";
    }
}
