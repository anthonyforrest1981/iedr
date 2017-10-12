package com.iedr.bpr.tests.utils.console;

import com.google.common.base.Predicate;
import com.iedr.bpr.tests.forms.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class TextFieldUtils {

    public static void verifyTextFieldValidation(TextField field, String validValue, String invalidValue,
                                                  String invalidValueMessage, String emptyValueMessage) {
        WebElement fieldElement = wd().findElement(field.getSelector());
        clearField(fieldElement, emptyValueMessage);
        fillFieldWithValidValue(fieldElement, validValue);
        clearField(fieldElement, emptyValueMessage);
        fillFieldWithInvalidValue(fieldElement, invalidValue, invalidValueMessage, emptyValueMessage);
    }

    public static void clearField(WebElement fieldElement, String emptyValueMessage) {
        boolean initialErrorMessagePresent = false;
        String initialErrorMessage = null;
        WebElement errorMessage = getErrorMessage(fieldElement);
        if (errorMessage.isDisplayed()) {
            initialErrorMessagePresent = true;
            initialErrorMessage = errorMessage.getText();
        }
        console().clickElement(fieldElement);
        console().clearInput(fieldElement);
        console().triggerValidationForElement(fieldElement);
        if (initialErrorMessagePresent) {
            waitForErrorMessageChanged(fieldElement, initialErrorMessage);
        } else {
            waitForErrorMessageDisplayed(fieldElement);
        }
        assertEquals(emptyValueMessage, getErrorMessage(fieldElement).getText());
    }

    public static void fillFieldWithValidValue(WebElement fieldElement, String validValue) {
        console().sendKeys(fieldElement, validValue);
        console().triggerValidationForElementAndWait(fieldElement);
        waitForErrorMessageHidden(fieldElement);
    }

    public static void fillFieldWithInvalidValue(WebElement fieldElement, String invalidValue,
                                                 String invalidValueMessage, String previousMessage) {
        console().sendKeys(fieldElement, invalidValue);
        console().triggerValidationForElement(fieldElement);
        console().waitForElementValidationFailed(fieldElement, 10);
        waitForErrorMessageChanged(fieldElement, previousMessage);
        assertEquals(invalidValueMessage, getErrorMessage(fieldElement).getText());
    }

    public static void waitForErrorMessageChanged(WebElement field, final String previousMessage) {
        final WebElement errorMessage = getErrorMessage(field);
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return !previousMessage.equals(errorMessage.getText());
            }
        });
    }

    public static void waitForErrorMessageDisplayed(WebElement field) {
        final WebElement errorMessage = getErrorMessage(field);
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return errorMessage.isDisplayed();
            }
        });
    }

    public static void waitForErrorMessageHidden(WebElement field) {
        final WebElement errorMessage = getErrorMessage(field);
        new WebDriverWait(wd(), 10).until(new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver input) {
                return !errorMessage.isDisplayed();
            }
        });
    }

    private static WebElement getErrorMessage(WebElement field) {
        return field.findElement(By.xpath("../../..//div[contains(@class, 'errorMessage')]"));
    }

}
