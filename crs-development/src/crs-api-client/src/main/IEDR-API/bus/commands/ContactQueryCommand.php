<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("QueryCommandObject.php");

class ContactQueryCommand extends QueryCommandObject {

    static public $QUERY_TYPE_ALL = 1;
    static public $QUERY_TYPE_TECH = 2;
    static public $QUERY_TYPE_ADMIN = 3;
    static public $QUERY_TYPES_NAMES = array(
        1 => "all",
        2 => "tech",
        3 => "admin");
    private $queryType;
    private $page;

    function ContactQueryCommand($queryType = '', $page = 1) {
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
