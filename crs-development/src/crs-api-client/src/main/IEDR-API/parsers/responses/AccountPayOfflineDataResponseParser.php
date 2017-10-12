<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/AccountPayOfflineDataResponse.php");
include_once("ResDataParser.php");

class AccountPayOfflineDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new AccountPayOfflineDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "account:invoice":
                    $text = $subNode->firstChild;
                    $obj->setInvoice(trim($text->wholeText));
                    break;
                case "account:amount":
                    $text = $subNode->firstChild;
                    $obj->setAmount(trim($text->wholeText));
                    break;
                case "account:orderID":
                    $text = $subNode->firstChild;
                    $obj->setOrderID(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }
}
