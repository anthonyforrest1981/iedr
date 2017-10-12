<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class Value {

    private $tag;
    private $namespace;
    private $value;

    function Value($tag = '', $namespace = '', $value = '') {
        $this->tag = $tag;
        $this->namespace = $namespace;
        $this->value = $value;
    }

    function getTag() {
        return $this->tag;
    }

    function setTag($tag) {
        $this->tag = $tag;
    }

    function getNamespace() {
        return $this->namespace;
    }

    function setNamespace($namespace) {
        $this->namespace = $namespace;
    }

    function getValue() {
        return $this->value;
    }

    function setValue($value) {
        $this->value = $value;
    }
}
