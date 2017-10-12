package com.iedr.bpr.tests.utils.email;

import java.sql.SQLException;

import com.iedr.bpr.tests.formdetails.console.PaymentDetails.PaymentMethod;
import com.iedr.bpr.tests.utils.User;
import com.iedr.bpr.tests.utils.email.ExpectedEmailSummary.EmailBuilder;

import static com.iedr.bpr.tests.TestEnvironment.db;

public class EmailSummaryGenerator {

    public ExpectedEmailSummary getApplicationFailedEmail(User billC, boolean registration) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        int eId = registration ? 71 : 72;
        return new EmailBuilder(eId).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .build();
    }

    public ExpectedEmailSummary getAuthCodeBulkExport(User adminC, User billC) {
        return new EmailBuilder(176).setAdminCEmail(adminC.email).setBillCEmail(billC.email).build();
    }

    public ExpectedEmailSummary getAuthCodeOnDemandEmail(User adminC, User billC, String domainName) {
        return new EmailBuilder(174).setAdminCEmail(adminC.email).setBillCEmail(billC.email).setDomain(domainName)
                .build();
    }

    public ExpectedEmailSummary getBillingTransferAcceptedEmail(User gainingUser, String domainName)
            throws SQLException {
        return getBillingTransferAcceptedEmail(gainingUser, gainingUser, domainName);
    }

    public ExpectedEmailSummary getBillingTransferAcceptedEmail(User gainingUser, User adminC, String domainName)
            throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        return new EmailBuilder(36).setAdminCEmail(adminC.email).setGainingBillCEmail(gainingUser.email)
                .setGainingBillCName(gainingUserName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getBillingTransferCompletedEmail(User gaining, User losing, String domainName)
            throws SQLException {
        return getBillingTransferCompletedEmail(gaining, losing, gaining, gaining, domainName);
    }

    public ExpectedEmailSummary getBillingTransferCompletedEmail(User gainingUser, User losingUser, User adminC,
            User techC, String domainName) throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        String losingUserName = db().getNicHandleName(losingUser.login);
        String techCName = db().getNicHandleName(techC.login);
        String domainHolder = db().getHolderForDomain(domainName);
        return new EmailBuilder(39).setAdminCEmail(adminC.email).setLosingBillCEmail(losingUser.email)
                .setLosingBillCName(losingUserName).setGainingBillCEmail(gainingUser.email)
                .setGainingBillCName(gainingUserName).setDomain(domainName).setTechCName(techCName)
                .setDomainHolder(domainHolder).build();
    }

    public ExpectedEmailSummary getBillingTransferDnsEmail(User gaining, String domainName) throws SQLException {
        return getBillingTransferDnsEmail(gaining, gaining, domainName);
    }

    public ExpectedEmailSummary getBillingTransferDnsEmail(User gainingUser, User techC, String domainName)
            throws SQLException {
        String techCName = db().getNicHandleName(techC.login);
        return new EmailBuilder(38).setGainingBillCEmail(gainingUser.email).setTechCEmail(techC.email)
                .setTechCName(techCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getBillingTransferHoldUpEmail(User gainingBillC, User losingBillC, User adminC,
            String domainName) throws SQLException {
        String gainingBillCName = db().getNicHandleName(gainingBillC.login);
        String losingBillCName = db().getNicHandleName(losingBillC.login);
        String ticketExpirationPeriod = db().getAppConfigValue("ticket_expiration_period");
        int daysRemaining = db().getMaxExpiringTicketNotificationPeriod();
        String daysRemainingWithSuffix = String.format("%s day%s", daysRemaining, daysRemaining == 1 ? "" : "s");
        String daysPassed = String.valueOf(Integer.parseInt(ticketExpirationPeriod) - daysRemaining);
        return new EmailBuilder(35).setGainingBillCEmail(gainingBillC.email).setGainingBillCName(gainingBillCName)
                .setLosingBillCName(losingBillCName).setAdminCEmail(adminC.email).setDomain(domainName)
                .setDaysRemainingWithDaysSuffix(daysRemainingWithSuffix).setDaysPassed(daysPassed).build();
    }

    public ExpectedEmailSummary getBillingTransferInsufficientFundsEmail(User billC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(73).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getBillingTransferPaymentEmail(User gainingUser, PaymentMethod method)
            throws SQLException {
        assert method != PaymentMethod.CHARITY;
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        int eId;
        if (method == PaymentMethod.ADP) {
            eId = 10;
        } else if (gainingUser.isRegistrar) {
            eId = 8;
        } else {
            eId = 9;
        }
        return new EmailBuilder(eId).setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName)
                .build();
    }

    public ExpectedEmailSummary getBillingTransferRemarksEmail(User gainingBillC, String domainName, String reason) {
        String remark = String.format("%s\n", reason);
        return new EmailBuilder(186).setGainingBillCEmail(gainingBillC.email).setDomain(domainName).setRemark(remark)
                .build();
    }

    public ExpectedEmailSummary getBillingTransferRequestEmail(User gainingUser, User losingUser, User adminC)
            throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        return new EmailBuilder(33).setAdminCEmail(adminC.email).setLosingBillCEmail(losingUser.email)
                .setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName).build();
    }

    public ExpectedEmailSummary getBillingTransferRequestExpiredEmail(User gainingUser, User losingUser, User adminC,
            String domainName) throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        String losingUserName = db().getNicHandleName(losingUser.login);
        String adminCName = db().getNicHandleName(adminC.login);
        return new EmailBuilder(83).setAdminCEmail(adminC.email).setAdminCName(adminCName)
                .setLosingBillCEmail(losingUser.email).setLosingBillCName(losingUserName)
                .setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName).setDomain(domainName)
                .build();
    }

    public ExpectedEmailSummary getBulkTransferCompletedEmail(User gainingUser) throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        return new EmailBuilder(84).setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName)
                .build();
    }

    public ExpectedEmailSummary getBulkTransferCompletedForDomainEmail(User gainingUser, User losingUser, User adminC,
            String domainName) throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        String losingUserName = db().getNicHandleName(losingUser.login);
        String adminCName = db().getNicHandleName(adminC.login);
        String domainHolder = db().getHolderForDomain(domainName);
        return new EmailBuilder(184).setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName)
                .setAdminCEmail(adminC.email).setAdminCName(adminCName).setLosingBillCEmail(losingUser.email)
                .setLosingBillCName(losingUserName).setDomainHolder(domainHolder).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getChangeOfPricingEmail() {
        return new EmailBuilder(75).build();
    }

    public ExpectedEmailSummary getChangeOfVatRateEmail() {
        return new EmailBuilder(58).build();
    }

    public ExpectedEmailSummary getConvertDomainToCharityEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(91).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getConvertDomainToWipoEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(92).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDepositCorrectionEmail(User billC) {
        return new EmailBuilder(50).setBillCNic(billC.login).build();
    }

    public ExpectedEmailSummary getDepositTopUpEmail(User billC) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(1).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login).build();
    }

    public ExpectedEmailSummary getDnsCheckFailure(User techC, String domainName) {
        return new EmailBuilder(102).setTechCEmail(techC.email).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDnsModificationEmail(User creator, User billC, User adminC, User techC,
            boolean tacRole, String domainName) throws SQLException {
        // tacRole - creator acts as adminC/techC
        String creatorName = db().getNicHandleName(creator.login);
        String billCName = db().getNicHandleName(billC.login);
        int eId;
        if (billC.isRegistrar) {
            eId = tacRole ? 142 : 141;
        } else {
            eId = tacRole ? 146 : 145;
        }
        EmailBuilder builder = new EmailBuilder(eId).setCreatorCEmail(creator.email).setCreatorCName(creatorName)
                .setBillCEmail(billC.email).setTechCEmail(techC.email).setDomain(domainName);
        if (eId == 145 || eId == 146) {
            builder.setAdminCEmail(adminC.email);
        }
        if (eId == 142) {
            builder.setRegistrarName(billCName);
        }
        return builder.build();
    }

    public ExpectedEmailSummary getDocumentationReceivedEmail(User billC, String domainName, String source) {
        return new EmailBuilder(47).setBillCEmail(billC.email).setDomain(domainName).set("SOURCE", source).build();
    }

    public ExpectedEmailSummary getDocumentUploadReportBasicEmail(String creatorEmail) {
        return new EmailBuilder(180).setCreatorCEmail(creatorEmail).build();
    }

    public ExpectedEmailSummary getDocumentUploadReportGeneralFailureEmail(String creatorEmail) {
        return new EmailBuilder(181).setCreatorCEmail(creatorEmail).build();
    }

    public ExpectedEmailSummary getDocumentUploadReportParseFailureEmail(String creatorEmail) {
        return new EmailBuilder(182).setCreatorCEmail(creatorEmail).build();
    }

    public ExpectedEmailSummary getDomainExpiredEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(74).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDomainModificationExpiredEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String adminCName = db().getNicHandleName(adminC.login);
        return new EmailBuilder(85).setBillCEmail(billC.email).setAdminCEmail(adminC.email).setAdminCName(adminCName)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDomainModificationHoldUpEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String adminCName = db().getNicHandleName(adminC.login);
        String ticketExpirationPeriod = db().getAppConfigValue("ticket_expiration_period");
        String daysRemaining = Integer.toString(db().getMaxExpiringTicketNotificationPeriod());
        String daysPassed = String.valueOf(Integer.parseInt(ticketExpirationPeriod) - Integer.parseInt(daysRemaining));
        return new EmailBuilder(41).setBillCEmail(billC.email).setAdminCEmail(adminC.email).setAdminCName(adminCName)
                .setDomain(domainName).setDaysPassed(daysPassed).build();
    }

    public ExpectedEmailSummary getDomainModificationReceivedEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(91).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDomainModificationSuccessfulEmail(User creator, User billC, User adminC,
            String domainName) throws SQLException {
        String creatorName = db().getNicHandleName(creator.login);
        int eId;
        if (creator.login.equals(adminC.login)) {
            eId = billC.isRegistrar ? 133 : 132;
        } else {
            eId = billC.isRegistrar ? 131 : 130;
        }
        return new EmailBuilder(eId).setCreatorCEmail(creator.email).setCreatorCName(creatorName)
                .setBillCEmail(billC.email).setAdminCEmail(adminC.email).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDomainModificationRemarksEmail(User user, String domainName, String reason) {
        String remark = String.format("%s\n", reason);
        return new EmailBuilder(28).setBillCEmail(user.email).setDomain(domainName).setRemark(remark).build();
    }

    public ExpectedEmailSummary getDsmEventForcedEmail(User nh, String domainName) {
        return new EmailBuilder(56).setNic(nh.login).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getEmailDisablerSettingChangedEmail(User billC) {
        return new EmailBuilder(170).setBillCEmail(billC.email).build();
    }

    public ExpectedEmailSummary getInvoiceEmail(User billC, PaymentMethod method) throws SQLException {
        int eId;
        if (method == PaymentMethod.CARD) {
            eId = 61;
        } else {
            eId = 24;
        }
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(eId).setBillCEmail(billC.email).setBillCName(billCName).build();
    }

    public ExpectedEmailSummary getInvoiceFailureEmail(PaymentMethod method) {
        assert method == PaymentMethod.CARD || method == PaymentMethod.ADP;
        int eId = method == PaymentMethod.CARD ? 25 : 60;
        return new EmailBuilder(eId).build();
    }

    public ExpectedEmailSummary getInvoiceRequestedEmail(User billC) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(101).setBillCEmail(billC.email).setBillCName(billCName).build();
    }

    public ExpectedEmailSummary getNewRegistrarAccountEmail(User billC) {
        return new EmailBuilder(82).setBillCEmail(billC.email).setBillCNic(billC.login).build();
    }

    public ExpectedEmailSummary getNhDetailsUpdatedEmail(User user) throws SQLException {
        String userName = db().getNicHandleName(user.login);
        return new EmailBuilder(151).setNic(user.login).setNicEmail(user.email).setNicName(userName).build();
    }

    public ExpectedEmailSummary getNhDetailsUpdatedTacEmail(User billC, User creator, User registrar, User user)
            throws SQLException {
        int eId = billC.isRegistrar ? 120 : 121;
        String creatorName = db().getNicHandleName(creator.login);
        EmailBuilder builder = new EmailBuilder(eId).setBillCEmail(billC.email).setCreatorCEmail(creator.email)
                .setCreatorCName(creatorName).setNic(user.login);
        if (eId == 120) {
            String registrarName = db().getNicHandleName(registrar.login);
            builder.setRegistrarName(registrarName);
        }
        return builder.build();
    }

    public ExpectedEmailSummary getNhPasswordChangedEmail(User user) throws SQLException {
        String userName = db().getNicHandleName(user.login);
        return new EmailBuilder(105).setNic(user.login).setNicEmail(user.email).setNicName(userName).build();
    }

    public ExpectedEmailSummary getNhPasswordResetEmail(User user) throws SQLException {
        String userName = db().getNicHandleName(user.login);
        return new EmailBuilder(49).setNic(user.login).setNicEmail(user.email).setNicName(userName).build();
    }

    public ExpectedEmailSummary getNhRegistrationEmail(User user) throws SQLException {
        String userName = db().getNicHandleName(user.login);
        return new EmailBuilder(51).setNic(user.login).setNicEmail(user.email).setNicName(userName).build();
    }

    public ExpectedEmailSummary getNhVatStatusChanged(User billC) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(79).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login).build();
    }

    public ExpectedEmailSummary getNrpNotificationEmail(User billC, User adminC, String domainName, boolean voluntary)
            throws SQLException {
        boolean adminCSameAsBillC = billC.login.equals(adminC.login);
        String adminCName = db().getNicHandleName(adminC.login);
        String adminEmail = adminC.email;
        return getNrpNotificationEmail(billC, domainName, voluntary, adminCSameAsBillC, adminCName, adminEmail);
    }

    public ExpectedEmailSummary getNrpNotificationEmail(User billC, User adminC1, User adminC2, String domainName,
            boolean voluntary) throws SQLException {
        boolean adminCSameAsBillC = billC.login.equals(adminC1.login);
        String adminCName = db().getNicHandleName(adminC1.login);
        String adminEmail = adminC1.email + "," + adminC2.email;
        return getNrpNotificationEmail(billC, domainName, voluntary, adminCSameAsBillC, adminCName, adminEmail);
    }

    private ExpectedEmailSummary getNrpNotificationEmail(User billC, String domainName, boolean voluntary,
            boolean adminCSameAsBillC, String adminCName, String adminEmail) throws SQLException {
        int eId;
        if (voluntary) {
            eId = billC.isRegistrar ? 13 : (adminCSameAsBillC ? 14 : 64);
        } else {
            eId = billC.isRegistrar ? 11 : (adminCSameAsBillC ? 12 : 63);
        }
        String billCName = db().getNicHandleName(billC.login);
        String info = "info@" + domainName;
        String postmaster = "postmaster@" + domainName;
        String webmaster = "webmaster@" + domainName;
        EmailBuilder builder = new EmailBuilder(eId).setAdminCEmail(adminEmail).setAdminCName(adminCName)
                .setBillCEmail(billC.email).set("INFO", info, false).set("POSTMASTER", postmaster, false)
                .set("WEBMASTER", webmaster, false).setDomain(domainName);
        if (eId == 11 || eId == 13 || eId == 63 || eId == 64) {
            builder.setBillCName(billCName);
        }
        return builder.build();
    }

    public ExpectedEmailSummary getNrpPaidReactivationEmail(User billC, String domainName, boolean involuntary,
            PaymentMethod method) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        int eId;
        if (involuntary) {
            eId = method == PaymentMethod.ADP ? 65 : 67;
        } else {
            eId = method == PaymentMethod.ADP ? 66 : 68;
        }
        EmailBuilder builder = new EmailBuilder(eId).setBillCName(billCName).setBillCNic(billC.login)
                .setDomain(domainName);
        if (eId == 65 || eId == 66 | eId == 68) {
            builder.setBillCEmail(billC.email);
        }
        return builder.build();
    }

    public ExpectedEmailSummary getNrpVoluntaryReactivationEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(15).setAdminCEmail(adminC.email).setBillCEmail(billC.email).setBillCName(billCName)
                .setBillCNic(billC.login).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getNrpTransferRequestEmail(User gainingUser, User losingUser, User adminC)
            throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        String losingUserName = db().getNicHandleName(losingUser.login);
        String adminCName = db().getNicHandleName(adminC.login);
        return new EmailBuilder(17).setAdminCEmail(adminC.email).setAdminCName(adminCName)
                .setLosingBillCEmail(losingUser.email).setLosingBillCName(losingUserName)
                .setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName).build();
    }

    public ExpectedEmailSummary getNrpTransferRequestFailedEmail(User gainingUser, User losingUser, User adminC,
            String domainName, boolean voluntary) throws SQLException {
        String gainingUserName = db().getNicHandleName(gainingUser.login);
        String losingUserName = db().getNicHandleName(losingUser.login);
        String adminCName = db().getNicHandleName(adminC.login);
        int eId = voluntary ? 19 : 18;
        return new EmailBuilder(eId).setAdminCEmail(adminC.email).setAdminCName(adminCName)
                .setLosingBillCEmail(losingUser.email).setLosingBillCName(losingUserName)
                .setGainingBillCEmail(gainingUser.email).setGainingBillCName(gainingUserName).setDomain(domainName)
                .build();
    }

    public ExpectedEmailSummary getPendingRenewalEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(26).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationAcceptedEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(43).setAdminCEmail(adminC.email).setBillCEmail(billC.email).setBillCName(billCName)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationCompletedEmail(User billC, User adminC, User techC, String domainName)
            throws SQLException {
        String adminCName = db().getNicHandleName(adminC.login);
        return new EmailBuilder(46).setAdminCEmail(adminC.email).setAdminCName(adminCName).setBillCEmail(billC.email)
                .setTechCEmail(techC.email).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationDnsVerifiedEmail(User billC, User techC, String domain) {
        return new EmailBuilder(45).setBillCEmail(billC.email).setTechCEmail(techC.email).setDomain(domain).build();
    }

    public ExpectedEmailSummary getRegistrationExpiredEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        String adminCName = db().getNicHandleName(adminC.login);
        return new EmailBuilder(86).setBillCEmail(billC.email).setRegistrarName(billCName).setAdminCEmail(adminC.email)
                .setAdminCName(adminCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationHoldUpEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        String adminCName = db().getNicHandleName(adminC.login);
        String ticketExpirationPeriod = db().getAppConfigValue("ticket_expiration_period");
        int daysRemaining = db().getMaxExpiringTicketNotificationPeriod();
        String daysRemainingWithSuffix = String.format("%s day%s", daysRemaining, daysRemaining == 1 ? "" : "s");
        String daysPassed = String.valueOf(Integer.parseInt(ticketExpirationPeriod) - daysRemaining);
        return new EmailBuilder(42).setBillCEmail(billC.email).setRegistrarName(billCName).setAdminCEmail(adminC.email)
                .setAdminCName(adminCName).setDomain(domainName)
                .setDaysRemainingWithDaysSuffix(daysRemainingWithSuffix).setDaysPassed(daysPassed).build();
    }

    public ExpectedEmailSummary getRegistrationInsufficientFundsEmail(User billC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(59).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationPaymentEmail(User billC, PaymentMethod method, String domainName)
            throws SQLException {
        assert method == PaymentMethod.ADP || method == PaymentMethod.CARD;
        String billCName = db().getNicHandleName(billC.login);
        int eId;
        if (method == PaymentMethod.CARD) {
            eId = 5;
        } else {
            eId = 7;
        }
        EmailBuilder builder = new EmailBuilder(eId).setBillCEmail(billC.email).setBillCName(billCName)
                .setDomain(domainName);
        if (eId == 7) {
            builder.setBillCNic(billC.login);
        }
        return builder.build();
    }

    public ExpectedEmailSummary getRegistrationReceivedEmail(User billC, User adminC, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(40).setAdminCEmail(adminC.email).setBillCEmail(billC.email).setBillCName(billCName)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationReceivedEmail(User billC, User adminC1, User adminC2, String domainName)
            throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(40).setAdminCEmail(adminC1.email + "," + adminC2.email).setBillCEmail(billC.email)
                .setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRegistrationRemarksEmail(User creator, String domainName, String reason) {
        String remark = String.format("%s\n", reason);
        return new EmailBuilder(103).setCreatorCEmail(creator.email).setDomain(domainName).setRemark(remark).build();
    }

    public ExpectedEmailSummary getRenewalDatePassedEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(16).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .setDomain(domainName).build();
    }

    public ExpectedEmailSummary getRenewalsPaymentEmail(User billC, PaymentMethod method) throws SQLException {
        assert method != PaymentMethod.CHARITY;
        String billCName = db().getNicHandleName(billC.login);
        int eId;
        if (method == PaymentMethod.CARD) {
            eId = 3;
        } else {
            eId = 2;
        }
        return new EmailBuilder(eId).setBillCEmail(billC.email).setBillCName(billCName).setBillCNic(billC.login)
                .build();
    }

    public ExpectedEmailSummary getTicketRemovalEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(104).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getEmailSendingFatalErrorEmail() {
        return new EmailBuilder(185).build();
    }

    public ExpectedEmailSummary getDomainIsUnlockedEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(189).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getDomainIsLockedEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(92).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
    }

    public ExpectedEmailSummary getTransactionInvalidationEmail(User billC) throws SQLException {
        String billCCompanyName = db().getNicHandleCompanyName(billC.login);
        return new EmailBuilder(57).setBillCEmail(billC.email).setBillCNic(billC.login).setBillCCoName(billCCompanyName)
                .build();
    }

    public ExpectedEmailSummary getDomainEntersLockingEmail(User billC, String domainName) throws SQLException {
        String billCName = db().getNicHandleName(billC.login);
        return new EmailBuilder(190).setBillCEmail(billC.email).setBillCName(billCName).setDomain(domainName).build();
   }

    public ExpectedEmailSummary getLockingServiceRolled (User billC) throws SQLException {
        return new EmailBuilder(192).setNicName(billC.login).build();
   }

    public ExpectedEmailSummary domainRemovedFromLocking(User billC, String domainName) throws SQLException {
        return new EmailBuilder(191).setBillCEmail(billC.email).setDomain(domainName).build();
   }
}
