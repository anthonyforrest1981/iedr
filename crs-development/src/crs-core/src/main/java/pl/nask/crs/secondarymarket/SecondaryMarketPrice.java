package pl.nask.crs.secondarymarket;

import java.math.BigDecimal;

import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.OperationType;

public class SecondaryMarketPrice {

    private Long id;
    private OperationType requestType;
    private CustomerType customerType;
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getRequestType() {
        return requestType;
    }

    public void setRequestType(OperationType requestType) {
        this.requestType = requestType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
