package pl.nask.crs.api.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.PlainDomain;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlainDomainVO {

    private String name;
    private String holder;
    private Date registrationDate;
    private Date renewalDate;
    private Date transferDate;
    private Date suspensionDate;
    private Date deletionDate;
    private DsmStateVO dsmState;
    private Date lockingDate;
    private Date lockingRenewalDate;

    public PlainDomainVO() {}

    public PlainDomainVO(PlainDomain plainDomain) {
        this.name = plainDomain.getName();
        this.holder = plainDomain.getHolder();
        this.registrationDate = plainDomain.getRegistrationDate();
        this.renewalDate = plainDomain.getRenewalDate();
        this.transferDate = plainDomain.getTransferDate();
        this.suspensionDate = plainDomain.getSuspensionDate();
        this.deletionDate = plainDomain.getDeletionDate();
        this.dsmState = new DsmStateVO(plainDomain.getDsmState());
        this.lockingDate = plainDomain.getLockingDate();
        this.lockingRenewalDate = plainDomain.getLockingRenewalDate();
    }

    public String getName() {
        return name;
    }

    public DsmStateVO getDsmState() {
        return dsmState;
    }

    public void setRemoveFromVoluntaryNRPPossible(boolean flag) {
        this.dsmState.setRemoveFromVoluntaryNRPPossible(flag);
    }

    public void setEnterVoluntaryNRPPossible(boolean flag) {
        this.dsmState.setEnterVoluntaryNRPPossible(flag);
    }
}
