<?php
/*
 * Based on AccountPayOnlineDataResponse
*20120404: Amended by CC to include Order ID response
 */
include_once("Response.php");

class AccountPayDataResponse extends Response {

    private $fee;
    private $vat;
    private $total;
    private $orderId;

    function AccountPayData($fee = '', $vat = '', $total = '', $orderId = '') {
        $this->fee = $fee;
        $this->vat = $vat;
        $this->total = $total;
        $this->orderId = $orderId;
    }

    function getFee() {
        return $this->fee;
    }

    function setFee($fee) {
        $this->fee = $fee;
    }

    function getVat() {
        return $this->vat;
    }

    function setVat($vat) {
        $this->vat = $vat;
    }

    function getTotal() {
        return $this->total;
    }

    function setTotal($total) {
        $this->total = $total;
    }

    function getOrderId() {
        return $this->orderId;
    }

    function setOrderId($orderId) {
        $this->orderId = $orderId;
    }
}
