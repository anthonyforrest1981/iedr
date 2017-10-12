package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.api.converter.Converter;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.nameservers.Nameserver;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainVO extends PlainDomainVO {

    private AccountVO resellerAccount;

    private Date changeDate;

    private String remark;
    private String authCode;
    private Date authCodeExpirationDate;

    private Integer dateRoll = null;

    private ContactVO creator;

    private List<ContactVO> adminContacts = new ArrayList<ContactVO>();
    private List<ContactVO> techContacts = new ArrayList<ContactVO>();
    private List<ContactVO> billingContacts = new ArrayList<ContactVO>();

    private List<NameserverVO> nameservers = new ArrayList<NameserverVO>();

    public DomainVO() {}

    public DomainVO(Domain domain) {
        super(domain);

        this.authCode = domain.getAuthCode();
        this.authCodeExpirationDate = domain.getAuthCodeExpirationDate();

        setResellerAccount(domain.getResellerAccount());
        this.changeDate = domain.getChangeDate();

        this.remark = domain.getRemark();
        this.dateRoll = domain.getDateRoll();
        setCreator(domain.getCreator());

        setAdminContacts(domain.getAdminContacts());
        setTechContacts(domain.getTechContacts());
        setBillingContacts(domain.getBillingContacts());
        setNameservers(domain.getNameservers());
    }

    void setResellerAccount(Account resellerAccount) {
        if (resellerAccount != null)
            this.resellerAccount = new AccountVO(resellerAccount);
    }

    public AccountVO getResellerAccount() {
        return resellerAccount;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public String getRemark() {
        return remark;
    }

    public String getAuthCode() {
        return authCode;
    }

    public Date getAuthCodeExpirationDate() {
        return authCodeExpirationDate;
    }

    public Integer getDateRoll() {
        return dateRoll;
    }

    public ContactVO getCreator() {
        return creator;
    }

    void setCreator(Contact creator) {
        if (creator != null)
            this.creator = new ContactVO(creator);
    }

    public List<ContactVO> getAdminContacts() {
        return adminContacts;
    }

    void setAdminContacts(List<Contact> adminContacts) {
        this.adminContacts = convertContacts(adminContacts);
    }

    public List<ContactVO> getTechContacts() {
        return techContacts;
    }

    void setTechContacts(List<Contact> techContacts) {
        this.techContacts = convertContacts(techContacts);
    }

    public List<ContactVO> getBillingContacts() {
        return billingContacts;
    }

    void setBillingContacts(List<Contact> billingContacts) {
        this.billingContacts = convertContacts(billingContacts);
    }

    private List<ContactVO> convertContacts(List<Contact> contacts) {
        if (contacts == null)
            return null;
        else {
            ArrayList<ContactVO> res = new ArrayList<ContactVO>(contacts.size());
            for (Contact c : contacts) {
                if (c != null)
                    res.add(new ContactVO(c));
                else
                    res.add(null);
            }
            return res;
        }
    }

    public List<NameserverVO> getNameservers() {
        return nameservers;
    }

    void setNameservers(List<Nameserver> nameservers) {
        this.nameservers = Converter.toNameserverVOList(nameservers);
    }
}
