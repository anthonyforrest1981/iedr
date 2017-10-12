<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainHolder.php");

class DomainHolderParser {

    function toXml($doc, $object) {
        $holder = $doc->createElement("domain:holder");
        if ($object->getName()) {
            $holderName = $doc->createElement("domain:holderName");
            $holderName->appendChild($doc->createTextNode($object->getName()));
            $holder->appendChild($holderName);
        }
        if ($object->getType()) {
            $holderType = $doc->createElement("domain:holderType");
            $holderType->appendChild($doc->createTextNode($object->getType()));
            $holder->appendChild($holderType);
        }
        if ($object->getRemark()) {
            $holderRemarks = $doc->createElement("domain:holderRemarks");
            $holderRemarks->appendChild($doc->createTextNode($object->getRemark()));
            $holder->appendChild($holderRemarks);
        }

        return $holder;
    }

    function fromDomNode($node) {
        $domainHolder = new DomainHolder();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:holderName":
                    $text = $subNode->firstChild;
                    $domainHolder->setName(trim($text->wholeText));
                    break;
                case "domain:holderType":
                    // holder type is never included in the response.
                    break;
                case "domain:holderRemarks":
                    $text = $subNode->firstChild;
                    if (is_object($text)) {
                        $domainHolder->setRemark(trim($text->wholeText));
                    } else {
                        $domainHolder->setRemark("");
                    }
                    break;
            }
        }

        return $domainHolder;
    }

}
