package com.iedr.bpr.tests.forms;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.Gui;

public class CheckboxField extends Field {

    public CheckboxField(Gui gui, By selector, By errorMessageElement) {
        super(gui, selector, errorMessageElement);
    }

    public void check() {
        gui.selectElement(selector);
    }

    public void uncheck() {
        gui.deselectElement(selector);
    }

}
