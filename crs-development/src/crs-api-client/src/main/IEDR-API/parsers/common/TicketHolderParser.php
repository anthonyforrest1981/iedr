<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/TicketHolder.php");

class TicketHolderParser {

    function toXml($doc, $object) {
        $holder = $doc->createElement("ticket:holder");
        if ($object->getName()) {
            $holderName = $doc->createElement("ticket:holderName");
            $holderName->appendChild($doc->createTextNode($object->getName()));
            $holder->appendChild($holderName);
        }
        if ($object->getType()) {
            $holderType = $doc->createElement("ticket:holderType");
            $holderType->appendChild($doc->createTextNode($object->getType()));
            $holder->appendChild($holderType);
        }
        if ($object->getRemark()) {
            $holderRemarks = $doc->createElement("ticket:holderRemarks");
            $holderRemarks->appendChild($doc->createTextNode($object->getRemark()));
            $holder->appendChild($holderRemarks);
        }

        return $holder;
    }

    function fromDomNode($node) {
        $ticketHolder = new TicketHolder();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "ticket:holderName":
                    $text = $subNode->firstChild;
                    $ticketHolder->setName(trim($text->wholeText));
                    break;
                case "ticket:holderType":
                    // holder type is never included in the response.
                    break;
                case "ticket:holderRemarks":
                    $text = $subNode->firstChild;
                    if (is_object($text)) {
                        $ticketHolder->setRemark(trim($text->wholeText));
                    } else {
                        $ticketHolder->setRemark("");
                    }
                    break;
            }
        }

        return $ticketHolder;
    }

}
