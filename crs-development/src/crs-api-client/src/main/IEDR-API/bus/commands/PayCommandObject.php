<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandObject.php");

abstract class PayCommandObject extends CommandObject {

    function __construct() {
        parent::__construct();
        $this->commandType = self::$COMMAND_PAY;
    }
}
