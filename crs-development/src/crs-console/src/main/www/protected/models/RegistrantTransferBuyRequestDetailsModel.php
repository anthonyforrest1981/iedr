<?php

class RegistrantTransferBuyRequestDetailsModel extends CFormModel {

    public $id;

    public $domainName;
    public $domainHolder;
    public $remarks;

    public $nicHandleDetails;
    public $adminCountryName;
    public $adminCountyName;

    public $creator;
    public $creationDate;
    public $status;
    public $checkedOut;
    public $changeDate;
    public $expiryDate;
    public $hostmasterRemarks;

    public $uploadEnabled;
    public $requestEditable;

    function __construct() {
        parent::__construct();
        $this->nicHandleDetails = new Nichandle_Details();
    }

    public function rules() {
        return array(array('id', 'required'),
            array('id', 'numerical', 'integerOnly' => true, 'min' => 1),
            array('id', 'filter', 'filter' => 'intval', 'skipOnError' => true),
            array('requestEditable', 'safe'),
            array('domainHolder, remarks', 'required', 'on' => 'editing'),
            array('domainHolder, remarks', 'Utf8Validator', 'on' => 'editing'),
            array('domainHolder', 'length', 'max' => 255, 'on' => 'editing'),
            array('remarks', 'length', 'max' => 10000, 'on' => 'editing'));
    }

    public function afterValidate() {
        if ($this->scenario == 'editing') {
            $this->nicHandleDetails->validate();
        }
    }

    public function attributeLabels() {
        return array('id' => 'Request ID',
            'domainName' => 'Domain Name',
            'domainHolder' => 'Domain Holder',
            'remarks' => 'Remarks',
            'creator' => 'Creator',
            'creationDate' => 'Created',
            'status' => 'Admin Status',
            'changeDate' => 'Change Date',
            'expiryDate' => 'Expiry Date',
            'hostmasterRemarks' => 'Hostmasters\' Remarks');
    }

    public function getNewNicHandle() {
        return $this->nicHandleDetails->getAsObject();
    }

    public function fillFromObject($buyRequest, $editing) {
        $this->id = $buyRequest->id;
        $this->domainName = $buyRequest->domainName;
        $this->domainHolder = $buyRequest->domainHolder;
        $this->remarks = $buyRequest->remark;

        $this->creator = $buyRequest->creatorNH;
        $this->creationDate = parseXmlDate($buyRequest->creationDate);
        $this->status = $buyRequest->status;
        $this->checkedOut = $buyRequest->checkedOut;
        $this->changeDate = parseXmlDate($buyRequest->changeDate);
        $this->expiryDate = Utility::getBuyRequestExpirationDate($buyRequest);
        $this->hostmasterRemarks = $buyRequest->hostmasterRemark;
        $this->fillNicHandleDetails($buyRequest);

        $this->adminCountryName = $buyRequest->adminCountry->name;
        $this->adminCountyName = $buyRequest->adminCounty->name;

        $requestPassed = $this->status == CRSSecondaryMarketAppService_buyRequestStatus::_PASSED;
        $this->uploadEnabled = !$requestPassed;
        $this->requestEditable = !$this->checkedOut && !$requestPassed;

        if ($this->requestEditable && $editing) {
            $this->remarks = null;
        }

        if (!$editing) {
            $this->setErrorsFromFailureReasons($buyRequest);
        }
    }

    private function fillNicHandleDetails($buyRequest) {
        $this->nicHandleDetails->name = $buyRequest->adminName;
        if (isset($buyRequest->adminCompanyName)) {
            $this->nicHandleDetails->companyName = $buyRequest->adminCompanyName;
        }
        $this->nicHandleDetails->address = $buyRequest->adminAddress;
        $this->nicHandleDetails->phones = $buyRequest->phones;
        if (isset($buyRequest->faxes)) {
            $this->nicHandleDetails->faxes = $buyRequest->faxes;
        }
        $this->nicHandleDetails->countyId = $buyRequest->adminCounty->id;
        $this->nicHandleDetails->countryId = $buyRequest->adminCountry->id;
        $this->nicHandleDetails->email = $buyRequest->adminEmail;
    }

    private function setErrorsFromFailureReasons($buyRequest) {
        if (isset($buyRequest->domainNameFR)) {
            $this->addError('domainName', $buyRequest->domainNameFR);
        }
        if (isset($buyRequest->domainHolderFR)) {
            $this->addError('domainHolder', $buyRequest->domainHolderFR);
        }
        if (isset($buyRequest->adminNameFR)) {
            $this->nicHandleDetails->addError('name', $buyRequest->adminNameFR);
        }
        if (isset($buyRequest->adminEmailFR)) {
            $this->nicHandleDetails->addError('email', $buyRequest->adminEmailFR);
        }
        if (isset($buyRequest->adminCompanyNameFR)) {
            $this->nicHandleDetails->addError('companyName', $buyRequest->adminCompanyNameFR);
        }
        if (isset($buyRequest->adminAddressFR)) {
            $this->nicHandleDetails->addError('address', $buyRequest->adminAddressFR);
        }
        if (isset($buyRequest->adminCountryFR)) {
            $this->addError('adminCountryName', $buyRequest->adminCountryFR);
        }
        if (isset($buyRequest->adminCountyFR)) {
            $this->addError('adminCountyName', $buyRequest->adminCountyFR);
        }
        if (isset($buyRequest->phonesFR)) {
            $this->nicHandleDetails->addError('phones', $buyRequest->phonesFR);
        }
        if (isset($buyRequest->faxesFR)) {
            $this->nicHandleDetails->addError('faxes', $buyRequest->faxesFR);
        }
    }

}