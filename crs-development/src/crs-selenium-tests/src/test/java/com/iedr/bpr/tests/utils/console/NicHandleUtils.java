package com.iedr.bpr.tests.utils.console;

import java.sql.SQLException;
import java.util.Map.Entry;

import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.forms.console.NicHandleForm;
import com.iedr.bpr.tests.pages.console.NewDirectAccountConfirmationPage;
import com.iedr.bpr.tests.pages.console.NewDirectAccountPage;
import com.iedr.bpr.tests.utils.email.EmailAddressUtils;

import static com.iedr.bpr.tests.TestEnvironment.db;
import static com.iedr.bpr.tests.utils.console.TextFieldUtils.verifyTextFieldValidation;
import static org.junit.Assert.fail;

public class NicHandleUtils {

    public static String createNewAccount(String nhName, String emailAddress, String password) throws SQLException {
        NicHandleDetails details = new NicHandleDetails(nhName, emailAddress);
        NewDirectAccountPage nap = new NewDirectAccountPage();
        nap.fillNewAccountForm(details, password);
        nap.submitAndWaitForSuccess();
        NewDirectAccountConfirmationPage nacp = new NewDirectAccountConfirmationPage();
        nacp.submitAndWaitForSuccess();
        String nh = db().getNicHandleByName(nhName);
        return nh;
    }

    public static void verifyEmail(NicHandleForm nhForm) {
        String validEmail = "email@iedr.ie";
        for (Entry<String,  String> entry : EmailAddressUtils.getInvalidEmailAddressMap().entrySet()) {
            String invalidEmail = entry.getKey();
            String invalidEmailMessage = entry.getValue();
            verifyEmailFailure(nhForm, validEmail, invalidEmail, invalidEmailMessage);
        }
    }

    private static void verifyEmailFailure(NicHandleForm nhForm, String validEmail, String invalidEmail,
                String invalidEmailMessage) {
        String emptyEmailMessage = "Email cannot be blank.";
        try {
            verifyTextFieldValidation(nhForm.getEmailField(), validEmail, invalidEmail, invalidEmailMessage,
                    emptyEmailMessage);
        } catch (TimeoutException|AssertionError e) {
            e.printStackTrace();
            fail(String.format("Incorrect email %s shouldn't have passed.", invalidEmail));
        }
    }

}
