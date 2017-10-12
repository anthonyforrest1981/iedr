<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/ContactResDataResponse.php");
include_once("ResDataParser.php");

class ContactResDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new ContactResDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "contact:page":
                    $text = $subNode->firstChild;
                    $obj->setPage((int)$text->wholeText);
                    break;
                case "contact:totalPages":
                    $text = $subNode->firstChild;
                    $obj->setTotalPages((int)$text->wholeText);
                    break;
                case "contact:id":
                    $text = $subNode->firstChild;
                    $obj->addId(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
