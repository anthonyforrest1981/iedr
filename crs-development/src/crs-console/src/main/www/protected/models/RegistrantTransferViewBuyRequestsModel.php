<?php

class RegistrantTransferViewBuyRequestsModel extends GridModelBase {

    const DEFAULT_DAYS = 30;

    public $days = self::DEFAULT_DAYS;
    public $columns = array();
    public $defaultSortColumn = 'A';

    public function __construct() {
        $this->columns = array(
            'A' => array('resultfield' => 'buyId', 'criteriafield' => null,
                'label' => 'Request', 'width' => 35, 'type' => 'int',
                'link' => 'registranttransfer/viewbuyrequest'),
            'B' => array('resultfield' => 'domainName', 'criteriafield' => 'domainName',
                'label' => 'Domain Name', 'width' => 90, 'type' => 'string'),
            'C' => array('resultfield' => 'domainHolder', 'criteriafield' => 'domainHolder',
                'label' => 'Holder', 'width' => 90, 'type' => 'string'),
            'D' => array('resultfield' => 'buyStatus', 'criteriafield' => 'status',
                'label' => 'Admin Status', 'width' => 60, 'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getBuyRequestStatuses())),
            'E' => array('resultfield' => 'buyCreationDate', 'criteriafield' => null,
                'label' => 'Expiry Date', 'width' => 40, 'type' => 'date'));
    }

    public function rules() {
        return array(
            array('days', 'filter', 'filter' => array('Utility', 'mb_trim')),
            array('days', 'required'),
            array('days', 'numerical', 'integerOnly' => true, 'min' => 1, 'max' => 999),
            array('days', 'filter', 'filter' => array($this, 'resetDaysOnError')));
    }

    public function attributeLabels() {
        return array('days' => 'The number of days');
    }

    public function resetDaysOnError($days) {
        return $this->getErrors('days') ? self::DEFAULT_DAYS : $days;
    }

    public function getSearchBase() {
        $criteria = new CRSSecondaryMarketAppService_buyRequestSearchCriteriaVO();
        $criteria->creationDateFrom =
            date('Y-m-d', strtotime(date('Y-m-d') . '-' . $this->days . 'days'));
        return $criteria;
    }

    public function addResults($request) {
        return array(
            'A' => $request->id,
            'B' => encode($request->domainName),
            'C' => encode($request->domainHolder),
            'D' => $request->status,
            'E' => Utility::getBuyRequestExpirationDate($request));
    }

}

