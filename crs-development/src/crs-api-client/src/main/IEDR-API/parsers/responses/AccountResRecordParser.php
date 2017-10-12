<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/AccountResRecord.php");

class AccountResRecordParser {

    function fromDomNode($node) {
        $resRecord = new AccountResRecord();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "account:domain":
                    $text = $subNode->firstChild;
                    $resRecord->setDomain(trim($text->wholeText));
                    break;
                case "account:holder":
                    $text = $subNode->firstChild;
                    $resRecord->setHolder(trim($text->wholeText));
                    break;
                case "account:status":
                    $text = $subNode->firstChild;
                    $resRecord->setStatus(trim($text->wholeText));
                    break;
                case "account:msdDate":
                    $text = $subNode->firstChild;
                    $resRecord->setMsdDate(trim($text->wholeText));
                    break;
                case "account:trnDate":
                    $text = $subNode->firstChild;
                    $resRecord->setTrnDate(trim($text->wholeText));
                    break;
                case "account:invoice":
                    $text = $subNode->firstChild;
                    $resRecord->setInvoice(trim($text->wholeText));
                    break;
                case "account:regDate":
                    $text = $subNode->firstChild;
                    $resRecord->setRegDate(trim($text->wholeText));
                    break;
                case "account:renDate":
                    $text = $subNode->firstChild;
                    $resRecord->setRenDate(trim($text->wholeText));
                    break;
                case "account:amount":
                    $text = $subNode->firstChild;
                    $resRecord->setAmount(trim($text->wholeText));
                    break;
                case "account:vat":
                    $text = $subNode->firstChild;
                    $resRecord->setVat(trim($text->wholeText));
                    break;
            }
        }

        return $resRecord;
    }

}
