package pl.nask.crs.web.reports;

import java.util.List;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class TransactionInfoReportAction extends AuthenticatedUserAwareAction {

    private PaymentAppService paymentAppService;

    private String orderId;

    public TransactionInfoReportAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<DomainInfo> getTransactionInfos() throws AccessDeniedException {
        return paymentAppService.getTransactionInfo(getUser(), orderId);
    }
}
