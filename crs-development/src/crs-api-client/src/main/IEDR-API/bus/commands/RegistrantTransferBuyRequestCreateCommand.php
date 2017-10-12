<?php
require_once("CreateCommandObject.php");

class RegistrantTransferBuyRequestCreateCommand extends CreateCommandObject {

    private $domainName;
    private $holder;
    private $contact;
    private $creditCard;

    function getDomainName() {
        return $this->domainName;
    }

    function setDomainName($domainName) {
        $this->domainName = $domainName;
    }

    function getHolder() {
        return $this->holder;
    }

    function setHolder($holder) {
        $this->holder = $holder;
    }

    function getContact() {
        return $this->contact;
    }

    function setContact($contact) {
        $this->contact = $contact;
    }

    function getCreditCard() {
        return $this->creditCard;
    }

    function setCreditCard($creditCard) {
        $this->creditCard = $creditCard;
    }

}
