<?php

include_once("CommandObject.php");

class DomainNRPCommand extends CommandObject {

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
