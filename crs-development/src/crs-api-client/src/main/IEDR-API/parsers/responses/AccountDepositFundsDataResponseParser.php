<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/AccountDepositFundsDataResponse.php");
include_once("ResDataParser.php");

class AccountDepositFundsDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new AccountDepositFundsDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "account:oldValue":
                    $text = $subNode->firstChild;
                    $obj->setOldValue((float)trim($text->wholeText));
                    break;
                case "account:newValue":
                    $text = $subNode->firstChild;
                    $obj->setNewValue((float)trim($text->wholeText));
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
