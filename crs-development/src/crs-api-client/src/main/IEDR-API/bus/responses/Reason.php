<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class Reason {

    static public $NEW_PASSWORD_EQUALS_TO_CURRENT_PASSWORD = 50;
    static public $NEW_PASSWORD_TO_EASY = 51;
    static public $INACTIVE_ACCOUNT = 52;
    static public $CVN_PRESIND_IS_MANDATORY = 53;
    static public $CVN_NUMBER_SYNTAX_ERROR = 54;
    static public $CVN_PRESIND_SYNTAX_ERROR = 55;
    static public $DOMAIN_REGISTERED = 100;
    static public $APLICATION_IN_PROGRESS = 101;
    static public $DOMAIN_NAME_MANDATORY = 150;
    static public $DOMAIN_HOLDER_MANDATORY = 151;
    static public $DUPLICATE_CONTACT_ID = 154;
    static public $DOMAIN_NAME_SYNTAX_ERROR = 155;
    static public $DOMAIN_NAME_EXISTS_OR_PENDING = 156;
    static public $PERIOD_UNIT_MONTH_NOT_ALLOWED = 157;
    static public $PERIOD_VALUE_NOT_ADMISSIBLE = 158;
    static public $TOO_FEW_DNS = 159;
    static public $TOO_MANY_DNS = 160;
    static public $DUPLICATE_DNS_NAME = 161;
    static public $DNS_SYNTAX_ERROR = 162;
    static public $IP_SYTAX_ERROR = 163;
    static public $GLUE_NOT_ALLOWED = 164;
    static public $TOO_FEW_ADMIN_CONTACTS = 165;
    static public $TOO_MANY_ADMIN_CONTACTS = 166;
    static public $TOO_FEW_TECH_CONTACTS = 167;
    static public $TOO_MANY_TECH_CONTACTS = 168;
    static public $CONTACT_ID_SYNTAX_ERROR = 169;
    static public $CONTACT_ID_DOESNT_EXIST = 170;
    static public $GLUE_IS_REQUIRED = 175;
    static public $HOLDER_REMARK_TOO_LONG = 176;
    static public $CONTACT_NOT_ACTIVE = 177;
    static public $HOLDER_NAME_TOO_LONG = 178;
    static public $HOLDER_MODIFICATION_NOT_ALLOWED = 179;
    static public $HOLDER_REMARK_MANDATORY = 180;
    static public $HOLDER_TYPE_MANDATORY  = 181;
    static public $HOLDER_TYPE_DOESNT_EXIST = 182;
    static public $CHARITY_HOLDER_TYPE_NOT_ALLOWED = 183;

    static public $DOMAIN_NAME_DOES_NOT_EXIST = 200;
    static public $DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER = 201;
    static public $DOMAIN_MODIFICATION_PENDING = 202;
    static public $DOMAIN_REGISTRATION_PENDING = 207;
    static public $DOMAIN_STATE_PREVENTS_ENTERING_VNRP = 208;
    static public $DOMAIN_STATE_PREVENTS_LEAVING_VNRP = 209;
    static public $DOMAIN_STATE_PREVENTS_MODIFICATIONS = 210;
    static public $DOMAIN_TRANSFER_PENDING = 211;
    static public $NOTHING_TO_CHANGE = 252;
    static public $DOMAIN_ALREADY_DELEGATED_TO_HOST_TO_ADD = 253;
    static public $DOMAIN_NOT_DELEGATED_TO_HOST_TO_REMOVE = 254;
    static public $HOST_NOT_CONFIGURED_FOR_DOMAIN = 255;
    static public $ADMINC_TO_ADD_ALREADY_ASSOCIATED_WITH_DOMAIN = 256;
    static public $TECHC_TO_ADD_ALREADY_ASSOCIATED_WITH_DOMAIN = 257;
    static public $ADMINC_TO_REMOVE_NOT_ASSOCIATED_WITH_DOMAIN = 258;
    static public $TECHC_TO_REMOVE_NOT_ASSOCIATED_WITH_DOMAIN = 259;
    static public $ATTRIBUTE_SUBTYPE_IS_MANDATORY_FOR_TRANSFERS_QRY = 270;
    static public $CHARITY_REGISTRATION_NOT_POSSIBLE = 274;
    static public $CHARITY_CODE_TOO_LONG = 275;
    static public $CHARITY_TRANSFER_NOT_POSSIBLE = 276;
    static public $INVALID_AUTHCODE = 280;
    static public $AUTHCODE_IS_MANDATORY = 281;
    static public $ADMIN_CONTACT_CANNOT_BE_DUPLICATED = 282;
    static public $CANNOT_GENERATE_AUTHCODE_FOR_DOMAIN = 283;
    static public $DOMAIN_ALREADY_MANAGED_BY_RESELLER = 300;
    static public $RESELLER_DEFAULTS_NOT_DEFINED = 301;
    static public $CONTACT_NAME_IS_MANDATORY_FIELD = 400;
    static public $CONTACT_ADDRESS_IS_MANDATORY_FIELD = 402;
    static public $CONTACT_COUNTRY_IS_MANDATORY_FIELD = 403;
    static public $CONTACT_VOICE_IS_MANDATORY_FIELD = 404;
    static public $CONTACT_EMAIL_IS_MANDATORY_FIELD = 405;
    static public $COUNTRY_DOES_NOT_EXIST = 406;
    static public $COUNTY_DOES_NOT_EXIST = 407;
    static public $COUNTY_DOES_NOT_MATCH_COUNTRY = 408;
    static public $COUNTY_IS_REQUIRED_FOR_THE_COUNTRY = 409;
    static public $COUNTRY_CHANGE_FORBIDDEN = 410;
    static public $CONTACT_EMAIL_IS_INVALID = 411;
    static public $CONTACT_ID_IS_MANDATORY_FIELD = 450;
    static public $CONTACT_ID_DOES_NOT_EXIST = 451;
    static public $CONTACT_IS_MANAGED_BY_ANOTHER_RESELLER = 452;
    static public $SELL_REQUEST_DOES_NOT_EXIST = 500;
    static public $SELL_REQUEST_EXISTS = 501;
    static public $BUY_REQUEST_DOES_NOT_EXIST = 502;
    static public $BUY_REQUEST_IN_USE = 503;
    static public $BUY_REQUEST_ALREADY_ACCEPTED = 504;
    static public $TICKET_NAME_IS_MANDATORY_FIELD = 700;
    static public $TICKET_NAME_DOES_NOT_EXIST = 701;
    static public $TICKET_IS_MANAGED_BY_ANOTHER_RESELLER = 702;
    static public $TICKET_IN_USE = 703;
    static public $TICKET_ALREADY_DELEGATED_TO_HOST_TO_ADD = 704;
    static public $TICKET_NOT_DELEGATED_TO_HOST_TO_REMOVE = 705;
    static public $ADMINC_TO_ADD_ALREADY_ASSOCIATED_WITH_TICKET = 706;
    static public $TECHC_TO_ADD_ALREADY_ASSOCIATED_WITH_TICKET = 707;
    static public $ADMINC_TO_REMOVE_NOT_ASSOCIATED_WITH_TICKET = 708;
    static public $TECHC_TO_REMOVE_NOT_ASSOCIATED_WITH_TICKET = 709;
    static public $ATTRIBUTE_PAGE_IS_OUT_OF_RANGE = 802;
    static public $ACCOUNT_DOMAIN_IS_MANDATORY_FIELD = 803;
    static public $ACCOUNT_CARDHOLDER_IS_MANDATORY_FIELD = 804;
    static public $ACCOUNT_CARDNUMBER_IS_MANDATORY_FIELD = 805;
    static public $ACCOUNT_EXPDATE_IS_MANDATORY_FIELD = 806;
    static public $ACCOUNT_CARDTYPE_IS_MANDATORY_FIELD = 807;
    static public $ACCOUNT_PAYMENT_SYSTEM_ERROR = 809;
    static public $ACCOUNT_DUPLICATE_DOMAIN = 811;
    static public $NOT_ENOUGHT_DEPOSIT_FUNDS = 813;
    static public $TOO_LOW_VALUE_TO_DEPOSIT = 814;
    static public $TOO_HIGH_VALUE_TO_DEPOSIT = 815;
    static public $DOMAIN_INCORRECT_STATE_FOR_REACTIVATION = 817;
    static public $ACCOUNT_CARDHOLDER_AND_FROMDEPOSIT_CANT_BE_USED_SIMULTANEOUSLY = 818;
    static public $DOMAIN_INCORRECT_STATE_FOR_OPERATION = 821;
    static public $INVALID_PAYMENT_METHOD = 822;
    static public $PAYMENT_PENDING = 823;
    static public $MSGS_LIST = array(
        50  => "New password equals to current one",
        51  => "New password is too easy",
        52  => "Account inactive. Contact Registration Services",
        53  => "Cvn presence indicator is mandatory field",
        54  => "Cvn number syntax error",
        55  => "Cvn presence indicator syntax error",

        100 => "Domain already registered",
        101 => "Application already in progress",
        150 => "Domain name is a mandatory field",
        151 => "Domain Holder is a mandatory field",
        154 => "Duplicate contact ID",
        155 => "Domain name syntax error",
        156 => "Domain name exists or pending registration",
        157 => "Period unit month is not allowed",
        158 => "Period value is not admissible",
        159 => "Too few nameservers",
        160 => "Too many nameservers",
        161 => "Duplicate nameserver name",
        162 => "Nameserver name syntax error",
        163 => "IP address syntax error",
        164 => "Glue record is not allowed",
        165 => "Too few administrative contacts",
        166 => "Too many administrative contacts",
        167 => "Too few technical contacts",
        168 => "Too many technical contacts",
        169 => "Contact ID syntax error",
        170 => "Contact ID does not exist",
        175 => "Glue record is required",
        176 => "Holder remark is too long",
        177 => "Contact is not active",
        178 => "Holder name is too long",
        179 => "Holder modification is not allowed",
        180 => "Holder remark is a mandatory field",
        181 => "Holder type is a mandatory field",
        182 => "Holder type does not exist",
        183 => "Charity holder type is not allowed",
        200 => "Domain name does not exist",
        201 => "Domain is managed by another reseller",
        202 => "Domain modification pending",
        207 => "Domain registration pending",
        208 => "Voluntary NRP not possible in current domain state",
        209 => "Leaving Voluntary NRP not possible in current domain state",
        210 => "Modify not possible in current domain state",
        211 => "Domain transfer pending",
        252 => "Nothing to change",
        253 => "Domain is already delegated to the host to add",
        254 => "Domain is not delegated to the host to remove",
        255 => "{fatal msg returned from chkdns tool}",
        256 => "Administrative contact to add is already associated with the domain",
        257 => "Technical contact to add is already associated with the domain",
        258 => "Administrative contact to remove is not associated with the domain",
        259 => "Technical contact to remove is not associated with the domain",
        270 => "Attribute subtype is mandatory for transfers queries",
        274 => "Charity registration is not possible",
        275 => "Charity code too long",
        276 => "Charity transfer is not possible",
        280 => "Invalid authcode",
        281 => "Authcode is mandatory",
        282 => "Admin contact cannot be duplicated",
        283 => "Cannot generate authcode for domain",

        300 => "Domain is already manged by reseller",
        301 => "Reseller defaults not defined",

        400 => "Contact name is mandatory field",
        402 => "Contact address is mandatory field",
        403 => "Contact country is mandatory field",
        404 => "Contact voice is mandatory field",
        405 => "Contact email is mandatory field",
        406 => "Country does not exist",
        407 => "County does not exist",
        408 => "County does not match country",
        409 => "County is required for the country",
        410 => "Country change forbidden",
        411 => "Contact email is invalid",

        450 => "Contact ID is mandatory field",
        451 => "Contact ID does not exist",
        452 => "Contact is managed by another reseller",

        500 => "Sell request does not exist",
        501 => "Sell request exists",
        502 => "BuyRequest does not exist",
        503 => "BuyRequest is currently being processed by a Hostmaster",
        504 => "BuyRequest has already been accepted and cannot be modified",

        700 => "Ticket name is mandatory field",
        701 => "Ticket name does not exist",
        702 => "Ticket is managed by another reseller",
        703 => "Ticket is currently being processed by a Hostmaster",
        704 => "Nameserver already associated with Ticket",
        705 => "Ticket is not delegated to the host being removed",
        706 => "Administrative contact being added is already associated with the ticket",
        707 => "Technical contact being added is already associated with the ticket",
        708 => "Administrative contact being removed is not associated with the ticket",
        709 => "Technical contact being removed is not associated with the ticket ",

        802 => "Attribute page is out of range",
        803 => "Account domain is a mandatory field",
        804 => "Account cardHolder is a mandatory field",
        805 => "Account cardNumber is a mandatory field",
        806 => "Account expiryDate is a mandatory field",
        807 => "Account cardType is a mandatory field",
        809 => "{code:reason returned from Realex Payment system}",
        811 => "Duplicate account domain",
        813 => "Not enought deposit funds",
        814 => "Deposit value too low",
        815 => "Deposit value too high",
        817 => "Incorrect domain state for reactivation",
        818 => "Credit card and pay from deposit cannot be used simultaneously",
        821 => "Domain illegal state for operation",
        822 => "Invalid payment method",
        823 => "Payment is pending");
    private $code;
    private $msg;

    function Reason() {
    }

    function getCode() {
        return $this->code;
    }

    function setCode($code) {
        $this->code = $code;
    }

    function getMsg() {
        return $this->msg;
    }

    function setMsg($msg) {
        $this->msg = $msg;
    }
}
