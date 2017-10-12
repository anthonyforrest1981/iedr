package pl.nask.crs.api.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.ExtendedDeposit;
import pl.nask.crs.payment.DepositTransactionType;

@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositVO {

    private String nicHandleId;
    private String nicHandleName;
    private Date transactionDate;
    private BigDecimal openBal;

    private BigDecimal closeBal;
    private BigDecimal reservedFunds;
    private BigDecimal availableFunds;

    private BigDecimal transactionAmount;
    private DepositTransactionType transactionType;

    private String orderId;
    private String remark;

    public DepositVO() {}

    public DepositVO(ExtendedDeposit extendedDeposit) {
        this((Deposit) extendedDeposit);
        this.reservedFunds = extendedDeposit.getReservedFunds();
        this.availableFunds = extendedDeposit.getCloseBalMinusReservations();
    }

    public DepositVO(Deposit deposit) {
        this.nicHandleId = deposit.getNicHandleId();
        this.nicHandleName = deposit.getNicHandleName();
        this.transactionDate = deposit.getTransactionDate();
        this.openBal = deposit.getOpenBal();
        this.closeBal = deposit.getCloseBal();
        this.transactionAmount = deposit.getTransactionAmount();
        this.transactionType = deposit.getTransactionType();
        this.orderId = deposit.getOrderId();
        this.remark = deposit.getRemark();
    }

}
