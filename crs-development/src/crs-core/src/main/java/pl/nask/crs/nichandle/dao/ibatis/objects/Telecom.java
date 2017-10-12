package pl.nask.crs.nichandle.dao.ibatis.objects;

public class Telecom {

    private String number;
    private TelecomType type;
    private int order;

    public Telecom() {
    }

    public Telecom(String number, TelecomType type, int order) {
        this.number = number;
        this.type = type;
        this.order = order;
    }

    public TelecomType getType() {
        return type;
    }

    public void setType(TelecomType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
