<?php

class RegistrantTransferViewSellRequestsModel extends GridModelBase {

    public $columns = array();
    public $defaultSortColumn = 'A';

    public function __construct() {
        $this->columns = array(
            'A' => array('resultfield' => 'sellId', 'criteriafield' => null,
                'label' => 'Request', 'width' => 35, 'type' => 'int'),
            'B' => array('resultfield' => 'domainName', 'criteriafield' => 'domainName',
                'label' => 'Domain Name', 'width' => 90, 'type' => 'string'),
            'C' => array('resultfield' => 'domainHolder', 'criteriafield' => 'domainHolder',
                'label' => 'Holder', 'width' => 90, 'type' => 'string'),
            'D' => array('resultfield' => 'sellCreationDate', 'criteriafield' => null,
                'label' => 'Completion Date', 'width' => 40, 'type' => 'date'));
    }

    public function getSearchBase() {
        return new CRSSecondaryMarketAppService_sellRequestSearchCriteriaVO();
    }

    public function addResults($request) {
        return array(
            'A' => $request->id,
            'B' => encode($request->buyRequest->domainName),
            'C' => encode($request->buyRequest->domainHolder),
            'D' => $this->getCompletionDate($request));
    }

    private function getCompletionDate($request) {
        return Utility::getSellRequestCompletionDate($request->creationDate);
    }

}

