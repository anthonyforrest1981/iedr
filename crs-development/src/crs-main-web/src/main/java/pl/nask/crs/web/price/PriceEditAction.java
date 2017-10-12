package pl.nask.crs.web.price;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.price.DomainPrice;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceEditAction extends PriceAction {

    private int id;
    private String code;

    public PriceEditAction(PaymentAppService paymentAppService) {
        super(paymentAppService);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String input() throws Exception {
        DomainPrice domainPrice = paymentAppService.getPrice(getUser(), id);
        wrapper = new PriceWrapper(domainPrice);
        return INPUT;
    }

    public String save() throws Exception {
        paymentAppService.modifyPrice(getUser(), wrapper.getId(), wrapper.getDescription(), wrapper.getPrice(),
                wrapper.getDuration(), wrapper.getValidFrom(), wrapper.getValidTo(), wrapper.isForRegistration(),
                wrapper.isForRenewal(), wrapper.isDirect());
        return SUCCESS;
    }
}
