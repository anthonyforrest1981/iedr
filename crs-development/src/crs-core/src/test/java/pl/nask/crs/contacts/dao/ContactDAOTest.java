package pl.nask.crs.contacts.dao;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.contacts.search.ContactSearchCriteria;

public class ContactDAOTest extends AbstractTest {

    @Resource
    ContactDAO contactDAO;

    @Test
    public void testFindAll() {
        ContactSearchCriteria all = new ContactSearchCriteria();
        SearchResult<Contact> res = contactDAO.find(all);
        AssertJUnit.assertEquals(964, res.getResults().size());
    }

    @Test
    public void testFindByName() {
        ContactSearchCriteria likeAName = new ContactSearchCriteria();
        likeAName.setName("A");
        SearchResult<Contact> res = contactDAO.find(likeAName);
        AssertJUnit.assertEquals(89, res.getResults().size());
    }

    @Test
    public void testLimitedFindAll() {
        ContactSearchCriteria all = new ContactSearchCriteria();
        LimitedSearchResult<Contact> res = contactDAO.find(all, 0, 50);
        AssertJUnit.assertEquals(964, res.getTotalResults());
        AssertJUnit.assertEquals(50, res.getResults().size());
    }

    @Test
    public void testLimitedFindByName() {
        ContactSearchCriteria likeAName = new ContactSearchCriteria();
        likeAName.setName("A");
        LimitedSearchResult<Contact> res = contactDAO.find(likeAName, 0, 50);
        AssertJUnit.assertEquals(89, res.getTotalResults());
        AssertJUnit.assertEquals(50, res.getResults().size());
    }

    @Test
    public void testFindByNicHandleIdAndType() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAP368-IEDR");
        criteria.setType(ContactType.BILLING);
        SearchResult<Contact> res = contactDAO.find(criteria);
        AssertJUnit.assertEquals(1, res.getResults().size());
    }

    @Test
    public void testFindByNicHandleIdAndTypeEmptyResult() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAE395-IEDR");
        criteria.setType(ContactType.BILLING);
        SearchResult<Contact> res = contactDAO.find(criteria);
        AssertJUnit.assertEquals(0, res.getResults().size());
    }

    @Test
    public void testCount() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAP368-IEDR");
        criteria.setType(ContactType.BILLING);
        AssertJUnit.assertEquals(1, contactDAO.count(criteria));
    }

    @Test
    public void testExists() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAP368-IEDR");
        criteria.setType(ContactType.BILLING);
        AssertJUnit.assertTrue(contactDAO.exists(criteria));
    }

    @Test
    public void testNotExists() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAE395-IEDR");
        criteria.setType(ContactType.BILLING);
        AssertJUnit.assertFalse(contactDAO.exists(criteria));
    }

    @Test
    public void testFindContactWithUnnormalizedNicHandle() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setName("Name for UTF-8 Test");
        SearchResult<Contact> res = contactDAO.find(criteria, null);
        Assert.assertEquals(res.getResults().size(), 1);
        Assert.assertEquals(res.getResults().get(0).getNicHandle(), "XY\u01788-IEDR");
    }

    @Test
    public void testGetDomainWithUnnormalizedDetails() {
        Contact contact = contactDAO.get("YYY9-IEDR");
        Assert.assertEquals(contact.getName(), "\u00cc\u1e43\u1e03\u00fa\u0205l");
        Assert.assertEquals(contact.getCompanyName(), "\u1e0c\u011c\u1e42\u0158 Ltd.");
        Assert.assertEquals(contact.getCountyName(), "Unnormalized co\u00fan\u0163y");
        Assert.assertEquals(contact.getCountryName(), "Unnormalized c\u00f4u\u1e45try");
        Assert.assertEquals(contact.getEmail(), "\u1eb9\u1e3f\u0105\u1ec9\u1e3d@server.xxx");
    }

    @Test
    public void testFindUtf8DomainWithSearchCriteria() {
        String nicHandle = "XX\u01787-IEDR";
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle(nicHandle);
        SearchResult<Contact> result = contactDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        criteria = new ContactSearchCriteria();
        criteria.setName("\u00cc\u1e43\u1e03\u00fa\u0205l");
        result = contactDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getNicHandle(), nicHandle);
        criteria = new ContactSearchCriteria();
        criteria.setCompanyName("\u1e0c\u011c\u1e42\u0158 Ltd.");
        result = contactDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getNicHandle(), nicHandle);
    }
}
