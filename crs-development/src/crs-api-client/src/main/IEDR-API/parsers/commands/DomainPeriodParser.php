<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/DomainPeriod.php");

class DomainPeriodParser {

    function toXml($doc, $object) {
        $node = $doc->createElement("domain:period", $object->getPeriod());
        $node->setAttribute("unit", $object->getUnit());

        return $node;
    }

}
