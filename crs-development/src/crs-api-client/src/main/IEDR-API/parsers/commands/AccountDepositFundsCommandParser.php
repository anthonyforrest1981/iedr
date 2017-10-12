<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/AccountDepositFundsCommand.php");
include_once("PayCommandParser.php");
include_once("AccountCreditCardParser.php");

class AccountDepositFundsCommandParser extends PayCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("account:depositFunds");
        $node->setAttribute("xmlns:account", "http://www.domainregistry.ie/ieapi-account-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-account-1.8 ieapi-account-1.8.xsd");
        $tmpNode = $this->document->createElement("account:value", $object->getValue());
        $node->appendChild($tmpNode);
        $ccNode = AccountCreditCardParser::toXml($this->document, $object->getCreditCard());
        $node->appendChild($ccNode);
        $this->command = $node;

        return parent::toXml($object);
    }

}
