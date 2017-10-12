package com.iedr.bpr.tests.forms.console;

import com.iedr.bpr.tests.forms.DomainContactsForm;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class ConsoleDomainContactsForm extends DomainContactsForm {

    private ErrorMessages errorMessages;

    public ConsoleDomainContactsForm(String admin1FieldId, String admin2FieldId, String techFieldId,
            String admin1FieldLabel, String admin2FieldLabel, String techFieldLabel) {
        super(console(), admin1FieldId, admin2FieldId, techFieldId);
        errorMessages = new ErrorMessages("Duplicated admin contact", admin1FieldLabel
                + " is not an existing Account", admin2FieldLabel + " is not an existing Account", techFieldLabel
                + " is not an existing Account", admin1FieldLabel + " cannot be blank.", techFieldLabel
                + " cannot be blank.");
    }

    @Override
    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }
}
