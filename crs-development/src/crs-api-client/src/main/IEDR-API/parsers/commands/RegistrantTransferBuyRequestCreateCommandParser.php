<?php
require_once("CreateCommandParser.php");
require_once("CreditCardParser.php");
require_once("IEDR-API/parsers/common/IeapiHolderParser.php");
require_once("IEDR-API/parsers/common/IeapiContactParser.php");

class RegistrantTransferBuyRequestCreateCommandParser extends CreateCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferBuyRequest:create");
        $node->setAttribute("xmlns:ieapicom", "http://www.domainregistry.ie/ieapicom-1.8");
        $node->setAttribute("xmlns:registrantTransferBuyRequest", "http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapicom-1.8 ieapicom-1.8.xsd http://www.domainregistry.ie/ieapi-registrantTransferBuyRequest-1.8 ieapi-registrantTransferBuyRequest-1.8.xsd");
        $tmpNode = $this->document->createElement("registrantTransferBuyRequest:domainName", $object->getDomainName());
        $node->appendChild($tmpNode);
        $tmpNode = IeapiHolderParser::toXml($this->document, $object->getHolder(), "registrantTransferBuyRequest");
        $node->appendChild($tmpNode);
        $tmpNode = IeapiContactParser::toXml($this->document, $object->getContact(), "registrantTransferBuyRequest");
        $node->appendChild($tmpNode);
        $payMethodNode = $this->document->createElement("registrantTransferBuyRequest:method");
        if ($object->getCreditCard()) {
            $ccNode = CreditCardParser::toXml($this->document, $object->getCreditCard());
            $payMethodNode->appendChild($ccNode);
        } else {
            $payFromDepositNode = $this->document->createElement("ieapicom:deposit");
            $payMethodNode->appendChild($payFromDepositNode);
        }
        $node->appendChild($payMethodNode);
        $this->command = $node;

        return parent::toXml($object);
    }

}
