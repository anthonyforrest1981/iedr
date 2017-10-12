<?php

class AccountFutureRenewalByMonthModel extends GridModelBase {
    public $date;
    public $productSummaries = array();
    public $defaultSortColumn = 'PK';

    public function __construct() {
        parent::__construct();
        $this->date = date("Y-m", strtotime('1 months', strtotime(date("Y-m-01"))));
        $this->productSummaries = Utility::getProductSummaries('REN');
        $this->columns = array(
            'PK' => array('label' => 'Domain','width' => 60,'link' => 'domains/viewdomain',
                'resultfield' => 'domainName','criteriafield' => 'domainName'),
            'H' => array('label' => 'Periods','width' => 30),
            'C' => array('label' => 'Holder','resultfield' => 'holder',
                // 'criteriafield' => null,
                'width' => 50,'type' => null),
            'D' => array('label' => 'Registration','width' => 15,'resultfield' => 'registrationDate',
                // 'criteriafield' => null,
                'type' => null),
            'K' => array('label' => 'Renewal','resultfield' => 'renewalDate',
                // 'criteriafield' => null,
                'width' => 15,'type' => null),
            'L' => array('label' => 'Pending Reservations',
                // 'resultfield' => 'pendingReservations',
                'width' => 25,'type' => null));
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array(
            'PK' => encode($o->name),
            'H' => Utility::createSelect(encode($o->name), $this->productSummaries),
            'C' => cleanString(encode($o->holder)),
            'D' => parseXmlDate($o->registrationDate),
            'K' => parseXmlDate($o->renewalDate),
            'L' => $this->createLink($o));
    }

    public function getSearchBase() {
        return new CRSDomainAppService_extendedDomainSearchCriteriaVO();
    }

    private function createLink($o) {
        if (Utility::isDirect()) {
            if ($o->pendingReservationPaymentMethod) {
                return CHtml::link("Yes", Yii::app()->createUrl("account_today_cc_reservations/menu"));
            }
        } else if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
            $link = "";
            if ($o->pendingReservationPaymentMethod == "CC") {
                $link = CHtml::link("CC", Yii::app()->createUrl("account_today_cc_reservations/menu"));
            }
            if ($o->pendingReservationPaymentMethod == "ADP") {
                $link = Utility::mb_trim($link . " " . CHtml::link("DEP", Yii::app()->createUrl("account_today_deposit_reservations/menu")));
            }
            if ($link != "") {
                return $link;
            }
        }
        return "No";
    }

}
