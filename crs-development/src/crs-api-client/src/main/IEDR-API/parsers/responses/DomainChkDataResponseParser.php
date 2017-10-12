<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/DomainChkDataResponse.php");
include_once("IEDR-API/bus/responses/DomainCd.php");
include_once("IEDR-API/bus/responses/Reason.php");
include_once("ResDataParser.php");

class DomainChkDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new DomainChkDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:cd":
                    $cd = new DomainCd();
                    foreach ($subNode->childNodes as $cdNode) {
                        switch ($cdNode->nodeName) {
                            case "domain:name":
                                $text = $cdNode->firstChild;
                                $cd->setName(trim($text->wholeText));
                                foreach ($cdNode->attributes as $attr) {
                                    if (!strcmp($attr->name, "avail")) {
                                        $cd->setAvail($attr->value);
                                    }
                                }
                                break;
                            case "domain:reason":
                                foreach ($cdNode->attributes as $attr) {
                                    if (!strcmp($attr->name, "code")) {
                                        $cd->setReasonCode($attr->value);
                                    }
                                }
                                break;

                        }
                    }
                    $obj->addCd($cd);
                    break;
            }
        }

        return $obj;
    }
}
