<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/TicketInfDataResponse.php");
include_once("IEDR-API/parsers/common/TicketNsParser.php");
include_once("IEDR-API/parsers/common/TicketHolderParser.php");
include_once("IEDR-API/parsers/common/TicketContactParser.php");
include_once("ResDataParser.php");

class TicketInfDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new TicketInfDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "ticket:name":
                    $text = $subNode->firstChild;
                    $obj->setName(trim($text->wholeText));
                    break;
                case "ticket:type":
                    $text = $subNode->firstChild;
                    $obj->setTicketType(trim($text->wholeText));
                    break;
                case "ticket:hostmasterStatus":
                    $text = $subNode->firstChild;
                    $obj->setHostmasterStatus(trim($text->wholeText));
                    break;
                case "ticket:dnsStatus":
                    $text = $subNode->firstChild;
                    $obj->setDnsStatus(trim($text->wholeText));
                    break;
                case "ticket:account":
                    $text = $subNode->firstChild;
                    $obj->setAccount(trim($text->wholeText));
                    break;
                case "ticket:regDate":
                    $text = $subNode->firstChild;
                    $obj->setRegDate(trim($text->wholeText));
                    break;
                case "ticket:renDate":
                    $text = $subNode->firstChild;
                    $obj->setRenDate(trim($text->wholeText));
                    break;
                case "ticket:hostmasterRemarks":
                    $text = $subNode->firstChild;
                    if (is_object($text)) {
                        $obj->setHostmasterRemark(trim($text->wholeText));
                    } else {
                        $obj->setHostmasterRemark("");
                    }
                    break;
                case "ticket:ns":
                    $ns = TicketNsParser::fromDomNode($subNode);
                    $obj->addNs($ns);
                    break;
                case "ticket:holder":
                    $holder = TicketHolderParser::fromDomNode($subNode);
                    $obj->setHolder($holder);
                    break;
                case "ticket:contact":
                    $contact = TicketContactParser::fromDomNode($subNode);
                    switch ($contact->getType()) {
                        case TicketContact::$TYPE_ADMIN:
                            $obj->addAdmin($contact->getName());
                            break;
                        case TicketContact::$TYPE_TECH:
                            $obj->addTech($contact->getName());
                            break;
                    }
                    break;
            }
        }

        return $obj;
    }
}
