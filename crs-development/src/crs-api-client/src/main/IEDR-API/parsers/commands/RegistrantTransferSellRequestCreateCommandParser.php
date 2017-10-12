<?php

require_once("CreateCommandParser.php");
require_once("CreditCardParser.php");

class RegistrantTransferSellRequestCreateCommandParser extends CreateCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("registrantTransferSellRequest:create");
        $node->setAttribute("xmlns:ieapicom", "http://www.domainregistry.ie/ieapicom-1.8");
        $node->setAttribute("xmlns:registrantTransferSellRequest",
                "http://www.domainregistry.ie/ieapi-registrantTransferSellRequest-1.8");
        $node->setAttribute("xsi:schemaLocation",
                "http://www.domainregistry.ie/ieapicom-1.8 ieapicom-1.8.xsd http://www.domainregistry.ie/ieapi-registrantTransferSellRequest-1.8 ieapi-registrantTransferSellRequest-1.8.xsd");

        $domainNode = $this->document->createElement("registrantTransferSellRequest:domainName", $object->getDomainName());
        $node->appendChild($domainNode);

        $authcodeNode = $this->document->createElement("registrantTransferSellRequest:authCode", $object->getAuthcode());
        $node->appendChild($authcodeNode);

        $payMethodNode = $this->document->createElement("registrantTransferSellRequest:method");
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
