package com.iedr.bpr.tests.pages.crsweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class AccountPage {

    private User user;

    public AccountPage(User user) {
        this.user = user;
    }

    public void viewDeposit() {
        view();
        crsweb().clickElement(By.id("account-view-view_deposit-view-input"));
    }

    public void view() {
        crsweb().view(SiteId.AccountsSearch);
        crsweb().fillInput("accounts-search_searchCriteria_nicHandle", user.login);
        crsweb().clickElement(By.id("accounts-search_0"));
        crsweb().clickElement(By.xpath("//img[contains(@title, 'View')]"));
    }


    public void topUpAccount(float amount, String remark, boolean doubleClick) {
        viewDeposit();
        crsweb().clickElement(By.id("deposit-view-input_deposit-topup-input"));
        crsweb().fillInput(By.name("topUpAmount"), String.valueOf(amount));
        crsweb().fillInput(By.name("remark"), remark);
        WebElement topUpButton = wd().findElement(By.id("deposit-topup-input_deposit-topup-topup"));
        if (doubleClick) {
            new Actions(wd()).doubleClick(topUpButton).perform();
        } else {
            crsweb().clickElement(topUpButton);
        }
        crsweb().waitForTextPresentOnPage("Current deposit status");
    }

    public void correctAccount(float amount, String remark) {
        viewDeposit();
        crsweb().clickElement(By.id("deposit-view-input_deposit-correct-input"));
        crsweb().fillInput(By.name("correctionAmount"), String.valueOf(amount));
        crsweb().fillInput(By.name("remark"), remark);
        crsweb().clickElement(By.id("deposit-correct-input_deposit-correct-correct"));
    }

}
