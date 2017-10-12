<?php

class DomainsTransferRegNew extends FormModelBase {
    public $domain_name;

    public function rules() {
        return array(array('domain_name','required'),array('domain_name','Utf8Validator'),
            array('domain_name','filter','filter' => array('Utility','mb_trim')),
            array('domain_name','filter','filter' => 'mb_strtolower'),
            array('domain_name','HostNameValidator','skipOnError' => true),array('domain_name','isTransferable'));
    }

    public function attributeLabels() {
        return array('domain_name' => 'Domain Name');
    }

    public function isTransferable($attribute, $params) {
        $errors = array();
        $domain_name = $this->$attribute;
        if (!Utility::isTransferPossible($domain_name)) {
            $this->addError($attribute, "$domain_name is invalid, already registered, or has a pending ticket, or has incorrect state");
        }
    }

}
