<?php
require_once("QueryCommandParser.php");

class RegistrantTransferBuyRequestQueryCommandParser extends QueryCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferBuyRequest:query");
        $node->setAttribute("xmlns:registrantTransferBuyRequest", "http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8 ieapi-registrantTransferBuyRequest-1.8.xsd");
        if ($object->getDomainName()) {
            $node->setAttribute("domainName", $object->getDomainName());
        }
        if ($object->getPage()) {
            $node->setAttribute("page", $object->getPage());
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
