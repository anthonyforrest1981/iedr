<?php
/*
* Copyright (C) 2007 NASK
* http://www.nask.pl
* http://www.dns.pl
*/
include_once("IEDR-API/bus/commands/CreditCard.php");

class AccountCreditCardParser {

    function toXml($doc, $object) {
        $node = $doc->createElement("account:card");
        $tmpNode = $doc->createElement("account:cardHolderName", $object->getCardHolder());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("account:cardNumber", $object->getCardNumber());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("account:expiryDate", $object->getExpiryDate());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("account:cardType", $object->getCardType());
        $node->appendChild($tmpNode);

        return $node;
    }

}
