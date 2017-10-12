<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CreateCommandObject.php");
include_once("DomainPeriod.php");

class DomainCreateCommand extends CreateCommandObject {

    private $name;
    private $period;
    private $nss = array();
    private $holder;
    private $admins = array();
    private $techs = array();
    private $charity;
    private $payFromDeposit;

    function DomainCreateCommand($name = '') {
        parent::__construct();
        $this->name = $name;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function setUnitPeriod($unit, $period) {
        $this->setPeriod(new DomainPeriod($period, $unit));
    }

    function getPeriod() {
        return $this->period;
    }

    function setPeriod($period) {
        $this->period = $period;
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

    function getCharity() {
        return $this->charity;
    }

    function setCharity($charity) {
        $this->charity = $charity;
    }

    function getPayFromDeposit() {
        return $this->payFromDeposit;
    }

    function setPayFromDeposit($payFromDeposit) {
        $this->payFromDeposit = $payFromDeposit;
    }
}
