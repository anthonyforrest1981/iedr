<?php

require_once("QueryCommandParser.php");

class RegistrantTransferSellRequestQueryCommandParser extends QueryCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferSellRequest:query");
        $node->setAttribute("xmlns:registrantTransferSellRequest",
                "http://www.domainregistry.ie/ieapi-registrantTransferSellRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
                "http://www.domainregistry.ie/ieapi-registrantTransferSellRequest-1.8 ieapi-registrantTransferSellRequest-1.8.xsd");

        if ($object->getPage()) {
            $node->setAttribute("page", $object->getPage());
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
