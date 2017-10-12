<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class AccountChkDepositDataResponse extends Response {

    private $actualBalance;
    private $availableBalance;
    private $transDate;

    function setAvailableBalance($value) {
        $this->availableBalance = $value;
    }

    function getActualBalance() {
        return $this->actualBalance;
    }

    function setActualBalance($value) {
        $this->actualBalance = $value;
    }

    function getAvaiableBalance() {
        return $this->availableBalance;
    }

    function getTransDate() {
        return $this->transDate;
    }

    function setTransDate($transDate) {
        $this->transDate = $transDate;
    }
}
