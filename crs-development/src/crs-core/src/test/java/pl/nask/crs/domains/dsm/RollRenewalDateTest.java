package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.RollRenewalDate;
import pl.nask.crs.domains.dsm.events.PaymentSettledEvent;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;

public class RollRenewalDateTest extends AbstractContextAwareTest {

    @Resource
    private EntityClassDAO entityClassDAO;

    @Resource
    private EntityCategoryDAO entityCategoryDAO;

    @Test
    public void testRenewalDateRolled() {
        Date currentDate = new Date();
        int period = 2;

        PaymentSettledEvent event = new PaymentSettledEvent(period);
        RollRenewalDate action = new RollRenewalDate();
        Domain domain = createDomain();
        domain.setRenewalDate(currentDate);
        domain.setDeletionDate(currentDate);
        domain.setSuspensionDate(currentDate);

        action.invoke(null, domain, event);

        Assert.assertNotNull(domain.getRenewalDate());
        AssertJUnit.assertEquals(DateUtils.addYears(currentDate, period), domain.getRenewalDate());
        AssertJUnit.assertEquals(currentDate, domain.getSuspensionDate());
        AssertJUnit.assertEquals(currentDate, domain.getDeletionDate());
    }

    private Domain createDomain() {
        return new Domain("name", "holder", entityClassDAO.get(1L), entityCategoryDAO.get(1L), null,
                new Contact("creator"), new Account(1), new Date(), new Date(), "remark", new Date(), false,
                new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Contact>(),
                new ArrayList<Nameserver>(), null, true, null, null);
    }

}
