<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandParser.php");

class CheckCommandParser extends CommandParser {

    function toXml($object) {
        $checkCommand = $this->document->createElement("check");
        $checkCommand->appendChild($this->command);
        $this->command = $checkCommand;

        return parent::toXml($object);
    }

}
