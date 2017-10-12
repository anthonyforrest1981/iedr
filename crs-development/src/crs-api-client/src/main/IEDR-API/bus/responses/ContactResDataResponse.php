<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("Response.php");

class ContactResDataResponse extends Response {

    private $page;
    private $totalPages;
    private $ids = array();

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

    function getIds() {
        return $this->ids;
    }

    function setIds($ids) {
        $this->ids = $ids;
    }

    function addId($id) {
        $this->ids[] = $id;
    }
}
