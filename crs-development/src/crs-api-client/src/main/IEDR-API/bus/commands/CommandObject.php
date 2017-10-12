<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/IeapiObject.php");

abstract class CommandObject extends IeapiObject {

    static public $COMMAND_CREATE = 1;
    static public $COMMAND_MODIFY = 2;
    static public $COMMAND_DELETE = 3;
    static public $COMMAND_INFO = 4;
    static public $COMMAND_QUERY = 5;
    static public $COMMAND_CHECK = 6;
    static public $COMMAND_LOGIN = 7;
    static public $COMMAND_LOGOUT = 8;
    static public $COMMAND_MSD = 9;
    static public $COMMAND_MSDCANCEL = 10;
    static public $COMMAND_PAY = 11;
    protected $type;
    protected $commandType;
    protected $tid;

    function __construct() {
        $this->type = self::$TYPE_COMMAND;
    }

    function getTid() {
        return $this->tid;
    }

    function setTid($tid) {
        $this->tid = $tid;
    }
}
