package pl.nask.crs.iedrapi.impl;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.ParameterValueRangeErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.Ticket;

public class CommandHandlerHelper {
    private final NicHandleAppService nicHandleAppService;
    private final AuthenticationService authService;
    private final DomainAppService domainAppService;
    private final TicketAppService ticketAppService;
    private final PaymentAppService paymentAppService;
    private final CommonAppService commonAppService;
    private final SecondaryMarketAppService secondaryMarketAppService;
    private final CountryFactory countryFactory;
    private final ServicesRegistry servicesRegistry;

    public CommandHandlerHelper(NicHandleAppService nicHandleAppService, AuthenticationService authService,
            DomainAppService domainAppService, TicketAppService ticketAppService, PaymentAppService paymentAppService,
            CommonAppService commonAppService, SecondaryMarketAppService secondaryMarketAppService,
            CountryFactory countryFactory, ServicesRegistry registry) {
        Validator.assertNotNull(nicHandleAppService, "nicHandleAppService");
        Validator.assertNotNull(authService, "authService");
        Validator.assertNotNull(domainAppService, "domainAppService");
        Validator.assertNotNull(ticketAppService, "ticketAppService");
        Validator.assertNotNull(paymentAppService, "paymentAppService");
        Validator.assertNotNull(commonAppService, "commonAppService");
        Validator.assertNotNull(secondaryMarketAppService, "secondaryMarketAppService");
        Validator.assertNotNull(countryFactory, "countryFactory");
        Validator.assertNotNull(registry, "servicesRegistry");

        this.nicHandleAppService = nicHandleAppService;
        this.authService = authService;
        this.domainAppService = domainAppService;
        this.ticketAppService = ticketAppService;
        this.paymentAppService = paymentAppService;
        this.commonAppService = commonAppService;
        this.secondaryMarketAppService = secondaryMarketAppService;
        this.countryFactory = countryFactory;
        this.servicesRegistry = registry;
    }

    public long getAccountId(AuthenticatedUserVO user) throws AccessDeniedException, CommandFailed {
        try {
            NicHandle loggedIn = nicHandleAppService.get(user, user.getUsername());
            return loggedIn.getAccount().getId();
        } catch (NicHandleNotFoundException e) {
            // shall never happen!
            throw new CommandFailed(e);
        }
    }

    public String getAccountName(AuthenticatedUserVO user) throws AccessDeniedException, CommandFailed {
        try {
            NicHandle loggedIn = nicHandleAppService.get(user, user.getUsername());
            return loggedIn.getAccount().getName();
        } catch (NicHandleNotFoundException e) {
            // shall never happen!
            throw new CommandFailed(e);
        }
    }

    public NicHandleAppService getNicHandleAppService() {
        return nicHandleAppService;
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public DomainAppService getDomainAppService() {
        return domainAppService;
    }

    public TicketAppService getTicketAppService() {
        return ticketAppService;
    }

    public PaymentAppService getPaymentAppService() {
        return paymentAppService;
    }

    public void validatePage(long total, long offset) throws ParameterValueRangeErrorException {
        if (total < offset)
            throw new ParameterValueRangeErrorException(ReasonCode.ATTRIBUTE_PAGE_IS_OUT_OF_RANGE);
    }

    public CommonAppService getCommonAppService() {
        return commonAppService;
    }

    public SecondaryMarketAppService getSecondaryMarketAppService() {
        return secondaryMarketAppService;
    }

    public Ticket getTicketForDomain(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, CommandFailed {
        try {
            return ticketAppService.getTicketForDomain(user, domainName);
        } catch (TooManyTicketsException e) {
            throw new CommandFailed("Too many tickets found for domain name = " + domainName);
        }
    }

    public Domain getDomainUnsafe(String domainName) throws DomainNotFoundException {
        return servicesRegistry.getDomainSearchService().getDomain(domainName);
    }

    public CountryFactory getCountryFactory() {
        return countryFactory;
    }

    public ServicesRegistry getServicesRegistry() {
        return servicesRegistry;
    }
}
