<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/DomainInfDataResponse.php");
include_once("IEDR-API/parsers/common/DomainNsParser.php");
include_once("IEDR-API/parsers/common/DomainHolderParser.php");
include_once("IEDR-API/parsers/common/DomainContactParser.php");
include_once("IEDR-API/parsers/responses/DsmStateParser.php");
include_once("ResDataParser.php");

class DomainInfDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new DomainInfDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:name":
                    $text = $subNode->firstChild;
                    $obj->setName(trim($text->wholeText));
                    break;
                case "domain:account":
                    $text = $subNode->firstChild;
                    $obj->setAccount(trim($text->wholeText));
                    break;
                case "domain:regDate":
                    $text = $subNode->firstChild;
                    $obj->setRegDate(trim($text->wholeText));
                    break;
                case "domain:renDate":
                    $text = $subNode->firstChild;
                    $obj->setRenDate(trim($text->wholeText));
                    break;
                case "domain:suspendDate":
                    $text = $subNode->firstChild;
                    $obj->setSuspendDate(trim($text->wholeText));
                    break;
                case "domain:deleteDate":
                    $text = $subNode->firstChild;
                    $obj->setDeleteDate(trim($text->wholeText));
                    break;
                case "domain:lockDate":
                    $text = $subNode->firstChild;
                    $obj->setLockDate(trim($text->wholeText));
                    break;
                case "domain:lockRenewDate":
                    $text = $subNode->firstChild;
                    $obj->setLockRenewDate(trim($text->wholeText));
                    break;
                case "domain:authCode":
                    $text = $subNode->firstChild;
                    $obj->setAuthCode(trim($text->wholeText));
                    break;
                case "domain:ns":
                    $ns = DomainNsParser::fromDomNode($subNode);
                    $obj->addNs($ns);
                    break;
                case "domain:holder":
                    $holder = DomainHolderParser::fromDomNode($subNode);
                    $obj->setHolder($holder);
                    break;
                case "domain:contact":
                    $contact = DomainContactParser::fromDomNode($subNode);
                    switch ($contact->getType()) {
                        case DomainContact::$TYPE_ADMIN:
                            $obj->addAdmin($contact->getName());
                            break;
                        case DomainContact::$TYPE_TECH:
                            $obj->addTech($contact->getName());
                            break;
                    }
                    break;
                case "domain:status":
                    $status = DsmStateParser::fromDomNode($subNode);
                    $obj->setStatus($status);
                    break;
            }
        }

        return $obj;
    }
}
