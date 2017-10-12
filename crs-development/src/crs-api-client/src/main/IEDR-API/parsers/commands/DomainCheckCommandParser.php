<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainCheckCommand.php");
include_once("IEDR-API/parsers/commands/CheckCommandParser.php");

class DomainCheckCommandParser extends CheckCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:check");
        $node->setAttribute("xmlns:domain", "http://www.domainregistry.ie/ieapi-domain-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-domain-1.8 ieapi-domain-1.8.xsd");
        foreach ($object->getDomains() as $domain) {
            $domainNode = $this->document->createElement("domain:name", $domain);
            $node->appendChild($domainNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
