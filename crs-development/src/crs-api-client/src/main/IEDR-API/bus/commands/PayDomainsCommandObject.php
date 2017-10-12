<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandObject.php");

abstract class PayDomainsCommandObject extends CommandObject {

    static public $PAY_TYPE_CURRRENREG = 1;
    static public $PAY_TYPE_FUTREN = 2;
    static public $PAY_TYPE_TRANADV = 3;
    static public $PAY_TYPE_TRAN = 4;
    static public $ALLOWED_TYPES = array(
        1 => "currRenReg",
        2 => "futRen",
        4 => "tranAdv",
        5 => "tran");
    private $payType;
    private $domains = array();

    function __construct($payType = '') {
        parent::__construct();
        $this->commandType = self::$COMMAND_PAY;
        $this->setPayType($payType);
    }

    function getPayType() {
        return $this->payType;
    }

    function setPayType($type) {
        $this->payType = $type;
    }

    function getDomains() {
        return $this->domains;
    }

    function setDomains($domains) {
        $this->domains = $domains;
    }

    function addDomain($domain) {
        $this->domains[] = $domain;
    }
}
