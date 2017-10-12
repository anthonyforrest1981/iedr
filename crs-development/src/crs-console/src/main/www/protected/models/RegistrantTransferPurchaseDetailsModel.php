<?php

class RegistrantTransferPurchaseDetailsModel extends ReturningModelBase {
    public $domainName;
    public $domainHolder;
    public $ownerType;
    public $isOwnerFromIreland;
    public $paymentType;
    public $cardtype;
    public $cardholder;
    public $creditcardno;
    public $exp_month;
    public $exp_year;
    public $cvn;
    public $remarks;
    public $acceptTnc;
    public $acceptDisclaimer;

    public $nicHandleDetails;

    public $payment;

    function __construct($domainName) {
        parent::__construct();
        $this->nicHandleDetails = new Nichandle_Details();
        $this->payment = Utility::getRequestPrice(CRSPaymentAppService_operationType::_BUY_REQUEST);
        $this->domainName = $domainName;
    }

    public function rules() {
        $rules = array(
            array('ownerType, remarks, paymentType, acceptTnc', 'required'),
            array('domainHolder', 'required', 'message' => 'Legal “owner” cannot be blank.'),
            array('domainName, domainHolder, remarks, paymentType', 'Utf8Validator'),
            array('domainHolder', 'length', 'max' => 255),
            array('remarks', 'length', 'max' => 10000),
            array('acceptTnc', 'CheckboxOnValidator',
                'message' => 'You must confirm that you have read and agree to our terms and conditions'),
            array('acceptDisclaimer', 'CheckboxOnValidator',
                'message' => 'You must confirm that you have read and agree to the Secondary Market Acknowledgement and Disclaimer'),
        );
        foreach (CreditCardValidationRules::rules() as $rule) {
            $rule['on'] = 'ccPayment';
            array_push($rules, $rule);
        }
        return $rules;
    }

    public function afterValidate() {
        $this->nicHandleDetails->validate();
    }

    public function attributeLabels() {
        $labels = array(
            'domainName' => 'Domain Name',
            'domainHolder' => 'Who will be the legal “owner” of the domain?',
            'ownerType' => 'Is the legal “owner” a',
            'isOwnerFromIreland' => 'Are you based in Ireland?',
            'remarks' => 'Remarks',
            'acceptTnc' => 'I accept the <a href="https://www.iedr.ie/registrations-terms-and-conditions/" target="_blank">terms and conditions</a>',
            'acceptDisclaimer' => 'I accept the <a href="https://www.iedr.ie/secondary-market-acknowledgement-and-disclaimer/" target="_blank">Secondary Market Acknowledgement and Disclaimer</a>');
        $labels = array_merge($labels, CreditCardFormSegmentModel::attributeLabels());
        return $labels;
    }

    public function setAttributes($attributes) {
        if (isset($attributes['paymentType']) && $attributes['paymentType'] == 'CC') {
            $this->scenario = 'ccPayment';
        }
        parent::setAttributes($attributes);
    }

    public function getNewNicHandle() {
        return $this->nicHandleDetails->getAsObject();
    }

    public function getCreditCard() {
        if ($this->paymentType == CRSPaymentAppService_paymentMethod::_CC) {
            return CreditCardFormSegmentModel::asWSAPICreditCardObject($this);
        } else {
            return null;
        }
    }

}
