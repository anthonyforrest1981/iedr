<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandParser.php");

class InfoCommandParser extends CommandParser {

    function toXml($object) {
        $infoCommand = $this->document->createElement("info");
        $infoCommand->appendChild($this->command);
        $this->command = $infoCommand;

        return parent::toXml($object);
    }

}
