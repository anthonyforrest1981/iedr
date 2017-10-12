<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class DomainHolder {

    private $name;
    private $type;
    private $remark;

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getType() {
        return $this->type;
    }

    function setType($type) {
        $this->type = $type;
    }

    function getRemark() {
        return $this->remark;
    }

    function setRemark($remark) {
        $this->remark = $remark;
    }
}