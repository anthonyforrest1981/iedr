<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");
include_once("InfoCommandParser.php");

class ContactInfoCommandParser extends InfoCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("contact:info");
        $node->setAttribute("xmlns:contact", "http://www.domainregistry.ie/ieapi-contact-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-contact-1.8 ieapi-contact-1.8.xsd");
        $tmpNode = $this->document->createElement("contact:id", $object->getId());
        $node->appendChild($tmpNode);
        $this->command = $node;

        return parent::toXml($object);
    }

}
