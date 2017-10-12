<?php

class ResetPasswordForm extends CFormModel {
    public $username;
    public $token;
    public $tfaPin;
    public $new_password;
    public $repeat_new_password;
    public $serverTime;

    const tokenRequestScenario = 'tokenRequest';
    const tokenVerificationScenario = 'tokenVerification';
    const passwordChangeScenario = 'passwordChange';

    public function rules() {
        return array(
            array('username', 'required', 'on' => self::tokenRequestScenario),
            array('username, token', 'required', 'on' => self::tokenVerificationScenario),
            array('username, token, new_password, repeat_new_password', 'required',
                'on' => self::passwordChangeScenario),
            array('username, token, new_password, repeat_new_password', 'Utf8Validator'),
            array('username', 'filter', 'filter' => 'mb_strtoupper', 'on' => self::tokenRequestScenario),
            array('username', 'NicHandleSyntaxValidator',
                'on' => array(self::tokenRequestScenario, self::passwordChangeScenario)),
            array('new_password', 'length', 'on' => self::passwordChangeScenario, 'min' => '8', 'max' => '16'),
            array('new_password', 'PasswordStrengthValidator', 'on' => self::passwordChangeScenario),
            array('repeat_new_password', 'matchSuppliedPasswordsValidatorFn', 'on' => self::passwordChangeScenario),
            array('tfaPin', 'numerical', 'on' => self::passwordChangeScenario));
    }

    public function attributeLabels() {
        return array(
            'username' => 'Account Number (Nic-Handle)',
            'new_password' => 'Enter New Password',
            'repeat_new_password' => 'Repeat New Password',
            'token' => 'Password Reset Token',
            'tfaPin' => 'PIN');
    }

    public function matchSuppliedPasswordsValidatorFn() {
        if ($this->new_password != $this->repeat_new_password) {
            $this->addError('repeat_new_password', 'New and repeated password must match.');
            $this->addError('new_password', '');
        }
    }

}

