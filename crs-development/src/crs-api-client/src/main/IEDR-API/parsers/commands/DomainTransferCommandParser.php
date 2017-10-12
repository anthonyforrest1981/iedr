<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("ModifyCommandParser.php");
include_once("IEDR-API/parsers/common/DomainNsParser.php");
include_once("IEDR-API/parsers/common/DomainContactParser.php");
include_once("DomainPeriodParser.php");
include_once("AccountCreditCardParser.php");
include_once("DomainPayFromDepositParser.php");

class DomainTransferCommandParser extends ModifyCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:transfer");
        $node->setAttribute("xmlns:domain", "http://www.domainregistry.ie/ieapi-domain-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-domain-1.8 ieapi-domain-1.8.xsd");
        $tmpNode = $this->document->createElement("domain:name", $object->getName());
        $node->appendChild($tmpNode);
        $period = $object->getPeriod();
        if ($period) {
            $tmpNode = DomainPeriodParser::toXml($this->document, $period);
            $node->appendChild($tmpNode);
        }
        foreach ($object->getNss() as $ns) {
            $tmpNode = DomainNsParser::toXml($this->document, $ns);
            $node->appendChild($tmpNode);
        }
        foreach ($object->getTechC() as $contact) {
            $tmpNode =
                DomainContactParser::toXml($this->document, $contact, DomainContact::$TYPE_TECH);
            $node->appendChild($tmpNode);
        }
        if ($object->getDefaults()) {
            $tmpNode = $this->document->createElement("domain:defaults", $object->getDefaults());
            $node->appendChild($tmpNode);
        }

        if ($object->getCard()) {
            $tmpNode = AccountCreditCardParser::toXml($this->document, $object->getCard());
            $node->appendChild($tmpNode);
        }

        if ($object->getAuthCode()) {
            $tmpNode = $this->document->createElement("domain:authCode", $object->getAuthCode());
            $node->appendChild($tmpNode);
        }

        if ($object->getPayFromDeposit()) {
            $tmpNode = DomainPayFromDepositParser::toXml($this->document, $object->getPayFromDeposit());
            $node->appendChild($tmpNode);
        }

        $this->command = $node;

        return parent::toXml($object);

    }

}
