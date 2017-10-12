<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/ContactQueryCommand.php");
include_once("QueryCommandParser.php");

class ContactQueryCommandParser extends QueryCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("contact:query");
        $node->setAttribute("xmlns:contact", "http://www.domainregistry.ie/ieapi-contact-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-contact-1.8 ieapi-contact-1.8.xsd");
        $node->setAttribute("type", $object->getQueryType());
        if ($object->getPage()) {
            $node->setAttribute("page", $object->getPage());
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
