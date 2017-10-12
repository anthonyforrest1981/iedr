<?php

/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class CreditCard {

    static public $PAY_CARD_TYPES = array("VISA", "MC", "LASER");
    private $cardHolder;
    private $cardNumber;
    private $expiryDate;
    private $cardType;
    private $cvnNumber;
    private $cvnPresInd;

    function getCardHolder() {
        return $this->cardHolder;
    }

    function setCardHolder($cardHolder) {
        $this->cardHolder = $cardHolder;
    }

    function getCardNumber() {
        return $this->cardNumber;
    }

    function setCardNumber($cardNumber) {
        $this->cardNumber = $cardNumber;
    }

    function getExpiryDate() {
        return $this->expiryDate;
    }

    function setExpiryDate($expiryDate) {
        $this->expiryDate = $expiryDate;
    }

    function getCardType() {
        return $this->cardType;
    }

    function setCardType($cardType) {
        $this->cardType = $cardType;
    }

    function getCvnNumber() {
        return $this->cvnNumber;
    }

    function setCvnNumber($cvnNumber) {
        $this->cvnNumber = $cvnNumber;
        $this->cvnPresInd = 1;
    }

    function getCvnPresInd() {
        return $this->cvnPresInd;
    }

}
