<?php

class CurrentRenewalsSelectionModel extends DynamicDomainListModel {
    public $command;
    public $returnurl;
    public $needCreditCard;
    public $renewalDateType;
    public $period;
    public $renewalMode;
    public $defaultPeriods = array();

    public function __construct($isVoluntary = false) {
        parent::__construct();
        $this->val_rules = array(array('domainlist,command,returnurl,renewalDateType','required'),
            array('needCreditCard','boolean'));
        $this->needCreditCard = 0;
        $this->renewalDateType = 'CURRENT';
        if ($isVoluntary == false) {
            $this->addMixinModel(new CreditCardFormSegmentModel());
            /*
             * Just to make asterisk available
             */
            $this->val_rules = array_merge($this->val_rules, array(
                array('cardholder, creditcardno, cardtype, exp_month, exp_year, cvn', 'required')));
        }
    }

    public function setFromPOST($p) {
        parent::setFromPOST($p);
        if ($this->needCreditCard == 1)
            $this->setScenario(CreditCardFormSegmentModel::$without_amount_scenario);
    }

}
