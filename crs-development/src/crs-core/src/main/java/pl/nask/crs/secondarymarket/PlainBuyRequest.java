package pl.nask.crs.secondarymarket;

import java.util.Date;

public class PlainBuyRequest {
    private long id = -1;
    private String domainName;
    private String creatorNH;
    private String domainHolder;
    private String remark;
    private String hostmasterRemark;
    private String adminName;
    private String adminEmail;
    private String adminCompanyName;
    private String adminAddress;
    private Date creationDate;
    private String authcode;
    private Date authcodeCreationDate;
    private String checkedOutTo;

    public PlainBuyRequest() {
    }

    public PlainBuyRequest(String domainName, String creatorNH, String domainHolder, String remark, String adminName,
            String adminEmail, String adminCompanyName, String adminAddress, Date creationDate) {
        this.domainName = domainName;
        this.creatorNH = creatorNH;
        this.domainHolder = domainHolder;
        this.remark = remark;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminCompanyName = adminCompanyName;
        this.adminAddress = adminAddress;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getCreatorNH() {
        return creatorNH;
    }

    public void setCreatorNH(String creatorNH) {
        this.creatorNH = creatorNH;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHostmasterRemark() {
        return hostmasterRemark;
    }

    public void setHostmasterRemark(String hostmasterRemark) {
        this.hostmasterRemark = hostmasterRemark;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminCompanyName() {
        return adminCompanyName;
    }

    public void setAdminCompanyName(String adminCompanyName) {
        this.adminCompanyName = adminCompanyName;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public Date getAuthcodeCreationDate() {
        return authcodeCreationDate;
    }

    public void setAuthcodeCreationDate(Date authcodeCreationDate) {
        this.authcodeCreationDate = authcodeCreationDate;
    }

    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    public void setCheckedOutTo(String checkedOutTo) {
        this.checkedOutTo = checkedOutTo;
    }

    public boolean isCheckedOut() {
        return checkedOutTo != null;
    }
}
