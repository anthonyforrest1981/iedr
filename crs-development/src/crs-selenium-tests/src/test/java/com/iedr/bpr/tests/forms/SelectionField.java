package com.iedr.bpr.tests.forms;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.Gui;

public class SelectionField extends Field {

    public SelectionField(Gui gui, By selector, By errorMessageElement) {
        super(gui, selector, errorMessageElement);
    }

    public void fill(String value) {
        gui.selectOptionByValue(selector, value);
    }

    public void fillWithText(String text) {
        gui.selectOptionByText(selector, text);
    }
}
