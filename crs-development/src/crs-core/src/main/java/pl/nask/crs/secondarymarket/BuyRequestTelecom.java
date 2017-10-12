package pl.nask.crs.secondarymarket;

import pl.nask.crs.nichandle.dao.ibatis.objects.TelecomType;

public class BuyRequestTelecom {

    private TelecomType type;
    private String value;
    private int order;

    public BuyRequestTelecom() {
    }

    public BuyRequestTelecom(TelecomType type, String value, int order) {
        this.type = type;
        this.value = value;
        this.order = order;
    }

    public TelecomType getType() {
        return type;
    }

    public void setType(TelecomType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
