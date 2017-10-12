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

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class CreateNicHandlePage implements SubmittableForm {

    private final String operationPrefix = "Nichandle_Details";
    public NicHandleForm nicHandleForm = new NicHandleForm(operationPrefix, false);

    public void view() {
        console().view(console().url.createNicHandle);
    }

    public void fillNewNicForm(NicHandleDetails details) {
        nicHandleForm.fillNicHandleForm(details);
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
        console().clickElement(By.cssSelector("input[type='submit'][value='Create']"));
    }

    public void submitAndWaitForSuccess() {
        submit();
        new WebDriverWait(wd(), 10).until(ExpectedConditions.presenceOfElementLocated(By.id("nic_created_message")));
        console().waitForTextPresentOnPage("Account Created");
    }
}
