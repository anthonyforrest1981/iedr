package pl.nask.crs.domains.services.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;

/**
 * @author Kasia Fulara, Artur Gniadzik
 */
public class DomainEmailParameters implements EmailParameters {

    private final static String POSTMASTER_PREFIX = "postmaster@";
    private final static String WEBMASTER_PREFIX = "webmaster@";
    private final static String INFO_PREFIX = "info@";
    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    private Domain domain;
    private String username;
    private Contact losingBillingContact;
    private Contact gainingBillingContact;
    private BuyRequest buyRequest;
    private SellRequest sellRequest;

    private MapBasedEmailParameters additionalParams = new MapBasedEmailParameters();

    public void setLosingBillingContact(Contact losingBillingContact) {
        this.losingBillingContact = losingBillingContact;
    }

    public void setGainingBillingContact(Contact gainingBillingContact) {
        this.gainingBillingContact = gainingBillingContact;
    }

    public void setBuyRequest(BuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }

    public void setSellRequest(SellRequest sellRequest) {
        this.sellRequest = sellRequest;
    }

    // used if there is a need of fetching additional Contact data
    private final NicHandleDAO nicHandleDAO;

    public DomainEmailParameters(String username, NicHandleDAO nicHandleDAO, Domain domain) {
        Validator.assertNotNull(domain, "domain");
        Validator.assertNotNull(nicHandleDAO, "nicHandleDAO");
        this.domain = domain;
        this.username = username;
        this.nicHandleDAO = nicHandleDAO;
    }

    public String getLoggedInNicHandle() {
        return this.username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        if (gaining && gainingBillingContact != null) {
            return gainingBillingContact.getNicHandle();
        } else if (losingBillingContact != null) { // accountrelated is old / current
            return losingBillingContact.getNicHandle();
        } else {
            return this.domain.getBillingContactNic();
        }
    }

    public String getDomainName() {
        return this.domain.getName();
    }

    private static Set<ParameterName> parameterNames;
    static {
        parameterNames = new HashSet<ParameterName>(Arrays.asList(ParameterNameEnum.DOMAIN,
                ParameterNameEnum.REGISTRATION_DATE, ParameterNameEnum.RENEWAL_DATE, ParameterNameEnum.SUSPENSION_DATE,
                ParameterNameEnum.DELETION_DATE, ParameterNameEnum.BILL_C_NIC, ParameterNameEnum.BILL_C_CO_NAME,
                ParameterNameEnum.BILL_C_TEL, ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.BILL_C_NAME,
                ParameterNameEnum.REGISTRAR_NAME, ParameterNameEnum.POSTMASTER, ParameterNameEnum.WEBMASTER,
                ParameterNameEnum.INFO, ParameterNameEnum.TECH_C_EMAIL, ParameterNameEnum.TECH_C_NAME,
                ParameterNameEnum.HOLDER, ParameterNameEnum.GAINING_BILL_C_NAME,
                ParameterNameEnum.GAINING_BILL_C_EMAIL, ParameterNameEnum.GAINING_BILL_C_NIC,
                ParameterNameEnum.LOSING_BILL_C_EMAIL, ParameterNameEnum.LOSING_BILL_C_NAME,
                ParameterNameEnum.TRANSACTION_DETAIL, ParameterNameEnum.TRANSACTION_VALUE, ParameterNameEnum.ORDER_ID,
                ParameterNameEnum.TICKET_ID, ParameterNameEnum.WHOIS, ParameterNameEnum.BUYER_EMAIL,
                ParameterNameEnum.COUNTDOWN_PERIOD));
        parameterNames.addAll(EmailParametersUtils.getAdminRelatedParameterNames());
    }

    public List<ParameterName> getParameterNames() {
        return new ArrayList<>(parameterNames);
    }

    public String getParameterValue(String name, boolean html) {

        Validator.assertNotNull(name, "parameter name");

        if (EmailParametersUtils.isAdminRelatedParameterName(name)) {
            return EmailParametersUtils.getAdminRelatedParameterValue(domain.getAdminContacts(), name, html);
        }

        ParameterNameEnum domainName = ParameterNameEnum.forName(name);
        switch (domainName) {
            case DOMAIN:
                return domain.getName();
            case REGISTRATION_DATE:
                return FORMATTER.format(domain.getRegistrationDate());
            case RENEWAL_DATE:
                return FORMATTER.format(domain.getRenewalDate());
            case SUSPENSION_DATE:
                return domain.getSuspensionDate() == null ? "" : FORMATTER.format(domain.getSuspensionDate());
            case DELETION_DATE:
                return domain.getDeletionDate() == null ? "" : FORMATTER.format(domain.getDeletionDate());
            case BILL_C_NIC: {
                final Contact billingContact = domain.getBillingContact();
                return billingContact == null ? null : billingContact.getNicHandle();
            }
            case BILL_C_CO_NAME: {
                final Contact billingContact = domain.getBillingContact();
                return billingContact == null ? null : billingContact.getCompanyName();
            }
            case BILL_C_TEL:
                NicHandle nh = nicHandleDAO.get(domain.getBillingContactNic());
                if (nh == null) {
                    return null;
                }
                return nh.getPhonesAsString();
            case BILL_C_EMAIL: {
                final Contact billingContact = domain.getBillingContact();
                return billingContact == null ? null : billingContact.getEmail();
            }
            case BILL_C_NAME:
            case REGISTRAR_NAME: {
                final Contact billingContact = domain.getBillingContact();
                return billingContact == null ? null : billingContact.getName();
            }
            case POSTMASTER:
                return POSTMASTER_PREFIX + domain.getName();
            case WEBMASTER:
                return WEBMASTER_PREFIX + domain.getName();
            case INFO:
                return INFO_PREFIX + domain.getName();
            case TECH_C_EMAIL: {
                final Contact techContact = domain.getTechContact();
                return techContact == null ? null : techContact.getEmail();
            }
            case TECH_C_NAME: {
                final Contact techContact = domain.getTechContact();
                return techContact == null ? null : techContact.getName();
            }
            case HOLDER:
                return domain.getHolder();
            case GAINING_BILL_C_NAME:
                return gainingBillingContact == null ? null : gainingBillingContact.getName();
            case GAINING_BILL_C_EMAIL:
                return gainingBillingContact == null ? null : gainingBillingContact.getEmail();
            case GAINING_BILL_C_NIC:
                return gainingBillingContact == null ? null : gainingBillingContact.getNicHandle();
            case LOSING_BILL_C_EMAIL:
                return losingBillingContact == null ? null : losingBillingContact.getEmail();
            case LOSING_BILL_C_NAME:
                return losingBillingContact == null ? null : losingBillingContact.getName();
            case BUYER_EMAIL:
                if (buyRequest != null) {
                    return buyRequest.getAdminEmail();
                }
                if (sellRequest != null) {
                    return sellRequest.getBuyRequest().getAdminEmail();
                }
            default:
                return additionalParams.getParameterValue(name, html);
        }
    }

    public void setParameter(ParameterNameEnum name, Object value) {
        this.additionalParams.set(name, value);
    }

}
