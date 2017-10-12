package pl.nask.crs.app;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.framework.Advised;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.authorization.aspects.AuthorizationAspect;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.app.dnscheck.DnsNotificationAppService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.WsAuthenticationService;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class PermissionCheckTest extends AbstractTestNGSpringContextTests {

    private Set<Method> exclusions = initExclusions();

    private Set<Method> initExclusions() {
        try {
            Set<Method> e = new HashSet<>();

            // no parameters required
            e.add(UserAppService.class.getMethod("getInternalUsers"));

            // simple dictionary method: no permission check required here
            e.add(DomainAppService.class.getMethod("getCountries", AuthenticatedUser.class));

            // AuthentiatedUser not passed
            e.add(AccountAppService.class.getMethod("createDirectAccount", NewNicHandle.class, String.class,
                    boolean.class));
            e.add(CommonAppService.class.getMethod("sendAuthCodeFromPortal", String.class, String.class));
            e.add(CommonAppService.class.getMethod("validateNameservers", List.class, List.class, String.class,
                    boolean.class));
            e.add(CommonAppService.class.getMethod("getApplicationConfiguration"));
            e.add(WsAuthenticationService.class.getMethod("authenticate", String.class, String.class, boolean.class,
                    String.class, boolean.class, String.class, boolean.class, String.class));
            e.add(WsAuthenticationService.class.getMethod("authenticateAndSwitchUser", String.class, String.class,
                    String.class, boolean.class, String.class, boolean.class));
            e.add(WsAuthenticationService.class.getMethod("getAuthenticatedUser", String.class, String.class));

            e.add(NicHandleAppService.class.getMethod("requestPasswordReset", String.class, String.class));
            e.add(NicHandleAppService.class.getMethod("resetPasswordFromToken", String.class, String.class,
                    String.class, String.class));
            return e;
        } catch (SecurityException e1) {
            e1.printStackTrace();
            throw new IllegalStateException("SecurityException: " + e1.getMessage(), e1);
        } catch (NoSuchMethodException e1) {
            throw new IllegalStateException("No such method: " + e1.getMessage(), e1);
        }
    }

    @DataProvider
    public static Object[][] classesToCheck() {
        return new Object[][] { {AccountAppService.class}, {BulkTransferAppService.class}, {CommonAppService.class},
                {ConfigAppService.class}, {DnsNotificationAppService.class}, {DocumentAppService.class},
                {DomainAppService.class}, {EmailDisablerAppService.class}, {EmailGroupAppService.class},
                {EmailTemplateAppService.class}, {InvoicingAppService.class}, {NicHandleAppService.class},
                {PaymentAppService.class}, {ReportsAppService.class}, {SecondaryMarketAppService.class},
                {TicketAppService.class}, {TriplePassAppService.class}, {UserAppService.class},
                {WsAuthenticationService.class}
        // SchedulerCron.class - cannot be tested here due to being in wrong package
        };
    }

    @Test(dataProvider = "classesToCheck")
    public void allMethodsHavePermissionsChecks(final Class<?> classToCheck) {
        SoftAssert softAssert = new SoftAssert();
        final Set<Method> methods = new HashSet<>(Arrays.asList(classToCheck.getMethods()));
        methods.removeAll(exclusions);

        if (methods.isEmpty()) {
            return;
        }

        final Object bean = applicationContext.getBean(classToCheck);
        if (!(bean instanceof Advised)) {
            Assert.fail(classToCheck.getCanonicalName() + " is not covered by aspects");
        }
        final Advised a = (Advised) bean;

        for (final Method method : methods) {
            softAssert.assertTrue(Iterables.any(Arrays.asList(a.getAdvisors()), new Predicate<Advisor>() {
                @Override
                public boolean apply(Advisor advisor) {
                    if (advisor instanceof AspectJPointcutAdvisor) {
                        AspectJPointcutAdvisor ajadv = (AspectJPointcutAdvisor) advisor;
                        if (isPermissionCheck(ajadv.getAdvice())) {
                            Pointcut pcut = ajadv.getPointcut();
                            return pcut.getMethodMatcher().matches(method, classToCheck);
                        }
                    }
                    return false;
                }
            }), "Method " + classToCheck.getSimpleName() + "#" + method.getName() + " is not protected by aspects");
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "classesToCheck")
    public void methodsMustDeclareThrowsAccessDeniedException(Class<?> classToCheck) {
        SoftAssert softAssert = new SoftAssert();
        for (Method method : classToCheck.getMethods()) {
            if (!exclusions.contains(method)) {
                softAssert.assertTrue(
                        Iterables.any(Arrays.asList(method.getExceptionTypes()), new Predicate<Class<?>>() {
                            @Override
                            public boolean apply(Class<?> aClass) {
                                return aClass.isAssignableFrom(AccessDeniedException.class);
                            }
                        }), "Method " + classToCheck.getSimpleName() + "#" + method.getName()
                                + " does not declare AccessDeniedException thrown");
            }
        }
        softAssert.assertAll();
    }

    private boolean isPermissionCheck(Advice advice) {
        if (!(advice instanceof AbstractAspectJAdvice))
            return false;
        AbstractAspectJAdvice adv = (AbstractAspectJAdvice) advice;

        return AuthorizationAspect.class.isAssignableFrom(adv.getAspectJAdviceMethod().getDeclaringClass());
    }

}
