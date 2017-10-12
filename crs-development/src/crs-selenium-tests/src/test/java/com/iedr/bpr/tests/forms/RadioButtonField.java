package com.iedr.bpr.tests.forms;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.Gui;
import org.openqa.selenium.WebElement;

import static com.iedr.bpr.tests.TestEnvironment.wd;

public class RadioButtonField extends Field {

    private String name;

    public RadioButtonField(Gui gui, By selector, By errorMessageElement, String name) {
        super(gui, selector, errorMessageElement);
        this.name = name;
    }

    public void choose(String value) {
        By childSelector = By.cssSelector(String.format("input[type='radio'][name='%s'][value='%s']", name, value));
        WebElement option = wd().findElement(selector).findElement(childSelector);
        gui.clickElement(option);
    }

}
