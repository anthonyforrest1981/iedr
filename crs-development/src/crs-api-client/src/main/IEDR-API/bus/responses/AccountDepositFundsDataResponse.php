<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class AccountDepositFundsDataResponse extends Response {

    private $oldValue;
    private $newValue;
    private $transDate;

    function AccountDepositFundsData($oldValue = '', $newValue = '', $transDate = '') {
        $this->oldValue = $oldValue;
        $this->newValue = $newValue;
        $this->transDate = $transDate;
    }

    function getOldValue() {
        return $this->oldValue;
    }

    function setOldValue($oldValue) {
        $this->oldValue = $oldValue;
    }

    function getNewValue() {
        return $this->newValue;
    }

    function setNewValue($newValue) {
        $this->newValue = $newValue;
    }

    function getTransDate() {
        return $this->transDate;
    }

    function setTransDate($transDate) {
        $this->transDate = $transDate;
    }
}
