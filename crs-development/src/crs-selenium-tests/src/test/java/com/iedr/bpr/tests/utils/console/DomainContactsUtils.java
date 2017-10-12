package com.iedr.bpr.tests.utils.console;

import org.openqa.selenium.TimeoutException;

import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.forms.DomainContactsForm;
import com.iedr.bpr.tests.forms.SubmittableForm;
import com.iedr.bpr.tests.gui.Gui;
import com.iedr.bpr.tests.utils.User;

import static org.junit.Assert.fail;

public class DomainContactsUtils {

    public static void verifyAll(User user, String suspendedUser, DomainContactsForm contactsForm,
            SubmittableForm containingForm, Gui gui) {
        DomainContactsForm.ErrorMessages errorMessages = contactsForm.getErrorMessages();
        // empty Admin Contact 1
        verifySingle(null, null, user.login, errorMessages.emptyAdmin1Message,
                "Empty admin contact 1 shouldn't have passed.", contactsForm, containingForm, gui);
        // non-existing Admin Contact 1
        verifySingle("ZZZ999-IEDR", null, user.login, errorMessages.invalidAdmin1Message,
                "Non-existing admin contact 1 shouldn't have passed.", contactsForm, containingForm, gui);
        // suspended or deleted Admin Contact 1
        verifySingle(suspendedUser, null, user.login, errorMessages.invalidAdmin1Message,
                "Suspended admin contact 1 shouldn't have passed.", contactsForm, containingForm, gui);
        // non-existing Admin Contact 2
        verifySingle(user.login, "ZZZ999-IEDR", user.login, errorMessages.invalidAdmin2Message,
                "Non-existing admin contact 2 shouldn't have passed.", contactsForm, containingForm, gui);
        // suspended or deleted Admin Contact 2
        verifySingle(user.login, suspendedUser, user.login, errorMessages.invalidAdmin2Message,
                "Suspended admin contact 2 shouldn't have passed.", contactsForm, containingForm, gui);
        // duplicate Admin Contacts
        verifySingle(user.login, user.login, user.login, errorMessages.duplicateMessage,
                "Duplicate admin contacts shouldn't have passed.", contactsForm, containingForm, gui);
        // empty Tech Contact
        verifySingle(user.login, null, null, errorMessages.emptyTechMessage,
                "Empty tech contact shouldn't have passed.", contactsForm, containingForm, gui);
        // non-existing Tech Contact
        verifySingle(user.login, null, "ZZZ999-IEDR", errorMessages.invalidTechMessage,
                "Non-existing Tech Contact shouldn't have passed.", contactsForm, containingForm, gui);
        // suspended or deleted Tech Contact
        verifySingle(user.login, null, suspendedUser, errorMessages.invalidTechMessage,
                "Suspended tech contact 1 shouldn't have passed.", contactsForm, containingForm, gui);
    }

    private static void verifySingle(String adminNh1, String adminNh2, String techNh, String message,
            String failureMessage, DomainContactsForm contactsForm, SubmittableForm containingForm, Gui gui) {
        contactsForm.fillDomainContacts(new DomainContactFormDetails(adminNh1, adminNh2, techNh));
        containingForm.submit();
        try {
            gui.waitForTextPresentOnPage(message);
        } catch (TimeoutException e) {
            fail(failureMessage);
        }
    }

}
