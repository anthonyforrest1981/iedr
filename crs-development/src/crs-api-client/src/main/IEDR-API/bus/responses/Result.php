<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("ResultCodes.php");
include_once("Reason.php");

class Result {

    private $code;
    private $msg;
    private $value;
    private $reasonCode;
    private $reasonMsg;

    function Result($code = '') {
        $this->setCode($code);
    }

    function getCode() {
        return $this->code;
    }

    function setCode($code) {
        $this->code = $code;
        $this->msg = $this->mapMessage($code, ResultCodes::$MSGS_LIST);
    }

    function getMsg() {
        return $this->msg;
    }

    function setMsg($msg) {
        $this->msg = $msg;
    }

    function getValue() {
        return $this->value;
    }

    function setValue($value) {
        $this->value = $value;
    }

    function getReasonCode() {
        return $this->reasonCode;
    }

    function setReasonCode($reasonCode) {
        $this->reasonCode = $reasonCode;
        $this->reasonMsg = $this->mapMessage($reasonCode, Reason::$MSGS_LIST);
    }

    private function mapMessage($code, $msgs) {
        if (!empty($code)) {
            if (array_key_exists($code, $msgs)) {
                return $msgs[$code];
            } else {
                echo "Cannot map message to the result code: $code\n";
            }
        }
    }

    function getReasonMsg() {
        return $this->reasonMsg;
    }

    function setReasonMsg($reasonMsg) {
        $this->reasonMsg = $reasonMsg;
    }
}
