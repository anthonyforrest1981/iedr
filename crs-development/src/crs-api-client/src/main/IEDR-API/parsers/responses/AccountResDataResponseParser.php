<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/AccountResDataResponse.php");
include_once("AccountResRecordParser.php");
include_once("ResDataParser.php");

class AccountResDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new AccountResDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "account:page":
                    $text = $subNode->firstChild;
                    $obj->setPage((int)$text->wholeText);
                    break;
                case "account:totalPages":
                    $text = $subNode->firstChild;
                    $obj->setTotalPages((int)$text->wholeText);
                    break;
                case "account:resRecord":
                    $record = AccountResRecordParser::fromDomNode($subNode);
                    $obj->addResRecord($record);
                    break;
            }
        }

        return $obj;
    }
}
