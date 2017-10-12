<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("InfoCommandObject.php");

class TicketInfoCommand extends InfoCommandObject {

    private $name;
    private $fillNsAddr6;

    function TicketInfoCommand($name = '', $fillNsAddr6 = '') {
        parent::__construct();
        $this->name = $name;
        $this->fillNsAddr6 = $fillNsAddr6;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function setFillNsAddr6($fillNsAddr6) {
        $this->fillNsAddr6 = $fillNsAddr6;
    }

    function fillNsAddr6() {
        return $this->fillNsAddr6;
    }
}
