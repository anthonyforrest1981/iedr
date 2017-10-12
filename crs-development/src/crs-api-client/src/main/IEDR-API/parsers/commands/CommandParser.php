<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/parsers/IeapiParser.php");

class CommandParser extends IeapiParser {

    function toXml($object) {
        $ieapi = parent::toXml($object);
        $commandNode = $this->document->createElement("command");
        $commandNode->appendChild($this->command);
        $tid = $this->document->createElement("tid", $object->getTid());
        $commandNode->appendChild($tid);
        $ieapi->appendChild($commandNode);

        return $ieapi;
    }

    function fromDomNode($node) {
        throw new Exception('fromDomNode called on CommandParser');
    }

}
