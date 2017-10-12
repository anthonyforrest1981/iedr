<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("ModifyCommandObject.php");

class DomainModifyCommand extends ModifyCommandObject {

    private $name;
    private $authcode;
    private $AdminCAdds = array();
    private $TechCAdds = array();
    private $NssAdds = array();
    private $AdminCRems = array();
    private $TechCRems = array();
    private $NssRems = array();
    private $holder;
    private $renewalMode = NULL;
    private $renew = NULL;

    function DomainModifyCommand($name = '') {
        parent::__construct();
        $this->name = $name;
    }

    function getName() {
        return $this->name;
    }

    function setName($name) {
        $this->name = $name;
    }

    function getAuthcode() {
        return $this->authcode;
    }

    function setAuthcode($code) {
        $this->authcode = $code;
    }

    function getAdminCAdds() {
        return $this->AdminCAdds;
    }

    function setAdminCAdds($adminCAdds) {
        $this->AdminCAdds = $adminCAdds;
    }

    function addAdminCAdd($adminC) {
        $this->AdminCAdds[] = $adminC;
    }

    function getTechCAdds() {
        return $this->TechCAdds;
    }

    function setTechCAdds($techCAdds) {
        $this->TechCAdds = $techCAdds;
    }

    function addTechCAdd($techC) {
        $this->TechCAdds[] = $techC;
    }

    function getNssAdds() {
        return $this->NssAdds;
    }

    function setNssAdds($nssAdds) {
        $this->NssAdds = $nssAdds;
    }

    function addNssAdd($ns) {
        $this->NssAdds[] = $ns;
    }

    function getAdminCRems() {
        return $this->AdminCRems;
    }

    function setAdminCRems($adminCRems) {
        $this->AdminCRems = $adminCRems;
    }

    function addAdminCRem($adminC) {
        $this->AdminCRems[] = $adminC;
    }

    function getTechCRems() {
        return $this->TechCRems;
    }

    function setTechCRems($techCRems) {
        $this->TechCRems = $techCRems;
    }

    function addTechCRem($techC) {
        $this->TechCRems[] = $techC;
    }

    function getNssRems() {
        return $this->NssRems;
    }

    function setNssRems($nssRems) {
        $this->NssRems = $nssRems;
    }

    function addNssRem($ns) {
        $this->NssRems[] = $ns;
    }

    function getHolder() {
        return $this->holder;
    }

    function setHolder($holder) {
        $this->holder = $holder;
    }

    function getRenewalMode() {
        return $this->renewalMode;
    }

    function setRenewalMode($renewalMode) {
        $this->renewalMode = $renewalMode;
    }

    function issetRenewalMode() {
        return isset($this->renewalMode);
    }

    function getRenew() {
        return $this->renew;
    }

    function setRenew($renew) {
        $this->renew = $renew;
    }

    function issetRenew() {
        return isset($this->renew);
    }
}
