package pl.nask.crs.commons.email.service.impl;

import java.util.Arrays;

import javax.annotation.Resource;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailInstantiator;
import pl.nask.crs.commons.email.service.EmailSendingAttempt;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.ticket.AbstractContextAwareTest;

public class AsyncEmailSenderFailureTest extends AbstractContextAwareTest {

    @Autowired
    private EmailInstantiator instantiator;

    @Autowired
    private EmailTemplateDAO dao;

    @Resource
    private AsyncEmailSender asyncSender;

    @Mocked({"sendEmail"})
    private MockedEmailSender sender;

    @Test
    public void emailSendingFailureTest(@Mocked({"fail", "isLimitOver"}) EmailSendingAttempt anyAttempt)
            throws Exception {
        final int attemptLimit = 3;
        final int retryMillis = 1;

        final Email email = new Email();
        email.setTemplateId(EmailTemplateNamesEnum.EMAIL_SENDING_FAILURE.getId() + 1);

        EmailQueue queue = asyncSender.getQueue();

        final Delegate<Email> emailDelegate = new Delegate<Email>() {
            public boolean verifyEmail(Email email) {
                return (email.getTemplateId() == EmailTemplateNamesEnum.EMAIL_SENDING_FAILURE.getId());
            }
        };

        new Expectations() {
            {
                EmailSendingAttempt attempt = new EmailSendingAttempt(email, attemptLimit);
                sender.sendEmail(withSameInstance(email));
                result = new IllegalArgumentException();
                times = attemptLimit;
                attempt.fail();
                times = attemptLimit;
                attempt.isLimitOver();
                returns(false, false, true);

                sender.sendEmail((Email) with(emailDelegate));
            }
        };

        queueEmailAndWaitForDelivery(email, queue, attemptLimit, retryMillis);
    }

    @Test
    public void invalidEmailSyntaxFailure() throws Exception {
        final Email email = new Email();
        email.setTemplateId(EmailTemplateNamesEnum.TOP_UP.getId());
        email.setToList(Arrays.asList("invalid@email@address"));
        EmailQueue queue = asyncSender.getQueue();

        final Delegate<Email> emailDelegate = new Delegate<Email>() {
            public boolean verifyEmail(Email email) {
                return (email.getTemplateId() == EmailTemplateNamesEnum.EMAIL_SENDING_FAILURE.getId());
            }
        };
        new Expectations() {
            {
                sender.sendEmail((Email) with(emailDelegate));
            }
        };

        queueEmailAndWaitForDelivery(email, queue, 1, 1);
    }

    private void queueEmailAndWaitForDelivery(Email email, EmailQueue queue, int attemptLimit, int retryMillis)
            throws InterruptedException {
        EmailSendingAttempt attempt = new EmailSendingAttempt(email, attemptLimit);
        asyncSender.stop(1);
        while (queue.size() > 0)
            queue.next();
        queue.queue(attempt);
        AssertJUnit.assertEquals(1, queue.size());
        asyncSender.setSleepTimeBeforeRetryMillis(retryMillis);
        asyncSender.start(1);
        Thread.sleep(retryMillis * attemptLimit + 1000);
        asyncSender.stop(60);
        AssertJUnit.assertEquals(0, queue.size());
    }

    public String toString() {
        return "test";
    }
}
