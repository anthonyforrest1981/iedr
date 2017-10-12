<?php

class DeletedDomainReportModel extends GridModelBase {
    public $columns = array();
    public $defaultSortColumn = 'A';

    public function __construct() {
        $this->columns = array(
            'A' => array('label' => 'Domain', 'width' => 32, 'resultfield' => 'name',
                'criteriafield' => 'domainName', 'sortby' => 'name'),
            'B' => array('label' => 'Holder', 'resultfield' => 'domainHolder', 'sortby' => 'holder', 'width' => 32),
            'C' => array('label' => 'Registration Date', 'resultfield' => 'registrationDate',
                'width' => 12),
            'D' => array('label' => 'Renewal Date', 'resultfield' => 'renewalDate',
                'width' => 12),
            'E' => array('label' => 'Deletion Date', 'resultfield' => 'deletionDate',
                'width' => 12));
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('A' => encode($o->name), 'B' => encode($o->holder), 'C' => parseXmlDate($o->registrationDate),
            'D' => parseXmlDate($o->renewalDate), 'E' => parseXmlDate($o->deletionDate));
    }

    public function getSearchBase() {
        return new CRSDomainAppService_deletedDomainSearchCriteriaVO();
    }

}
?>
