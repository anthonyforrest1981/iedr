package com.iedr.bpr.tests.pages.console;

import static com.iedr.bpr.tests.TestEnvironment.console;

public class ViewTransfersPage extends DomainGridPage {

    public void viewTransfersIn() {
        console().view(console().url.viewTransfersIn);
    }

    public void viewTransfersAway() {
        console().view(console().url.viewTransfersAway);
    }

    public String getHolder(String domainName) {
        return getFieldValue(domainName, "B");
    }

    public String getTransferDate(String domainName) {
        return getFieldValue(domainName, "C");
    }
}
