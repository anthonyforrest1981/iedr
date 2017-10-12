<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/AccountChkDepositDataResponse.php");
include_once("ResDataParser.php");

class AccountChkDepositDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new AccountChkDepositDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "account:actualBalance":
                    $text = $subNode->firstChild;
                    $obj->setActualBalance((float)trim($text->wholeText));
                    break;
                case "account:availableBalance":
                    $text = $subNode->firstChild;
                    $obj->setAvailableBalance((float)trim($text->wholeText));
                    break;
                case "account:transDate":
                    $text = $subNode->firstChild;
                    $obj->setTransDate(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
