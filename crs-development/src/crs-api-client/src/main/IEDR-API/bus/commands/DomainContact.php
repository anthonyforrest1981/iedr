<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class DomainContact {

    static public $TYPE_ADMIN = 1;
    static public $TYPE_TECH = 2;
    private $type;
    private $name;

    function DomainContact($name = '', $type = '') {
        $this->name = $name;
        $this->type = $type;
    }

    function getType() {
        return $this->type;
    }

    function setType($type) {
        $this->type = $type;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }
}
