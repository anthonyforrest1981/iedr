package pl.nask.crs.web.domains;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang.time.DateUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.utils.JsonResponse;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockingRenewalDateOutOfBoundsException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.SessionStorageAction;

public class DomainsRollLockingServiceAction extends GenericSearchAction<Domain, DomainSearchCriteria> implements
        SessionStorageAction {

    private DomainAppService domainAppService;
    private AccountSearchService accountSearchService;

    private final Set<String> selected;
    private List<String> selectedDomains;

    private List<DomainLockRollInfoWrapper> domainsToRoll;

    private boolean showAll;

    public DomainsRollLockingServiceAction(final DomainAppService domainAppService,
            AccountSearchService accountSearchService) {
        super(new AppSearchService<Domain, DomainSearchCriteria>() {
            @Override
            public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return domainAppService.searchFull(user, criteria, offset, limit, orderBy);
            }
        });
        this.setDefaultSortBy(Collections.singletonList(new SortCriterion("lockingRenewalDate", true)));
        this.domainAppService = domainAppService;
        this.accountSearchService = accountSearchService;
        selected = new TreeSet<>();
    }

    @Override
    protected void resetSearch() {
        super.resetSearch();
        if (getResetSearch()) {
            selected.clear();
        }
    }

    @Override
    protected DomainSearchCriteria createSearchCriteria() {
        DomainSearchCriteria searchCriteria = new DomainSearchCriteria();
        searchCriteria.setLockingActive(true);
        return searchCriteria;
    }

    public String confirmRollDates() throws AccessDeniedException, DomainNotFoundException {
        if (selected == null || selected.isEmpty()) {
            addActionMessage("No domains are selected for prolonging");
            return ERROR;
        }
        int index = 0;
        domainsToRoll = new ArrayList<>();
        List<Domain> domains = domainAppService.getAll(getUser(), Lists.newArrayList(selected));
        for (Domain domain : domains) {
            domainsToRoll.add(new DomainLockRollInfoWrapper(index++, domain));
        }
        return SUCCESS;
    }

    public String doRollDates()
            throws TemplateInstantiatingException, AccessDeniedException, EmailSendingException,
            TemplateNotFoundException {
        try {
            Map<String, Date> domainsNewLockingRenewalDates = new HashMap<>();
            for (DomainLockRollInfoWrapper domainInfo : domainsToRoll) {
                domainsNewLockingRenewalDates.put(domainInfo.getName(), domainInfo.getNewLockingRenewalDate());
            }
            domainAppService.rollLockRenewalDates(getUser(), domainsNewLockingRenewalDates);
            addActionMessage("Domain dates are rolled");
            return SUCCESS;
        } catch (DomainIllegalStateException e) {
            addActionError(e.getDomainName() + ": " + e.getMessage());
            return ERROR;
        } catch (DomainLockingRenewalDateOutOfBoundsException e) {
            addActionError(e.getDomainName() + ": " + e.getRawErrorMessage());
            return ERROR;
        }
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public Set<String> getSelected() {
        return selected;
    }

    public void setSelected(Set<String> newValues) {
        selected.clear();
        if (newValues != null) {
            selected.addAll(newValues);
        }
    }

    public List<String> getSelectedDomains() {
        return selectedDomains;
    }

    public void setSelectedDomains(List<String> selectedDomains) {
        this.selectedDomains = selectedDomains;
    }

    public String addToStorage() throws IOException {
        if (selectedDomains != null)
            selected.addAll(selectedDomains);
        return JsonResponse.OK();
    }

    public String removeFromStorage() throws IOException {
        if (selectedDomains != null)
            selected.removeAll(selectedDomains);
        return JsonResponse.OK();
    }

    public String getAllSelectedAsString() {
        return Joiner.on(",").join(selected);
    }

    public List<DomainLockRollInfoWrapper> getDomainsToRoll() {
        return domainsToRoll;
    }

    public Date getProposedRolledDate(final Date base) {
        return DateUtils.addYears(base, 1);
    }

    public void setDomainsToRoll(List<DomainLockRollInfoWrapper> domainsToRoll) {
        this.domainsToRoll = domainsToRoll;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    @Override
    protected int getPageSize() {
        return showAll ? Integer.MAX_VALUE : super.getPageSize();
    }

    public static class DomainLockRollInfoWrapper {
        private final int index;
        private final String name;
        private final String billingNH;
        private final String billingName;
        private final Date renewalDate;
        private final boolean locked;
        private final Date lockingDate;
        private final Date lockingRenewalDate;
        private Date newLockingRenewalDate;

        public DomainLockRollInfoWrapper(final int index, final Domain domain) {
            this.index = index;
            name = domain.getName();

            Contact billingContact = domain.getBillingContact();
            if (billingContact != null) {
                billingNH = billingContact.getNicHandle();
                billingName = billingContact.getName();
            } else {
                billingName = billingNH = null;
            }
            renewalDate = domain.getRenewalDate();
            locked = domain.getDsmState().getLocked();
            lockingDate = domain.getLockingDate();
            lockingRenewalDate = domain.getLockingRenewalDate();
            if (lockingRenewalDate != null) {
                newLockingRenewalDate = DateUtils.addYears(lockingRenewalDate, 1);
            } else {
                newLockingRenewalDate = null;
            }
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public String getBillingNH() {
            return billingNH;
        }

        public String getBillingName() {
            return billingName;
        }

        public Date getRenewalDate() {
            return renewalDate;
        }

        public boolean isLocked() {
            return locked;
        }

        public Date getLockingDate() {
            return lockingDate;
        }

        public Date getLockingRenewalDate() {
            return lockingRenewalDate;
        }

        public Date getNewLockingRenewalDate() {
            return newLockingRenewalDate;
        }

        public void setNewLockingRenewalDate(Date newLockingRenewalDate) {
            this.newLockingRenewalDate = newLockingRenewalDate;
        }
    }
}
