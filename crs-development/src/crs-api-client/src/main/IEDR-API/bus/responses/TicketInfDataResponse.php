<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class TicketInfDataResponse extends Response {

    private $name;
    private $ticketType;
    private $hostmasterStatus;
    private $dnsStatus;
    private $nss = array();
    private $holder;
    private $admins = array();
    private $techs = array();
    private $account;
    private $regDate;
    private $renDate;
    private $hostmasterRemark;

    function TicketInfData($name = '', $type = '', $hostmasterStatus = '', $dnsStatus = '',
                           $account = '', $regDate = '', $renDate = '', $hostmasterRemark = '') {
        $this->name = $name;
        $this->ticketType = $type;
        $this->hostmasterStatus = $hostmasterStatus;
        $this->dnsStatus = $dnsStatus;
        $this->account = $account;
        $this->regDate = $regDate;
        $this->renDate = $renDate;
        $this->hostmasterRemark = $hostmasterRemark;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getTicketType() {
        return $this->ticketType;
    }

    function setTicketType($type) {
        $this->ticketType = $type;
    }

    function getHostmasterStatus() {
        return $this->hostmasterStatus;
    }

    function setHostmasterStatus($hostmasterStatus) {
        $this->hostmasterStatus = $hostmasterStatus;
    }

    function getDnsStatus() {
        return $this->dnsStatus;
    }

    function setDnsStatus($dnsStatus) {
        $this->dnsStatus = $dnsStatus;
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

    function getHostmasterRemark() {
        return $this->hostmasterRemark;
    }

    function setHostmasterRemark($remark) {
        $this->hostmasterRemark = $remark;
    }

    function getHolder() {
        return $this->holder;
    }

    function setHolder($holder) {
        $this->holder = $holder;
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
}
