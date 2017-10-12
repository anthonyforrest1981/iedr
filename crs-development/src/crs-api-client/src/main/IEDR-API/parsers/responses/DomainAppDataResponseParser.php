<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/DomainAppDataResponse.php");
include_once("ResDataParser.php");

class DomainAppDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new DomainAppDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:name":
                    $text = $subNode->firstChild;
                    $obj->setName(trim($text->wholeText));
                    break;
                case "domain:appNumber":
                    $text = $subNode->firstChild;
                    $obj->setAppNumber(trim($text->wholeText));
                    break;
                case "domain:appDate":
                    $text = $subNode->firstChild;
                    $obj->setAppDate(trim($text->wholeText));
                    break;
                case "domain:exDate":
                    $text = $subNode->firstChild;
                    $obj->setExDate(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
