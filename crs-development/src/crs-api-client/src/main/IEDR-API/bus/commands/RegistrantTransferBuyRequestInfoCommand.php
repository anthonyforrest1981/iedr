<?php
require_once("InfoCommandObject.php");

class RegistrantTransferBuyRequestInfoCommand extends InfoCommandObject {

    private $id;

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }

}
