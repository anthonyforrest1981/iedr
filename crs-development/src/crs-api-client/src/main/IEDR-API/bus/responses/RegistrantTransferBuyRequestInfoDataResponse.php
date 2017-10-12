<?php

require_once("Response.php");

class RegistrantTransferBuyRequestInfoDataResponse extends Response {

    private $id;
    private $domainName;
    private $holder;
    private $crDate;
    private $contact;
    private $status;

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

    function getCrDate() {
        return $this->crDate;
    }

    function setCrDate($crDate) {
        $this->crDate = $crDate;
    }

    function getContact() {
        return $this->contact;
    }

    function setContact($contact) {
        $this->contact = $contact;
    }

    function getStatus() {
        return $this->status;
    }

    function setStatus($status) {
        $this->status = $status;
    }

}
