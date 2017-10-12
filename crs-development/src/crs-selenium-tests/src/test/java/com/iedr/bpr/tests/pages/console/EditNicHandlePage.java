package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.console.NicHandleForm;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class EditNicHandlePage implements SubmittableForm {

    private final String operationPrefix = "Nichandle_Details";
    public NicHandleForm nicHandleForm = new NicHandleForm(operationPrefix, true);

    public void view(User user) {
        console().viewProfile(user.login);
        console().clickAndWaitForPageToLoad(By.linkText("Edit Account"));
    }

    public void fillNicHandleForm(NicHandleDetails details) {
        nicHandleForm.fillNicHandleForm(details);
    }

    public void editProfile(User contact, NicHandleDetails details) {
        view(contact);
        fillNicHandleForm(details);
        submitAndWaitForSuccess();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(nicHandleForm);
    }

    @Override
    public void submit() {
        console().clickElement(By.cssSelector("input[type='submit'][value='Update']"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        new WebDriverWait(wd(), 10).until(ExpectedConditions.presenceOfElementLocated(By.id("nic_updated_message")));
        console().waitForTextPresentOnPage("Account saved successfully");
    }

}
