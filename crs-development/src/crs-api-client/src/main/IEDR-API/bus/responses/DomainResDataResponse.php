<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class DomainResDataResponse extends Response {

    private $page;
    private $totalPages;
    private $domains = array();

    function getPage() {
        return $this->page;
    }

    function setPage($page) {
        $this->page = $page;
    }

    function getTotalPages() {
        return $this->totalPages;
    }

    function setTotalPages($totalPages) {
        $this->totalPages = $totalPages;
    }

    function getDomains() {
        return $this->domains;
    }

    function setDomains($names) {
        $this->domains = $names;
    }

    function addDomain($domain) {
        $this->domains[] = $domain;
    }
}
