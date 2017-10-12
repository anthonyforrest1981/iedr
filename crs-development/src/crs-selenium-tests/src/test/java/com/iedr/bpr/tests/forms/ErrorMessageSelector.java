package com.iedr.bpr.tests.forms;

import org.openqa.selenium.By;

public class ErrorMessageSelector {

    public static final By VIA_PARENT_ONE_LEVEL_UP = By.xpath("..//div[contains(@class, 'errorMessage')]");
    public static final By VIA_PARENT_TWO_LEVELS_UP = By.xpath("../..//div[contains(@class, 'errorMessage')]");
    public static final By VIA_PARENT_THREE_LEVELS_UP = By.xpath("../../..//div[contains(@class, 'errorMessage')]");

    public static final By NAMESERVER_NAME = By
            .xpath("../../preceding-sibling::*[1]/div[contains(@class, 'errorMessage')]");
    public static final By NAMESERVER_IP = By.xpath("../preceding-sibling::*[1]/div[contains(@class, 'errorMessage')]");

    public static final By BELOW = VIA_PARENT_ONE_LEVEL_UP;
    public static final By IN_TABLE = VIA_PARENT_THREE_LEVELS_UP;

}
