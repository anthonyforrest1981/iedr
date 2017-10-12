<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("ModifyCommandObject.php");

class ContactModifyCommand extends ModifyCommandObject {

    private $id;
    private $companyName;
    private $address;
    private $county;
    private $country;
    private $voice;
    private $fax;
    private $email;

    function ContactModifyCommand($id = '', $companyName = '', $address = '', $county = '',
                                  $country = '', $voice = '', $fax = '', $email = '') {
        parent::__construct();
        $this->id = $id;
        $this->companyName = $companyName;
        $this->address = $address;
        $this->county = $county;
        $this->country = $country;
        $this->voice = $voice;
        $this->fax = $fax;
        $this->email = $email;
    }

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
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
}
