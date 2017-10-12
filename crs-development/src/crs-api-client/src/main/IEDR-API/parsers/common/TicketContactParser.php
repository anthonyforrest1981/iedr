<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/TicketContact.php");

class TicketContactParser {

    function toXml($doc, $object, $type) {
        $node = $doc->createElement("ticket:contact", $object);
        if ($type == TicketContact::$TYPE_ADMIN) {
            $node->setAttribute("type", "admin");
        } else {
            if ($type == TicketContact::$TYPE_TECH) {
                $node->setAttribute("type", "tech");
            }
        }

        return $node;
    }

    function fromDomNode($node) {
        $ticketContact = new TicketContact();
        $text = $node->firstChild;
        $ticketContact->setName(trim($text->wholeText));
        foreach ($node->attributes as $attr) {
            if (!strcmp($attr->name, "type")) {
                switch ($attr->value) {
                    case "admin":
                        $ticketContact->setType(TicketContact::$TYPE_ADMIN);
                        break;
                    case "tech":
                        $ticketContact->setType(TicketContact::$TYPE_TECH);
                        break;
                }
            }
        }

        return $ticketContact;
    }

}
