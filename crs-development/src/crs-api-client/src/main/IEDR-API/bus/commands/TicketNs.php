<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class TicketNs {

    private $name;
    private $ip;
    private $ipv6;

    function TicketNs($name = '', $ip = '', $ipv6 = '') {
        $this->name = $name;
        $this->ip = $ip;
        $this->ipv6 = $ipv6;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getIp() {
        return $this->ip;
    }

    function setIp($ip) {
        $this->ip = $ip;
    }

    function getIpv6() {
        return $this->ipv6;
    }

    function setIpv6($ipv6) {
        $this->ipv6 = $ipv6;
    }
}
