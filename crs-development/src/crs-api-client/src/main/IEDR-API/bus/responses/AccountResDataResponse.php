<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class AccountResDataResponse extends Response {

    private $page;
    private $totalPages;
    private $resRecords = array();

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

    function getResRecords() {
        return $this->resRecords;
    }

    function setResRecords($records) {
        $this->resRecords = $records;
    }

    function addResRecord($record) {
        $this->resRecords[] = $record;
    }
}
