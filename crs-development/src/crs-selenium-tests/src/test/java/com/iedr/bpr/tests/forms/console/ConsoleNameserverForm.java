package com.iedr.bpr.tests.forms.console;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.forms.ErrorMessageSelector;
import com.iedr.bpr.tests.forms.NameserverForm;
import com.iedr.bpr.tests.forms.TextField;
import com.iedr.bpr.tests.utils.DomainNameServer;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class ConsoleNameserverForm extends NameserverForm {

    private static ErrorMessages ERROR_MESSAGES = new ErrorMessages("Nameserver 2 name duplicates 1",
            "Nameserver 2 is not a valid host name", "Not a valid IPv4 address", "Nameserver 2 must have glue defined",
            "Glue is not allowed", "Please correct your nameservers");

    public ConsoleNameserverForm(boolean ipDisplayed) {
        super(ipDisplayed);
    }

    @Override
    public int countVisibleRows() {
        List<WebElement> rows = wd().findElements(By.xpath("//td[contains(@class, 'nameserver')]/.."));
        int visible = 0;
        for (WebElement row : rows) {
            if (row.isDisplayed()) {
                visible += 1;
            }
        }
        return visible;
    }

    @Override
    public void addRow() {
        console().clickElement(By.id("ns_add_buttton"));
    }

    @Override
    public void removeRow(int i) {
        console().clickElement(By.id(String.format("ns_del_buttton_%s", i)));
    }

    @Override
    public void fillRow(int i, DomainNameServer dns) {
        getNameField(i).fill(dns.name);
        if (ipDisplayed) {
            getIpv4Field(i).fill(dns.ipv4);
            getIpv6Field(i).fill(dns.ipv6);
        }
    }

    @Override
    public TextField getNameField(int i) {
        return new TextField(console(), By.id(String.format("ns_%s", i)), ErrorMessageSelector.NAMESERVER_NAME, true);
    }

    @Override
    public TextField getIpv4Field(int i) {
        return new TextField(console(), By.id(String.format("ipv4_%s", i)), ErrorMessageSelector.NAMESERVER_IP, true);
    }

    @Override
    public TextField getIpv6Field(int i) {
        return new TextField(console(), By.id(String.format("ipv6_%s", i)), ErrorMessageSelector.NAMESERVER_IP, true);
    }

    @Override
    public ErrorMessages getErrorMessages() {
        return ERROR_MESSAGES;
    }

    @Override
    public void verifyDns() {
        console().clickElement(By.id("ns_ver_buttton"));
    }

}
