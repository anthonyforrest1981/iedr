package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface SecondaryMarketTestMethods {

    void canDomainBePurchased() throws AccessDeniedException;

    void canDomainBeSold() throws AccessDeniedException;

    void canNotOwnDomainBeSold() throws AccessDeniedException;

    void findBuyRequests() throws AccessDeniedException;

    void findOwnBuyRequests() throws AccessDeniedException;

    void findSellRequests() throws AccessDeniedException;

    void findOwnSellRequests() throws AccessDeniedException;

    void findHistoricalBuyRequests() throws AccessDeniedException;

    void findHistoricalSellRequests() throws AccessDeniedException;

    void getBuyRequest() throws AccessDeniedException;

    void getNotOwnBuyRequest() throws AccessDeniedException;

    void getSellRequest() throws AccessDeniedException;

    void getNotOwnSellRequest() throws AccessDeniedException;

    void getBuyRequestHistory() throws AccessDeniedException;

    void getHistoricalBuyRequest() throws AccessDeniedException;

    void getHistoricalSellRequest() throws AccessDeniedException;

    void registerBuyRequest() throws AccessDeniedException;

    void registerSellRequest() throws AccessDeniedException;

    void registerSellRequestForNotOwnDomain() throws AccessDeniedException;

    void verifySecondaryMarketAuthCode() throws AccessDeniedException;

    void regenerateAndResendAuthCode() throws AccessDeniedException;

    void verifySecondaryMarketAuthCodeForNotOwnDomain() throws AccessDeniedException;

    void modifyBuyRequest() throws AccessDeniedException;

    void modifyNotOwnBuyRequest() throws AccessDeniedException;

    void modifyBuyRequestAsHostmaster() throws AccessDeniedException;

    void acceptBuyRequest() throws AccessDeniedException;

    void cancelBuyRequest() throws AccessDeniedException;

    void saveBuyRequest() throws AccessDeniedException;

    void rejectBuyRequest() throws AccessDeniedException;

    void cancelNotOwnBuyRequest() throws AccessDeniedException;

    void isBuyRequestUsedInSale() throws AccessDeniedException;

    void invalidateBuyRequest() throws AccessDeniedException;

    void checkoutBuyRequest() throws AccessDeniedException;

    void checkinBuyRequest() throws AccessDeniedException;

    void reassignBuyRequest() throws AccessDeniedException;

    void cancelSellRequest() throws AccessDeniedException;

    void findSellRequestsToComplete() throws AccessDeniedException;

    void completeSellRequest() throws AccessDeniedException;

    void findBuyRequestNotifications() throws AccessDeniedException;

    void sendBuyRequestNotification() throws AccessDeniedException;

    void findBuyRequestsWithCompletedSales() throws AccessDeniedException;

    void findBuyRequestsWithExpiredAuthCode() throws AccessDeniedException;

    void deleteBuyRequestWithCompletedSale() throws AccessDeniedException;

    void deleteBuyRequestWithExpiredAuthCode() throws AccessDeniedException;

    void findBuyRequestsToCleanup() throws AccessDeniedException;

    void cleanupBuyRequest() throws AccessDeniedException;

}
