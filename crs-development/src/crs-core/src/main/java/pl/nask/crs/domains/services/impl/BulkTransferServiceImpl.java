package pl.nask.crs.domains.services.impl;

import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.dao.BulkTransferDAO;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.services.*;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class BulkTransferServiceImpl implements BulkTransferService {
    private final static Logger LOG = Logger.getLogger(BulkTransferServiceImpl.class);

    private final BulkTransferDAO dao;

    private final DomainDAO domainDao;

    private final AccountSearchService accountService;

    private final NicHandleSearchService nicHandleSearchService;

    private final NicHandleService nicHandleService;

    private final ResellerDefaultsService defaultsService;

    private final DomainSearchService domainSearchService;

    private final DomainService domainService;

    private final DomainStateMachine domainStateMachine;

    private final EmailTemplateSender emailSender;

    public BulkTransferServiceImpl(BulkTransferDAO dao, DomainDAO domainDao, AccountSearchService accountSearchService,
            NicHandleSearchService nicHandleSearchService, NicHandleService nicHandleService,
            ResellerDefaultsService defaultsService, DomainSearchService domainSearchService,
            DomainService domainService, DomainStateMachine domainStateMachine, EmailTemplateSender emailSender) {
        this.dao = dao;
        this.domainDao = domainDao;
        this.accountService = accountSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.defaultsService = defaultsService;
        this.domainSearchService = domainSearchService;
        this.domainService = domainService;
        this.domainStateMachine = domainStateMachine;
        this.emailSender = emailSender;
    }

    @Override
    public long createBulkTransferProcess(long losingAccount, long gainingAccount, String remarks) {
        if (losingAccount == gainingAccount) {
            throw new IllegalArgumentException("Gaining and losing account must be different");
        }
        if (nicHandleSearchService.isAccountDirect(losingAccount) ||
                nicHandleSearchService.isAccountDirect(gainingAccount)) {
            throw new IllegalArgumentException("Neither losing nor gaining account may be a guest account");
        }

        return dao.createBulkTransferProcess(losingAccount, gainingAccount, remarks);
    }

    @Override
    public void addDomains(long bulkTransferId, List<String> domainNames) throws BulkTransferValidationException {
        // validation first
        BulkTransferRequest tr = dao.get(bulkTransferId);
        List<String> errors = new ArrayList<>();

        Set<String> duplicatedDomains = tr.containsDomains(domainNames);
        for (String domain : duplicatedDomains) {
            errors.add(domain + " is already added to the request");
        }

        // all other domains should be validated for their existence
        for (String domain : domainNames) {
            if (!duplicatedDomains.contains(domain) && !domainDao.exists(domain)) {
                errors.add(domain + " does not exist");
            }
        }

        if (!errors.isEmpty()) {
            throw new BulkTransferValidationException(errors);
        }

        for (String domain : domainNames) {
            dao.addDomainToTransfer(bulkTransferId, domain);
        }
    }

    @Override
    public List<BulkTransferRequest> findTransfers() {
        return dao.find(null).getResults();
    }

    @Override
    public BulkTransferRequest getTransferRequest(long id) {
        return dao.get(id);
    }

    @Override
    public void removeDomain(long id, String domainName) throws BulkTransferValidationException {
        if (dao.isNotTransferred(id, domainName)) {
            dao.removeDomainFromTransfer(id, domainName);
        } else {
            throw new BulkTransferValidationException(Arrays.asList(domainName + " already transferred"));
        }
    }

    @Override
    public void closeTransferRequest(long id, String hostmasterHandle) throws BulkTransferValidationException {
        dao.lock(id);// lock does not fetch all domains
        BulkTransferRequest tr = dao.get(id);
        assertNotClosed(tr);
        assertFullyCompleted(tr);
        closeTransferAndSendEmails(tr, hostmasterHandle);
    }

    @Override
    public void forceCloseTransferRequest(long id, String hostmasterHandle) {
        if (dao.lock(id)) {
            BulkTransferRequest locked = dao.get(id);
            if (!locked.isClosed()) {
                closeTransferAndSendEmails(locked, hostmasterHandle);
            }
        }
    }

    @Override
    public void transferAll(AuthenticatedUser user, long id)
            throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException,
            AccountNotFoundException {
        transfer(user, id, false);
        closeTransferAndSendEmails(dao.get(id), user.getUsername());
    }

    @Override
    public void transferValid(AuthenticatedUser user, long id)
            throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException,
            AccountNotFoundException {
        transfer(user, id, true);
    }

    private void transfer(AuthenticatedUser user, long id, boolean skipDomainsWithValidationErrors)
            throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException,
            AccountNotFoundException {
        BulkTransfer transfer = new BulkTransfer(id, user, dao, accountService, nicHandleSearchService,
                nicHandleService, defaultsService, domainSearchService, domainService, domainStateMachine, emailSender);
        transfer.transferDomains(skipDomainsWithValidationErrors);
    }

    private void assertNotClosed(BulkTransferRequest tr) throws BulkTransferValidationException {
        if (tr.isClosed()) {
            throw new BulkTransferValidationException(Arrays.asList("Transfer already closed"));
        }
    }

    private void assertFullyCompleted(BulkTransferRequest tr) throws BulkTransferValidationException {
        if (!tr.isFullyCompleted()) {
            throw new BulkTransferValidationException(
                    Arrays.asList("Transfer not completed, there are still some domains pending"));
        }
    }

    private void closeTransferAndSendEmails(BulkTransferRequest request, String hostmasterHandle) {
        dao.closeTransfer(request.getId(), hostmasterHandle, new Date());
        try {
            emailSender.sendEmail(EmailTemplateNamesEnum.BULK_TRANSFER_COMPLETED.getId(),
                    new BulkTransferEmailParameters(request, hostmasterHandle, accountService, nicHandleSearchService));
        } catch (TemplateNotFoundException e) {
            LOG.warn("No template found: " + EmailTemplateNamesEnum.BULK_TRANSFER_COMPLETED.getId());
        } catch (TemplateInstantiatingException e) {
            LOG.warn("Error instatiating template " + EmailTemplateNamesEnum.BULK_TRANSFER_COMPLETED.getId() + " : "
                    + e.getMessage());
        } catch (Exception e) {
            LOG.warn("Error sending email: " + e);
        }
    }

}
