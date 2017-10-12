package com.iedr.bpr.tests.forms.crsweb;

import com.iedr.bpr.tests.forms.DomainContactsForm;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;

public class CrsWebDomainContactsForm extends DomainContactsForm {

    private static ErrorMessages errorMessages = new ErrorMessages("Duplicate admin contact", "Not a valid Nic Handle",
            "Not a valid Nic Handle", "Not a valid Nic Handle", "You must enter a value for an admin contact",
            "You must enter a value for a tech contact");

    public CrsWebDomainContactsForm(String admin1FieldId, String admin2FieldId, String techFieldId) {
        super(crsweb(), admin1FieldId, admin2FieldId, techFieldId);
    }

    @Override
    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
