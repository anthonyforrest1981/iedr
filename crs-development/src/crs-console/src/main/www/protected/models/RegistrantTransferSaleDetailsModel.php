<?php

class RegistrantTransferSaleDetailsModel extends FormModelBase {

    public $domainName;
    public $authcode;
    public $paymentType;
    public $cardtype;
    public $cardholder;
    public $creditcardno;
    public $exp_month;
    public $exp_year;
    public $cvn;
    public $acceptTnc;

    public $payment;

    public $submit = false;
    public $authcodeCorrect = true;

    function __construct($domainName) {
        parent::__construct();
        $this->payment = Utility::getRequestPrice(CRSPaymentAppService_operationType::_SELL_REQUEST);
        $this->domainName = $domainName;
    }

    public function rules() {
        $rules = array(
            array('authcode,paymentType,acceptTnc', 'required'),
            array('submit', 'safe'),
            array('domainName,authcode,paymentType', 'Utf8Validator'),
            array('acceptTnc', 'CheckboxOnValidator',
                'message' => 'You must confirm that you have read and agree to our terms and conditions'));
        foreach (CreditCardValidationRules::rules() as $rule) {
            $rule['on'] = 'ccPayment';
            array_push($rules, $rule);
        }
        return $rules;
    }

    public function setAttributes($attributes) {
        if (isset($attributes['paymentType']) && $attributes['paymentType'] == 'CC') {
            $this->scenario = 'ccPayment';
        }
        parent::setAttributes($attributes);
    }

    public function attributeLabels() {
        $labels = array('domainName' => 'Domain Name', 'authcode' => 'Authcode',
            'acceptTnc' => 'I accept the <a href="https://www.iedr.ie/registrations-terms-and-conditions/" target="_blank">terms and conditions</a>');
        $labels = array_merge($labels, CreditCardFormSegmentModel::attributeLabels());
        return $labels;
    }

}