<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("commands/CommandParser.php");
include_once("responses/ResponseParser.php");
include_once("ApiXmlParser.php");

abstract class IeapiParser extends ApiXmlParser {

    function toXml($object) {
        $ieapi = $this->document->createElement("ieapi");
        $ieapi->setAttribute("xmlns", "http://www.domainregistry.ie/ieapi-1.8");
        $ieapi->setAttributeNS('http://www.w3.org/2001/XMLSchema-instance', 'xsi:schemaLocation',
            'http://www.domainregistry.ie/ieapi-1.8 ieapi-1.8.xsd');

        return $ieapi;
    }

    function fromXmlNode($node) {
        $node = self::findElementNode($node);
        switch ($node->nodeName) {
            case "response":
                $obj = ResponseParser::fromDomNode($node);
                break;
            default:
                throw new ApiXmlParserException("Unknown tag: " . $node->nodeName);
        }

        return $obj;
    }
}
