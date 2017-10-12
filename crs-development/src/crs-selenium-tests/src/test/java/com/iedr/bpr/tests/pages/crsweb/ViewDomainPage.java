package com.iedr.bpr.tests.pages.crsweb;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.formdetails.crsweb.DomainModificationDetails;
import com.iedr.bpr.tests.forms.DomainContactsForm;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.NameserverForm;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.crsweb.CrsWebDomainContactsForm;
import com.iedr.bpr.tests.forms.crsweb.CrsWebDomainNameserverForm;
import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertTrue;

public class ViewDomainPage implements SubmittableForm {

    public DomainContactsForm contactsForm =
            new CrsWebDomainContactsForm("adminContact1", "adminContact2", "techContact");
    public NameserverForm nameserverForm = new CrsWebDomainNameserverForm();

    public void view(String domainName) {
        crsweb().view(SiteId.DomainsSearch);
        String trXPath = "//table[@id='domainRow']//td[text()='" + domainName + "']/..";
        By img = By.xpath(trXPath + "//img[@title='View']");
        if (crsweb().isElementPresentInstantaneously(By.xpath(trXPath))) {
            crsweb().clickElement(img);
        } else {
            crsweb().fillInput("domains-search_searchCriteria_domainName", domainName);
            wd().findElement(By.id("domains-search_searchCriteria_domainName")).sendKeys(Keys.RETURN);
            crsweb().waitForElementPresent(By.xpath(trXPath));
            crsweb().clickElement(img);
        }
    }

    public void edit(String domainName) {
        view(domainName);
        crsweb().clickElement(By.id("domainview_domainedit_input"));
        crsweb().waitForTextPresentOnPage("Domain Edit");
    }

    public void fillModificationDetails(DomainModificationDetails details) {
        if (details.getDomainHolder() != null) {
            crsweb().fillInput(By.name("domainWrapper.domainHolder"), details.getDomainHolder());
        }
        if (details.getDomainClass() != null) {
            crsweb().selectOptionByText(By.name("domainWrapper.holderClassId"), details.getDomainClass());
        }
        if (details.getDomainCategory() != null) {
            String classId = wd().findElement(By.name("domainWrapper.holderClassId")).getAttribute("value");
            String categorySelectContainerId = "categorySelect_domainEditHolderEntities_" + classId;
            crsweb().selectOptionByText(By.cssSelector(String.format("#%s > select", categorySelectContainerId)),
                    details.getDomainCategory());
        }
        if (details.getContactDetails() != null) {
            contactsForm.fillDomainContacts(details.getContactDetails());
        }
        if (details.getDnsDetails() != null) {
            nameserverForm.fillNameserverDetails(details.getDnsDetails());
        }

        crsweb().fillInput(By.id("domainedit_domainWrapper_newRemark"), details.getRemark());
        submit();
        crsweb().waitForTextPresentOnPage("Domain View");
    }

    public void changeDomainHolderType(String domainName, String type) {
        view(domainName);
        crsweb().clickElement(By.id("openChangeHolderTypeDialog"));
        crsweb().clickElement(By.xpath("//select[@id='holderType_type']/option[contains(@value, '" + type + "')]"));
        crsweb().clickElement(By.xpath("//div[contains(@id, 'changeHolderTypeDialog')]//input[@value='Submit']"));
        new WebDriverWait(wd(), 10).until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//div[contains(@id, 'changeHolderTypeDialog')]")));
    }

    public void performDnsCheck(String domainName) {
        view(domainName);
        crsweb().clickElement(By.id("domainview_domainview-dnsCheck"));
    }

    public void sendAuthCode(String domainName) {
        view(domainName);
        crsweb().clickElement(By.id("domainview_domainview-sendAuthCode"));
        crsweb().waitForTextPresentOnPage("Email sent successfully");
    }

    public List<HistoricalDomainRow> getHistoricalRows(String domainName) {
        List<HistoricalDomainRow> rows = new ArrayList<>();
        view(domainName);
        List<WebElement> trs = wd().findElements(By.cssSelector("#domainRow > tbody > tr"));
        for (WebElement tr : trs) {
            List<WebElement> tds = tr.findElements(By.cssSelector("td"));
            rows.add(new HistoricalDomainRow(tds.get(0).getText(), tds.get(4).getText()));
        }
        return rows;
    }

    public void setLockingStatus(String domainName, boolean enterLocking) {
        setLockingStatus(domainName, enterLocking, false);
    }

    public void removeFromLocking(String domainName) {
        setLockingStatus(domainName, false, true);
    }

    private void setLockingStatus(String domainName, boolean enterLocking, boolean removeFromLocking) {
        view(domainName);
        crsweb().clickElement(By.id("openChangeLockDialog"));
        String dialogXPath = "//div[contains(@id, 'changeLock')]";
        String message = enterLocking ? "Locking domain" : "Unlocking domain";
        assertTrue(wd().findElement(By.xpath(dialogXPath)).getText().contains(message));
        if (!enterLocking && removeFromLocking) {
            crsweb().clickElement(By.xpath(dialogXPath + "//input[contains(@type, 'checkbox')]"));
        }
        crsweb().clickElement(By.xpath(dialogXPath + "//input[contains(@type, 'Submit')]"));
        new WebDriverWait(wd(), 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(dialogXPath)));
    }

    public void alterStatus() {
        crsweb().clickElement(By.id("openAlterStatusDialog"));
    }

    @Override
    public void submit() {
        crsweb().clickElement(By.id("domainedit__save"));
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return new ArrayList<>();
    }


    public class HistoricalDomainRow {
        public String date;
        public String remark;

        public HistoricalDomainRow(String date, String remark) {
            this.date = date;
            this.remark = remark;
        }

    }
}
