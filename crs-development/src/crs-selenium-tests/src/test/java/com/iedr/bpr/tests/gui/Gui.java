package com.iedr.bpr.tests.gui;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.SeleniumTest;

import static com.iedr.bpr.tests.TestEnvironment.wd;
import static com.iedr.bpr.tests.TestEnvironment.browser;
import static org.junit.Assert.*;

public class Gui {

    public void fillInput(String id, String value) {
        fillInput(By.id(id), value);
    }

    public void fillInput(By by, String value) {
        WebElement input = wd().findElement(by);
        fillInput(input, value);
    }

    public void fillInput(WebElement input, String value) {
        clearInput(input);
        sendKeys(input, value);
    }

    public void clearInput(WebElement input) {
        if (SeleniumTest.Browser.Edge.equals(browser())) {
            // Edge won't clear an element's value unless it's in the viewport. Sending NULL to this element will scroll
            // the viewport, making the element visible.
            input.sendKeys(Keys.NULL);
        }
        input.clear();
    }

    public void sendKeys(WebElement input, String value) {
        if (SeleniumTest.Browser.IE.equals(browser())) {
            input.click();
        }
        input.sendKeys(value == null ? "" : value);
    }

    public void fillInputAndTriggerChangeAndBlurEvents(By by, String value) {
        // When browser window doesn't have focus, change and blur events are not triggered. Some fields in out
        // application (like date fields in crsweb) require both or one of these events to work and we have to trigger
        // them manually.
        fillInput(by, value);
        WebElement element = wd().findElement(by);
        triggerChangeEvent(element);
        triggerBlurEvent(element);
    }

    public void selectOptionByValue(By selectBy, String optionValue) {
        By optionBy = By.cssSelector(String.format("option[value='%s']", optionValue));
        selectOptionBy(selectBy, optionBy);
    }

    public void selectOptionByText(By selectBy, String optionText) {
        By optionBy = By.xpath(String.format("option[.='%s']", optionText));
        selectOptionBy(selectBy, optionBy);
    }

    private void selectOptionBy(By selectBy, By optionBy) {
        WebElement select = wd().findElement(selectBy);
        clickElement(select.findElement(optionBy));
        if (SeleniumTest.Browser.Edge.equals(browser())) {
            triggerChangeEvent(select);
        }
    }

    public void selectElement(By elementBy) {
        WebElement element = wd().findElement(elementBy);
        if (!element.isSelected()) {
            clickElement(element);
        }
    }

    public void deselectElement(By elementBy) {
        WebElement element = wd().findElement(elementBy);
        if (element.isSelected()) {
            clickElement(element);
        }
    }

    public void waitForTextPresentOnPage(String text) {
        // This function is often used to wait for a page, with a certain text,
        // to load, so the default timeout has to be big enough for that.
        waitForTextPresentOnPage(text, 30);
    }

    public void waitForTextPresentOnPage(final String text, int timeout) {
        // WebDriver has an ExpectedCondition for this called
        // textToBePresentInElementLocated, but on IE it sometimes throws an
        // error when getting text from element. We don't want to fail the test
        // in this case, so we're catching every WebDriverException and ignore
        // it.
        try {
            new WebDriverWait(wd(), timeout).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    WebElement html = driver.findElement(By.tagName("html"));
                    try {
                        String htmlText = html.getText();
                        return htmlText.contains(text);
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        } catch (TimeoutException e) {
            fail(String.format("Timed out waiting for text: %s", text));
        }
    }

    public void waitForTextPresentInElement(String text, WebElement element) {
        waitForTextPresentInElement(text, element, 10);
    }

    public void waitForTextPresentInElement(final String text, final WebElement element, int timeout) {
        try {
            new WebDriverWait(wd(), timeout).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    return element.getText().equals(text);
                }
            });
        } catch (TimeoutException e) {
            fail(String.format("Timed out waiting for text: %s", text));
        }
    }

    public boolean isElementPresent(By by) {
        return wd().findElements(by).size() > 0;
    }

    public boolean isElementPresentInstantaneously(By by) {
        return countElementsInstantaneously(by) > 0;
    }

    public void assertElementNotPresent(By by) {
        assertFalse(isElementPresentInstantaneously(by));
    }

    public void assertElementPresent(By by) {
        assertTrue(by.toString(), isElementPresent(by));
    }

    public void waitForElementPresent(final By by) {
        new WebDriverWait(wd(), SeleniumTest.defaultImplicitlyWaitTimeout).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return isElementPresentInstantaneously(by);
            }
        });
    }

    public int countElementsInstantaneously(By by) {
        wd().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            return wd().findElements(by).size();
        } finally {
            wd().manage().timeouts().implicitlyWait(SeleniumTest.defaultImplicitlyWaitTimeout, TimeUnit.SECONDS);
        }
    }

    public void waitForCurrentUrlEquals(final String url) {
        new WebDriverWait(wd(), SeleniumTest.defaultImplicitlyWaitTimeout).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return url.equals(driver.getCurrentUrl());
            }
        });
    }

    public boolean isAlertPresent() {
        try {
            new WebDriverWait(wd(), 3).until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    try {
                        driver.switchTo().alert();
                        return true;
                    } catch (NoAlertPresentException e) {
                        return false;
                    }
                }
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickElement(By elementBy) {
        WebElement element = wd().findElement(elementBy);
        clickElement(element);
    }

    public void clickElement(WebElement element) {
        if (SeleniumTest.Browser.Edge.equals(browser())) {
            // Edge won't click an element unless it's in the viewport. Sending NULL to this element will scroll the
            // viewport, making the element visible.
            try {
                element.sendKeys(Keys.NULL);
            } catch (WebDriverException e) {
                // Unfortunatelly sending keys won't work for some of the elements we want to click. In that case let's
                // hope they are in the viewport.
            }
        }
        element.click();
    }

    public void clickAndWaitForPageToLoad(By linkBy) {
        final WebElement link = wd().findElement(linkBy);
        clickElement(link);
        waitForElementToBecomeStale(link);
    }

    public void clickElementWithEventTriggered(By elementBy) {
        // When Firefox is in background it won't trigger a click event when clicking an element. Most of the times the
        // click is handled by the browser, but in some cases we depend on the javascript event.
        if (SeleniumTest.Browser.Firefox.equals(browser())) {
            triggerClickEvent(wd().findElement(elementBy));
        } else {
            clickElement(elementBy);
        }
    }

    public void waitForElementToBecomeStale(final WebElement element) {
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                try {
                    if (SeleniumTest.Browser.Firefox.equals(browser())) {
                        // Works faster in Firefox, other commands result in a 5s wait.
                        element.findElement(By.id(""));
                    } else {
                        element.isDisplayed();
                    }
                    return false;
                } catch (StaleElementReferenceException e) {
                    return true;
                } catch (WebDriverException e) {
                    return false;
                }
            }
        });
    }

    public void waitForElementTextNotEmpty(final WebElement element) {
        new WebDriverWait(wd(), 5).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                String text = element.getText();
                return text != null && !"".equals(text);
            }
        });
    }

    public void triggerChangeEvent(WebElement target) {
        triggerEvent(target, "change");
    }

    public void triggerBlurEvent(WebElement target) {
        triggerEvent(target, "blur");
    }

    public void triggerClickEvent(WebElement target) {
        triggerEvent(target, "click");
    }

    private void triggerEvent(WebElement target, String eventName) {
        JavascriptExecutor js = (JavascriptExecutor) wd();
        String script = String.format(
                "var target = arguments[0];" +
                        "if (target.on%s) {" +
                        "target.on%s();" +
                        "} else {" +
                        "try {" +
                        "var event = new Event('%s');" +
                        "} catch (e) {" +
                        "var event = document.createEvent('HTMLEvents');" +
                        "event.initEvent('on%s', false, true);" +
                        "}" +
                        "target.dispatchEvent(event);" +
                        "}",
        eventName, eventName, eventName, eventName);
        js.executeScript(script, target);
    }

}
