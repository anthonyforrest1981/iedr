<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainNRPCancelCommand.php");
include_once("NRPCommandParser.php");

class DomainNRPCancelCommandParser extends NRPCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:nrpCancel");
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
