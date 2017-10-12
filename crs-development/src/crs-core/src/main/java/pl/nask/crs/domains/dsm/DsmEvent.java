package pl.nask.crs.domains.dsm;

import java.util.Date;

public interface DsmEvent {

    String DOMAIN_RENEWAL_DATE = "DOMAIN_RENEWAL_DATE";

    String RENEWAL_PERIOD = "RENEWAL_PERIOD";

    String TICKET = "TICKET";

    String PAY_METHOD = "PAY_METHOD";

    String OLD_BILL_C = "OLD_BILL_C";

    String NEW_BILL_C = "NEW_BILL_C";

    String NEW_ADMIN_C = "NEW_ADMIN_C";

    String TRANSACTION_DETAIL = "TRANSACTION_DETAIL";

    String TRANSACTION_VALUE = "TRANSACTION_VALUE";

    String ORDER_ID = "ORDER_ID";

    String BUY_REQUEST = "BUY_REQUEST";

    String SELL_REQUEST = "SELL_REQUEST";

    String EVENT_DATE = "EVENT_DATE";

    String COUNTDOWN_PERIOD = "COUNTDOWN_PERIOD";

    DsmEventName getName();

    /**
     * Gets the parameter of type java.util.Date associated with this event.
     * @param name param name
     * @return param value, never null
     * @throws IllegalArgumentException if there is no parameter with such name or a parameter is not a type of Date
     */
    Date getDateParameter(String name);

    /**
     * Gets the parameter associated with this event.
     * @param name param name
     * @return param value, never null
     * @throws IllegalArgumentException if there is no parameter with such name
     */
    Object getParameter(String name);

    boolean hasParameter(String name);

    /**
     * Gets the parameter of type java.lang.String associated with this event.
     * @param name param name
     * @return param value, never null
     * @throws IllegalArgumentException if there is no parameter with such name or a parameter is not a type of Date
     */
    String getStringParameter(String name);

    int getIntParameter(String renewalPeriod);
}
