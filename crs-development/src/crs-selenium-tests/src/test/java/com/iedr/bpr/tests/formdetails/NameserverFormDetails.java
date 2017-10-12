package com.iedr.bpr.tests.formdetails;

import java.util.Arrays;
import java.util.List;

import com.iedr.bpr.tests.utils.DomainNameServer;

public class NameserverFormDetails {

    private List<DomainNameServer> dnsList;

    public NameserverFormDetails(List<DomainNameServer> dnsList) {
        this.dnsList = dnsList;
    }

    public NameserverFormDetails(String domainName) {
        this(Arrays.asList(new DomainNameServer(String.format("ns1.%s", domainName), "10.10.1.1", null),
                new DomainNameServer(String.format("ns2.%s", domainName), "10.10.1.2", null)));
    }

    public List<DomainNameServer> getDnsList() {
        return dnsList;
    }

    public int getSize() {
        if (dnsList == null) {
            return 0;
        }
        return dnsList.size();
    }

}
