package pl.nask.crs.api.emaildisabler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.EmailDisablerSearchResultVO;
import pl.nask.crs.api.vo.EmailDisablerSuppressResultVO;
import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class EmailDisablerAppServiceEndpoint extends WsSessionAware implements CRSEmailDisablerAppService {

    private final static Logger LOGGER = Logger.getLogger(EmailDisablerAppServiceEndpoint.class);

    private EmailDisablerAppService service;

    public void setService(EmailDisablerAppService service) {
        this.service = service;
    }

    public EmailDisablerSearchResultVO getAll(AuthenticatedUserVO user) throws AccessDeniedException {
        return new EmailDisablerSearchResultVO(service.getAllFor(user));
    }

    public EmailDisablerSuppressResultVO modifySuppressionMode(
            List<EmailDisablerSuppressInfo> emailDisablerSuppressInfos, AuthenticatedUserVO user)
            throws AccessDeniedException {
        List<EmailDisablerSuppressInfo> persistedEmailDisablerSuppressInfo = service.modifySuppressionMode(user,
                emailDisablerSuppressInfos);
        List<EmailDisablerSuppressInfo> rejectedEmailDisablerSuppressInfo = difference(emailDisablerSuppressInfos,
                persistedEmailDisablerSuppressInfo);
        EmailDisablerSuppressResultVO result = new EmailDisablerSuppressResultVO(persistedEmailDisablerSuppressInfo,
                rejectedEmailDisablerSuppressInfo);
        return result;
    }

    List<EmailDisablerSuppressInfo> difference(List<EmailDisablerSuppressInfo> minuend,
            List<EmailDisablerSuppressInfo> subtrahend) {

        Set<EmailDisablerSuppressInfo> subtrahendAsSet = new HashSet<EmailDisablerSuppressInfo>(subtrahend);

        List<EmailDisablerSuppressInfo> difference = new ArrayList<EmailDisablerSuppressInfo>();
        for (EmailDisablerSuppressInfo suppressInfo : minuend) {
            if (!subtrahendAsSet.contains(suppressInfo)) {
                difference.add(suppressInfo);
            }
        }

        return difference;
    }

}
