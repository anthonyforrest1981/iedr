package pl.nask.crs.documents.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.*;
import pl.nask.crs.documents.dao.ibatis.ConvertingDocumentDAO;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.dao.BuyRequestDAO;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class IncomingDocumentsDAOTest extends AbstractContextAwareTest {

    @Resource
    BuyRequestDAO buyRequestDAO;
    @Resource
    ConvertingDocumentDAO documentDAO;

    @Test
    public void getDoc() {
        Document actualDoc = documentDAO.get(1l);
        Document expectedDoc = createDocId1();
        compareDocs(actualDoc, expectedDoc);
        compareDocsDomains(actualDoc, expectedDoc);
    }

    @Test
    public void findAllDocs() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createAllDocs(actualDocs);
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsByDomain() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("comp.ie");
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createDocsDomain();
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsByBuyRequestId() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setBuyRequestId(21l);
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createDocsBuyRequest();
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsByDomainWithLimit() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("comp.ie");
        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1, idSortCrit());
        List<Document> expectedDocs = createDocsDomainLimited();
        compareDocList(actualDocs.getResults(), expectedDocs);
    }

    @Test
    public void findDocsBySource() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDocSource("Ne");
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createDocsSource(actualDocs);
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsBySourceWithLimit() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDocSource("Ne");
        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 1, 1, idSortCrit());
        List<Document> expectedDocs = createDocsSourceLimited();
        compareDocList(actualDocs.getResults(), expectedDocs);
    }

    @Test
    public void findDocsByDomainAndSource() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("comp.ie");
        criteria.setDocSource("Ne");
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createDocsDomainSource();
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsByDomainAndSourceWithLimit() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("comp.ie");
        criteria.setDocSource("Ne");
        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1, idSortCrit());
        List<Document> expectedDocs = createDocsDomainSourceLimited();
        compareDocList(actualDocs.getResults(), expectedDocs);
    }

    @Test
    public void findDocsByFromTo() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setFrom(new Date(1224663365000l));
        criteria.setTo(new Date(1224832631000l));
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createDocsFromTo();
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsByFromToWithLimit() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setFrom(new Date(1224663365000l));
        criteria.setTo(new Date(1224832631000l));
        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1, idSortCrit());
        List<Document> expectedDocs = createDocsFromToLimited();
        compareDocList(actualDocs.getResults(), expectedDocs);
    }

    @Test
    public void findDocsByFromToDomainSource() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setFrom(new Date(1224663365000l));
        criteria.setTo(new Date(1224832631000l));
        criteria.setDomainName("comp.ie");
        criteria.setDocSource("Ne");
        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
        List<Document> actualDocs = result.getResults();
        List<Document> expectedDocs = createDocsFromToDomainSource();
        compareDocList(actualDocs, expectedDocs);
    }

    @Test
    public void findDocsByFromToDomainSourceWithLimit() {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setFrom(new Date(1224663365000l));
        criteria.setTo(new Date(1224832631000l));
        criteria.setDomainName("comp.ie");
        criteria.setDocSource("Ne");
        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 1, 1, idSortCrit());
        List<Document> expectedDocs = createDocsFromToDomainSourceLimited();
        compareDocList(actualDocs.getResults(), expectedDocs);
    }

    private List<SortCriterion> idSortCrit() {
        return Arrays.asList(new SortCriterion("id", true));
    }

    @Test
    public void createDoc() {
        List<String> domains = new ArrayList<>();
        domains.add("elvis.ie");
        domains.add("presley.ie");
        domains.add("jo.ie");
        Document f = new Document(new DocumentFile("fax006.tif", DocumentFileType.FAX_NEW), DocumentPurpose.REGISTRATION, "SOURCE IE", 104l, domains, null, null);
        documentDAO.create(f);
    }

    @Test
    public void updateDocDomain() {
        List<String> domains = new ArrayList<>();
        domains.add("elvis.ie");
        domains.add("presley.ie");
        domains.add("beforeUpdate.ie");
        Document document =
                new Document(new DocumentFile("fax006.tif", DocumentFileType.FAX_NEW), DocumentPurpose.REGISTRATION, "UPDATE TEST SOURCE IE", 104l, domains,
                        null, null);
        documentDAO.create(document);

        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("beforeUpdate.ie");
        criteria.setDocSource("UPDATE TEST SOURCE IE");

        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1);
        document = actualDocs.getResults().get(0);
        int beforeUpdateDomainCount = document.getDomains().size();
        assertEquals(beforeUpdateDomainCount, 3);
        domains = new ArrayList<>();
        domains.add("elvis.ie");
        domains.add("presley.ie");
        domains.add("beforeUpdate.ie");
        domains.add("afterUpdate.ie");
        document.setDomains(domains);
        documentDAO.update(document);
        criteria.setDomainName("afterUpdate.ie");
        actualDocs = documentDAO.find(criteria, 0, 1);
        document = actualDocs.getResults().get(0);
        int afterUpdateDomainCount = document.getDomains().size();
        assertEquals(afterUpdateDomainCount, 4);
    }

    @Test
    public void createDocWithCreatorId() {
        List<String> domains = new ArrayList<>();
        domains.add("elvis.ie");
        domains.add("presley.ie");
        domains.add("jo.ie");
        Document document =
                new Document(new DocumentFile("fax006.tif", DocumentFileType.FAX_NEW), DocumentPurpose.REGISTRATION, "CREATE TEST SOURCE IE", 104l, domains,
                        null, "IDL2-IEDR");
        documentDAO.create(document);

        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("elvis.ie");
        criteria.setDocSource("CREATE TEST SOURCE IE");
        LimitedSearchResult<Document> docs = documentDAO.find(criteria, 0, 1);

        assertEquals(1, docs.getResults().size());
    }

    @Test
    public void testCreate() {
        final DocumentFile documentFile = new DocumentFile("test.xml", DocumentFileType.ATTACHMENT_NEW);
        Document doc = new Document(documentFile, DocumentPurpose.REGISTRATION,
                "uploader", 104l, Arrays.asList("domain1.ie", "domain2.ie"), null, "IDL2-IEDR");
        documentDAO.create(doc);
        Document dbDoc = documentDAO.get(doc.getId());
        assertNotNull(dbDoc);
    }

    @Test
    public void testBuyRequest() {
        Document doc = documentDAO.get(1l);
        assertNull(doc.getBuyRequest());
        BuyRequest req = buyRequestDAO.get(2l);
        doc.setBuyRequest(req);
        documentDAO.update(doc);
        Document dbDoc = documentDAO.get(1l);
        assertNotNull(dbDoc.getBuyRequest());
        assertEquals(dbDoc.getBuyRequest().getDomainName(), "sec-mrkt-domain-1.ie");
    }

    protected List<Document> createAllDocs(List<Document> actualDocs) {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId1());
        docs.add(createDocId2());
        docs.add(createDocId3());
        docs.add(createDocId4());
        docs.add(createDocId5(actualDocs));
        docs.add(createDocId6());
        docs.add(createDocId7());
        docs.add(createDocId8());
        docs.add(createDocId9());
        docs.add(createDocId10());
        docs.add(createDocId11());
        docs.add(createDocId12());
        docs.add(createDocId13());
        docs.add(createDocId20());
        docs.add(createDocId21());
        docs.add(createDocId22());
        docs.add(createDocId23());
        return docs;
    }

    protected List<Document> createDocsDomain() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId3LimitedDomain());
        docs.add(createDocId4());
        return docs;
    }

    protected List<Document> createDocsBuyRequest() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId2());
        docs.add(createDocId8());
        return docs;
    }

    protected List<Document> createDocsDomainLimited() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId3LimitedDomain());
        return docs;
    }

    protected List<Document> createDocsSource(List<Document> actualDocs) {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId2());
        docs.add(createDocId3());
        docs.add(createDocId4());
        docs.add(createDocId5(actualDocs));
        docs.add(createDocId7());
        docs.add(createDocId8());
        docs.add(createDocId9());
        docs.add(createDocId10());
        docs.add(createDocId11());
        docs.add(createDocId12());
        docs.add(createDocId13());
        return docs;
    }

    protected List<Document> createDocsSourceLimited() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId3());
        return docs;
    }

    protected List<Document> createDocsDomainSource() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId3LimitedDomain());
        docs.add(createDocId4());
        return docs;
    }

    protected List<Document> createDocsDomainSourceLimited() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId3LimitedDomain());
        return docs;
    }

    protected List<Document> createDocsFromTo() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId2());
        docs.add(createDocId3());
        docs.add(createDocId4());
        docs.add(createDocId7());
        docs.add(createDocId8());
        return docs;
    }

    protected List<Document> createDocsFromToLimited() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId2());
        return docs;
    }

    protected List<Document> createDocsFromToDomainSource() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId3LimitedDomain());
        docs.add(createDocId4());
        return docs;
    }

    protected List<Document> createDocsFromToDomainSourceLimited() {
        List<Document> docs = new ArrayList<>();
        docs.add(createDocId4());
        return docs;
    }

    protected Document createDocId1() {
        List<String> domains = new ArrayList<>();
        domains.add("easy.ie");
        domains.add("george.ie");
        return new Document(1l, new Date(1224573266000l), new DocumentFile("fax001.tif", "fax"), DocumentPurpose.TRANSFER,
                "Indigo NOC", 100l, domains, null, null);
    }

    protected Document createDocId2() {
        List<String> domains = new ArrayList<>();
        domains.add("movie.ie");
        return new Document(2l, new Date(1224659766000l), new DocumentFile("fax002.tif", "fax"), DocumentPurpose.DELETION,
                "Nell i Stas", null, domains, null, null);
    }

    protected Document createDocId3() {
        List<String> domains = new ArrayList<>();
        domains.add("comp.ie");
        domains.add("book.ie");
        return new Document(3l, new Date(1224746211000l), new DocumentFile("att001.pdf", "attachment"), DocumentPurpose.REGISTRATION,
                "NetNames UK", 104l, domains, null, null);
    }

    protected Document createDocId4() {
        List<String> domains = new ArrayList<>();
        domains.add("comp.ie");
        domains.add("book.ie");
        return new Document(4l, new Date(1224832631000l), new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK",
                104l, domains, null, null);
    }

    protected Document createDocId5(List<Document> actualDocs) {
        List<String> domains = new ArrayList<>();
        domains.add("comp.ie");
        domains.add("book.ie");

        //currentDate for that document is generated dynamicaly in sql question , that why have to get it from actualDocs list
        Date currentDate = null;
        for (Document d : actualDocs) {
            if (d.getId() == 5l) currentDate = d.getDate();
        }
        return new Document(5l, currentDate, new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK", 104l, domains,
                null, null);
    }

    protected Document createDocId6() {
        return new Document(6l, new Date(1224573266000L), new DocumentFile("fax001.tif", "fax"), DocumentPurpose.TRANSFER,
                "Indigo NOC", 100l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId7() {
        return new Document(7l, new Date(1224659766000L), new DocumentFile("fax002.tif", "fax"), DocumentPurpose.DELETION,
                "Nell i Stas", null, new ArrayList<String>(), null, null);
    }

    protected Document createDocId8() {
        return new Document(8l, new Date(1224746211000L), new DocumentFile("att001.pdf", "attachment"), DocumentPurpose.REGISTRATION,
                "NetNames UK", 104l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId9() {
        return new Document(9l, new Date(1227518231000L), new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK",
                104l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId10() {
        return new Document(10l, new Date(1256368631000L), new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK",
                104l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId11() {
        return new Document(11l, new Date(1256368631000L), new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK",
                104l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId12() {
        return new Document(12l, new Date(1259054231000L), new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK",
                104l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId13() {
        return new Document(13l, new Date(1259054231000L), new DocumentFile(null, "paper"), DocumentPurpose.REGISTRATION, "NetNames UK",
                104l, new ArrayList<String>(), null, null);
    }

    protected Document createDocId20() {
        return new Document(20l, new Date(1420099200000l), new DocumentFile("fäx001.tif", "fax"), DocumentPurpose.TRANSFER, "Indigö NOC",
                990l, new ArrayList<String>(), "IDL2-IËDP", null);
    }

    protected Document createDocId21() {
        return new Document(21l, new Date(1420099200000l), new DocumentFile("fäx002.tif", "fax"), DocumentPurpose.TRANSFER, "Indigä NOC",
                991l, new ArrayList<String>(), "IDË2-IEDR", null);
    }

    protected Document createDocId22() {
        return new Document(22l, null, new DocumentFile("non-existing-1.jpg", "attachment"),
                DocumentPurpose.MISCELLANEOUS, "email", 101L, new ArrayList<String>(), "test", null);
    }

    protected Document createDocId23() {
        return new Document(23l, null, new DocumentFile("non-existing-2.jpg", "attachment"),
                DocumentPurpose.MISCELLANEOUS, "email", 101L, new ArrayList<String>(), "test", null);
    }

    protected Document createDocId3LimitedDomain() {
        List<String> domains = new ArrayList<>();
        domains.add("comp.ie");
        return new Document(3l, new Date(1224746211000l), new DocumentFile("att001.pdf", "attachment"), DocumentPurpose.REGISTRATION,
                "NetNames UK", 104l, domains, null, null);
    }

    protected void compareDocs(Document actual, Document expected) {
        assertEquals(actual.getId(), expected.getId());
        if (expected.getDate() != null) {
            assertEquals(actual.getDate(), expected.getDate());
        }
        assertEquals(actual.getDocPurpose(), expected.getDocPurpose());
        assertEquals(actual.getDocSource(), expected.getDocSource());
        assertEquals(actual.getAccountNumber(), expected.getAccountNumber());
        assertEquals(actual.getDocumentFile().getFileType(), expected.getDocumentFile().getFileType());
        assertEquals(actual.getDocumentFile().getFileName(), expected.getDocumentFile().getFileName());
        assertEquals(actual.getDocumentFile().getModificationDate(), expected.getDocumentFile().getModificationDate());
    }

    protected void compareDocsDomains(Document actual, Document expected) {
        if (actual.getDomains() == null) {
            assertNull(expected.getDomains());
        } else {
            assertEquals(actual.getDomains().size(), expected.getDomains().size());
            for (String domain : actual.getDomains()) {
                assertTrue(expected.getDomains().contains(domain));
            }
            for (String domain : expected.getDomains()) {
                assertTrue(expected.getDomains().contains(domain));
            }
        }
    }

    protected void compareDocList(List<Document> actualDocs, List<Document> expectedDocs) {
        assertEquals(actualDocs.size(), expectedDocs.size());
        for (int i = 0; i < actualDocs.size(); i++) {
            compareDocs(actualDocs.get(i), expectedDocs.get(i));
        }
    }
}
