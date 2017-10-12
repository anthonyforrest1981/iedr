package com.iedr.bpr.tests.pages.crsweb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.iedr.bpr.tests.gui.CrsWebGui.SiteId;
import com.iedr.bpr.tests.utils.IncomingDocs.DocumentType;
import com.iedr.bpr.tests.utils.User;

import static com.iedr.bpr.tests.TestEnvironment.crsweb;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class DocumentsPage {

    private User user;

    public DocumentsPage(User user) {
        this.user = user;
    }

    public void viewDocumentsList(String domainName, boolean assigned) {
        crsweb().login(user);
        if (assigned) {
            crsweb().view(SiteId.DocumentsSearch);
            crsweb().fillInput(By.id("documents-search_documentSearchCriteria_domainName"), domainName);
            crsweb().clickElement(By.id("documents-search_0"));
        } else {
            crsweb().view(SiteId.DocumentsNew);
            crsweb().clickElement(By.linkText("Show all"));
        }
    }

    public void viewDocumentFromList(DocumentType type) {
        String title = documentTypeToTitle(type);
        WebElement tr = wd().findElement(
                By.xpath(String.format("//td[contains(@class, 'documents-type')]/img[@title='%s']//..//..", title)));
        crsweb().clickElement(tr.findElement(By.cssSelector("img[title='View']")));
    }

    public void viewDocumentFromList(DocumentType type, String fileName) {
        String title = documentTypeToTitle(type);
        WebElement tr = findRow(title, fileName);
        crsweb().clickElement(tr.findElement(By.cssSelector("img[title='View']")));
    }

    public void editDomainNamesForDocument(DocumentType type, String assignedDOmains) {
        String title = documentTypeToTitle(type);
        WebElement tr = findRow(title, assignedDOmains);
        crsweb().clickElement(tr.findElement(By.cssSelector("img[title='Edit domain names']")));
    }

    public void assignDocument(DocumentType type, String fileName, String domainName, int accountNumber, String source) {
        viewDocumentFromList(type, fileName);
        crsweb().fillInput(By.id("documents-update_document_domainNames"), domainName);
        crsweb().fillInput(By.id("documents-update_document_docSource"), source);
        crsweb().selectOptionByValue(By.id("documents-update_document_account"), String.valueOf(accountNumber));
        crsweb().clickAndWaitForPageToLoad(By.id("documents-update_0"));
    }

    public int getNewDocumentsCount() {
        viewDocumentsList(null, false);
        return getResultsCount();
    }

    public int getResultsCount() {
        if (wd().findElement(By.tagName("html")).getText().contains("Nothing found to display.")) {
            return 0;
        } else {
            String pageBannerText = wd().findElement(By.className("pagebanner")).getText();
            if (pageBannerText.contains("One item found")) {
                return 1;
            } else {
                Pattern pattern = Pattern.compile("(\\d+) items found");
                Matcher matcher = pattern.matcher(pageBannerText);
                matcher.find();
                return Integer.valueOf(matcher.group(1));
            }
        }
    }

    private String documentTypeToTitle(DocumentType type) {
        if (type == DocumentType.ATTACHMENT_ASSIGNED || type == DocumentType.ATTACHMENT_NEW) {
            return "Attachment";
        } else if (type == DocumentType.FAX_ASSIGNED || type == DocumentType.FAX_NEW) {
            return "Fax";
        } else if (type == DocumentType.PAPER_ASSIGNED || type == DocumentType.PAPER_NEW) {
            return "Paper";
        }
        throw new RuntimeException("Invalid type");
    }

    private WebElement findRow(String title, String fileName) {
        WebElement row = null;
        for (WebElement tr : wd().findElements(By.cssSelector(".result tbody tr"))) {
            String dTitle = tr.findElement(By.cssSelector(".documents-type img")).getAttribute("title");
            String dFileName = tr.findElement(By.cssSelector(".documents-name")).getText();
            if (title.equals(dTitle) && fileName.equals(dFileName)) {
                row = tr;
            }
        }
        if (row == null) {
            throw new RuntimeException("Document row not found");
        }
        return row;
    }

}
