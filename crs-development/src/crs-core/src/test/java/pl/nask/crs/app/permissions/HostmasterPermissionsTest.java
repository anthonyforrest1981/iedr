package pl.nask.crs.app.permissions;

import java.util.Arrays;

import org.testng.annotations.Test;

import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.user.Level;

public class HostmasterPermissionsTest extends AbstractPermissionTest {

    private final static String USER_NAME = "AAA906-IEDR";
    private final static Level USER_LEVEL = Level.Hostmaster;

    private final static long NOT_OWN_ACCOUNT_ID = 263L;

    private final static String NOT_OWN_INVOICE_NUMBER = "103";

    private final static long NOT_OWN_TICKET_ID = 401001;

    private final static String DOMAIN_NAME_FOR_NOT_OWN_TICKET = "1registerdomaincc.ie";

    private final static String NOT_OWN_DOMAIN_NAME = "chriswilson.ie";

    private final static String NOT_OWN_NH_NAME = "AAG061-IEDR";

    private final static long NOT_OWN_TRANSACTION_ID = 6;
    private final static long NOT_OWN_TRANSACTION_HIST_ID = 68;
    private final static String NOT_OWN_ORDER_ID = "20120621144809-D-7485777";

    private final static long NOT_OWN_BUY_REQUEST_ID = 1L;

    private final static long NOT_OWN_SELL_REQUEST_ID = 3L;


    @Override
    protected String getSystemDiscriminator() {
        return "crs";
    }

    @Override
    protected String getUserName() {
        return USER_NAME;
    }

    @Override
    protected Level getGroup() {
        return USER_LEVEL;
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void registerDomain() throws AccessDeniedException {
        requestRegistration(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void topUpDeposit() throws AccessDeniedException {
        topUpDeposit(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewXmlInvoice() throws AccessDeniedException {
        viewXmlInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewPdfInvoice() throws AccessDeniedException {
        viewPdfInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewNotOwnXmlInvoice() throws AccessDeniedException {
        viewXmlInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewNotOwnPdfInvoice() throws AccessDeniedException {
        viewPdfInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewMergedInvoices() throws AccessDeniedException {
        viewMergedInvoices(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void payForDomainRenewal() throws AccessDeniedException {
        payForDomainRenewal(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void payForNotOwnDomainRenewal() throws AccessDeniedException {
        payForDomainRenewal(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void transfer() throws AccessDeniedException {
        transferRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelTicketAsOwner() throws AccessDeniedException {
        cancelTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelNotOwnTicket() throws AccessDeniedException {
        cancelTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void enterVoluntaryNRP() throws AccessDeniedException {
        enterVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void removeDomainFromVoluntaryNRP() throws AccessDeniedException {
        removeFromVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void enterVoluntaryNRPNotOwn() throws AccessDeniedException {
        enterVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void removeNotOwnDomainFromVoluntaryNRP() throws AccessDeniedException {
        removeFromVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void getRelatedDomains() throws AccessDeniedException {
        getRelatedDomains(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyDomain() throws AccessDeniedException {
        modifyDomain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyDomainNameservers() throws AccessDeniedException {
        modifyNameservers(authenticatedUser, Arrays.asList(NOT_OWN_DOMAIN_NAME));
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnDomain() throws AccessDeniedException {
        modifyDomain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnDomainNameservers() throws AccessDeniedException {
        modifyNameservers(authenticatedUser, Arrays.asList(NOT_OWN_DOMAIN_NAME));
    }

    @Override
    @Test
    public void editTicket() throws AccessDeniedException {
        editTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void editNotOwnTicket() throws AccessDeniedException {
        editTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void viewTicket() throws AccessDeniedException {
        viewTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void viewNotOwnTicket() throws AccessDeniedException {
        viewTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void reauthoriseCCTransaction() throws AccessDeniedException {
        reauthoriseCCTransaction(authenticatedUser, NOT_OWN_TRANSACTION_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void reauthoriseNotOwnCCTransaction() throws AccessDeniedException {
        reauthoriseCCTransaction(authenticatedUser, NOT_OWN_TRANSACTION_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cleanupTicket() throws AccessDeniedException {
        cleanupTicket(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void generateOrProlongAuthCode() throws AccessDeniedException {
        generateOrProlongAuthCode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void generateOrProlongAuthCodeNotOwn() throws AccessDeniedException {
        generateOrProlongAuthCode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendAuthCodeByEmail() throws AccessDeniedException {
        sendAuthCodeByEmail(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendAuthCodeByEmailNotOwn() throws AccessDeniedException {
        sendAuthCodeByEmail(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void verifyAuthCode() throws AccessDeniedException {
        verifyAuthCode(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getOwnerTypeByName() throws AccessDeniedException {
        getOwnerTypeByName(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getOwnerTypes() throws AccessDeniedException {
        getOwnerTypes(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendNotifications() throws AccessDeniedException {
        sendNotifications(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewUserDeposit() throws AccessDeniedException {
        viewUserDeposit(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findUserHistoricalDeposits() throws AccessDeniedException {
        findUserHistoricalDeposits(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getDomainPricing() throws AccessDeniedException {
        getDomainPricing(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getRequestPrice() throws AccessDeniedException {
        getRequestPrice(authenticatedUser);
    }

    @Override
    @Test
    public void getVatRate() throws AccessDeniedException {
        getVatRate(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getPrice() throws AccessDeniedException {
        getPrice(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void addPrice() throws AccessDeniedException {
        addPrice(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyPrice() throws AccessDeniedException {
        modifyPrice(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void addVatRate() throws AccessDeniedException {
        addVatRate(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void invalidateVat() throws AccessDeniedException {
        invalidateVat(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getValidVat() throws AccessDeniedException {
        getValidVat(authenticatedUser);
    }

    @Override
    @Test
    public void getVatCategories() throws AccessDeniedException {
        getVatCategories(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTopUpHistory() throws AccessDeniedException {
        getTopUpHistory(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendEmailWithInvoices() throws AccessDeniedException {
        sendEmailWithInvoices(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendEmailWithNotOwnInvoices() throws AccessDeniedException {
        sendEmailWithInvoices(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findOwnReservations() throws AccessDeniedException {
        findOwnReservations(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDomainReservations() throws AccessDeniedException {
        findDomainReservations(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDomainReservationsNotOwn() throws AccessDeniedException {
        findDomainReservations(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNotSettledReservationsTotals() throws AccessDeniedException {
        getNotSettledReservationsTotals(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findHistoricalReservations() throws AccessDeniedException {
        findHistoricalReservations(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getSettledTransactionHistory() throws AccessDeniedException {
        getSettledTransactionHistory(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransactionToReauthorise() throws AccessDeniedException {
        getTransactionToReauthorise(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findHistoricalTransactions() throws AccessDeniedException {
        findHistoricalTransactions(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findUserInvoices() throws AccessDeniedException {
        findUserInvoices(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getInvoiceInfo() throws AccessDeniedException {
        getInvoiceInfo(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNotOwnInvoiceInfo() throws AccessDeniedException {
        getInvoiceInfo(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findAllPrices() throws AccessDeniedException {
        findAllPrices(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDeposits() throws AccessDeniedException {
        findDeposits(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDepositWithHistory() throws AccessDeniedException {
        findDepositWithHistory(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getReadyADPTransactionsReport() throws AccessDeniedException {
        getReadyADPTransactionsReport(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findInvoices() throws AccessDeniedException {
        findInvoices(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findExtendedReservations() throws AccessDeniedException {
        findExtendedReservations(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewDeposit() throws AccessDeniedException {
        viewDeposit(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void correctDeposit() throws AccessDeniedException {
        correctDeposit(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void depositFundsOffline() throws AccessDeniedException {
        depositFundsOffline(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findAllTransactions() throws AccessDeniedException {
        findAllTransactions(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTransactionsToCleanup() throws AccessDeniedException {
        findTransactionsToCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cleanupTransaction() throws AccessDeniedException {
        cleanupTransaction(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void autorenewAll() throws AccessDeniedException {
        autorenewAll(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void generateInvoice() throws AccessDeniedException {
        generateInvoice(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void invalidateTransactionIfNeeded() throws AccessDeniedException {
        invalidateTransactionIfNeeded(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void settleTransaction() throws AccessDeniedException {
        settleTransaction(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void setTransactionStartedSettlement() throws AccessDeniedException {
        setTransactionStartedSettlement(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void isTransferPossible() throws AccessDeniedException {
        isTransferPossible(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void zoneCommit() throws AccessDeniedException {
        zoneCommit(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void zonePublished() throws AccessDeniedException {
        zonePublished(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void zoneUnpublished() throws AccessDeniedException {
        zoneUnpublished(authenticatedUser);
    }

    @Override
    @Test
    public void createBulkTransferProcess() throws AccessDeniedException {
        createBulkTransferProcess(authenticatedUser);
    }

    @Override
    @Test
    public void addDomains() throws AccessDeniedException {
        addDomains(authenticatedUser);
    }

    @Override
    @Test
    public void findTransfers() throws AccessDeniedException {
        findTransfers(authenticatedUser);
    }

    @Override
    @Test
    public void getTransferRequest() throws AccessDeniedException {
        getTransferRequest(authenticatedUser);
    }

    @Override
    @Test
    public void removeDomain() throws AccessDeniedException {
        removeDomain(authenticatedUser);
    }

    @Override
    @Test
    public void closeTransferRequest() throws AccessDeniedException {
        closeTransferRequest(authenticatedUser);
    }

    @Override
    @Test
    public void forceCloseTransferRequest() throws AccessDeniedException {
        forceCloseTransferRequest(authenticatedUser);
    }

    @Override
    @Test
    public void transferAll() throws AccessDeniedException {
        transferAll(authenticatedUser);
    }

    @Override
    @Test
    public void transferValid() throws AccessDeniedException {
        transferValid(authenticatedUser);
    }

    @Override
    @Test
    public void viewDomain() throws AccessDeniedException {
        view(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void viewDomainNotOwn() throws AccessDeniedException {
        view(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewPlainDomain() throws AccessDeniedException {
        viewPlain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewPlainDomainNotOwn() throws AccessDeniedException {
        viewPlain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void editDomain() throws AccessDeniedException {
        edit(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void editDomainNotOwn() throws AccessDeniedException {
        edit(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void saveDomain() throws AccessDeniedException {
        save(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void saveDomainNotOwn() throws AccessDeniedException {
        save(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void searchForOwnDomains() throws AccessDeniedException {
        searchForDomainsByNicHandle(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void searchForDomainsAnotherNicHandle() throws AccessDeniedException {
        searchForDomainsByNicHandle(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void searchForDomainsOwnAccount() throws AccessDeniedException {
        searchForDomainsByAccountId(authenticatedUser, NOT_OWN_ACCOUNT_ID);
    }

    @Override
    @Test
    public void searchForDomainsAnotherAccount() throws AccessDeniedException {
        searchForDomainsByAccountId(authenticatedUser, NOT_OWN_ACCOUNT_ID);
    }

    @Override
    @Test
    public void searchForNotOwnDomains() throws AccessDeniedException {
        searchForDomainsEmptyCriteria(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void searchFullDomains() throws AccessDeniedException {
        searchFull(authenticatedUser);
    }

    @Override
    @Test
    public void searchFullWithLockingActive() throws AccessDeniedException {
        searchFullWithLockingActive(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findExtendedOwnDomains() throws AccessDeniedException {
        findExtendedOwnDomains(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findExtendedDomainsOwnAccount() throws AccessDeniedException {
        findExtendedDomainsOwnAccount(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findExtendedNotOwnDomains() throws AccessDeniedException {
        findExtendedNotOwnDomains(authenticatedUser);
    }

    @Override
    @Test
    public void isEventValid() throws AccessDeniedException {
        isEventValid(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void isEventValidNotOwn() throws AccessDeniedException {
        isEventValid(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkAvailability() throws AccessDeniedException {
        checkAvailability(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void forceDSMEvent() throws AccessDeniedException {
        forceDSMEvent(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void forceDSMState() throws AccessDeniedException {
        forceDSMState(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getDsmStates() throws AccessDeniedException {
        getDsmStates(authenticatedUser);
    }

    @Override
    @Test
    public void updateHolderType() throws AccessDeniedException {
        updateHolderType(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void updateHolderTypeNotOwn() throws AccessDeniedException {
        updateHolderType(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void lockDomain() throws AccessDeniedException {
        lock(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void unlockDomain() throws AccessDeniedException {
        unlock(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void revertToBillable() throws AccessDeniedException {
        revertToBillable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void revertToBillableNotOwn() throws AccessDeniedException {
        revertToBillable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkPayAvailable() throws AccessDeniedException {
        checkPayAvailable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkPayAvailableNotOwn() throws AccessDeniedException {
        checkPayAvailable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyRenewalMode() throws AccessDeniedException {
        modifyRenewalMode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDomainsToAuthCodeCleanup() throws AccessDeniedException {
        findDomainsToAuthCodeCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDomainsToAuthCodePortalCleanup() throws AccessDeniedException {
        findDomainsToAuthCodePortalCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void authCodeCleanup() throws AccessDeniedException {
        authCodeCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void authCodePortalCleanup() throws AccessDeniedException {
        authCodePortalCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void bulkExportAuthCodes() throws AccessDeniedException {
        bulkExportAuthCodes(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDomainAutorenewals() throws AccessDeniedException {
        findDomainAutorenewals(authenticatedUser);
    }

    @Override
    @Test
    public void findDomainCountForContact() throws AccessDeniedException {
        findDomainCountForContact(authenticatedUser);
    }

    @Override
    @Test
    public void findDomainsForCurrentRenewal() throws AccessDeniedException {
        findDomainsForCurrentRenewal(authenticatedUser);
    }

    @Override
    @Test
    public void findDomainsForFutureRenewal() throws AccessDeniedException {
        findDomainsForFutureRenewal(authenticatedUser);
    }

    @Override
    @Test
    public void findOwnDomain() throws AccessDeniedException {
        findOwnDomain(authenticatedUser);
    }

    @Override
    @Test
    public void findOwnPlainDomain() throws AccessDeniedException {
        findOwnPlainDomain(authenticatedUser);
    }

    @Override
    @Test
    public void findOwnExtendedDomain() throws AccessDeniedException {
        findOwnPlainDomain(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTransferredInDomains() throws AccessDeniedException {
        findTransferredInDomains(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTransferredAwayDomains() throws AccessDeniedException {
        findTransferredAwayDomains(authenticatedUser);
    }

    @Override
    @Test
    public void getAllDomains() throws AccessDeniedException {
        getAllDomains(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void getAllDomainsNotOwn() throws AccessDeniedException {
        getAllDomains(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void getNsReports() throws AccessDeniedException {
        getNsReports(authenticatedUser);
    }

    @Override
    @Test
    public void isCharity() throws AccessDeniedException {
        isCharity(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void rollLockRenewalDates() throws AccessDeniedException {
        rollLockRenewalDates(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void runDeleteProcess() throws AccessDeniedException {
        runDeleteProcess(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void runDeletionDatePasses() throws AccessDeniedException {
        runDeletionDatePasses(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void runNotificationProcess() throws AccessDeniedException {
        runNotificationProcess(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void runRenewalDatePasses() throws AccessDeniedException {
        runRenewalDatePasses(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void runSuspensionDatePasses() throws AccessDeniedException {
        runSuspensionDatePasses(authenticatedUser);
    }

    @Override
    @Test
    public void sendAuthCodeByEmailAsHostmaster() throws AccessDeniedException {
        sendAuthCodeByEmailAsHostmaster(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendWhoisDataEmail() throws AccessDeniedException {
        sendWhoisDataEmail(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void validateDomainToModify() throws AccessDeniedException {
        validateDomainToModify(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void validateNotOwnDomainToModify() throws AccessDeniedException {
        validateDomainToModify(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyRenewalModeNotOwn() throws AccessDeniedException {
        modifyRenewalMode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTotalDomains() throws AccessDeniedException {
        findTotalDomains(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTotalDomainsPerDate() throws AccessDeniedException {
        findTotalDomainsPerDate(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTotalDomainsPerClass() throws AccessDeniedException {
        findTotalDomainsPerClass(authenticatedUser);
    }

    @Override
    @Test
    public void searchForReports() throws AccessDeniedException {
        searchForReports(authenticatedUser);
    }

    @Override
    @Test
    public void historyTicket() throws AccessDeniedException {
        historyTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void reviseTicket() throws AccessDeniedException {
        reviseTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void checkOutTicket() throws AccessDeniedException {
        checkOutTicket(authenticatedUser);
    }

    @Override
    @Test
    public void checkInTicket() throws AccessDeniedException {
        checkInTicket(authenticatedUser);
    }

    @Override
    @Test
    public void alterStatusTicket() throws AccessDeniedException {
        alterStatusTicket(authenticatedUser);
    }

    @Override
    @Test
    public void reassignTicket() throws AccessDeniedException {
        reassignTicket(authenticatedUser);
    }

    @Override
    @Test
    public void acceptTicket() throws AccessDeniedException {
        acceptTicket(authenticatedUser);
    }

    @Override
    @Test
    public void rejectTicket() throws AccessDeniedException {
        rejectTicket(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTicketsToCleanup() throws AccessDeniedException {
        findTicketsToCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTicketNotifications() throws AccessDeniedException {
        findTicketNotifications(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendTicketExpirationEmail() throws AccessDeniedException {
        sendTicketExpirationEmail(authenticatedUser);
    }

    @Override
    @Test
    public void getTicketForDomain() throws AccessDeniedException {
        getTicketForDomain(authenticatedUser, DOMAIN_NAME_FOR_NOT_OWN_TICKET);
    }

    @Override
    @Test
    public void getTicketForDomainNotOwn() throws AccessDeniedException {
        getTicketForDomain(authenticatedUser, DOMAIN_NAME_FOR_NOT_OWN_TICKET);
    }

    @Override
    @Test
    public void findOwnTickets() throws AccessDeniedException {
        findOwnTickets(authenticatedUser);
    }

    @Override
    @Test
    public void searchForTickets() throws AccessDeniedException {
        searchForTickets(authenticatedUser);
    }

    @Override
    @Test
    public void saveTicket() throws AccessDeniedException {
        saveTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void saveTicketNotOwn() throws AccessDeniedException {
        saveTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test
    public void updateAsAdminTicket() throws AccessDeniedException {
        updateAsAdminTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateAsOwnerTicket() throws AccessDeniedException {
        updateAsOwnerTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateAsOwnerNotOwnTicket() throws AccessDeniedException {
        updateAsOwnerTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAdminPassedTickets() throws AccessDeniedException {
        getAdminPassedTickets(authenticatedUser);
    }

    @Override
    @Test
    public void performFinancialCheck() throws AccessDeniedException {
        performFinancialCheck(authenticatedUser);
    }

    @Override
    @Test
    public void performTechnicalCheck() throws AccessDeniedException {
        performTechnicalCheck(authenticatedUser);
    }

    @Override
    @Test
    public void performTicketCancellation() throws AccessDeniedException {
        performTicketCancellation(authenticatedUser);
    }

    @Override
    @Test
    public void promoteModificationTicket() throws AccessDeniedException {
        promoteModificationTicket(authenticatedUser);
    }

    @Override
    @Test
    public void promoteTicketToDomain() throws AccessDeniedException {
        promoteTicketToDomain(authenticatedUser);
    }

    @Override
    @Test
    public void promoteTransferTicket() throws AccessDeniedException {
        promoteTransferTicket(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getEntries() throws AccessDeniedException {
        getEntries(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateEntry() throws AccessDeniedException {
        updateEntry(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void createEntry() throws AccessDeniedException {
        createEntry(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getEntry() throws AccessDeniedException {
        getEntry(authenticatedUser);
    }

    @Override
    @Test
    public void getAccount() throws AccessDeniedException {
        getAccount(authenticatedUser, NOT_OWN_ACCOUNT_ID);
    }

    @Override
    @Test
    public void getNotOwnAccount() throws AccessDeniedException {
        getAccount(authenticatedUser, NOT_OWN_ACCOUNT_ID);
    }

    @Override
    @Test
    public void historyAccount() throws AccessDeniedException {
        historyAccount(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() throws AccessDeniedException {
        alterStatusAccount(authenticatedUser);
    }

    @Override
    @Test
    public void saveAccount() throws AccessDeniedException {
        saveAccount(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveAccountFlagChanged() throws AccessDeniedException {
        saveAccountFlagChanged(authenticatedUser);
    }

    @Override
    @Test
    public void createAccount() throws AccessDeniedException {
        createAccount(authenticatedUser);
    }

    @Override
    @Test
    public void getNicHandle() throws AccessDeniedException {
        getNicHandle(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void getNotOwnNicHandle() throws AccessDeniedException {
        getNicHandle(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void historyNicHandle() throws AccessDeniedException {
        historyNicHandle(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void historyNotOwnNicHandle() throws AccessDeniedException {
        historyNicHandle(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void alterStatusNicHandle() throws AccessDeniedException {
        alterStatusNicHandle(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNicHandleOwnAccount() throws AccessDeniedException {
        modifyNicHandleOwnAccount(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnNicHandle() throws AccessDeniedException {
        modifyNicHandleOwnAccount(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void modifyNicHandleAsHostmaster() throws AccessDeniedException {
        modifyNicHandleAsHostmaster(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void saveNewPassword() throws AccessDeniedException {
        saveNewPassword(authenticatedUser, USER_NAME);
    }

    @Override
    @Test
    public void saveNewPasswordNotOwn() throws AccessDeniedException {
        saveNewPassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void changePassword() throws AccessDeniedException {
        changePassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void changePasswordNotOwn() throws AccessDeniedException {
        changePassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void resetPassword() throws AccessDeniedException {
        resetPassword(authenticatedUser, USER_NAME, IP);
    }

    @Override
    @Test
    public void resetPasswordNotOwn() throws AccessDeniedException {
        resetPassword(authenticatedUser, NOT_OWN_NH_NAME, IP);
    }

    @Override
    @Test
    public void createNicHandle() throws AccessDeniedException {
        createNicHandle(authenticatedUser);
    }

    @Override
    @Test
    public void createNicHandleOwnAccount() throws AccessDeniedException {
        createNicHandleOwnAccount(authenticatedUser);
    }

    @Override
    @Test
    public void getDefaults() throws AccessDeniedException {
        getDefaults(authenticatedUser);
    }

    @Override
    @Test
    public void saveDefaults() throws AccessDeniedException {
        saveDefaults(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void removeUserPermission() throws AccessDeniedException {
        removeUserPermission(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void addUserPermission() throws AccessDeniedException {
        addUserPermission(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void getUser() throws AccessDeniedException {
        getUser(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test
    public void getUserLevel() throws AccessDeniedException {
        getUserLevel(authenticatedUser);
    }

    @Override
    @Test
    public void getUserHistory() throws AccessDeniedException {
        getUserHistory(authenticatedUser);
    }

    @Override
    @Test
    public void isTfaUsed() throws AccessDeniedException {
        isTfaUsed(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void changePermissionGroups() throws AccessDeniedException {
        changePermissionGroups(authenticatedUser);
    }

    @Override
    @Test
    public void changeTfaAsUser() throws AccessDeniedException {
        changeTfaAsUser(authenticatedUser);
    }

    @Override
    @Test
    public void changeTfaAsHostmaster() throws AccessDeniedException {
        changeTfaAsHostmaster(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void canBeABillingContact() throws AccessDeniedException {
        canBeABillingContact(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void canBeABillingContactNotOwn() throws AccessDeniedException {
        canBeABillingContact(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cleanupLoginAttempts() throws AccessDeniedException {
        cleanupLoginAttempts(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cleanupResetPassword() throws AccessDeniedException {
        cleanupResetPassword(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void removeDeletedNichandles() throws AccessDeniedException {
        removeDeletedNichandles(authenticatedUser);
    }

    @Override
    @Test
    public void searchForNicHandles() throws AccessDeniedException {
        searchForNicHandles(authenticatedUser, null);
    }

    @Override
    @Test
    public void searchForNicHandlesNotOwn() throws AccessDeniedException {
        searchForNicHandles(authenticatedUser, null);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransactionInfoById() throws AccessDeniedException {
        getTransactionInfoById(authenticatedUser, NOT_OWN_TRANSACTION_HIST_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransactionInfoByIdNotOwn() throws AccessDeniedException {
        getTransactionInfoById(authenticatedUser, NOT_OWN_TRANSACTION_HIST_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransactionInfoByOrderId() throws AccessDeniedException {
        getTransactionInfoByOrderId(authenticatedUser, NOT_OWN_ORDER_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransactionInfoByOrderIdNotOwn() throws AccessDeniedException {
        getTransactionInfoByOrderId(authenticatedUser, NOT_OWN_ORDER_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDeletedDomains() throws AccessDeniedException {
        DeletedDomainSearchCriteria crit = new DeletedDomainSearchCriteria();
        crit.setBillingNH(NOT_OWN_NH_NAME);
        findDeletedDomains(authenticatedUser, crit);
    }

    @Override
    @Test
    public void findOwnDeletedDomains() throws AccessDeniedException {
        DeletedDomainSearchCriteria crit = new DeletedDomainSearchCriteria();
        findOwnDeletedDomains(authenticatedUser, crit);
    }

    @Override
    @Test
    public void addDocument() throws AccessDeniedException {
        addDocument(authenticatedUser);
    }

    @Override
    @Test
    public void deleteDocumentFile() throws AccessDeniedException {
        deleteDocumentFile(authenticatedUser);
    }

    @Override
    @Test
    public void documentFileExists() throws AccessDeniedException {
        documentFileExists(authenticatedUser);
    }

    @Override
    @Test
    public void findDocuments() throws AccessDeniedException {
        findDocuments(authenticatedUser);
    }

    @Override
    @Test
    public void getDocument() throws AccessDeniedException {
        getDocument(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getDocumentSettings() throws AccessDeniedException {
        getDocumentSettings(authenticatedUser);
    }

    @Override
    @Test
    public void getNewDocuments() throws AccessDeniedException {
        getNewDocuments(authenticatedUser);
    }

    @Override
    @Test
    public void getPath() throws AccessDeniedException {
        getPath(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void handleMailUpload() throws AccessDeniedException {
        handleMailUpload(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void handleUpload() throws AccessDeniedException {
        handleUpload(authenticatedUser);
    }

    @Override
    @Test
    public void updateDocument() throws AccessDeniedException {
        updateDocument(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void canDomainBePurchased() throws AccessDeniedException {
        canDomainBePurchased(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void canDomainBeSold() throws AccessDeniedException {
        canDomainBeSold(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void canNotOwnDomainBeSold() throws AccessDeniedException {
        canDomainBeSold(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void findBuyRequests() throws AccessDeniedException {
        findBuyRequests(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findOwnBuyRequests() throws AccessDeniedException {
        findOwnBuyRequests(authenticatedUser);
    }

    @Override
    @Test
    public void findSellRequests() throws AccessDeniedException {
        findSellRequests(authenticatedUser);
    }

    @Override
    @Test
    public void findOwnSellRequests() throws AccessDeniedException {
        findOwnSellRequests(authenticatedUser);
    }

    @Override
    @Test
    public void findHistoricalBuyRequests() throws AccessDeniedException {
        findHistoricalBuyRequests(authenticatedUser);
    }

    @Override
    @Test
    public void findHistoricalSellRequests() throws AccessDeniedException {
        findHistoricalSellRequests(authenticatedUser);
    }

    @Override
    @Test
    public void getBuyRequest() throws AccessDeniedException {
        getBuyRequest(authenticatedUser, NOT_OWN_BUY_REQUEST_ID);
    }

    @Override
    @Test
    public void getNotOwnBuyRequest() throws AccessDeniedException {
        getBuyRequest(authenticatedUser, NOT_OWN_BUY_REQUEST_ID);
    }

    @Override
    @Test
    public void getSellRequest() throws AccessDeniedException {
        getSellRequest(authenticatedUser, NOT_OWN_SELL_REQUEST_ID);
    }

    @Override
    @Test
    public void getNotOwnSellRequest() throws AccessDeniedException {
        getSellRequest(authenticatedUser, NOT_OWN_SELL_REQUEST_ID);
    }

    @Override
    @Test
    public void getBuyRequestHistory() throws AccessDeniedException {
        getBuyRequestHistory(authenticatedUser);
    }

    @Override
    @Test
    public void getHistoricalBuyRequest() throws AccessDeniedException {
        getHistoricalBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void getHistoricalSellRequest() throws AccessDeniedException {
        getHistoricalSellRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void registerBuyRequest() throws AccessDeniedException {
        registerBuyRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void registerSellRequest() throws AccessDeniedException {
        registerSellRequest(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void registerSellRequestForNotOwnDomain() throws AccessDeniedException {
        registerSellRequest(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void verifySecondaryMarketAuthCode() throws AccessDeniedException {
        verifySecondaryMarketAuthCode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test
    public void regenerateAndResendAuthCode() throws AccessDeniedException {
        regenerateAndResendAuthCode(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void verifySecondaryMarketAuthCodeForNotOwnDomain() throws AccessDeniedException {
        verifySecondaryMarketAuthCode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyBuyRequest() throws AccessDeniedException {
        modifyBuyRequest(authenticatedUser, NOT_OWN_BUY_REQUEST_ID);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnBuyRequest() throws AccessDeniedException {
        modifyBuyRequest(authenticatedUser, NOT_OWN_BUY_REQUEST_ID);
    }

    @Override
    @Test
    public void modifyBuyRequestAsHostmaster() throws AccessDeniedException {
        modifyBuyRequestAsHostmaster(authenticatedUser);
    }

    @Override
    @Test
    public void acceptBuyRequest() throws AccessDeniedException {
        acceptBuyRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelBuyRequest() throws AccessDeniedException {
        cancelBuyRequest(authenticatedUser, NOT_OWN_BUY_REQUEST_ID);
    }

    @Override
    @Test
    public void saveBuyRequest() throws AccessDeniedException {
        saveBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void rejectBuyRequest() throws AccessDeniedException {
        rejectBuyRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelNotOwnBuyRequest() throws AccessDeniedException {
        cancelBuyRequest(authenticatedUser, NOT_OWN_BUY_REQUEST_ID);
    }

    @Override
    @Test
    public void isBuyRequestUsedInSale() throws AccessDeniedException {
        isBuyRequestUsedInSale(authenticatedUser);
    }

    @Override
    @Test
    public void invalidateBuyRequest() throws AccessDeniedException {
        invalidateBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void checkoutBuyRequest() throws AccessDeniedException {
        checkoutBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void checkinBuyRequest() throws AccessDeniedException {
        checkinBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void reassignBuyRequest() throws AccessDeniedException {
        reassignBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void cancelSellRequest() throws AccessDeniedException {
        cancelSellRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findSellRequestsToComplete() throws AccessDeniedException {
        findSellRequestsToComplete(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void completeSellRequest() throws AccessDeniedException {
        completeSellRequest(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findBuyRequestNotifications() throws AccessDeniedException {
        findBuyRequestNotifications(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendBuyRequestNotification() throws AccessDeniedException {
        sendBuyRequestNotification(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findBuyRequestsWithCompletedSales() throws AccessDeniedException {
        findBuyRequestsWithCompletedSales(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findBuyRequestsWithExpiredAuthCode() throws AccessDeniedException {
        findBuyRequestsWithExpiredAuthCode(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void deleteBuyRequestWithCompletedSale() throws AccessDeniedException {
        deleteBuyRequestWithCompletedSale(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void deleteBuyRequestWithExpiredAuthCode() throws AccessDeniedException {
        deleteBuyRequestWithExpiredAuthCode(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void findBuyRequestsToCleanup() throws AccessDeniedException {
        findBuyRequestsToCleanup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void cleanupBuyRequest() throws AccessDeniedException {
        cleanupBuyRequest(authenticatedUser);
    }

    @Override
    @Test
    public void getEmailGroup() throws AccessDeniedException {
        getEmailGroup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateEmailGroup() throws AccessDeniedException {
        updateEmailGroup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void createEmailGroup() throws AccessDeniedException {
        createEmailGroup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void deleteEmailGroup() throws AccessDeniedException {
        deleteEmailGroup(authenticatedUser);
    }

    @Override
    @Test
    public void getAllEmailGroups() throws AccessDeniedException {
        getAllEmailGroups(authenticatedUser);
    }

    @Override
    @Test
    public void searchForEmailGroups() throws AccessDeniedException {
        searchForEmailGroups(authenticatedUser);
    }

    @Override
    @Test
    public void getEmailTemplate() throws AccessDeniedException {
        getEmailTemplate(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveEditableFieldsInEmailTemplate() throws AccessDeniedException {
        saveEditableFieldsInEmailTemplate(authenticatedUser);
    }

    @Override
    @Test
    public void searchForEmailTemplates() throws AccessDeniedException {
        searchForEmailTemplates(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAllEmailDisablersFor() throws AccessDeniedException {
        getAllFor(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAllEmailDisablersOfEmailGroup() throws AccessDeniedException {
        getAllOfEmailGroup(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAllEmailDisablersOfTemplate() throws AccessDeniedException {
        getAllOfTemplate(authenticatedUser);
    }

    @Override
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyEmailDisablerSuppressionMode() throws AccessDeniedException {
        modifySuppressionMode(authenticatedUser);
    }
}
