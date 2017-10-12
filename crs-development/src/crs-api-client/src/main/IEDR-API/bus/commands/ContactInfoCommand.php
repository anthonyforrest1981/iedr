<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("InfoCommandObject.php");

class ContactInfoCommand extends InfoCommandObject {

    private $id;

    function ContactInfoCommand($id = '') {
        parent::__construct();
        $this->id = $id;
    }

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }
}
