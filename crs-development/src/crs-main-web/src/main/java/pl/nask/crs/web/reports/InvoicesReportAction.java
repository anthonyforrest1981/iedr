package pl.nask.crs.web.reports;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.utils.JsonResponse;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.PlainInvoice;
import pl.nask.crs.payment.PlainInvoiceSearchCriteria;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.SessionStorageAction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoicesReportAction extends GenericSearchAction<PlainInvoice, PlainInvoiceSearchCriteria> implements
        SessionStorageAction {
    private static final Logger log = Logger.getLogger(InvoicesReportAction.class);

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";
    private final PaymentAppService paymentAppService;
    private final AccountSearchService accountSearchService;

    private boolean showAll;

    private Set<String> selected;
    private List<String> selectedInvoices;
    private InputStream mergedInvoices;

    public InvoicesReportAction(final PaymentAppService paymentAppService, AccountSearchService accountSearchService) {
        super(new AppSearchService<PlainInvoice, PlainInvoiceSearchCriteria>() {
            public LimitedSearchResult<PlainInvoice> search(AuthenticatedUser user, PlainInvoiceSearchCriteria criteria,
                    long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
                return paymentAppService.findInvoices(user, criteria, offset, limit, orderBy);
            }
        });
        this.paymentAppService = paymentAppService;
        this.accountSearchService = accountSearchService;
        selected = new TreeSet<String>();
    }

    @Override
    protected PlainInvoiceSearchCriteria createSearchCriteria() {
        return new PlainInvoiceSearchCriteria();
    }

    public String getDatePattern() {
        return YEAR_MONTH_DAY_PATTERN;
    }

    public List<CustomerType> getCustomerTypes() {
        return Arrays.asList(CustomerType.Direct, CustomerType.Registrar);
    }

    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    public Set<String> getSelected() {
        return selected;
    }

    public void setSelected(Set<String> selected) {
        this.selected = selected;
    }

    public List<String> getSelectedInvoices() {
        return selectedInvoices;
    }

    public void setSelectedInvoices(List<String> selectedInvoices) {
        this.selectedInvoices = selectedInvoices;
    }

    public InputStream getMergedInvoices() {
        return mergedInvoices;
    }

    protected void updateSearchCriteria(PlainInvoiceSearchCriteria searchCriteria) {
        if (Validator.isEmpty(searchCriteria.getBillingNH()))
            searchCriteria.setBillingNH(null);
        if (Validator.isEmpty(searchCriteria.getAccountName()))
            searchCriteria.setAccountName(null);
        if (Validator.isEmpty(searchCriteria.getInvoiceNumber()))
            searchCriteria.setInvoiceNumber(null);
        if (Validator.isEmpty(searchCriteria.getInvoiceNumberFrom()))
            searchCriteria.setInvoiceNumberFrom(null);
        if (Validator.isEmpty(searchCriteria.getInvoiceNumberTo()))
            searchCriteria.setInvoiceNumberTo(null);
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

    public List<Account> getAccountsByName() {
        return accountSearchService.getAccountsWithExclusion();
    }

    public List<Account> getAccountsByNic() {
        return accountSearchService.getAccountsWithExclusion(null,
                Arrays.asList(new SortCriterion("billingContactId", true)));
    }

    public String viewMergedInvoices() {
        if (selected == null || selected.isEmpty()) {
            addActionError("No invoices selected!");
            return ERROR;
        }
        List<String> invoiceNumbers = new ArrayList<String>(selected);
        Collections.sort(invoiceNumbers, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        try {
            mergedInvoices = paymentAppService.viewMergedInvoices(getUser(), invoiceNumbers);
        } catch (InvoiceNotFoundException e) {
            log.error("Couldn't find invoice: " + e.number, e);
            addActionError(String.format("Invoice %s not found in the system", e.number));
            return ERROR;
        } catch (Exception e) {
            log.error("Error while merging invoices", e);
            addActionError("Merging invoices failed. See logs for details.");
            return ERROR;
        }
        selected.clear();
        return SUCCESS;
    }

    public String addToStorage() throws IOException {
        if (selected == null)
            selected = new TreeSet<String>();
        if (selectedInvoices != null)
            selected.addAll(selectedInvoices);
        return JsonResponse.OK();
    }

    public String removeFromStorage() throws IOException {
        if (selected == null)
            selected = new TreeSet<String>();
        if (selectedInvoices != null)
            selected.removeAll(selectedInvoices);
        return JsonResponse.OK();
    }

    public String getAllSelectedAsString() {
        return Joiner.on(",").join(selected);
    }
}
