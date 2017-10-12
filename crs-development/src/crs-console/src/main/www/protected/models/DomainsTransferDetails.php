<?php

class DomainsTransferDetails extends DynamicModelBase {
    public $toCreated = true;
    public $domain_name;
    public $authcode;
    public $authcode_failures = 0;
    public $retries;
    public $accept_tnc;
    public $admin_contact_nic_1;
    public $admin_contact_nic_2;
    public $nameservers = array();
    public $nameserversCount;
    public $ipv4Addresses = array();
    public $ipv6Addresses = array();
    public $maxRows;
    public $minRows;
    public $renewalPeriod;
    public $tech_contact;
    public $charitycode;
    public $paymentType;
    public $cardtype;
    public $rules;
    public $period;
    public $periodType;
    public $cardholder;
    public $creditcardno;
    public $exp_month;
    public $exp_year;
    public $cvn;
    public $charityPaymentForced = false;
    public $labels = array();
    public $errors;
    public $message_displayed;
    public $standardRules = array(array('accept_tnc,admin_contact_nic_1,authcode,tech_contact','required'),
        array('domain_name,authcode,renewalPeriod,admin_contact_nic_1,admin_contact_nic_2,tech_contact,paymentType',
            'Utf8Validator'),array('admin_contact_nic_1,admin_contact_nic_2,tech_contact','NicHandleValidator'),
        array('accept_tnc','CheckboxOnValidator',
            'message' => 'You must confirm that you have read and agree to our terms and conditions'),
        array('admin_contact_nic_1,admin_contact_nic_2,tech_contact','filter','filter' => 'mb_strtoupper'),
        array('domain_name','filter','filter' => 'mb_strtolower'),
        array('nameservers','NameserverListComplexValidator','dname' => 'domain_name'),
        array('admin_contact_nic_1,admin_contact_nic_2','UniquenessValidator',
            'fields' => array('admin_contact_nic_1','admin_contact_nic_2'),'label' => 'admin contact'));
    public $ccValidationRules;

    public function setupRules($what) {
        $copy = Utility::hideRestrictedFieldsInModel($what);
        Yii::log("Setting up validation rules for " . print_r($copy, true));
        Yii::log("Setting up validation rules for[domain_name] " . print_r($what['domain_name'], true));
        /* Payment */
        CRSDomainAppService_service::isCharity($this->charityPaymentForced, $this->errors, Yii::app()->user->authenticatedUser, $what['domain_name']);
        Yii::log("Setting up validation rules, charity = " . $this->charityPaymentForced);
        $this->val_rules = $this->standardRules;
        if (!$this->charityPaymentForced) {
            $this->val_rules[] = array('renewalPeriod, paymentType','required');
        }
        $this->val_rules[] = array('charityPaymentForced','safe');
        $this->val_rules[] = array('paymentType','safe');
        if (array_key_exists('paymentType', $what)) {
            switch ($what['paymentType']) {
                case 'CC':
                    $this->val_rules = array_merge($this->val_rules, $this->ccValidationRules);
                    $this->cardtype = $what['cardtype'];
                    $this->cardholder = $what['cardholder'];
                    $this->creditcardno = $what['creditcardno'];
                    $this->exp_month = $what['exp_month'];
                    $this->exp_year = $what['exp_year'];
                    $this->cvn = $what['cvn'];
                    break;
            }
        }
        $this->rules = $this->val_rules;
    }

    public function __construct($transferdata = null) {
        parent::__construct();
        $this->setDefaults($transferdata);
        if (isset($this->domain_name)) {
            CRSDomainAppService_service::isCharity($this->charityPaymentForced, $this->errors, Yii::app()->user->authenticatedUser, $this->domain_name);
        }
        $this->val_rules = $this->standardRules;
        $this->val_rules[] = array('paymentType','safe');
        $this->val_rules[] = array('charityPaymentForced','safe');
        if (!$this->charityPaymentForced) {
            $this->val_rules[] = array('renewalPeriod, paymentType','required');
            $this->ccValidationRules = CreditCardValidationRules::rules();
            $this->val_rules = array_merge($this->val_rules, $this->ccValidationRules);
        }
        $this->rules = $this->val_rules;
        $this->labels = array('domain_name' => 'Domain which is valid and available',
            'accept_tnc' => 'I accept the <a href="https://www.iedr.ie/registrations-terms-and-conditions/" target="_blank">terms and conditions</a>',
            'admin_contact_nic_1' => 'Admin Contact 1','admin_contact_nic_2' => 'Admin Contact 2',
            'renewalPeriod' => 'Renewal Period','remarks' => 'Remarks','tech_contact' => 'Technical Contact',
            'charitycode' => 'Charity Code','paymentType' => 'Payment Type');
        $this->labels = array_merge($this->labels, CreditCardFormSegmentModel::attributeLabels());
    }

    public function setFromPOST($p) {
        parent::setFromPOST($p);
        foreach ($p['nameservers'] as $i => $value) {
            $this->nameservers[$i] = $value;
        }
        foreach ($p['ipv4Addresses'] as $i => $value) {
            $this->ipv4Addresses[$i] = $value;
        }
        foreach ($p['ipv6Addresses'] as $i => $value) {
            $this->ipv6Addresses[$i] = $value;
        }
    }

    public function setDefaults($transferdata) {
        if (isset($transferdata['domain'])) {
            $this->domain_name = $transferdata['domain'];
        }
        $appConfig = Utility::getApplicationConfiguration();
        $this->maxRows = $appConfig->nameserversMaxCount;
        $this->minRows = $appConfig->nameserversMinCount;
        $this->retries = $appConfig->authCodeFailureLimit;
        $this->nameserversCount = $this->minRows;
        try {
            $rd = Yii::app()->user->resellerDefaults;
            $this->tech_contact = $rd->techContactId;
            if (is_array($rd->nameservers)) {
                $nameserversArray = $rd->nameservers;
            } else {
                $nameserversArray = array($rd->nameservers);
            }
            $this->nameservers = $nameserversArray;
            $this->nameserversCount = max(count($nameserversArray), $this->minRows);
        } catch (Exception $e) {
            Yii::log('No Resseler Defaults found', 'info', 'Domains_Creation_Details::setDefaults()');
        }
    }

    public function rules() {
        return $this->rules;
    }

    public function attributeLabels() {
        return $this->labels;
    }

}
