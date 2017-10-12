<?php

/**
 * Validates Nameserver List along with related IP addresses, validation rules include:
 *
 * (1) existence - entered nameserver may not be blank (unless it is explicitly declared)
 *
 * (2) uniqueness - multiple nameservers of the same name are not allowed
 *
 * (3) syntactic correctness of the entered hosts
 *
 * (4) IP correctness
 *
 * (5) Glue-Record Validation Rules - whether IP address should be entered or not
 *
 * This Validator references may be added to specify, along with the name of the model-attribute to be validated,
 * four additional names of items in the model (obligatory if the names are other than default):
 *
 * (1) Domain name (or domain name list) having the Nameserver
 *
 * (2) IP address list
 *
 * (3) number of hosts that are declared
 *
 * (4) whether to allow empty nameservers
 *
 * Example Model Validation Rules entry:
 *
 * array('someNameservers', 'NameserverListComplexValidator', 'dname'=>'someDomainName', 'ipv4Name'=>'someIPAddresses', 'nameserversCount'=>'someNameserversCount'),
 */
class NameserverListComplexValidator extends NameserverListValidator {
    const IPV4_REGEX = '/^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))\.((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))\.((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))\.((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))$/u';
    const IPV6_REGEX = '/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/u';
    public $ipv4Name = 'ipv4Addresses';
    public $ipv4List;
    public $ipv6Name = 'ipv6Addresses';
    public $ipv6List;

    protected function initValues() {
        $ipv4Field = $this->ipv4Name;
        if (!isset($this->model->$ipv4Field) || !is_array($this->model->$ipv4Field))
            return false;
        $this->ipv4List = $this->model->$ipv4Field;
        $ipv6Field = $this->ipv6Name;
        if (!isset($this->model->$ipv6Field) || !is_array($this->model->$ipv6Field))
            return false;
        $this->ipv6List = $this->model->$ipv6Field;
        return parent::initValues();
    }

    protected function normalizeNameservers() {
        parent::normalizeNameservers();
        $normalizedIpv4 = array();
        $normalizedIpv6 = array();
        $ipv4Field = $this->ipv4Name;
        $ipv6Field = $this->ipv6Name;
        for ($i = 0; $i < $this->count; $i++) {
            $origIpv4Value = isset($this->ipv4List[$i]) ? $this->ipv4List[$i] : '';
            $origIpv6Value = isset($this->ipv6List[$i]) ? $this->ipv6List[$i] : '';
            $normalizedIpv4Value = false;
            $normalizedIpv6Value = false;
            try {
                $normalizedIpv4Value = Utf8Validator::validateAndNormalizeUtf8($origIpv4Value);
                $normalizedIpv4[$i] = $normalizedIpv4Value;
            } catch (Exception $e) {
                $this->model->addError("$this->ipv4Name" . "[$i]", $e->getMessage());
                $normalizedIpv4[$i] = $origIpv4Value;
            }
            try {
                $normalizedIpv6Value = Utf8Validator::validateAndNormalizeUtf8($origIpv6Value);
                $normalizedIpv6[$i] = $normalizedIpv6Value;
            } catch (Exception $e) {
                $this->model->addError("$this->ipv6Name" . "[$i]", $e->getMessage());
                $normalizedIpv6[$i] = $origIpv6Value;
            }
            $this->ipv4List[$i] = $normalizedIpv4Value;
            $this->ipv6List[$i] = $normalizedIpv6Value;
        }
        $this->model->$ipv4Field = $normalizedIpv4;
        $this->model->$ipv6Field = $normalizedIpv6;
    }

    protected function validateNameserver($i) {
        parent::validateNameserver($i);
        // get hostname, ipv4 and ipv6 from model attributes
        $domainNameAttrName = $this->dname;
        $domainNames = $this->model->$domainNameAttrName;
        $dnsName = $this->nameservers[$i];
        $dnsIpv4 = isset($this->ipv4List[$i]) ? $this->ipv4List[$i] : null;
        $dnsIpv6 = isset($this->ipv6List[$i]) ? $this->ipv6List[$i] : null;
        if ($dnsIpv4 === false || $dnsIpv6 === false) {
            // false is only possible by failed normalization, and that means error is already set
            return;
        }
        $glueRequired = Utility::isGlueRequired($domainNames, $dnsName);
        $ipv4Defined = !!$dnsIpv4;
        $ipv6Defined = !!$dnsIpv6;
        if ($glueRequired) {
            // at least one of ipv4 or ipv6 must be filled and valid
            if (!$ipv4Defined && !$ipv6Defined) {
                $this->model->addError("$this->nsName" . "[$i]", "Nameserver " . ($i + 1) . " must have glue defined");
            }
            if ($ipv4Defined && !preg_match(self::IPV4_REGEX, $dnsIpv4)) {
                $this->model->addError("$this->ipv4Name" . "[$i]", "Not a valid IPv4 address");
            }
            if ($ipv6Defined && !preg_match(self::IPV6_REGEX, $dnsIpv6)) {
                $this->model->addError("$this->ipv6Name" . "[$i]", "Not a valid IPv6 address");
            }
        } else {
            // glue not allowed, ipv4 and ipv6 must be blank
            if ($ipv4Defined) {
                $this->model->addError("$this->ipv4Name" . "[$i]", "Glue is not allowed");
            }
            if ($ipv6Defined) {
                $this->model->addError("$this->ipv6Name" . "[$i]", "Glue is not allowed");
            }
        }
    }

}
