<?php

require_once("CreateCommandObject.php");

class RegistrantTransferSellRequestCreateCommand extends CreateCommandObject {

    private $domainName;
    private $authcode;
    private $creditCard;

    function getDomainName() {
        return $this->domainName;
    }

    function setDomainName($domainName) {
        $this->domainName = $domainName;
    }

    function getAuthcode() {
        return $this->authcode;
    }

    function setAuthcode($authcode) {
        $this->authcode = $authcode;
    }

    function getCreditCard() {
        return $this->creditCard;
    }

    function setCreditCard($creditCard) {
        $this->creditCard = $creditCard;
    }

}