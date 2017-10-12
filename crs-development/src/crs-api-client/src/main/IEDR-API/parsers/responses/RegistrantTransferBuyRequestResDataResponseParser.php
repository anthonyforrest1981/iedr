<?php
require_once("IEDR-API/bus/responses/RegistrantTransferBuyRequestResDataResponse.php");
require_once("RegistrantTransferBuyRequestRequestDataResponseParser.php");
require_once("ResDataParser.php");

class RegistrantTransferBuyRequestResDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $resData = new RegistrantTransferBuyRequestResDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "registrantTransferBuyRequest:page":
                    $text = $subNode->firstChild;
                    $resData->setPage((int)$text->wholeText);
                    break;
                case "registrantTransferBuyRequest:totalPages":
                    $text = $subNode->firstChild;
                    $resData->setTotalPages((int)$text->wholeText);
                    break;
                case "registrantTransferBuyRequest:request":
                    $request = RegistrantTransferBuyRequestRequestDataResponseParser::fromDomNode($subNode);
                    $resData->addRequest($request);
                    break;
            }
        }

        return $resData;
    }

}
