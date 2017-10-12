<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("ApiXmlParserException.php");
include_once("IeapiParser.php");

class ApiXmlParser {

    protected $document;

    function __construct() {
        $this->document = new DOMDocument('1.0', 'UTF-8');
        $this->document->formatOutput = TRUE;
    }

    function toXml($object) {
        switch ($object->getType()) {
            case IeapiObject::$TYPE_COMMAND:
                include_once("commands/" . get_class($object) . "Parser.php");
                break;
            default:
                throw new ApiXmlParserException("Invalid object type for toXml function");
        }
        $parserName = get_class($object) . "Parser";
        $parser = new $parserName();
        $root = $parser->toXml($object);
        $document = $parser->getDocument();
        $document->appendChild($root);

        return $document->saveXML();
    }

    function fromXml($xml) {
        try {
            libxml_use_internal_errors(TRUE);
            $dom = DOMDocument::loadXML($xml);

            if (!$dom) {
                ApiXmlParser::libxml_display_errors();
                throw new ApiXmlParserException("It's not correct XML format!");
            }

            $config = ConfigFactory::getConfig();
            $schemaDir = $config->getAttribute("schemas_dir");
            $schemas = $config->getAttribute("schemas");
            $valid = FALSE;
            foreach ($schemas as $scheme) {
                if ($dom->schemaValidate($schemaDir . "/" . $scheme)) {
                    $valid = TRUE;
                    break;
                }
            }
            if (!$valid) {
                ApiXmlParser::libxml_display_errors();
                throw new ApiXmlParserException("Scheme validation problem.");
            }

            $ieapi = self::findElementNode($dom);

            return IeapiParser::fromXmlNode($ieapi);
        } catch (DOMException $e) {
            ApiXmlParser::display_errors();
            throw new ApiXmlParserException($e->getMessage());
        }
    }

    function libxml_display_errors() {
        print "libxml_display_errors";
        $errors = libxml_get_errors();
        foreach ($errors as $error) {
            print ApiXmlParser::libxml_display_error($error);
        }
        libxml_clear_errors();
    }

    function libxml_display_error($error) {
        $return = "\n";
        switch ($error->level) {
            case LIBXML_ERR_WARNING:
                $return .= "Warning $error->code: ";
                break;
            case LIBXML_ERR_ERROR:
                $return .= "Error $error->code: ";
                break;
            case LIBXML_ERR_FATAL:
                $return .= "Fatal Error $error->code: ";
                break;
        }
        $return .= trim($error->message);
        if ($error->file) {
            $return .= " in $error->file";
        }
        $return .= " on line $error->line\n";

        return $return;
    }

    function findElementNode($root) {
        foreach ($root->childNodes as $node) {
            if ($node->nodeType == XML_ELEMENT_NODE) {
                return $node;
            }
        }

        return NULL;
    }

    function getDocument() {
        return $this->document;
    }
}
