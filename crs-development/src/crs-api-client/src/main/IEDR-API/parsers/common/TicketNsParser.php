<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/TicketNs.php");

class TicketNsParser {

    function toXml($doc, $object) {
        $node = $doc->createElement("ticket:ns");
        $nsNameNode = $doc->createElement("ticket:nsName", $object->getName());
        $node->appendChild($nsNameNode);
        if ($object->getIp()) {
            $nsAddrNode = $doc->createElement("ticket:nsAddr", $object->getIp());
            $node->appendChild($nsAddrNode);
        }
        if ($object->getIpv6()) {
            $nsAddr6Node = $doc->createElement("ticket:nsAddr6", $object->getIpv6());
            $node->appendChild($nsAddr6Node);
        }

        return $node;
    }

    function fromDomNode($node) {
        $ticketNs = new TicketNs();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "ticket:nsName":
                    $text = $subNode->firstChild;
                    $ticketNs->setName(trim($text->wholeText));
                    break;
                case "ticket:nsAddr":
                    $text = $subNode->firstChild;
                    $ticketNs->setIp(trim($text->wholeText));
                    break;
                case "ticket:nsAddr6":
                    $text = $subNode->firstChild;
                    $ticketNs->setIpv6(trim($text->wholeText));
                    break;
            }
        }

        return $ticketNs;
    }

}
