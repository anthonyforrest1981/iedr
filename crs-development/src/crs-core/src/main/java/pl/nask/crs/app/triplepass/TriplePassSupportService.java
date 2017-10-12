package pl.nask.crs.app.triplepass;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.app.triplepass.exceptions.TriplePassException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

public interface TriplePassSupportService {

    void triplePass(AuthenticatedUser user) throws AccessDeniedException;

    public void triplePass(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TriplePassException, DomainLockedException, TicketNotFoundException,
            TicketIllegalStateException, DomainNotFoundException, NicHandleException, EmptyRemarkException,
            DomainIllegalStateException, ValidationException, ClassDoesNotMatchCategoryException, FinancialCheckException,
            TechnicalCheckException, HostNotConfiguredException, TransactionNotFoundException, CardPaymentException,
            CategoryDoesNotMatchSubcategoryException;

}
