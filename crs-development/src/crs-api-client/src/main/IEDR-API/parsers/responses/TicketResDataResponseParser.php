<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/TicketResDataResponse.php");
include_once("ResDataParser.php");

class TicketResDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new TicketResDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "ticket:page":
                    $text = $subNode->firstChild;
                    $obj->setPage((int)$text->wholeText);
                    break;
                case "ticket:totalPages":
                    $text = $subNode->firstChild;
                    $obj->setTotalPages((int)$text->wholeText);
                    break;
                case "ticket:domain":
                    $text = $subNode->firstChild;
                    $obj->addDomain(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
