package com.iedr.bpr.tests.pages.console;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.google.common.collect.ImmutableSet;
import com.iedr.bpr.tests.formdetails.console.DomainRegistrationDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.forms.*;
import com.iedr.bpr.tests.forms.console.PaymentForm;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class DomainRegistrationDetailsPage extends AbstractDomainDetailsPage {

    private static final String OPERATION_PREFIX = "Domains_Creation_Details";

    @Override
    protected String getOperationPrefix() {
        return OPERATION_PREFIX;
    }

    public PaymentForm paymentForm = new PaymentForm(getOperationPrefix(),
            ImmutableSet.of(PaymentDetails.PaymentMethod.ADP, PaymentDetails.PaymentMethod.CARD));
    public TextField holderField = new TextField(console(), By.id(getOperationPrefix() + "_holder"),
            ErrorMessageSelector.IN_TABLE, true);
    public TextField remarksField = new TextField(console(), By.id(getOperationPrefix() + "_remarks"),
            ErrorMessageSelector.IN_TABLE, true);
    public SelectionField ownerTypeField = new SelectionField(console(), By.id(getOperationPrefix() + "_ownerType"),
            ErrorMessageSelector.IN_TABLE);
    public CheckboxField basedInIrelandField = new CheckboxField(console(),
            By.id(getOperationPrefix() + "_isOwnerFromIreland"), ErrorMessageSelector.IN_TABLE);

    public DomainRegistrationDetailsPage() {
        this.renewalPeriodField = new SelectionField(console(), By.id(getOperationPrefix() + "_registration_period"),
                ErrorMessageSelector.IN_TABLE);
    }

    public void fillDomainRegistrationDetails(DomainRegistrationDetails details) {
        holderField.fill(details.getDomainHolder());
        ownerTypeField.fillWithText(details.getOwnerType());
        if (details.isBasedInIreland()) {
            basedInIrelandField.check();
        } else {
            basedInIrelandField.uncheck();
        }
        remarksField.fill(details.getRemarks());
        contactsForm.fillDomainContacts(details.getContactDetails());
        nameserverForm.fillNameserverDetails(details.getDnsDetails());
        selectRenewalPeriod(details.getPaymentPeriod());
        paymentForm.fillPaymentDetails(details.getPaymentDetails());
        tncField.check();
    }

    public void fillDomainRegistrationDetailsAndValidate(DomainRegistrationDetails details) {
        fillDomainRegistrationDetails(details);
        console().triggerFormValidation();
    }

    public void verifyDNS() {
        console().clickElement(By.name("yt8"));
    }

    @Override
    public List<Form> getSubForms() {
        return Arrays.asList(holderField, ownerTypeField, basedInIrelandField, remarksField, contactsForm,
                nameserverForm, paymentForm, tncField);
    }
}
