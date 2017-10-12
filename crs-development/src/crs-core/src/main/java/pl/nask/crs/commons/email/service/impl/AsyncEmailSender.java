package pl.nask.crs.commons.email.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.utils.EmailValidator;
import pl.nask.crs.commons.utils.InvalidEmailException;

public class AsyncEmailSender {
    private final static Logger LOG = Logger.getLogger(AsyncEmailSender.class);
    public static final int DEFAULT_MILLIS_BEFORE_RETRY = 5000;

    private final EmailSender sender;
    private final EmailQueue queue;
    private final EmailInstantiator instantiator;
    private final EmailTemplateDAO dao;

    private volatile boolean started = false;
    private volatile ExecutorService pool;

    private int poolSize;

    private int sleepTimeBeforeRetryMillis = DEFAULT_MILLIS_BEFORE_RETRY;

    public AsyncEmailSender(EmailSender sender, EmailQueue queue, EmailInstantiator instantiator, EmailTemplateDAO dao) {
        this.sender = sender;
        this.queue = queue;
        this.instantiator = instantiator;
        this.dao = dao;
    }

    public int getSleepTimeBeforeRetryMillis() {
        return sleepTimeBeforeRetryMillis;
    }

    public void setSleepTimeBeforeRetryMillis(int sleepTimeBeforeRetryMillis) {
        this.sleepTimeBeforeRetryMillis = sleepTimeBeforeRetryMillis;
    }

    public synchronized void start(int numberOfWorkers) {
        if (!started) {
            this.poolSize = numberOfWorkers;
            pool = Executors.newFixedThreadPool(numberOfWorkers);
            for (int i = 0; i < numberOfWorkers; i++) {
                pool.submit(new Runnable() {
                    public void run() {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                EmailSendingAttempt attempt = queue.next();
                                send(attempt);
                            } catch (InterruptedException e) {
                                LOG.warn("Interrupted while waiting for the email to be send", e);
                                Thread.currentThread().interrupt();
                            } catch (Exception e) {
                                LOG.fatal("Sender failed to operate " + e.getMessage());
                                Thread.currentThread().interrupt();
                            }
                        }

                        if (Thread.currentThread().isInterrupted()) {
                            LOG.info("Exiting after being interrupted ");
                            int size = queue.size();
                            if (size > 0) {
                                LOG.error("Exiting after being interrupted, " + size + " messages will NOT be send");
                            }
                        }
                    }
                });
            }
        }
        started = true;
    }

    public synchronized boolean stop(int timeout) {
        if (started) {
            pool.shutdownNow();
            try {
                pool.awaitTermination(timeout, TimeUnit.SECONDS);
                if (pool.isTerminated()) {
                    LOG.info("Email sender thread stopped");
                    started = false;
                    poolSize = 0;
                } else {
                    LOG.warn("Failed to stop email sender thread, it failed to stop in a time of " + timeout
                            + " seconds");
                }
            } catch (InterruptedException e) {
                LOG.warn("Failed to stop email sender thread, it's still alive");
            }
        }
        return started;
    }

    private void send(EmailSendingAttempt attempt) {
        Email email = attempt.getEmail();
        try {
            validateEmailRecipients(email);
            sender.sendEmail(email);
        } catch (InvalidEmailException e) {
            LOG.error("Attempted to send email with an invalid address: " + e.getMessage(), e);
            sendErrorMessageFallback(email, e);
        } catch (Exception e) {
            LOG.warn("Error sending email, putting back to the queue / email subject: " + email.getSubject()
                    + " / error was: " + e.getClass().getSimpleName() + ", message: " + e.getMessage(), e);
            attempt.fail();
            if (!attempt.isLimitOver()) {
                LOG.debug("Error sending email, putting back to the queue: " + email + "\n" + attempt.getAttemptsLeft()
                        + " attempts left.", e);
                try {
                    attempt.setDelay(sleepTimeBeforeRetryMillis, TimeUnit.MILLISECONDS);
                    queue.queue(attempt);
                } catch (InterruptedException e1) {
                    LOG.error("Interrupted while adding email to the queue, giving up: " + email);
                }
            } else {
                LOG.error("Error sending email, attempt limit reached, email cancelled / email subject: "
                        + email.getSubject());
                sendErrorMessageFallback(email, e);
            }
        }
    }

    private void validateEmailRecipients(Email email) throws InvalidEmailException {
        validateRecipients(email.getToList());
        validateRecipients(email.getCcList());
        validateRecipients(email.getBccList());
    }

    private void validateRecipients(List<String> recipients) throws InvalidEmailException {
        for (String recipient : recipients) {
            EmailValidator.validateEmail(recipient);
        }
    }

    private void sendErrorMessageFallback(Email cancelledEmail, Exception e) {
        EmailTemplate template = dao.get(EmailTemplateNamesEnum.EMAIL_SENDING_FAILURE.getId());
        MapBasedEmailParameters parameters = new MapBasedEmailParameters();
        parameters.set(ParameterNameEnum.EMAIL_SUBJECT, cancelledEmail.getSubject());
        parameters.set(ParameterNameEnum.EMAIL_TEXT, cancelledEmail.toString());
        parameters.set(ParameterNameEnum.EMAIL_LOGS, e.getMessage());
        try {
            Email errorMessage = instantiator.instantiate(template, parameters, true);
            sender.sendEmail(errorMessage);
        } catch (Exception e1) {
            LOG.error("Failed to send error message", e);
        }
    }

    public EmailQueue getQueue() {
        return queue;
    }

    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public String toString() {
        return "AsyncEmailSender [sender=" + sender + "]";
    }

    public String getConfiguration() {
        return "ThreadPool size:" + poolSize + ", " + sender;
    }

    public boolean isRunning() {
        return started;
    }
}
