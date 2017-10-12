package pl.nask.crs.app;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.services.TopLevelDomainService;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.InvoicingService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.price.PriceService;
import pl.nask.crs.secondarymarket.services.SecondaryMarketService;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.vat.VatService;

public interface ServicesRegistry {
    DomainSearchService getDomainSearchService();

    AccountSearchService getAccountSearchService();

    NicHandleService getNicHandleService();

    NicHandleSearchService getNicHandleSearchService();

    PaymentService getPaymentService();

    PaymentSearchService getPaymentSearchService();

    InvoicingService getInvoicingService();

    TicketSearchService getTicketSearchService();

    TicketService getTicketService();

    SecondaryMarketService getSecondaryMarketService();

    DnsCheckService getDnsCheckService();

    DomainService getDomainService();

    EmailTemplateSender getEmailTemplateSender();

    VatService getVatService();

    PriceService getPriceService();

    DepositService getDepositService();

    ContactSearchService getContactSearchService();

    EntityService getEntityService();

    TopLevelDomainService getTopLevelDomainService();

    ApplicationConfig getApplicationConfig();

}
