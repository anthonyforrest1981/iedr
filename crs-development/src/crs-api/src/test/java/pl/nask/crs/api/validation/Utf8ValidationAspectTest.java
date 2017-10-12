package pl.nask.crs.api.validation;

import java.lang.reflect.Method;
import java.util.*;

import javax.jws.WebService;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import org.springframework.aop.framework.Advised;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import pl.nask.crs.api.account.CRSResellerAppService;
import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.common.CRSCommonAppService;
import pl.nask.crs.api.document.CRSDocumentAppService;
import pl.nask.crs.api.domain.CRSDomainAppService;
import pl.nask.crs.api.emaildisabler.CRSEmailDisablerAppService;
import pl.nask.crs.api.nichandle.CRSNicHandleAppService;
import pl.nask.crs.api.payment.CRSPaymentAppService;
import pl.nask.crs.api.ticket.CRSTicketAppService;
import pl.nask.crs.api.users.CRSPermissionsAppService;
import pl.nask.crs.api.zone.CRSZoneAppService;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;

@ContextConfiguration(locations = {"/crs-api-config.xml"})
public class Utf8ValidationAspectTest extends AbstractTransactionalTestNGSpringContextTests {

    @DataProvider
    public static Object[][] classesToCheck() {
        return new Object[][] { {CRSResellerAppService.class}, {CRSAuthenticationService.class},
                {CRSCommonAppService.class}, {CRSDocumentAppService.class}, {CRSDomainAppService.class},
                {CRSEmailDisablerAppService.class}, {CRSNicHandleAppService.class}, {CRSPaymentAppService.class},
                {CRSTicketAppService.class}, {CRSPermissionsAppService.class}, {CRSZoneAppService.class},};
    }

    @Test(dataProvider = "classesToCheck")
    public void allMethodsAreRunThroughUtf8ValidatorAspect(final Class<?> classToCheck) {
        SoftAssert softAssert = new SoftAssert();

        final Map<String, ?> beans = applicationContext.getBeansOfType(classToCheck);
        final Set<Method> methods = new HashSet<>(Arrays.asList(classToCheck.getMethods()));
        // for each webservice we should have two beans - a proxy and the real thing
        if (beans.size() != 2) {
            Assert.fail(classToCheck.getCanonicalName() + " should have two beans: proxy and impl, but has "
                    + beans.size());
        }
        int typeOfBeansFound = 0;
        for (String key : beans.keySet()) {
            final Object bean = beans.get(key);
            // one bean should be *Proxy, a @WebService and not under aspects
            if (key.endsWith("Proxy")) {
                typeOfBeansFound += 1;
                Assert.assertNotNull(bean.getClass().getAnnotation(WebService.class), bean.getClass()
                        .getCanonicalName() + " is not a @WebService");
                Assert.assertFalse(bean instanceof Advised, "*Proxy is under aspects");
            } else {
                typeOfBeansFound += 3;
                if (!(bean instanceof Advised)) {
                    Assert.fail(bean.getClass().getCanonicalName() + " is not covered by aspects");
                }
                final Advised a = (Advised) bean;

                final List<Advisor> advisors = new ArrayList<>(Arrays.asList(a.getAdvisors()));
                for (final Method method : methods) {
                    softAssert.assertTrue(Iterables.any(advisors, new Predicate<Advisor>() {
                        @Override
                        public boolean apply(Advisor advisor) {
                            if (advisor instanceof InstantiationModelAwarePointcutAdvisor) {
                                InstantiationModelAwarePointcutAdvisor ajadv = (InstantiationModelAwarePointcutAdvisor) advisor;
                                if (isUtf8Aspect(ajadv.getAdvice())) {
                                    Pointcut pcut = ajadv.getPointcut();
                                    return pcut.getMethodMatcher().matches(method, bean.getClass());
                                }
                            }
                            return false;
                        }
                    }), "Method " + bean.getClass().getSimpleName() + "#" + method.getName()
                            + " is not protected by aspects");
                }
            }
        }
        if (typeOfBeansFound != 4) {
            Assert.fail(classToCheck.getCanonicalName()
                    + " does not have beans of both types: one *Proxy and one under aspects");
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "classesToCheck")
    public void allMethodsDeclareIncorrectUtf8(Class<?> classToCheck) {
        SoftAssert softAssert = new SoftAssert();
        for (Method method : classToCheck.getMethods()) {
            final List<Class<?>> exceptionTypes = Arrays.asList(method.getExceptionTypes());
            softAssert.assertTrue(Iterables.any(exceptionTypes, new Predicate<Class<?>>() {
                @Override
                public boolean apply(Class<?> aClass) {
                    return aClass.isAssignableFrom(IncorrectUtf8FormatException.class);
                }
            }), "Method " + classToCheck.getSimpleName() + "#" + method.getName()
                    + " does not declare IncorrectUtf8FormatException thrown");
        }
        softAssert.assertAll();
    }

    private boolean isUtf8Aspect(Advice advice) {
        if (!(advice instanceof AbstractAspectJAdvice))
            return false;
        AbstractAspectJAdvice adv = (AbstractAspectJAdvice) advice;

        return Utf8ValidatorAspect.class.isAssignableFrom(adv.getAspectJAdviceMethod().getDeclaringClass());
    }
}
