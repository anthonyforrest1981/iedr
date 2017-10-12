<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("CommandParser.php");

class LoginCommandParser extends CommandParser {

    function toXml($object) {
        $login = $this->document->createElement("login");
        $clID = $this->document->createElement("clID", $object->getUsername());
        $login->appendChild($clID);
        $pw = $this->document->createElement("pw", $object->getPassword());
        $login->appendChild($pw);
        if ($object->getNewPW()) {
            $newPW = $this->document->createElement("newPW", $object->getNewPW());
            $login->appendChild($newPW);
        }
        $this->command = $login;

        return parent::toXml($object);
    }
}
