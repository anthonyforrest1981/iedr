<?php

require_once("Response.php");

class RegistrantTransferSellRequestInfoDataResponse extends Response {

    private $id;
    private $domainName;
    private $holder;
    private $creationDate;
    private $completionDate;
    private $contact;

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }

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

    function getCreationDate() {
        return $this->creationDate;
    }

    function setCreationDate($creationDate) {
        $this->creationDate = $creationDate;
    }

    function getCompletionDate() {
        return $this->completionDate;
    }

    function setCompletionDate($completionDate) {
        $this->completionDate = $completionDate;
    }

    function getContact() {
        return $this->contact;
    }

    function setContact($contact) {
        $this->contact = $contact;
    }

}