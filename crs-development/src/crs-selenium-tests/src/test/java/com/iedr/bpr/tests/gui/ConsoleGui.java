package com.iedr.bpr.tests.gui;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;
import com.iedr.bpr.tests.TestConfig;
import com.iedr.bpr.tests.pages.console.LoginPage;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.browser;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class ConsoleGui extends Gui {
    public ConsoleUrlProvider url;
    Properties config;

    public ConsoleGui(ConsoleUrlProvider url) throws IOException {
        config = TestConfig.getConfig();
        this.url = url;
    }

    public void selectOptionByValue(By selectBy, String optionValue) {
        WebElement select = wd().findElement(selectBy);
        By optionBy = By.cssSelector(String.format("option[value='%s']", optionValue));
        String optionText = select.findElement(optionBy).getAttribute("innerHTML");
        selectOptionByText(select, optionText);
    }

    public void selectOptionByText(By selectBy, String optionText) {
        WebElement select = wd().findElement(selectBy);
        selectOptionByText(select, optionText);
    }

    private void selectOptionByText(WebElement select, String optionText) {
        WebElement selectricWrapper = select.findElement(By.xpath("../.."));
        String wrapperClass = selectricWrapper.getAttribute("class");
        if (wrapperClass != null && wrapperClass.contains("selectric-wrapper")) {
            WebElement label = selectricWrapper.findElement(By.cssSelector(".selectric > .label"));
            WebElement option = selectricWrapper.findElement(By.xpath(String.format(".//li[.='%s']", optionText)));
            clickElement(label);
            clickElement(option);
        } else {
            // It is a regular HTML select.
            clickElement(select.findElement(By.xpath(String.format("option[.='%s']", optionText))));
        }
        if (SeleniumTest.Browser.Edge.equals(browser())) {
            triggerChangeEvent(select);
        }
    }

    public void login(User user) {
        LoginPage lp = new LoginPage();
        lp.login(user);
    }

    public boolean loggedInAs(String userName) {
        By by = By.xpath("//iframe[@src='/index.php?r=site/summaryframe']");
        if (!isElementPresentInstantaneously(by)) {
            return false;
        }
        WebElement iframe = wd().findElement(by);
        wd().switchTo().frame(iframe);
        WebElement link = wd().findElement(By.xpath("//a[normalize-space()='My Account']"));
        boolean result = link.getAttribute("onclick").contains(userName);
        wd().switchTo().defaultContent();
        return result;
    }

    public void forceLogout() {
        wd().get(url.logout);
    }

    public void viewMyAccount() {
        viewMyAccount(1);
    }

    private void viewMyAccount(int attempt) {
        int attemptsLimit = 3;
        By iframeBy = By.xpath("//iframe[@src='/index.php?r=site/summaryframe']");
        WebElement iframe = wd().findElement(iframeBy);
        wd().switchTo().frame(iframe);
        By linkBy = By.xpath("//a[normalize-space()='My Account']");
        try {
            clickElement(linkBy);
        } catch (StaleElementReferenceException e) {
            // Sometimes we get this error on IE, when it's in the background. I haven't found the cause, it's either
            // a bug in IE driver or some race condition that I couldn't figure out.
            // This workaround seems to be enough, I never saw two failed attempts in a row.
            if (attempt < attemptsLimit) {
                viewMyAccount(attempt + 1);
            } else {
                throw e;
            }
        }
        wd().switchTo().defaultContent();
    }

    public void checkTacMenuItems() {
        Set<String> tacMenuItems = new HashSet<>(Arrays.asList("Home", "Domains", "Reports", "All Domains",
                "Registrant Transfer", "View Your Sell Requests", "Tickets", "DNS"));
        assertEquals(tacMenuItems, getMenuItems());
    }

    private Set<String> getMenuItems() {
        Set<String> menuItems = new HashSet<>();
        for (WebElement item : wd().findElements(By.xpath("//ul[@id='yw0']//li"))) {
            menuItems.addAll(getSubMenuItems(item));
        }
        return menuItems;
    }

    private Set<String> getSubMenuItems(WebElement item) {
        // Selenium won't show the text of a hidden element, so for each expandable menu item, we have to move cursor to
        // it, to display its children.
        Set<String> menuItems = new HashSet<>();
        String itemText = item.getText();
        if (itemText.contains("»")) {
            menuItems.add(itemText.split("»")[0].trim());
            new Actions(wd()).moveToElement(item).perform();
            new WebDriverWait(wd(), 5).until(ExpectedConditions.visibilityOf(item.findElement(By.xpath("./ul"))));
            for (WebElement child : item.findElements(By.tagName("li"))) {
                menuItems.addAll(getSubMenuItems(child));
            }
        } else {
            menuItems.add(item.getText());
        }
        return menuItems;
    }

    public void viewProfile(String profileId) {
        view(url.viewNicHandle + "&id=" + profileId);
    }

    public void view(final String pageUrl) {
        final boolean sameStartingUrl = pageUrl.equals(wd().getCurrentUrl());
        WebElement html = null;
        if (sameStartingUrl) {
            html = wd().findElement(By.tagName("html"));
        }
        wd().get(pageUrl);
        // Make sure we loaded the desired page.
        try {
            if (sameStartingUrl) {
                waitForElementToBecomeStale(html);
            }
            new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver wd) {
                    return pageUrl.equals(wd.getCurrentUrl());
                }
            });
        } catch (TimeoutException e) {
            assertEquals(pageUrl, wd().getCurrentUrl());
        }
    }

    private void waitForElementValidated(WebElement element, int timeout, boolean strict) {
        final WebElement parent = element.findElement(By.xpath(".."));
        try {
            new WebDriverWait(wd(), timeout).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    return parent.getAttribute("class").contains("success");
                }
            });
        } catch (TimeoutException e) {
            // Some forms don't trigger validation on blur event if the value hasn't changed. In those cases "strict"
            // parameter can be set to false, so that an error isn't raised.
            if (strict) {
                throw e;
            }
        }
    }

    public void waitForElementValidationFailed(WebElement element, int timeout) {
        final WebElement parent = element.findElement(By.xpath(".."));
        new WebDriverWait(wd(), timeout).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return parent.getAttribute("class").contains("error");
            }
        });
    }

    public void triggerFormValidation() {
        wd().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            List<WebElement> errors = wd().findElements(
                    By.cssSelector(".row.error input[type='text']," + ".row.error input[type='checkbox'],"
                            + ".row.error input[type='password']," + ".row.error textarea," + ".row.error select"));
            for (WebElement element : errors) {
                // If there are some errors, trigger validation once again to
                // make sure they weren't caused by timing issues.
                triggerValidationForElement(element);
                waitForElementValidated(element, 1, false);
            }
        } finally {
            wd().manage().timeouts().implicitlyWait(SeleniumTest.defaultImplicitlyWaitTimeout, TimeUnit.SECONDS);
        }
    }

    public void triggerValidationForElement(WebElement element) {
        if (SeleniumTest.Browser.Firefox.equals(browser())) {
            // If Firefox is in the background, it doesn't trigger change and blur events, which should be the result of
            // pressing the Tab key.
            triggerChangeEvent(element);
            triggerBlurEvent(element);
        } else {
            element.sendKeys(Keys.TAB);
        }
    }

    public void triggerValidationForElementAndWait(WebElement element) {
        triggerValidationForElement(element);
        waitForElementValidated(element, 5, true);
    }

    public String getNewTicketMessage(String domainName) {
        return String.format("Your ticket for %s has been received and will be"
                + " processed by our hostmasters in due course.", domainName);
    }

    public static class CardTypeXPath {
        public static String MASTER = "//img[@src='images/master.gif']";
        public static String VISA = "//img[@src='images/nvisa.gif']";
        public static String DEBIT_VISA = "//img[@src='images/nvisadeb.gif']";
    }

}
