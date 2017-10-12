package pl.nask.crs.payment.exceptions;

import java.math.BigDecimal;

public class DepositBelowLimitException extends Exception {

    public DepositBelowLimitException(BigDecimal limit) {
        super("Miminal allowed amount is " + limit + " €");
    }
}
