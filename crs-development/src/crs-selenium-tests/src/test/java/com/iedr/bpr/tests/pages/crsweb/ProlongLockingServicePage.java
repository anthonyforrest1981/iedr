package com.iedr.bpr.tests.pages.crsweb;

import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.gui.CrsWebGui;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;

public class ProlongLockingServicePage implements SubmittableForm {

    public static final String ROLL_LOCKING_SERVICE_ID =
            "domainsRollLockingService-confirmRollDates_domainsRollLockingService-doRollDates";

    public void viewAndProlong(String domainName, final ProlongType prolongType) throws SQLException {
        crsweb().view(CrsWebGui.SiteId.ProlongLockingService);
        Date startDate = db().getLockingRenewalDate(domainName);
        String trXPath = "//table[@id='domainRow']//td[text()='" + domainName + "']/..";
        By box = By.xpath(trXPath + "//input[@type='checkbox']");
        if (crsweb().isElementPresentInstantaneously(By.xpath(trXPath))) {
            crsweb().clickElement(box);
            crsweb().waitForTextPresentOnPage("You have selected 1 domain");
            crsweb().clickElement(By.id("domainsRollLockingService-confirmRollDates_action_0"));
        }

        if (prolongType == ProlongType.BY_1_YEAR) {
            oneYearProlong();
            Date prolDate = db().getLockingRenewalDate(domainName);
            Calendar pdc = Calendar.getInstance();
            pdc.setTime(prolDate);
            assertEquals(DateUtils.addYears(DateUtils.truncate(startDate, Calendar.DATE), 1), prolDate);
        } else {
            syncLockingDomain();
            Date syncedDate = db().getLockingRenewalDate(domainName);
            Date domainRnDate = db().getRenewalDateForDomain(domainName);
            assertEquals(syncedDate, domainRnDate);
        }
    }

    private void oneYearProlong() {
        crsweb().clickElement(By.id(ROLL_LOCKING_SERVICE_ID));
    }

    private void syncLockingDomain() {
        crsweb().clickElement(By.className("lockrenewaldate-sync-select"));
        crsweb().clickElement(By.id(ROLL_LOCKING_SERVICE_ID));
    }

    @Override
    public void submit() {
        crsweb().clickElement(By.id(ROLL_LOCKING_SERVICE_ID));
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return new ArrayList<>();
    }

    public enum ProlongType {
        BY_1_YEAR,
        SYNC_WITH_RENEW,
    }
}

