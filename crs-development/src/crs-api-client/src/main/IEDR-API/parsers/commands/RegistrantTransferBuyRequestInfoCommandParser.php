<?php
require_once("InfoCommandParser.php");

class RegistrantTransferBuyRequestInfoCommandParser extends InfoCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferBuyRequest:info");
        $node->setAttribute("xmlns:registrantTransferBuyRequest", "http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8 ieapi-registrantTransferBuyRequest-1.8.xsd");
        $tmpNode = $this->document->createElement("registrantTransferBuyRequest:id", $object->getId());
        $node->appendChild($tmpNode);
        $this->command = $node;

        return parent::toXml($object);
    }

}
