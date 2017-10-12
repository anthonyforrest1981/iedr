<?php

require_once("IEDR-API/bus/responses/RegistrantTransferSellRequestDataResponse.php");
require_once("ResDataParser.php");
require_once("IEDR-API/parsers/common/IeapiHolderParser.php");

class RegistrantTransferSellRequestDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new RegistrantTransferSellRequestDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "registrantTransferSellRequest:id":
                    $text = $subNode->firstChild;
                    $obj->setId(trim($text->wholeText));
                    break;
                case "registrantTransferSellRequest:domainName":
                    $text = $subNode->firstChild;
                    $obj->setDomainName(trim($text->wholeText));
                    break;
                case "registrantTransferSellRequest:holder":
                    $holder = IeapiHolderParser::fromDomNode($subNode);
                    $obj->setHolder($holder);
                    break;
                case "registrantTransferSellRequest:crDate":
                    $text = $subNode->firstChild;
                    $obj->setCreationDate(trim($text->wholeText));
                    break;
                case "registrantTransferSellRequest:compDate":
                    $text = $subNode->firstChild;
                    $obj->setCompletionDate(trim($text->wholeText));
                    break;
            }
        }

        return $obj;
    }

}