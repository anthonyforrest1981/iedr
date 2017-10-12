<?php
include_once("IEDR-API/bus/commands/IeapiHolder.php");

class IeapiHolderParser {

    function toXml($doc, $object, $namespace) {
        $holder = $doc->createElement($namespace . ":holder");
        if ($object->getName()) {
            $holderName = $doc->createElement("ieapicom:holderName");
            $holderName->appendChild($doc->createTextNode($object->getName()));
            $holder->appendChild($holderName);
        }
        if ($object->getType()) {
            $holderType = $doc->createElement("ieapicom:holderType");
            $holderType->appendChild($doc->createTextNode($object->getType()));
            $holder->appendChild($holderType);
        }
        if ($object->getRemark()) {
            $holderRemarks = $doc->createElement("ieapicom:holderRemarks");
            $holderRemarks->appendChild($doc->createTextNode($object->getRemark()));
            $holder->appendChild($holderRemarks);
        }

        return $holder;
    }

    function fromDomNode($node) {
        $ieapiHolder = new IeapiHolder();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "ieapicom:holderName":
                    $text = $subNode->firstChild;
                    $ieapiHolder->setName(trim($text->wholeText));
                    break;
                case "ieapicom:holderType":
                    // holder type is never included in the response.
                    break;
                case "ieapicom:holderRemarks":
                    $text = $subNode->firstChild;
                    if (is_object($text)) {
                        $ieapiHolder->setRemark(trim($text->wholeText));
                    } else {
                        $ieapiHolder->setRemark("");
                    }
                    break;
            }
        }

        return $ieapiHolder;
    }

}
