<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandParser.php");

class ModifyCommandParser extends CommandParser {

    function toXml($object) {
        $modifyCommand = $this->document->createElement("modify");
        $modifyCommand->appendChild($this->command);
        $this->command = $modifyCommand;

        return parent::toXml($object);
    }

}
