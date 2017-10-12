<?php
include_once("IEDR-API/bus/commands/IeapiContact.php");

class IeapiContactParser {

    function toXml($doc, $object, $namespace) {
        $node = $doc->createElement($namespace . ":contact");

        $tmpNode = $doc->createElement("ieapicom:name", $object->getName());
        $node->appendChild($tmpNode);
        if ($object->getCompanyName()) {
            $tmpNode = $doc->createElement("ieapicom:companyName", $object->getCompanyName());
            $node->appendChild($tmpNode);
        }
        $tmpNode = $doc->createElement("ieapicom:addr", $object->getAddress());
        $node->appendChild($tmpNode);
        if ($object->getCounty()) {
            $tmpNode = $doc->createElement("ieapicom:county", $object->getCounty());
            $node->appendChild($tmpNode);
        }
        $tmpNode = $doc->createElement("ieapicom:country", $object->getCountry());
        $node->appendChild($tmpNode);
        $tmpNode = $doc->createElement("ieapicom:voice", $object->getVoice());
        $node->appendChild($tmpNode);
        if ($object->getFax()) {
            $tmpNode = $doc->createElement("ieapicom:fax", $object->getFax());
            $node->appendChild($tmpNode);
        }
        $tmpNode = $doc->createElement("ieapicom:email", $object->getEmail());
        $node->appendChild($tmpNode);

        return $node;
    }

    function fromDomNode($node) {
        $ieapiContact = new IeapiContact();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "ieapicom:name":
                    $text = $subNode->firstChild;
                    $ieapiContact->setName(trim($text->wholeText));
                    break;
                case "ieapicom:companyName":
                    $text = $subNode->firstChild;
                    $ieapiContact->setCompanyName(trim($text->wholeText));
                    break;
                case "ieapicom:addr":
                    $text = $subNode->firstChild;
                    $ieapiContact->setAddress(trim($text->wholeText));
                    break;
                case "ieapicom:county":
                    $text = $subNode->firstChild;
                    $ieapiContact->setCounty(trim($text->wholeText));
                    break;
                case "ieapicom:country":
                    $text = $subNode->firstChild;
                    $ieapiContact->setCountry(trim($text->wholeText));
                    break;
                case "ieapicom:voice":
                    $text = $subNode->firstChild;
                    $ieapiContact->setVoice(trim($text->wholeText));
                    break;
                case "ieapicom:fax":
                    $text = $subNode->firstChild;
                    $ieapiContact->setFax(trim($text->wholeText));
                    break;
                case "ieapicom:email":
                    $text = $subNode->firstChild;
                    $ieapiContact->setEmail(trim($text->wholeText));
                    break;
            }
        }

        return $ieapiContact;
    }

}
