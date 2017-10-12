<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("PayCommandObject.php");

class AccountDepositFundsCommand extends PayCommandObject {

    private $value;
    private $creditCard;

    function getValue() {
        return $this->value;
    }

    function setValue($value) {
        $this->value = $value;
    }

    function getCreditCard() {
        return $this->creditCard;
    }

    function setCreditCard($creditCard) {
        $this->creditCard = $creditCard;
    }
}
