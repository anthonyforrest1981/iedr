package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyRequestVO {

    private long id;
    private String domainName;
    private String domainHolder;
    private String remark;

    private String adminName;
    private String adminEmail;
    private String adminCompanyName;
    private String adminAddress;
    private CountryVO adminCountry;
    private CountyVO adminCounty;
    private List<String> phones = new ArrayList<>();
    private List<String> faxes = new ArrayList<>();

    private String creatorNH;
    private Date creationDate;
    private Date authcodeCreationDate;

    private BuyRequestStatus status;
    private boolean checkedOut;
    private String hostmasterRemark;
    private Date changeDate;

    private String domainNameFR;
    private String domainHolderFR;
    private String adminNameFR;
    private String adminEmailFR;
    private String adminCompanyNameFR;
    private String adminAddressFR;
    private String adminCountryFR;
    private String adminCountyFR;
    private String phonesFR;
    private String faxesFR;

    public BuyRequestVO() {}

    public BuyRequestVO(BuyRequest buyRequest) {
        this.id = buyRequest.getId();
        this.domainName = buyRequest.getDomainName();
        this.domainHolder = buyRequest.getDomainHolder();
        this.remark = buyRequest.getRemark();
        this.adminName = buyRequest.getAdminName();
        this.adminEmail = buyRequest.getAdminEmail();
        this.adminCompanyName = buyRequest.getAdminCompanyName();
        this.adminAddress = buyRequest.getAdminAddress();
        this.adminCountry = new CountryVO(buyRequest.getAdminCountry());
        this.adminCounty = new CountyVO(buyRequest.getAdminCounty());
        this.phones = buyRequest.getPhones();
        this.faxes = buyRequest.getFaxes();
        this.creatorNH = buyRequest.getCreatorNH();
        this.creationDate = buyRequest.getCreationDate();
        this.authcodeCreationDate = buyRequest.getAuthcodeCreationDate();
        this.status = buyRequest.getStatus();
        this.checkedOut = buyRequest.getCheckedOutTo() != null;
        this.hostmasterRemark = buyRequest.getHostmasterRemark();
        this.changeDate = buyRequest.getChangeDate();

        if (buyRequest.getDomainNameFR() != null) {
            this.domainNameFR = buyRequest.getDomainNameFR().getDescription();
        }
        if (buyRequest.getDomainHolderFR() != null) {
            this.domainHolderFR = buyRequest.getDomainHolderFR().getDescription();
        }
        if (buyRequest.getAdminNameFR() != null) {
            this.adminNameFR = buyRequest.getAdminNameFR().getDescription();
        }
        if (buyRequest.getAdminEmailFR() != null) {
            this.adminEmailFR = buyRequest.getAdminEmailFR().getDescription();
        }
        if (buyRequest.getAdminCompanyNameFR() != null) {
            this.adminCompanyNameFR = buyRequest.getAdminCompanyNameFR().getDescription();
        }
        if (buyRequest.getAdminAddressFR() != null) {
            this.adminAddressFR = buyRequest.getAdminAddressFR().getDescription();
        }
        if (buyRequest.getAdminCountryFR() != null) {
            this.adminCountryFR = buyRequest.getAdminCountryFR().getDescription();
        }
        if (buyRequest.getAdminCountyFR() != null) {
            this.adminCountyFR = buyRequest.getAdminCountyFR().getDescription();
        }
        if (buyRequest.getPhonesFR() != null) {
            this.phonesFR = buyRequest.getPhonesFR().getDescription();
        }
        if (buyRequest.getFaxesFR() != null) {
            this.faxesFR = buyRequest.getFaxesFR().getDescription();
        }
    }

}
