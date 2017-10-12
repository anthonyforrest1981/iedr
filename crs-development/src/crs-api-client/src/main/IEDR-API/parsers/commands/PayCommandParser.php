<?php
/*
* Copyright (C) 2006 NASK
* http://www.nask.pl
* http://www.dns.pl
*/
include_once("CommandParser.php");

class PayCommandParser extends CommandParser {

    function toXml($object) {
        $payCommand = $this->document->createElement("pay");
        $payCommand->appendChild($this->command);
        $this->command = $payCommand;

        return parent::toXml($object);
    }

}
