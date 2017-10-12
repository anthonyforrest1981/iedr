<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/ContactCreateCommand.php");
include_once("CreateCommandParser.php");

class ContactCreateCommandParser extends CreateCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("contact:create");
        $node->setAttribute("xmlns:contact", "http://www.domainregistry.ie/ieapi-contact-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-contact-1.8 ieapi-contact-1.8.xsd");
        if ($object->getName()) {
            $tmpNode = $this->document->createElement("contact:name", $object->getName());
            $node->appendChild($tmpNode);
        }
        if ($object->getCompanyName()) {
            $tmpNode =
                $this->document->createElement("contact:companyName", $object->getCompanyName());
            $node->appendChild($tmpNode);
        }
        if ($object->getAddress()) {
            $tmpNode = $this->document->createElement("contact:addr", $object->getAddress());
            $node->appendChild($tmpNode);
        }
        if ($object->getCounty()) {
            $tmpNode = $this->document->createElement("contact:county", $object->getCounty());
            $node->appendChild($tmpNode);
        }
        if ($object->getCountry()) {
            $tmpNode = $this->document->createElement("contact:country", $object->getCountry());
            $node->appendChild($tmpNode);
        }
        if ($object->getVoice()) {
            $tmpNode = $this->document->createElement("contact:voice", $object->getVoice());
            $node->appendChild($tmpNode);
        }
        if ($object->getFax()) {
            $tmpNode = $this->document->createElement("contact:fax", $object->getFax());
            $node->appendChild($tmpNode);
        }
        if ($object->getEmail()) {
            $tmpNode = $this->document->createElement("contact:email", $object->getEmail());
            $node->appendChild($tmpNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
