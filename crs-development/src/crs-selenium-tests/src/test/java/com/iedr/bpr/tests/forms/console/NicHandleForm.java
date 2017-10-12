package com.iedr.bpr.tests.forms.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.formdetails.NicHandleDetails;
import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SelectionField;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class NicHandleForm implements Form {

    protected String operationPrefix; // "Nichandle_Details"

    protected TextField nameField;
    protected TextField emailField;
    protected TextField phonesField;
    protected TextField faxesField;
    protected TextField companyNameField;
    protected TextField addressField;
    protected SelectionField countryField;
    protected SelectionField countyField;

    private boolean hasFaxField;

    public NicHandleForm(String operationPrefix, boolean hasFaxField) {
        nameField = new TextField(console(), By.id(operationPrefix + "_name"), ErrorMessageSelector.IN_TABLE, true);
        emailField = new TextField(console(), By.id(operationPrefix + "_email"), ErrorMessageSelector.IN_TABLE, true);
        phonesField = new TextField(console(), By.id(operationPrefix + "_phones"), ErrorMessageSelector.IN_TABLE, true);
        faxesField = new TextField(console(), By.id(operationPrefix + "_faxes"), ErrorMessageSelector.IN_TABLE, true);
        companyNameField = new TextField(console(), By.id(operationPrefix + "_companyName"),
                ErrorMessageSelector.IN_TABLE, true);
        addressField = new TextField(console(), By.id(operationPrefix + "_address"), ErrorMessageSelector.IN_TABLE,
                true);
        countryField = new SelectionField(console(), By.id(operationPrefix + "_countryId"), ErrorMessageSelector.IN_TABLE);
        countyField = new SelectionField(console(), By.id(operationPrefix + "_countyId"), ErrorMessageSelector.IN_TABLE);
        this.hasFaxField = hasFaxField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getCompanyNameField() {
        return companyNameField;
    }

    public void fillNicHandleForm(NicHandleDetails details) {
        if (details.isNameEditable()) {
            nameField.fill(details.getNhName());
        }
        emailField.fill(details.getEmail());
        phonesField.fill(details.getPhone());
        if (hasFaxField) {
            faxesField.fill(details.getFax());
        }
        companyNameField.fill(details.getCompany());
        addressField.fill(details.getAddress());
        countryField.fillWithText(details.getCountry());
        countyField.fillWithText(details.getCounty());
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        List<Form> subForms = new ArrayList<>();
        subForms.addAll(Arrays.<Form>asList(nameField, emailField, phonesField, companyNameField, addressField,
                countryField, countyField));
        if (hasFaxField) {
            subForms.add(faxesField);
        }
        return subForms;
    }

}
