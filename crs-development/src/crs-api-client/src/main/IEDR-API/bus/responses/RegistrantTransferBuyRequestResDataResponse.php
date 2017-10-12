<?php

require_once("Response.php");

class RegistrantTransferBuyRequestResDataResponse extends Response {

    private $page;
    private $totalPages;
    private $requests = array();

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

    function getRequests() {
        return $this->requests;
    }

    function setRequests($requests) {
        $this->requests = $requests;
    }

    function addRequest($request) {
        $this->requests[] = $request;
    }

}
