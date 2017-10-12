package pl.nask.crs.documents.dao.ibatis;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.documents.AbstractContextAwareTest;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocument;
import pl.nask.crs.documents.search.DocumentSearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class InternalDocumentIBatisDAOUtf8Test extends AbstractContextAwareTest {

    @Autowired
    InternalDocumentIBatisDAO dao;

    @Test
    public void testGet() {
        InternalDocument doc = dao.get(20l);
        assertEquals(doc.getDocFilename(), "fäx001.tif");
        assertEquals(doc.getDocSource(), "Indigö NOC");
        assertEquals(doc.getDomains(), Arrays.asList("ns1.domainnameä.ie", "ns2.domainnameä.ie"));
    }

    @Test
    public void testFind() {
        DocumentSearchCriteria docCriteria = new DocumentSearchCriteria();
        docCriteria.setDomainName("ns1.domainnamea\u0308.ie");
        SearchCriteria criteria = docCriteria;
        SearchResult<InternalDocument> result = dao.find(criteria);
        assertEquals(result.getResults().size(), 1);
        assertEquals(result.getResults().get(0).getDocFilename(), "fäx002.tif");
        assertEquals(result.getResults().get(0).getDocSource(), "Indigä NOC");
    }

    @Test
    public void testCreate() {
        InternalDocument doc = dao.get(20l);
        dao.create(doc);
        InternalDocument newDoc = dao.get(doc.getId());
        assertTrue(newDoc.getId() != 20l);
        assertEquals(newDoc.getDocSource(), "Indigö NOC");
    }

    @Test
    public void testUpdate() {
        InternalDocument doc = dao.get(20l);
        dao.create(doc);
        InternalDocument newDoc = dao.get(doc.getId());
        assertTrue(newDoc.getId() != 20l);
        assertEquals(newDoc.getDocSource(), "Indigö NOC");
    }

}
