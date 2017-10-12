package pl.nask.crs.web.domains;

import java.util.*;

import org.apache.commons.lang.StringEscapeUtils;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.wrappers.DomainWrapper;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.NewDomainStatus;
import pl.nask.crs.domains.SecondaryMarketStatus;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.HistoricalDomainService;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class DomainViewAction extends HistoricalDomainAction {

    private DomainAppService domainAppService;
    private DnsCheckService dnsCheckService;
    private DnsNotificationService dnsNotificationService;
    private DomainWrapper domainWrapper;
    private String domainName;

    String previousAction = "domains-search";
    private List<NewDomainStatus> domainStatuses;

    public DomainViewAction(DomainAppService domainAppService, HistoricalDomainService historicalDomainService,
            AccountSearchService accountSearchService, DnsCheckService dnsCheckService,
            DnsNotificationService dnsNotificationService) {
        super(domainAppService, historicalDomainService, accountSearchService);
        this.domainAppService = domainAppService;
        this.dnsCheckService = dnsCheckService;
        this.dnsNotificationService = dnsNotificationService;
    }

    public List<NewDomainStatus> getDomainStatuses() throws AccessDeniedException {
        if (domainStatuses == null) {
            domainStatuses = new ArrayList<NewDomainStatus>();
            if (hasPermission("domain.alterstatus.deleted")
                    && domainAppService.isEventValid(getUser(), domainName, DsmEventName.EnterVoluntaryNRP)) {
                domainStatuses.add(NewDomainStatus.Deleted);
            } else if (hasPermission("domain.alterstatus.reactivate")
                    && domainAppService.isEventValid(getUser(), domainName, DsmEventName.RemoveFromVoluntaryNRP)) {
                domainStatuses.add(NewDomainStatus.Reactivate);
            }
            if (hasPermission("domain.alterstatus.active")) {
                // check if triplePass is needed for a domain
                NRPStatus currentStatus = domainWrapper.getDomain().getDsmState().getNrpStatus();
                switch (currentStatus) {
                    case InvoluntaryMailedPaymentPending:
                    case InvoluntarySuspendedPaymentPending:
                        domainStatuses.add(NewDomainStatus.Active);
                        break;
                    default:
                        break;
                }
            }
        }
        return domainStatuses;
    }

    public DomainWrapper getDomainWrapper() {
        return domainWrapper;
    }

    public void setDomainWrapper(DomainWrapper domainWrapper) {
        this.domainWrapper = domainWrapper;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    /**
     * needed in domain-view.jsp to know where to go back from contact view
     *
     * @return false (this is not history domain)
     */
    public boolean isHistory() {
        return false;
    }

    public String execute() throws Exception {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        setHistoricalDomainName(domainName);
        search();
        return SUCCESS;
    }

    public String makeContactInfo(Contact contact) {
        return ContactHelper.makeContactInfo(contact);
    }

    public List<DomainHolderType> getHolderTypes() {
        List<DomainHolderType> types = new ArrayList<DomainHolderType>();
        for (DomainHolderType t : DomainHolderType.values()) {
            if (t != DomainHolderType.NA && t != domainWrapper.getDomain().getDsmState().getDomainHolderType()) {
                types.add(t);
            }
        }
        return types;
    }

    public String dnsCheck() throws AccessDeniedException, DomainNotFoundException {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        Set<String> failedNameservers = new HashSet<String>();
        final List<Nameserver> nameservers = domainWrapper.getDnsWrapper().getNameservers();
        for (Nameserver ns : nameservers) {
            failedNameservers.add(ns.getName());
        }
        try {
            dnsCheckService.check(Arrays.asList(domainName), nameservers, getUser().getUsername(), true);
            failedNameservers = new HashSet<String>();
            addActionMessage("DNS Check compete: Passed");
            return SUCCESS;
        } catch (DnsCheckProcessingException e) {
            addActionError("Error performing DNS check: " + StringEscapeUtils.escapeHtml(e.getMessage()));
            log.error("Error performing DNS check", e);
            return ERROR;
        } catch (HostNotConfiguredException e) {
            createNotification(e);
            addActionError("DNS Check complete: failed <pre>"
                    + StringEscapeUtils.escapeHtml(e.getFullOutputMessage(false)) + "</pre>");
            return SUCCESS;
        } finally {
            dnsNotificationService.removeObsoleteNotifications(domainName, null, failedNameservers);
        }
    }

    private void createNotification(HostNotConfiguredException e) {
        String domainName = domainWrapper.getName();
        for (String nsName : e.getNameserverNames()) {
            DnsNotification notification = new DnsNotification(domainName, null, nsName, new Date(),
                    e.getSingleOutputMessage(domainName, nsName, true));
            dnsNotificationService.createNotification(notification);
        }
    }

    public String sendAuthCode() throws AccessDeniedException, DomainNotFoundException {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        try {
            domainAppService.sendAuthCodeByEmail(getUser(), domainName);
            addActionMessage("Email sent successfully");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Sending email failure: " + e.getMessage());
            return ERROR;
        }
    }

    public String sendWhois() throws AccessDeniedException, DomainNotFoundException {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        try {
            domainAppService.sendWhoisDataEmail(getUser(), domainName);
            addActionMessage("Email sent successfully");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Sending email failure: " + e.getMessage());
            return ERROR;
        }
    }

    public boolean isLocked() {
        Boolean locked = domainWrapper.getDomain().getDsmState().getLocked();
        if (locked == null) {
            return false;
        } else {
            return locked;
        }
    }

    public boolean isInLockingService() {
        Date lockDate = domainWrapper.getDomain().getLockingDate();
        return lockDate != null;
    }

    public boolean isEditable() {
        return (!SecondaryMarketStatus.SellRequestRegistered.equals(domainWrapper.getDomain().getDsmState()
                .getSecondaryMarketStatus()) && !isLocked());
    }

}
