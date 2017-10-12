<?php

class Domains_Creation_Details extends DynamicModelBase {
    public $toCreated = true;
    public $domainlist;
    public $accept_tnc;
    public $admin_contact_nic_1;
    public $admin_contact_nic_2;
    public $ownerType;
    public $isOwnerFromIreland;
    public $holder;
    public $nameservers = array();
    public $ipv4Addresses = array();
    public $ipv6Addresses = array();
    public $nameserversCount;
    public $maxRows;
    public $minRows;
    public $registration_period;
    public $remarks;
    public $tech_contact;
    public $charitycode;
    public $paymentType;
    public $cardtype;
    public $period;
    public $periodType;
    public $cardholder;
    public $creditcardno;
    public $exp_month;
    public $exp_year;
    public $cvn;

    protected $standardRules = array(
        array('ownerType,remarks,registration_period,isOwnerFromIreland','required'),
        array('holder', 'required', 'message' => 'Legal “owner” cannot be blank.'),
        array('domainlist,holder,registration_period,remarks,admin_contact_nic_1,admin_contact_nic_2,tech_contact,paymentType',
            'Utf8Validator'),
        array('admin_contact_nic_1,admin_contact_nic_2,tech_contact','NicHandleValidator'),
        array('accept_tnc,admin_contact_nic_1,domainlist,tech_contact','required'),
        array('accept_tnc','CheckboxOnValidator',
            'message' => 'You must confirm that you have read and agree to our terms and conditions'),
        array('admin_contact_nic_1,admin_contact_nic_2,tech_contact','filter','filter' => 'mb_strtoupper'),
        array('holder','length','max' => 255),
        array('remarks','length','max' => 10000),
        array('nameservers','NameserverListComplexValidator'),
        array('admin_contact_nic_1,admin_contact_nic_2','UniquenessValidator',
            'fields' => array('admin_contact_nic_1','admin_contact_nic_2'),'label' => 'admin contact'));
    protected $ccValidationRules; // setup in contructor
    protected $charityValidationRules = array(
        array('charitycode','required'),
        array('charitycode','length','max' => 20)
    );
    protected $paymentRules = array(
        array('paymentType', 'required')
    );

    public function __construct($domlist = null) {
        parent::__construct();
        $appConfig = Utility::getApplicationConfiguration();
        $this->maxRows = $appConfig->nameserversMaxCount;
        $this->minRows = $appConfig->nameserversMinCount;
        $this->ccValidationRules = CreditCardValidationRules::rules();
        $this->val_rules = $this->standardRules;
        $this->attr_labels = array(
            'domainlist' => 'Domains which are valid and available',
            'accept_tnc' => 'I accept the <a href="https://www.iedr.ie/registrations-terms-and-conditions/" target="_blank">terms and conditions</a>',
            'holder' => 'Who will be the legal “owner” of the domain?',
            'ownerType' => 'Is the legal “owner” a',
            'isOwnerFromIreland' => 'Are you based in Ireland?',
            'registration_period' => 'Registration Period',
            'charitycode' => 'Charity Number',
            'remarks' => 'Supporting Information',
            'admin_contact_nic_1' => 'Admin Contact 1',
            'admin_contact_nic_2' => 'Admin Contact 2',
            'tech_contact' => 'Technical Contact',
            'paymentType' => 'Payment Type');
        $this->attr_labels = array_merge($this->attr_labels, CreditCardFormSegmentModel::attributeLabels());
        $this->setDefaults($domlist);
    }

    public function setupRules($postData) {
        // val_rules is a strange concept from DynamicModelBase, should not be required here as this model does not
        // use addMixinModel(), but keeping it around to not break class contract of DynamicModelBase,
        // also default implementation of rules() method in DynamicModelBase uses val_rules

        // depending on payment type we validate fields from this payment type
        if ($this->isOwnerTypeCharity() &&  $this->isOwnerFromIreland) {
            $this->val_rules = array_merge($this->standardRules, $this->charityValidationRules);
        } else {
            $this->val_rules = array_merge($this->standardRules, $this->paymentRules);
            if ($this->paymentType === 'CC') {
                $this->val_rules = array_merge($this->val_rules, $this->ccValidationRules);
            }
            if ($this->isOwnerTypeCharity()) {
                $this->val_rules = array_merge($this->val_rules, $this->charityValidationRules);
            }
        }
    }

    public function setDefaults($domlist) {
        $this->domainlist = $domlist;
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
            Yii::log(print_r($e, true), 'error', 'Domains_Creation_Details::setDefaults()');
        }
    }

    public function setFromPOST($p) {
        parent::setFromPOST($p);

        // now clear / modify received values based on selected ownerType. The problem is that if user
        // selects Charity from Ireland, he get's implicit payment type of Charity, otherwise must still
        // provide payment method
        if ($this->isOwnerTypeCharity() && $this->isOwnerFromIreland) {
            $this->paymentType = 'CH';
        }
    }

    public function getRemarks() {
        if ($this->isOwnerTypeCharity() && !$this->isOwnerFromIreland) {
            return "CharityCode: {$this->charitycode}\n{$this->remarks}";
        } else {
            return $this->remarks;
        }
    }

    private function isOwnerTypeCharity() {
        return in_array($this->ownerType, getCharityOwnerTypeIds());
    }

}
