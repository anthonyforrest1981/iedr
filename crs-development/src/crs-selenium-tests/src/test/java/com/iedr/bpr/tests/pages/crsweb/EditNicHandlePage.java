package com.iedr.bpr.tests.pages.crsweb;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class EditNicHandlePage {

    private List<NicHandleChange> changes;

    public EditNicHandlePage() {
        changes = new ArrayList<>();
    }

    public void view(String nicHandle) {
        crsweb().viewNicHandle(nicHandle);
        crsweb().clickElement(By.id("nic-handle-view_nic-handle-edit_input"));
    }

    public void addInputFieldChange(By by, String value) {
        changes.add(new InputFieldNicHandleChange(by, value));
    }

    public void addSelectFieldChange(By by, String value) {
        changes.add(new SelectFieldNicHandleChange(by, value));
    }

    private void editNicHandleNoConfirmation(String nicHandle) {
        view(nicHandle);
        for (NicHandleChange change : changes) {
            change.apply();
        }
        crsweb().fillInput(By.id("nic-handle-edit_hostmastersRemark"), "Remark");
        crsweb().clickElement(By.id("nic-handle-edit_nic-handle-edit_save"));
    }

    public void editNicHandle(String nicHandle) {
        editNicHandleNoConfirmation(nicHandle);
        // Wait for edit button to reappear.
        new WebDriverWait(wd(), 10).until(ExpectedConditions.presenceOfElementLocated(By
                .id("nic-handle-view_nic-handle-edit_input")));
    }

    public void editNicHandleError(String nicHandle, String errorMessage) {
        editNicHandleNoConfirmation(nicHandle);
        crsweb().waitForTextPresentOnPage(errorMessage);
    }

    private abstract class NicHandleChange {
        protected By by;
        protected String value;

        public NicHandleChange(By by, String value) {
            this.by = by;
            this.value = value;
        }

        public abstract void apply();
    }

    private class InputFieldNicHandleChange extends NicHandleChange {
        public InputFieldNicHandleChange(By by, String value) {
            super(by, value);
        }

        public void apply() {
            crsweb().fillInput(by, value);
        }
    }

    private class SelectFieldNicHandleChange extends NicHandleChange {
        public SelectFieldNicHandleChange(By by, String value) {
            super(by, value);
        }

        public void apply() {
            crsweb().selectOptionByValue(by, value);
        }
    }

}
