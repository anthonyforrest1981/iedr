<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class DomainInfDataResponse extends Response {

    private $name;
    private $nss = array();
    private $holder;
    private $admins = array();
    private $techs = array();
    private $account;
    private $regDate;
    private $renDate;
    private $suspendDate;
    private $deleteDate;
    private $lockDate;
    private $lockRenewDate;
    private $authCode;
    private $status;

    function DomainInfData($name = '', $status = '', $account = '', $regDate = '', $renDate = '') {
        $this->name = $name;
        $this->status = $status;
        $this->account = $account;
        $this->regDate = $regDate;
        $this->renDate = $renDate;
    }

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

    function getNss() {
        return $this->nss;
    }

    function setNss($nss) {
        $this->nss = $nss;
    }

    function addNs($ns) {
        $this->nss[] = $ns;
    }

    function getHolder() {
        return $this->holder;
    }

    function setHolder($holder) {
        $this->holder = $holder;
    }

    function getAdmins() {
        return $this->admins;
    }

    function setAdmins($admins) {
        $this->admins = $admins;
    }

    function addAdmin($admin) {
        $this->admins[] = $admin;
    }

    function getTechs() {
        return $this->techs;
    }

    function setTechs($techs) {
        $this->techs = $techs;
    }

    function addTech($tech) {
        $this->techs[] = $tech;
    }

    function getAccount() {
        return $this->account;
    }

    function setAccount($account) {
        $this->account = $account;
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

    function getAuthCode() {
        return $this->authCode;
    }

    function setAuthCode($authCode) {
        return $this->authCode = $authCode;
    }
}
