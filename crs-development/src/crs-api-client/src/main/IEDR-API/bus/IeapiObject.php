<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

abstract class IeapiObject {

    static public $TYPE_COMMAND = 1;
    static public $TYPE_RESPONSE = 2;
    protected $type;

    function getType() {
        return $this->type;
    }

    function setType($type) {
        $this->type = $type;
    }
}
