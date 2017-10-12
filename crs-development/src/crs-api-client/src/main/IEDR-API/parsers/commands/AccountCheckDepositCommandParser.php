<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/AccountCheckDepositCommand.php");
include_once("PayCommandParser.php");

class AccountCheckDepositCommandParser extends PayCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("account:checkDeposit");
        $node->setAttribute("xmlns:account", "http://www.domainregistry.ie/ieapi-account-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-account-1.8 ieapi-account-1.8.xsd");
        $this->command = $node;

        return parent::toXml($object);
    }

}
