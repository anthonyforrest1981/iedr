<?php
require_once("CreateCommandObject.php");

class RegistrantTransferBuyRequestDeleteCommand extends CreateCommandObject {

    private $id;

    function getId() {
        return $this->id;
    }

    function setId($id) {
        $this->id = $id;
    }

}
