<?php

require_once("InfoCommandParser.php");

class RegistrantTransferSellRequestInfoCommandParser extends InfoCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferSellRequest:info");
        $node->setAttribute("xmlns:registrantTransferSellRequest",
                "http://www.domainregistry.ie/ieapi-registrantTransferSellRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
                "http://www.domainregistry.ie/ieapi-registrantTransferSellRequest-1.8 ieapi-registrantTransferSellRequest-1.8.xsd");

        $idNode = $this->document->createElement("registrantTransferSellRequest:id", $object->getId());
        $node->appendChild($idNode);

        $this->command = $node;
        return parent::toXml($object);
    }

}
