<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainNs.php");

class DomainNsParser {

    function toXml($doc, $object) {
        $node = $doc->createElement("domain:ns");
        $nsNameNode = $doc->createElement("domain:nsName", $object->getName());
        $node->appendChild($nsNameNode);
        if ($object->getIp()) {
            $nsAddrNode = $doc->createElement("domain:nsAddr", $object->getIp());
            $node->appendChild($nsAddrNode);
        }
        if ($object->getIpv6()) {
            $nsAddr6Node = $doc->createElement("domain:nsAddr6", $object->getIpv6());
            $node->appendChild($nsAddr6Node);
        }

        return $node;
    }

    function fromDomNode($node) {
        $domainNs = new DomainNs();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:nsName":
                    $text = $subNode->firstChild;
                    $domainNs->setName(trim($text->wholeText));
                    break;
                case "domain:nsAddr":
                    $text = $subNode->firstChild;
                    $domainNs->setIp(trim($text->wholeText));
                    break;
                case "domain:nsAddr6":
                    $text = $subNode->firstChild;
                    $domainNs->setIpv6(trim($text->wholeText));
                    break;
            }
        }

        return $domainNs;
    }

}
