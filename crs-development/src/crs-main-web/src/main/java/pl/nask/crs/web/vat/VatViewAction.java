package pl.nask.crs.web.vat;

import java.util.List;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatViewAction extends AuthenticatedUserAwareAction {

    private static final String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";

    private PaymentAppService paymentAppService;

    private String datePattern = YEAR_MONTH_DAY_PATTERN;

    private Integer id;

    public VatViewAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public List<Vat> getVatList() throws AccessDeniedException {
        return paymentAppService.getValid(getUser());
    }

    public String getDatePattern() {
        return datePattern;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String invalidate() {
        try {
            paymentAppService.invalidate(getUser(), id);
        } catch (VatNotFoundException e) {
            addActionError("Vat not found!");
            return ERROR;
        } catch (NextValidVatNotFoundException e) {
            addActionError("Cannot invalidate last valid vat!");
            return ERROR;
        } catch (Exception e) {
            addActionError("ERROR: " + e.getMessage());
            return ERROR;
        }
        addActionMessage("Vat rate with id=" + id + " invalidated.");
        return SUCCESS;
    }
}
