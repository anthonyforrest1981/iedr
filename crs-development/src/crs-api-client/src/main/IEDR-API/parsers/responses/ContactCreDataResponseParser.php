<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/ContactCreDataResponse.php");
include_once("ResDataParser.php");

class ContactCreDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new ContactCreDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "contact:id":
                    $text = $subNode->firstChild;
                    $obj->setId(trim($text->wholeText));
                    break;
                case "contact:crDate":
                    $text = $subNode->firstChild;
                    $obj->setCrDate(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
