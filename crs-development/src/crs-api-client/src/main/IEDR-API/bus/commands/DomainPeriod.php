<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class DomainPeriod {

    private $unit;
    private $period;

    function DomainPeriod($period = '', $unit = '') {
        $this->unit = $unit;
        $this->period = $period;
    }

    function getUnit() {
        return $this->unit;
    }

    function setUnit($unit) {
        $this->unit = $unit;
    }

    function getPeriod() {
        return $this->period;
    }

    function setPeriod($period) {
        $this->period = $period;
    }
}
