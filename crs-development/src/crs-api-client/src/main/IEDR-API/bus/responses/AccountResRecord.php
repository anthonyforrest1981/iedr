<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class AccountResRecord {

    private $domain;
    private $holder;
    private $invoice;
    private $regDate;
    private $renDate;
    private $amount;
    private $vat;
    private $status;
    private $trnDate;
    private $msdDate;

    function AccountResRecord($domain = '', $holder = '', $invoice = '', $regDate = '',
                              $renDate = '', $amount = '', $vat = '', $status = '', $trnDate = '',
                              $msdDate = '') {
        $this->domain = $domain;
        $this->holder = $holder;
        $this->invoice = $invoice;
        $this->regDate = $regDate;
        $this->renDate = $renDate;
        $this->amount = $amount;
        $this->vat = $vat;
        $this->status = $status;
        $this->trnDate = $trnDate;
        $this->msdDate = $msdDate;
    }

    function getDomain() {
        return $this->domain;
    }

    function setDomain($domain) {
        $this->domain = $domain;
    }

    function getHolder() {
        return $this->holder;
    }

    function setHolder($holder) {
        $this->holder = $holder;
    }

    function getInvoice() {
        return $this->invoice;
    }

    function setInvoice($invoice) {
        $this->invoice = $invoice;
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

    function getAmount() {
        return $this->amount;
    }

    function setAmount($amount) {
        $this->amount = $amount;
    }

    function getVat() {
        return $this->vat;
    }

    function setVat($vat) {
        $this->vat = $vat;
    }

    function getStatus() {
        return $this->status;
    }

    function setStatus($status) {
        $this->status = $status;
    }

    function getTrnDate() {
        return $this->trnDate;
    }

    function setTrnDate($trnDate) {
        $this->trnDate = $trnDate;
    }

    function getMsdDate() {
        return $this->msdDate;
    }

    function setMsdDate($msdDate) {
        $this->msdDate = $msdDate;
    }
}
