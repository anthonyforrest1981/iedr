<?php
require_once("ModifyCommandParser.php");
require_once("IEDR-API/parsers/common/IeapiHolderParser.php");
require_once("IEDR-API/parsers/common/IeapiContactParser.php");

class RegistrantTransferBuyRequestModifyCommandParser extends ModifyCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferBuyRequest:modify");
        $node->setAttribute("xmlns:ieapicom", "http://www.domainregistry.ie/ieapicom-1.8");
        $node->setAttribute("xmlns:registrantTransferBuyRequest", "http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapicom-1.8 ieapicom-1.8.xsd http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8 ieapi-registrantTransferBuyRequest-1.8.xsd");

        $tmpNode = $this->document->createElement("registrantTransferBuyRequest:id", $object->getBuyRequestId());
        $node->appendChild($tmpNode);
        if ($object->getHolder()) {
            $tmpNode = IeapiHolderParser::toXml($this->document, $object->getHolder(), "registrantTransferBuyRequest");
            $node->appendChild($tmpNode);
        }
        if ($object->getContact()) {
            $tmpNode = IeapiContactParser::toXml($this->document, $object->getContact(), "registrantTransferBuyRequest");
            $node->appendChild($tmpNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
