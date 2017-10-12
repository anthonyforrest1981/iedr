package pl.nask.crs.contacts.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;

public class InternalContactIBatisDAO extends GenericIBatisDAO<InternalContact, String> {

    public InternalContactIBatisDAO() {
        setFindQueryId("contact.findContacts");
        setCountFindQueryId("contact.countContacts");
        setGetQueryId("contact.getContactByNicHandleId");
    }

    public String getDefaultTechContact(String billingContact) {
        return performQueryForObject("contact.getDefaultTechContact", billingContact);
    }

}
