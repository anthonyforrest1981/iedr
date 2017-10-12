<?php

class AuthcodePortalModel extends DynamicModelBase {
    public $domain_name;
    public $email;
    public $recaptchaResponse;
    public $message;

    public function rules() {
        return array(
            array('domain_name, email', 'required'),
            array('recaptchaResponse', 'required', 'message' => RecaptchaValidator::$RECAPTCHA_ERROR),
            array('domain_name, email', 'Utf8Validator'),
            array('domain_name','filter', 'filter' => 'mb_strtolower'),
            array('email', 'EmailAddrValidator'),
            array('recaptchaResponse', 'RecaptchaValidator', 'on' => 'submit')
        );
    }

    public function attributeLabels() {
        return array('domain_name' => 'Domain Name', 'email' => 'Email');
    }

    public function clear() {
        $this->domain_name = null;
        $this->email = null;
    }

}
