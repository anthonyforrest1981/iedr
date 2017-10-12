<?php
require_once("IEDR-API/bus/responses/RegistrantTransferBuyRequestRequestDataResponse.php");
require_once("IEDR-API/parsers/common/IeapiHolderParser.php");
require_once("ResDataParser.php");

class RegistrantTransferBuyRequestRequestDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $requestData = new RegistrantTransferBuyRequestRequestDataResponse();
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
            }
        }

        return $requestData;
    }

}
