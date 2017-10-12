<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/DomainShortInfDataResponse.php");
include_once("IEDR-API/parsers/responses/DsmStateParser.php");
include_once("ResDataParser.php");

class DomainShortInfDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new DomainShortInfDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:name":
                    $text = $subNode->firstChild;
                    $obj->setName(trim($text->wholeText));
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
                case "domain:transDate":
                    $text = $subNode->firstChild;
                    $obj->setTransDate(trim($text->wholeText));
                    break;
                case "domain:status":
                    $status = DsmStateParser::fromDomNode($subNode);
                    $obj->setStatus($status);
                    break;
                case "domain:reservationPending":
                    $text = $subNode->firstChild;
                    $obj->setReservationPending(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
