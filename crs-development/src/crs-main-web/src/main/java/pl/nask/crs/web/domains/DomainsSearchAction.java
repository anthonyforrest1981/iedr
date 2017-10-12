package pl.nask.crs.web.domains;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.web.GenericSearchAction;

public class DomainsSearchAction extends GenericSearchAction<Domain, DomainSearchCriteria> {

    private final static String[] REG_DATE_FORMATS = new String[] {"yyyy-MM-dd"};
    private final static List<DomainHolderType> holderTypes = Arrays.asList(DomainHolderType.values());
    private final static List<NRPStatus> nrpStatuses = Arrays.asList(NRPStatus.values());

    private AccountSearchService accountSearchService;
    private EntityService entityService;
    // previously edited/transferred domain name
    private String domainName;
    private Date registrationFrom;
    private Date registrationTo;

    public DomainsSearchAction(DomainAppService domainAppService, AccountSearchService accountSearchService,
            EntityService entityService) {
        super(domainAppService);
        Validator.assertNotNull(accountSearchService, "account search service");
        Validator.assertNotNull(entityService, "entity service");
        this.accountSearchService = accountSearchService;
        this.entityService = entityService;
    }

    public void setRegistrationFrom(String registrationFrom) {
        try {
            Date reg = org.apache.commons.lang.time.DateUtils.parseDate(registrationFrom, REG_DATE_FORMATS);
            this.registrationFrom = DateUtils.startOfDay(reg);
        } catch (ParseException e) {
            addActionError("Invalid date format " + e);
        }
    }

    public void setRegistrationTo(String registrationTo) {
        try {
            Date reg = org.apache.commons.lang.time.DateUtils.parseDate(registrationTo, REG_DATE_FORMATS);
            this.registrationTo = DateUtils.startOfDay(reg);
        } catch (ParseException e) {
            addActionError("Invalid date format " + e);
        }
    }

    @Override
    protected DomainSearchCriteria createSearchCriteria() {
        return new DomainSearchCriteria();
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<EntityClass> getClassList() {
        return entityService.getClasses();
    }

    public List<EntityCategory> getCategoryList() {
        return entityService.getCategories();
    }

    @Override
    protected void updateSearchCriteria(DomainSearchCriteria searchCriteria) {
        Long lall = -1l;

        if ("".equals(searchCriteria.getNicHandle()))
            searchCriteria.setNicHandle(null);
        if ("".equals(searchCriteria.getDomainHolder()))
            searchCriteria.setDomainHolder(null);
        if ("".equals(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
        if ("".equals(searchCriteria.getHolderClassName()))
            searchCriteria.setHolderClassName(null);
        if ("".equals(searchCriteria.getHolderCategoryName()))
            searchCriteria.setHolderCategoryName(null);
        if (lall.equals(searchCriteria.getAccountId()))
            searchCriteria.setAccountId(null);
        if (registrationFrom != null)
            searchCriteria.setRegistrationFrom(registrationFrom);
        if (registrationTo != null)
            searchCriteria.setRegistrationTo(registrationTo);
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    // returns list containing previously edited domain
    public List<Domain> getProcessedDomainsList() {
        List<Domain> l = new ArrayList<Domain>();
        if (domainName == null)
            return l;

        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName(domainName);

        LimitedSearchResult<Domain> t;
        try {
            t = searchService.search(getUser(), criteria, 0, 1, null);
            l.addAll(t.getResults());
        } catch (Exception e) {
            addActionError("Internal error occurred: " + e);
            e.printStackTrace();
        }
        return l;
    }

    public List<NRPStatus> getNrpStatuses() {
        return nrpStatuses;
    }

    public List<DomainHolderType> getHolderTypes() {
        return holderTypes;
    }

}
