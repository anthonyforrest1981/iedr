package pl.nask.crs.commons.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;

public class GenericApplicationConfig implements ApplicationConfig {

    private GenericConfig baseConfig;

    public GenericApplicationConfig(GenericConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    @Override
    public Long getIedrAccountId() {
        return getLong("iedr_account_id");
    }

    @Override
    public Long getGuestAccountId() {
        return getLong("guest_account_id");
    }

    @Override
    public NRPConfig getNRPConfig() {
        return new NRPConfig(baseConfig);
    }

    @Override
    public int getEligibleForDeletionDomainState() {
        return getInt("deletion_dsm_state");
    }

    @Override
    public ExportConfiguration getAccountUpdateExportConfig() {
        return new ExportConfiguration(getString("account_update_xml_output_dir"),
                getBool("account_update_xml_output_use_date_subdir"));
    }

    @Override
    public ExportConfiguration getDoaExportConfiguration() {
        return new ExportConfiguration(getString("doa_xml_output_dir"), getBool("doa_xml_output_use_date_subdir"));
    }

    @Override
    public List<Integer> getRenewalNotificationPeriods() {
        String periodString = getString("renewal_notification_periods");
        return fromString(periodString);
    }

    @Override
    public List<Integer> getSuspensionNotificationPeriods() {
        String periodString = getString("suspension_notification_periods");
        return fromString(periodString);
    }

    @Override
    public int getTicketExpirationPeriod() {
        return getInt("ticket_expiration_period");
    }

    @Override
    public List<Integer> getTicketExpirationNotificationPeriods() {
        String periodString = getString("ticket_expiration_notification_periods");
        return fromString(periodString);
    }

    @Override
    public int getReservationExpirationPeriod() {
        int ticket_expiration_period = getTicketExpirationPeriod();
        int reservation_expiration_period = getInt("reservation_expiration_period");
        if (ticket_expiration_period > reservation_expiration_period) {
            Logger.getLogger(GenericApplicationConfig.class).fatal(
                    "ticket expiration period is longer than reservation expiration period, using longer one");
            return ticket_expiration_period;
        }
        return reservation_expiration_period;
    }

    @Override
    public ExportConfiguration getXmlInvoiceExportConfig() {
        return new InvoiceExportConfiguration(getString("xml_invoice_output_dir"),
                getString("xml_invoice_archive_dir"), getBool("xml_invoice_output_use_date_subdir"));
    }

    @Override
    public ExportConfiguration getPdfInvoiceExportConfig() {
        return new InvoiceExportConfiguration(getString("pdf_invoice_output_dir"),
                getString("pdf_invoice_archive_dir"), getBool("pdf_invoice_output_use_date_subdir"));
    }

    @Override
    public ExportConfiguration getABMXmlInvoiceExportConfig() {
        return new ExportConfiguration(getString("abm_xml_invoice_output_dir"),
                getBool("abm_xml_invoice_output_use_date_subdir"));
    }

    @Override
    public int getPasswordExpiryPeriod() {
        return getInt("password_expiry_period");
    }

    @Override
    public int getUserSessionTimeout() {
        return getInt("user_session_timeout_minutes");
    }

    @Override
    public LoginLockoutConfig getLoginLockoutConfig() {
        return new LoginLockoutConfig(baseConfig);
    }

    @Override
    public int getPasswordResetTokenExpiry() {
        return getInt("password_reset_token_expiry_period");
    }

    @Override
    public int getPasswordResetTokenAttempts() {
        return getInt("password_reset_token_attempts");
    }

    @Override
    public GAConfig getGoogleAuthenticationConfig() {
        return new GAConfig(baseConfig);
    }

    @Override
    public IncomingDocsConfig getIncomingDocsConfig() {
        IncomingDocsConfig cfg = new IncomingDocsConfig();
        String[] mappingKeys = new String[] {"FAX_NEW", "FAX_ASSIGNED", "ATTACHMENT_NEW", "ATTACHMENT_ASSIGNED",
                "PAPER_NEW", "PAPER_ASSIGNED"};

        for (String key : mappingKeys) {
            String configKey = "incoming_docs_path_" + key.toLowerCase();
            if (baseConfig.getEntry(configKey) != null) {
                cfg.addMapping(key, getString(configKey));
            }
        }

        return cfg;
    }

    @Override
    public BigDecimal getDepositMinLimit() {
        return MoneyUtils.getRoundedAndScaledValue(getInt("deposit_min_limit"));
    }

    @Override
    public BigDecimal getDepositMaxLimit() {
        return MoneyUtils.getRoundedAndScaledValue(getInt("deposit_max_limit"));
    }

    @Override
    public int getNameserverMinCount() {
        return getInt("nameserver_min_count");
    }

    @Override
    public int getNameserverMaxCount() {
        return getInt("nameserver_max_count");
    }

    @Override
    public int getAuthCodeExpirationPeriod() {
        return getInt("authcode_expiration_period");
    }

    @Override
    public int getAuthCodeFailureLimit() {
        return getInt("authcode_failure_limit");
    }

    @Override
    public int getAuthCodePortalLimit() {
        return getInt("authcode_portal_limit");
    }

    @Override
    public int getDocumentUploadSizeLimit() {
        return getInt("document_size_limit_in_mb") * 1024 * 1024;
    }

    @Override
    public int getDocumentUploadCountLimit() {
        return getInt("document_count_limit");
    }

    @Override
    public int getEmailAttemptLimit() {
        return getInt("email_attempt_limit");
    }

    @Override
    public List<String> getDocumentAllowedTypes() {
        String allowedTypes = getString("document_allowed_types");
        List<String> result = new ArrayList<String>();
        if (allowedTypes != null && !allowedTypes.isEmpty()) {
            String[] types = allowedTypes.split(",");
            for (int i = 0; i < types.length; i++) {
                result.add(types[i].toLowerCase());
            }
        }
        return result;
    }

    @Override
    public boolean isOneOrTwoLetterDomainAllowed() {
        return getBool("allow_one_or_two_letter_domains");
    }

    @Override
    public boolean isIDNDomainAllowed() {
        return getBool("allow_idn_domains");
    }

    @Override
    public String getPortalUserNicHandle() {
        return getString("portal_user_nic_handle");
    }

    @Override
    public int getResetPasswordCleanUpPeriod() {
        return getInt("reset_password_cleanup_period");
    }

    @Override
    public int getLoginAttemptCleanUpPeriod() {
        return getInt("login_attempt_cleanup_period");
    }

    @Override
    public int getSecondaryMarketCountdownPeriod() {
        return getInt("secondary_market_countdown_period");
    }

    @Override
    public int getSecondaryMarketAuthcodeExpirationPeriod() {
        return getInt("secondary_market_authcode_expiration_period");
    }

    @Override
    public int getSecondaryMarketAfterSalePeriod() {
        return getInt("secondary_market_after_sale_period");
    }

    @Override
    public List<Integer> getSecondaryMarketAuthcodeExpirationNotificationPeriods() {
        String periodString = getString("secondary_market_authcode_expiration_notification_periods");
        return fromString(periodString);
    }

    /*
     * convenience methods
     */

    private int getInt(String configKey) {
        return (Integer) baseConfig.getEntryOrThrowNpe(configKey).getTypedValue();
    }

    private long getLong(String configKey) {
        return (Long) baseConfig.getEntryOrThrowNpe(configKey).getTypedValue();
    }

    private String getString(String configKey) {
        return baseConfig.getEntryOrThrowNpe(configKey).getValue();
    }

    private boolean getBool(String configKey) {
        return (Boolean) baseConfig.getEntryOrThrowNpe(configKey).getTypedValue();
    }

    private List<Integer> fromString(String periodsAsString) {
        List<Integer> periods = new ArrayList<Integer>();
        for (String period : periodsAsString.split(",")) {
            periods.add(Integer.parseInt(period));
        }
        return periods;
    }
}
