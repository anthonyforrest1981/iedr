<?php

class CurrentRenewalsModel extends GridModelBase {
    public $returnurl = '';
    public $renewalDateType;
    public $productSummaries = array();
    public $defaultSortColumn = 'PK';

    public function __construct() {
        parent::__construct();
        $this->renewalDateType = 'RENEWAL_THIS_MONTH';
        $this->productSummaries = Utility::getProductSummaries('REN');
        $this->columns = array(
            'PK' => array('label' => 'Domain','width' => 60,'link' => 'domains/viewdomain',
                'resultfield' => 'domainName','criteriafield' => 'domainName','type' => 'string'),
            'H' => array('label' => 'Periods','width' => 35,'title' => 'false'),
            'C' => array('label' => 'Holder','resultfield' => 'holder','width' => 50,'type' => 'string'),
            'D' => array('label' => 'Registration','width' => 20,'resultfield' => 'registrationDate','type' => 'string'),
            'K' => array('label' => 'Renewal','resultfield' => 'renewalDate','width' => 20,'type' => 'string'));
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array(
            'PK' => encode($o->name),
            'H' => Utility::createSelect(encode($o->name), $this->productSummaries),
            'C' => cleanString(encode($o->holder)),
            'D' => parseXmlDate($o->registrationDate),
            'K' => parseXmlDate($o->renewalDate));
    }

    public function getSearchBase() {
        return new CRSDomainAppService_extendedDomainSearchCriteriaVO();
    }

    public function getSort($sortparams) {
        $sort = parent::getSort($sortparams);
        return $sort;
    }

}
