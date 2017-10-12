<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class DomainChkDataResponse extends Response {

    private $cds = array();

    function DomainChkData() {
    }

    function getCds() {
        return $this->cds;
    }

    function setCds($cds) {
        $this->cds = $cds;
    }

    function addCd($cd) {
        $this->cds[] = $cd;
    }
}
