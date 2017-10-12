package pl.nask.crs.app.triplepass.email;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.ticket.operation.DomainOperation;

public class FinancialCheckEmailParameters implements EmailParameters {

    private NicHandle billingNH;
    private String domainName;
    private DomainOperation.DomainOperationType operationType;
    private String orderId;
    private TransactionDetails transactionDetails;
    private BigDecimal transactionValue;
    private String username;

    public FinancialCheckEmailParameters(String username, NicHandle billingNH, String domainName,
            DomainOperation.DomainOperationType operationType, String orderId, TransactionDetails transactionDetails,
            BigDecimal transactionValue) {
        this.billingNH = billingNH;
        this.domainName = domainName;
        this.orderId = orderId;
        this.username = username;
        this.operationType = operationType;
        this.transactionDetails = transactionDetails;
        this.transactionValue = MoneyUtils.getRoundedAndScaledValue(transactionValue);
    }

    public String getLoggedInNicHandle() {
        return this.username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return this.billingNH.getNicHandleId();
    }

    public String getDomainName() {
        return this.domainName;
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.BILL_C_NIC, ParameterNameEnum.BILL_C_EMAIL,
                ParameterNameEnum.BILL_C_NAME, ParameterNameEnum.DOMAIN, ParameterNameEnum.ORDER_ID,
                ParameterNameEnum.TRANSACTION_DETAIL, ParameterNameEnum.TRANSACTION_VALUE,
                ParameterNameEnum.TICKET_TYPE);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameter = ParameterNameEnum.forName(name);
        switch (parameter) {
            case BILL_C_NAME:
                return billingNH.getName();
            case BILL_C_NIC:
                return billingNH.getNicHandleId();
            case DOMAIN:
                return domainName;
            case BILL_C_EMAIL:
                return billingNH.getEmail();
            case TRANSACTION_DETAIL:
                return html ? transactionDetails.toHtmlString() : transactionDetails.toString();
            case TRANSACTION_VALUE:
                return MoneyUtils.getRoundedAndScaledValue(transactionValue, 2).toString();
            case TICKET_TYPE:
                return operationType.getFullName();
            case ORDER_ID:
                return orderId;
            default:
                return null;
        }
    }
}
