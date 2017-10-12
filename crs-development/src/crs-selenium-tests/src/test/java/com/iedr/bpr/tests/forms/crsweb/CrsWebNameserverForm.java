package com.iedr.bpr.tests.forms.crsweb;

import com.iedr.bpr.tests.forms.NameserverForm;
import com.iedr.bpr.tests.utils.DomainNameServer;

public abstract class CrsWebNameserverForm extends NameserverForm {

    private static ErrorMessages ERROR_MESSAGES = new ErrorMessages("Duplicate nameserver",
            "Not a valid nameserver name", "Not a valid IP", "Glue is required for this dns",
            "Glue is not allowed for this dns", "FATAL Invalid nameserver name");

    public CrsWebNameserverForm(boolean ipDisplayed) {
        super(ipDisplayed);
    }

    @Override
    public void fillRow(int i, DomainNameServer dns) {
        getNameField(i).fill(dns.name);
        getIpv4Field(i).fill(dns.ipv4);
        getIpv6Field(i).fill(dns.ipv6);
    }

    @Override
    public ErrorMessages getErrorMessages() {
        return ERROR_MESSAGES;
    }
}
