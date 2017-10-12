<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("DeleteCommandObject.php");

class TicketDeleteCommand extends DeleteCommandObject {

    private $name;

    function TicketDeleteCommand($name = '') {
        parent::__construct();
        $this->name = $name;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }
}
