<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CheckCommandObject.php");

class DomainCheckCommand extends CheckCommandObject {

    private $domains = array();

    function getDomains() {
        return $this->domains;
    }

    function setDomains($domains) {
        $this->domains = $domains;
    }

    function addDomain($domain) {
        $this->domains[] = $domain;
    }
}
