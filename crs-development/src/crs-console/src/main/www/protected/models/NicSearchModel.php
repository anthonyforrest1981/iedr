<?php

class NicSearchModel extends GridModelBase {
    public $columns;
    public $countryList;
    public $countyList;
    public $defaultSortColumn = 'A';
    public $fromOthers = '0';

    public function __construct($from = '0') {
        $this->fromOthers = $from;
        parent::__construct();
        // BUG request too large !!!
        // $this->countryList = getCountryOptions();
        // $this->countyList = getCounties();
        $this->columns = array(
            'A' => array('resultfield' => 'nicHandleId','criteriafield' => 'nicHandleId','label' => 'Account Number',
                'width' => 70,'link' => ($from == '0') ? ('nichandles/viewnichandle') : null,'type' => 'string'),
            'B' => array('resultfield' => 'name','criteriafield' => 'name','label' => 'Name','width' => 90,
                'type' => 'string'),
            'C' => array('resultfield' => 'companyName','criteriafield' => 'companyName','label' => 'Company',
                'width' => 90,'type' => 'string'),
            'D' => array('resultfield' => 'address','criteriafield' => 'address','label' => 'Address','width' => 160,
                'type' => 'string'),
            'E' => array('resultfield' => 'county','criteriafield' => 'countyName','label' => 'County','width' => 60,
                'type' => 'string'),
            'F' => array('resultfield' => 'country','criteriafield' => 'countryName','label' => 'Country','width' => 40,
                'type' => 'string'),
            'G' => array('resultfield' => 'email','criteriafield' => 'email','label' => 'Email','width' => 100,
                'type' => 'email'),
            'H' => array('resultfield' => 'phones','criteriafield' => 'phone','label' => 'Phone','width' => 100,
                'type' => 'string'));
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('A' => encode($o->nicHandleId),'B' => encode($o->name),'C' => encode($o->companyName),
            'D' => $this->replaceLineBreaksWithCommas(encode($o->address)),'E' => encode($o->county->name),
            'F' => encode($o->country->name),'G' => encode($o->email),'H' => isset($o->phones) ? encode($o->phones) : '');
    }

    public function replaceLineBreaksWithCommas($s) {
        return preg_replace(array("/\r/u","/\n/u",'/&#10;/u','/&#13;/u','/[%,]+/u','/,[, ]+/u'), ',', $s);
    }

    function getReturningDataNames() {
        return array_merge(parent::getReturningDataNames(), array('mode','nictype','nic'));
    }

    public function getSearchBase() {
        $criteria = new CRSNicHandleAppService_nicHandleSearchCriteriaVO();
        $criteria->accountNumber = Yii::app()->user->id;
        if (Utility::isDirect()) {
            $criteria->creator = Yii::app()->user->authenticatedUser->username;
        }
        return $criteria;
    }

}
