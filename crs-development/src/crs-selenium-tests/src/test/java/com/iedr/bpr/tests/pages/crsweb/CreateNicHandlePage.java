package com.iedr.bpr.tests.pages.crsweb;

import java.sql.SQLException;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class CreateNicHandlePage {

    public void view() {
        crsweb().view(SiteId.NicHandlesCreate);
    }

    public void fillNicHandleDetails(NicHandleDetails details) throws SQLException {
        crsweb().fillInput(By.name("nicHandleWrapper.name"), details.getNhName());
        crsweb().fillInput(By.name("nicHandleWrapper.companyName"), details.getCompany());
        crsweb().selectOptionByText(By.id("nic-handle-create-input_nicHandleWrapper_accountNumber"),
                db().getGuestAccountName());
        crsweb().fillInput(By.name("nicHandleWrapper.email"), details.getEmail());
        crsweb().fillInput(By.name("nicHandleWrapper.address"), details.getAddress());
        crsweb().clickElement(By.xpath("//input[@onclick='addRow_Phone()']"));
        crsweb().fillInput(By.cssSelector("#nsTable_Phone input[name='nicHandleWrapper.phonesWrapper.phone']"),
                details.getPhone());
        crsweb().selectOptionByText(By.cssSelector("#nic-handle-create-input_[onchange='updateCountryValue(this)']"),
                details.getCountry());
        crsweb().selectOptionByText(getVisibleCountySelect(), details.getCounty());
        crsweb().clickElement(By.xpath("//input[@value='Save']"));
    }

    private By getVisibleCountySelect() {
        return By.xpath("//div[starts-with(@id, 'countySelect_nicHandleWrapper') "
                + "and not(contains(@style, 'display: none'))]/select");
    }

}
