package pl.nask.crs.regression;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class RenewalNotificationsTest extends AbstractEmailsTest {
    public static final String DIRECT_DOMAIN_IE = "directdomain.ie";

    @Autowired
    DomainAppService domainAppService;

    @Autowired
    ApplicationConfig config;

    @Autowired
    DomainDAO domainDao;

    @Autowired
    HistoricalDomainDAO historicalDomainDAO;

    @Autowired
    CRSAuthenticationService crsAuthenticationService;

    private AuthenticatedUser user;

    private Map<ParameterNameEnum, String> populatedValues;

    @BeforeClass
    public void setUser() throws Exception {
        user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void paramsShouldBePopulatedInTemplate26() throws Exception {
        sendOtherNotifications();

        // now, set the renewal date to today and test the expectations
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        int period = config.getRenewalNotificationPeriods().get(0);
        d.setRenewalDate(DateUtils.getCurrDate(period));
        // make sure, that the admin contact differs from the billing contact
        d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        preparePopulatedValues(DateUtils.getCurrDate(period));
        createExpectations(26, populatedValues);
        domainAppService.runNotificationProcess(user);
    }

    @Test
    public void renewalNotificationShouldBeSentForOneMatchingPeriodOnly() throws Exception {
        sendOtherNotifications();

        // now, set the renewal date to today and test the expectations
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        int period = 5;
        d.setRenewalDate(DateUtils.getCurrDate(period));
        // make sure, that the admin contact differs from the billing contact
        d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        preparePopulatedValues(DateUtils.getCurrDate(period));
        createExpectations(26, populatedValues, 1, 1);
        domainAppService.runNotificationProcess(user);
    }

    @Test
    public void renewalNotificationShouldNotBeSentTwiceIfBillAndAdminAreTheSame() throws Exception {
        sendOtherNotifications();

        // now, set the renewal date to today and test the expectations
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        int period = 5;
        d.setRenewalDate(DateUtils.getCurrDate(period));
        // make sure, that the admin contact is the same as the billing contact
        d.setAdminContacts(d.getBillingContacts());
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        preparePopulatedValues(DateUtils.getCurrDate(period));
        createExpectations(26, populatedValues, 1, 1);
        domainAppService.runNotificationProcess(user);
    }

    @Test
    public void renewalNotificationShouldNotBeSentDirectlyAfterTransfer() throws Exception {
        sendOtherNotifications();

        // now, set the renewal date to today and test the expectations
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        int period = 5;
        d.setRenewalDate(DateUtils.getCurrDate(period));
        d.setTransferDate(DateUtils.getCurrDate(0));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        createExpectations(26, new HashMap<ParameterNameEnum, String>(), 0, 0);
        domainAppService.runNotificationProcess(user);
    }

    @Test
    public void paramsShouldBePopulatedInTemplate74() throws Exception {
        sendOtherNotifications();
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        // make sure, that the admin contact differs from the billing contact
        d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));

        preparePopulatedValues(DateUtils.getCurrDate(0));
        createExpectations(74, populatedValues);
        // make sure that the notification about domain expiry will be sent
        d = domainDao.get(DIRECT_DOMAIN_IE);
        d.setRenewalDate(DateUtils.getCurrDate(0));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        domainAppService.runNotificationProcess(user);
    }

    // refs https://drotest4.nask.net.pl:3000/issues/14264
    // Bug in RenewalNotification job - sending to domains with a past renewal date
    @Test
    public void renewalNotificationShouldNotBeSentIfDomainRenewalDateIsInThePast() throws Exception {
        sendOtherNotifications();
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        // make sure, that the admin contact differs from the billing contact
        d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));

        // make sure all notifications were sent (just to test a specific domain)
        // the process may have to be run multiple times
        domainAppService.runNotificationProcess(user);

        preparePopulatedValues(DateUtils.getCurrDate(-20));
        createExpectations(26, populatedValues, 0, 0);
        // make sure that the notification about domain expiry will be sent
        d = domainDao.get(DIRECT_DOMAIN_IE);
        d.setRenewalDate(DateUtils.getCurrDate(-20)); // set the renewal date to the past
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        domainAppService.runNotificationProcess(user);
    }

    @Test
    public void renewalNotificationShouldNotBeSentDirectlyAfterRegistration() throws Exception {
        sendOtherNotifications();
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        Date date = DateUtils.getCurrDate(400);
        d.setRenewalDate(date);
        d.setRegistrationDate(date);
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        createExpectations(74, new HashMap<ParameterNameEnum, String>(), 0, 0);
        domainAppService.runNotificationProcess(user);
    }

    @Test
    public void renewalNotificationShouldNotBeSentIfDomainInNRP() throws Exception {
        sendOtherNotifications();
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        Date date = DateUtils.getCurrDate(0);
        d.setRenewalDate(date);
        d.setRegistrationDate(date);
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 26, new Date(), TestOpInfo.DEFAULT.getActorName()));
        createExpectations(74, new HashMap<ParameterNameEnum, String>(), 0, 0);
        domainAppService.runNotificationProcess(user);
    }

    private void sendOtherNotifications() throws AccessDeniedException {
        // make sure all notifications were sent (just to test a specific domain)
        // prevent from sending notifications for directDomain.ie (set the renewal date to the far future)
        Domain d = domainDao.get(DIRECT_DOMAIN_IE);
        d.setRenewalDate(DateUtils.getCurrDate(400));
        domainDao.updateUsingHistory(historicalDomainDAO.create(d, 25, new Date(), TestOpInfo.DEFAULT.getActorName()));
        domainAppService.runNotificationProcess(user);
    }

    private void preparePopulatedValues(Date expectedRenDt) {
        populatedValues = new HashMap<ParameterNameEnum, String>();
        populatedValues.put(ParameterNameEnum.BILL_C_EMAIL, "NHEmail000019@server.xxx");
        populatedValues.put(ParameterNameEnum.BILL_C_NIC, "AAU809-IEDR");
        populatedValues.put(ParameterNameEnum.BILL_C_NAME, "Paul Janssens");
        populatedValues.put(ParameterNameEnum.DOMAIN, DIRECT_DOMAIN_IE);
        populatedValues.put(ParameterNameEnum.RENEWAL_DATE, new SimpleDateFormat("dd-MM-yyyy").format(expectedRenDt));
        populatedValues.put(ParameterNameEnum.DAYS_TO_RENEWAL, "" + DateUtils.diffInDays(expectedRenDt, new Date()));
    }
}
