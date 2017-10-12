<?php
include_once("IEDR-API/bus/commands/CreditCard.php");

class CreditCardParser {

    function toXml($doc, $object) {
        $node = $doc->createElement("ieapicom:card");
        $tmpNode = $doc->createElement("ieapicom:cardHolderName", $object->getCardHolder());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("ieapicom:cardNumber", $object->getCardNumber());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("ieapicom:expiryDate", $object->getExpiryDate());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("ieapicom:cardType", $object->getCardType());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("ieapicom:cvnNumber", $object->getCvnNumber());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("ieapicom:cvnPresInd", $object->getCvnPresInd());
        $node->appendChild($tmpNode);

        return $node;
    }

}
