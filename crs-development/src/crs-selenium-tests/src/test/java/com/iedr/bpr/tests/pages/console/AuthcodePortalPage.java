package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class AuthcodePortalPage implements SubmittableForm {

    public TextField domainName;
    public TextField email;
    public TextField captcha;

    public AuthcodePortalPage() {
        domainName = new TextField(console(), By.id("AuthcodePortalModel_domain_name"),
                ErrorMessageSelector.VIA_PARENT_TWO_LEVELS_UP, true);
        email = new TextField(console(), By.id("AuthcodePortalModel_email"),
                ErrorMessageSelector.VIA_PARENT_TWO_LEVELS_UP, true);
        captcha = new TextField(console(), By.id("AuthcodePortalModel_verifyCode"),
                ErrorMessageSelector.VIA_PARENT_TWO_LEVELS_UP, false);
    }

    public void view() {
        wd().get(console().url.authcodePortal);
    }

    @Override
    public void submit() {
        console().clickElement(By.name("yt0"));
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.<Form>asList(domainName, email, captcha);
    }
}
