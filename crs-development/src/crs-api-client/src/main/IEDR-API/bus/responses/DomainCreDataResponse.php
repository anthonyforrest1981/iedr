<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class DomainCreDataResponse extends Response {

    private $name;
    private $creDate;
    private $exDate;

    function DomainCreData($name = '', $creDate = '', $exDate = '') {
        $this->name = $name;
        $this->creDate = $creDate;
        $this->exDate = $exDate;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getCreDate() {
        return $this->creDate;
    }

    function setCreDate($creDate) {
        $this->creDate = $creDate;
    }

    function getExDate() {
        return $this->exDate;
    }

    function setExDate($exDate) {
        $this->exDate = $exDate;
    }

}
