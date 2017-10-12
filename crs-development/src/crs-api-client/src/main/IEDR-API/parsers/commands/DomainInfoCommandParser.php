<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("InfoCommandParser.php");

class DomainInfoCommandParser extends InfoCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:info");
        $node->setAttribute("xmlns:domain", "http://www.domainregistry.ie/ieapi-domain-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-domain-1.8 ieapi-domain-1.8.xsd");
        $tmpNode = $this->document->createElement("domain:name", $object->getName());
        $node->appendChild($tmpNode);
        if ($object->getAuthCode()) {
            $tmpNode = $this->document->createElement("domain:authCode", $object->getAuthCode());
            $node->appendChild($tmpNode);
        }
        if ($object->getAuthCodeForceGeneration()) {
            $tmpNode =
                $this->document->createElement("domain:authCodeForceGeneration",
                    $object->getAuthCodeForceGeneration());
            $node->appendChild($tmpNode);
        }
        if ($object->fillNsAddr6()) {
            $tmpNode = $this->document->createElement("domain:fillNsAddr6", $object->fillNsAddr6());
            $node->appendChild($tmpNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
