package pl.nask.crs.web.domains;

import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.app.domains.wrappers.DomainWrapper;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class DomainEditAction extends AuthenticatedUserAwareAction {

    private final static Logger log = Logger.getLogger(DomainEditAction.class);
    private DomainAppService domainAppService;
    private DnsCheckService dnsCheckService;
    private DnsNotificationService dnsNotificationService;
    private DomainWrapper domainWrapper;
    private String domainName;
    private EntityService entityService;
    private String previousAction = "domains-search";

    private static final String EXECUTE = "execute";
    private static final String SAVE = "save";

    private List<Contact> oldAdminContacts;
    private List<Contact> oldTechContacts;

    public DomainEditAction(DomainAppService domainAppService, DnsCheckService dnsCheckService,
            DnsNotificationService dnsNotificationService, EntityService entityService) {
        Validator.assertNotNull(domainAppService, "domain application service");
        Validator.assertNotNull(dnsCheckService, "dns check service");
        Validator.assertNotNull(dnsNotificationService, "dns notification service");
        this.domainAppService = domainAppService;
        this.dnsCheckService = dnsCheckService;
        this.dnsNotificationService = dnsNotificationService;
        this.entityService = entityService;

    }

    public DomainAppService getDomainAppService() {
        return domainAppService;
    }

    public void setDomainAppService(DomainAppService domainAppService) {
        Validator.assertNotNull(domainAppService, "domain application service");
        this.domainAppService = domainAppService;
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

    public List<EntityClass> getDomainClasses() {
        return entityService.getClasses();
    }

    public List<EntityCategory> getDomainCategories() {
        return entityService.getCategories();
    }

    public List<Contact> getOldAdminContacts() {
        return oldAdminContacts;
    }

    public void setOldAdminContacts(List<Contact> oldAdminContacts) {
        this.oldAdminContacts = oldAdminContacts;
    }

    public List<Contact> getOldTechContacts() {
        return oldTechContacts;
    }

    public void setOldTechContacts(List<Contact> oldTechContacts) {
        this.oldTechContacts = oldTechContacts;
    }

    public String input() throws Exception {
        ExtendedDomainInfo domainInfo = domainAppService.edit(getUser(), domainName);
        oldAdminContacts = new ArrayList<Contact>(domainInfo.getDomain().getAdminContacts());
        oldTechContacts = new ArrayList<Contact>(domainInfo.getDomain().getTechContacts());
        domainWrapper = new DomainWrapper(domainInfo, entityService);
        return EXECUTE;
    }

    public String save() throws Exception {
        try {
            Domain domain = domainWrapper.getDomain();
            getDomainAppService().save(getUser(), domain);
            if (isAdminOrTechChanged()) {
                dnsCheck();
            }
            return SAVE;
        } catch (NicHandleNotActiveException ex) {
            addActionError("Nic handle " + ex.getNicHandleId() + " has not Active status.");
            return ERROR;
        }
    }

    private boolean isAdminOrTechChanged() {
        String oldTechContact = oldTechContacts.get(0).getNicHandle();
        String newTechContact = domainWrapper.getTechContact();
        if (!oldTechContact.equalsIgnoreCase(newTechContact)) {
            return true;
        }

        String oldAdminContact1 = oldAdminContacts.get(0).getNicHandle();
        String newAdminContact1 = domainWrapper.getAdminContact1();
        if (!oldAdminContact1.equalsIgnoreCase(newAdminContact1)) {
            return true;
        }

        String oldAdminContact2 = oldAdminContacts.size() > 1 ? oldAdminContacts.get(1).getNicHandle() : null;
        String newAdminContact2 = domainWrapper.getAdminContact2();
        if (Validator.isEmpty(oldAdminContact2) && !Validator.isEmpty(newAdminContact2)) {
            return true;
        }
        if (oldAdminContact2 != null && !oldAdminContact2.equalsIgnoreCase(newAdminContact2)) {
            return true;
        }
        return false;
    }

    public void dnsCheck() throws AccessDeniedException, DomainNotFoundException {
        domainWrapper = new DomainWrapper(domainAppService.view(getUser(), domainName));
        final List<Nameserver> nameservers = domainWrapper.getDnsWrapper().getNameservers();
        Set<String> failedNameservers = new HashSet<String>();
        for (Nameserver ns : nameservers) {
            failedNameservers.add(ns.getName());
        }
        try {
            dnsCheckService.check(Arrays.asList(domainName), nameservers, getUser().getUsername(), true);
            failedNameservers = new HashSet<String>();
        } catch (DnsCheckProcessingException e) {
            log.error("Error performing DNS check", e);
        } catch (HostNotConfiguredException e) {
            createNotification(e);
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

    public String makeContactInfo(Contact contact) {
        return ContactHelper.makeContactInfo(contact);
    }

}
