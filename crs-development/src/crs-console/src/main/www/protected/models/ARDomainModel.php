<?php

class ARDomainModel extends AllDomainsModel {

    public function __construct() {
        $this->columns = array(
            'A' => array('resultfield' => 'name', 'criteriafield' => 'domainName',
                'label' => 'Domain Name', 'width' => 28, 'link' => 'domains/viewdomain',
                'type' => 'string'),
            'B' => array('resultfield' => 'holder', 'criteriafield' => 'domainHolder',
                'label' => 'Holder', 'width' => 28, 'type' => 'string'),
            'C' => array('resultfield' => 'dsmRenewalMode', 'criteriafield' => 'renewalModes',
                'label' => 'Autorenew Status', 'width' => 10, 'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getRenewalsMode())),
            'D' => array('resultfield' => 'dsmNrpStatus', 'criteriafield' => 'shortNRPStatus',
                'label' => 'Status', 'width' => 16, 'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getAllShortStatuses())),
            'E' => array('resultfield' => 'renewalDate', 'criteriafield' => 'renewalDate',
                'label' => 'Renewal Date', 'width' => 18, 'type' => 'datefilter'));
    }

    public function getSearchBase() {
        // override to set the filter
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->billingStatus = array('A','R');
        if (isset($criteria->accountId)) {
            $criteria->accountId = null;
        }
        return $criteria;
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        if ($criteria->contactType == null) {
            unset($criteria->contactType);
        }
        if (!isset($criteria->renewalModes)) {
            $criteria->renewalModes[] = 'Autorenew';
            $criteria->renewalModes[] = 'RenewOnce';
        }
        foreach ($searchparams as $key => $value) {
            if (is_object($value) && isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                if ($field != null) {
                    switch ($field) {
                        case 'renewalDate':
                            $dates = mb_split(" ", $value->data);
                            $criteria->renewalFrom = $dates[0];
                            $criteria->renewalTo = $dates[1];
                            unset($criteria->$field);
                            break;
                    }
                }
            }
        }
        Yii::log('SEARCH CRITERIA = ' . print_r($criteria, true) . ' IN ARDomainModel');
        return $criteria;
    }

    public function getSort($sortparams) {
        Yii::log('SORTPARAMS= ' . print_r($sortparams, true));
        return parent::getSort($sortparams);
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('A' => encode($o->name), 'B' => cleanString(encode($o->holder)), 'C' => $o->dsmState->renewalMode,
            'D' => $o->dsmState->shortNrpStatus, 'E' => parseXmlDate($o->renewalDate));
    }

}
