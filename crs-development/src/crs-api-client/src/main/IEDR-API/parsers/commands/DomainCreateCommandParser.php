<?php

include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("CreateCommandParser.php");
include_once("DomainPeriodParser.php");
include_once("IEDR-API/parsers/common/DomainNsParser.php");
include_once("IEDR-API/parsers/common/DomainContactParser.php");
include_once("IEDR-API/parsers/common/DomainHolderParser.php");
include_once("DomainPayFromDepositParser.php");

class DomainCreateCommandParser extends CreateCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:create");
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
        if ($object->getHolder()) {
            $tmpNode = DomainHolderParser::toXml($this->document, $object->getHolder());
            $node->appendChild($tmpNode);
        }
        if ($object->getCharity()) {
            $tmpNode = $this->document->createElement("domain:chy", $object->getCharity());
            $node->appendChild($tmpNode);
        }
        foreach ($object->getAdmins() as $contact) {
            $tmpNode =
                DomainContactParser::toXml($this->document, $contact, DomainContact::$TYPE_ADMIN);
            $node->appendChild($tmpNode);
        }
        foreach ($object->getTechs() as $contact) {
            $tmpNode =
                DomainContactParser::toXml($this->document, $contact, DomainContact::$TYPE_TECH);
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
