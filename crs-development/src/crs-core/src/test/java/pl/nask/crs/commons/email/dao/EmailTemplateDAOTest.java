package pl.nask.crs.commons.email.dao;

import java.util.Collections;

import javax.annotation.Resource;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class EmailTemplateDAOTest extends AbstractTest {

    @Resource
    EmailTemplateDAO emailTemplateDAO;

    @BeforeClass
    public static void init() {}

    @Test
    public void testGetEmailTemplateById() {
        EmailTemplate template = emailTemplateDAO.get(73);
        assertNotNull(template);
    }

    @Test
    public void testGet() {
        EmailTemplate tpl = emailTemplateDAO.get(1);
        assertNotNull(tpl);
        assertEquals("Deposit Top-up (DOA) - $ORDER_ID$", tpl.getSubject());
    }

    @Test
    public void testLock() {
        assertTrue(emailTemplateDAO.lock(1));
        EmailTemplate tpl = emailTemplateDAO.get(1);
        assertNotNull(tpl);
        assertEquals("Deposit Top-up (DOA) - $ORDER_ID$", tpl.getSubject());
    }

    @Test
    public void testGetUnnormalizedUtf8() {
        EmailTemplate template = emailTemplateDAO.get(950);
        assertEquals("Tëst content", template.getText());
        assertEquals("Tëst subject", template.getSubject());
        assertEquals(Collections.singletonList("to@dömain.ie"), template.getToList());
        assertEquals(Collections.singletonList("cc@dömain.ie"), template.getCcList());
        assertEquals(Collections.singletonList("bcc@dömain.ie"), template.getBccList());
        assertEquals(Collections.singletonList("ito@dömain.ie"), template.getInternalToList());
        assertEquals(Collections.singletonList("icc@dömain.ie"), template.getInternalCcList());
        assertEquals(Collections.singletonList("ibcc@dömain.ie"), template.getInternalBccList());
        assertEquals("fröm@domain.ie", template.getMailSmtpFrom());
        assertEquals("Sënd reason", template.getSendReason());
        assertEquals("Nön-suppressible reason", template.getNonSuppressibleReason());
    }
}
