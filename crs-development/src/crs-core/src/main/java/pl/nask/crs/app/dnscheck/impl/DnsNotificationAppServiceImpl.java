package pl.nask.crs.app.dnscheck.impl;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.dnscheck.DnsNotificationAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class DnsNotificationAppServiceImpl implements DnsNotificationAppService {

    private DnsNotificationService dnsNotificationService;

    public DnsNotificationAppServiceImpl(DnsNotificationService dnsNotificationService) {
        Validator.assertNotNull(dnsNotificationService, "dnsNotificationService");
        this.dnsNotificationService = dnsNotificationService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendNotifications(AuthenticatedUser user) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        dnsNotificationService.sendNotifications();
    }

}
