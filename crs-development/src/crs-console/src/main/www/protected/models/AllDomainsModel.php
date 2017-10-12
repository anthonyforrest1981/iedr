<?php

class AllDomainsModel extends GridModelBase {
    public $contact_type;
    public $domainCountForContact = array('BILLING' => 0,'ADMIN' => 0,'TECH' => 0);
    public $columns = array();
    public $defaultSortColumn = 'A';

    public function __construct($contact_type = null) {
        if (isset($contact_type) && $this->verifyContactType($contact_type)) {
            $this->contact_type = $contact_type;
        } else if (Utility::isRegistrar()) {
            $this->contact_type = 'BILLING';
        } else {
            $this->contact_type = 'ADMIN';
        }
        $this->columns = array(
            'A' => array('resultfield' => 'name', 'criteriafield' => 'domainName',
                'label' => 'Domain Name', 'width' => 33, 'link' => 'domains/viewdomain',
                'type' => 'string'),
            'B' => array('resultfield' => 'holder', 'criteriafield' => 'domainHolder',
                'label' => 'Holder', 'width' => 33, 'type' => 'string'),
            'C' => array('resultfield' => 'dsmNrpStatus', 'criteriafield' => 'shortNRPStatus',
                'label' => 'Status', 'width' => 15,'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getAllShortStatuses())),
            'D' => array('resultfield' => 'renewalDate', 'criteriafield' => 'renewalDate',
                'label' => 'Renewal Date', 'width' => 18,'type' => 'datefilter'));
    }

    public function getSearchBase() {
        $criteria = new CRSDomainAppService_domainSearchCriteriaVO();
        $criteria->accountId = Yii::app()->user->id;
        return $criteria;
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param array $searchparams
     *            Parameter description (if any) ...
     * @return object Return description (if any) ...
     * @access public
     */
    public function arrayAsColModelSelectOptions($arr) {
        $s = ':(any);';
        $i = 0;
        $n = count($arr);
        foreach ($arr as $k => $v)
            $s .= $k . ':' . $v . (++$i < $n ? ';' : '');
        return $s;
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        $criteria->contactType = $this->contact_type;
        foreach ($searchparams as $key => $value) {
            if (is_object($value) && isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                Yii::log('SEARCH CRITERIA ITEM = ' . print_r($field, true));
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
        Yii::log('SEARCH CRITERIA= ' . print_r($criteria, true) . ' IN AllDomainsModel');
        return $criteria;
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param object $o
     *            Parameter description (if any) ...
     * @return mixed Return description (if any) ...
     * @access public
     */
    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        $dsm = $o->dsmState->shortNrpStatus;
        return array('A' => encode($o->name), 'B' => cleanString(encode($o->holder)), 'C' => $dsm,
            'D' => parseXmlDate($o->renewalDate));
    }

    public function verifyContactType($contact_type) {
        return (array_key_exists($contact_type, $this->domainCountForContact));
    }

    public function setSummary($domainCountForContact) {
        if (isset($domainCountForContact->results)) {
            if (is_array($domainCountForContact->results)) {
                foreach ($domainCountForContact->results as $key => $value) {
                    $this->domainCountForContact[$value->contactType] = $value->domainCount;
                }
            } else {
                $this->domainCountForContact[$domainCountForContact->results->contactType] = $domainCountForContact->results->domainCount;
            }
        }
    }

}
