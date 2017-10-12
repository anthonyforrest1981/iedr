package com.iedr.bpr.tests.utils.crsweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;

import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ToolTipsUtils {

    public static void openToolTips(WebElement dialogBox) {
        WebDriver driver = wd();
        Locatable hoverElement = (Locatable) dialogBox;
        Mouse mouse = ((HasInputDevices) driver).getMouse();
        mouse.mouseMove(hoverElement.getCoordinates());
    }
}
