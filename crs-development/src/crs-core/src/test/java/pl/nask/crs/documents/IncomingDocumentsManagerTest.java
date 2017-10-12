package pl.nask.crs.documents;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;

/**
 * @author Piotr Tkaczyk
 * @author Marianna Mysiorska
 */
public class IncomingDocumentsManagerTest extends AbstractContextAwareTest {
    @Resource
    private IncomingDocumentsManager incomingDocumentsManager;

    private List<String> testFaxFiles = Arrays.asList("1.tiff", "2.TIFF", "3.tif", "4.jpg", "file", "wrong.",
            "aaa.bbb.ccc");

    @Test(expectedExceptions = NoSuchDirectoryException.class)
    public void testNewFaxDirectoryNotExist() throws Exception {
        incomingDocumentsManager.getNewDocumentFiles();
    }

    @Test(expectedExceptions = NoSuchDirectoryException.class)
    public void testAssignedFaxDirectoryNotExist() throws Exception {
        DocumentFile documentFile = new DocumentFile("test123.tiff", DocumentFileType.FAX_NEW);
        incomingDocumentsManager.markDocumentFileAsAssigned(documentFile, true);
    }

    @Test
    public void testGetNewDocuments() throws Exception {
        createTestDirectory();

        List<DocumentFile> documentFileList = incomingDocumentsManager.getNewDocumentFiles();

        AssertJUnit.assertNotNull(documentFileList);
        AssertJUnit.assertEquals(3, documentFileList.size());

        for (DocumentFile documentFile : documentFileList)
            AssertJUnit.assertTrue(testFaxFiles.contains(documentFile.getFileName()));

        removeTestDirectory();
    }

    @Test
    public void testMarkAsAssignedMoved() throws Exception {
        createTestDirectory();
        String testFilename = "1.tiff";
        String newFilename = DocumentFile.getNormalizedFilenameWithAddDate(testFilename, null);

        DocumentFile document = new DocumentFile(testFilename, DocumentFileType.FAX_NEW);
        incomingDocumentsManager.markDocumentFileAsAssigned(document, true);
        DocumentFile assignedDocument = incomingDocumentsManager.getDocumentFile(newFilename,
                DocumentFileType.FAX_ASSIGNED);

        AssertJUnit.assertNotNull(assignedDocument);
        AssertJUnit.assertEquals(newFilename, assignedDocument.getFileName());

        File newDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW);
        File[] otherFiles = newDir.listFiles();

        AssertJUnit.assertEquals(6, otherFiles.length);

        File assignedDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED);
        File[] assignedFiles = assignedDir.listFiles();

        AssertJUnit.assertEquals(1, assignedFiles.length);

        removeTestDirectory();
    }

    @Test
    public void testMarkAsAssignedCopied() throws Exception {
        createTestDirectory();
        String testFilename = "1.tiff";
        String newFilename = DocumentFile.getNormalizedFilenameWithAddDate(testFilename, null);

        DocumentFile document = new DocumentFile(testFilename, DocumentFileType.FAX_NEW);
        incomingDocumentsManager.markDocumentFileAsAssigned(document, false);
        DocumentFile assignedDocument = incomingDocumentsManager.getDocumentFile(newFilename,
                DocumentFileType.FAX_ASSIGNED);

        AssertJUnit.assertNotNull(assignedDocument);
        AssertJUnit.assertEquals(newFilename, assignedDocument.getFileName());

        File newDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW);
        File[] otherFiles = newDir.listFiles();

        AssertJUnit.assertEquals(7, otherFiles.length);

        File assignedDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED);
        File[] assignedFiles = assignedDir.listFiles();

        AssertJUnit.assertEquals(1, assignedFiles.length);

        removeTestDirectory();
    }

    @Test(expectedExceptions = NoSuchDocumentException.class)
    public void testMarkAsAssignedFail() throws DocumentGeneralException, IOException {
        createTestDirectory();
        try {
            DocumentFile document = new DocumentFile("notexists.tiff", DocumentFileType.FAX_NEW);
            incomingDocumentsManager.markDocumentFileAsAssigned(document, true);
        } finally {
            removeTestDirectory();
        }
    }

    private void createTestDirectory() throws IOException {
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_MAIL));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_CONSOLE));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_NEW));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_ASSIGNED));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED));

        File faxNewDirectory = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW);
        for (String testFaxFile : testFaxFiles) {
            File faxFile = new File(faxNewDirectory, testFaxFile);
            faxFile.createNewFile();
        }
    }

    private void removeTestDirectory() throws IOException {
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_NEW));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_ASSIGNED));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_CONSOLE));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_MAIL));
    }
}
