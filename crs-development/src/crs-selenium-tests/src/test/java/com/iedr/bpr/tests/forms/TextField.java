package com.iedr.bpr.tests.forms;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.Gui;

import static com.iedr.bpr.tests.TestEnvironment.wd;

public class TextField extends Field {

    private boolean acceptsAnyString;

    public TextField(Gui gui, By selector, By errorMessageElement, boolean acceptsAnyString) {
        super(gui, selector, errorMessageElement);
        this.acceptsAnyString = acceptsAnyString;
    }

    /*
    * Indicates if all formats are accepted. Should be set to false if the value of the field will be interpreted as a
    * number, date etc.
    * */
    public boolean acceptsAnyString() {
        return acceptsAnyString;
    }

    public void fill(String value) {
        gui.fillInput(selector, value);
    }

    public String getValue() {
        return wd().findElement(selector).getAttribute("value");
    }

}
