<?php

require_once("QueryCommandObject.php");

class RegistrantTransferSellRequestQueryCommand extends QueryCommandObject {

    private $page;

    function getPage() {
        return $this->page;
    }

    function setPage($page) {
        $this->page = $page;
    }

}