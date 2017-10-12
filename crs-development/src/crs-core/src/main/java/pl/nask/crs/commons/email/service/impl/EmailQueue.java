package pl.nask.crs.commons.email.service.impl;

import java.util.concurrent.DelayQueue;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.service.EmailSendingAttempt;

public class EmailQueue {
    private static final Logger LOG = Logger.getLogger(EmailQueue.class);
    private final DelayQueue<EmailSendingAttempt> queue = new DelayQueue<EmailSendingAttempt>();

    public EmailSendingAttempt next() throws InterruptedException {
        return queue.take();
    }

    public void queue(EmailSendingAttempt emailSendingAttempt) throws InterruptedException {
        queue.put(emailSendingAttempt);
        int size = size();
        if (size > 100) {
            LOG.warn("Email Queue is growing (" + size + "), is there any consumer running?");
        }
    }

    public int size() {
        return queue.size();
    }
}
