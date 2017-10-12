<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandParser.php");

class QueryCommandParser extends CommandParser {

    function toXml($object) {
        $queryCommand = $this->document->createElement("query");
        $queryCommand->appendChild($this->command);
        $this->command = $queryCommand;

        return parent::toXml($object);
    }

}
