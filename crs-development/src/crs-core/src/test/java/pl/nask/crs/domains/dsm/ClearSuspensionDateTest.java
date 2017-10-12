package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.ClearSuspensionDate;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;

public class ClearSuspensionDateTest extends AbstractContextAwareTest {

    @Resource
    private EntityClassDAO entityClassDAO;

    @Resource
    private EntityCategoryDAO entityCategoryDAO;

    @Test
    public void clearSuspensionDateAction() {
        ClearSuspensionDate action = new ClearSuspensionDate();
        Domain domain = createDomain();
        domain.setSuspensionDate(new Date());
        domain.setDeletionDate(new Date());
        action.invoke(null, domain, null);
        Assert.assertNull(domain.getSuspensionDate());
    }

    private Domain createDomain() {
        return new Domain("name", "holder", entityClassDAO.get(1L), entityCategoryDAO.get(1L), null,
                new Contact("creator"), new Account(1), new Date(), new Date(), "remark", new Date(), false,
                new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Contact>(),
                new ArrayList<Nameserver>(), null, true, null, null);
    }
}
