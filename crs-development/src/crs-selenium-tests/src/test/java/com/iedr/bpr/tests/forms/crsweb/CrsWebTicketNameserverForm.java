package com.iedr.bpr.tests.forms.crsweb;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class CrsWebTicketNameserverForm extends CrsWebNameserverForm {

    public CrsWebTicketNameserverForm() {
        super(true);
    }

    @Override
    public int countVisibleRows() {
        return wd().findElements(By.cssSelector("#ticket-nameservers > .ticket-nameserver")).size();
    }

    @Override
    public void addRow() {
        crsweb().clickElement(By.id("add-nameserver"));
    }

    @Override
    public void removeRow(int i) {
        console().clickElement(wd().findElements(By.cssSelector(".ticket-nameserver > a.button")).get(i));
    }

    @Override
    public TextField getNameField(int i) {
        return new TextField(crsweb(), By.name("ticketWrapper.newNameserverWrappers[" + i + "].name"),
                ErrorMessageSelector.BELOW, true);
    }

    @Override
    public TextField getIpv4Field(int i) {
        return new TextField(crsweb(), By.name("ticketWrapper.newNameserverWrappers[" + i + "].ipv4"),
                ErrorMessageSelector.BELOW, true);
    }

    @Override
    public TextField getIpv6Field(int i) {
        return new TextField(crsweb(), By.name("ticketWrapper.newNameserverWrappers[" + i + "].ipv6"),
                ErrorMessageSelector.BELOW, true);
    }

    @Override
    public void verifyDns() {
        crsweb().clickElement(By.name("method:dnsCheck"));
    }

}
