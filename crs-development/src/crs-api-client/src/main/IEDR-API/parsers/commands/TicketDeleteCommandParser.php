<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/TicketDeleteCommand.php");
include_once("DeleteCommandParser.php");

class TicketDeleteCommandParser extends DeleteCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("ticket:delete");
        $node->setAttribute("xmlns:ticket", "http://www.domainregistry.ie/ieapi-ticket-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-ticket-1.8 ieapi-ticket-1.8.xsd");
        $tmpNode = $this->document->createElement("ticket:name", $object->getName());
        $node->appendChild($tmpNode);
        $this->command = $node;

        return parent::toXml($object);
    }

}
