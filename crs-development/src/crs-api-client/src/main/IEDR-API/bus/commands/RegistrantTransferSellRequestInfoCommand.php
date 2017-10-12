<?php

require_once("InfoCommandObject.php");

class RegistrantTransferSellRequestInfoCommand extends InfoCommandObject {

    private $id;

    function __construct($id) {
        parent::__construct();
        $this->id = $id;
    }

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }

}