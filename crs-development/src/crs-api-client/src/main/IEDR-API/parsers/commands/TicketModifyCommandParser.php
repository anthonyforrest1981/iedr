<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/parsers/commands/ModifyCommandParser.php");
include_once("IEDR-API/parsers/common/TicketNsParser.php");
include_once("IEDR-API/parsers/common/TicketContactParser.php");
include_once("IEDR-API/parsers/common/TicketHolderParser.php");

class TicketModifyCommandParser extends ModifyCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("ticket:modify");
        $node->setAttribute("xmlns:ticket", "http://www.domainregistry.ie/ieapi-ticket-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-ticket-1.8 ieapi-ticket-1.8.xsd");
        if ($object->getName()) {
            $tmpNode = $this->document->createElement("ticket:name", $object->getName());
            $node->appendChild($tmpNode);
        }
        $nsAdds = $object->getNssAdds();
        $adminAdds = $object->getAdminCAdds();
        $techsAdds = $object->getTechCAdds();
        if ((count($nsAdds) + count($adminAdds) + count($techsAdds)) > 0) {
            $addNode = $this->document->createElement("ticket:add");
            foreach ($nsAdds as $add) {
                $nsNode = TicketNsParser::toXml($this->document, $add);
                $addNode->appendChild($nsNode);
            }
            foreach ($adminAdds as $add) {
                if (get_class($add)) {
                    $add = $add->getName();
                }
                $contactNode =
                    TicketContactParser::toXml($this->document, $add, TicketContact::$TYPE_ADMIN);
                $addNode->appendChild($contactNode);
            }
            foreach ($techsAdds as $add) {
                if (get_class($add)) {
                    $add = $add->getName();
                }
                $contactNode =
                    TicketContactParser::toXml($this->document, $add, TicketContact::$TYPE_TECH);
                $addNode->appendChild($contactNode);
            }
            $node->appendChild($addNode);
        }
        $nsRems = $object->getNssRems();
        $adminsRems = $object->getAdminCRems();
        $techsRems = $object->getTechCRems();
        if ((count($nsRems) + count($adminsRems) + count($techsRems)) > 0) {
            $remNode = $this->document->createElement("ticket:rem");
            foreach ($nsRems as $rem) {
                $nsNode = TicketNsParser::toXml($this->document, $rem);
                $remNode->appendChild($nsNode);
            }
            foreach ($adminsRems as $rem) {
                if (get_class($rem)) {
                    $rem = $rem->getName();
                }
                $contactNode =
                    TicketContactParser::toXml($this->document, $rem, TicketContact::$TYPE_ADMIN);
                $remNode->appendChild($contactNode);
            }
            foreach ($techsRems as $rem) {
                if (get_class($rem)) {
                    $rem = $rem->getName();
                }
                $contactNode =
                    TicketContactParser::toXml($this->document, $rem, TicketContact::$TYPE_TECH);
                $remNode->appendChild($contactNode);
            }
            $node->appendChild($remNode);
        }
        if ($object->getHolder()) {
            $chgNode = $this->document->createElement("ticket:chg");
            $holderNode = TicketHolderParser::toXml($this->document, $object->getHolder());
            $chgNode->appendChild($holderNode);
            $node->appendChild($chgNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
