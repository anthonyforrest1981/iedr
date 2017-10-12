package com.iedr.bpr.tests.pages.console;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iedr.bpr.tests.utils.FileDownloader;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvoicePage {

    public void view() {
        console().view(console().url.invoiceHistory);
    }

    public void selectMonth(int month, int year) {
        console().selectOptionByValue(By.id("AllInvoiceModel_year"), String.valueOf(year));
        console().selectOptionByValue(By.id("AllInvoiceModel_month"), String.valueOf(month));
        console().clickElement(By.name("yt0"));
    }

    public boolean invoiceVisible(String invoiceNumber) {
        return !wd().findElements(By.cssSelector(String.format("td[title='%s']", invoiceNumber))).isEmpty();
    }

    public void checkInvoiceTypes(String invoiceNumber, List<String> invoiceTypes) {
        List<WebElement> visibleTypes = wd().findElements(
                By.xpath(String.format("//td[@title='%s']/..//td[9]/a", invoiceNumber)));
        for (WebElement visible : visibleTypes) {
            String invoiceType = visible.getText();
            String message = String.format("Invoice type '%s' should not be visible", invoiceType);
            assertTrue(message, invoiceTypes.contains(invoiceType));
        }
        assertEquals(invoiceTypes.size(), visibleTypes.size());
    }

    public String viewInvoice(String invoiceNumber, String invoiceType)
            throws KeyManagementException, NullPointerException, NoSuchAlgorithmException, KeyStoreException,
            IOException, URISyntaxException {
        WebElement link = wd().findElement(By.xpath(getInvoiceLinkXPath(invoiceNumber, invoiceType)));
        FileDownloader downloader = new FileDownloader(wd());
        String invoice = downloader.downloadFile(link.getAttribute("href"));
        return invoice;
    }

    public boolean sendInvoice(String invoiceNumber) {
        console().clickElement(By.xpath(getSendInvoiceLinkXPath(invoiceNumber)));
        new WebDriverWait(wd(), 3).until(ExpectedConditions.alertIsPresent());
        Alert alert = wd().switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return "Send email succesful.".equals(alertText);
    }

    private String getInvoiceLinkXPath(String invoiceNumber, String invoiceType) {
        return String.format("//td[@title='%s']/..//a[text()='%s']", invoiceNumber, invoiceType);
    }

    private String getSendInvoiceLinkXPath(String invoiceNumber) {
        return String.format("//td[@title='%s']/..//a[text()='Send']", invoiceNumber);
    }

}
