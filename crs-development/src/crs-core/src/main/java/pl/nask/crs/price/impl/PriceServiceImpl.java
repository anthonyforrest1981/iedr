package pl.nask.crs.price.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.DomainPriceDatesOutOfBoundException;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.PriceNotFoundException;
import pl.nask.crs.price.PriceService;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.price.email.PricingEmailParameters;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.vat.DomainPriceValidator;
import pl.nask.crs.vat.dao.VatDAO;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceServiceImpl implements PriceService {

    private final static Logger LOG = Logger.getLogger(PriceServiceImpl.class);
    private DomainPricingDAO pricingDAO;
    private VatDAO vatDAO;
    private EmailTemplateSender emailTemplateSender;

    public PriceServiceImpl(DomainPricingDAO pricingDAO, VatDAO vatDAO, EmailTemplateSender emailTemplateSender) {
        this.pricingDAO = pricingDAO;
        this.vatDAO = vatDAO;
        this.emailTemplateSender = emailTemplateSender;
    }

    @Override
    public void addPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct)
            throws ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException {
        DomainPrice newDomainPrice = DomainPrice.newInstance(code, decription, price, duration, validFrom, validTo,
                forRegistration, forRenewal, direct);
        DomainPriceValidator.validate(vatDAO.getAllValid(), newDomainPrice);
        pricingDAO.create(newDomainPrice);
        sendEmail(newDomainPrice, user);
    }

    @Override
    public List<DomainPrice> getAll() {
        return pricingDAO.getAll();
    }

    @Override
    public void save(DomainPrice domainPrice, AuthenticatedUser user)
            throws ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException {
        DomainPriceValidator.validate(vatDAO.getAllValid(), domainPrice);
        pricingDAO.update(domainPrice);
        sendEmail(domainPrice, user);
    }

    @Override
    public DomainPrice get(int id) throws PriceNotFoundException {
        DomainPrice domainPrice = pricingDAO.get(id);
        if (domainPrice == null) {
            throw new PriceNotFoundException(id);
        }
        return domainPrice;
    }

    @Override
    public LimitedSearchResult<DomainPrice> findAll(long offset, long limit, List<SortCriterion> sortBy) {
        return pricingDAO.findAll(offset, limit, sortBy);
    }

    private void sendEmail(DomainPrice domainPrice, AuthenticatedUser user) {
        try {
            EmailParameters params = new PricingEmailParameters(domainPrice, user.getUsername());
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.PRICING_TABLE_UPDATE.getId(), params);
        } catch (Exception e) {
            LOG.warn("Problem with pricing update email occurred.", e);
        }
    }
}
