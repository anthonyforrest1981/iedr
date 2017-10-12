<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("IEDR-API/parsers/commands/ModifyCommandParser.php");
include_once("IEDR-API/parsers/common/DomainNsParser.php");
include_once("IEDR-API/parsers/common/DomainContactParser.php");
include_once("IEDR-API/parsers/common/DomainHolderParser.php");

class DomainModifyCommandParser extends ModifyCommandParser {

    function toXml($object) {
        $node = $this->document->createElement("domain:modify");
        $node->setAttribute("xmlns:domain", "http://www.domainregistry.ie/ieapi-domain-1.8");
        $node->setAttribute("xsi:schemaLocation",
            "http://www.domainregistry.ie/ieapi-domain-1.8 ieapi-domain-1.8.xsd");
        $tmpNode = $this->document->createElement("domain:name", $object->getName());
        $node->appendChild($tmpNode);
        $nsAdds = $object->getNssAdds();
        $adminAdds = $object->getAdminCAdds();
        $techsAdds = $object->getTechCAdds();
        if ((count($nsAdds) + count($adminAdds) + count($techsAdds)) > 0) {
            $addNode = $this->document->createElement("domain:add");
            foreach ($nsAdds as $add) {
                $nsNode = DomainNsParser::toXml($this->document, $add);
                $addNode->appendChild($nsNode);
            }
            foreach ($adminAdds as $add) {
                if (is_object($add)) {
                    $add = $add->getName();
                }
                $contactNode =
                    DomainContactParser::toXml($this->document, $add, DomainContact::$TYPE_ADMIN);
                $addNode->appendChild($contactNode);
            }
            foreach ($techsAdds as $add) {
                if (is_object($add)) {
                    $add = $add->getName();
                }
                $contactNode =
                    DomainContactParser::toXml($this->document, $add, DomainContact::$TYPE_TECH);
                $addNode->appendChild($contactNode);
            }
            $node->appendChild($addNode);
        }
        $nsRems = $object->getNssRems();
        $adminsRems = $object->getAdminCRems();
        $techsRems = $object->getTechCRems();
        if ((count($nsRems) + count($adminsRems) + count($techsRems)) > 0) {
            $remNode = $this->document->createElement("domain:rem");
            foreach ($nsRems as $rem) {
                $nsNode = DomainNsParser::toXml($this->document, $rem);
                $remNode->appendChild($nsNode);
            }
            foreach ($adminsRems as $rem) {
                if (is_object($rem)) {
                    $rem = $rem->getName();
                }
                $contactNode =
                    DomainContactParser::toXml($this->document, $rem, DomainContact::$TYPE_ADMIN);
                $remNode->appendChild($contactNode);
            }
            foreach ($techsRems as $rem) {
                if (is_object($rem)) {
                    $rem = $rem->getName();
                }
                $contactNode =
                    DomainContactParser::toXml($this->document, $rem, DomainContact::$TYPE_TECH);
                $remNode->appendChild($contactNode);
            }
            $node->appendChild($remNode);
        }
        if ($object->getHolder() || $object->issetRenewalMode()
            || $object->issetRenew()
        ) {
            $chgNode = $this->document->createElement("domain:chg");
            if ($object->getHolder()) {
                $holderNode = DomainHolderParser::toXml($this->document, $object->getHolder());
                $chgNode->appendChild($holderNode);
            }
            if ($object->issetRenewalMode()) {
                $arNode =
                    $this->document->createElement("domain:renewalMode", $object->getRenewalMode());
                $chgNode->appendChild($arNode);
            }
            if ($object->issetRenew()) {
                $rNode =
                    $this->document->createElement("domain:renew", $object->getRenew() ? "1" : "0");
                $chgNode->appendChild($rNode);
            }
            $node->appendChild($chgNode);
        }
        $this->command = $node;

        return parent::toXml($object);
    }

}
