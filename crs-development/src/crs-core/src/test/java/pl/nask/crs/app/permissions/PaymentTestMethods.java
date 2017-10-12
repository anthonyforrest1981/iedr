package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface PaymentTestMethods {

    void payForDomainRenewal() throws AccessDeniedException;

    void payForNotOwnDomainRenewal() throws AccessDeniedException;

    void viewXmlInvoice() throws AccessDeniedException;

    void viewPdfInvoice() throws AccessDeniedException;

    void viewNotOwnXmlInvoice() throws AccessDeniedException;

    void viewNotOwnPdfInvoice() throws AccessDeniedException;

    void viewMergedInvoices() throws AccessDeniedException;

    void sendEmailWithInvoices() throws AccessDeniedException;

    void sendEmailWithNotOwnInvoices() throws AccessDeniedException;

    void getInvoiceInfo() throws AccessDeniedException;

    void getNotOwnInvoiceInfo() throws AccessDeniedException;

    void viewUserDeposit() throws AccessDeniedException;

    void topUpDeposit() throws AccessDeniedException;

    void getTopUpHistory() throws AccessDeniedException;

    void findUserHistoricalDeposits() throws AccessDeniedException;

    void findOwnReservations() throws AccessDeniedException;

    void findDomainReservations() throws AccessDeniedException;

    void findDomainReservationsNotOwn() throws AccessDeniedException;

    void getNotSettledReservationsTotals() throws AccessDeniedException;

    void findHistoricalReservations() throws AccessDeniedException;

    void getSettledTransactionHistory() throws AccessDeniedException;

    void getTransactionToReauthorise() throws AccessDeniedException;

    void findHistoricalTransactions() throws AccessDeniedException;

    void findUserInvoices() throws AccessDeniedException;

    void getDomainPricing() throws AccessDeniedException;

    void getRequestPrice() throws AccessDeniedException;

    void getVatRate() throws AccessDeniedException;

    void getPrice() throws AccessDeniedException;

    void findAllPrices() throws AccessDeniedException;

    void addPrice() throws AccessDeniedException;

    void modifyPrice() throws AccessDeniedException;

    void addVatRate() throws AccessDeniedException;

    void invalidateVat() throws AccessDeniedException;

    void getValidVat() throws AccessDeniedException;

    void getVatCategories() throws AccessDeniedException;

    //reports
    void findDeposits() throws AccessDeniedException;

    void findDepositWithHistory() throws AccessDeniedException;

    void getReadyADPTransactionsReport() throws AccessDeniedException;

    void findInvoices() throws AccessDeniedException;

    void findExtendedReservations() throws AccessDeniedException;

    //manage deposits
    void viewDeposit() throws AccessDeniedException;

    void correctDeposit() throws AccessDeniedException;

    void depositFundsOffline() throws AccessDeniedException;

    void getTransactionInfoById() throws AccessDeniedException;

    void getTransactionInfoByIdNotOwn() throws AccessDeniedException;

    void getTransactionInfoByOrderId() throws AccessDeniedException;

    void getTransactionInfoByOrderIdNotOwn() throws AccessDeniedException;

    void findAllTransactions() throws AccessDeniedException;

    void findTransactionsToCleanup() throws AccessDeniedException;

    void cleanupTransaction() throws AccessDeniedException;

    void autorenewAll() throws AccessDeniedException;

}
