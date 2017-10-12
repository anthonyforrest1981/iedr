package pl.nask.crs.app;

/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.SecondaryMarketStatus;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.ticket.Ticket;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public final class ValidationHelper {

    public static final int REQUESTERS_REMARK_MAX_LENGTH = 10000;
    public static final int HOLDER_MAX_LENGTH = 255;
    public static final int CHARITY_CODE_MAX_LENGTH = 20;

    /*>>>@EnsuresNonNull("#1.getOperation().getDomainNameField().getNewValue()")*/
    /*>>>@Pure*/
    public static void validateDomainName(Ticket ticket) throws DomainNameMandatoryException {
        if (isEmpty(ticket.getOperation().getDomainNameField().getNewValue()))
            throw new DomainNameMandatoryException();
    }

    public static void validateRequestersRemark(Ticket ticket) throws HolderRemarkTooLongException {
        if (!isEmpty(ticket.getRequestersRemark())
                && ticket.getRequestersRemark().length() > REQUESTERS_REMARK_MAX_LENGTH) {
            throw new HolderRemarkTooLongException();
        }
    }

    public static void validateHolder(Ticket ticket)
            throws DomainHolderMandatoryException, HolderCategoryMandatoryException, HolderClassMandatoryException,
            DomainHolderTooLongException {

        validateHolder(ticket.getOperation().getDomainHolderField().getNewValue());
        if (ticket.getOperation().getDomainHolderClassField().getNewValue() == null) {
            throw new HolderClassMandatoryException();
        }
        if (ticket.getOperation().getDomainHolderCategoryField().getNewValue() == null) {
            throw new HolderCategoryMandatoryException();
        }
    }

    public static void validateHolder(String holder)
            throws DomainHolderMandatoryException, DomainHolderTooLongException {
        if (isEmpty(holder)) {
            throw new DomainHolderMandatoryException();
        }
        if (holder.length() > HOLDER_MAX_LENGTH) {
            throw new DomainHolderTooLongException();
        }
    }

    public static void validateCharityCode(Ticket ticket) throws CharityCodeTooLongException {
        if (ticket.getCharityCode() != null && ticket.getCharityCode().length() > CHARITY_CODE_MAX_LENGTH) {
            throw new CharityCodeTooLongException();
        }
    }

    public static void validateAccountId(long id, Domain domain) throws DomainManagedByAnotherResellerException {
        if (id != domain.getResellerAccount().getId())
            throw new DomainManagedByAnotherResellerException(domain.getName());
    }

    public static void validateNRP(Domain domain) throws DomainInNRPException {
        if (domain.isNRP())
            throw new DomainInNRPException(domain.getName());
    }

    public static void checkIsEditable(Domain domain) throws DomainLockedException, SellRequestExistsException {
        if (Boolean.TRUE.equals(domain.getDsmState().getLocked())) {
            throw new DomainLockedException(domain.getName());
        }
        if (SecondaryMarketStatus.SellRequestRegistered.equals(domain.getDsmState().getSecondaryMarketStatus())) {
            throw new SellRequestExistsException(domain.getName());
        }
    }

}
