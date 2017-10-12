package pl.nask.crs.commons.email.service.impl;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.service.EmailDisablerCheckService;
import pl.nask.crs.commons.email.service.EmailParameters;

public class EmailDisablerCheckServiceImpl implements EmailDisablerCheckService {
    private EmailDisablerDAO disablerDAO;

    public EmailDisablerCheckServiceImpl(EmailDisablerDAO disablerDAO) {
        this.disablerDAO = disablerDAO;
    }

    @Override
    public boolean shouldSendToExternal(EmailTemplate template, EmailParameters templateParameters) {
        String domain = templateParameters.getDomainName();
        String owner = templateParameters.getAccountRelatedNicHandle(template.isSuppressedByGaining());
        String user = templateParameters.getLoggedInNicHandle();
        // Send if...
        EmailDisabler disabler = disablerDAO.get(new EmailDisablerKey(template.getId(), owner));
        // The email is not suppressible...
        if (template == null || !template.isSuppressible()) {
            return true;
        }
        // Or account owner did not disable email...
        if (disabler == null || !disabler.isDisabled()) {
            return true;
        }
        // Or the current user is an admin or tech contact for the domain.
        if (domain != null && disablerDAO.isNhAdminOrTechForDomain(user, domain)) {
            return true;
        }
        // Otherwise disable.
        return false;
    }
}
