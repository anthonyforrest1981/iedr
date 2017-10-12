package pl.nask.crs.api.validation;

/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.ReservationSearchCriteriaVO;
import pl.nask.crs.api.vo.search.TicketSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public final class ValidationHelper {

    // allowed characters based on https://jira.domainregistry.ie/jira/browse/CRS-795 with removed duplicates
    private static final String CARD_HOLDER_PATTERN = "^[a-z0-9àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿß \\p{Punct}]+$";

    private ValidationHelper() {}

    /*>>>@EnsuresNonNull("#1")*/
    public static void validateNonNull(/*>>>@Nullable*/ Object o, String msg) throws GenericValidationException {
        try {
            Validator.assertNotNull(o, msg);
        } catch (IllegalArgumentException e) {
            throw new GenericValidationException(e.getMessage());
        }
    }

    /*>>>@EnsuresNonNull({"#1", "#1.getUsername()", "#1.getAuthenticationToken()"})*/
    public static void validate(/*>>>@Nullable*/ AuthenticatedUserVO token) throws AuthenticationException {
        try {
            Validator.assertNotNull(token, "user");
            Validator.assertNotEmpty(token.getUsername(), "user.username");
            Validator.assertNotEmpty(token.getAuthenticationToken(), "user.authenticationToken");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    public static void validate(CreditCardVO creditCard) throws GenericValidationException {
        try {
            Validator.assertNotNull(creditCard, "creditCard");
            Validator.assertNotEmpty(creditCard.getCardNumber(), "creditCard.cardNumber");
            Validator.assertNotNull(creditCard.getCardExpDate(), "creditCard.cardExpDate");
            Validator.assertNotEmpty(creditCard.getCardType(), "creditCard.cardType");
            Validator.assertNotEmpty(creditCard.getCardHolderName(), "creditCard.cardHolderName");
            validateCardHolder(creditCard.getCardHolderName());
            validateCvn(creditCard.getCvnNumber(), creditCard.getCvnPresenceIndicator());
        } catch (IllegalArgumentException e) {
            throw new GenericValidationException(e.getMessage());
        }
    }

    private static void validateCardHolder(String cardHolderName) throws GenericValidationException {
        if (!cardHolderName.toLowerCase().matches(CARD_HOLDER_PATTERN)) {
            throw new GenericValidationException("Credit card holder contains illegal character");
        }
    }

    private static void validateCvn(String cvnNumber, Integer cvnPresenceIndicator) throws GenericValidationException {
        if (cvnNumber != null) {
            Validator.assertNotNull(cvnPresenceIndicator, "paymentRequest.cvnPresenceIndicator");
            if (!cvnNumber.matches("^[0-9]{3}$"))
                throw new GenericValidationException("Invalid CVN Number");
            if (!cvnPresenceIndicator.toString().matches("^[1234]{1}$"))
                throw new GenericValidationException("Invalid CVN Presence Indicator");
        } else {
            if (cvnPresenceIndicator != null)
                throw new GenericValidationException("paymentRequest.cvnPresenceIndicator must be null");
        }
    }

    /*>>>@EnsuresNonNull("#1")*/
    public static void validate(/*>>>@Nullable*/ TicketEditVO ticketEdit)
            throws GenericValidationException, TooManyNameserversException, GlueRequiredException, IpSyntaxException,
            GlueNotAllowedException, NameserverNameSyntaxException, TooFewNameserversException,
            DuplicatedNameserverException {
        Validator.assertNotNull(ticketEdit, "domainOperation");
        Validator.assertNotEmpty(ticketEdit.getDomainNameField(), "domainNameField");
        checkNameserversNotNull(ticketEdit.getNameservers());
        validateStringChangeList(ticketEdit.getAdminContactsField(), "adminContactsField");
        validateStringChangeList(ticketEdit.getTechContactsField(), "techContactsField");
        validateStringChangeList(ticketEdit.getBillingContactsField(), "billingContactsField");
        Validator.assertNotEmpty(ticketEdit.getDomainHolderField(), "domainHolderField");
        Validator.assertNotNull(ticketEdit.getResellerAccountField(), "resellerAccountField");
    }

    private static void validateStringChangeList(List<String> fields, String fieldName)
            throws GenericValidationException {
        if (Validator.isEmpty(fields)) {
            throw new GenericValidationException(fieldName + " required");
        } else {
            // only first element is required
            Validator.assertNotEmpty(fields.get(0), fieldName + "[" + 0 + "]");
        }
    }

    private static void checkNameserversNotNull(List<NameserverVO> nameservers)
            throws GenericValidationException, TooFewNameserversException, TooManyNameserversException,
            GlueNotAllowedException, GlueRequiredException, IpSyntaxException, NameserverNameSyntaxException,
            DuplicatedNameserverException {
        if (nameservers == null) {
            throw new GenericValidationException("Nameserver list cannot be null");
        }
        for (NameserverVO nsvo : nameservers) {
            if (nsvo == null || nsvo.getName() == null) {
                throw new NameserverNameSyntaxException("");
            }
        }
    }

    public static boolean isEmptySimpleDomainFieldChange(SimpleDomainFieldChange<String> fieldChange) {
        return fieldChange == null
                || (Validator.isEmpty(fieldChange.getCurrentValue()) && Validator.isEmpty(fieldChange.getNewValue())
                    && fieldChange.getFailureReason() == null);
    }

    public static boolean isEmptyIpFieldChange(IpFieldChange fieldChange) {
        return fieldChange == null
                || (Validator.isEmpty(fieldChange.getOldValue()) && Validator.isEmpty(fieldChange.getNewValue())
                    && fieldChange.getFailureReason() == null);
    }

    public static void validate(List<DomainWithPeriodVO> domains) throws GenericValidationException {
        if (domains == null || domains.isEmpty())
            throw new GenericValidationException("domains list is empty");
        for (DomainWithPeriodVO d : domains)
            if (d.getDomainName() == null)
                throw new GenericValidationException("domains.domainName is null");
    }

    public static void validate(PaymentMethod paymentMethod, CreditCardVO creditCardVO)
            throws GenericValidationException {
        if (paymentMethod == null)
            throw new GenericValidationException("payment method is null");
        switch (paymentMethod) {
            case CC:
                validate(creditCardVO);
                break;
            case ADP:
                if (creditCardVO != null)
                    throw new GenericValidationException("payment request");
                break;
            default:
                throw new GenericValidationException("Invalid payment method");
        }
    }

    public static void validate(ReservationSearchCriteriaVO criteriaVO) throws GenericValidationException {
        if (criteriaVO == null)
            throw new GenericValidationException("ReservationCriteriaVO is null");
        if (criteriaVO.getPaymentMethod() == null)
            throw new GenericValidationException("criteriaVO.paymentMethod");
    }

    /*>>>@EnsuresNonNull("#1")*/
    public static void validate(/*>>>@Nullable*/ TicketSearchCriteriaVO criteria) throws GenericValidationException {
        validateNonNull(criteria, "search criteria");
        criteria.filterValues();
    }
}
