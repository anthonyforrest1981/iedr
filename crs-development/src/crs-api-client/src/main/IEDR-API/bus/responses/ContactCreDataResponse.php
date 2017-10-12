<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class ContactCreDataResponse extends Response {

    private $id;
    private $crDate;

    function ContactCreData($id = '', $crDate = '') {
        $this->id = $id;
        $this->crDate = $crDate;
    }

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }

    function getCrDate() {
        return $this->crDate;
    }

    function setCrDate($crDate) {
        $this->crDate = $crDate;
    }
}
