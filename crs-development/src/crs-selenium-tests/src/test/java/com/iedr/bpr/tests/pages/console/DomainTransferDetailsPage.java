package com.iedr.bpr.tests.pages.console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.google.common.collect.ImmutableSet;
import com.iedr.bpr.tests.formdetails.console.DomainTransferDetails;
import com.iedr.bpr.tests.formdetails.console.PaymentDetails;
import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.Form;
import com.iedr.bpr.tests.forms.SelectionField;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.forms.console.PaymentForm;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class DomainTransferDetailsPage extends AbstractDomainDetailsPage {

    private static final String OPERATION_PREFIX = "DomainsTransferDetails";

    public final TextField authcodeField = new TextField(console(), By.id(getOperationPrefix() + "_authcode"),
            ErrorMessageSelector.IN_TABLE, true);
    public PaymentForm paymentForm = new PaymentForm(getOperationPrefix(), ImmutableSet.of(
            PaymentDetails.PaymentMethod.ADP, PaymentDetails.PaymentMethod.CARD));

    private boolean isCharity = false;

    @Override
    protected String getOperationPrefix() {
        return OPERATION_PREFIX;
    }

    public DomainTransferDetailsPage(final boolean isCharity) {
        this.isCharity = isCharity;
        if (!isCharity) {
            this.renewalPeriodField = new SelectionField(console(), By.id(getOperationPrefix() + "_renewalPeriod"),
                    ErrorMessageSelector.IN_TABLE);
        }
    }

    public void fillTransferDomainDetails(DomainTransferDetails details) throws SQLException {
        authcodeField.fill(details.getAuthcode());
        contactsForm.fillDomainContacts(details.getContactDetails());
        nameserverForm.fillNameserverDetails(details.getDnsDetails());
        if (!isCharity) {
            selectRenewalPeriod(details.getPaymentPeriod());
            paymentForm.fillPaymentDetails(details.getPaymentMethod());
        }
        tncField.check();
        console().triggerFormValidation();
    }

    @Override
    public List<Form> getSubForms() {
        List<Form> result = new ArrayList<>(Arrays.asList(authcodeField, contactsForm, nameserverForm, tncField));
        if (!isCharity) {
            result.add(renewalPeriodField);
            result.add(paymentForm);
        }
        return result;
    }
}
