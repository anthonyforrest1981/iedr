<?php

/**
 * defines menu data and related functions
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */
/**
 * Get menu structure (nested arrays)
 *
 * passes runtime-determined data to getMenuWithParams()
 *
 * @return array nested array of menu items
 */
function getMenuByLevel() {
    switch ($_SESSION['lastLevel']) {
        case UserPermissionLevel::UNDEFINED:
            return getMenuForGuest();
        case UserPermissionLevel::DIRECT:
            return getMenuForDirect();
        case UserPermissionLevel::REGISTRAR:
            return getMenuForRegistrar();
        case UserPermissionLevel::SUPER_REGISTRAR:
            return getMenuForSuperRegistrar();
        case UserPermissionLevel::TAC:
            return getMenuForTAC();
        default:
            return getMenuForGuest();
    }
}

function setLevel($result) {
    $_SESSION['lastLevel'] = UserPermissionLevel::UNDEFINED;
    if ($result != null) {
        switch ($result) {
            case UserPermissionLevel::DIRECT:
            case UserPermissionLevel::REGISTRAR:
            case UserPermissionLevel::SUPER_REGISTRAR:
            case UserPermissionLevel::TAC:
                $_SESSION['lastLevel'] = $result;
                break;
        }
    }
    Yii::log('Permission level set ' . $_SESSION['lastLevel']);
}

function getMenu() {
    if (isset(Yii::app()->user->authenticatedUser)) {
        if (!array_key_exists('lastLogged', $_SESSION)) {
            $_SESSION['lastLogged'] = '';
        }
        if (!array_key_exists('lastLevel', $_SESSION)) {
            $_SESSION['lastLevel'] = '';
        }
        $user = Yii::app()->user->authenticatedUser;
        $trimmed_user = Utility::mb_trim($user->username);
        if ($_SESSION['lastLogged'] != $trimmed_user) {
            $_SESSION['lastLogged'] = $trimmed_user;
            $result = null;
            $errors = array();
            CRSPermissionsAppService_service::getUserLevel($result, $errors, $user);
            setLevel($result);
        }
        if (Yii::app()->user->authenticatedUser->passwordChangeRequired) {
            return getMenuForPasswordChange();
        } else {
            return getMenuByLevel();
        }
    } else {
        return getMenuForGuest();
    }
}

function getMenuForPasswordChange() {
    return array(
        array('label' => 'Change Password', 'url' => urlFor('/site/changePassword'),
            'visible' => true));
}

function getMenuForGuest() {
    return array(
        array('label' => 'Home', 'url' => urlFor('/site/index')),
        array('label' => 'Login', 'url' => urlFor('/site/login'), 'visible' => true),
        array('label' => 'Reset Password', 'url' => urlFor('/passwordReset/request'), 'visible' => true),
        array('label' => 'Create New Account', 'url' => urlFor('/site/newDirectAccount'), 'visible' => true),
        array('label' => 'Log In Instructions',
            'url' => 'https://www.iedr.ie/wp-content/uploads/2014/04/Console-Login-Instructions.pdf',
            'visible' => true, 'linkOptions' => array('target' => '_blank')),
        array('label' => 'Get Your Authcode', 'url' => urlFor('/authcodePortal/request'), 'visible' => true),
        array('label' => 'DNS Check', 'url' => urlFor('/dnscheck/check'), 'visible' => true));
}

function getMenuForRegistrar() {
    return array(
        array('label' => 'Home', 'url' => urlFor('/site/index')),
        array('label' => 'Confirm', 'url' => urlFor('/grid/confirm'), 'visible' => false),
        array('label' => 'Confirm Action', 'url' => urlFor('/grid/confirmaction'), 'visible' => false, 'id' => 'grid/Confirm Action'),
        array('label' => 'Confirmation', 'url' => urlFor('/grid/results'), 'visible' => false),
        array('label' => 'Domains', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'View Domain', 'url' => urlFor('/domains/viewdomain'), 'visible' => false),
                array('label' => 'Register New', 'url' => urlFor('/domains/regnew'), 'visible' => true),
                array('label' => 'Request Billing Transfer', 'url' => urlFor('/domains/requesttransfer'),
                    'visible' => true),
                array('label' => 'Request Transfer Details',
                    'url' => urlFor('/domains/requesttransferdetails'), 'visible' => false),
                array('label' => 'Request Transfer Result', 'url' => urlFor('/domains/requesttransferresult'),
                    'visible' => false),
                array('label' => 'Domain Details', 'url' => urlFor('/domains/domaindetails'),
                    'visible' => false),
                array('label' => 'Ticket Confirmation', 'url' => urlFor('/domains/domainscreated'),
                    'visible' => false),
                array('label' => 'Reports', 'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'All Domains', 'url' => urlFor('/domainreports/alldomains'),
                            'visible' => true),
                        array('label' => 'Newly Registered Domains',
                            'url' => urlFor('/newdomains/view'), 'visible' => true),
                        array('label' => 'Locked Domains', 'url' => urlFor('/lockeddomains/view'),
                            'visible' => true),
                        array('label' => 'Confirm Action', 'url' => urlFor('/domainreports/confirm_action'),
                            'visible' => false, 'id' => 'domainreports/Confirm Action'))))),
        array('label' => 'Registrant Transfer', 'url' => urlFor(null),
            'visible' => true,
            'items' => array(
                array('label' => 'Apply for a Domain', 'breadcrumbLabel' => 'Applying for an already registered Domain',
                    'url' => urlFor('/registranttransfer/apply'),
                    'visible' => true),
                array('label' => 'Apply for a Domain (Details)', 'breadcrumbLabel' => 'Applying for an already registered Domain',
                    'url' => urlFor('/registranttransfer/purchasedetails'),
                    'visible' => false),
                array('label' => 'Purchase Request Received', 'breadcrumbLabel' => 'Request Received',
                    'url' => urlFor('/registranttransfer/purchaseresult'),
                    'visible' => false),
                array('label' => 'Sell a Domain', 'breadcrumbLabel' => 'Transferring your .ie domain',
                    'url' => urlFor('/registranttransfer/sell'), 'visible' => true),
                array('label' => 'Sell a Domain (Details)', 'breadcrumbLabel' => 'Transferring your .ie domain',
                    'url' => urlFor('/registranttransfer/saledetails'),
                    'visible' => false),
                array('label' => 'Sale Request Received', 'breadcrumbLabel' => 'Request Received',
                    'url' => urlFor('/registranttransfer/saleresult'),
                    'visible' => false),
                array('label' => 'View Your Buy Requests', 'url' => urlFor('/registranttransferbuyrequests/view'),
                    'visible' => true),
                array('label' => 'View Buy Request', 'url' => urlFor('/registranttransfer/viewbuyrequest'),
                    'visible' => false),
                array('label' => 'Edit Buy Request', 'url' => urlFor('/registranttransfer/editbuyrequest'),
                    'visible' => false),
                array('label' => 'View Your Sell Requests', 'url' => urlFor('/registranttransfersellrequests/view'),
                    'visible' => true))),
        array('label' => 'Account Management', 'url' => urlFor(null),
            'visible' => true,
            'items' => array(
                array('label' => 'View Registrar Details', 'url' => urlFor('/registrar_details/viewregistrar'),
                    'visible' => false),
                array('label' => 'Deposit Account Tx', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Deposit Balance', 'url' => urlFor(null),
                            'visible' => true, 'id' => 'account_deposit_balance/',
                            'items' => array(
                                array('label' => 'Deposit Balance',
                                    'url' => urlFor('/account_deposit_balance/deposit_balance'),
                                    'visible' => true),
                                array('label' => 'Top up my deposit account',
                                    'url' => urlFor('/account_topup_my_deposit_account/menu'),
                                    'visible' => true),
                                array('label' => 'View Deposit Top-ups',
                                    'url' => urlFor('/account_view_deposit_topups/menu'),
                                    'visible' => true))),
                        array('label' => 'View Today`s Deposit Reservations',
                            'url' => urlFor('/account_today_deposit_reservations/menu'),
                            'visible' => true),
                        array('label' => 'Deposit Statement of Account',
                            'url' => urlFor('/account_deposit_statement_of_account/menu'),
                            'visible' => true),
                        array('label' => 'Failed Transactions',
                            'url' => urlFor('/account_failed_transactions/menu'), 'visible' => true))),
                array('label' => 'Pay to Renew Domains', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Current Renewals', 'url' => urlFor('/account_current_renewals/menu'),
                            'visible' => true),
                        array('label' => 'Confirm Action', 'id' => 'account_current_renewals/Confirm Action',
                            'url' => urlFor('/account_current_renewals/confirm_action'), 'visible' => false),
                        array('label' => 'Current Renewals Results',
                            'url' => urlFor('/account_current_renewals/result'), 'visible' => false),
                        array('label' => 'Future Renewals by month',
                            'url' => urlFor('/account_future_renewals_by_month/menu'), 'visible' => true))),
                array('label' => 'Domain Status (NRP)', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Current NRP Statuses',
                            'url' => urlFor('/account_current_nrp_statuses/menu'), 'visible' => true),
                        array('label' => 'Confirm Action', 'id' => 'account_current_nrp_statuses/Confirm Action',
                            'url' => urlFor('/account_current_nrp_statuses/confirm_action'), 'visible' => false),
                        array('label' => 'Current NRP Statuses Results',
                            'url' => urlFor('/account_current_nrp_statuses/result'), 'visible' => false),
                        array('label' => 'Deleted Domain Report',
                            'url' => urlFor('/account_deleted_domain_report/menu'), 'visible' => true))),
                array('label' => 'Credit Card Tx', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'View Today`s CC Reservations',
                            'url' => urlFor('/account_today_cc_reservations/menu'), 'visible' => true),
                        array('label' => 'Reauthorise CC Transaction',
                            'url' => urlFor('/account_reauthorise_cc_transaction/menu'),
                            'visible' => true,
                            'items' => array(
                                array('label' => 'Reauthorise',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/reauthorise'),
                                    'visible' => false),
                                array('label' => 'Confirm Action', 'id' => 'account_reauthorise_cc_transaction/Confirm Action',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/confirm_action'),
                                    'visible' => false),
                                array('label' => 'Reauthorise Results',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/result'),
                                    'visible' => false))))),
                array('label' => 'Invoice & Payment History',
                    'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'View Invoice History',
                            'url' => urlFor('/account_view_invoice_history/menu'), 'visible' => true),
                        array('label' => 'View Invoice',
                            'url' => urlFor('/account_view_invoice_history/invoiceview'), 'visible' => false),
                        array('label' => 'View Payment History',
                            'url' => urlFor('/account_view_payment_history/menu'), 'visible' => true),
                        array('label' => 'View Payment Details',
                            'url' => urlFor('/account_view_payment_history/paymentview'), 'visible' => false),
                        array('label' => 'Single Payment Search',
                            'url' => urlFor('/account_single_payment_search/menu'), 'visible' => true))),
                array('label' => 'Accounting Reports', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'View Autorenew Domains',
                            'url' => urlFor('/account_view_autorenew_domains/menu'), 'visible' => true),
                        array('label' => 'View Transfers In',
                            'url' => urlFor('/account_view_transfers_in/menu'), 'visible' => true),
                        array('label' => 'View Transfers Away',
                            'url' => urlFor('/account_view_transfers_away/menu'), 'visible' => true),
                        array('label' => 'View Charity Domains',
                            'url' => urlFor('/account_view_charity_domains/menu'), 'visible' => true),
                        array('label' => 'View Non-Billable Domains',
                            'url' => urlFor('/account_view_non_billable_domains/menu'),
                            'visible' => true))))),
        array('label' => 'Contacts', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'Find', 'url' => urlFor('/nicsearch/namesearch'), 'visible' => true),
                array('label' => 'Create Account', 'url' => urlFor('/nichandles/createnichandle'),
                    'visible' => true),
                array('label' => 'View Account', 'url' => urlFor('/nichandles/viewnichandle'), 'visible' => false),
                array('label' => 'Edit Account', 'url' => urlFor('/nichandles/editnichandle'), 'visible' => false))),
        array('label' => 'Tickets', 'url' => urlFor('/tickets/main'), 'visible' => true,
            'items' => array(
                array('label' => 'View Ticket', 'url' => urlFor('/tickets/viewticket'), 'visible' => false),
                array('label' => 'Edit Ticket', 'url' => urlFor('/tickets/editticket'), 'visible' => false))),
        array('label' => 'DNS', 'url' => urlFor('/nsreports/dnsserversearch'), 'visible' => true,
            'items' => array(
                array('label' => 'DNS Modification', 'url' => urlFor('/dns/dnsupdate'), 'visible' => false))),
        array('label' => 'Email Disabler', 'url' => urlFor('/emailDisabler/menu')),
        array('label' => 'Login', 'url' => urlFor('/site/login'), 'visible' => false));
}

function getMenuForSuperRegistrar() {
    return array(array('label' => 'Home', 'url' => urlFor('/site/index')),
        array('label' => 'Confirm', 'url' => urlFor('/grid/confirm'), 'visible' => false),
        array('label' => 'Confirm Action', 'url' => urlFor('/grid/confirmaction'), 'visible' => false,
            'id' => 'grid/Confirm Action'),
        array('label' => 'Confirmation', 'url' => urlFor('/grid/results'), 'visible' => false),
        array('label' => 'Domains', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'View Domain', 'url' => urlFor('/domains/viewdomain'), 'visible' => false),
                array('label' => 'Register New', 'url' => urlFor('/domains/regnew'), 'visible' => true),
                array('label' => 'Request Billing Transfer', 'url' => urlFor('/domains/requesttransfer'),
                    'visible' => true),
                array('label' => 'Request Transfer Details',
                    'url' => urlFor('/domains/requesttransferdetails'), 'visible' => false),
                array('label' => 'Request Transfer Result', 'url' => urlFor('/domains/requesttransferresult'),
                    'visible' => false),
                array('label' => 'Domain Details', 'url' => urlFor('/domains/domaindetails'),
                    'visible' => false),
                array('label' => 'Ticket Confirmation', 'url' => urlFor('/domains/domainscreated'),
                    'visible' => false),
                array('label' => 'Reports', 'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'All Domains', 'url' => urlFor('/domainreports/alldomains'),
                            'visible' => true),
                        array('label' => 'Newly Registered Domains',
                            'url' => urlFor('/newdomains/view'), 'visible' => true),
                        array('label' => 'Confirm Action', 'url' => urlFor('/domainreports/confirm_action'),
                            'visible' => false, 'id' => 'domainreports/Confirm Action'))))),
        array('label' => 'Registrant Transfer', 'url' => urlFor(null),
            'visible' => true,
            'items' => array(
                array('label' => 'Apply for a Domain', 'breadcrumbLabel' => 'Applying for an already registered Domain',
                    'url' => urlFor('/registranttransfer/apply'),
                    'visible' => true),
                array('label' => 'Apply for a Domain (Details)', 'breadcrumbLabel' => 'Applying for an already registered Domain',
                    'url' => urlFor('/registranttransfer/purchasedetails'),
                    'visible' => false),
                array('label' => 'Purchase Request Received', 'breadcrumbLabel' => 'Request Received',
                    'url' => urlFor('/registranttransfer/purchaseresult'),
                    'visible' => false),
                array('label' => 'Sell a Domain', 'breadcrumbLabel' => 'Transferring your .ie domain',
                    'url' => urlFor('/registranttransfer/sell'),
                    'visible' => true),
                array('label' => 'Sell a Domain (Details)', 'breadcrumbLabel' => 'Transferring your .ie domain',
                    'url' => urlFor('/registranttransfer/saledetails'),
                    'visible' => false),
                array('label' => 'Sale Request Received', 'breadcrumbLabel' => 'Request Received',
                    'url' => urlFor('/registranttransfer/saleresult'),
                    'visible' => false),
                array('label' => 'View Your Buy Requests', 'url' => urlFor('/registranttransferbuyrequests/view'),
                    'visible' => true),
                array('label' => 'View Buy Request', 'url' => urlFor('/registranttransfer/viewbuyrequest'),
                    'visible' => false),
                array('label' => 'Edit Buy Request', 'url' => urlFor('/registranttransfer/editbuyrequest'),
                    'visible' => false),
                array('label' => 'View Your Sell Requests', 'url' => urlFor('/registranttransfersellrequests/view'),
                    'visible' => true))),
        array('label' => 'Account Management', 'url' => urlFor(null),
            'visible' => true,
            'items' => array(
                array('label' => 'View Registrar Details', 'url' => urlFor('/registrar_details/viewregistrar'),
                    'visible' => false),
                array('label' => 'Deposit Account Tx', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Deposit Balance', 'url' => urlFor(null),
                            'visible' => true, 'id' => 'account_deposit_balance/',
                            'items' => array(
                                array('label' => 'Deposit Balance',
                                    'url' => urlFor('/account_deposit_balance/deposit_balance'),
                                    'visible' => true),
                                array('label' => 'Top up my deposit account',
                                    'url' => urlFor('/account_topup_my_deposit_account/menu'),
                                    'visible' => true),
                                array('label' => 'View Deposit Top-ups',
                                    'url' => urlFor('/account_view_deposit_topups/menu'),
                                    'visible' => true))),
                        array('label' => 'View Today`s Deposit Reservations',
                            'url' => urlFor('/account_today_deposit_reservations/menu'),
                            'visible' => true),
                        array('label' => 'Deposit Statement of Account',
                            'url' => urlFor('/account_deposit_statement_of_account/menu'),
                            'visible' => true),
                        array('label' => 'Failed Transactions',
                            'url' => urlFor('/account_failed_transactions/menu'), 'visible' => true))),
                array('label' => 'Pay to Renew Domains', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Current Renewals', 'url' => urlFor('/account_current_renewals/menu'),
                            'visible' => true),
                        array('label' => 'Confirm Action', 'id' => 'account_current_renewals/Confirm Action',
                            'url' => urlFor('/account_current_renewals/confirm_action'), 'visible' => false),
                        array('label' => 'Current Renewals Results',
                            'url' => urlFor('/account_current_renewals/result'), 'visible' => false),
                        array('label' => 'Future Renewals by month',
                            'url' => urlFor('/account_future_renewals_by_month/menu'), 'visible' => true))),
                array('label' => 'Domain Status (NRP)', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Current NRP Statuses',
                            'url' => urlFor('/account_current_nrp_statuses/menu'), 'visible' => true),
                        array('label' => 'Confirm Action', 'id' => 'account_current_nrp_statuses/Confirm Action',
                            'url' => urlFor('/account_current_nrp_statuses/confirm_action'), 'visible' => false),
                        array('label' => 'Current NRP Statuses Results',
                            'url' => urlFor('/account_current_nrp_statuses/result'), 'visible' => false),
                        array('label' => 'Deleted Domain Report',
                            'url' => urlFor('/account_deleted_domain_report/menu'), 'visible' => true))),
                array('label' => 'Credit Card Tx', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'View Today`s CC Reservations',
                            'url' => urlFor('/account_today_cc_reservations/menu'), 'visible' => true),
                        array('label' => 'Reauthorise CC Transaction',
                            'url' => urlFor('/account_reauthorise_cc_transaction/menu'),
                            'visible' => true,
                            'items' => array(
                                array('label' => 'Reauthorise',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/reauthorise'),
                                    'visible' => false),
                                array('label' => 'Confirm Action', 'id' => 'account_reauthorise_cc_transaction/Confirm Action',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/confirm_action'),
                                    'visible' => false),
                                array('label' => 'Reauthorise Results',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/result'),
                                    'visible' => false))))),
                array('label' => 'Invoice & Payment History',
                    'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'View Invoice History',
                            'url' => urlFor('/account_view_invoice_history/menu'), 'visible' => true),
                        array('label' => 'View Invoice',
                            'url' => urlFor('/account_view_invoice_history/invoiceview'), 'visible' => false),
                        array('label' => 'View Payment History',
                            'url' => urlFor('/account_view_payment_history/menu'), 'visible' => true),
                        array('label' => 'View Payment Details',
                            'url' => urlFor('/account_view_payment_history/paymentview'), 'visible' => false),
                        array('label' => 'Single Payment Search',
                            'url' => urlFor('/account_single_payment_search/menu'), 'visible' => true))),
                array('label' => 'Accounting Reports', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'View Autorenew Domains',
                            'url' => urlFor('/account_view_autorenew_domains/menu'), 'visible' => true),
                        array('label' => 'View Transfers In',
                            'url' => urlFor('/account_view_transfers_in/menu'), 'visible' => true),
                        array('label' => 'View Transfers Away',
                            'url' => urlFor('/account_view_transfers_away/menu'), 'visible' => true),
                        array('label' => 'View Charity Domains',
                            'url' => urlFor('/account_view_charity_domains/menu'), 'visible' => true),
                        array('label' => 'View Non-Billable Domains',
                            'url' => urlFor('/account_view_non_billable_domains/menu'),
                            'visible' => true))))
            ),
        array('label' => 'Contacts', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'Find', 'url' => urlFor('/nicsearch/namesearch'), 'visible' => true),
                array('label' => 'Create Account', 'url' => urlFor('/nichandles/createnichandle'),
                    'visible' => true),
                array('label' => 'View Account', 'url' => urlFor('/nichandles/viewnichandle'), 'visible' => false),
                array('label' => 'Edit Account', 'url' => urlFor('/nichandles/editnichandle'), 'visible' => false))),
        array('label' => 'Tickets', 'url' => urlFor('/tickets/main'), 'visible' => true,
            'items' => array(
                array('label' => 'View Ticket', 'url' => urlFor('/tickets/viewticket'), 'visible' => false),
                array('label' => 'Edit Ticket', 'url' => urlFor('/tickets/editticket'), 'visible' => false))),
        array('label' => 'DNS', 'url' => urlFor('/nsreports/dnsserversearch'), 'visible' => true,
            'items' => array(
                array('label' => 'DNS Modification', 'url' => urlFor('/dns/dnsupdate'), 'visible' => false))),
        array('label' => 'Login', 'url' => urlFor('/site/login'), 'visible' => false));
}

function getMenuForTAC() {
    return array(array('label' => 'Home', 'url' => urlFor('/site/index')),
        array('label' => 'Confirm', 'url' => urlFor('/grid/confirm'), 'visible' => false),
        array('label' => 'Confirm Action', 'url' => urlFor('/grid/confirmaction'), 'visible' => false,
            'id' => 'grid/Confirm Action'),
        array('label' => 'Confirmation', 'url' => urlFor('/grid/results'), 'visible' => false),
        array('label' => 'Domains', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'View Domain', 'url' => urlFor('/domains/viewdomain'), 'visible' => false),
                array('label' => 'Domain Details', 'url' => urlFor('/domains/domaindetails'),
                    'visible' => false),
                array('label' => 'Ticket Confirmation', 'url' => urlFor('/domains/domainscreated'),
                    'visible' => false),
                array('label' => 'Reports', 'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'Confirm Action', 'url' => urlFor('/domainreports/confirm_action'),
                            'visible' => false, 'id' => 'domainreports/Confirm Action'),
                        array('label' => 'All Domains', 'url' => urlFor('/domainreports/alldomains'),
                            'visible' => true))),
                array('label' => 'Create Account', 'url' => urlFor('/nichandles/createnichandle'),
                    'visible' => false),
                array('label' => 'View Account', 'url' => urlFor('/nichandles/viewnichandle'), 'visible' => false),
                array('label' => 'Edit Account', 'url' => urlFor('/nichandles/editnichandle'), 'visible' => false))),
        array('label' => 'Registrant Transfer', 'url' => urlFor(null),
            'visible' => true,
            'items' => array(
                array('label' => 'View Your Sell Requests', 'url' => urlFor('/registranttransfersellrequests/view'),
                    'visible' => true))),
        array('label' => 'Tickets', 'url' => urlFor('/tickets/main'), 'visible' => true,
            'items' => array(
                array('label' => 'View Ticket', 'url' => urlFor('/tickets/viewticket'), 'visible' => false),
                array('label' => 'Edit Ticket', 'url' => urlFor('/tickets/editticket'), 'visible' => false))),
        array('label' => 'DNS', 'url' => urlFor('/nsreports/dnsserversearch'), 'visible' => true,
            'items' => array(
                array('label' => 'DNS Modification', 'url' => urlFor('/dns/dnsupdate'), 'visible' => false))),
        array('label' => 'Change Password', 'url' => urlFor('/site/changePassword'), 'visible' => false),
        array('label' => 'Login', 'url' => urlFor('/site/login'), 'visible' => false));
}

function getMenuForDirect() {
    return array(array('label' => 'Home', 'url' => urlFor('/site/index')),
        array('label' => 'Confirm', 'url' => urlFor('/grid/confirm'), 'visible' => false),
        array('label' => 'Confirm Action', 'url' => urlFor('/grid/confirmaction'), 'visible' => false,
            'id' => 'grid/Confirm Action'),
        array('label' => 'Confirmation', 'url' => urlFor('/grid/results'), 'visible' => false),
        array('label' => 'Domains', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'View Domain', 'url' => urlFor('/domains/viewdomain'), 'visible' => false),
                array('label' => 'Register New', 'url' => urlFor('/domains/regnew'), 'visible' => true),
                array('label' => 'Request Billing Transfer', 'url' => urlFor('/domains/requesttransfer'),
                    'visible' => true),
                array('label' => 'Request Transfer Details',
                    'url' => urlFor('/domains/requesttransferdetails'), 'visible' => false),
                array('label' => 'Request Transfer Result', 'url' => urlFor('/domains/requesttransferresult'),
                    'visible' => false),
                array('label' => 'Domain Details', 'url' => urlFor('/domains/domaindetails'),
                    'visible' => false),
                array('label' => 'Ticket Confirmation', 'url' => urlFor('/domains/domainscreated'),
                    'visible' => false),
                array('label' => 'Reports', 'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'Confirm Action', 'url' => urlFor('/domainreports/confirm_action'),
                            'visible' => false, 'id' => 'domainreports/Contifm Action'),
                        array('label' => 'All Domains', 'url' => urlFor('/domainreports/alldomains'),
                            'visible' => true),
                        array('label' => 'View Charity Domains',
                            'url' => urlFor('/account_view_charity_domains/menu'), 'visible' => true))),
                array('label' => 'Find', 'url' => urlFor('/nicsearch/namesearch'), 'visible' => false),
                array('label' => 'Create Account', 'url' => urlFor('/nichandles/createnichandle'),
                    'visible' => false),
                array('label' => 'View Account', 'url' => urlFor('/nichandles/viewnichandle'), 'visible' => false),
                array('label' => 'Edit Account', 'url' => urlFor('/nichandles/editnichandle'), 'visible' => false))),
        array('label' => 'Registrant Transfer', 'url' => urlFor(null), 'visible' => true,
            'items' => array(
                array('label' => 'Apply for a Domain', 'breadcrumbLabel' => 'Applying for an already registered Domain',
                    'url' => urlFor('/registranttransfer/apply'),
                    'visible' => true),
                array('label' => 'Apply for a Domain (Details)', 'breadcrumbLabel' => 'Applying for an already registered Domain',
                    'url' => urlFor('/registranttransfer/purchasedetails'),
                    'visible' => false),
                array('label' => 'Purchase Request Received', 'breadcrumbLabel' => 'Request Received',
                    'url' => urlFor('/registranttransfer/purchaseresult'),
                    'visible' => false),
                array('label' => 'Sell a Domain', 'breadcrumbLabel' => 'Transferring your .ie domain',
                    'url' => urlFor('/registranttransfer/sell'),
                    'visible' => true),
                array('label' => 'Sell a Domain (Details)', 'breadcrumbLabel' => 'Transferring your .ie domain',
                    'url' => urlFor('/registranttransfer/saledetails'),
                    'visible' => false),
                array('label' => 'Sale Request Received', 'breadcrumbLabel' => 'Request Received',
                    'url' => urlFor('/registranttransfer/saleresult'),
                    'visible' => false),
                array('label' => 'View Your Buy Requests', 'url' => urlFor('/registranttransferbuyrequests/view'),
                    'visible' => true),
                array('label' => 'View Buy Request', 'url' => urlFor('/registranttransfer/viewbuyrequest'),
                    'visible' => false),
                array('label' => 'Edit Buy Request', 'url' => urlFor('/registranttransfer/editbuyrequest'),
                    'visible' => false),
                array('label' => 'View Your Sell Requests', 'url' => urlFor('/registranttransfersellrequests/view'),
                    'visible' => true))),
        array('label' => 'Account Management', 'url' => urlFor(null),
            'visible' => true,
            'items' => array(
                array('label' => 'Pay to Renew Domains', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Current Renewals', 'url' => urlFor('/account_current_renewals/menu'),
                            'visible' => true),
                        array('label' => 'Confirm Action', 'id' => 'account_current_renewals/Confirm Action',
                            'url' => urlFor('/account_current_renewals/confirm_action'), 'visible' => false),
                        array('label' => 'Current Renewals Results',
                            'url' => urlFor('/account_current_renewals/result'), 'visible' => false),
                        array('label' => 'Future Renewals by month',
                            'url' => urlFor('/account_future_renewals_by_month/menu'), 'visible' => true))),
                array('label' => 'Domain Status (NRP)', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'Current NRP Statuses',
                            'url' => urlFor('/account_current_nrp_statuses/menu'), 'visible' => true),
                        array('label' => 'Confirm Action', 'id' => 'account_current_nrp_statuses/Confirm Action',
                            'url' => urlFor('/account_current_nrp_statuses/confirm_action'), 'visible' => false),
                        array('label' => 'Current NRP Statuses Results',
                            'url' => urlFor('/account_current_nrp_statuses/result'), 'visible' => false))),
                array('label' => 'Credit Card Tx', 'url' => urlFor(null),
                    'visible' => true,
                    'items' => array(
                        array('label' => 'View Today`s CC Reservations',
                            'url' => urlFor('/account_today_cc_reservations/menu'), 'visible' => true),
                        array('label' => 'Reauthorise CC Transaction',
                            'url' => urlFor('/account_reauthorise_cc_transaction/menu'),
                            'visible' => true,
                            'items' => array(
                                array('label' => 'Reauthorise',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/reauthorise'),
                                    'visible' => false),
                                array('label' => 'Confirm Action', 'id' => 'account_reauthorise_cc_transaction/Confirm Action',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/confirm_action'),
                                    'visible' => false),
                                array('label' => 'Reauthorise Results',
                                    'url' => urlFor('/account_reauthorise_cc_transaction/result'),
                                    'visible' => false))))),
                array('label' => 'Invoice & Payment History',
                    'url' => urlFor(null), 'visible' => true,
                    'items' => array(
                        array('label' => 'View Invoice History',
                            'url' => urlFor('/account_view_invoice_history/menu'), 'visible' => true),
                        array('label' => 'View Invoice',
                            'url' => urlFor('/account_view_invoice_history/invoiceview'), 'visible' => false),
                        array('label' => 'View Payment History',
                            'url' => urlFor('/account_view_payment_history/menu'), 'visible' => true),
                        array('label' => 'View Payment Details',
                            'url' => urlFor('/account_view_payment_history/paymentview'), 'visible' => false),
                        array('label' => 'Single Payment Search',
                            'url' => urlFor('/account_single_payment_search/menu'), 'visible' => true))))),
        array('label' => 'Tickets', 'url' => urlFor('/tickets/main'), 'visible' => true,
            'items' => array(
                array('label' => 'View Ticket', 'url' => urlFor('/tickets/viewticket'), 'visible' => false),
                array('label' => 'Edit Ticket', 'url' => urlFor('/tickets/editticket'), 'visible' => false))),
        array('label' => 'DNS', 'url' => urlFor('/nsreports/dnsserversearch'), 'visible' => true,
            'items' => array(
                array('label' => 'DNS Modification', 'url' => urlFor('/dns/dnsupdate'), 'visible' => false))),
        array('label' => 'Change Password', 'url' => urlFor('/site/changePassword'), 'visible' => false),
        array('label' => 'Login', 'url' => urlFor('/site/login'), 'visible' => false));
}

function urlFor($url) {
    if ($url === null) {
        return '#';
    } else {
        return array($url);
    }
}

/**
 * For the given menu label id, locate the label in the menu tree,
 * then print a row of ' / '-separated labels for each parent-or-self menu item
 */
function printBreadcrumbTrail($labelOrId) {
    $m = array('items' => getMenu(), 'label' => 'Root');
    checkLabelsAreUnique(array($m));
    $pathToLabel = menuFindLabelRecurse($m, $labelOrId);
    if ($pathToLabel == null) {
        throw new Exception('The Menu label "' . $labelOrId . '" was NOT FOUND : either change the search-string, or update the menu\'s label (case sensitive)');
    }
    $labels = array_map(function($menuItem) {
        return isset($menuItem['breadcrumbLabel']) ? $menuItem['breadcrumbLabel'] : $menuItem['label'];
    }, $pathToLabel);
    // Omit the first item ("Root").
    echo implode(" – ", array_slice($labels, 1));
}

/**
 * Recursively locate a label in a menu tree, returning a list of parent item labels
 */
function menuFindLabelRecurse($menu, $labelOrId) {
    $key = isset($menu['id']) ? $menu['id'] : $menu['label'];
    if ($key == $labelOrId) {
        return array($menu);
    }
    if (isset($menu['items'])) {
        foreach ($menu['items'] as $childMenu) {
            $pathToLabel = menuFindLabelRecurse($childMenu, $labelOrId);
            if ($pathToLabel != null) {
                return array_merge(array($menu), $pathToLabel);
            }
        }
    }
    return null;
}

function countLabels(&$result, $menuItems) {
    if (is_array($menuItems)) {
        foreach ($menuItems as $menuItem) {
            $itemId = isset($menuItem['id']) ? $menuItem['id'] : $menuItem['label'];
            if (!isset($result[$itemId])) {
                $result[$itemId] = 0;
            }
            $result[$itemId] += 1;
            if (isset($menuItem['items'])) {
                countLabels($result, $menuItem['items']);
            }
        }
    }
}

function checkLabelsAreUnique($m) {
    $a = array();
    countLabels($a, $m);
    $errs = array();
    foreach ($a as $k => $v)
        if ($v > 1)
            $errs[] = $k;
    if (count($errs) > 0) {
        Yii::log('Menu Labels are Duplicated : "' . implode('", "', $errs) . '"', CLogger::LEVEL_WARNING);
    }
}
