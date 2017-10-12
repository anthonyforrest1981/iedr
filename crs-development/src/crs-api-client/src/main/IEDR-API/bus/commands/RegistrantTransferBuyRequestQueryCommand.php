<?php
require_once("QueryCommandObject.php");

class RegistrantTransferBuyRequestQueryCommand extends QueryCommandObject {

    private $domainName;
    private $page;

    function getDomainName() {
        return $this->domainName;
    }

    function setDomainName($domainName) {
        $this->domainName = $domainName;
    }

    function getPage() {
        return $this->page;
    }

    function setPage($page) {
        $this->page = $page;
    }

}
