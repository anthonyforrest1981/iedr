package com.iedr.bpr.tests.forms.crsweb;

import org.openqa.selenium.By;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.TextField;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class CrsWebDomainNameserverForm extends CrsWebNameserverForm {

    public CrsWebDomainNameserverForm() {
        super(true);
    }

    @Override
    public int countVisibleRows() {
        return wd().findElements(By.cssSelector("#domain-nameservers .domain-nameserver")).size();
    }

    @Override
    public void addRow() {
        crsweb().clickElement(By.cssSelector("#domain-nameservers input[name='add']"));
    }

    @Override
    public void removeRow(int i) {
        String rowId = String.format("cns%sdiv", i);
        crsweb().clickElement(By.cssSelector(String.format("#%s .ctrl-field-icon img", rowId)));
    }

    @Override
    public TextField getNameField(int i) {
        return new TextField(crsweb(), By.id("cns" + i + "div_name"), ErrorMessageSelector.BELOW, true);
    }

    @Override
    public TextField getIpv4Field(int i) {
        return new TextField(crsweb(), By.id("cns" + i + "div_ipv4"), ErrorMessageSelector.BELOW, true);
    }

    @Override
    public TextField getIpv6Field(int i) {
        return new TextField(crsweb(), By.id("cns" + i + "div_ipv6"), ErrorMessageSelector.BELOW, true);
    }

    @Override
    public void verifyDns() {
        crsweb().clickElement(By.name("domainview_domainview-dnsCheck"));
    }

}
