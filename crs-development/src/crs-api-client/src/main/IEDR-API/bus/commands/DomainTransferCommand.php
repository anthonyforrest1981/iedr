<?php

include_once("ModifyCommandObject.php");
include_once("DomainPeriod.php");

class DomainTransferCommand extends ModifyCommandObject {

    private $name;
    private $techC = array();
    private $nss = array();
    private $defaults;
    private $period;
    private $card;
    private $authCode;
    private $payFromDeposit;

    function DomainTransferCommand($name = '') {
        parent::__construct();
        $this->name = $name;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function addTechC($techC) {
        $this->techC[] = $techC;
    }

    function getTechC() {
        return $this->techC;
    }

    function setTechC($techC) {
        $this->techC = $techC;
    }

    function addNs($ns) {
        $this->nss[] = $ns;
    }

    function getNss() {
        return $this->nss;
    }

    function setNss($nss) {
        $this->nss = $nss;
    }

    function getDefaults() {
        return $this->defaults;
    }

    function setDefaults($defaults) {
        $this->defaults = $defaults;
    }

    function issetDefaults() {
        return isset($this->defaults);
    }

    public function getPeriod() {
        return $this->period;
    }

    public function setPeriod($period) {
        $this->period = $period;
    }

    public function setPeriodWithUnit($unit, $period) {
        $this->setPeriod(new DomainPeriod($period, $unit));
    }

    public function getAuthCode() {
        return $this->authCode;
    }

    public function setAuthCode($code) {
        $this->authCode = $code;
    }

    public function getCard() {
        return $this->card;
    }

    public function setCard($card) {
        $this->card = $card;
    }

    public function getPayFromDeposit() {
        return $this->payFromDeposit;
    }

    public function setPayFromDeposit($payFromDeposit) {
        $this->payFromDeposit = $payFromDeposit;
    }
}
