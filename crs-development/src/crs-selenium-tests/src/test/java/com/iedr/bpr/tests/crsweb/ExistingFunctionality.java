package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.pages.crsweb.DocumentsPage;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.pages.crsweb.ViewDomainPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.FileDownloader;
import com.iedr.bpr.tests.utils.IncomingDocs;
import com.iedr.bpr.tests.utils.IncomingDocs.DocumentType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;
import com.iedr.bpr.tests.utils.email.DetailedExpectedEmailSummary;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary;
import com.jcraft.jsch.JSchException;

import static com.iedr.bpr.tests.TestEnvironment.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExistingFunctionality extends SeleniumTest {

    private Map<String, String> initialAppConfig;
    private IncomingDocs incomingDocs;

    public ExistingFunctionality(Browser browser) {
        super(browser);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        incomingDocs = new IncomingDocs(config.getProperty("documents_dir_path"));
        initialAppConfig = incomingDocs.getCurrentAppConfig();
    }

    @Override
    public void tearDown() throws Exception {
        try {
            restoreAppConfig();
            incomingDocs.removeCreatedDocDirs();
        } finally {
            super.tearDown();
        }
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/ef_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/ef_data.sql";
    }

    @Test
    public void test_ef_nosc01() {
        // Ticket History view
        String domain = "ef-nosc01.ie";
        crsweb().login(this.internal);
        viewHistoricalTicket(domain);
    }

    @Test
    public void test_ef_nosc02() {
        // Domain History view
        String activeDomain = "ef-nosc02a.ie";
        String deletedDomain = "ef-nosc02b.ie";
        crsweb().login(this.internal);
        viewHistoricalDomain(activeDomain, true);
        viewHistoricalDomain(deletedDomain, false);
    }

    @Test
    public void test_ef_nosc03()
            throws IOException, JSchException, SQLException, KeyManagementException, NullPointerException,
            NoSuchAlgorithmException, KeyStoreException, URISyntaxException {
        // Documents Search
        String domain = "ef-nosc03.ie";
        String newFileName = UUID.randomUUID().toString() + ".pdf";
        String fileContent = UUID.randomUUID().toString();
        String tempDirName = incomingDocs.populateDocDirs(domain, newFileName, fileContent);
        incomingDocs.setIncomingDocsDirectory(tempDirName, initialAppConfig);
        TestDocuments td = new TestDocuments();
        td.checkAssignedDocuments(domain, fileContent);
    }

    @Test
    public void test_ef_nosc04()
            throws IOException, JSchException, SQLException, KeyManagementException, NullPointerException,
            NoSuchAlgorithmException, KeyStoreException, URISyntaxException {
        // Documents New
        User user = this.registrar;
        String domain = "ef-nosc04.ie";
        String newFileName = UUID.randomUUID().toString() + ".pdf";
        String fileContent = UUID.randomUUID().toString();
        String tempDirName = incomingDocs.populateDocDirs(domain, newFileName, fileContent);
        String source = String.format("Source for %s", domain);
        incomingDocs.setIncomingDocsDirectory(tempDirName, initialAppConfig);
        TestDocuments td = new TestDocuments();
        td.checkAssignedDocumentsInDb(domain, 0);
        td.assignNewDocuments(domain, newFileName, source);
        td.checkAssignedDocumentsInDb(domain, 3);
        td.checkAssignedDocuments(domain, fileContent);
        for (int i = 0; i < 3; i++) {
            emails.add(new DetailedExpectedEmailSummary.MatchEverything(emailSummaryGenerator
                    .getDocumentationReceivedEmail(user, domain, source)));
        }
        checkAndResetEmails(emails);
    }

    @Test
    public void test_ef_nosc05() {
        // Domain Search
        List<DomainSearchCriterion> criteria = Arrays.asList(new NameDomainSearchCriterion("ef-nosc05.ie"),
                new HolderDomainSearchCriterion("Modified Holder"), new ContactDomainSearchCriterion("EFAB-IEDR"),
                new AccountDomainSearchCriterion("603"), new ClassDomainSearchCriterion("2"),
                new CategoryDomainSearchCriterion("5"), new StatusDomainSearchCriterion("VS"),
                new HolderTypeDomainSearchCriterion("C"),
                new RenewalDateDomainSearchCriterion(new LocalDate().minusDays(10)),
                new RegistrationDateDomainSearchCriterion(new LocalDate().minusDays(10)));
        TestDomainSearchCriterion tdsc = new TestDomainSearchCriterion("ef-nosc05", 5);
        crsweb().login(this.internal);
        tdsc.testAllDomains();
        for (DomainSearchCriterion crit : criteria) {
            tdsc.test(crit);
        }
    }

    @Test
    public void test_ef_nosc06() throws SQLException {
        // Triple-PASS Modification ticket - MOD Ticket by BillC Triple-Passed
        // (Direct a/c)
        User user = this.direct;
        User billC = user;
        User adminC = new User("EFAA-IEDR", "Passw0rd!", false, "ef_aa@iedr.ie");
        String domain = "ef-nosc06.ie";
        emails.add(getModificationSuccessfulEmail(user, adminC, billC, domain));
        TestTriplePassModificationTicket ttpmt = new TestTriplePassModificationTicket();
        ttpmt.test(user, domain, ContactType.BILL);
    }

    @Test
    public void test_ef_nosc07() throws SQLException {
        // Triple-PASS Modification ticket - MOD Ticket by BillC Triple-Passed
        // (Registrar a/c)
        User user = this.registrar;
        User billC = user;
        User adminC = new User("EFAB-IEDR", "Passw0rd!", false, "ef_ab@iedr.ie");
        String domain = "ef-nosc07.ie";
        emails.add(getModificationSuccessfulEmail(user, adminC, billC, domain));
        TestTriplePassModificationTicket ttpmt = new TestTriplePassModificationTicket();
        ttpmt.test(user, domain, ContactType.BILL);
    }

    @Test
    public void test_ef_nosc08() throws SQLException {
        // Triple-PASS Modification ticket - MOD Ticket by AdminC Triple-Passed
        // (Direct a/c)
        User user = new User("EFAA-IEDR", "Passw0rd!", false, "ef_aa@iedr.ie");
        User billC = this.direct;
        User adminC = user;
        String domain = "ef-nosc08.ie";
        emails.add(getModificationSuccessfulEmail(user, adminC, billC, domain));
        TestTriplePassModificationTicket ttpmt = new TestTriplePassModificationTicket();
        ttpmt.test(user, domain, ContactType.ADMIN);
    }

    @Test
    public void test_ef_nosc09() throws SQLException {
        // Triple-PASS Modification ticket - MOD Ticket by AdminC Triple-Passed
        // (Registrar a/c)
        User user = new User("EFAB-IEDR", "Passw0rd!", false, "ef_ab@iedr.ie");
        User billC = this.registrar;
        User adminC = user;
        String domain = "ef-nosc09.ie";
        emails.add(getModificationSuccessfulEmail(user, adminC, billC, domain));
        TestTriplePassModificationTicket ttpmt = new TestTriplePassModificationTicket();
        ttpmt.test(user, domain, ContactType.ADMIN);
    }

    @Test
    public void test_ef_nosc10()
            throws IOException, JSchException, SQLException, KeyManagementException, NullPointerException,
            NoSuchAlgorithmException, KeyStoreException, URISyntaxException {
        // Documents - De-Compress logged documents
        String domain = "ef-nosc10.ie";
        String newFileName = UUID.randomUUID().toString() + ".pdf";
        String fileContent = UUID.randomUUID().toString();
        String tempDirName = incomingDocs.populateDocDirs(domain, newFileName, fileContent);
        incomingDocs.setIncomingDocsDirectory(tempDirName, initialAppConfig);
        TestDocuments td = new TestDocuments();
        td.checkAssignedDocumentsInDb(domain, 0);
        td.assignNewDocuments(domain, newFileName, "Source");
        td.checkAssignedDocumentsInDb(domain, 3);
        td.checkAssignedDocuments(domain, fileContent);
        td.compressAssignedDocuments(domain);
        td.checkAssignedDocuments(domain, fileContent);
    }

    @Test
    public void test_ef_nosc11() throws IOException, JSchException, SQLException {
        // Add additional domain to previously assigned docs
        String domainNameA = "ef-nosc11-a.ie";
        String domainNameB = "ef-nosc11-b.ie";
        String newFileName = UUID.randomUUID().toString() + ".pdf";
        String fileContent = UUID.randomUUID().toString();
        String tempDirName = incomingDocs.populateDocDirs(domainNameA, newFileName, fileContent);
        incomingDocs.setIncomingDocsDirectory(tempDirName, initialAppConfig);
        DocumentsPage dp = new DocumentsPage(internal);
        dp.viewDocumentsList(domainNameA, true);
        dp.editDomainNamesForDocument(DocumentType.ATTACHMENT_ASSIGNED, domainNameA);
        wd().findElement(By.id("documents-nameUpdate_document_domainNames")).sendKeys(", " + domainNameB);
        crsweb().clickElement(By.id("documents-nameUpdate_0"));
        crsweb().waitForElementPresent(
                By.xpath(String.format("//td[contains(@class, 'documents-name') and .='%s, %s']", domainNameA,
                        domainNameB)));
    }

    @Test
    public void test_ef_nosc12() throws Exception {
        String domainName = "ef-nosc12.ie";
        crsweb().login(internal);
        ViewDomainPage vdp = new ViewDomainPage();
        List<ViewDomainPage.HistoricalDomainRow> rows = vdp.getHistoricalRows(domainName);
        List<String> expectedRemarks = Arrays.asList("Current change", "Hist change #1 from top",
                "Hist change #2 from top", "Hist change #3 from top", "Hist change #4 from top");
        List<String> remarks = new ArrayList<>();
        for (ViewDomainPage.HistoricalDomainRow row : rows) {
            remarks.add(row.remark);
        }
        assertEquals(expectedRemarks, remarks);

    }

    private class TestDomainSearchCriterion {
        private String prefix;
        private int allDomainsCount;

        public TestDomainSearchCriterion(String prefix, int allDomainsCount) {
            this.prefix = prefix;
            this.allDomainsCount = allDomainsCount;
        }

        public void testAllDomains() {
            crsweb().view(SiteId.DomainsSearch);
            filterBasedOnPrefix();
            assertEquals(allDomainsCount, getResultsCount());
        }

        public void test(DomainSearchCriterion crit) {
            crsweb().view(SiteId.DomainsSearch);
            crit.apply(prefix);
            assertEquals(crit.toString(), 1, getResultsCount());
        }

        private void filterBasedOnPrefix() {
            NameDomainSearchCriterion name = new NameDomainSearchCriterion(prefix);
            name.apply(prefix);
        }

        private int getResultsCount() {
            if (!crsweb().isElementPresentInstantaneously(By.className("result"))) {
                return 0;
            }
            String text = wd().findElement(By.className("pagebanner")).getText();
            String strCount = text.replaceAll("^([\\w\\d]+) items? found.*$", "$1");
            int count = "One".equals(strCount) ? 1 : Integer.valueOf(strCount);
            return count;
        }

    }

    private abstract class DomainSearchCriterion {
        protected String fieldId;
        protected String value;

        public abstract void fill();

        public DomainSearchCriterion(String fieldId, String value) {
            this.fieldId = fieldId;
            this.value = value;
        }

        public void apply(String prefix) {
            NameDomainSearchCriterion name = new NameDomainSearchCriterion(prefix);
            name.fill();
            fill();
            submit();
            waitForCriterionApplied();
        }

        public void submit() {
            crsweb().clickElement(By.id("domains-search_0"));

        }

        protected void waitForCriterionApplied() {
            new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
                public boolean apply(WebDriver driver) {
                    String fieldValue = driver.findElement(By.id(fieldId)).getAttribute("value");
                    return !value.equals(fieldValue);
                }
            });
        }

        public String toString() {
            return String.format("Criterion: %s", this.getClass().getName());
        }
    }

    private abstract class InputDomainSearchCriterion extends DomainSearchCriterion {

        public InputDomainSearchCriterion(String fieldId, String value) {
            super(fieldId, value);
        }

        public void fill() {
            crsweb().fillInput(By.id(fieldId), value);
        }

    }

    private abstract class SelectDomainSearchCriterion extends DomainSearchCriterion {

        public SelectDomainSearchCriterion(String fieldId, String value) {
            super(fieldId, value);
        }

        public void fill() {
            crsweb().selectOptionByValue(By.id(fieldId), value);
        }

    }

    private abstract class DateDomainSearchCriterion extends DomainSearchCriterion {
        private String fromFieldId;
        private String toFieldId;

        public DateDomainSearchCriterion(String fromFieldId, String toFieldId, LocalDate date) {
            super(null, DateTimeFormat.forPattern("dd/MM/yyyy").print(date));
            this.fromFieldId = fromFieldId;
            this.toFieldId = toFieldId;
        }

        public void fill() {
            crsweb().fillInputAndTriggerChangeAndBlurEvents(By.id(fromFieldId), value);
            crsweb().fillInputAndTriggerChangeAndBlurEvents(By.id(toFieldId), value);
        }

        protected void waitForCriterionApplied() {
            new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
                public boolean apply(WebDriver driver) {
                    String fromValue = driver.findElement(By.id(fromFieldId)).getAttribute("value");
                    String toValue = driver.findElement(By.id(toFieldId)).getAttribute("value");
                    return !value.equals(fromValue) && !value.equals(toValue);
                }
            });
        }

    }

    private class NameDomainSearchCriterion extends InputDomainSearchCriterion {

        public NameDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_domainName", value);
        }

    }

    private class HolderDomainSearchCriterion extends InputDomainSearchCriterion {

        public HolderDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_domainHolder", value);
        }

    }

    private class ContactDomainSearchCriterion extends InputDomainSearchCriterion {

        public ContactDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_nicHandle", value);
        }

    }

    private class AccountDomainSearchCriterion extends SelectDomainSearchCriterion {

        public AccountDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_accountId", value);
        }

    }

    private class ClassDomainSearchCriterion extends SelectDomainSearchCriterion {

        public ClassDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_holderClassId", value);
        }

    }

    private class CategoryDomainSearchCriterion extends SelectDomainSearchCriterion {

        public CategoryDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_holderCategoryId", value);
        }

    }

    private class StatusDomainSearchCriterion extends SelectDomainSearchCriterion {

        public StatusDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_nrpStatus", value);
        }

    }

    private class HolderTypeDomainSearchCriterion extends SelectDomainSearchCriterion {

        public HolderTypeDomainSearchCriterion(String value) {
            super("domains-search_searchCriteria_holderType", value);
        }

    }

    private class RenewalDateDomainSearchCriterion extends DateDomainSearchCriterion {

        public RenewalDateDomainSearchCriterion(LocalDate date) {
            super("jscal_input_searchCriteria_renewalFrom", "jscal_input_searchCriteria_renewalTo", date);
        }

    }

    private class RegistrationDateDomainSearchCriterion extends DateDomainSearchCriterion {

        public RegistrationDateDomainSearchCriterion(LocalDate date) {
            super("jscal_input_searchCriteria_registrationFrom", "jscal_input_searchCriteria_registrationTo", date);
        }

    }

    private void restoreAppConfig() throws SQLException {
        incomingDocs.updateAppConfig(initialAppConfig, initialAppConfig);
    }

    private class TestDocuments {

        public void checkAssignedDocuments(String domainName, String expectedContent)
                throws SQLException, KeyManagementException, NullPointerException, NoSuchAlgorithmException,
                KeyStoreException, IOException, URISyntaxException {
            DocumentsPage dp = new DocumentsPage(internal);
            dp.viewDocumentsList(domainName, true);
            List<Integer> docIds = db().getDocumentIdsForDomain(domainName);
            List<WebElement> rows = wd().findElements(By.cssSelector("table.result tbody tr"));
            assertEquals(docIds.size(), rows.size());
            for (DocumentType type : DocumentType.values()) {
                viewDocument(type, expectedContent, dp);
            }
        }

        public void assignNewDocuments(String domainName, String fileName, String source) throws SQLException {
            DocumentsPage dp = new DocumentsPage(internal);
            dp.viewDocumentsList(domainName, false);
            String xpath = String.format("//td[contains(@class, 'documents-name') and .='%s']", fileName);
            assertEquals(DocumentType.newDocuments().size(), wd().findElements(By.xpath(xpath)).size());
            for (DocumentType type : DocumentType.newDocuments()) {
                assignDocument(type, fileName, domainName, source, dp);
            }
            crsweb().assertElementNotPresent(By.xpath(xpath));
        }

        public void checkAssignedDocumentsInDb(String domainName, int expectedCount) throws SQLException {
            List<Integer> docIds = db().getDocumentIdsForDomain(domainName);
            assertEquals(expectedCount, docIds.size());
        }

        public void compressAssignedDocuments(String domainName)
                throws SQLException, NumberFormatException, JSchException, IOException {
            List<Integer> docIds = db().getDocumentIdsForDomain(domainName);
            for (int docId : docIds) {
                String fileName = db().getDocumentName(docId);
                String filePath = incomingDocs.getDocumentLocation(fileName,
                        DocumentType.getType(db().getDocumentType(docId), true));
                assertTrue(isDocumentOnDisk(filePath));
                ssh().crsweb.execute(String.format("gzip %s", filePath), true);
                assertFalse(isDocumentOnDisk(filePath));
                assertTrue(isDocumentOnDisk(filePath + ".gz"));
            }
        }

        public boolean isDocumentOnDisk(String filePath) throws NumberFormatException, JSchException, IOException {
            String command = String.format("find %s -name %s | wc -l", FilenameUtils.getFullPath(filePath),
                    FilenameUtils.getName(filePath));
            int result = Integer.valueOf(ssh().crsweb.execute(command, true));
            return result == 1;
        }

        private void viewDocument(DocumentType type, String expectedContent, DocumentsPage dp)
                throws KeyManagementException, NullPointerException, NoSuchAlgorithmException, KeyStoreException,
                IOException, URISyntaxException {
            dp.viewDocumentFromList(type);
            By fileLinkBy = By.linkText("Unknown document format. Click to download");
            crsweb().assertElementPresent(fileLinkBy);
            WebElement fileLink = wd().findElement(fileLinkBy);
            FileDownloader downloader = new FileDownloader(wd());
            String document = downloader.downloadFile(fileLink.getAttribute("href"));
            assertEquals(expectedContent, document.trim());
            crsweb().clickElement(By.cssSelector("input[value='Back']"));
        }

        private void assignDocument(DocumentType type, String fileName, String domainName, String source,
                DocumentsPage dp) throws SQLException {
            int accountNumber = db().getAccountForDomain(domainName);
            dp.assignDocument(type, fileName, domainName, accountNumber, source);
        }

    }

    private void viewHistoricalTicket(String domainName) {
        crsweb().view(SiteId.TicketsHistory);
        crsweb().fillInput(By.id("ticketshistory-search_searchCriteria_domainName"), domainName);
        crsweb().clickElement(By.id("ticketshistory-search_0"));
        crsweb().clickElement(By.cssSelector("img[title='View Historical Record']"));
        checkPageTitle("Ticket View (History)");
        crsweb().waitForTextPresentOnPage(domainName);
    }

    private void viewHistoricalDomain(String domainName, boolean active) {
        crsweb().view(SiteId.DomainsHistory);
        crsweb().fillInput(By.id("historical-domain-search_searchCriteria_domainName"), domainName);
        crsweb().clickElement(By.id("historical-domain-search_0"));
        crsweb().clickElement(By.cssSelector("img[title='View']"));
        checkPageTitle("Domain View (History)");
        crsweb().waitForTextPresentOnPage(domainName);
        By currentDomainButton = By.id("historical-domain-view_domainview");
        if (active) {
            crsweb().assertElementPresent(currentDomainButton);
        } else {
            crsweb().assertElementNotPresent(currentDomainButton);
        }
    }

    private void checkPageTitle(String title) {
        assertEquals(title, wd().findElement(By.cssSelector("#header p")).getText());
    }

    private class TestTriplePassModificationTicket {
        public void test(User user, String domainName, ContactType type) {
            createModificationTicket(user, domainName, type);
            ViewTicketPage ttp = new ViewTicketPage(internal);
            ttp.triplePassTicketAccept(domainName);
            checkAndResetEmails(emails);
        }

        private void createModificationTicket(User user, String domainName, ContactType type) {
            console().login(user);
            com.iedr.bpr.tests.pages.console.ViewDomainPage vdp = new com.iedr.bpr.tests.pages.console.ViewDomainPage();
            ViewDomainUtils.viewDomain(domainName, type);
            vdp.initModification();
            vdp.holderField.fill("Modified Holder");
            vdp.remarksField.fill("Remark");
            vdp.submitAndWaitForSuccess(domainName, true);
        }
    }

    private ExpectedEmailSummary getModificationSuccessfulEmail(User creator, User adminC, User billC, String domainName)
            throws SQLException {
        return emailSummaryGenerator.getDomainModificationSuccessfulEmail(creator, billC, adminC, domainName);
    }

}
