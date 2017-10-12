package com.iedr.bpr.tests.forms;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.gui.Gui;

import static com.iedr.bpr.tests.TestEnvironment.wd;

public abstract class Field implements Form {

    protected Gui gui;
    protected By selector;
    protected By errorMessageElement;

    public Field(Gui gui, By selector, By errorMessageElement) {
        this.gui = gui;
        this.selector = selector;
        this.errorMessageElement = errorMessageElement;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public List<Form> getSubForms() {
        return new ArrayList<>();
    }

    public By getSelector() {
        return selector;
    }

    public boolean hasErrorMessage() {
        return wd().findElement(selector).findElements(errorMessageElement).size() > 0;
    }

    public WebElement getErrorMessageElement() {
        return wd().findElement(selector).findElement(errorMessageElement);
    }

    @Override
    public String toString() {
        return "Field by selector " + selector;
    }
}
