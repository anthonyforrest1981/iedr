<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/AccountPayFromDepositDataResponse.php");
include_once("ResDataParser.php");

class AccountPayFromDepositDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new AccountPayFromDepositDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "account:fee":
                    $text = $subNode->firstChild;
                    $obj->setFee(trim($text->wholeText));
                    break;
                case "account:vat":
                    $text = $subNode->firstChild;
                    $obj->setVat(trim($text->wholeText));
                    break;
                case "account:total":
                    $text = $subNode->firstChild;
                    $obj->setTotal(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
