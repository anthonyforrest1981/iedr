package pl.nask.crs.payment.exceptions;

import java.math.BigDecimal;

public class DepositOverLimitException extends Exception {

    public DepositOverLimitException(BigDecimal limit) {
        super("Maximal allowed amount is " + limit + " €");
    }

}
