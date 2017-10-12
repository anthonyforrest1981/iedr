<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Reason.php");

class DomainCd {

    static public $AVAIL_TRUE = 1;
    static public $AVAIL_FALSE = 0;
    private $name;
    private $avail;
    private $reasonCode;
    private $reasonMsg;

    function DomainCd() {
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getAvail() {
        return $this->avail;
    }

    function setAvail($avail) {
        $this->avail = $avail;
    }

    function getReasonCode() {
        return $this->reasonCode;
    }

    function setReasonCode($code) {
        $this->reasonCode = $code;
        $this->setReasonMsg(Reason::$MSGS_LIST[$code]);
    }

    function getReasonMsg() {
        return $this->reasonMsg;
    }

    function setReasonMsg($msg) {
        $this->reasonMsg = $msg;
    }
}
