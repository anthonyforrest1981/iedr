<?php

require_once("Response.php");

class RegistrantTransferSellRequestDataResponse extends Response {

    private $id;
    private $domainName;
    private $holder;
    private $creationDate;
    private $completionDate;

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

}