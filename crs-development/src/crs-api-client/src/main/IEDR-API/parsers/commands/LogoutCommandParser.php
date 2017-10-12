<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("CommandParser.php");

class LogoutCommandParser extends CommandParser {

    function toXml($object) {
        $logout = $this->document->createElement("logout");
        $this->command = $logout;

        return parent::toXml($object);
    }
}
