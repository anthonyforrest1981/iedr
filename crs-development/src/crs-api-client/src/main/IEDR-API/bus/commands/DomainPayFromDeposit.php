<?php

class DomainPayFromDeposit {

    private $autorenewEnabled = FALSE;

    function DomainPayFromDeposit($autorenewEnabled = FALSE) {
        $this->autorenewEnabled = $autorenewEnabled;
    }

    function isAutorenewEnabled() {
        return $this->autorenewEnabled;
    }

    function setAutorenewEnabled($autorenewEnabled) {
        $this->autorenewEnabled = $autorenewEnabled;
    }
}
