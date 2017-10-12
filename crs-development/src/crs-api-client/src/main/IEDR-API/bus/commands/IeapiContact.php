<?php

class IeapiContact {

    private $name;
    private $companyName;
    private $address;
    private $county;
    private $country;
    private $voice;
    private $fax;
    private $email;

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
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
