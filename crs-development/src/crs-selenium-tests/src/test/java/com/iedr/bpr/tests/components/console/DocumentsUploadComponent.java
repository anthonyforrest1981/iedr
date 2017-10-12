package com.iedr.bpr.tests.components.console;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;

import com.iedr.bpr.tests.components.Component;

import static com.iedr.bpr.tests.TestEnvironment.console;
import static com.iedr.bpr.tests.TestEnvironment.wd;

public class DocumentsUploadComponent extends Component {

    public DocumentsUploadComponent() {
        super(console());
    }

    public void uploadResourceFile() throws IOException {
        uploadResourceFile("/documentToUpload.png");
    }

    public void uploadResourceFile(String resourcePath) throws IOException {
        URL source = getClass().getResource(resourcePath);
        String fileName = FilenameUtils.getBaseName(resourcePath);
        String fileExtension = FilenameUtils.getExtension(resourcePath);
        File destination = File.createTempFile(fileName, "." + fileExtension);
        destination.deleteOnExit();
        FileUtils.copyURLToFile(source, destination);
        uploadFile(destination.getAbsolutePath());
        waitForFileUploaded(destination.getAbsolutePath());
    }

    public void uploadRandomAccessFile(long size, String fileExtension) throws IOException {
        File temp = File.createTempFile("documentToUpload", "." + fileExtension);
        temp.deleteOnExit();
        String path = temp.getAbsolutePath();
        RandomAccessFile f = new RandomAccessFile(path, "rw");
        try {
            f.setLength(size);
        } finally {
            f.close();
        }
        uploadFile(path);
    }

    public void uploadFile(String absolutePath) {
        wd().findElement(getFileDialogButton()).sendKeys(absolutePath);
    }

    public void waitForFileUploaded(String absolutePath) {
        String file = FilenameUtils.getName(absolutePath);
        gui.waitForElementPresent(By.xpath(String.format("//li[@class='document_upload']"
                + "//span[@class='file_name' and .='%s']", file)));
    }

    public By getFileDialogButton() {
        return By.xpath("//div[@class='upload_next' and " + "not(contains(@style, 'display: none'))]"
                + "//input[@name='documents[]']");
    }

}
