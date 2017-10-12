<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandParser.php");

class CreateCommandParser extends CommandParser {

    function toXml($object) {
        $createCommand = $this->document->createElement("create");
        $createCommand->appendChild($this->command);
        $this->command = $createCommand;

        return parent::toXml($object);
    }

}
