<?php

include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("PayCommandParser.php");
include_once("AccountCreditCardParser.php");

class AccountPayCommandParser extends PayCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("account:pay");
        $node->setAttribute("xmlns:account", "http://www.domainregistry.ie/ieapi-account-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-account-1.8 ieapi-account-1.8.xsd");

        foreach ($object->getDomains() as $domain) {
            $tmpNode = $this->document->createElement("account:domain");
            $t = $this->document->createTextNode($domain);
            $tmpNode->appendChild($t);
            $node->appendChild($tmpNode);
        }

        $payMethodNode = $this->document->createElement("account:method");
        if ($object->getCreditCard()) {
            $ccNode = AccountCreditCardParser::toXml($this->document, $object->getCreditCard());
            $payMethodNode->appendChild($ccNode);
        } else {
            $payFromDepositNode = $this->document->createElement("account:deposit");
            $payMethodNode->appendChild($payFromDepositNode);
        }
        $node->appendChild($payMethodNode);

        if ($object->getPeriod()) {
            $periodNode = $this->document->createElement("account:period", $object->getPeriod());
            $node->appendChild($periodNode);
        }

        $this->command = $node;

        return parent::toXml($object);
    }

}
