package pl.nask.crs.commons.config;

import java.math.BigDecimal;
import java.util.List;

public interface ApplicationConfig {
    Long getIedrAccountId();

    Long getGuestAccountId();

    NRPConfig getNRPConfig();

    int getEligibleForDeletionDomainState();

    ExportConfiguration getAccountUpdateExportConfig();

    ExportConfiguration getDoaExportConfiguration();

    List<Integer> getRenewalNotificationPeriods();

    List<Integer> getSuspensionNotificationPeriods();

    /**
     * @return
     * a period in which the ticket has to be processed or will be deleted from the system
     */
    int getTicketExpirationPeriod();

    /**
     * @return
     * List of numbers of days before expiration date when a notification emails should be sent
     */
    List<Integer> getTicketExpirationNotificationPeriods();

    /**
     * @return number of days after which reservation will be considered unsettleable and removed from the system
     */
    int getReservationExpirationPeriod();

    ExportConfiguration getXmlInvoiceExportConfig();

    ExportConfiguration getPdfInvoiceExportConfig();

    ExportConfiguration getABMXmlInvoiceExportConfig();

    /**
     * @return
     * maximum number of days the user password may be used without changing.
     */
    int getPasswordExpiryPeriod();

    /**
     * @return session timeout in minutes
     */
    int getUserSessionTimeout();

    LoginLockoutConfig getLoginLockoutConfig();

    GAConfig getGoogleAuthenticationConfig();

    /**
     * @return
     * password token expiry time, in minutes
     */
    int getPasswordResetTokenExpiry();

    int getPasswordResetTokenAttempts();

    IncomingDocsConfig getIncomingDocsConfig();

    /**
     * @return Minimal amount that can be added to deposit by top-up operation
     */
    BigDecimal getDepositMinLimit();

    /**
     * @return Maximal amount that can be added to deposit by top-up operation
     */
    BigDecimal getDepositMaxLimit();

    /**
     * @return Minimal count of nameservers that must be associated with a domain or a ticket
     */
    int getNameserverMinCount();

    /**
     * @return Maximum count of nameservers that can be associated with a domain or a ticket
     */
    int getNameserverMaxCount();

    /**
     * A perion after which a generated authode is cleared
     */
    int getAuthCodeExpirationPeriod();

    /**
     * Number of trials that may be made while entering an authcode
     */
    int getAuthCodeFailureLimit();

    /**
     * Number of allowed daily generations from Portal
     */
    int getAuthCodePortalLimit();

    /**
     * @return maximum size in bytes of uploaded documents
     */
    int getDocumentUploadSizeLimit();

    /**
     * @return
     */
    int getDocumentUploadCountLimit();

    int getEmailAttemptLimit();

    List<String> getDocumentAllowedTypes();

    boolean isOneOrTwoLetterDomainAllowed();

    boolean isIDNDomainAllowed();

    String getPortalUserNicHandle();

    int getResetPasswordCleanUpPeriod();

    int getLoginAttemptCleanUpPeriod();

    int getSecondaryMarketCountdownPeriod();

    int getSecondaryMarketAuthcodeExpirationPeriod();

    int getSecondaryMarketAfterSalePeriod();

    List<Integer> getSecondaryMarketAuthcodeExpirationNotificationPeriods();

}
