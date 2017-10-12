package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.ClearDeletionDate;
import pl.nask.crs.domains.dsm.actions.SetDeletionDate;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;

public class SetDeletionDateTest extends AbstractContextAwareTest {

    @Resource
    ApplicationConfig cfg;

    @Resource
    private EntityClassDAO entityClassDAO;

    @Resource
    private EntityCategoryDAO entityCategoryDAO;

    @Test
    public void setDeletionDate() {
        SetDeletionDate action = new SetDeletionDate(cfg);
        Domain domain = createDomain();
        Date currDt = new Date();
        domain.setSuspensionDate(currDt);

        action.invoke(null, domain, null);

        AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(),
                DateUtils.addDays(currDt, cfg.getNRPConfig().getNrpSuspendedPeriod())));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void setDeletionDateFailed() {
        SetDeletionDate action = new SetDeletionDate(cfg);
        Domain domain = createDomain();
        domain.setSuspensionDate(null);

        action.invoke(null, domain, null);
    }

    @Test
    public void clearDeletionDate() {
        ClearDeletionDate action = new ClearDeletionDate();
        Domain domain = createDomain();
        domain.setDeletionDate(new Date());
        action.invoke(null, domain, null);

        Assert.assertNull(domain.getDeletionDate());
    }

    private Domain createDomain() {
        return new Domain("name", "holder", entityClassDAO.get(1L), entityCategoryDAO.get(1L), null,
                new Contact("creator"), new Account(1), new Date(), new Date(), "remark", new Date(), false,
                new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Contact>(),
                new ArrayList<Nameserver>(), null, true, null, null);
    }
}
