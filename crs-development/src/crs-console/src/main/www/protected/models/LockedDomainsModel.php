<?php

class LockedDomainsModel extends GridModelBase {
    public $defaultSortColumn = 'A';
    public $columns = array();

    public function __construct() {
        $this->columns = array(
            'A' => array('resultfield' => 'name','criteriafield' => 'domainName','label' => 'Domain Name','width' => 60,
                'link' => 'domains/viewdomain','type' => 'string'),
            'B' => array('resultfield' => 'holder','criteriafield' => 'domainHolder','label' => 'Holder','width' => 60,
                'type' => 'string'),
            'C' => array('resultfield' => 'lockingDate','criteriafield' => 'lockingDate','label' => 'Lock Date',
                'width' => 60,'type' => 'datefilter'),
            'D' => array('resultfield' => 'lockingRenewalDate','criteriafield' => 'lockingRenewalDate',
                'label' => 'Lock Renewal Date','width' => 60,'type' => 'datefilter'),
            'E' => array('resultfield' => 'dsmState','label' => 'Locked','width' => 20,'type' => 'boolean'));
    }

    public function getSearchBase() {
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->accountId = Yii::app()->user->id;
        return $criteria;
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        $criteria->lockingActive = true;
        foreach ($searchparams as $key => $value) {
            if (is_object($value) && isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                if ($field != null) {
                    switch ($field) {
                        case 'lockingDate':
                            $dates = mb_split(" ", $value->data);
                            $criteria->lockFrom = $dates[0];
                            $criteria->lockTo = $dates[1];
                            unset($criteria->$field);
                            break;
                        case 'lockingRenewalDate':
                            $dates = mb_split(" ", $value->data);
                            $criteria->lockRenewalFrom = $dates[0];
                            $criteria->lockRenewalTo = $dates[1];
                            unset($criteria->$field);
                            break;
                    }
                }
            }
        }
        Yii::log('SEARCH CRITERIA= ' . print_r($criteria, true) . ' IN LockedDomainsModel');
        return $criteria;
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('A' => encode($o->name),'B' => cleanString(encode($o->holder)),
            'C' => parseXmlDate($o->lockingDate),'D' => parseXmlDate($o->lockingRenewalDate),
            'E' => $o->dsmState->locked ? "yes" : "no");
    }

}