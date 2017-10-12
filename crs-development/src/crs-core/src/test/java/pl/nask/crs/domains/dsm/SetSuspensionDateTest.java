package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.SetSuspensionDateCurrent;
import pl.nask.crs.domains.dsm.actions.SetSuspensionDateRenewal;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;

public class SetSuspensionDateTest extends AbstractContextAwareTest {

    @Resource
    ApplicationConfig cfg;

    @Resource
    private EntityClassDAO entityClassDAO;

    @Resource
    private EntityCategoryDAO entityCategoryDAO;

    @Test
    public void setSuspensionDateCurrentAction() {
        SetSuspensionDateCurrent action = new SetSuspensionDateCurrent(cfg);
        Domain domain = createDomain();
        domain.setSuspensionDate(null);
        domain.setDeletionDate(null);
        action.invoke(null, domain, null);
        Date now = new Date();
        AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getSuspensionDate(),
                DateUtils.addDays(now, cfg.getNRPConfig().getNrpMailedPeriod() + 1)));
    }

    @Test
    public void setSuspensionDateRenewalAction() {
        SetSuspensionDateRenewal action = new SetSuspensionDateRenewal(cfg);
        Domain domain = createDomain();
        domain.setSuspensionDate(null);
        domain.setDeletionDate(null);
        Date renewalDate = new Date();
        domain.setRenewalDate(renewalDate);
        action.invoke(null, domain, null);
        Date expectedSuspensionDate = DateUtils.addDays(renewalDate, cfg.getNRPConfig().getNrpMailedPeriod() + 1);
        AssertJUnit.assertTrue(
                "Expected suspensionDate to be: " + expectedSuspensionDate + " but got " + domain.getSuspensionDate(),
                DateUtils.isSameDay(domain.getSuspensionDate(), expectedSuspensionDate));
    }

    @Test
    public void setSuspensionDateRenewalWithFixedDate() {
        SetSuspensionDateRenewal action = new SetSuspensionDateRenewal(cfg);
        Domain domain = createDomain();
        domain.setSuspensionDate(null);
        domain.setDeletionDate(null);
        Date renewalDate = new Date("2013/09/28");
        domain.setRenewalDate(renewalDate);
        action.invoke(null, domain, null);
        Date expectedSuspensionDate = new Date("2013/11/06"); // ren_dt + nrp_mailed (38) + 1
        AssertJUnit.assertTrue(
                "Expected suspensionDate to be: " + expectedSuspensionDate + " but got " + domain.getSuspensionDate(),
                DateUtils.isSameDay(domain.getSuspensionDate(), expectedSuspensionDate));
    }

    private Domain createDomain() {
        return new Domain("name", "holder", entityClassDAO.get(1L), entityCategoryDAO.get(1L), null,
                new Contact("creator"), new Account(1), new Date(), new Date(), "remark", new Date(), false,
                new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Contact>(),
                new ArrayList<Nameserver>(), null, true, null, null);
    }
}
