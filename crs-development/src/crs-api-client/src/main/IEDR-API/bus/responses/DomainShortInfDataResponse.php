<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class DomainShortInfDataResponse {

    private $name;
    private $regDate;
    private $renDate;
    private $transDate;
    private $suspendDate;
    private $deleteDate;
    private $lockDate;
    private $lockRenewDate;
    private $status;
    private $reservationPending;

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

    function getRegDate() {
        return $this->regDate;
    }

    function setRegDate($regDate) {
        $this->regDate = $regDate;
    }

    function getRenDate() {
        return $this->renDate;
    }

    function setRenDate($renDate) {
        $this->renDate = $renDate;
    }

    function getSuspendDate() {
        return $this->suspendDate;
    }

    function setSuspendDate($suspDate) {
        $this->suspendDate = $suspDate;
    }

    function getTransDate() {
        return $this->transDate;
    }

    function setTransDate($transDate) {
        $this->transDate = $transDate;
    }

    function getDeleteDate() {
        return $this->deleteDate;
    }

    function setDeleteDate($delDate) {
        return $this->deleteDate = $delDate;
    }

    function getLockDate() {
        return $this->lockDate;
    }

    function setLockDate($lockDate) {
        return $this->lockDate = $lockDate;
    }

    function getLockRenewDate() {
        return $this->lockRenewDate;
    }

    function setLockRenewDate($lockRenewDate) {
        return $this->lockRenewDate = $lockRenewDate;
    }

    function setReservationPending($flag) {
        $this->reservationPending = $flag;
    }

    function isReservationPending() {
        return $this->reservationPending;
    }
}
