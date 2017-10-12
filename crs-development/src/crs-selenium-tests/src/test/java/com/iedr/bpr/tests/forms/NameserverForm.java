package com.iedr.bpr.tests.forms;

import java.util.ArrayList;
import java.util.List;

import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.utils.DomainNameServer;

public abstract class NameserverForm implements Form {

    protected boolean ipDisplayed;

    public NameserverForm(boolean ipDisplayed) {
        this.ipDisplayed = ipDisplayed;
    }

    public void fillNameserverDetails(NameserverFormDetails details) {
        List<DomainNameServer> dnsList = details.getDnsList();
        int visibleRowsCount = countVisibleRows();
        for (int i = 0; i < details.getSize(); i++) {
            if (i >= visibleRowsCount) {
                addRow();
            }
            fillRow(i, dnsList.get(i));
        }
    }

    public void removeLastRow() {
        int visibleRowsCount = countVisibleRows();
        removeRow(visibleRowsCount - 1);
    }

    public abstract int countVisibleRows();

    public abstract void addRow();

    public abstract void removeRow(int i);

    public abstract void fillRow(int i, DomainNameServer dns);

    public abstract TextField getNameField(int i);

    public abstract TextField getIpv4Field(int i);

    public abstract TextField getIpv6Field(int i);

    public abstract ErrorMessages getErrorMessages();

    public abstract void verifyDns();

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public List<Form> getSubForms() {
        int visibleRowsCount = countVisibleRows();
        List<Form> subForms = new ArrayList<>();
        for (int i = 0; i < visibleRowsCount; i++) {
            subForms.add(getNameField(i));
            if (ipDisplayed) {
                subForms.add(getIpv4Field(i));
                subForms.add(getIpv6Field(i));
            }
        }
        return subForms;
    }

    public static class ErrorMessages {

        public String duplicateMessage = null;
        public String invalidDnsMessage = null;
        public String invalidIpv4Message = null;
        public String requiredGlueMessage = null;
        public String notAllowedGlueMessage = null;
        public String ckdnsValidationMessage = null;

        public ErrorMessages(String duplicateMessage, String invalidDnsMessage, String invalidIpv4Message,
                String requiredGlueMessage, String notAllowedGlueMessage, String ckdnsValidationMessage) {
            this.duplicateMessage = duplicateMessage;
            this.invalidDnsMessage = invalidDnsMessage;
            this.invalidIpv4Message = invalidIpv4Message;
            this.requiredGlueMessage = requiredGlueMessage;
            this.notAllowedGlueMessage = notAllowedGlueMessage;
            this.ckdnsValidationMessage = ckdnsValidationMessage;
        }
    }
}
