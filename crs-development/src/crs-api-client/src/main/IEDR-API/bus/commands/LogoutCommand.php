<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandObject.php");

class LogoutCommand extends CommandObject {

    function LogoutCommand() {
        parent::__construct();
        $this->commandType = self::$COMMAND_LOGOUT;
    }

}
