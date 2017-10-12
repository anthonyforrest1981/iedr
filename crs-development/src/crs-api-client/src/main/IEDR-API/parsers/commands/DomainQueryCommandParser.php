<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");
include_once("QueryCommandParser.php");

class DomainQueryCommandParser extends QueryCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:query");
        $node->setAttribute("xmlns:domain", "http://www.domainregistry.ie/ieapi-domain-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-domain-1.8 ieapi-domain-1.8.xsd");

        if ($object->getContact()) {
            $tmpNode = $this->document->createElement("domain:contact", $object->getContact());
            if ($object->getContactType()) {
                $tmpNode->setAttribute("type", $object->getContactType());
            }
            $node->appendChild($tmpNode);
        }
        if ($object->getRenewalDateRangeStart()) {
            $tmpNode =
                $this->document->createElement("domain:renewalDateRangeStart",
                    $object->getRenewalDateRangeStart());
            $node->appendChild($tmpNode);
        }
        if ($object->getRenewalDateRangeEnd()) {
            $tmpNode =
                $this->document->createElement("domain:renewalDateRangeEnd",
                    $object->getRenewalDateRangeEnd());
            $node->appendChild($tmpNode);
        }

        if ($object->getTransfer()) {
            $tmpNode = $this->document->createElement("domain:transfer");
            $tmpNode->setAttribute("type", $object->getTransfer()->getDirection());

            if ($object->getTransfer()->getDateRangeStart()) {
                $tmpNode->setAttribute("from", $object->getTransfer()->getDateRangeStart());
            }
            if ($object->getTransfer()->getDateRangeENd()) {
                $tmpNode->setAttribute("to", $object->getTransfer()->getDateRangeEnd());
            }
            $node->appendChild($tmpNode);
        }

        if ($object->getDomainStatus()) {
            $tmpNode =
                $this->document->createElement("domain:domainStatus", $object->getDomainStatus());
            $node->appendChild($tmpNode);
        }

        if ($object->isAttachReservationInfo()) {
            $tmpNode =
                $this->document->createElement("domain:attachReservationInfo",
                    $object->isAttachReservationInfo());
            $node->appendChild($tmpNode);
        }

        if ($object->isAuthCode()) {
            $tmpNode = $this->document->createElement("domain:authCode", $object->isAuthCode());
            $node->appendChild($tmpNode);
        }

        if ($object->isAuthCodeForceGeneration()) {
            $tmpNode =
                $this->document->createElement("domain:authCodeForceGeneration",
                    $object->isAuthCodeForceGeneration());
            $node->appendChild($tmpNode);
        }

        if ($object->getPage()) {
            $node->setAttribute("page", $object->getPage());
        }

        $this->command = $node;

        return parent::toXml($object);
    }

}
