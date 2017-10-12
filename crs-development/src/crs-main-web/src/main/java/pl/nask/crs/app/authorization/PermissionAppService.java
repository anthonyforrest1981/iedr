package pl.nask.crs.app.authorization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.authorization.queries.AccountSavePermissionQuery;
import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.LoginPermission;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

public class PermissionAppService {
    private static final Logger log = Logger.getLogger(PermissionAppService.class);

    private static final Map<String, PermissionQuery> permissions;

    static {
        permissions = new HashMap<String, PermissionQuery>();

        PermissionQuery triplePassPermission = new ConjunctionPermissionQuery(Arrays.asList(
                new NamedPermissionQuery(TriplePassAppService.class.getCanonicalName() + ".performFinancialCheck"),
                new NamedPermissionQuery(TriplePassAppService.class.getCanonicalName() + ".performTechnicalCheck"),
                new NamedPermissionQuery(TriplePassAppService.class.getCanonicalName() + ".performTicketCancellation"),
                new NamedPermissionQuery(TriplePassAppService.class.getCanonicalName() + ".promoteModificationTicket"),
                new NamedPermissionQuery(TriplePassAppService.class.getCanonicalName() + ".promoteTicketToDomain"),
                new NamedPermissionQuery(TriplePassAppService.class.getCanonicalName() + ".promoteTransferTicket")));

        permissions.put("login", new NamedPermissionQuery(new LoginPermission(LoginPermission.CRS).getName()));
        permissions.put("mainMenu", new NamedPermissionQuery("crs-menu"));
        // Menu Tickets
        permissions.put("ticketSection", new AlternativeNamesPermissionQuery("tickets", "ticketshistory",
                "ticketReports"));
        permissions.put("tickets", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".search"));
        permissions.put("ticketHistory", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".view"));
        permissions.put("ticketReports", new NamedPermissionQuery(ReportsAppService.class.getCanonicalName()
                + ".search"));
        // Menu Domains
        permissions.put("domainSection", new AlternativeNamesPermissionQuery("domains", "historicalDomains",
                "exportAuthCodes", "lockingServiceReport"));
        permissions.put("domains", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".search"));
        permissions.put("historicalDomains", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".view"));
        permissions.put("exportAuthCodes", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".bulkExportAuthCodes"));
        permissions.put("lockingServiceReport", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".searchFullWithLockingActive"));
        // Menu Secondary Market
        permissions.put("secondaryMarketSection", new AlternativeNamesPermissionQuery("buyRequests",
                "buyRequestHistory", "sellRequests", "sellRequestHistory", "saleAuthcodes"));
        permissions.put("buyRequests", new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName()
                + ".findBuyRequests"));
        permissions.put("buyRequestHistory",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName()
                        + ".findHistoricalBuyRequests"));
        permissions.put("sellRequests", new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName()
                + ".findSellRequests"));
        permissions.put("sellRequestHistory",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName()
                        + ".findHistoricalSellRequests"));
        permissions.put("saleAuthcodes", new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName()
                + ".findBuyRequests"));
        // Menu Emails
        permissions.put("emailTemplateSection", new AlternativeNamesPermissionQuery("emailTemplates", "emailGroups"));
        permissions.put("emailTemplates", new NamedPermissionQuery(EmailTemplateAppService.class.getCanonicalName()
                + ".search"));
        permissions.put("emailGroups", new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName()
                + ".search"));
        // Menu Nic Handles
        permissions.put("nicHandleSection", new AlternativeNamesPermissionQuery("nicHandles", "createNicHandle"));
        permissions.put("nicHandles",
                new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".search"));
        permissions.put("createNicHandle", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName()
                + ".createNicHandle"));
        // Menu Documents
        permissions.put("documentSection", new AlternativeNamesPermissionQuery("searchDocuments", "newDocuments"));
        permissions.put("searchDocuments", new NamedPermissionQuery(DocumentAppService.class.getCanonicalName()
                + ".findDocuments"));
        permissions.put("newDocuments", new NamedPermissionQuery(DocumentAppService.class.getCanonicalName()
                + ".getNewDocuments"));
        // Menu Accounts
        permissions.put("accountSection", new AlternativeNamesPermissionQuery("searchAccounts", "createAccounts"));
        permissions.put("searchAccounts", new NamedPermissionQuery(AccountAppService.class.getCanonicalName()
                + ".search"));
        permissions.put("createAccounts", new NamedPermissionQuery(AccountAppService.class.getCanonicalName()
                + ".create"));
        // Menu DSM
        permissions.put("DSMSection", new AlternativeNamesPermissionQuery("forceEvent", "forceState"));
        permissions.put("forceEvent", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".forceDSMEvent"));
        permissions.put("forceState", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".forceDSMState"));
        // Menu Vat
        permissions.put("vatSection", new AlternativeNamesPermissionQuery("viewVat", "createVat"));
        permissions.put("viewVat", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".getValid"));
        permissions.put("createVat", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".addVatRate"));
        // Menu Task
        permissions.put("taskSection", new AlternativeNamesPermissionQuery("taskView", "taskCreate", "jobView",
                "jobHist"));
        permissions
                .put("taskView", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".getJobConfigs"));
        permissions.put("taskCreate",
                new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".addJobConfig"));
        permissions.put("jobView", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".findJobs"));
        permissions.put("jobHist",
                new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".findJobsHistory"));
        // Menu Pricing
        permissions.put("pricingSection", new AlternativeNamesPermissionQuery("viewPrice", "createPrice"));
        permissions.put("viewPrice", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".findAllPrices"));
        permissions.put("createPrice", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".addPrice"));
        // Menu CRS Configuration
        permissions.put("crsConfigurationSection", new AlternativeNamesPermissionQuery("crsConfiguration"));
        permissions.put("crsConfiguration", new NamedPermissionQuery(ConfigAppService.class.getCanonicalName()
                + ".getEntries"));
        // Menu Bulk Transfer
        permissions.put("bulkTransferSection", new AlternativeNamesPermissionQuery("viewBulkTransfer",
                "createBulkTransfer"));
        permissions.put("viewBulkTransfer", new NamedPermissionQuery(BulkTransferAppService.class.getCanonicalName()
                + ".createBulkTransferProcess"));
        permissions.put("createBulkTransfer", new NamedPermissionQuery(BulkTransferAppService.class.getCanonicalName()
                + ".findTransfers"));
        // Menu Reports
        permissions.put("reportSection", new AlternativeNamesPermissionQuery("depositReport", "doaReport",
                "invoiceReport", "extendedReservationReport", "nrpReport", "charityReport", "totalDomainsReport",
                "totalDomainsPerDateReport", "perClassReport", "deletedDomainsReport", "autorenewReport",
                "prolongReport"));
        permissions.put("depositReport", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".findDeposits"));
        permissions.put("doaReport", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".findDepositWithHistory"));
        permissions.put("invoiceReport", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".findInvoices"));
        permissions.put("extendedReservationReport", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".findExtendedReservations"));
        permissions.put("nrpReport",
                new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".searchFull"));
        permissions.put("charityReport", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".searchFull"));
        permissions.put("totalDomainsReport", new NamedPermissionQuery(ReportsAppService.class.getCanonicalName()
                + ".findTotalDomains"));
        permissions.put("totalDomainsPerDateReport",
                new NamedPermissionQuery(ReportsAppService.class.getCanonicalName() + ".findTotalDomainsPerDate"));
        permissions.put("perClassReport", new NamedPermissionQuery(ReportsAppService.class.getCanonicalName()
                + ".findTotalDomainsPerClass"));
        permissions.put("deletedDomainsReport", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".findDeletedDomains"));
        permissions.put("autorenewReport", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".findDomainAutorenewals"));
        permissions.put("prolongReport", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".searchFull"));

        // ticket buttons
        permissions.put("ticketrevise.edit.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName()
                + ".save"));
        permissions.put("ticketrevise.alterstatus.button",
                new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".alterStatus"));
        permissions.put("ticketrevise.save.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName()
                + ".save"));
        permissions.put("ticketrevise.accept.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".accept"),
                        triplePassPermission)));
        permissions.put(
                "ticketrevise.reject.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".reject"),
                        triplePassPermission)));
        permissions.put("ticket.checkIn", new NamedPermissionQuery(TicketAppService.class.getCanonicalName()
                + ".checkIn"));
        permissions.put("ticket.checkOut", new NamedPermissionQuery(TicketAppService.class.getCanonicalName()
                + ".checkOut"));
        permissions.put("ticket.reassign", new NamedPermissionQuery(TicketAppService.class.getCanonicalName()
                + ".reassign"));
        permissions.put("ticket.altesStatus", new NamedPermissionQuery(TicketAppService.class.getCanonicalName()
                + ".alterStatus"));
        permissions.put("ticket.revise",
                new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".revise"));
        permissions.put("ticket.view", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".view"));

        // domain buttons
        permissions.put("domain.edit.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".edit"));
        permissions.put("domain.lock.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".lock"));
        permissions.put("domain.unlock.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".unlock"));
        permissions.put("domain.changeHolderType.button",
                new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".updateHolderType"));
        permissions.put("domain.sendAuthCode.button",
                new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".sendAuthCodeByEmail"));
        permissions.put("domain.sendWhois.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName()
                + ".sendWhoisDataEmail"));
        permissions.put("domain.alterstatus.button", new AlternativeNamesPermissionQuery("domain.alterstatus.active",
                "domain.alterstatus.deleted", "domain.alterstatus.reactivate"));
        permissions.put("domain.alterstatus.active", triplePassPermission);
        permissions.put("domain.alterstatus.deleted",
                new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".enterVoluntaryNRP"));
        permissions.put("domain.alterstatus.reactivate",
                new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".removeFromVoluntaryNRP"));

        // secondary market buttons
        permissions.put("buyRequest.checkin",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".checkinBuyRequest"));
        permissions.put("buyRequest.checkout",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".checkoutBuyRequest"));
        permissions.put("buyRequest.reassign",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".reassignBuyRequest"));
        permissions.put("saleAuthcode.resend",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".regenerateAndResendAuthCode"));
        permissions.put("saleAuthcode.invalidate",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".invalidateBuyRequest"));
        permissions.put("buyRequest.view",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".getBuyRequest"));
        permissions.put("buyRequest.accept",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".acceptBuyRequest"));
        permissions.put("buyRequest.reject",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".rejectBuyRequest"));
        permissions.put("buyRequest.save",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".saveBuyRequest"));
        permissions.put("buyRequest.revise",
                new AlternativeNamesPermissionQuery("buyRequest.accept", "buyRequest.reject", "buyRequest.save"));
        permissions.put("buyRequest.edit",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".modifyBuyRequestAsHostmaster"));
        permissions.put("sellRequest.cancel",
                new NamedPermissionQuery(SecondaryMarketAppService.class.getCanonicalName() + ".cancelSellRequest"));

        // nichandle buttons
        permissions.put("nichandle.edit.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".get"),
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".modifyNicHandle"))));
        permissions.put("nichandle.alterstatus.button",
                new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".alterStatus"));
        permissions.put("nichandle.create.button",
                new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".createNicHandle"));
        permissions.put("nichandle.accesslevel.button",
                new NamedPermissionQuery(UserAppService.class.getCanonicalName() + ".getUser"));
        permissions.put("nichandle.accesslevel.editGroups.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(UserAppService.class.getCanonicalName() + ".changePermissionGroups"),
                        new NamedPermissionQuery(UserAppService.class.getCanonicalName() + ".getUser"))));
        permissions.put("nichandle.accesslevel.editPerms.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".addUserPermission"),
                        new NamedPermissionQuery(UserAppService.class.getCanonicalName() + ".getUser"))));
        permissions.put("nichandle.resetpassword.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".get"),
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".saveNewPassword"),
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".resetPassword"))));
        permissions.put("nichandle.tfa.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".changeTfa"),
                        new NamedPermissionQuery(UserAppService.class.getCanonicalName() + ".getUser"))));

        // account buttons
        permissions.put("account.alterstatus.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".get"),
                        new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".alterStatus"))));
        permissions.put("account.edit.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".get"),
                        new AccountSavePermissionQuery(AccountAppService.class.getCanonicalName() + ".save"))));
        permissions.put("account.edit.editFlags",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".get"),
                        new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".save"))));
        permissions.put("account.deposit.button", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".viewDeposit"));
        permissions.put("account.deposit.correct.button",
                new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".correctDeposit"));
        permissions.put("account.deposit.topup.button",
                new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".depositFundsOffline"));

        // emails
        permissions.put("emailtemplate.edit.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(EmailTemplateAppService.class.getCanonicalName() + ".get"),
                        new NamedPermissionQuery(EmailTemplateAppService.class.getCanonicalName()
                                + ".saveEditableFields"))));
        permissions.put("emailgroup.edit.button",
                new ConjunctionPermissionQuery(Arrays.asList(
                        new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".get"),
                        new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".update"))));
        permissions.put("emailgroup.new.button", new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName()
                + ".create"));
        permissions.put("emailgroup.delete.button",
                new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".delete"));

        // tasks
        permissions.put("task.update", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName()
                + ".modifyJobConfig"));
        permissions.put("task.delete", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName()
                + ".removeJobConfig"));

        // vat
        permissions.put("vat.invalidate", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName()
                + ".invalidate"));
    }

    private AuthorizationService authorizationService;

    public PermissionAppService(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");

        this.authorizationService = authorizationService;
    }

    public boolean verifyUserPermission(AuthenticatedUser user, String permissionName) {
        PermissionQuery query = permissions.get(permissionName);
        if (query == null) {
            log.warn("Unmapped permission name " + permissionName);
            return false;
        }
        return verifyUserPermission(user, query);
    }

    public boolean verifyUserPermission(AuthenticatedUser user, PermissionQuery query) {
        if (query instanceof CombinedPermissionQuery) {
            return ((CombinedPermissionQuery) query).isMetFor(this, user);
        }
        return hasPermission(user, (PermissionQuery) query);
    }

    private boolean hasPermission(AuthenticatedUser user, PermissionQuery query) {

        try {
            authorizationService.authorize(user, query);
        } catch (PermissionDeniedException e) {
            return false;
        } catch (Exception e) {
            log.warn("Exception while checking permission " + query);
            return false;
        }
        return true;
    }

}
