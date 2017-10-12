package pl.nask.crs.commons.email.service;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public enum EmailTemplateNamesEnum {
    TOP_UP(1),
    INIT_RENEWAL_ADP(2),
    INIT_RENEWAL_CC(3),
    CC_NREG_PRE_AUTH(5),
    NREG_ADP_MONEY_RESEVED(7),
    CC_XFER_PRE_AUTH_REGISTRAR(8),
    CC_XFER_PRE_AUTH_DIRECT(9),
    TRANSFER_COMPLETED_ADP(10),
    RENEWAL_DATE_PASSES_REGISTRAR(11),
    RENEWAL_DATE_PASSES_DIRECT(12),
    ENTER_VNRP_REGISTRAR(13),
    ENTER_VNRP_DIRECT(14),
    REMOVE_FROM_VNRP(15),
    RENEWAL_DATE_PASSES_VNRP(16),
    TRANSFER_REQUEST_NRP(17),
    TRANSFER_CANCELLATION_INRP(18),
    TRANSFER_CANCELLATION_VNRP(19),
    INVOICE_SUMMARY_ADP(24),
    INVOICE_FAILURE_CC(25),
    RENEWAL_NOTIFICATION(26),
    QUERY_MODIFICATION(28),
    TRANSFER_REQUEST(33),
    XFER_TICKET_EXPIRATION(35),
    ACCEPT_TRANSFER(36),
    XFER_DNS_CHECK_SUCCESS(38),
    TRANSFER_COMPLETED(39),
    NREG_APPLICATION(40),
    MOD_TICKET_EXPIRATION(41),
    REG_TICKET_EXPIRATION(42),
    ACCEPT_REGISTRATION(43),
    NREG_DNS_CHECK_SUCCESS(45),
    DOMAIN_CREATED(46),
    ADD_DOC(47),
    RESET_PASSWORD(49),
    DEPOSIT_CORRECTION(50),
    CREATE_NIC_HANDLE(51),
    FORCE_DSM_EVENT(56),
    INVALIDATED_TRANSACTION(57),
    VAT_TABLE_UPDATE(58),
    INSUFFICIENT_DEPOSIT_FUNDS_NREG(59),
    INVOICE_FAILURE_ADP(60),
    INVOICE_SUMMARY_CC(61),
    RENEWAL_DATE_PASSES_LARGE_DIRECT(63),
    ENTER_VNRP_LARGE_DIRECT(64),
    ADP_PAYMENT_INITIATED_INRP(65),
    ADP_PAYMENT_INITIATED_VNRP(66),
    CC_PAYMENT_INITIATED_INRP(67),
    CC_PAYMENT_INITIATED_VNRP(68),
    NREG_CC_PREAUTH_RELEASED(71),
    XFER_CC_PREAUTH_RELEASED(72),
    INSUFFICIENT_DEPOSIT_FUNDS_XFER(73),
    DOMAIN_EXPIRED(74),
    PRICING_TABLE_UPDATE(75),
    VAT_CHANGE(79),
    CREATE_ACCOUNT(82),
    TRANSFER_REQUEST_EXPIRED(83),
    BULK_TRANSFER_COMPLETED(84),
    MOD_REQUEST_EXPIRED(85),
    REG_REQUEST_EXPIRED(86),
    SET_CHARITY(91),
    LOCK_DOMAIN(92),
    SEND_INVOICE(101),
    DNS_NOTIFICATION(102),
    QUERY_REGISTRATION(103),
    TICKET_CLEANUP(104),
    CHANGE_PASSWORD(105),

    TAC_NIC_HANDLE_DETAILS_AMENDED_DIRECT(121),
    TAC_NIC_HANDLE_DETAILS_AMENDED_REGISTRAR(120),

    QUERY_MODIFICATION_A(129),
    ACCEPT_MODIFICATION_DIRECT_B(130),
    ACCEPT_MODIFICATION_REG_B(131),
    ACCEPT_MODIFICATION_DIRECT_A(132),
    ACCEPT_MODIFICATION_REG_A(133),

    TAC_DNS_MOD_DIRECT_BILL(145),
    TAC_DNS_MOD_DIRECT_AT(146),
    TAC_DNS_MOD_REG_BILL(141),
    TAC_DNS_MOD_REG_AT(142),

    DNS_CHECK_ERROR(150),

    ACCOUNT_DATA_UPDATE(151),
    SCHEDULER_JOB_FAILED(160),
    SCHEDULER_JOB_FINISHED_WITH_ERRORS(161),

    EMAIL_DISABLER_CONFIRMATION(170),
    EMAIL_TEMPLATE_CHANGE_NOTIFICATION(171),
    AUTHCODE_ON_DEMAND(174),
    AUTHCODE_VIOLATION(175),
    AUTHCODE_BULK_EXPORT(176),
    AUTHCODE_FROM_PORTAL(177),

    DOCUMENT_UPLOAD_SUCCESSFUL(180),
    DOCUMENT_UPLOAD_FAILURE(181),
    DOCUMENT_MAIL_PARSE_FAILURE(182),

    BULK_TRANSFER_FOR_DOMAIN(184),
    EMAIL_SENDING_FAILURE(185),
    QUERY_TRANSFER(186),

    UNLOCK_DOMAIN(189),
    LOCKING_SERVICE_ENABLED(190),
    LOCKING_SERVICE_DISABLED(191),
    LOCKING_SERVICE_PROLONGED_CONFIRMATION(192),
    WHOIS_DATA(193),

    FIRST_BUY_REQUEST_REGISTERED(194),
    LAST_BUY_REQUEST_CANCELLED(195),
    SELL_REQUEST_REGISTERED(196),
    SELL_REQUEST_CANCELLED(197),
    BUY_REQUEST_CC_PAYMENT(198),
    BUY_REQUEST_ADP_PAYMENT(199),
    BUY_REQUEST_REGISTERED(200),
    SELL_REQUEST_CC_PAYMENT(201),
    SELL_REQUEST_ADP_PAYMENT(202),
    BUY_REQUEST_ACCEPTED(203),
    BUY_REQUEST_REMOVED(204),
    BUY_REQUEST_INVALIDATED(205),
    BUY_REQUEST_EXPIRATION(206),
    BUY_REQUEST_AUTHCODE_EXPIRATION(207),
    BUY_REQUEST_REMOVED_ANOTHER_SALE_COMPLETED(208),
    BUY_REQUEST_EXPIRED(209),
    SELL_REQUEST_COMPLETED(210),
    SECONDARY_MARKET_NEW_DIRECT_PASSWORD(211),
    BUY_REQUEST_QUERY(212),
    BUY_REQUEST_HOLD_UPDATE(213);

    private int id;

    private EmailTemplateNamesEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}