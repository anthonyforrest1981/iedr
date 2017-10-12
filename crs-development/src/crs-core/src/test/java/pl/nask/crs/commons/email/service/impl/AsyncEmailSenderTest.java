package pl.nask.crs.commons.email.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailInstantiator;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingAttempt;
import pl.nask.crs.ticket.AbstractContextAwareTest;

public class AsyncEmailSenderTest extends AbstractContextAwareTest {
    private EmailSender sender = new MockedEmailSender();

    private EmailQueue queue = new EmailQueue();

    @Autowired
    private EmailInstantiator instantiator;

    @Autowired
    private EmailTemplateDAO dao;

    @Autowired
    private ApplicationConfig applicationConfig;

    private AsyncEmailSender asyncSender;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        asyncSender = new AsyncEmailSender(sender, queue, instantiator, dao);
        while (queue.size() != 0) {
            queue.next();
        }
        addMessages(1000);
    }

    private void addMessages(int count) throws InterruptedException {
        int attemptLimit = applicationConfig.getEmailAttemptLimit();
        for (int i = 0; i < count; i++) {
            queue.queue(new EmailSendingAttempt(new Email(), attemptLimit));
        }
    }

    @Test(timeOut = 30000)
    public void senderShouldBeRunningAfterStart() {
        asyncSender.start(10);
        AssertJUnit.assertTrue(asyncSender.isRunning());
        // check if the queue is emptied after some time
        waitUntillQueueSizeIsZero();
    }

    @Test(timeOut = 30000)
    public void senderShouldBeStoppedAfterStop() {
        asyncSender.start(10);
        AssertJUnit.assertTrue(asyncSender.isRunning());
        asyncSender.stop(1000);
        AssertJUnit.assertFalse(asyncSender.isRunning());
    }

    @Test(timeOut = 30000)
    public void senderShouldBeAbleToStopWithEmailsInTheQueue() throws Exception {
        addMessages(15000);
        asyncSender.start(2);
        AssertJUnit.assertTrue(asyncSender.isRunning());
        asyncSender.stop(1000);
        AssertJUnit.assertFalse(asyncSender.isRunning());
        AssertJUnit.assertTrue(queue.size() > 0);
    }

    private void waitUntillQueueSizeIsZero() {
        while (queue.size() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
