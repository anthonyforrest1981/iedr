package com.iedr.bpr.tests.console;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.formdetails.console.PredefinedPayments;
import com.iedr.bpr.tests.pages.console.AllDomainsPage;
import com.iedr.bpr.tests.utils.ContactType;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.console.DomainTransferUtils;
import com.iedr.bpr.tests.utils.console.ViewDomainUtils;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UC084 extends SeleniumTest {

    public UC084(Browser browser) {
        super(browser);
    }

    @Override
    protected String getResetDataScript() {
        return "sql_data/console/uc084_reset_data.sql";
    }

    @Override
    protected String getLoadDataScript() {
        return "sql_data/console/uc084_data.sql";
    }

    @Test
    public void test_uc084_sc01_present() throws SQLException {
        User user = this.registrar;
        String domainName = "uc084-sc01-a.ie";
        String currentAuthCode = db().getAuthcodeForDomain(domainName);
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        String authCode = getAuthCode();
        assertEquals(currentAuthCode, db().getAuthcodeForDomain(domainName));
        assertEquals(currentAuthCode, authCode);
    }

    @Test
    public void test_uc084_sc01_empty() throws SQLException {
        User user = this.registrar;
        String domainName = "uc084-sc01-b.ie";
        assertEquals(null, db().getAuthcodeForDomain(domainName));
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        String authCode = getAuthCode();
        String dbAuthCode = db().getAuthcodeForDomain(domainName);
        assertNotNull(dbAuthCode);
        assertEquals(dbAuthCode, authCode);
    }

    @Test
    public void test_uc084_sc02() throws SQLException {
        User gaining = this.registrar;
        User losing = this.registrarNonVat;
        String domainName = "uc084-sc02.ie";
        emails.add(emailSummaryGenerator.getBillingTransferRequestEmail(gaining, losing, this.adminContact));
        console().login(gaining);
        DomainTransferUtils.transferDomain(domainName, gaining, PredefinedPayments.DEPOSIT_PAYMENT_DETAILS);
        int ticketId = db().getTicketId(domainName);
        assertEquals("Passed", db().getTicketAdminStatus(ticketId));
        checkAndResetEmails(emails);
    }

    @Test
    public void test_uc084_sc03_present() throws SQLException {
        User user = this.registrar;
        String domainName = "uc084-sc03-a.ie";
        String currentAuthCode = db().getAuthcodeForDomain(domainName);
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        String authCode = getAuthCodeByEmail(user, user, domainName);
        assertEquals(currentAuthCode, db().getAuthcodeForDomain(domainName));
        assertEquals(currentAuthCode, authCode);
    }

    @Test
    public void test_uc084_sc03_empty() throws SQLException {
        User user = this.registrar;
        String domainName = "uc084-sc03-b.ie";
        assertEquals(null, db().getAuthcodeForDomain(domainName));
        console().login(user);
        ViewDomainUtils.viewDomain(domainName, ContactType.BILL);
        String authCode = getAuthCodeByEmail(user, user, domainName);
        String dbAuthCode = db().getAuthcodeForDomain(domainName);
        assertNotNull(dbAuthCode);
        assertEquals(dbAuthCode, authCode);
    }

    @Test
    public void test_uc084_sc05() throws JSONException, SQLException {
        User user = this.registrar;
        List<String> domainNames = Arrays.asList("uc084-sc05-a.ie", "uc084-sc05-b.ie", "uc084-sc05-c.ie");
        Map<String, String> expectedAuthCodes = getAuthCodesForDomains(domainNames);
        String domainPrefix = "uc084-sc05";
        console().login(user);
        console().view(console().url.allDomains);
        goToAuthCodeConfirmationPage(domainNames, domainPrefix);
        console().clickElement(By.xpath("//a[@class='button' and .='Return']"));
        console().waitForCurrentUrlEquals(console().url.allDomains);
        goToAuthCodeConfirmationPage(domainNames, domainPrefix);
        console().clickElement(By.xpath("//input[@value='Proceed']"));
        console().waitForElementPresent(By.name("CSVFile"));
        Map<String, AuthCode> authCodes = downloadAuthCodes(user);
        checkAuthCodes(authCodes, expectedAuthCodes);
    }

    private String getAuthCode() {
        console().clickElement(By.cssSelector("input[value='Show authcode']"));
        assertTrue("AuthCode alert not present", console().isAlertPresent());
        Alert alert = wd().switchTo().alert();
        String authCode = alert.getText();
        alert.accept();
        Matcher m = Pattern.compile("^The authcode is: ([^\n]+)\n").matcher(authCode);
        assertTrue(authCode, m.find());
        return m.group(1);
    }

    private String getAuthCodeByEmail(User adminC, User billC, String domainName) throws SQLException {
        console().clickElement(By.cssSelector("input[value='Send authcode by email']"));
        console().waitForTextPresentOnPage("Email sent successfully");
        return extractAuthCodeFromReceivedEmails(adminC, billC, domainName);
    }

    private Map<String, String> getAuthCodesForDomains(List<String> domainNames) throws SQLException {
        Map<String, String> authCodes = new HashMap<>();
        for (String domainName : domainNames) {
            authCodes.put(domainName, db().getAuthcodeForDomain(domainName));
        }
        return authCodes;
    }

    private void goToAuthCodeConfirmationPage(List<String> domainNames, String domainPrefix) {
        AllDomainsPage adp = new AllDomainsPage();
        adp.selectDomainsFromList(domainNames, domainPrefix, "gs_A");
        WebElement button = wd().findElement(By.id("gridaction_authcodedownload"));
        assertEquals(String.format("Download Authcodes for %s Domains", domainNames.size()),
                button.getAttribute("value"));
        console().clickElement(button);
        for (String domainName : domainNames) {
            assertTrue(console().isElementPresent(By.xpath(String.format("//label[.='%s']", domainName))));
        }
    }

    private Map<String, AuthCode> downloadAuthCodes(User user) throws JSONException {
        String script = "var callback = arguments[arguments.length - 1];" + "var form = $(\"#CSVAction\");"
                + "var button = $(\"input[name='CSVFile']\");" + "var formData = form.serializeArray();"
                + "formData.push({name: button.attr('name'), value: button.val()});"
                + "var req = $.post(form.attr('action'), $.param(formData), function (data) {"
                + " result = {data: data, headers: req.getAllResponseHeaders()};"
                + " callback(JSON.stringify(result));" + "});";
        Object response = ((JavascriptExecutor) wd()).executeAsyncScript(script);
        JSONObject json = new JSONObject((String) response);
        Map<String, String> headers = getResponseHeaders(json);
        checkHeaders(headers, user);
        String data = json.getString("data");
        return getAuthCodes(data);
    }

    private Map<String, String> getResponseHeaders(JSONObject json) throws JSONException {
        Map<String, String> headers = new HashMap<>();
        String lineSeparator = "\r\n"; // http header separator
        String[] headerLinesArray = json.getString("headers").split(lineSeparator);
        List<String> headerLines = new ArrayList<>(Arrays.asList(headerLinesArray));
        for (String headerLine : headerLines) {
            if ("".equals(headerLine)) {
                continue;
            }
            String[] header = headerLine.split(": ");
            headers.put(header[0], header[1]);
        }
        return headers;
    }

    private Map<String, AuthCode> getAuthCodes(String data) {
        Map<String, AuthCode> authCodes = new HashMap<>();
        String[] headerLinesArray = data.split("\n");
        List<String> authCodeLines = new ArrayList<>(Arrays.asList(headerLinesArray));
        String header = authCodeLines.remove(0);
        assertEquals("\"Domain name\",\"Authcode\",\"Expiration Date\"", header);
        for (String line : authCodeLines) {
            if ("".equals(line)) {
                continue;
            }
            String[] lineValues = line.split(",");
            String domainName = lineValues[0].replace("\"", "");
            String authCode = lineValues[1].replace("\"", "");
            LocalDate expirationDate = new LocalDate(lineValues[2].replace("\"", ""));
            authCodes.put(domainName, new AuthCode(authCode, expirationDate));
        }
        return authCodes;
    }

    private void checkHeaders(Map<String, String> headers, User user) {
        String contentDisposition = headers.get("Content-Disposition");
        String fileName = contentDisposition.substring(contentDisposition.indexOf("filename=") + 9);
        String expectedFileName = String.format("Authcodes_%s-%s.csv", user.login, new LocalDate());
        assertEquals(fileName, expectedFileName, fileName);
        String contentType = headers.get("Content-Type");
        assertTrue(contentType, contentType.contains("text/csv"));
    }

    private void checkAuthCodes(Map<String, AuthCode> authCodes, Map<String, String> expectedAuthCodes) {
        assertEquals(authCodes.keySet().toString(), expectedAuthCodes.size(), authCodes.size());
        for (String domainName : expectedAuthCodes.keySet()) {
            assertTrue(String.format("No authcode for %s", domainName), authCodes.containsKey(domainName));
            AuthCode authCode = authCodes.get(domainName);
            String expectedAuthCode = expectedAuthCodes.get(domainName);
            if (expectedAuthCode == null) {
                assertNotNull(authCode.authCode);
            } else {
                assertEquals(expectedAuthCode, authCode.authCode);
            }
            LocalDate now = new LocalDate();
            assertEquals(14, Days.daysBetween(now, authCode.expirationDate).getDays());
        }
    }

    private class AuthCode {
        String authCode;
        LocalDate expirationDate;

        AuthCode(String authCode, LocalDate expirationDate) {
            this.authCode = authCode;
            this.expirationDate = expirationDate;
        }
    }
}
