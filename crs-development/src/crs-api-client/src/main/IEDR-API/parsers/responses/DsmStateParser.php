<?php

include_once("IEDR-API/bus/responses/DsmState.php");

class DsmStateParser extends ResDataParser {

    function fromDomNode($node) {
        $state = new DsmState();
        foreach ($node->childNodes as $subNode) {
            switch ($subNode->nodeName) {
                case "domain:locked":
                    $state->locked = DsmStateParser::text($subNode);
                    break;
                case "domain:holderType":
                    $state->holderType = DsmStateParser::text($subNode);
                    break;
                case "domain:renewalMode":
                    $state->renewalMode = DsmStateParser::text($subNode);
                    break;
                case "domain:renewalStatus":
                    $state->renewalStatus = DsmStateParser::text($subNode);
                    break;
                case "domain:published":
                    $state->published = DsmStateParser::text($subNode);
                    break;
            }
        }

        return $state;
    }

    function text($node) {
        $text = $node->firstChild;

        return trim($text->wholeText);
    }

}
