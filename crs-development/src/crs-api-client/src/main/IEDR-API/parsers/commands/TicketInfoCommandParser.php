<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("InfoCommandParser.php");

class TicketInfoCommandParser extends InfoCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("ticket:info");
        $node->setAttribute("xmlns:ticket", "http://www.domainregistry.ie/ieapi-ticket-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-ticket-1.8 ieapi-ticket-1.8.xsd");
        $tmpNode = $this->document->createElement("ticket:name", $object->getName());
        $node->appendChild($tmpNode);
        if ($object->fillNsAddr6()) {
            $tmpNode = $this->document->createElement("ticket:fillNsAddr6", $object->fillNsAddr6());
            $node->appendChild($tmpNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
