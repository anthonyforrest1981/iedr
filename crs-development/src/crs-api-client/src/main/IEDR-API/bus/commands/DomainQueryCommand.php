<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("QueryCommandObject.php");
include_once("TransferQuery.php");

class DomainQueryCommand extends QueryCommandObject {

    private $contact;
    private $contactType;
    private $renewalDateRangeStart;
    private $renewalDateRangeEnd;
    private $transfer;
    private $domainStatus;
    private $page;
    private $attachReservationInfo;
    private $authCode;
    private $authCodeForceGeneration;

    function DomainQueryCommand($contact = '', $contactType = '', $renewalDateRangeStart = '',
                                $renewalDateRangeEnd = '', $transfer = NULL, $status = '',
                                $page = 1) {
        parent::__construct();
        $this->contact = $contact;
        $this->contactType = $contactType;
        $this->renewalDateRangeStart = $renewalDateRangeStart;
        $this->renewalDateRangeEnd = $renewalDateRangeEnd;
        $this->transfer = $transfer;
        $this->domainStatus = $status;
        $this->page = $page;
    }

    public function getContact() {
        return $this->contact;
    }

    public function setContact($contact) {
        $this->contact = $contact;
    }

    public function getContactType() {
        return $this->contactType;
    }

    public function setContactType($contactType) {
        $this->contactType = $contactType;
    }

    public function getPage() {
        return $this->page;
    }

    public function setPage($page) {
        $this->page = $page;
    }

    public function getRenewalDateRangeEnd() {
        return $this->renewalDateRangeEnd;
    }

    public function setRenewalDateRangeEnd($renewalDateRangeEnd) {
        $this->renewalDateRangeEnd = $renewalDateRangeEnd;
    }

    public function getRenewalDateRangeStart() {
        return $this->renewalDateRangeStart;
    }

    public function setRenewalDateRangeStart($renewalDateRangeStart) {
        $this->renewalDateRangeStart = $renewalDateRangeStart;
    }

    public function getDomainStatus() {
        return $this->domainStatus;
    }

    public function setDomainStatus($status) {
        $this->domainStatus = $status;
    }

    public function getTransfer() {
        return $this->transfer;
    }

    public function setTransfer($transfer) {
        $this->transfer = $transfer;
    }

    public function setAttachReservationInfo($flag) {
        $this->attachReservationInfo = $flag;
    }

    public function isAttachReservationInfo() {
        return $this->attachReservationInfo;
    }

    public function setAuthCode($flag) {
        $this->authCode = $flag;
    }

    public function isAuthCode() {
        return $this->authCode;
    }

    public function setAuthCodeForceGeneration($flag) {
        $this->authCodeForceGeneration = $flag;
    }

    public function isAuthCodeForceGeneration() {
        return $this->authCodeForceGeneration;
    }
}
