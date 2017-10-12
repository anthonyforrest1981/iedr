package com.iedr.bpr.tests.pages.crsweb;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class BulkTransferPage {

    public void view(String nicHandle) {
        crsweb().viewNicHandle(nicHandle);
        crsweb().clickElement(By.id("nic-handle-view_nic-handle-edit_input"));
    }

    public void createBulkTransfer(int losingAccount, int gainingAccount) {
        crsweb().view(SiteId.BulkTransferCreate);
        crsweb().selectOptionByValue(By.id("bulkTransferCreate-create_losingAccountId"), String.valueOf(losingAccount));
        crsweb().selectOptionByValue(By.id("bulkTransferCreate-create_gainingAccountId"),
                String.valueOf(gainingAccount));
        crsweb().fillInput("bulkTransferCreate-create_remarks", "New bulk transfer");
        crsweb().clickElement(By.id("bulkTransferCreate-create_0"));
    }

    public void completeBulkTransfer(List<String> domains) {
        completeBulkTransferNoConfirmation(domains);
        crsweb().waitForTextPresentOnPage("Completion date");
    }

    public void completeBulkTransferNoConfirmation(List<String> domains) {
        String domainsStr = StringUtils.join(domains, ", ");
        crsweb().fillInput("bulkTransferView-input_newDomains", domainsStr);
        crsweb().clickElement(By.id("bulkTransferView-input_bulkTransferView-addDomains"));
        crsweb().clickElement(By.id("bulkTransferView-input_bulkTransferView-transferAll"));
    }

}
