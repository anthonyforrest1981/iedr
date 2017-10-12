<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandParser.php");

class DeleteCommandParser extends CommandParser {

    function toXml($object) {
        $deleteCommand = $this->document->createElement("delete");
        $deleteCommand->appendChild($this->command);
        $this->command = $deleteCommand;

        return parent::toXml($object);
    }

}
