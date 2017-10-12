<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

//amended by CC 20120404 to include setPeriod

include_once("PayDomainsCommandObject.php");

class AccountPayCommand extends PayDomainsCommandObject {

    private $creditCard;
    private $period;

    function getCreditCard() {
        return $this->creditCard;
    }

    function setCreditCard($creditCard) {
        $this->creditCard = $creditCard;
    }

    public function getPeriod() {
        return $this->period;
    }

    public function setPeriod($period) {
        $this->period = $period;
    }

}
