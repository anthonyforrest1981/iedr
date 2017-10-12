package com.iedr.bpr.tests.crsweb;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.pages.crsweb.AccountPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.SchedulerMonitor.SchedulerJob;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainRegistrationUtils;
import com.iedr.bpr.tests.utils.console.PaymentUtils;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UC036 extends SeleniumTest {

    public UC036(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc036_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc036_data.sql";
    }

    @Test
    public void test_uc036_nosc01() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Deposit Actual Balance
        User user = this.registrar;
        crsweb().login(this.internal);
        DepositReport report = new DepositReport();
        report.setNicHandleCriterion(user);
        testReport(report);
        float availableBalance = report.actualBalance;
        float topUpAmount = topUpOnline(user);
        float domainCost = registerDomain("uc036-nosc01.ie", user, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS, 1);
        crsweb().login(this.internal);
        report.setNicHandleCriterion(user);
        report.run();
        assertEquals(availableBalance + topUpAmount - domainCost, report.actualBalance, 1e-2);
    }

    @Test
    public void test_uc036_nosc02() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: DOA
        User user = this.registrar;
        crsweb().login(this.internal);

        float amount = 1000;
        String remark = "Test remark";
        String previousOrderId = db().getDepositOrderId(user.login);
        String orderId = topUpOffline(user, amount, remark);
        assertNotEquals(previousOrderId, orderId);

        DoaReport report = new DoaReport();
        report.setNicHandleCriterion(user);
        report.setTransactionDateRangeCriterion(new Date(), new Date());
        report.setShowAllCriterion();
        testReport(report);

        DoaReport.Row row = report.getRow(orderId);
        assertEquals(orderId, row.orderId);
        assertEquals(user.login, row.billCNic);
        assertEquals(this.internal.login, row.staffNic);
        assertEquals(amount, row.amount, 1e-2);
        assertEquals(remark, row.remark);
    }

    @Test
    public void test_uc036_nosc03() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Invoices
        User user = this.registrar;
        String domainName = "uc036-nosc03.ie";
        InvoicesReport report = new InvoicesReport();
        registerDomain(domainName, user, PredefinedPayments.VALID_CREDIT_CARD, 1);
        String invoiceNumber = db().getSettledInvoiceNumber(domainName);
        report.setBillingNhCriterion(user);
        report.setInvoiceRangeCriterion(invoiceNumber, invoiceNumber);
        float netAmount = db().getPriceForRegistrar(1, true);
        float vatRate = db().getVatRate(user.login);
        crsweb().login(this.internal);
        testReport(report);
        List<InvoicesReport.Invoice> rows = report.invoices;
        assertEquals(1, rows.size());
        InvoicesReport.Invoice invoice = rows.get(0);
        assertEquals(invoiceNumber, invoice.number);
        assertEquals(user.login, invoice.billCNic);
        assertEquals(netAmount, invoice.netAmount, 1e-2);
        assertEquals(vatRate * netAmount, invoice.vatAmount, 1e-2);
    }

    @Test
    public void test_uc036_nosc04() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Ex Invoices
        User user = this.registrar;
        String domainName = "uc036-nosc04.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        registerDomain(domainName, user, paymentDetails, paymentPeriod);
        float netAmount = db().getPriceForRegistrar(paymentPeriod, true);
        float vatRate = db().getVatRate(user.login);
        String invoiceNumber = db().getSettledInvoiceNumber(domainName);
        RevenueAssuranceReport report = new RevenueAssuranceReport();
        report.setInvoiceCriterion(invoiceNumber);
        crsweb().login(this.internal);
        testReport(report);
        List<RevenueAssuranceReport.ExtendedReservation> rows = report.invoices;
        assertEquals(1, rows.size());
        RevenueAssuranceReport.ExtendedReservation invoice = rows.get(0);
        assertEquals(db().getSettledInvoiceNumber(domainName), invoice.number);
        assertEquals(domainName, invoice.domainName);
        assertEquals(user.login, invoice.billCNic);
        assertEquals(paymentDetails.getMethod(), invoice.method);
        assertEquals(paymentPeriod * 12, invoice.durationMonths);
        assertEquals(netAmount, invoice.netAmount, 1e-2);
        assertEquals(vatRate * netAmount, invoice.vatAmount, 1e-2);
    }

    @Test
    public void test_uc036_nosc05() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Receipts: Bank
        // Reconciliation
        User user = this.registrar;
        String domainName = "uc036-nosc05.ie";
        PaymentDetails paymentDetails = PredefinedPayments.VALID_CREDIT_CARD;
        int paymentPeriod = 1;
        float domainCost = registerDomain(domainName, user, paymentDetails, paymentPeriod);
        String invoiceNumber = db().getSettledInvoiceNumber(domainName);
        String orderId = db().getSettledOrderId(domainName);
        BankReconciliationReport report = new BankReconciliationReport();
        report.setInvoiceRangeCriterion(invoiceNumber, invoiceNumber);
        crsweb().login(this.internal);
        testReport(report);
        List<BankReconciliationReport.Invoice> rows = report.invoices;
        assertEquals(1, rows.size());
        BankReconciliationReport.Invoice invoice = rows.get(0);
        assertEquals(user.login, invoice.billCNic);
        assertEquals(domainName, invoice.domainName);
        assertEquals(db().getSettledInvoiceNumber(domainName), invoice.number);
        assertEquals(orderId, invoice.orderId);
        assertEquals(domainCost, invoice.amount, 1e-2);
    }

    @Test
    public void test_uc036_nosc06() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: NRP Domains
        User user = this.registrar;
        NrpDomainsReport report = new NrpDomainsReport();
        report.setRegistrarNameCiterion(db().getAccountName(db().getNicHandleAccountNumber(user.login)));
        report.setNrpStatusCriterion("IM");
        crsweb().login(this.internal);
        testReport(report);
        List<String> domains = report.domains;
        List<String> expectedDomains = db().getNicHandleDomainsInState(user.login, Arrays.asList(2, 18, 66, 82));
        assertEquals(expectedDomains, domains);
    }

    @Test
    public void test_uc036_nosc07() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Charity Domains
        User user = this.registrar;
        compareNrpAndCharityReports(user);
        checkAllCharityDomains(user);
    }

    @Test
    public void test_uc036_nosc08() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: TotalDomains
        User user = this.registrar;
        TotalDomainsReport report = new TotalDomainsReport();
        crsweb().login(this.internal);
        testReport(report);
        int registrarAccount = db().getNicHandleAccountNumber(user.login);
        int expectedCount = db().getDomainsCountForAccount(registrarAccount);
        int reportedCount = report.getTotalDomains(user.login);
        assertEquals(expectedCount, reportedCount);
        int guestAccount = 1;
        expectedCount = db().getDomainsCountForAccount(guestAccount);
        reportedCount = report.getTotalDomains("Direct Registrants");
        assertEquals(expectedCount, reportedCount);
    }

    @Test
    public void test_uc036_nosc09() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Registrations Per
        // Month/Year for Registrar Report
        User user = this.registrar;
        crsweb().login(this.internal);
        int accountNumber = db().getNicHandleAccountNumber(user.login);
        String holderClass = "Body Corporate (Ltd,PLC,Company)";
        String holderCategory = "Corporate Name";
        Date regDateFrom = new LocalDate().minusMonths(12).toDate();
        Date regDateTo = new Date();
        checkRegistrationsReport(user, accountNumber, holderClass, holderCategory, regDateFrom, regDateTo, false);
        checkRegistrationsReport(user, accountNumber, holderClass, holderCategory, null, null, true);
    }

    @Test
    public void test_uc036_nosc10() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Domains per Class
        // Category Report
        User user = this.registrar;
        crsweb().login(this.internal);
        int accountNumber = db().getNicHandleAccountNumber(user.login);
        String holderClass = "Body Corporate (Ltd,PLC,Company)";
        String holderCategory = "Corporate Name";
        int expectedCount = getRegistrationsCountFromDb(accountNumber, holderClass, holderCategory, null, null);
        int reportedTotalCount = getRegistrationsCountFromFullReport(user, accountNumber, holderClass, holderCategory);
        assertEquals(expectedCount, reportedTotalCount);
    }

    @Test
    public void test_uc036_nosc11() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Deleted Domains
        User user = this.registrar;
        int accountNumber = db().getNicHandleAccountNumber(user.login);
        crsweb().login(this.internal);
        DeletedDomainsReport report = new DeletedDomainsReport();
        report.setAccountCriterion(accountNumber);
        testReport(report);
        int deletedDomains = report.deleted;

        Map<String, String> exactCriteria = new HashMap<String, String>();
        Map<String, List<Date>> dateRangeCriteria = new HashMap<String, List<Date>>();
        exactCriteria.put("A_Number", String.valueOf(accountNumber));
        exactCriteria.put("DSM_State", "387");
        Date regDateFrom = new LocalDate().minusMonths(12).toDate();
        dateRangeCriteria.put("D_Reg_TS", Arrays.asList(regDateFrom, regDateFrom));
        int expectedDomains = db().getHistDomainsCount(exactCriteria, dateRangeCriteria, true);

        assertEquals(expectedDomains, deletedDomains);
    }

    @Test
    public void test_uc036_nosc12() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Invoices (Merge invoices)
        User user = this.registrar;
        crsweb().login(this.internal);
        InvoicesReport report = new InvoicesReport();
        report.setBillingNhCriterion(user);
        testReport(report);
        assertEquals(0, report.getSelectedInvoicesCount());
        List<String> invoiceNumbers = report.getInvoiceNumbers();
        report.toggleInvoices(invoiceNumbers.subList(0, 3));
        assertEquals(3, report.getSelectedInvoicesCount());
        report.toggleInvoices(invoiceNumbers.subList(0, 1));
        assertEquals(2, report.getSelectedInvoicesCount());
        List<String> page1Invoices = invoiceNumbers.subList(1, 3);
        assertEquals(page1Invoices, report.getInvoicesSelectedOnCurrentPage());
        report.goToNextPage();
        assertEquals(2, report.getSelectedInvoicesCount());
        invoiceNumbers = report.getInvoiceNumbers();
        report.toggleInvoices(invoiceNumbers.subList(0, 2));
        assertEquals(4, report.getSelectedInvoicesCount());
        report.toggleSelectAll();
        assertEquals(2, report.getSelectedInvoicesCount());
        report.toggleSelectAll();
        assertEquals(2 + invoiceNumbers.size(), report.getSelectedInvoicesCount());
        report.goToPreviousPage();
        assertEquals(page1Invoices, report.getInvoicesSelectedOnCurrentPage());
        report.clearAll();
        assertEquals(0, report.getSelectedInvoicesCount());
    }

    @Test
    public void test_uc036_nosc13() throws SQLException {
        // UC#036: Run Accounts Report - UC036 - Title: Autorenews
        User user = this.registrar;
        String renewalMode = "A";
        crsweb().login(this.internal);
        AutorenewsReport report = new AutorenewsReport();
        report.setBillingNhCriterion(user);
        report.setRenewalModeCriterion(renewalMode);
        report.setShowAllCriterion();
        testReport(report);
        List<String> expectedDomains = db().getNicHandleDomainsInState(user.login,
                db().getDsmStates("Renewal_Mode", renewalMode));
        assertEquals(expectedDomains, report.autorenews);
    }

    private void checkRegistrationsReport(User user, int accountNumber, String holderClass, String holderCategory,
            Date regDateFrom, Date regDateTo, boolean compareReports) throws SQLException {
        int expectedCount = getRegistrationsCountFromDb(accountNumber, holderClass, holderCategory, regDateFrom,
                regDateTo);
        int reportedYearlyCount = getRegistrationsCountFromYearlyReport(user, accountNumber, holderClass,
                holderCategory, regDateFrom, regDateTo);
        assertEquals(expectedCount, reportedYearlyCount);
        if (compareReports) {
            int reportedTotalCount = getRegistrationsCountFromFullReport(user, accountNumber, holderClass,
                    holderCategory);
            assertEquals(reportedYearlyCount, reportedTotalCount);
        }
    }

    private int getRegistrationsCountFromDb(int accountNumber, String holderClass, String holderCategory,
            Date regDateFrom, Date regDateTo) throws SQLException {
        Map<String, String> exactCriteria = new HashMap<String, String>();
        Map<String, List<Date>> dateRangeCriteria = new HashMap<String, List<Date>>();
        exactCriteria.put("A_Number", String.valueOf(accountNumber));
        exactCriteria.put("CL.name", holderClass);
        exactCriteria.put("CT.name", holderCategory);

        if (regDateFrom != null && regDateTo != null) {
            dateRangeCriteria.put("D_Reg_TS", Arrays.asList(regDateFrom, regDateTo));
        }

        return db().getDomainsCountWithJoin(exactCriteria, dateRangeCriteria, true);
    }

    private int getRegistrationsCountFromYearlyReport(User user, int accountNumber, String holderClass,
            String holderCategory, Date regDateFrom, Date regDateTo) {
        RegistrationsReport regReport = new RegistrationsReport();
        regReport.setAccountCriterion(accountNumber);
        regReport.setHolderClassCriterion(holderClass);
        regReport.setHolderCategoryCriterion(holderCategory);

        if (regDateFrom != null && regDateTo != null) {
            regReport.setRegistrationRangeCriterion(regDateFrom, regDateTo);
        }

        testReport(regReport);
        return regReport.getTotalDomains(user.login, holderClass, holderCategory);
    }

    private int getRegistrationsCountFromFullReport(User user, int accountNumber, String holderClass,
            String holderCategory) {
        PerClassReport perClassReport = new PerClassReport();
        perClassReport.setAccountCriterion(accountNumber);
        perClassReport.setHolderClassCriterion(holderClass);
        perClassReport.setHolderCategoryCriterion(holderCategory);

        testReport(perClassReport);
        return perClassReport.getTotalDomains(user.login, holderClass, holderCategory);
    }

    private void compareNrpAndCharityReports(User user) throws SQLException {
        NrpDomainsReport nrpReport = new NrpDomainsReport();
        nrpReport.setBillingNhCriterion(user);
        nrpReport.setNrpStatusCriterion("VS");
        nrpReport.setHolderTypeCriterion("C");
        CharityDomainsReport charityReport = new CharityDomainsReport();
        charityReport.setBillingNhCriterion(user);
        charityReport.setNrpStatusCriterion("VS");
        crsweb().login(this.internal);
        nrpReport.run();
        charityReport.run();
        List<String> nrpDomains = nrpReport.domains;
        List<String> charityDomains = charityReport.domains;
        List<Integer> dsmStates = db().getDsmStates("NRP_Status", "VS");
        List<String> expectedDomains = db().getNicHandleDomainsInState(user.login, dsmStates);
        assertEquals(expectedDomains, nrpDomains);
        assertEquals(nrpDomains, charityDomains);
    }

    private void checkAllCharityDomains(User user) throws SQLException {
        CharityDomainsReport charityReport = new CharityDomainsReport();
        charityReport.setBillingNhCriterion(user);
        crsweb().login(this.internal);
        charityReport.run();
        List<String> domains = charityReport.domains;
        List<Integer> dsmStates = db().getDsmStates("D_Holder_Type", "C");
        List<String> expectedDomains = db().getNicHandleDomainsInState(user.login, dsmStates);
        assertEquals(expectedDomains, domains);
    }

    private float topUpOnline(User user) {
        console().login(user);
        String topUpAmount = "1000";
        PaymentUtils.topUpAccount(user, topUpAmount);
        return Float.valueOf(topUpAmount);
    }

    private String topUpOffline(User user, float amount, String remark) throws SQLException {
        AccountPage da = new AccountPage(user);
        da.topUpAccount(amount, remark, false);
        String orderId = db().getDepositOrderId(user.login);
        return orderId;
    }

    private float registerDomain(String domainName, User user, PaymentDetails paymentDetails, int paymentPeriod)
            throws SQLException {
        console().login(user);
        DomainRegistrationDetails details = new DomainRegistrationDetails(user, domainName, paymentDetails,
                paymentPeriod, true);
        DomainRegistrationUtils.registerDomain(domainName, user, details);
        ViewTicketPage ttp = new ViewTicketPage(this.internal);
        ttp.triplePassTicketAccept(domainName);
        scheduler().runJob(SchedulerJob.INVOICING);
        float vatRate = db().getVatRate(user.login);
        float totalCost = db().getPriceForRegistrar(1, true) * (1 + vatRate);
        return totalCost;
    }

    private void testReport(Report report) {
        report.run();
        report.checkSearchCriteria();
        report.checkColumns();
        report.checkExportOptions();
        report.checkReportSpecific();
    }

    private abstract class Report {

        protected final String prefix;
        protected List<BaseSearchCriterion> searchCriteria;
        protected List<String> visibleColumns;

        public Report(String prefix) {
            this.prefix = prefix;
            searchCriteria = new ArrayList<BaseSearchCriterion>();
        }

        public abstract void view();

        public abstract void checkSearchCriteria();

        protected abstract List<String> getExpectedColumns();

        public void run() {
            view();
            fillSearchCriteria();
            searchCriteria.clear();
            saveVisibleColumns();
            saveSearchResults();
        }

        protected void saveVisibleColumns() {
            visibleColumns = getVisibleColumns();
        }

        protected void saveSearchResults() {}

        public void checkExportOptions() {
            assertEquals(getExpectedExportOptions(), getVisibleExportOptions());
        }

        public void checkColumns() {
            assertEquals(getExpectedColumns(), visibleColumns);
        }

        public void checkReportSpecific() {}

        public void goToNextPage() {
            goToPage(">");
        }

        public void goToPreviousPage() {
            goToPage("<");
        }

        private void goToPage(String page) {
            WebElement pageLinks = wd().findElement(By.className("pagelinks"));
            By linkBy = By.xpath(String.format("//a[normalize-space(.)='%s']", page));
            crsweb().clickElement(pageLinks.findElement(linkBy));
        }

        public void setAccountCriterion(int accountNumber) {
            setSearchCriterion(new SearchCriterion("accountId", String.valueOf(accountNumber), true, true));
        }

        public void setBillingNhCriterion(User user) {
            setSearchCriterion(new SearchCriterion("billingNH", user.login, true, true));
        }

        public void setHolderCategoryCriterion(String holderCategory) {
            setSearchCriterion(new SearchCriterion("holderCategoryId", holderCategory, true, false));
        }

        public void setHolderClassCriterion(String holderClass) {
            setSearchCriterion(new SearchCriterion("holderClassId", holderClass, true, false));
        }

        public void setHolderTypeCriterion(String holderType) {
            setSearchCriterion(new SearchCriterion("holderType", holderType, true, true));
        }

        public void setInvoiceCriterion(String invoiceNumber) {
            setSearchCriterion(new SearchCriterion("invoiceNumber", invoiceNumber, false, false));
        }

        public void setInvoiceRangeCriterion(String invoiceNumberFrom, String invoiceNumberTo) {
            setSearchCriterion(new SearchCriterion("invoiceNumberFrom", invoiceNumberFrom, false, false));
            setSearchCriterion(new SearchCriterion("invoiceNumberTo", invoiceNumberTo, false, false));
        }

        public void setShowAllCriterion() {
            setSearchCriterion(new ShowAllSearchCriterion());
        }

        public void setTransactionDateRangeCriterion(Date transactionDateFrom, Date transactionDateTo) {
            setSearchCriterion(new DateSearchCriterion("transactionDateFrom", transactionDateFrom));
            setSearchCriterion(new DateSearchCriterion("transactionDateTo", transactionDateTo));
        }

        public void setNicHandleCriterion(User user) {
            setSearchCriterion(new SearchCriterion("nicHandleId", user.login, true, true));
        }

        public void setNrpStatusCriterion(String nrpStatus) {
            setSearchCriterion(new SearchCriterion("nrpStatus", nrpStatus, true, true));
        }

        public void setRegistrarNameCiterion(String name) {
            setSearchCriterion(new SearchCriterion("accountId", name, true, false));
        }

        public void setRegistrationRangeCriterion(Date registrationFrom, Date registrationTo) {
            setSearchCriterion(new DateSearchCriterion("registrationFrom", registrationFrom));
            setSearchCriterion(new DateSearchCriterion("registrationTo", registrationTo));
        }

        public void setRenewalModeCriterion(String renewalMode) {
            setSearchCriterion(new SearchCriterion("renewalMode", renewalMode, true, true));
        }

        protected List<WebElement> getRows() {
            return wd().findElements(By.cssSelector(".result > tbody > tr"));
        }

        protected List<String> getExpectedExportOptions() {
            return Arrays.asList("CSV", "XML");
        }

        private List<String> getVisibleExportOptions() {
            List<WebElement> options = wd().findElements(By.cssSelector(".exportlinks .export"));
            List<String> names = new ArrayList<String>();
            for (WebElement option : options) {
                names.add(option.getText().trim());
            }
            return names;
        }

        protected List<String> getVisibleColumns() {
            List<WebElement> columns = wd().findElements(By.cssSelector(".result thead th"));
            List<String> names = new ArrayList<String>();
            for (WebElement column : columns) {
                names.add(column.getText().trim());
            }
            return names;
        }

        protected int getColumnIndex(String name) {
            return visibleColumns.indexOf(name) + 1;
        }

        protected String getColumnValue(WebElement row, String name) {
            int index = getColumnIndex(name);
            WebElement cell = row.findElement(By.cssSelector(String.format("td:nth-child(%s)", index)));
            return cell.getText();
        }

        protected void checkSearchCriteriaPresent(List<String> searchCriteria, List<String> dateSearchCriteria,
                boolean showAllPresent) {
            for (String crit : searchCriteria) {
                crsweb().assertElementPresent(getSearchCriteriaElement(crit));
            }
            for (String crit : dateSearchCriteria) {
                crsweb().assertElementPresent(getDateSearchCriteriaElement(crit));
            }
            By showAll = getShowAllSearchCriteriaElement();
            if (showAllPresent) {
                crsweb().assertElementPresent(showAll);
            } else {
                crsweb().assertElementNotPresent(showAll);
            }
        }

        protected By getSearchCriteriaElement(String crit) {
            return By.id(String.format("%s-search_searchCriteria_%s", prefix, crit));
        }

        protected By getDateSearchCriteriaElement(String crit) {
            return By.id(String.format("jscal_input_searchCriteria_%s", crit));
        }

        protected By getShowAllSearchCriteriaElement() {
            return By.id(String.format("%s-search_showAll", prefix));
        }

        protected void clickSubmitButton() {
            crsweb().clickElement(By.id(String.format("%s-search_0", prefix)));
        }

        protected void setSearchCriterion(BaseSearchCriterion sc) {
            searchCriteria.add(sc);
        }

        private void fillSearchCriteria() {
            for (BaseSearchCriterion sc : searchCriteria) {
                sc.fill();
            }
            if (!searchCriteria.isEmpty()) {
                clickSubmitButton();
            }
        }

        protected abstract class BaseSearchCriterion {
            public abstract void fill();
        }

        protected class SearchCriterion extends BaseSearchCriterion {
            public String fieldName;
            public String value;
            private boolean isSelectable;
            private boolean selectByValue;

            public SearchCriterion(String fieldName, String value, boolean isSelectable, boolean selectByValue) {
                this.fieldName = fieldName;
                this.value = value;
                this.isSelectable = isSelectable;
                this.selectByValue = selectByValue;
            }

            public void fill() {
                By element = getSearchCriteriaElement(fieldName);
                if (isSelectable) {
                    if (selectByValue) {
                        crsweb().selectOptionByValue(element, value);
                    } else {
                        crsweb().selectOptionByText(element, value);
                    }
                } else {
                    crsweb().fillInput(element, value);
                }
            }
        }

        protected class DateSearchCriterion extends BaseSearchCriterion {
            public String fieldName;
            public String value;

            public DateSearchCriterion(String fieldName, Date date) {
                this.fieldName = fieldName;
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                this.value = df.format(date);
            }

            public void fill() {
                By element = getDateSearchCriteriaElement(fieldName);
                crsweb().fillInputAndTriggerChangeAndBlurEvents(element, value);
            }
        }

        protected class ShowAllSearchCriterion extends BaseSearchCriterion {

            public void fill() {
                By element = getShowAllSearchCriteriaElement();
                crsweb().selectElement(element);
            }
        }

    }

    private abstract class RegistrationCountingReport extends Report {
        private List<Row> totalDomains;
        private String registrarColumnName;

        public RegistrationCountingReport(String prefix, String registrarColumnName) {
            super(prefix);
            this.registrarColumnName = registrarColumnName;
            totalDomains = new ArrayList<Row>();
        }

        public void saveSearchResults() {
            totalDomains.clear();
            for (WebElement row : getRows()) {
                String registrar = getColumnValue(row, registrarColumnName);
                String holderClass = getColumnValue(row, "Class");
                String holderCategory = getColumnValue(row, "Category");
                int count = Integer.valueOf(getColumnValue(row, "Domains Count"));
                totalDomains.add(new Row(registrar, holderClass, holderCategory, count));
            }
        }

        public int getTotalDomains(String registrar, String holderClass, String holderCategory) {
            int count = 0;
            for (Row row : totalDomains) {
                if (registrar.equals(row.registrar) && holderClass.equals(row.holderClass)
                        && holderCategory.equals(row.holderCategory)) {
                    count += row.count;
                }
            }
            if (count == 0) {
                // Rows with 0 domains are not listed.
                throw new RuntimeException(String.format("No matching row found for (%s, %s, %s) in %s", registrar,
                        holderClass, holderCategory, totalDomains));
            }
            return count;
        }

        private class Row {
            public String registrar;
            public String holderClass;
            public String holderCategory;
            public int count;

            public Row(String registrar, String holderClass, String holderCategory, int count) {
                this.registrar = registrar;
                this.holderClass = holderClass;
                this.holderCategory = holderCategory;
                this.count = count;
            }

            public String toString() {
                return String.format("(%s, %s, %s)", registrar, holderClass, holderCategory);
            }
        }

    }

    private class DepositReport extends Report {
        public float actualBalance;

        public DepositReport() {
            super("depositAccount");
        }

        public void saveSearchResults() {
            actualBalance = getActualBalance();
        }

        public void view() {
            crsweb().view(SiteId.ReportDeposit);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("accountBillNH", "nicHandleId");
            checkSearchCriteriaPresent(searchCriteria, new ArrayList<String>(), true);
        }

        public List<String> getExpectedColumns() {
            List<String> columns = Arrays.asList("Nic Handle Id", "Name", "Actual Balance", "View Tx History",
                    "Reserved Funds", "Available Balance");
            return columns;
        }

        public void checkReportSpecific() {
            TransactionsHistoryReport report = new TransactionsHistoryReport();
            testReport(report);
        }

        private float getActualBalance() {
            WebElement row = getRows().get(0);
            String balance = getColumnValue(row, "Actual Balance");
            return Float.valueOf(balance.replace(",", ""));
        }

    }

    private class TransactionsHistoryReport extends Report {

        public TransactionsHistoryReport() {
            super("transactionsHistReport");
        }

        public void view() {
            crsweb().clickElement(By.cssSelector("img[title='View Transactions History']"));
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("transactionType", "remark");
            List<String> dateSearchCriteria = Arrays.asList("transactionDateFrom", "transactionDateTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Date", "Order Id", "Remark", "Transaction Type", "Transaction Amount",
                    "Actual Balance");
        }

    }

    private class DoaReport extends Report {

        private Map<String, Row> rows;

        public DoaReport() {
            super("viewDOA");
            rows = new HashMap<String, Row>();
        }

        public void view() {
            crsweb().view(SiteId.ReportDoa);
        }

        public void saveSearchResults() {
            for (WebElement tr : getRows()) {
                String orderId = getColumnValue(tr, "Order Id");
                String billCNic = getColumnValue(tr, "Bill-C Nic");
                String staffNic = getColumnValue(tr, "IEDR Staff Nic");
                String amountStr = getColumnValue(tr, "DOA Amount Lodged");
                float amount = Float.valueOf(amountStr.replace(",", ""));
                String remark = getColumnValue(tr, "Remark");
                rows.put(orderId, new Row(orderId, billCNic, staffNic, amount, remark));
            }
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("accountBillNH", "nicHandleId", "correctorNH");
            List<String> dateSearchCriteria = Arrays.asList("transactionDateFrom", "transactionDateTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        public Row getRow(String orderId) {
            return rows.get(orderId);
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Lodgment Date", "Bill-C Nic", "IEDR Staff Nic", "DOA Amount Lodged", "Order Id",
                    "Remark");
        }

        public class Row {
            public String orderId;
            public String billCNic;
            public String staffNic;
            public float amount;
            public String remark;

            public Row(String orderId, String billCNic, String staffNic, float amount, String remark) {
                this.orderId = orderId;
                this.billCNic = billCNic;
                this.staffNic = staffNic;
                this.amount = amount;
                this.remark = remark;
            }
        }

    }

    private class InvoicesReport extends Report {

        public List<Invoice> invoices;

        public InvoicesReport() {
            super("viewInvoices");
        }

        protected void saveSearchResults() {
            invoices = getInvoices();
        }

        public void view() {
            crsweb().view(SiteId.ReportInvoices);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("paymentMethod", "customerType", "accountName", "billingNH",
                    "invoiceNumberFrom", "invoiceNumberTo");
            List<String> dateSearchCriteria = Arrays.asList("settledFrom", "settledTo", "invoiceDateFrom",
                    "invoiceDateTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        public void setBillingNhCriterion(User user) {
            setSearchCriterion(new SearchCriterion("billingNH", user.login, true, true));
        }

        public void toggleInvoices(final List<String> invoiceNames) {
            int currentCount = getSelectedInvoicesCount();
            int expectedCount = currentCount;
            for (String invoiceName : invoiceNames) {
                By inputBy = By.cssSelector(String.format("input[checkbox-list-value='%s']", invoiceName));
                WebElement input = wd().findElement(inputBy);
                expectedCount = input.isSelected() ? expectedCount - 1 : expectedCount + 1;
                crsweb().clickElement(input);
            }
            waitForSelectedInvoicesCount(expectedCount);
        }

        private void waitForSelectedInvoicesCount(final int expectedCount) {
            new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver webDriver) {
                    return getSelectedInvoicesCount() == expectedCount;
                }
            });
        }

        public void toggleSelectAll() {
            final int currentCount = getSelectedInvoicesCount();
            By selectAllBy = By.cssSelector(".checkbox-list-selectall-placeholder > input");
            crsweb().clickElement(selectAllBy);
            new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver webDriver) {
                    return getSelectedInvoicesCount() != currentCount;
                }
            });
        }

        public void clearAll() {
            crsweb().clickElement(By.className("clear-button"));
            waitForSelectedInvoicesCount(0);
        }

        public List<Invoice> getInvoices() {
            List<Invoice> invoices = new ArrayList<Invoice>();
            for (WebElement row : getRows()) {
                invoices.add(new Invoice(row));
            }
            return invoices;
        }

        public List<String> getInvoiceNumbers() {
            List<Invoice> invoices = getInvoices();
            List<String> invoiceNumbers = new ArrayList<String>();
            for (Invoice invoice : invoices) {
                invoiceNumbers.add(invoice.number);
            }
            return invoiceNumbers;
        }

        public int getSelectedInvoicesCount() {
            String text = wd().findElement(By.cssSelector(".checkbox-list-container > p")).getText();
            if ("You haven't selected any invoices to print".equals(text)) {
                return 0;
            } else {
                Pattern p = Pattern.compile("^You have selected (\\d+) invoices");
                Matcher m = p.matcher(text);
                if (m.find()) {
                    return Integer.valueOf(m.group(1));
                } else {
                    throw new RuntimeException(String.format("Unexpected format: %s", text));
                }
            }
        }

        public List<String> getInvoicesSelectedOnCurrentPage() {
            List<WebElement> selected = wd().findElements(By.cssSelector("input.print-checkbox:checked"));
            List<String> numbers = new ArrayList<String>();
            for (WebElement checkbox : selected) {
                numbers.add(checkbox.getAttribute("checkbox-list-value"));
            }
            return numbers;
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("", "Invoice Number", "Invoice Date", "Settlement Date", "Bill-C Nic", "Bill-C Name",
                    "Invoice Amount", "Net Amount", "Vat Amount", "Invoice Info", "Pdf");
        }

        public class Invoice {
            public String number;
            public String billCNic;
            public float netAmount;
            public float vatAmount;

            public Invoice(WebElement tr) {
                number = getColumnValue(tr, "Invoice Number");
                billCNic = getColumnValue(tr, "Bill-C Nic");
                netAmount = Float.valueOf(getColumnValue(tr, "Net Amount"));
                vatAmount = Float.valueOf(getColumnValue(tr, "Vat Amount"));
            }
        }

    }

    private class RevenueAssuranceReport extends Report {

        public List<ExtendedReservation> invoices;

        public RevenueAssuranceReport() {
            super("viewExtendedReservations");
        }

        protected void saveSearchResults() {
            invoices = getExtendedReservations();
        }

        public void view() {
            crsweb().view(SiteId.ReportRevenueAssurance);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("invoiceNumber", "invoiceNumberFrom", "invoiceNumberTo");
            List<String> dateSearchCriteria = Arrays.asList("settledFrom", "settledTo", "invoiceDateFrom",
                    "invoiceDateTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        private List<ExtendedReservation> getExtendedReservations() {
            List<ExtendedReservation> invoices = new ArrayList<ExtendedReservation>();
            for (WebElement row : getRows()) {
                invoices.add(new ExtendedReservation(row));
            }
            return invoices;
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Invoice Number", "Domain Name", "Bill-C Nic", "Bill-C Name", "Payment Method",
                    "Customer Type", "Operation Type", "Settlement Date", "Invoice Date", "Start Date",
                    "Duration Months", "Renewal Date", "Domain Base", "Domain Vat", "Domain Gross");
        }

        private class ExtendedReservation {
            public String number;
            public String domainName;
            public String billCNic;
            public PaymentMethod method;
            public int durationMonths;
            public float netAmount;
            public float vatAmount;

            public ExtendedReservation(WebElement tr) {
                number = getColumnValue(tr, "Invoice Number");
                domainName = getColumnValue(tr, "Domain Name");
                billCNic = getColumnValue(tr, "Bill-C Nic");
                String paymentMethod = getColumnValue(tr, "Payment Method");
                method = PaymentMethod.valueOf("CC".equals(paymentMethod) ? "CARD" : paymentMethod);
                durationMonths = Integer.valueOf(getColumnValue(tr, "Duration Months"));
                netAmount = Float.valueOf(getColumnValue(tr, "Domain Base"));
                vatAmount = Float.valueOf(getColumnValue(tr, "Domain Vat"));
            }
        }

    }

    private class BankReconciliationReport extends Report {

        public List<Invoice> invoices;

        public BankReconciliationReport() {
            super("bankReceipts");
        }

        protected void saveSearchResults() {
            invoices = getInvoices();
        }

        public void view() {
            crsweb().view(SiteId.ReportBankReconciliation);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("invoiceNumberFrom", "invoiceNumberTo");
            List<String> dateSearchCriteria = Arrays.asList("invoiceDateFrom", "invoiceDateTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        private List<Invoice> getInvoices() {
            List<Invoice> invoices = new ArrayList<Invoice>();
            for (WebElement row : getRows()) {
                invoices.add(new Invoice(row));
            }
            return invoices;
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Invoice Date", "Bill-C Nic", "Bill-C Name", "Domain Name", "Invoice Number",
                    "Order ID", "Total Value");
        }

        private class Invoice {
            public String billCNic;
            public String domainName;
            public String number;
            public String orderId;
            public float amount;

            public Invoice(WebElement tr) {
                billCNic = getColumnValue(tr, "Bill-C Nic");
                domainName = getColumnValue(tr, "Domain Name");
                number = getColumnValue(tr, "Invoice Number");
                orderId = getColumnValue(tr, "Order ID");
                amount = Float.valueOf(getColumnValue(tr, "Total Value"));
            }
        }

    }

    private class NrpDomainsReport extends Report {

        public List<String> domains;

        public NrpDomainsReport() {
            super("nrpDomains");
        }

        protected void saveSearchResults() {
            domains = getDomainNames();
        }

        public void view() {
            crsweb().view(SiteId.ReportNrpDomains);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("domainName", "nrpStatus", "holderType", "renewalMode", "type",
                    "accountId", "billingNH");
            List<String> dateSearchCriteria = Arrays.asList("suspensionFrom", "suspensionTo", "deletionFrom", "deletionTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        private List<String> getDomainNames() {
            List<String> domains = new ArrayList<String>();
            for (WebElement row : getRows()) {
                domains.add(getColumnValue(row, "Domain Name"));
            }
            return domains;
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Domain Name", "BillC NH", "BillC Name", "Holder Name", "Class", "Category",
                    "Country", "County", "NRP Status", "DSM State", "Locked", "Registration Date", "Renewal Date",
                    "Suspension Date", "Deletion Date");
        }

    }

    private class CharityDomainsReport extends Report {

        List<String> domains;

        public CharityDomainsReport() {
            super("charityDomains");
        }

        protected void saveSearchResults() {
            domains = getDomainNames();
        }

        public void view() {
            crsweb().view(SiteId.ReportCharityDomains);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("domainName", "nrpStatus", "type", "accountId", "billingNH");
            List<String> dateSearchCriteria = Arrays.asList("registrationFrom", "registrationTo", "renewalFrom",
                    "renewalTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        private List<String> getDomainNames() {
            List<String> domains = new ArrayList<String>();
            for (WebElement row : getRows()) {
                domains.add(getColumnValue(row, "Domain Name"));
            }
            return domains;
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Domain Name", "BillC NH", "BillC Name", "NRP Status", "Registration Date",
                    "Renewal Date", "Suspension Date", "Deletion Date");
        }

    }

    private class TotalDomainsReport extends Report {

        private Map<String, Integer> totalDomains;

        public TotalDomainsReport() {
            super(null);
            totalDomains = new HashMap<String, Integer>();
        }

        public void saveSearchResults() {
            totalDomains.clear();
            for (WebElement row : getRows()) {
                String registrar = getColumnValue(row, "Registrar");
                int count = Integer.valueOf(getColumnValue(row, "Domains Count"));
                totalDomains.put(registrar, count);
            }
        }

        public void view() {
            crsweb().view(SiteId.ReportTotalDomains);
        }

        public void checkSearchCriteria() {

        }

        public int getTotalDomains(String registrar) {
            return totalDomains.get(registrar);
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Registrar", "Name", "Domains Count");
        }

    }

    private class RegistrationsReport extends RegistrationCountingReport {

        public RegistrationsReport() {
            super("totalDomainsPerDateReport", "Registrar");
        }

        public void view() {
            crsweb().view(SiteId.ReportRegistrations);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("accountId", "holderClassId", "holderCategoryId", "customerType",
                    "reportTimeGranulation", "reportTypeGranulation");
            List<String> dateSearchCriteria = Arrays.asList("registrationFrom", "registrationTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Registrar", "Name", "Class", "Category", "Domains Count", "", "Date");
        }

    }

    private class PerClassReport extends RegistrationCountingReport {

        public PerClassReport() {
            super("domainsPerClassReport", "Bill-C Nic");
        }

        public void view() {
            crsweb().view(SiteId.ReportPerClass);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("holderClassId", "holderCategoryId", "accountId", "billingNH");
            List<String> dateSearchCriteria = Arrays.asList("from", "to");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, false);
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Class", "Category", "Domains Count", "Account Name", "Bill-C Nic");
        }

    }

    private class DeletedDomainsReport extends Report {

        public int deleted;

        public DeletedDomainsReport() {
            super("deleted");
        }

        public void saveSearchResults() {
            deleted = getRows().size();
        }

        public void view() {
            crsweb().view(SiteId.ReportDeletedDomains);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("domainName", "type", "accountId", "billingNH");
            List<String> dateSearchCriteria = Arrays.asList("registrationFrom", "registrationTo", "renewalFrom",
                    "renewalTo", "deletionFrom", "deletionTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Domain Name", "BillC NH", "BillC Name", "Holder Name", "Class", "Category",
                    "Country", "County", "Registration Date", "Renewal Date", "Deletion Date");
        }

    }

    private class AutorenewsReport extends Report {

        List<String> autorenews;

        public AutorenewsReport() {
            super("autorenews");
        }

        public void saveSearchResults() {
            autorenews = new ArrayList<String>();
            for (WebElement row : getRows()) {
                autorenews.add(getColumnValue(row, "Domain Name"));
            }
        }

        public void view() {
            crsweb().view(SiteId.ReportAutorenews);
        }

        public void checkSearchCriteria() {
            List<String> searchCriteria = Arrays.asList("domainName", "billingNH", "renewalMode");
            List<String> dateSearchCriteria = Arrays.asList("renewalFrom", "renewalTo");
            checkSearchCriteriaPresent(searchCriteria, dateSearchCriteria, true);
        }

        public void setBillingNhCriterion(User user) {
            setSearchCriterion(new SearchCriterion("billingNH", user.login, false, false));
        }

        protected List<String> getExpectedColumns() {
            return Arrays.asList("Domain Name", "BillC NH", "BillC Name", "Autorenew Status", "Renewal Date");
        }

    }

}
