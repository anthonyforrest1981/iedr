<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class DomainAppDataResponse extends Response {

    private $name;
    private $appNumber;
    private $appDate;
    private $exDate;

    function DomainAppData($name = '', $appNumber = '', $appDate = '', $exDate = '') {
        $this->name = $name;
        $this->appNumber = $appNumber;
        $this->appDate = $appDate;
        $this->exDate = $exDate;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getAppNumber() {
        return $this->appNumber;
    }

    function setAppNumber($appNumber) {
        $this->appNumber = $appNumber;
    }

    function getAppDate() {
        return $this->appDate;
    }

    function setAppDate($appDate) {
        $this->appDate = $appDate;
    }

    function getExDate() {
        return $this->exDate;
    }

    function setExDate($exDate) {
        $this->exDate = $exDate;
    }

}
