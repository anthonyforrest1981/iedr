<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainContact.php");

class DomainContactParser {

    function toXml($doc, $object, $type) {
        $node = $doc->createElement("domain:contact", $object);
        if ($type == DomainContact::$TYPE_ADMIN) {
            $node->setAttribute("type", "admin");
        } else {
            if ($type == DomainContact::$TYPE_TECH) {
                $node->setAttribute("type", "tech");
            }
        }

        return $node;
    }

    function fromDomNode($node) {
        $domainContact = new DomainContact();
        $text = $node->firstChild;
        $domainContact->setName(trim($text->wholeText));
        foreach ($node->attributes as $attr) {
            if (!strcmp($attr->name, "type")) {
                switch ($attr->value) {
                    case "admin":
                        $domainContact->setType(DomainContact::$TYPE_ADMIN);
                        break;
                    case "tech":
                        $domainContact->setType(DomainContact::$TYPE_TECH);
                        break;
                }
            }
        }

        return $domainContact;
    }

}
