<?php

class CurrentNRPStatusesModel extends AllDomainsModel {
    public $columns;
    public $defaultSortColumn = 'PK';
    public $productSummaries = array();
    public $disabled = false;

    public function __construct($contact_type = null) {
        if (isset($contact_type) && $this->verifyContactType($contact_type)) {
            $this->contact_type = $contact_type;
        } else {
            $this->contact_type = 'BILLING';
        }
        ;
        $this->disabled = ($this->contact_type != 'BILLING');
        $this->excludeFromExport = array('B','J');
        $this->productSummaries = Utility::getProductSummaries('REN');
        $this->columns = array(
            'PK' => array('resultfield' => 'name','criteriafield' => 'domainName','label' => 'Domain Name',
                'width' => 60,'link' => 'domains/viewdomain','type' => 'string'),
            'B' => array('label' => 'Periods','width' => 65,'title' => 'false'),
            'C' => array('resultfield' => 'holder','label' => 'Holder','width' => 60),
            'D' => array('resultfield' => 'dsmNrpStatus','criteriafield' => 'shortNRPStatus','label' => 'Status',
                'width' => 50,'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getNrpShortStatuses())),
            'E' => array('resultfield' => 'deletionDate','label' => 'Del Date','width' => 25,'type' => 'string'),
            'F' => array('resultfield' => 'suspensionDate','label' => 'Sus Date','width' => 25,'type' => 'string'),
            'G' => array('resultfield' => 'renewalDate','label' => 'Ren Date','width' => 25,'type' => 'string'),
            'H' => array('resultfield' => 'registrationDate','label' => 'Reg Date','width' => 25,'type' => 'string'),
            'J' => array('label' => 'Reservations','width' => 30,'sorting' => false),
            'K' => array('label' => 'Reservations','type' => 'hidden'));
    }

    public function getSearchBase() {
        return new CRSDomainAppService_extendedDomainSearchCriteriaVO();
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('PK' => encode($o->name),
            'B' => Utility::createSelect(encode($o->name), $this->productSummaries),
            'C' => cleanString(encode($o->holder)),
            'D' => $o->dsmState->shortNrpStatus,
            'E' => parseXmlDate($o->deletionDate),
            'F' => parseXmlDate($o->suspensionDate),
            'G' => parseXmlDate($o->renewalDate),
            'H' => parseXmlDate($o->registrationDate),
            'J' => $this->createLink($o),
            'K' => $this->createValue($o));
    }


    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        $criteria->activeFlag = false;
        return $criteria;
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

    private function createValue($o) {
        if (Utility::isDirect()) {
            if ($o->pendingReservationPaymentMethod) {
                return 'Yes';
            }
        } else if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
            $link = '';
            if ($o->pendingReservationPaymentMethod = "CC") {
                $link = 'CC';
            }
            if ($o->pendingReservationPaymentMethod = "ADP") {
                $link = 'DEP';
            }
            if ($link != '') {
                return $link;
            }
        }
        return 'No';
    }

}
