<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("QueryCommandObject.php");

class TicketQueryCommand extends QueryCommandObject {

    static public $QUERY_TYPE_ALL = 1;
    static public $QUERY_TYPE_REGISTRATIONS = 2;
    static public $QUERY_TYPE_MODIFICATIONS = 3;
    static public $QUERY_TYPE_DELETIONS = 4;
    static public $QUERY_TYPES_NAMES = array(
        1 => "all",
        2 => "registrations",
        3 => "modifications",
        4 => "deletions");
    private $queryType;
    private $page;

    function TicketQueryCommand($queryType = '', $page = 1) {
        parent::__construct();
        $this->queryType = $queryType;
        $this->page = $page;
    }

    function getQueryType() {
        return $this->queryType;
    }

    function setQueryType($type) {
        $this->queryType = $type;
    }

    function getPage() {
        return $this->page;
    }

    function setPage($page) {
        $this->page = $page;
    }
}
