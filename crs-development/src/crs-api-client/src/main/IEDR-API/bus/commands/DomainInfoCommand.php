<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("InfoCommandObject.php");

class DomainInfoCommand extends InfoCommandObject {

    private $name;
    private $authCode;
    private $authCodeForceGeneration;
    private $fillNsAddr6;

    function DomainInfoCommand($name = '') {
        parent::__construct();
        $this->name = $name;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getAuthCode() {
        return $this->authCode;
    }

    function setAuthCode($value) {
        $this->authCode = $value;
    }

    function getAuthCodeForceGeneration() {
        return $this->authCodeForceGeneration;
    }

    function setAuthCodeForceGeneration($value) {
        $this->authCodeForceGeneration = $value;
    }

    function setFillNsAddr6($fillNsAddr6) {
        $this->fillNsAddr6 = $fillNsAddr6;
    }

    function fillNsAddr6() {
        return $this->fillNsAddr6;
    }
}
