package pl.nask.crs.scheduler;

import javax.annotation.Resource;

import mockit.Delegate;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.log4j.NDCAwareFileAppender;
import pl.nask.crs.security.authentication.AuthenticatedUser;

@ContextConfiguration(locations = { "/scheduler-config.xml", "/scheduler-config-test.xml" })
public class JobLoggerTest extends AbstractTransactionalTestNGSpringContextTests {
	@Resource
	SchedulerCron schedulerCron;

	@Mocked
	EmailTemplateSenderImpl templateSender;

	@Resource
	EmailSender emailSender;

	AuthenticatedUser user = new AuthenticatedUser() {
		@Override
		public String getUsername() {
			return "IDL2-IEDR";
		}

		@Override
		public String getSuperUserName() {
			return null;
		}

		@Override
		public String getAuthenticationToken() {
			return null;
		}
	};

	@Test
	public void testProcessAwareAppender() {
		NDCAwareFileAppender appender = (NDCAwareFileAppender) Logger.getRootLogger().getAppender("processAware");
		AssertJUnit.assertNotNull(appender);
	}

	@Test
	public void emailShouldBeSentWhenErrorIsLogged() throws Exception {
		new NonStrictExpectations() {
			{
				templateSender.sendEmail(EmailTemplateNamesEnum.SCHEDULER_JOB_FINISHED_WITH_ERRORS.getId(),
						withInstanceOf(EmailParameters.class));
				minTimes = 1;
			}
		};

		schedulerCron.start(user);
		schedulerCron.invoke(user, "EmptyJobWithError");
		Thread.sleep(1000);
		schedulerCron.stop(user);
	}

	@Test
	public void emailShouldBeSentWhenJobIsFailed() throws Exception {
		new NonStrictExpectations() {
			{
				templateSender.sendEmail(EmailTemplateNamesEnum.SCHEDULER_JOB_FAILED.getId(),
						withInstanceOf(EmailParameters.class));
				minTimes = 1;
			}
		};

		schedulerCron.start(user);
		schedulerCron.invoke(user, "EmptyFailingJob");
		Thread.sleep(1000);
		schedulerCron.stop(user);
	}

	@Test
	public void emailShouldBeSentEvenIfDbConnectionIsDeadAndEmailTemplateCannotBeRead() throws Exception {
		new NonStrictExpectations() {
			{
				templateSender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
				result = new Exception("BOOM!");
			}
		};

		new NonStrictExpectations(emailSender) {
			{
				emailSender.sendEmail(withInstanceOf(Email.class));
				minTimes = 1;
				result = new Delegate<Email>() {
					public void sendEmail(Email email) {
						Assert.assertTrue(email.getText().contains("BOOM!"));
					}
				};
			}
		};

		schedulerCron.start(user);
		schedulerCron.invoke(user, "EmptyFailingJob");
		Thread.sleep(1000);
		schedulerCron.stop(user);
	}

}
