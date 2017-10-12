<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/ContactModifyCommand.php");
include_once("ModifyCommandParser.php");

class ContactModifyCommandParser extends ModifyCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("contact:modify");
        $node->setAttribute("xmlns:contact", "http://www.domainregistry.ie/ieapi-contact-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-contact-1.8 ieapi-contact-1.8.xsd");
        $tmpNode = $this->document->createElement("contact:id", $object->getId());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:companyName", $object->getCompanyName());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:addr", $object->getAddress());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:county", $object->getCounty());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:country", $object->getCountry());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:voice", $object->getVoice());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:fax", $object->getFax());
        $node->appendChild($tmpNode);
        $tmpNode = $this->document->createElement("contact:email", $object->getEmail());
        $node->appendChild($tmpNode);
        $this->command = $node;

        return parent::toXml($object);
    }

}
