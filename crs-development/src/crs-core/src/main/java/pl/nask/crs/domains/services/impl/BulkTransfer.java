package pl.nask.crs.domains.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.BulkTransferDAO;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.events.BulkTransferEvent;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.BulkTransferException;
import pl.nask.crs.domains.services.BulkTransferValidationException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.domains.transfer.BulkTransferDomain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class BulkTransfer {
    // id of the bulk transfer object in the database
    private final long bulkTransferId;
    private BulkTransferRequest transferRequest;

    private List<Domain> validatedDomains = new ArrayList<>();
    private List<String> validationErrors = new ArrayList<>();

    private DomainService domainService;
    private DomainSearchService domainSearchService;
    private DomainStateMachine domainStateMachine;

    private BulkTransferDAO dao;

    private NicHandleSearchService nicHandleSearchService;
    private NicHandleService nicHandleService;
    private ResellerDefaultsService defaultsService;

    private AccountSearchService accountService;
    private Account losingAccount;
    private Account gainingAccount;
    private Contact gainingTechContact;
    private EmailTemplateSender emailSender;

    private AuthenticatedUser user;

    public BulkTransfer(long bulkTransferId, AuthenticatedUser user, BulkTransferDAO dao,
            AccountSearchService accountService, NicHandleSearchService nicHandleSearchService,
            NicHandleService nicHandleService, ResellerDefaultsService defaultsService,
            DomainSearchService domainSearchService, DomainService domainService,
            DomainStateMachine domainStateMachine, EmailTemplateSender emailSender) {
        Validator.assertNotNull(user, "BulkTransfer authenticated user");
        this.bulkTransferId = bulkTransferId;
        this.user = user;
        this.dao = dao;
        this.accountService = accountService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.defaultsService = defaultsService;
        this.domainSearchService = domainSearchService;
        this.domainService = domainService;
        this.domainStateMachine = domainStateMachine;
        this.emailSender = emailSender;
    }

    /**
     * performs bulk transfer on all domains from the request, which were not transferred yet.
     * @param skipDomainsWithValidationErrors - if true all the domains that passes the validation will be transferred; if false the transfer will be performed only if all domains passes the vaildation.
     *
     * @throws BulkTransferValidationException if the transfer request is already closed.
     * @throws BulkTransferException
     * @throws AccountNotFoundException
     */
    public void transferDomains(boolean skipDomainsWithValidationErrors)
            throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException,
            AccountNotFoundException {
        updateTransferRequest();
        validateTransferRequest();
        validateDomains();
        if (!skipDomainsWithValidationErrors) {
            if (!validationErrors.isEmpty()) {
                throw new BulkTransferValidationException(validationErrors);
            }
        }
        performTransfer();
    }

    private void validateDomains() {
        for (BulkTransferDomain td : transferRequest.getRequestedDomains()) {
            if (td.getTransferDate() == null) {
                Domain d;
                try {
                    d = domainSearchService.getDomain(td.getName());
                    List<String> msg = validate(d);
                    if (msg.isEmpty()) {
                        validatedDomains.add(d);
                    } else {
                        validationErrors.addAll(msg);
                    }
                } catch (DomainNotFoundException e) {
                    validationErrors.add(td.getName() + " not found");
                }
            }
        }
    }

    private void validateTransferRequest() throws BulkTransferValidationException {
        if (transferRequest.isClosed()) {
            throw new BulkTransferValidationException(Arrays.asList("Transfer request already closed"));
        }
    }

    private void updateTransferRequest() throws DefaultsNotFoundException, AccountNotFoundException {
        dao.lock(bulkTransferId);
        transferRequest = dao.get(bulkTransferId);
        losingAccount = accountService.getAccount(transferRequest.getLosingAccount());
        gainingAccount = accountService.getAccount(transferRequest.getGainingAccount());
        ResellerDefaults defaults = defaultsService.get(gainingAccount.getBillingContact().getNicHandle());
        gainingTechContact = new Contact(defaults.getTechContactId());
    }

    private List<String> validate(Domain d) {
        List<String> msg = new ArrayList<String>();
        // The system confirms that each selected domain currently has the losing customer as the bill-c [just a double-check].
        addError(validateBillingContact(d), msg);
        // 1. The system confirms that each selected domain's current admin-c is associated with the same Account Number - that of the losing customer.
        // 2. The system confirms that each admin-c associated with each domain selected for transfer is not associated with any domain not selected for transfer.
        // 3. The system confirms that each domain's current admin-c is not associated with any domain which is not also associated with the losing customer's account.
        addError(validateAdminContacts(d), msg);
        // 4. The system confirms that, for each domain, there is no unsettled transaction in the system, for any transaction type.
        addError(validateTransaction(d), msg);
        // 5. The system confirms that no domain has an NRP status of XPA, XPI, XPV.
        addError(validateNRPStatus(d), msg);
        // 6. The system confirms that no domain is locked.
        addError(validateLocked(d), msg);
        return msg;
    }

    private void addError(String msg, List<String> errors) {
        if (msg != null)
            errors.add(msg);
    }

    private String validateLocked(Domain d) {
        if (d.getDsmState().getLocked() != null && d.getDsmState().getLocked().booleanValue())
            return d.getName() + " is locked";
        else
            return null;
    }

    private String validateNRPStatus(Domain d) {
        NRPStatus st = d.getDsmState().getNrpStatus();

        switch (st) {
            case TransferPendingActive:
            case TransferPendingInvNRP:
            case TransferPendingVolNRP:
                return d.getName() + " has an illegal NRP Status: " + st;
            default:
                return null;
        }
    }

    private String validateTransaction(Domain d) {
        if (dao.hasUnsettledReservations(d.getName()))
            return d.getName() + " has unsettled reservations";
        else
            return null;

    }

    private String validateAdminContacts(Domain d) {
        for (Contact c : d.getAdminContacts()) {
            if (c != null && c.getNicHandle() != null) {
                NicHandle nh;
                // 1. The system confirms that each selected domain's current admin-c is associated with the same Account Number - that of the losing customer.
                try {
                    nh = nicHandleSearchService.getNicHandle(c.getNicHandle());
                    if (nh.getAccount().getId() != transferRequest.getLosingAccount())
                        return d.getName() + " : admin contact " + c.getNicHandle()
                                + " associated with a different account";
                } catch (NicHandleNotFoundException e) {
                    return d.getName() + " : admin contact does not exist : " + c.getNicHandle();
                }
                // 2. The system confirms that each admin-c associated with each domain selected for transfer is not associated with any domain not selected for transfer.
                List<String> dnames = dao.findDomainNamesWithSameContactNotInTransfer(bulkTransferId, c.getNicHandle());
                if (!dnames.isEmpty()) {
                    return d.getName() + " : admin contact (" + c.getNicHandle()
                            + ") is associated with domains, which are not in transfer : " + dnames;
                }
                // 3. The system confirms that each domain's current admin-c is not associated with any domain which is not also associated with the losing customer's account.
                // actually this is a data integrity check... should receive one
                int i = dao.countAssociatedDomainsAccounts(c.getNicHandle());
                if (i > 1) {
                    return d.getName() + " : admin contact (" + c.getNicHandle()
                            + ") is associated with domains, which are associated with a different account";
                }
            }
        }
        return null;
    }

    private String validateBillingContact(Domain d) {
        if (d.getBillingContacts().size() == 0)
            return d.getName() + " does not have a billing contact!";
        String domainBillC = d.getBillingContacts().get(0).getNicHandle();
        String losingBillC = losingAccount.getBillingContact().getNicHandle();
        if (!domainBillC.equals(losingBillC)) {
            return d.getName() + " has a different billing contact (" + domainBillC + ") than a losing account ("
                    + losingBillC + ")";
        } else
            return null;
    }

    private void performTransfer() throws BulkTransferException {
        /*
         * 7. FOR EACH DOMAIN:
               8. The system sets the domain's transfer date to be the current date
               9. The system sets the domain's account to be that of the gaining customer.
               10. The system sets the domain's bill-c to be that of the gaining customer.
               11. The system sets the domain's tech-c to be the default tech-C of the gaining customer.
               12. The system sets the admin-c's account to be that of the gaining customer.
               13. The system clears the domain's authcode (if exists)
               14. The system runs the DSM event that sets the domain to "no-autorenew" mode
               15. The system sends a notification email about completing the transfer (E_ID = 184)
           16. END FOR EACH DOMAIN
           17. The system records the successful bulk transfer details - including the ID (Nic) of the actor, date and time, and set of domains successfully transferred, along with the comment supplied by the actor.
           18. The system indicates that all domains have been successfully transferred.
         */
        Date transferDate = new Date();
        for (Domain domain : validatedDomains) {
            domain.setTransferDate(transferDate);
            createTransferRecord(domain.getName(), transferDate);
            transferDomain(domain);
            markAsTransferred(domain.getName(), transferDate);
            sendNotificationEmail(domain);
        }
    }

    private void createTransferRecord(String domainName, Date transferDate) {
        domainService.createTransferRecord(domainName, transferDate, losingAccount.getBillingContact().getNicHandle(),
                gainingAccount.getBillingContact().getNicHandle());
    }

    private void markAsTransferred(String domainName, Date transferDate) {
        dao.markDomainAsTransferred(bulkTransferId, domainName, user.getUsername(), transferDate);
    }

    private void transferDomain(Domain domain) throws BulkTransferException {
        try {
            domain.setResellerAccount(gainingAccount);
            domain.setBillingContacts(Arrays.asList(gainingAccount.getBillingContact()));
            domain.setTechContacts(Arrays.asList(gainingTechContact));
            for (Contact c : domain.getAdminContacts()) {
                if (c != null && c.getNicHandle() != null) {
                    updateAdminC(c.getNicHandle());
                }
            }
            domain.setRemark(transferRequest.getRemarks());
            domain.setAuthCode(null);
            domain.setAuthCodeExpirationDate(null);
            OpInfo opInfo = new OpInfo(user, transferRequest.getRemarks());
            domainService.save(domain, opInfo);
            domainStateMachine.handleEvent(user, domain, new BulkTransferEvent(), opInfo);
        } catch (BulkTransferException e) {
            throw e;
        } catch (Exception e) {
            throw new BulkTransferException("Error transferring domain", e);
        }
    }

    private void sendNotificationEmail(Domain domain) throws BulkTransferException {
        try {
            EmailParameters param = new BulkTransferEmailParameters(transferRequest, user.getUsername(), accountService,
                    nicHandleSearchService, domain);
            emailSender.sendEmail(EmailTemplateNamesEnum.BULK_TRANSFER_FOR_DOMAIN.getId(), param);
        } catch (Exception e) {
            throw new BulkTransferException("Error sending notification email", e);
        }
    }

    private void updateAdminC(String nicHandle) throws BulkTransferException {
        try {
            OpInfo opInfo = new OpInfo(user, transferRequest.getRemarks());
            nicHandleService.changeNicHandleAccount(nicHandle, gainingAccount.getId(), opInfo);
        } catch (Exception e) {
            throw new BulkTransferException("Error updating admin contact" + nicHandle, e);
        }
    }
}
