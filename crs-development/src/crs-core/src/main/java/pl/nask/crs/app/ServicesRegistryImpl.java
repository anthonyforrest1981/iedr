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

public class ServicesRegistryImpl implements ServicesRegistry {

    private DomainSearchService domainSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private NicHandleService nicHandleService;
    private PaymentService paymentService;
    private PaymentSearchService paymentSearchService;
    private InvoicingService invoicingService;
    private AccountSearchService accountSearchService;
    private TicketSearchService ticketSearchService;
    private TicketService ticketService;
    private SecondaryMarketService secondaryMarketService;
    private DnsCheckService dnsCheckService;
    private DomainService domainService;
    private EmailTemplateSender emailTemplateSender;
    private VatService vatService;
    private PriceService priceService;
    private DepositService depositService;
    private ContactSearchService contactSearchService;
    private EntityService entityService;
    private TopLevelDomainService topLevelDomainService;
    private ApplicationConfig config;

    @Override
    public DomainSearchService getDomainSearchService() {
        return domainSearchService;
    }

    @Override
    public NicHandleSearchService getNicHandleSearchService() {
        return nicHandleSearchService;
    }

    @Override
    public NicHandleService getNicHandleService() {
        return nicHandleService;
    }

    @Override
    public PaymentService getPaymentService() {
        return paymentService;
    }

    @Override
    public PaymentSearchService getPaymentSearchService() {
        return paymentSearchService;
    }

    @Override
    public InvoicingService getInvoicingService() {
        return invoicingService;
    }

    @Override
    public AccountSearchService getAccountSearchService() {
        return accountSearchService;
    }

    @Override
    public TicketSearchService getTicketSearchService() {
        return ticketSearchService;
    }

    @Override
    public TicketService getTicketService() {
        return ticketService;
    }

    @Override
    public SecondaryMarketService getSecondaryMarketService() {
        return secondaryMarketService;
    }

    @Override
    public DnsCheckService getDnsCheckService() {
        return dnsCheckService;
    }

    @Override
    public DomainService getDomainService() {
        return domainService;
    }

    @Override
    public EmailTemplateSender getEmailTemplateSender() {
        return emailTemplateSender;
    }

    @Override
    public VatService getVatService() {
        return vatService;
    }

    @Override
    public PriceService getPriceService() {
        return priceService;
    }

    @Override
    public DepositService getDepositService() {
        return depositService;
    }

    @Override
    public ContactSearchService getContactSearchService() {
        return contactSearchService;
    }

    @Override
    public EntityService getEntityService() {
        return entityService;
    }

    @Override
    public TopLevelDomainService getTopLevelDomainService() {
        return topLevelDomainService;
    }

    @Override
    public ApplicationConfig getApplicationConfig() {
        return config;
    }

    public void setDomainSearchService(DomainSearchService domainSearchService) {
        this.domainSearchService = domainSearchService;
    }

    public void setNicHandleSearchService(NicHandleSearchService nicHandleSearchService) {
        this.nicHandleSearchService = nicHandleSearchService;
    }

    public void setNicHandleService(NicHandleService nicHandleService) {
        this.nicHandleService = nicHandleService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setPaymentSearchService(PaymentSearchService paymentSearchService) {
        this.paymentSearchService = paymentSearchService;
    }

    public void setInvoicingService(InvoicingService invoicingService) {
        this.invoicingService = invoicingService;
    }

    public void setAccountSearchService(AccountSearchService accountSearchService) {
        this.accountSearchService = accountSearchService;
    }

    public void setTicketSearchService(TicketSearchService ticketSearchService) {
        this.ticketSearchService = ticketSearchService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void setSecondaryMarketService(SecondaryMarketService secondaryMarketService) {
        this.secondaryMarketService = secondaryMarketService;
    }

    public void setDnsCheckService(DnsCheckService dnsCheckService) {
        this.dnsCheckService = dnsCheckService;
    }

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    public void setEmailTemplateSender(EmailTemplateSender emailTemplateSender) {
        this.emailTemplateSender = emailTemplateSender;
    }

    public void setVatService(VatService vatService) {
        this.vatService = vatService;
    }

    public void setPriceService(PriceService priceService) {
        this.priceService = priceService;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }

    public void setContactSearchService(ContactSearchService contactSearchService) {
        this.contactSearchService = contactSearchService;
    }

    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    public void setTopLevelDomainService(TopLevelDomainService topLevelDomainService) {
        this.topLevelDomainService = topLevelDomainService;
    }

    public void setConfig(ApplicationConfig config) {
        this.config = config;
    }
}
