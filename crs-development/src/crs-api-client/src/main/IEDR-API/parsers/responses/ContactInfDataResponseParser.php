<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/ContactInfDataResponse.php");
include_once("ResDataParser.php");

class ContactInfDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new ContactInfDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "contact:id":
                    $text = $subNode->firstChild;
                    $obj->setId(trim($text->wholeText));
                    break;
                case "contact:status":
                    $text = $subNode->firstChild;
                    $obj->setStatus(trim($text->wholeText));
                    break;
                case "contact:name":
                    $text = $subNode->firstChild;
                    $obj->setName(trim($text->wholeText));
                    break;
                case "contact:companyName":
                    $text = $subNode->firstChild;
                    $obj->setCompanyName(trim($text->wholeText));
                    break;
                case "contact:addr":
                    $text = $subNode->firstChild;
                    $obj->setAddress(trim($text->wholeText));
                    break;
                case "contact:county":
                    $text = $subNode->firstChild;
                    $obj->setCounty(trim($text->wholeText));
                    break;
                case "contact:country":
                    $text = $subNode->firstChild;
                    $obj->setCountry(trim($text->wholeText));
                    break;
                case "contact:voice":
                    $text = $subNode->firstChild;
                    $obj->setVoice(trim($text->wholeText));
                    break;
                case "contact:fax":
                    $text = $subNode->firstChild;
                    $obj->setFax(trim($text->wholeText));
                    break;
                case "contact:email":
                    $text = $subNode->firstChild;
                    $obj->setEmail(trim($text->wholeText));
                    break;
                case "contact:account":
                    $text = $subNode->firstChild;
                    $obj->setAccount(trim($text->wholeText));
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
