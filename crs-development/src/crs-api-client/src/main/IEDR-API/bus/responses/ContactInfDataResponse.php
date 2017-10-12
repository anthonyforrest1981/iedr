<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class ContactInfDataResponse extends Response {

    private $id;
    private $status;
    private $name;
    private $companyName;
    private $address;
    private $county;
    private $country;
    private $voice;
    private $fax;
    private $email;
    private $account;
    private $crDate;

    function ContactInfData($id = '', $status = '', $name = '', $companyName = '', $address = '',
                            $county = '', $country = '', $voice = '', $fax = '', $email = '',
                            $account = '', $crDate = '') {

        $this->id = $id;
        $this->status = $status;
        $this->name = $name;
        $this->companyName = $companyName;
        $this->address = $address;
        $this->county = $county;
        $this->country = $country;
        $this->voice = $voice;
        $this->fax = $fax;
        $this->email = $email;
        $this->account = $account;
        $this->crDate = $crDate;
    }

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getStatus() {
        return $this->status;
    }

    function setStatus($status) {
        $this->status = $status;
    }

    function getCompanyName() {
        return $this->companyName;
    }

    function setCompanyName($companyName) {
        $this->companyName = $companyName;
    }

    function getAddress() {
        return $this->address;
    }

    function setAddress($address) {
        $this->address = $address;
    }

    function getCounty() {
        return $this->county;
    }

    function setCounty($county) {
        $this->county = $county;
    }

    function getCountry() {
        return $this->country;
    }

    function setCountry($country) {
        $this->country = $country;
    }

    function getVoice() {
        return $this->voice;
    }

    function setVoice($voice) {
        $this->voice = $voice;
    }

    function getFax() {
        return $this->fax;
    }

    function setFax($fax) {
        $this->fax = $fax;
    }

    function getEmail() {
        return $this->email;
    }

    function setEmail($email) {
        $this->email = $email;
    }

    function getAccount() {
        return $this->account;
    }

    function setAccount($account) {
        $this->account = $account;
    }

    function getCrDate() {
        return $this->crDate;
    }

    function setCrDate($crDate) {
        $this->crDate = $crDate;
    }
}
