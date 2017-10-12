package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.InternalRegistrar;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class InternalRegistrarVO {

    private String accountName;
    private String billingNH;

    public InternalRegistrarVO() {}

    public InternalRegistrarVO(InternalRegistrar internalRegistrar) {
        this.accountName = internalRegistrar.getAccountName();
        this.billingNH = internalRegistrar.getBillingNH();
    }
}
