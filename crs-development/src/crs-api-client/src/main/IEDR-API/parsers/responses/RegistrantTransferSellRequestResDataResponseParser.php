<?php

require_once("IEDR-API/bus/responses/RegistrantTransferSellRequestResDataResponse.php");
require_once("RegistrantTransferSellRequestDataResponseParser.php");
require_once("ResDataParser.php");

class RegistrantTransferSellRequestResDataResponseParser extends ResDataParser {

    function fromDomNode($node) {
        $obj = new RegistrantTransferSellRequestResDataResponse();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "registrantTransferSellRequest:page":
                    $text = $subNode->firstChild;
                    $obj->setPage((int)$text->wholeText);
                    break;
                case "registrantTransferSellRequest:totalPages":
                    $text = $subNode->firstChild;
                    $obj->setTotalPages((int)$text->wholeText);
                    break;
                case "registrantTransferSellRequest:request":
                    $request = RegistrantTransferSellRequestDataResponseParser::fromDomNode($subNode);
                    $obj->addRequest($request);
                    break;
            }
        }

        return $obj;
    }
}