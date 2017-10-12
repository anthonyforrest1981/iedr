package com.iedr.bpr.tests.crsweb;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.pages.crsweb.ViewTicketPage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UC028 extends SeleniumTest {

    private static final int NODES_COLUMNS = 5;
    private static final String NODE_ID_SEPARATOR = " -> ";
    private static final String EMPTY_VALUE = "";
    private final Map<String, SiteNode> siteMap = getSiteMap();
    private User accessRightTestUser = new User("UC028AA-IEDR", "Passw0rd!");

    public UC028(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/crsweb/uc028_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/crsweb/uc028_data.sql";
    }

    @Test
    public void test_uc028_sc01() throws IOException, SQLException {
        // UC#028: Configure CRS Access Control List - NH_Level
        List<Integer> nhLevels = Arrays.asList(8, 16, 32, 128, 256, 512);
        List<AccessRightError> errors = new ArrayList<AccessRightError>();
        for (int nhLevel : nhLevels) {
            reloadData(true);
            List<AccessRightError> nhLevelErrors = testAccessRights(nhLevel);
            errors.addAll(nhLevelErrors);
        }
        assertTrue(String.format("%s access rights errors:\n%s", errors.size(), StringUtils.join(errors, "\n")),
                errors.isEmpty());
    }

    @Test
    public void test_uc028_sc02() throws SQLException {
        // UC#028: Configure CRS Access Control List - Permissions
        User user = new User("UC028AB-IEDR", "Passw0rd!");
        int nhLevel = 8;
        db().setNicHandleLevel(user.login, nhLevel);
        crsweb().login(this.internal);
        List<String> permissions = Arrays.asList("ticketReportsPermission", "nicHandleEditPermission");
        addPermissions(user, permissions);
        crsweb().logout();
        crsweb().login(user);
        SiteNode ticketsReportsSiteNode = getTicketsReportsSiteNode();
        AccessRightResult trResult = ticketsReportsSiteNode.testAccessRight(AccessRightMode.ALLOWED, nhLevel);
        assertFalse("ticketReportsPermission not added", trResult.hasErrors());
        SiteNode editNicHandleSiteNode = getEditNicHandleSiteNode();
        AccessRightResult enhResult = editNicHandleSiteNode.testAccessRight(AccessRightMode.ALLOWED, db()
                .getNicHandleLevel(user.login));
        assertFalse("nicHandleEditPermission not added", enhResult.hasErrors());
        crsweb().logout();
    }

    private void addPermissions(User user, List<String> permissions) {
        crsweb().viewNicHandle(user.login);
        crsweb().clickElement(By.id("nic-handle-view_nic-handle-access-level_input"));
        crsweb().clickElement(By.id("nic-handle-access-level_nic-handle-access-editPerms-edit"));
        for (String permission : permissions) {
            addPermission(permission);
        }
    }

    private void addPermission(String permission) {
        WebElement tr = wd().findElement(By.xpath(String.format("//td[.='%s']/..", permission)));
        crsweb().clickElement(tr.findElement(By.linkText("Add")));
    }

    private SiteNode getTicketsReportsSiteNode() {
        return getSiteNode(Arrays.asList("Tickets", "Reports"));
    }

    private SiteNode getEditNicHandleSiteNode() {
        return getSiteNode(Arrays.asList("Nic Handles", "Search", "Nic Handle - View", "Edit"));
    }

    private SiteNode getSiteNode(List<String> nodeIds) {
        AccessRight accessRight = new AccessRight(nodeIds, new HashMap<Integer, AccessRightMode>());
        return siteMap.get(accessRight.nodeId);
    }

    private List<AccessRightError> testAccessRights(int nhLevel) throws SQLException, IOException {
        User user = accessRightTestUser;
        db().setNicHandleLevel(user.login, nhLevel);
        List<AccessRight> accessRights = readAccessRights();
        List<AccessRightError> errors = new ArrayList<AccessRightError>();
        crsweb().login(user);

        for (AccessRight accessRight : accessRights) {
            SiteNode node = siteMap.get(accessRight.nodeId);
            if (node == null) {
                throw new RuntimeException(String.format("%s node not expected to be in site map\n%s",
                        accessRight.nodeId, siteMap));
            }
            AccessRightMode mode = accessRight.modes.get(nhLevel);
            AccessRightResult result = node.testAccessRight(mode, nhLevel);
            if (result.hasErrors()) {
                errors.add(result.error);
            }
        }

        crsweb().logout();
        return errors;
    }

    private List<AccessRight> readAccessRights() throws IOException {
        CSVParser parser = CSVParser.parse(UC028.class.getResource("/UC028_AccessRights"), Charset.forName("UTF-8"),
                CSVFormat.DEFAULT);
        Map<Integer, Integer> nhLevelColumns = null;
        List<AccessRight> accessRights = new ArrayList<AccessRight>();
        List<String> previousValues = null;
        for (CSVRecord csvRecord : parser) {
            long lineNumber = parser.getCurrentLineNumber();
            if (lineNumber == 1) {
                nhLevelColumns = getNhLevelColumns(csvRecord);
            } else {
                List<String> currentValues = fillMissingValues(csvRecord, previousValues);
                previousValues = currentValues;
                AccessRight accessRight = getAccessRight(currentValues, nhLevelColumns);
                accessRights.add(accessRight);
            }
        }
        return accessRights;
    }

    private Map<Integer, Integer> getNhLevelColumns(CSVRecord csvRecord) {
        Map<Integer, Integer> columns = new HashMap<Integer, Integer>();
        for (int i = 0; i < csvRecord.size(); i++) {
            if (i >= NODES_COLUMNS) {
                String value = csvRecord.get(i);
                int nhLevel = getNhLevel(value);
                columns.put(i, nhLevel);
            }
        }
        return columns;
    }

    private List<String> fillMissingValues(CSVRecord csvRecord, List<String> previousValues) {
        int i = findFirstNonEmptyIndex(csvRecord);
        List<String> values = new ArrayList<String>();
        for (int j = 0; j < csvRecord.size(); j++) {
            if (j < i && previousValues != null) {
                values.add(previousValues.get(j));
            } else {
                values.add(csvRecord.get(j));
            }
        }
        return values;
    }

    private AccessRight getAccessRight(List<String> values, Map<Integer, Integer> nhLevelColumns) {
        Map<Integer, AccessRightMode> modes = new HashMap<Integer, AccessRightMode>();
        List<String> nodeIds = new ArrayList<String>();
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i);
            value = value.replaceAll("-+>", "").trim();
            if (i < NODES_COLUMNS) {
                if (!EMPTY_VALUE.equals(value)) {
                    nodeIds.add(value);
                }
            } else {
                int nhLevel = nhLevelColumns.get(i);
                AccessRightMode mode = getAccessRightMode(value);
                modes.put(nhLevel, mode);
            }
        }
        return new AccessRight(nodeIds, modes);
    }

    private int findFirstNonEmptyIndex(CSVRecord csvRecord) {
        for (int i = 0; i < csvRecord.size(); i++) {
            String value = csvRecord.get(i);
            if (!EMPTY_VALUE.equals(value)) {
                return i;
            }
        }
        throw new RuntimeException("Empty lines not allowed in csv input");
    }

    private int getNhLevel(String value) {
        value = value.replaceAll("^(\\d+) - [\\w ]+$", "$1");
        return Integer.valueOf(value);
    }

    private AccessRightMode getAccessRightMode(String value) {
        if ("yes".equals(value)) {
            return AccessRightMode.ALLOWED;
        } else if ("no".equals(value)) {
            return AccessRightMode.NOT_ALLOWED;
        } else if (value.contains("read-only") || value.contains("RO")) {
            return AccessRightMode.READ_ONLY;
        } else if (value.contains("permission denied") || value.contains("not possible to invalidate")) {
            return AccessRightMode.PERMISSION_DENIED;
        }
        throw new RuntimeException(String.format("Invalid access right mode: %s", value));
    }

    private class AccessRight {
        public String nodeId;
        public Map<Integer, AccessRightMode> modes;

        public AccessRight(List<String> nodeIds, Map<Integer, AccessRightMode> modes) {
            this.nodeId = getJointNodeId(nodeIds);
            this.modes = modes;
        }

        private String getJointNodeId(List<String> nodeIds) {
            return StringUtils.join(nodeIds, NODE_ID_SEPARATOR);
        }
    }

    private class AccessRightResult {
        public AccessRightError error;

        public AccessRightResult() {
            error = null;
        }

        public AccessRightResult(AccessRightException e) {
            error = new AccessRightError(e);
        }

        public boolean hasErrors() {
            return error != null;
        }
    }

    private class AccessRightError {
        private String message;

        public AccessRightError(AccessRightException e) {
            message = e.getMessage();
        }

        public String toString() {
            return message;
        }
    }

    private class AccessRightException extends Exception {

        private static final long serialVersionUID = -7342171098383607299L;

        public String fullId;
        public String accessRight;
        public int nhLevel;

        public AccessRightException(String fullId, String accessRight, boolean expected, int nhLevel) {
            super(accessRightFailedMessage(fullId, accessRight, expected, nhLevel));
            this.fullId = fullId;
            this.accessRight = accessRight;
            this.nhLevel = nhLevel;
        }

    }

    private static String accessRightFailedMessage(String fullId, String accessRight, boolean expected, int nhLevel) {
        String expectedHumanReadable = expected ? "allowed" : "disallowed";
        String message = String.format("%s: %s access right failed (should be %s) for NH_Level=%s", fullId,
                accessRight, expectedHumanReadable, nhLevel);
        return message;
    }

    private abstract class SiteNode {
        public String id;
        public SiteNode parent;
        public Map<String, SiteNode> children;

        public SiteNode(String id) {
            this.id = id;
            this.children = new HashMap<String, SiteNode>();
            this.parent = null;
        }

        // expected argument is used for optimization. If we expected page not
        // to be visible, then we won't wait for it to become visible.
        public abstract boolean visible();

        protected abstract void view();

        public AccessRightResult testAccessRight(AccessRightMode mode, int nhLevel) {
            try {
                resetView();
                testMode(mode, nhLevel);
            } catch (AccessRightException e) {
                String methodName = String.format("%s_%s_(NH_Level=%s)", e.fullId.replace("->", "-"), e.accessRight,
                        e.nhLevel);
                failure.takeScreenShot(failure.generateScreenShotName(UC028.class.getName(), methodName));
                return new AccessRightResult(e);
            } catch (Exception e) {
                throw new RuntimeException(String.format("Unexpected error when testing: %s", getFullId()), e);
            }
            return new AccessRightResult();
        }

        protected void addChild(SiteNode child) {
            child.setParent(this);
            children.put(child.id, child);
        }

        protected void setParent(SiteNode parent) {
            this.parent = parent;
        }

        protected String getFullId() {
            return parent == null ? id : parent.getFullId() + NODE_ID_SEPARATOR + id;
        }

        private void resetView() {
            if (crsweb().isElementPresentInstantaneously(By.className("jqmOverlay"))) {
                for (WebElement dialog: wd().findElements(By.className("jqmWindow"))) {
                    if (dialog.isDisplayed()) {
                        crsweb().clickElement(dialog.findElement(By.className("closeDialog")));
                        break;
                    }
                }
            }
        }

        private void testMode(AccessRightMode mode, int nhLevel) throws AccessRightException {
            switch (mode) {
                case ALLOWED:
                    testAllowedMode(nhLevel);
                    break;
                case NOT_ALLOWED:
                    testNotAllowedMode(nhLevel);
                    break;
                case READ_ONLY:
                case PERMISSION_DENIED:
                    testPermissionDeniedMode(nhLevel);
            }
        }

        protected abstract void testAllowedMode(int nhLevel) throws AccessRightException;

        protected abstract void testNotAllowedMode(int nhLevel) throws AccessRightException;

        protected abstract void testPermissionDeniedMode(int nhLevel) throws AccessRightException;

        protected void checkVisible(boolean expected, boolean actual, int nhLevel) throws AccessRightException {
            checkAccessRight("Visible", actual, expected, nhLevel);
        }

        protected void checkAccessible(boolean expected, boolean actual, int nhLevel) throws AccessRightException {
            checkAccessRight("Accessible", actual, expected, nhLevel);
        }

        protected void checkModifiable(boolean expected, boolean actual, int nhLevel) throws AccessRightException {
            checkAccessRight("Modifiable", actual, expected, nhLevel);
        }

        private void checkAccessRight(String accessRight, boolean actual, boolean expected, int nhLevel)
                throws AccessRightException {
            try {
                if (expected) {
                    assertTrue(actual);
                } else {
                    assertFalse(actual);
                }
            } catch (AssertionError e) {
                throw new AccessRightException(getFullId(), accessRight, expected, nhLevel);
            }
        }

        public String toString() {
            return getFullId();
        }
    }

    private abstract class MainMenuNode extends SiteNode {

        public MainMenuNode(String id) {
            super(id);
        }

        public boolean visible() {
            // Main menu top items' text starts with two &nbsp;. Index of the
            // first character in substring function is 1, hence the 3.
            String xpath = String.format("//td[contains(@class, 'smd-menu-top') and " + "'%s' = substring(., 3)]", id,
                    id);
            return crsweb().isElementPresentInstantaneously(By.xpath(xpath));
        }

        protected void view() {}

        protected void testAllowedMode(int nhLevel) throws AccessRightException {
            checkVisible(true, visible(), nhLevel);
        }

        protected void testNotAllowedMode(int nhLevel) throws AccessRightException {
            checkVisible(false, visible(), nhLevel);
        }

        protected void testPermissionDeniedMode(int nhLevel) throws AccessRightException {
            throw new RuntimeException("Invalid mode to test");
        }

    }

    private abstract class PageSiteNode extends SiteNode {

        protected String siteTitle;
        protected boolean linkReloadsPage;

        public PageSiteNode(String id, String siteTitle) {
            super(id);
            this.siteTitle = siteTitle;
            this.linkReloadsPage = true;
        }

        protected abstract By getLinkElement();

        protected abstract By getModifiableElement();

        public boolean visible() {
            if (parent.visible()) {
                parent.view();
                return crsweb().isElementPresentInstantaneously(getLinkElement());
            } else {
                return false;
            }
        }

        public boolean accessible() {
            view();
            return pageTitlePresent(siteTitle);
        }

        public boolean modifiable() {
            By element = getModifiableElement();
            return element == null ? true : crsweb().isElementPresentInstantaneously(element);
        }

        protected void view() {
            parent.view();
            if (linkReloadsPage) {
                crsweb().clickAndWaitForPageToLoad(getLinkElement());
            } else {
                crsweb().clickElement(getLinkElement());
            }
        }

        protected void testAllowedMode(int nhLevel) throws AccessRightException {
            checkVisible(true, visible(), nhLevel);
            checkAccessible(true, accessible(), nhLevel);
            checkModifiable(true, modifiable(), nhLevel);
        }

        protected void testNotAllowedMode(int nhLevel) throws AccessRightException {
            checkVisible(false, visible(), nhLevel);
        }

        protected void testPermissionDeniedMode(int nhLevel) throws AccessRightException {
            checkVisible(true, visible(), nhLevel);
            checkAccessible(true, accessible(), nhLevel);
            checkModifiable(false, modifiable(), nhLevel);
        }

        protected boolean pageTitlePresent(String title) {
            return crsweb().isElementPresentInstantaneously(
                    By.xpath(String.format("//div[@id='header']//p[text()='%s']", title)));
        }

        protected boolean tableWithTitlePresent(String title) {
            return crsweb().isElementPresentInstantaneously(
                    By.xpath(String.format("//td[@id='main-panel']//div[text()='%s']", title)));
        }

    }

    private abstract class SubMenuSiteNode extends PageSiteNode {

        protected SiteId siteId;

        public SubMenuSiteNode(String id, SiteId siteId, String siteTitle) {
            super(id, siteTitle);
            this.siteId = siteId;
        }

        protected By getLinkElement() {
            return By.xpath(String.format("//a[@title='%s']", siteId.toString()));
        }
    }

    private abstract class ButtonBasedPageNode extends PageSiteNode {

        public ButtonBasedPageNode(String id, String siteTitle) {
            super(id, siteTitle);
        }

        public boolean visible() {
            boolean buttonPresent = super.visible();
            if (buttonPresent) {
                boolean buttonEnabled = !"disabled".equals(wd().findElement(getLinkElement()).getAttribute("disabled"));
                return buttonEnabled;
            } else {
                return false;
            }
        }

    }

    private static enum AccessRightMode {
        ALLOWED, NOT_ALLOWED, READ_ONLY, PERMISSION_DENIED
    }

    private class TicketsMainMenu extends MainMenuNode {

        public TicketsMainMenu() {
            super("Tickets");
            addChild(new TicketsSearchPageNode());
            addChild(new TicketsHistoryPageNode());
            addChild(new TicketsReportsPageNode());
        }

    }

    private class DomainsMainMenu extends MainMenuNode {
        public DomainsMainMenu() {
            super("Domains");
            addChild(new DomainsSearchPageNode());
            addChild(new DomainsHistoryPageNode());
            addChild(new DomainsExportAuthcodesPageNode());
        }
    }

    private class EmailsMainMenu extends MainMenuNode {
        public EmailsMainMenu() {
            super("Emails");
            addChild(new EmailsViewPageNode());
            addChild(new EmailsGroupsPageNode());
        }
    }

    private class NicHandlesMainMenu extends MainMenuNode {
        public NicHandlesMainMenu() {
            super("Nic Handles");
            addChild(new NicHandlesSearchPageNode());
            addChild(new NicHandlesCreatePageNode());
        }
    }

    private class DocumentsMainMenu extends MainMenuNode {
        public DocumentsMainMenu() {
            super("Documents");
            addChild(new DocumentsSearchPageNode());
            addChild(new DocumentsNewPageNode());
        }
    }

    private class AccountsMainMenu extends MainMenuNode {
        public AccountsMainMenu() {
            super("Accounts");
            addChild(new AccountsSearchPageNode());
            addChild(new AccountsCreatePageNode());
        }
    }

    private class DsmMainMenu extends MainMenuNode {
        public DsmMainMenu() {
            super("DSM");
            addChild(new DsmForceEventPageNode());
            addChild(new DsmForceStatePageNode());
        }
    }

    private class VatMainMenu extends MainMenuNode {
        public VatMainMenu() {
            super("Vat");
            addChild(new VatViewPageNode());
            addChild(new VatCreatePageNode());
        }
    }

    private class TaskMainMenu extends MainMenuNode {
        public TaskMainMenu() {
            super("Task");
            addChild(new TaskViewPageNode());
            addChild(new TaskCreatePageNode());
            addChild(new TaskJobPageNode());
            addChild(new TaskJobHistPageNode());

        }
    }

    private class ProductPricingMainMenu extends MainMenuNode {
        public ProductPricingMainMenu() {
            super("Product Pricing");
            addChild(new ProductPricingViewPageNode());
            addChild(new ProductPricingCreatePageNode());
        }
    }

    private class CrsConfigurationMainMenu extends MainMenuNode {
        public CrsConfigurationMainMenu() {
            super("CRS configuration");
            addChild(new CrsConfigurationViewPageNode());
        }
    }

    private class BulkTransferMainMenu extends MainMenuNode {
        public BulkTransferMainMenu() {
            super("Bulk transfer");
            addChild(new BulkTransferCreatePageNode());
            addChild(new BulkTransferViewPageNode());
        }
    }

    private class AccountsReportMainMenu extends MainMenuNode {
        public AccountsReportMainMenu() {
            super("Accounts Report");
            addChild(new ReportDepositPageNode());
            addChild(new ReportDoaPageNode());
            addChild(new ReportInvoicesPageNode());
            addChild(new ReportRevenueAssurancePageNode());
            addChild(new ReportBankReconciliationPageNode());
            addChild(new ReportNrpDomainsPageNode());
            addChild(new ReportCharityDomainsPageNode());
            addChild(new ReportTotalDomainsPageNode());
            addChild(new ReportRegistrationsPageNode());
            addChild(new ReportPerClassPageNode());
            addChild(new ReportDeletedDomainsPageNode());
            addChild(new ReportAutorenewsPageNode());
        }
    }

    private class TicketsSearchPageNode extends SubMenuSiteNode {
        public TicketsSearchPageNode() {
            super("Search", SiteId.TicketsSearch, "Tickets Search");
            addChild(new TicketCheckOutPageNode());
        }

        public By getModifiableElement() {
            return By.id("tickets-search_0");
        }

    }

    private class TicketsHistoryPageNode extends SubMenuSiteNode {
        public TicketsHistoryPageNode() {
            super("History", SiteId.TicketsHistory, "Tickets History Search");
        }

        public By getModifiableElement() {
            return By.id("ticketshistory-search_0");
        }
    }

    private class TicketsReportsPageNode extends SubMenuSiteNode {
        public TicketsReportsPageNode() {
            super("Reports", SiteId.TicketsReports, "Hostmaster Usage");
        }

        public By getModifiableElement() {
            return By.id("reports-search_0");
        }
    }

    private class DomainsSearchPageNode extends SubMenuSiteNode {
        public DomainsSearchPageNode() {
            super("Search", SiteId.DomainsSearch, "Domains Search");
            addChild(new DomainViewPageNode());
        }

        public By getModifiableElement() {
            return By.id("domains-search_0");
        }
    }

    private class DomainsHistoryPageNode extends SubMenuSiteNode {
        public DomainsHistoryPageNode() {
            super("History", SiteId.DomainsHistory, "Domains History");
        }

        public By getModifiableElement() {
            return By.id("historical-domain-search_0");
        }
    }

    private class DomainsExportAuthcodesPageNode extends SubMenuSiteNode {
        public DomainsExportAuthcodesPageNode() {
            super("Export Authcodes", SiteId.DomainsExportAuthcodes, "Export Authcodes");
        }

        public By getModifiableElement() {
            return By.id("domainExportAuthCodes-search_0");
        }
    }

    private class EmailsViewPageNode extends SubMenuSiteNode {
        public EmailsViewPageNode() {
            super("View", SiteId.EmailsView, "Emails Listing");
        }

        public boolean accessible() {
            return super.accessible() && emailViewPageAccessible();
        }

        public boolean modifiable() {
            return emailViewPageModifiable();
        }

        public By getModifiableElement() {
            return null;
        }

        private void viewEmailTemplate() {
            view();
            crsweb().clickAndWaitForPageToLoad(By.cssSelector("img[title='View']"));
        }

        private boolean emailViewPageAccessible() {
            viewEmailTemplate();
            String title = "Email View";
            return pageTitlePresent(title) && tableWithTitlePresent("Email Template");
        }

        private boolean emailViewPageModifiable() {
            viewEmailTemplate();
            return crsweb().isElementPresentInstantaneously(By.id("emailtemplate-view_emailtemplate-input"));
        }

    }

    private class EmailsGroupsPageNode extends SubMenuSiteNode {
        public EmailsGroupsPageNode() {
            super("Groups", SiteId.EmailsGroups, "Email Group Listing");
        }

        public By getModifiableElement() {
            return By.id("emailgroups-search_emailgroup-new");
        }

    }

    private class NicHandlesSearchPageNode extends SubMenuSiteNode {
        public NicHandlesSearchPageNode() {
            super("Search", SiteId.NicHandlesSearch, "Nic Handle Search");
            addChild(new NicHandleViewPageNode());
        }

        public By getModifiableElement() {
            return By.id("nic-handles-search_0");
        }
    }

    private class NicHandlesCreatePageNode extends SubMenuSiteNode {
        public NicHandlesCreatePageNode() {
            super("Create", SiteId.NicHandlesCreate, "Nic Handle Create");
        }

        public By getModifiableElement() {
            return By.id("nic-handle-create-input_nic-handle-create_create");
        }
    }

    private class DocumentsSearchPageNode extends SubMenuSiteNode {
        public DocumentsSearchPageNode() {
            super("Search", SiteId.DocumentsSearch, "Assigned Documents");
        }

        public By getModifiableElement() {
            return By.id("documents-search_0");
        }
    }

    private class DocumentsNewPageNode extends SubMenuSiteNode {
        public DocumentsNewPageNode() {
            super("New", SiteId.DocumentsNew, "New Documents");
        }

        public By getModifiableElement() {
            return null;
        }
    }

    private class AccountsSearchPageNode extends SubMenuSiteNode {
        public AccountsSearchPageNode() {
            super("Search", SiteId.AccountsSearch, "Account Search");
            addChild(new AccountDetailPageNode());
        }

        public By getModifiableElement() {
            return By.id("accounts-search_0");
        }
    }

    private class AccountsCreatePageNode extends SubMenuSiteNode {
        public AccountsCreatePageNode() {
            super("Create", SiteId.AccountsCreate, "Account Create");
        }

        public By getModifiableElement() {
            return By.id("account-create-input_account-create_create");
        }
    }

    private class DsmForceEventPageNode extends SubMenuSiteNode {
        public DsmForceEventPageNode() {
            super("Force Event", SiteId.DsmForceEvent, "Force DSM Event");
        }

        public By getModifiableElement() {
            return By.id("forceDSMevent-input_forceDSMevent-force");
        }
    }

    private class DsmForceStatePageNode extends SubMenuSiteNode {
        public DsmForceStatePageNode() {
            super("Force State", SiteId.DsmForceState, "Force DSM State");
        }

        public By getModifiableElement() {
            return By.id("forceDSMstate-input_forceDSMstate-force");
        }
    }

    private class VatViewPageNode extends SubMenuSiteNode {
        public VatViewPageNode() {
            super("View", SiteId.VatView, "Vat View");
        }

        public By getModifiableElement() {
            return By.cssSelector("img[title='Invalidate']");
        }
    }

    private class VatCreatePageNode extends SubMenuSiteNode {
        public VatCreatePageNode() {
            super("Create", SiteId.VatCreate, "Create vat rate");
        }

        public By getModifiableElement() {
            return By.id("vatCreate-input_vatCreate-create");
        }
    }

    private class TaskViewPageNode extends SubMenuSiteNode {
        public TaskViewPageNode() {
            super("View", SiteId.TaskView, "Task View");
        }

        public boolean modifiable() {
            return crsweb().isElementPresentInstantaneously(By.cssSelector("img[title='Edit']"))
                    && crsweb().isElementPresentInstantaneously(By.cssSelector("img[title='Remove']"));
        }

        public By getModifiableElement() {
            return null;
        }
    }

    private class TaskCreatePageNode extends SubMenuSiteNode {
        public TaskCreatePageNode() {
            super("Create", SiteId.TaskCreate, "Create Task");
        }

        public By getModifiableElement() {
            return By.id("task-create-input_task-create");
        }
    }

    private class TaskJobPageNode extends SubMenuSiteNode {
        public TaskJobPageNode() {
            super("Job", SiteId.TaskJob, "Job View");
        }

        public By getModifiableElement() {
            return null;
        }
    }

    private class TaskJobHistPageNode extends SubMenuSiteNode {
        public TaskJobHistPageNode() {
            super("Job Hist", SiteId.TaskJobHist, "Job History View");
        }

        public By getModifiableElement() {
            return null;
        }
    }

    private class ProductPricingViewPageNode extends SubMenuSiteNode {
        public ProductPricingViewPageNode() {
            super("View", SiteId.ProductPricingView, "Product Pricing View");
        }

        public By getModifiableElement() {
            return By.cssSelector("img[title='Edit']");
        }
    }

    private class ProductPricingCreatePageNode extends SubMenuSiteNode {
        public ProductPricingCreatePageNode() {
            super("Create", SiteId.ProductPricingCreate, "Create Product Price");
        }

        public By getModifiableElement() {
            return By.id("priceCreate-input_priceCreate-create");
        }
    }

    private class CrsConfigurationViewPageNode extends SubMenuSiteNode {
        public CrsConfigurationViewPageNode() {
            super("View", SiteId.CrsConfigurationView, "Config view");
        }

        public By getModifiableElement() {
            return By.cssSelector("img[title='Edit']");
        }
    }

    private class BulkTransferCreatePageNode extends SubMenuSiteNode {
        public BulkTransferCreatePageNode() {
            super("Create", SiteId.BulkTransferCreate, "Create bulk transfer request");
        }

        public By getModifiableElement() {
            return By.id("bulkTransferCreate-create_0");
        }
    }

    private class BulkTransferViewPageNode extends SubMenuSiteNode {
        public BulkTransferViewPageNode() {
            super("View", SiteId.BulkTransferView, "List bulk transfers");
        }

        public By getModifiableElement() {
            return null;
        }
    }

    private class ReportDepositPageNode extends SubMenuSiteNode {
        public ReportDepositPageNode() {
            super("Deposit", SiteId.ReportDeposit, "Deposit Actual Balance");
        }

        public By getModifiableElement() {
            return By.id("depositAccount-search_0");
        }
    }

    private class ReportDoaPageNode extends SubMenuSiteNode {
        public ReportDoaPageNode() {
            super("DOA", SiteId.ReportDoa, "DOA");
        }

        public By getModifiableElement() {
            return By.id("viewDOA-search_0");
        }
    }

    private class ReportInvoicesPageNode extends SubMenuSiteNode {
        public ReportInvoicesPageNode() {
            super("Invoices", SiteId.ReportInvoices, "Invoices");
        }

        public By getModifiableElement() {
            return By.id("viewInvoices-search_0");
        }
    }

    private class ReportRevenueAssurancePageNode extends SubMenuSiteNode {
        public ReportRevenueAssurancePageNode() {
            super("Revenue Assurance", SiteId.ReportRevenueAssurance, "Ex Invoices");
        }

        public By getModifiableElement() {
            return By.id("viewExtendedReservations-search_0");
        }
    }

    private class ReportBankReconciliationPageNode extends SubMenuSiteNode {
        public ReportBankReconciliationPageNode() {
            super("Receipts: Bank Reconciliation", SiteId.ReportBankReconciliation, "Receipts: Bank Reconciliation");
        }

        public By getModifiableElement() {
            return By.id("bankReceipts-search_0");
        }
    }

    private class ReportNrpDomainsPageNode extends SubMenuSiteNode {
        public ReportNrpDomainsPageNode() {
            super("NRP", SiteId.ReportNrpDomains, "NRP Domains");
        }

        public By getModifiableElement() {
            return By.id("nrpDomains-search_0");
        }
    }

    private class ReportCharityDomainsPageNode extends SubMenuSiteNode {
        public ReportCharityDomainsPageNode() {
            super("Charity", SiteId.ReportCharityDomains, "Charity Domains");
        }

        public By getModifiableElement() {
            return By.id("charityDomains-search_0");
        }
    }

    private class ReportTotalDomainsPageNode extends SubMenuSiteNode {
        public ReportTotalDomainsPageNode() {
            super("TotalDomains", SiteId.ReportTotalDomains, "Total Domains per Registrar Report");
        }

        public By getModifiableElement() {
            return null;
        }
    }

    private class ReportRegistrationsPageNode extends SubMenuSiteNode {
        public ReportRegistrationsPageNode() {
            super("Per Class", SiteId.ReportRegistrations, "Registrations Per Month/Year for Registrar Report");
        }

        public By getModifiableElement() {
            return By.id("totalDomainsPerDateReport-search_0");
        }
    }

    private class ReportPerClassPageNode extends SubMenuSiteNode {
        public ReportPerClassPageNode() {
            super("Registrations", SiteId.ReportPerClass, "Domains per Class Category Report");
        }

        public By getModifiableElement() {
            return By.id("domainsPerClassReport-search_0");
        }
    }

    private class ReportDeletedDomainsPageNode extends SubMenuSiteNode {
        public ReportDeletedDomainsPageNode() {
            super("Deleted", SiteId.ReportDeletedDomains, "Deleted Domains");
        }

        public By getModifiableElement() {
            return By.id("deleted-search_0");
        }
    }

    private class ReportAutorenewsPageNode extends SubMenuSiteNode {
        public ReportAutorenewsPageNode() {
            super("Autorenews", SiteId.ReportAutorenews, "Autorenews");
        }

        public By getModifiableElement() {
            return By.id("autorenews-search_0");
        }
    }

    private class TicketCheckOutPageNode extends PageSiteNode {
        public TicketCheckOutPageNode() {
            super("Ticket - Check out", "Ticket Revise");
        }

        public void view() {
            ViewTicketPage ttp = new ViewTicketPage(accessRightTestUser);
            ttp.viewReviseAndEditTicket("uc028-sc01.ie");
        }

        public By getModifiableElement() {
            return By.id("ticketrevise-input__save");
        }

        protected By getLinkElement() {
            return By.cssSelector("img[title='Check Out']");
        }
    }

    private class DomainViewPageNode extends PageSiteNode {
        public DomainViewPageNode() {
            super("Domain - View", "Domain View");
            addChild(new DomainAlterStatusPageNode());
            addChild(new DomainChangeHolderTypePageNode());
            addChild(new DomainChangeLockPageNode());
            addChild(new DomainEditPageNode());
            addChild(new DomainDnsCheckPageNode());
            addChild(new DomainSendAuthcodePageNode());
            addChild(new DomainSendWhoisDataPageNode());
            addChild(new DomainCancelPageNode());
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.cssSelector("img[title='View']");
        }
    }

    private class DomainAlterStatusPageNode extends ButtonBasedPageNode {
        public DomainAlterStatusPageNode() {
            super("Alter Status", "Domain View");
            this.linkReloadsPage = false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("openAlterStatusDialog");
        }
    }

    private class DomainChangeHolderTypePageNode extends ButtonBasedPageNode {
        public DomainChangeHolderTypePageNode() {
            super("Change Holder Type", "Domain View");
            this.linkReloadsPage = false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("openChangeHolderTypeDialog");
        }
    }

    private class DomainChangeLockPageNode extends ButtonBasedPageNode {
        public DomainChangeLockPageNode() {
            super("Lock", "Domain View");
            this.linkReloadsPage = false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("openChangeLockDialog");
        }
    }

    private class DomainEditPageNode extends ButtonBasedPageNode {
        public DomainEditPageNode() {
            super("Edit", "Domain Edit");
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("domainview_domainedit_input");
        }
    }

    private class DomainSendAuthcodePageNode extends ButtonBasedPageNode {
        public DomainSendAuthcodePageNode() {
            super("Send Authcode", "Domain View");
            this.linkReloadsPage = false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("domainview_domainview-sendAuthCode");
        }
    }

    private class DomainDnsCheckPageNode extends ButtonBasedPageNode {
        public DomainDnsCheckPageNode() {
            super("DNS Check", "Domain View");
            this.linkReloadsPage = false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("domainview_domainview-dnsCheck");
        }
    }

    private class DomainSendWhoisDataPageNode extends ButtonBasedPageNode {
        public DomainSendWhoisDataPageNode() {
            super("Send Whois Data", "Domain View");
            this.linkReloadsPage = true;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("domainview_domainview-sendWhois");
        }
    }

    private class DomainCancelPageNode extends ButtonBasedPageNode {
        public DomainCancelPageNode() {
            super("Cancel", "Domains Search");
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.name("action:domains-input");
        }
    }

    private class NicHandleViewPageNode extends PageSiteNode {
        public NicHandleViewPageNode() {
            super("Nic Handle - View", "Nic Handle View");
            addChild(new NicHandleAccessLevelPageNode());
            addChild(new NicHandleResetPasswordPageNode());
            addChild(new NicHandleTfaPageNode());
            addChild(new NicHandleAlterStatusPageNode());
            addChild(new NicHandleEditPageNode());
            addChild(new NicHandleCreatePageNode());
            addChild(new NicHandleCancelPageNode());
        }

        public By getModifiableElement() {
            return null;
        }

        protected void view() {
            crsweb().viewNicHandle("UC028AB-IEDR");
        }

        protected By getLinkElement() {
            return By.cssSelector("img[title='View']");
        }
    }

    private class NicHandleAccessLevelPageNode extends ButtonBasedPageNode {
        public NicHandleAccessLevelPageNode() {
            super("Access Level", "Nic Handle Access Level");
        }

        public By getModifiableElement() {
            return By.id("nic-handle-access-level_nic-handle-access-editPerms-edit");
        }

        protected By getLinkElement() {
            return By.id("nic-handle-view_nic-handle-access-level_input");
        }
    }

    private class NicHandleResetPasswordPageNode extends ButtonBasedPageNode {
        public NicHandleResetPasswordPageNode() {
            super("Reset Password", "Nic Handle Reset Password");
        }

        public boolean modifiable() {
            By button = getModifiableElement();
            if (crsweb().isElementPresentInstantaneously(button)) {
                crsweb().clickElement(button);
                return !pageTitlePresent(siteTitle);
            } else {
                return false;
            }
        }

        public By getModifiableElement() {
            return By.id("nic-handle-reset-password__resetPassword");
        }

        protected By getLinkElement() {
            return By.id("nic-handle-view_nic-handle-reset-password_input");
        }
    }

    private class NicHandleTfaPageNode extends ButtonBasedPageNode {
        public NicHandleTfaPageNode() {
            super("T.F.A", "Two Factor Authentication Setup");
        }

        public boolean modifiable() {
            By button = getModifiableElement();
            if (crsweb().isElementPresentInstantaneously(button)) {
                crsweb().clickElement(button);
                return !pageTitlePresent(siteTitle);
            } else {
                return false;
            }
        }

        public By getModifiableElement() {
            return By.cssSelector("#nic-handle-tfa_nic-handle-tfa_enable, " + "#nic-handle-tfa_nic-handle-tfa_disable");
        }

        protected By getLinkElement() {
            return By.id("nic-handle-view_nic-handle-tfa_input");
        }
    }

    private class NicHandleAlterStatusPageNode extends ButtonBasedPageNode {
        public NicHandleAlterStatusPageNode() {
            super("Alter Status", "Nic Handle View");
            this.linkReloadsPage = false;
        }

        public boolean accessible() {
            view();
            By dialogBy = By.xpath("//div[starts-with(@id, 'alterStatusDialog')]");
            boolean dialogPresent;
            dialogPresent = crsweb().isElementPresent(dialogBy);
            if (dialogPresent) {
                WebElement dialog = wd().findElement(dialogBy);
                if (dialog.isDisplayed()) {
                    crsweb().clickElement(dialog.findElement(By.cssSelector(".closeDialog")));
                    return true;
                }
            }
            return false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("openAlterStatusDialog");
        }

    }

    private class NicHandleEditPageNode extends ButtonBasedPageNode {
        public NicHandleEditPageNode() {
            super("Edit", "Nic Handle Edit");
        }

        public By getModifiableElement() {
            return By.id("nic-handle-edit_nic-handle-edit_save");
        }

        protected By getLinkElement() {
            return By.id("nic-handle-view_nic-handle-edit_input");
        }
    }

    private class NicHandleCreatePageNode extends ButtonBasedPageNode {
        public NicHandleCreatePageNode() {
            super("Create...", "Nic Handle Create");
        }

        public By getModifiableElement() {
            return By.id("nic-handle-createFrom_nic-handle-create_create");
        }

        protected By getLinkElement() {
            return By.id("nic-handle-view_nic-handle-createFrom_input");
        }
    }

    private class NicHandleCancelPageNode extends ButtonBasedPageNode {
        public NicHandleCancelPageNode() {
            super("Cancel", "Nic Handle Search");
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("nic-handle-view_%{previousAction}");
        }
    }

    private class AccountDetailPageNode extends PageSiteNode {
        public AccountDetailPageNode() {
            super("Account - Detail", "Account View");
            addChild(new AccountDetailAlterStatusPageNode());
            addChild(new AccountDetailEditPageNode());
            addChild(new AccountDetailDepositPageNode());
            addChild(new AccountDetailCancelPageNode());
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.cssSelector("img[title='View']");
        }
    }

    private class AccountDetailAlterStatusPageNode extends ButtonBasedPageNode {
        public AccountDetailAlterStatusPageNode() {
            super("Alter Status", "Account View");
            this.linkReloadsPage = false;
        }

        public boolean accessible() {
            view();
            By dialogBy = By.xpath("//div[starts-with(@id, 'alterStatusDialog')]");
            boolean dialogPresent;
            dialogPresent = crsweb().isElementPresent(dialogBy);
            if (dialogPresent) {
                WebElement dialog = wd().findElement(dialogBy);
                if (dialog.isDisplayed()) {
                    crsweb().clickElement(dialog.findElement(By.cssSelector(".closeDialog")));
                    return true;
                }
            }
            return false;
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("openAlterStatusDialog");
        }
    }

    private class AccountDetailEditPageNode extends ButtonBasedPageNode {
        public AccountDetailEditPageNode() {
            super("Edit", "Account Edit");
        }

        public By getModifiableElement() {
            return By.id("account-edit-input_account-edit-save");
        }

        protected By getLinkElement() {
            return By.id("account-view-view_account-edit-input");
        }
    }

    private class AccountDetailDepositPageNode extends ButtonBasedPageNode {
        public AccountDetailDepositPageNode() {
            super("Deposit", "View deposit");
            addChild(new AccountDetailDepositCorrectionPageNode());
            addChild(new AccountDetailDepositTopupPageNode());
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("account-view-view_deposit-view-input");
        }
    }

    private class AccountDetailDepositCorrectionPageNode extends ButtonBasedPageNode {
        public AccountDetailDepositCorrectionPageNode() {
            super("Correction", "Correct deposit");
        }

        public By getModifiableElement() {
            return By.id("deposit-correct-input_deposit-correct-correct");
        }

        protected By getLinkElement() {
            return By.id("deposit-view-input_deposit-correct-input");
        }
    }

    private class AccountDetailDepositTopupPageNode extends ButtonBasedPageNode {
        public AccountDetailDepositTopupPageNode() {
            super("Topup", "TopUp Deposi");
        }

        public By getModifiableElement() {
            return By.id("deposit-topup-input_deposit-topup-topup");
        }

        protected By getLinkElement() {
            return By.id("deposit-view-input_deposit-topup-input");
        }
    }

    private class AccountDetailCancelPageNode extends ButtonBasedPageNode {
        public AccountDetailCancelPageNode() {
            super("Cancel", "Account Search");
        }

        public By getModifiableElement() {
            return null;
        }

        protected By getLinkElement() {
            return By.id("account-view-view_%{previousAction}");
        }
    }

    private Map<String, SiteNode> getSiteMap() {
        Map<String, SiteNode> siteMap = new HashMap<String, SiteNode>();
        List<? extends SiteNode> mainMenu = getMainMenu();
        addToMapRecursively(mainMenu, siteMap, null);
        return siteMap;
    }

    private void addToMapRecursively(List<? extends SiteNode> nodes, Map<String, SiteNode> siteMap, String prefix) {
        for (SiteNode node : nodes) {
            String id = prefix == null ? node.id : prefix + NODE_ID_SEPARATOR + node.id;
            siteMap.put(id, node);
            List<SiteNode> children = new ArrayList<SiteNode>(node.children.values());
            addToMapRecursively(children, siteMap, id);
        }
    }

    private List<MainMenuNode> getMainMenu() {
        return Arrays.asList(new TicketsMainMenu(), new DomainsMainMenu(), new EmailsMainMenu(),
                new NicHandlesMainMenu(), new DocumentsMainMenu(), new AccountsMainMenu(), new DsmMainMenu(),
                new VatMainMenu(), new TaskMainMenu(), new ProductPricingMainMenu(), new CrsConfigurationMainMenu(),
                new BulkTransferMainMenu(), new AccountsReportMainMenu());
    }

}
