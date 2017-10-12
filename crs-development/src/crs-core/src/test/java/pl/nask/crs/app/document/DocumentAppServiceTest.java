package pl.nask.crs.app.document;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.documents.*;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.WrongFaxFileExtensionException;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(locations = {"/application-services-config.xml", "/incoming-docs-config-test.xml"})
public class DocumentAppServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    @Qualifier("authenticationService")
    AuthenticationService authService;

    @Resource
    DocumentAppService service;

    @Resource
    IncomingDocumentsManager incomingDocumentsManager;

    @Resource
    DomainDAO domainDao;

    @Resource
    TicketDAO ticketDao;

    @BeforeMethod
    public void init() throws Exception {
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_MAIL));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_CONSOLE));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_NEW));
        FileUtils.forceMkdir(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_ASSIGNED));

        // upload from console
        createFile("console_1.tiff", DocumentFileType.UPLOAD_VIA_CONSOLE);
        createFile("console_2.tiff", DocumentFileType.UPLOAD_VIA_CONSOLE);

        // upload from mail
        createFile("mail_1.tiff", DocumentFileType.UPLOAD_VIA_MAIL);
        createFile("mail_2.tiff", DocumentFileType.UPLOAD_VIA_MAIL);
    }

    private void createFile(String filename, DocumentFileType fileType) throws IOException {
        File dir = incomingDocumentsManager.getDirectory(fileType);
        File faxFile = new File(dir, filename);
        faxFile.createNewFile();
    }

    @AfterMethod
    public void cleanup() throws IOException {
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_ASSIGNED));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_NEW));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_CONSOLE));
        FileUtils.forceDelete(incomingDocumentsManager.getDirectory(DocumentFileType.UPLOAD_VIA_MAIL));
    }

    @Test
    public void handleMailUploadWithoutDomainNames()
            throws NoSuchDirectoryException, WrongFaxFileExtensionException, AuthenticationException {
        AuthenticatedUser user = authService.authenticate("AAE359-IEDR", "Passw0rd!", false, "127.0.0.1", false, null,
                false, "crs");
        List<? extends UploadFilename> attachmentFilenames = Lists.newArrayList(new UploadFilenameImpl("mail_1.tiff"),
                new UploadFilenameImpl("mail_2.tiff"));
        service.handleMailUpload(user, "jb@rdprojekt.pl", "Empty email\nwith no domains.", attachmentFilenames);

        // In this case upload is done without domain, documents should be moved to attachment_new
        File attachmentNewFiles = incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_NEW);
        assertEquals(FileUtils.listFiles(attachmentNewFiles, null, false).size(), 2);
    }

    @Test
    public void handleMailUploadWithCorrectDomainNames()
            throws NoSuchDirectoryException, WrongFaxFileExtensionException, AuthenticationException {
        AuthenticatedUser user = authService.authenticate("AAE359-IEDR", "Passw0rd!", false, "127.0.0.1", false, null,
                false, "crs");
        List<? extends UploadFilename> attachmentFilenames = Lists.newArrayList(new UploadFilenameImpl("mail_1.tiff"),
                new UploadFilenameImpl("mail_2.tiff"));
        service.handleMailUpload(user, "jb@rdprojekt.pl",
                "Domains: onetouchping.ie, onetouchpumps.ie\nwith no domains.", attachmentFilenames);

        // In this case upload is done with domains, documents should be moved to attachment_assigned, tickets should get renewed
        File attachmentAssigned = incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_ASSIGNED);
        assertEquals(FileUtils.listFiles(attachmentAssigned, null, false).size(), 2);

        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setFrom(DateUtils.addSeconds(new Date(), -1));
        LimitedSearchResult<Document> result = service.findDocuments(user, criteria, 0, 10, null);
        assertEquals(result.getTotalResults(), 2);
        Document doc_1 = result.getResults().get(0);
        assertEquals(doc_1.getDomainsAsString(), "onetouchping.ie, onetouchpumps.ie");
        assertEquals(doc_1.getDocPurpose(), DocumentPurpose.MISCELLANEOUS);
        Document doc_2 = result.getResults().get(0);
        assertEquals(doc_2.getDomainsAsString(), "onetouchping.ie, onetouchpumps.ie");
        assertEquals(doc_2.getDocPurpose(), DocumentPurpose.MISCELLANEOUS);

        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchCriteria.setExactDomainName("onetouchping.ie");
        LimitedSearchResult<Ticket> ticketFindResult = ticketDao.find(ticketSearchCriteria, 0, 1);
        Ticket t = ticketFindResult.getResults().get(0);
        assertEquals(t.getHostmastersRemark(), "Documents uploaded via mail");
        assertEquals(t.getAdminStatus(), AdminStatus.RENEW);

        ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchCriteria.setExactDomainName("onetouchpumps.ie");
        ticketFindResult = ticketDao.find(ticketSearchCriteria, 0, 1);
        t = ticketFindResult.getResults().get(0);
        assertEquals(t.getHostmastersRemark(), "Documents uploaded via mail");
        assertEquals(t.getAdminStatus(), AdminStatus.RENEW);
    }

    @Test
    public void handleMailUploadWithIncorrectDomainNames()
            throws NoSuchDirectoryException, WrongFaxFileExtensionException, AuthenticationException {
        AuthenticatedUser user = authService.authenticate("AAE359-IEDR", "Passw0rd!", false, "127.0.0.1", false, null,
                false, "crs");
        List<? extends UploadFilename> attachmentFilenames = Lists.newArrayList(new UploadFilenameImpl("mail_1.tiff"),
                new UploadFilenameImpl("mail_2.tiff"));
        service.handleMailUpload(user, "jb@rdprojekt.pl",
                "Domains: \nonetouchping.ie, onetouchpumps.ie\nwith no domains.", attachmentFilenames);

        // In this case upload is done with incorrect mail format, documents should be moved to attachment_new
        File attachmentNew = incomingDocumentsManager.getDirectory(DocumentFileType.ATTACHMENT_NEW);
        assertEquals(FileUtils.listFiles(attachmentNew, null, false).size(), 2);

        TicketSearchCriteria ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchCriteria.setExactDomainName("onetouchping.ie");
        LimitedSearchResult<Ticket> ticketFindResult = ticketDao.find(ticketSearchCriteria, 0, 1);
        Ticket t = ticketFindResult.getResults().get(0);
        assertEquals(t.getHostmastersRemark(), "This application has been accepted");
        assertEquals(t.getAdminStatus(), AdminStatus.HOLD_PAPERWORK);

        ticketSearchCriteria = new TicketSearchCriteria();
        ticketSearchCriteria.setExactDomainName("onetouchpumps.ie");
        ticketFindResult = ticketDao.find(ticketSearchCriteria, 0, 1);
        t = ticketFindResult.getResults().get(0);
        assertEquals(t.getHostmastersRemark(), "This application has been accepted");
        assertEquals(t.getAdminStatus(), AdminStatus.PASSED);
    }

    private class UploadFilenameImpl implements UploadFilename {
        private final String filename;

        public UploadFilenameImpl(String filesystemFilename) {
            this.filename = filesystemFilename;
        }

        @Override
        public String getFilesystemName() {
            return filename;
        }

        @Override
        public String getUserFilename() {
            return filename;
        }
    }
}
