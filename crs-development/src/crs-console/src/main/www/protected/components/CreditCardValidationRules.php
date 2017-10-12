<?php

class CreditCardValidationRules {
    const CREDIT_CARD_HOLDER_PATTERN = "/^[a-z0-9àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿß [:punct:]]+$/ui";

    /**
     * Returns credit card rules, without payment checks and without any scenarios set
     */
    static function rules() {
        $year = date('y', time());
        return array(array('cardholder, creditcardno, exp_month, exp_year, cardtype, cvn','required'),
            array('cardholder, creditcardno, cardtype','Utf8Validator'),
            array('cardholder','match','pattern' => CreditCardValidationRules::CREDIT_CARD_HOLDER_PATTERN),
            array('creditcardno','CreditCardValidator'),
            array('exp_month','numerical','integerOnly' => true,'min' => 1,'max' => 12),
            array('exp_year','numerical','integerOnly' => true,'min' => $year,'max' => ($year + 15)),
            array('cvn','match','pattern' => '/^[0-9]{3}$/ui','message' => '{attribute} must be a three digit number'));
    }

}
