<?php

/**
 * Validates Nameserver List, validation rules include:
 *
 * (1) existence - entered nameserver may not be blank (unless it is explicitly declared)
 *
 * (2) uniqueness - multiple nameservers of the same name are not allowed
 *
 * (3) syntactic correctness of the entered hosts
 *
 * This Validator references may be added to specify, along with the name of the model-attribute to be validated,
 * three additional names of items in the model (obligatory if the names are other than default):
 *
 * (1) domain name (or domain name list) having the Nameserver
 *
 * (2) number of hosts that are declared
 *
 * (3) whether to allow empty nameservers
 *
 * Example Model Validation Rules entry:
 *
 * array('someNameservers', 'NameserverListValidator', 'dname'=>'someDomainName', 'ipv4Addresses'=>'someIPv4Addresses', 'ipv6Addresses'=>'someIPv6Addresses', 'nameserversCount'=>'someNameserversCount'),
 */
class NameserverListValidator extends CValidator {
    var $dname = 'domainlist';
    var $nameserversCount = 'nameserversCount';
    var $allowEmpty = false;
    var $model;
    var $nsName;
    var $nameservers;
    var $count;

    public function validateAttribute($model, $attribute) {
        $this->model = $model;
        $this->nsName = $attribute;
        if (!$this->initValues()) {
            return;
        }
        $this->normalizeNameservers();
        for ($i = 0; $i < $this->count; $i++) {
            $this->validateNameserver($i);
        }
    }

    protected function initValues() {
        $attribute = $this->nsName;
        if (!isset($this->model->$attribute) || !is_array($this->model->$attribute))
            return false;
        $this->nameservers = $this->model->$attribute;
        $x = $this->nameserversCount;
        if (!isset($this->model->$x))
            return false;
        $this->count = $this->model->$x;
        return true;
    }

    protected function normalizeNameservers() {
        $normalizedValues = array();
        $nsName = $this->nsName;
        for ($i = 0; $i < $this->count; $i++) {
            $origNameserver = isset($this->nameservers[$i]) ? $this->nameservers[$i] : "";
            // if normalization fails false will be understood as "failed" by proper validation routine.
            $normalized = false;
            try {
                $normalized = Utf8Validator::validateAndNormalizeUtf8($origNameserver);
                $normalizedValues[$i] = $normalized;
            } catch (Exception $e) {
                $this->model->addError("$this->nsName" . "[$i]", $e->getMessage());
                // failed to normalize, so let's put original value into model so that next rendering of the page
                // has something to show.
                $normalizedValues[$i] = $origNameserver;
            }
            $this->nameservers[$i] = $normalized;
        }
        $this->model->$nsName = $normalizedValues;
    }

    protected function validateNameserver($i) {
        $nsName = $this->nsName;
        if ($this->nameservers[$i] === false) {
            return;
        }
        if (!$this->allowEmpty && (!isset($this->nameservers[$i]) || $this->nameservers[$i] == null)) {
            $this->model->addError("$nsName" . "[$i]", "Nameserver " . ($i + 1) . " cannot be blank");
        }
        if (isset($this->nameservers[$i]) && !empty($this->nameservers[$i]) && !HostNameValidator::isValid($this->nameservers[$i])) {
            $this->model->addError("$nsName" . "[$i]", "Nameserver " . ($i + 1) . " is not a valid host name");
        }
        for ($j = $i + 1; $j < $this->count; $j++) {
            if (isset($this->nameservers[$i]) && isset($this->nameservers[$j]) && !empty($this->nameservers[$i]) && !empty($this->nameservers[$j])) {
                $nameserver_i = idn_to_ascii(Utility::getNormalizedHostname($this->nameservers[$i]));
                $nameserver_j = idn_to_ascii(Utility::getNormalizedHostname($this->nameservers[$j]));
                if ($nameserver_i !== false && $nameserver_i == $nameserver_j) {
                    $this->model->addError("$nsName" . "[$i]", 'Nameserver ' . ($i + 1) . ' name duplicates ' . ($j + 1));
                    $this->model->addError("$nsName" . "[$j]", 'Nameserver ' . ($j + 1) . ' name duplicates ' . ($i + 1));
                }
            }
        }
    }

}
