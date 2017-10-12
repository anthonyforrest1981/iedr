<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/DomainResDataResponse.php");
include_once("IEDR-API/parsers/responses/DomainShortInfDataResponseParser.php");
include_once("ResDataParser.php");

class DomainResDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $domainParser = new DomainShortInfDataResponseParser();
        $obj = new DomainResDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:page":
                    $text = $subNode->firstChild;
                    $obj->setPage((int)$text->wholeText);
                    break;
                case "domain:totalPages":
                    $text = $subNode->firstChild;
                    $obj->setTotalPages((int)$text->wholeText);
                    break;
                case "domain:domain":
                    $domain = $domainParser->fromDomNode($subNode);
                    $obj->addDomain($domain);
                    break;
            }
        }

        return $obj;
    }
}
