<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/responses/Response.php");
include_once("IEDR-API/bus/responses/Value.php");
include_once("IEDR-API/parsers/IeapiParser.php");

class ResponseParser extends IeapiParser {

    function toXml($node) {
        throw new Exception('toXml called on ResponseParser');
    }

    function fromDomNode($node) {
        $result = NULL;
        $obj = NULL;
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "result":
                    $result = new Result();
                    foreach ($subNode->attributes as $attr) {
                        switch ($attr->name) {
                            case "code":
                                $result->setCode($attr->value);
                                break;
                        }
                    }
                    foreach ($subNode->childNodes as $cn) {
                        switch ($cn->nodeName) {
                            case "value":
                                $value = new Value();
                                foreach ($cn->childNodes as $valueNode) {
                                    if ($valueNode->nodeType != XML_ELEMENT_NODE) {
                                        continue;
                                    }
                                    $value->setTag($valueNode->nodeName);
                                    $value->setNamespace($valueNode->namespaceURI);
                                    $text = $valueNode->firstChild;
                                    if ($text === null) {
                                        $value->setValue($text);
                                    } else {
                                        $value->setValue(trim($text->wholeText));
                                    }
                                    $result->setValue($value);
                                    break;
                                }
                                break;
                            case "reason":
                                foreach ($cn->attributes as $attr) {
                                    if (!strcmp("code", $attr->name)) {
                                        $result->setReasonCode($attr->value);
                                        break;
                                    }
                                }
                                $text = $cn->firstChild;
                                $result->setReasonMsg(trim($text->wholeText));
                                break;
                        }
                    }
                    break;
                case "resData":
                    include_once("ResDataParser.php");
                    $obj = ResDataParser::fromDomNode($subNode);
                    break;
            }
        }
        if (!$obj) {
            $obj = new Response();
        }
        $obj->setResult($result);

        return $obj;
    }

}
