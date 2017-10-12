<?php

class DnsCheckModel extends DynamicModelBase {
    public $maxRows;
    public $minRows;
    public $domainName;
    public $nameserversCount;
    public $nameservers = array();
    public $ipv4Addresses = array();
    public $ipv6Addresses = array();
    public $recaptchaResponse;

    public function __construct() {
        parent::__construct();
        $appConfig = Utility::getApplicationConfiguration();
        $this->maxRows = $appConfig->nameserversMaxCount;
        $this->minRows = $appConfig->nameserversMinCount;
        $this->nameserversCount = $this->minRows;
    }

    public function rules() {
        return array(
            array('domainName', 'required'),
            array('recaptchaResponse', 'required', 'message' => RecaptchaValidator::$RECAPTCHA_ERROR),
            array('domainName', 'Utf8Validator'),
            array('domainName', 'IEDomainValidator', 'skipOnError' => true),
            array('nameserversCount', 'numerical', 'integerOnly' => true, 'min' => $this->minRows,
                    'max' => $this->maxRows),
            array('nameservers', 'NameserverListComplexValidator', 'dname' => 'domainName'),
            array('recaptchaResponse', 'RecaptchaValidator', 'on' => 'submit')
        );
    }

    public function attributeLabels() {
        return array('domainName' => 'Domain Name');
    }

    public function setFromPOST($p) {
        parent::setFromPOST($p);
        foreach ($p['nameservers'] as $i => $value) {
            $this->nameservers[$i] = $value;
        }
        foreach ($p['ipv4Addresses'] as $i => $value) {
            $this->ipv4Addresses[$i] = $value;
        }
        foreach ($p['ipv6Addresses'] as $i => $value) {
            $this->ipv6Addresses[$i] = $value;
        }
    }

    public function getNameserverList() {
        $nameserverList = array();
        for ($i = 0; $i < $this->nameserversCount; $i++) {
            $nameserver = array();
            $nameserver['name'] = $this->nameservers[$i];
            $nameserver['ipv4Address'] = $this->ipv4Addresses[$i];
            $nameserver['ipv6Address'] = $this->ipv6Addresses[$i];
            $nameserverList[] = $nameserver;
        }
        return $nameserverList;
    }

}