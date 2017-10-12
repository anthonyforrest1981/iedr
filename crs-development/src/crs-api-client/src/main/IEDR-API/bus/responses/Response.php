<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/IeapiObject.php");
include_once("Result.php");

class Response extends IeapiObject {

    private $result;
    private $tid;

    function __construct($code = '') {
        $this->type = self::$TYPE_RESPONSE;
        $this->setResultCode($code);
    }

    function setResultCode($code) {
        $this->result = new Result($code);
    }

    function getResult() {
        return $this->result;
    }

    function setResult($result) {
        $this->result = $result;
    }

    function getTid() {
        return $this->tid;
    }

    function setTid($tid) {
        $this->tid = $tid;
    }
}
