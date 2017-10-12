<?php

include_once("IEDR-API/bus/commands/DomainPayFromDeposit.php");

class DomainPayFromDepositParser {

    function toXml($doc, $object) {
        $node = $doc->createElement("domain:payFromDeposit");
        if ($object->isAutorenewEnabled()) {
            $node->setAttribute("autorenewEnabled", "true");
        }
        return $node;
    }

}

