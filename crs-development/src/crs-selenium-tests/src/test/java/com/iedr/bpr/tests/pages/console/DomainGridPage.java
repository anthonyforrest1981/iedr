package com.iedr.bpr.tests.pages.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public abstract class DomainGridPage {

    public void filterDomainsByName(String domainName) {
        filterDomainsIfNeeded(domainName, "gs_A");
    }

    public void filterDomainsIfNeeded(String domainPrefix, String filterFieldId) {
        waitForAnyResults();
        if (domainsNeedFiltering(domainPrefix)) {
            filterDomains(domainPrefix, filterFieldId);
        }
    }

    private void waitForAnyResults() {
        new WebDriverWait(wd(), 5).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return !getVisibleRows().isEmpty();
            }
        });
    }

    private boolean domainsNeedFiltering(String domainPrefix) {
        List<WebElement> visibleRows = getVisibleRows();
        for (WebElement row : visibleRows) {
            String domainName = getDomainNameFromRow(row);
            if (!domainName.contains(domainPrefix)) {
                return true;
            }
        }
        return false;
    }

    private WebElement getRowById(String id) {
        String css = String.format("#thisJqGrid > tbody > tr.jqgrow[id='%s']", id);
        return wd().findElement(By.cssSelector(css));
    }

    private List<WebElement> getVisibleRows() {
        waitForResultsToBeDisplayed();
        List<WebElement> rows;
        wd().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            rows = wd().findElements(By.cssSelector("#thisJqGrid > tbody > tr.jqgrow"));
        } finally {
            wd().manage().timeouts().implicitlyWait(SeleniumTest.defaultImplicitlyWaitTimeout, TimeUnit.SECONDS);
        }
        return rows;
    }

    private List<WebElement> getSelectedRows() {
        waitForResultsToBeDisplayed();
        List<WebElement> rows;
        wd().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            rows = wd().findElements(
                    By.xpath("//tr[contains(@class,'jqgrow')]/td[@aria-describedby='thisJqGrid_cb']"
                            + "/input[@checked='checked']/../.."));
        } finally {
            wd().manage().timeouts().implicitlyWait(SeleniumTest.defaultImplicitlyWaitTimeout, TimeUnit.SECONDS);
        }
        return rows;
    }

    private void waitForResultsToBeDisplayed() {
        console().waitForElementPresent(By.id("gbox_thisJqGrid"));
        new WebDriverWait(wd(), 30).until(ExpectedConditions.invisibilityOfElementLocated(By.id("load_thisJqGrid")));
    }

    private WebElement findCheckboxInRow(WebElement row) {
        return row.findElement(By.cssSelector("td[aria-describedby='thisJqGrid_cb'] > input[type='checkbox']"));
    }

    private String getDomainNameFromRow(WebElement row) {
        return row.getAttribute("id");
    }

    public List<String> getVisibleDomainNames() {
        List<WebElement> visibleDomains = getVisibleRows();
        List<String> names = new ArrayList<String>();
        for (WebElement domain : visibleDomains) {
            String domainName = domain.getAttribute("id").replace("jqg_thisJqGrid_", "");
            names.add(domainName);
        }
        return names;
    }

    private void checkVisibleDomainsFromList(List<String> domainNames) {
        List<WebElement> visibleRows = getVisibleRows();
        for (WebElement row : visibleRows) {
            String domainName = getDomainNameFromRow(row);
            WebElement checkbox = findCheckboxInRow(row);
            if (domainNames.contains(domainName) && !checkbox.isSelected()) {
                console().clickElement(checkbox);
            }
        }
    }

    public void filterDomains(final String prefix, String filterFieldId) {
        final WebElement firstRow = getVisibleRows().get(0);
        console().fillInput(filterFieldId, prefix);
        wd().findElement(By.id(filterFieldId)).sendKeys(Keys.END);
        // Wait for the firstRow to become a stale element reference, which will confirm that the results were reloaded.
        console().waitForElementToBecomeStale(firstRow);
    }

    public void clearFilter(String filterFieldId) {
        filterDomains("", filterFieldId);
    }

    public void selectDomainsFromList(final List<String> domainNames, String domainPrefix, String filterFieldId) {
        filterDomainsIfNeeded(domainPrefix, filterFieldId);
        checkVisibleDomainsFromList(domainNames);
        // Make sure we selected all domains.
        new WebDriverWait(wd(), 5).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                List<WebElement> selectedRows = getSelectedRows();
                List<String> selectedDomainNames = getDomainNames(selectedRows);
                return selectedDomainNames.containsAll(domainNames);
            }
        });
    }

    public void selectDomainFromList(String domainName) {
        List<String> domainNames = Arrays.asList(domainName);
        selectDomainsFromList(domainNames, domainName, "gs_PK");
    }

    private List<String> getDomainNames(List<WebElement> rows) {
        List<String> domainNames = new ArrayList<String>();
        for (WebElement row : rows) {
            domainNames.add(getDomainNameFromRow(row));
        }
        return domainNames;
    }

    public String getFieldValue(String domainName, String columnId) {
        WebElement row = getRowById(domainName);
        String css = String.format("td[aria-describedby='%s']", "thisJqGrid_" + columnId);
        return row.findElement(By.cssSelector(css)).getText();
    }

}
