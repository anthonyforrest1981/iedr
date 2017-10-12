<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/DomainCreDataResponse.php");
include_once("ResDataParser.php");

class DomainCreDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new DomainCreDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:name":
                    $text = $subNode->firstChild;
                    $obj->setName(trim($text->wholeText));
                    break;
                case "domain:creDate":
                    $text = $subNode->firstChild;
                    $obj->setCreDate(trim($text->wholeText));
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
