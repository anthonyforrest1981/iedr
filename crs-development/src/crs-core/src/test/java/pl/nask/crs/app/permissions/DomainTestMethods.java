package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface DomainTestMethods {

    void viewDomain() throws AccessDeniedException;

    void viewDomainNotOwn() throws AccessDeniedException;

    void viewPlainDomain() throws AccessDeniedException;

    void viewPlainDomainNotOwn() throws AccessDeniedException;

    void editDomain() throws AccessDeniedException;

    void editDomainNotOwn() throws AccessDeniedException;

    void saveDomain() throws AccessDeniedException;

    void saveDomainNotOwn() throws AccessDeniedException;

    void searchForOwnDomains() throws AccessDeniedException;

    void searchForDomainsAnotherNicHandle() throws AccessDeniedException;

    void searchForDomainsOwnAccount() throws AccessDeniedException;

    void searchForDomainsAnotherAccount() throws AccessDeniedException;

    void searchForNotOwnDomains() throws AccessDeniedException;

    void searchFullDomains() throws AccessDeniedException;

    void searchFullWithLockingActive() throws AccessDeniedException;

    void findExtendedOwnDomains() throws AccessDeniedException;

    void findExtendedDomainsOwnAccount() throws AccessDeniedException;

    void findExtendedNotOwnDomains() throws AccessDeniedException;

    void findDeletedDomains() throws AccessDeniedException;

    void findOwnDeletedDomains() throws AccessDeniedException;

    void isEventValid() throws AccessDeniedException;

    void isEventValidNotOwn() throws AccessDeniedException;

    void checkAvailability() throws AccessDeniedException;

    void forceDSMEvent() throws AccessDeniedException;

    void forceDSMState() throws AccessDeniedException;

    void getDsmStates() throws AccessDeniedException;

    void updateHolderType() throws AccessDeniedException;

    void updateHolderTypeNotOwn() throws AccessDeniedException;

    void lockDomain() throws AccessDeniedException;

    void unlockDomain() throws AccessDeniedException;

    void revertToBillable() throws AccessDeniedException;

    void revertToBillableNotOwn() throws AccessDeniedException;

    void checkPayAvailable() throws AccessDeniedException;

    void checkPayAvailableNotOwn() throws AccessDeniedException;

    void modifyRenewalMode() throws AccessDeniedException;

    void modifyRenewalModeNotOwn() throws AccessDeniedException;

    void enterVoluntaryNRP() throws AccessDeniedException;

    void removeDomainFromVoluntaryNRP() throws AccessDeniedException;

    void enterVoluntaryNRPNotOwn() throws AccessDeniedException;

    void removeNotOwnDomainFromVoluntaryNRP() throws AccessDeniedException;

    void getRelatedDomains() throws AccessDeniedException;

    void findDomainsToAuthCodeCleanup() throws AccessDeniedException;

    void findDomainsToAuthCodePortalCleanup() throws AccessDeniedException;

    void authCodeCleanup() throws AccessDeniedException;

    void authCodePortalCleanup() throws AccessDeniedException;

    void bulkExportAuthCodes() throws AccessDeniedException;

    void findDomainAutorenewals() throws AccessDeniedException;

    void findDomainCountForContact() throws AccessDeniedException;

    void findDomainsForCurrentRenewal() throws AccessDeniedException;

    void findDomainsForFutureRenewal() throws AccessDeniedException;

    void findOwnDomain() throws AccessDeniedException;

    void findOwnPlainDomain() throws AccessDeniedException;

    void findOwnExtendedDomain() throws AccessDeniedException;

    void findTransferredInDomains() throws AccessDeniedException;

    void findTransferredAwayDomains() throws AccessDeniedException;

    void getAllDomains() throws AccessDeniedException;

    void getAllDomainsNotOwn() throws AccessDeniedException;

    void getNsReports() throws AccessDeniedException;

    void isCharity() throws AccessDeniedException;

    void rollLockRenewalDates() throws AccessDeniedException;

    void runDeleteProcess() throws AccessDeniedException;

    void runDeletionDatePasses() throws AccessDeniedException;

    void runNotificationProcess() throws AccessDeniedException;

    void runRenewalDatePasses() throws AccessDeniedException;

    void runSuspensionDatePasses() throws AccessDeniedException;

    void sendAuthCodeByEmailAsHostmaster() throws AccessDeniedException;

    void sendWhoisDataEmail() throws AccessDeniedException;

    void validateDomainToModify() throws AccessDeniedException;

    void validateNotOwnDomainToModify() throws AccessDeniedException;

}
