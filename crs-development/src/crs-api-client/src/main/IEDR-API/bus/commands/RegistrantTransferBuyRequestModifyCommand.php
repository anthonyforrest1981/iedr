<?php
require_once("ModifyCommandObject.php");

class RegistrantTransferBuyRequestModifyCommand extends ModifyCommandObject {

    private $buyRequestId;
    private $holder;
    private $contact;

    public function getBuyRequestId() {
        return $this->buyRequestId;
    }

    public function setBuyRequestId($buyRequestId) {
        $this->buyRequestId = $buyRequestId;
    }

    public function getHolder() {
        return $this->holder;
    }

    public function setHolder($holder) {
        $this->holder = $holder;
    }

    public function getContact() {
        return $this->contact;
    }

    public function setContact($contact) {
        $this->contact = $contact;
    }

}
