<?php
require_once("IEDR-API/bus/responses/RegistrantTransferBuyRequestInfoDataResponse.php");
require_once("IEDR-API/parsers/common/IeapiHolderParser.php");
require_once("IEDR-API/parsers/common/IeapiContactParser.php");
require_once("ResDataParser.php");

class RegistrantTransferBuyRequestInfoDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $requestData = new RegistrantTransferBuyRequestInfoDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "registrantTransferBuyRequest:id":
                    $text = $subNode->firstChild;
                    $requestData->setId(trim($text->wholeText));
                    break;
                case "registrantTransferBuyRequest:domainName":
                    $text = $subNode->firstChild;
                    $requestData->setDomainName(trim($text->wholeText));
                    break;
                case "registrantTransferBuyRequest:holder":
                    $holderNode = IeapiHolderParser::fromDomNode($subNode);
                    $requestData->setHolder($holderNode);
                    break;
                case "registrantTransferBuyRequest:crDate":
                    $text = $subNode->firstChild;
                    $requestData->setCrDate(trim($text->wholeText));
                    break;
                case "registrantTransferBuyRequest:contact":
                    $contact = IeapiContactParser::fromDomNode($subNode);
                    $requestData->setContact($contact);
                    break;
                case "registrantTransferBuyRequest:status":
                    $text = $subNode->firstChild;
                    $requestData->setStatus(trim($text->wholeText));
                    break;
            }
        }

        return $requestData;
    }

}
