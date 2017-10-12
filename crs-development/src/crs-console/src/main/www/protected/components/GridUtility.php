<?php

/**
 * defines GridUtility class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
/**
 * Utility class with useful html-generating functions
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class GridUtility {

    static public function getDatesForInvoice($controller, $text, $formId, $model, $modelDateField = 'date') {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' for ' . $form->dropDownList($model, 'year', GridUtility::getInvoiceYearSelectOpts()) . ' ' . $form->dropDownList($model, 'month', GridUtility::getMonthList()) . ' ' . CHtml::submitButton('Update') . '</h2></div>';
        $controller->endWidget();
    }

    static public function makeCurrenRenewalsOption($controller, $text, $formId, $model,
            $modelDateField = 'renewalDateType') {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' for ' . $form->dropDownList($model, $modelDateField, array(
            'OVERDUE' => 'Overdue','RENEWAL_TODAY' => 'Due for renewal today',
            'RENEWAL_THIS_MONTH' => 'Due for renewal this month')) . ' ' . CHtml::submitButton('Update') . '</h2></div>';
        $controller->endWidget();
    }

    static public function makeMonthYearDateSelectUpdateForm($controller, $text, $formId, $model, $modelDateField = 'date') {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' for ' . $form->dropDownList($model, 'date', GridUtility::getNextMnYrSelOpts()) . ' ' . CHtml::submitButton('Update') . '</h2></div>';
        $controller->endWidget();
    }

    static public function readyForSettlement($controller, $text, $formId, $model, $modelDateField = 'list') {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        $list = array('All','Ready for settelment','Not ready for settelment');
        echo '<div class="row"><h2>' . $text . ' ' . $form->dropDownList($model, 'list', $list) . ' ' . CHtml::submitButton('Update') . '</h2></div>';
        $controller->endWidget();
    }

    /**
     * returns array of year-month values for select-lists
     *
     * @return array array of 10 strings like '2011-06' => 'June-2011', for next 10 months
     * @access protected
     * @static
     *
     */
    static protected function getNextMnYrSelOpts($asc = true) {
        $firstDate = strtotime(date("Y-m-01"));
        $retval = array();
        $sign = '+';
        if ($asc == false) {
            $firstDate = strtotime('11 months', $firstDate);
            $sign = '-';
        }
        for ($i = 0; $i < 12; $i++) {
            $timePeriod = strtotime($sign . $i . ' months', $firstDate);
            $retval[date('Y-m', $timePeriod)] = date('F', $timePeriod);
        }
        return $retval;
    }

    static protected function getInvoiceYearSelectOpts() {
        // start at current year
        $currentDate = strtotime(date('Y-01-01'));
        $startDate = strtotime(GridUtility::getInvoiceStartYear() . '-01-01');
        $retval = array();
        $timePeriod = $currentDate;
        while ($timePeriod >= $startDate) {
            $retval[date('Y', $timePeriod)] = date('Y', $timePeriod);
            $timePeriod = strtotime('-1 year', $timePeriod);
        }
        return $retval;
    }

    /**
     * returns a two-element array of last year and this year as integers
     *
     * @return array two-element array of last year and this year as integers
     * @access protected
     * @static
     *
     */
    static protected function getYearSelOpts() {
        // start at previous year
        $selarr = array();
        $Y = date('Y', strtotime('-1 year'));
        $selarr[$Y] = $Y;
        $Y = date('Y');
        $selarr[$Y] = $Y;
        return $selarr;
    }

    /**
     * returns an array of months for select-lists
     *
     * @return array array of months, key and value are both month-name strings
     * @access public
     * @static
     *
     */
    static public function getMonthSelOpts() {
        // start at Jan
        $arr = array();
        foreach (self::getMonthList() as $i => $m)
            $arr[$m] = $m;
        return $arr;
    }

    /**
     * returns an array of month numbers and names for select-lists
     *
     * @return array array of (int,1-12)month => (string,eg 'January')month-name
     * @access public
     * @static
     *
     */
    static public function getMonthList() {
        $l = array();
        for ($i = 1; $i < 13; $i++)
            $l[$i] = date('F', strtotime('2000-' . $i . '-01'));
        return $l;
    }

    static public function getDomainsWithRenewalMode($model) {
        $domains = array();
        $domainWithPeriods = mb_split(',', $model->period);
        for ($i = 0; $i < count($domainWithPeriods); $i++) {
            $pair = mb_split(' ', $domainWithPeriods[$i]);
            Yii::log('pair= ' . print_r($pair, true));
            if (count($pair) == 2) {
                $domains[$pair[0]] = $pair[1];
            }
        }
        Yii::log('added= ' . print_r(count($domains), true));
        return $domains;
    }

    static public function getDomainsWithPeriod($model) {
        $prices = get_reg_prices();
        $r_codes = $prices['CODE'];
        $domains = array();
        $domainWithPeriods = mb_split(',', $model->period);
        for ($i = 0; $i < count($domainWithPeriods); $i++) {
            $pair = mb_split(' ', $domainWithPeriods[$i]);
            Yii::log('pair= ' . print_r($pair, true));
            if (count($pair) == 2) {
                $domains[$i] = new CRSPaymentAppService_domainWithPeriodVO();
                $domains[$i]->domainName = $pair[0];
                $periodCode = $pair[1];
                $domains[$i]->periodInYears = $r_codes[$periodCode]->duration;
                Yii::log('domain= ' . print_r($pair[0], true));
                Yii::log('period= ' . print_r($pair[1], true));
            }
        }
        Yii::log('added= ' . print_r(count($domains), true));
        return $domains;
    }

    static public function getAllShortStatuses() {
        return array(CRSDomainAppService_shortNRPStatus::_Active => 'Active',
            CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed => CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended => CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended,
            CRSDomainAppService_shortNRPStatus::_VoluntaryMailed => CRSDomainAppService_shortNRPStatus::_VoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_VoluntarySuspended => CRSDomainAppService_shortNRPStatus::_VoluntarySuspended)
        // CRSDomainAppService_shortNRPStatus::_NA => 'N/A',
        ;
    }

    static public function getNrpShortStatuses() {
        return array(
            CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed => CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended => CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended,
            CRSDomainAppService_shortNRPStatus::_VoluntaryMailed => CRSDomainAppService_shortNRPStatus::_VoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_VoluntarySuspended => CRSDomainAppService_shortNRPStatus::_VoluntarySuspended);
    }

    static public function getTicketTypes() {
        return array(CRSTicketAppService_domainOperationType::_REG => 'Registration',
            CRSTicketAppService_domainOperationType::_MOD => 'Modification',
            CRSTicketAppService_domainOperationType::_XFER => 'Transfer',
            CRSTicketAppService_domainOperationType::_DEL => 'Deletion');
    }

    static public function getReservationTypes() {
        return array('REGISTRATION' => 'New Reg', 'TRANSFER' => 'Xfer', 'RENEWAL' => 'Renewal',
            'BUY_REQUEST' => 'Buy Request', 'SELL_REQUEST' => 'Sell Request');
    }

    static public function getRenewalsMode() {
        return array('Autorenew' => 'Autorenew','RenewOnce' => 'RenewOnce');
    }

    static public function getAdminStatuses() {
        return array(
            CRSTicketAppService_adminStatus::_NEW => CRSTicketAppService_adminStatus::_NEW,
            CRSTicketAppService_adminStatus::_PASSED => CRSTicketAppService_adminStatus::_PASSED,
            CRSTicketAppService_adminStatus::_HOLD_UPDATE => CRSTicketAppService_adminStatus::_HOLD_UPDATE,
            CRSTicketAppService_adminStatus::_HOLD_PAPERWORK => CRSTicketAppService_adminStatus::_HOLD_PAPERWORK,
            CRSTicketAppService_adminStatus::_STALLED => CRSTicketAppService_adminStatus::_STALLED,
            CRSTicketAppService_adminStatus::_RENEW => CRSTicketAppService_adminStatus::_RENEW,
            CRSTicketAppService_adminStatus::_FINANCE_HOLDUP => CRSTicketAppService_adminStatus::_FINANCE_HOLDUP,
            CRSTicketAppService_adminStatus::_CANCELLED => CRSTicketAppService_adminStatus::_CANCELLED,
            CRSTicketAppService_adminStatus::_HOLD_REGISTRAR_APPROVAL => CRSTicketAppService_adminStatus::_HOLD_REGISTRAR_APPROVAL,
            CRSTicketAppService_adminStatus::_DOCUMENTS_SUBMITTED => CRSTicketAppService_adminStatus::_DOCUMENTS_SUBMITTED);
    }

    static public function getTechStatuses() {
        return array(
            CRSTicketAppService_techStatus::_NEW => CRSTicketAppService_techStatus::_NEW,
            CRSTicketAppService_techStatus::_PASSED => CRSTicketAppService_techStatus::_PASSED,
            CRSTicketAppService_techStatus::_STALLED => CRSTicketAppService_techStatus::_STALLED);
    }

    static public function getFinancialStatuses() {
        return array(
            CRSTicketAppService_financialStatus::_NEW => CRSTicketAppService_financialStatus::_NEW,
            CRSTicketAppService_financialStatus::_PASSED => CRSTicketAppService_financialStatus::_PASSED,
            CRSTicketAppService_financialStatus::_STALLED => CRSTicketAppService_financialStatus::_STALLED);
    }

    static public function getInvoiceStartYear() {
        return '2013';
    }

    static public function getBuyRequestStatuses() {
        return array(
            CRSSecondaryMarketAppService_buyRequestStatus::_NEW => CRSSecondaryMarketAppService_buyRequestStatus::_NEW,
            CRSSecondaryMarketAppService_buyRequestStatus::_PASSED => CRSSecondaryMarketAppService_buyRequestStatus::_PASSED,
            CRSSecondaryMarketAppService_buyRequestStatus::_HOLD_UPDATE => CRSSecondaryMarketAppService_buyRequestStatus::_HOLD_UPDATE,
            CRSSecondaryMarketAppService_buyRequestStatus::_HOLD_PAPERWORK => CRSSecondaryMarketAppService_buyRequestStatus::_HOLD_PAPERWORK,
            CRSSecondaryMarketAppService_buyRequestStatus::_STALLED => CRSSecondaryMarketAppService_buyRequestStatus::_STALLED,
            CRSSecondaryMarketAppService_buyRequestStatus::_RENEW => CRSSecondaryMarketAppService_buyRequestStatus::_RENEW,
            CRSSecondaryMarketAppService_buyRequestStatus::_FINANCE_HOLDUP => CRSSecondaryMarketAppService_buyRequestStatus::_FINANCE_HOLDUP,
            CRSSecondaryMarketAppService_buyRequestStatus::_HOLD_REGISTRAR_APPROVAL => CRSSecondaryMarketAppService_buyRequestStatus::_HOLD_REGISTRAR_APPROVAL,
            CRSSecondaryMarketAppService_buyRequestStatus::_DOCUMENTS_SUBMITTED => CRSSecondaryMarketAppService_buyRequestStatus::_DOCUMENTS_SUBMITTED);
    }

}

