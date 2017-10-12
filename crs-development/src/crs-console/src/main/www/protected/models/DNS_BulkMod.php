<?php

class DNS_BulkMod extends FormModelBase {
    public $maxRows;
    public $minRows;
    private $domainlist;
    public $nameserversCount;
    public $nameservers = array();

    public function __construct() {
        parent::__construct();
        $appConfig = Utility::getApplicationConfiguration();
        $this->maxRows = $appConfig->nameserversMaxCount;
        $this->minRows = $appConfig->nameserversMinCount;
        $this->nameserversCount = $this->minRows;
    }

    public function rules() {
        return array(array('domainlist','Utf8Validator'),
            array('domainlist','IEDomainValidator','skipOnError' => true),
            array('nameserversCount','numerical','integerOnly' => true,'min' => $this->minRows,'max' => $this->maxRows),
            array('nameservers','NameserverListValidator'),array('nameservers','checkGlue'));
    }

    public function setDomainlist($domainNames) {
        $newDomainlist = $domainNames;
        if (!is_array($newDomainlist)) {
            $newDomainlist = preg_split('/[\s,;:]+/u', Utility::mb_trim($domainNames));
        }
        $this->domainlist = $newDomainlist;
    }

    public function getDomainlist() {
        return $this->domainlist;
    }

    public function getNsName($index) {
        return $this->nameservers[$index];
    }

    public function isDomainListValid() {
        return IEDomainValidator::isValid($this->domainlist);
    }

    public function domainlistAsString() {
        return join(',', $this->domainlist);
    }

    public function checkGlue($attrib, $params) {
        for ($i = 0; $i < $this->nameserversCount; $i++) {
            $ns = $this->getNsName($i);
            if (Utility::isGlueRequired($this->domainlist, $ns)) {
                $dname = "";
                foreach ($this->domainlist as $domain) {
                    if (mb_strpos($ns, $domain) !== false) {
                        $dname = $domain;
                    }
                }
                $this->addError('nameservers_' . $i, $dname . " : glue needed for " . $ns);
            }
        }
    }

}
