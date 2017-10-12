<?php

class Domains_RegNew extends FormModelBase {
    private $domain_names;
    /**
     * indicate, if the model should handle one domain only
     */
    public $oneDomainAllowed = false;

    public function rules() {
        return array(array('domain_names','Utf8Validator'),
            array('domain_names','IEDomainValidator','skipOnError' => true,'checkAsANewDomain' => true),
            array('domain_names','domainsAreAvailable','skipOnError' => true));
    }

    public function attributeLabels() {
        return array('domain_names' => 'Domain Name' . $this->oneDomainAllowed ? '' : '(s)');
    }

    /**
     * Setter following standard naming convention set${fieldName} allows Yii to use it in mass assignments
     */
    public function setdomain_names($domain_names) {
        if (!is_array($domain_names)) {
            try {
                $normalized = Utf8Validator::validateAndNormalizeUtf8($domain_names);
                $this->domain_names = preg_split('/[\s,;:]+/u', Utility::mb_trim($normalized, ',;:'));
            } catch (Exception $e) {
                // an array containing one element with unnormalized input
                // an error message will be set by another calling of UTF8 validator within validation rules
                $this->domain_names = array($domain_names);
            }
        } else {
            // no validator called as the function with an array argument may be called by the validator itself
            $this->domain_names = $domain_names;
        }
    }

    public function getdomain_names() {
        return $this->domain_names;
    }

    public function domainsAreAvailable($attribute, $params) {
        $errors = array();
        $domains = $this->$attribute;
        if (!is_array($domains)) {
            $domains = array($domains);
        }
        foreach ($domains as $domain) {
            // check if this domain is available
            if (!Utility::domainIsAvailable($domain)) {
                $errors[] = "$domain is already registered or has a pending ticket";
            }
        }
        if (!empty($errors)) {
            $this->addError($attribute, implode("<br/>", $errors));
        }
    }

}
