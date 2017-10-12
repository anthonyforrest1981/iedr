package pl.nask.crs.commons.email.dao;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateKey;
import pl.nask.crs.history.HistoricalObject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class HistoricalEmailTemplateDAOTest extends AbstractTest {
    @Resource
    HistoricalEmailTemplateDAO historicalEmailTemplateDAO;
    @Resource
    EmailTemplateDAO emailTemplateDAO;

    @Test
    public void basicDAOTests() {
        long origCount = historicalEmailTemplateDAO.count(null);

        String nicHandle = "AA11-IE\u0308DR";
        int emailId = 42;
        EmailTemplate emailTemplate = emailTemplateDAO.get(emailId);

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);

        final HistoricalObject<EmailTemplate> histEmail = new HistoricalObject<EmailTemplate>(emailTemplate, aDate,
                nicHandle);
        historicalEmailTemplateDAO.create(histEmail);

        HistoricalObject<EmailTemplate> histEmailDb = historicalEmailTemplateDAO.get(new HistoricalEmailTemplateKey(
                emailTemplate.getId(), histEmail.getChangeId()));
        assertNotNull(histEmailDb);
        assertEquals(histEmailDb.getChangeDate(), DateUtils.truncate(aDate, Calendar.SECOND));
        assertEquals(histEmailDb.getChangedBy(), "AA11-IËDR");
        assertEquals(histEmailDb.getObject().getId(), emailTemplate.getId());
        assertEquals(histEmailDb.getObject().getText(), emailTemplate.getText());
        assertEquals(histEmailDb.getObject().getSubject(), emailTemplate.getSubject());
        assertEquals(histEmailDb.getObject().getToList(), emailTemplate.getToList());
        assertEquals(histEmailDb.getObject().getCcList(), emailTemplate.getCcList());
        assertEquals(histEmailDb.getObject().getBccList(), emailTemplate.getBccList());
        assertEquals(histEmailDb.getObject().getInternalToList(), emailTemplate.getInternalToList());
        assertEquals(histEmailDb.getObject().getInternalCcList(), emailTemplate.getInternalCcList());
        assertEquals(histEmailDb.getObject().getInternalBccList(), emailTemplate.getInternalBccList());
        assertEquals(histEmailDb.getObject().getMailSmtpFrom(), emailTemplate.getMailSmtpFrom());
        assertEquals(histEmailDb.getObject().getSendReason(), emailTemplate.getSendReason());
        assertEquals(histEmailDb.getObject().isActive(), emailTemplate.isActive());
        assertEquals(histEmailDb.getObject().isHtml(), emailTemplate.isHtml());
        assertEquals(histEmailDb.getObject().isSuppressedByGaining(), emailTemplate.isSuppressedByGaining());
        assertEquals(histEmailDb.getObject().isSuppressible(), emailTemplate.isSuppressible());
        assertEquals(histEmailDb.getObject().getNonSuppressibleReason(), emailTemplate.getNonSuppressibleReason());
        assertEquals(histEmailDb.getObject().getSendReason(), emailTemplate.getSendReason());

    }

    @Test
    public void basicUtf8Test() {
        HistoricalObject<EmailTemplate> histEmailDb = historicalEmailTemplateDAO.get(
                new HistoricalEmailTemplateKey(950, 22));
        assertNotNull(histEmailDb);
        assertEquals(histEmailDb.getChangedBy(), "IDL2-IËDP");
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalEmailTemplateKey key = new HistoricalEmailTemplateKey(100, 24);
        HistoricalObject<EmailTemplate> histEmail = historicalEmailTemplateDAO.get(key);
        assertNotNull(histEmail);
        assertEquals(histEmail.getObject().getSubject(), "Bad utf8 email subject\01");
        assertEquals(histEmail.getObject().getText(), "Bad utf8 email text\01");
    }
}
