<?php

class LoginForm extends CFormModel {
    public $username;
    public $password;
    public $rememberMe;
    public $internalUser;
    public $google_pin;
    public $serverTime;

    public function rules() {
        return array(
            array('username, password, internalUser', 'required'),
            array('username, password, internalUser', 'Utf8Validator'),
            array('password', 'length', 'max' => '16'),
            array('username, internalUser', 'filter', 'filter' => 'mb_strtoupper'),
            array('internalUser', 'validateInternalUser'),
            array('google_pin', 'numerical'),
            array('password', 'authenticate')
        );
    }

    public function attributeLabels() {
        return array('username' => 'Account Number (Nic-Handle)', 'password' => 'Password',
            'internalUser' => 'Internal Nic Handle', 'google_pin' => 'PIN');
    }

    public function validateInternalUser($attribute, $params) {
        if (Utility::isInternalNetwork()) {
            $u = $this->$attribute;
            $users = Utility::getIEDRUsers();
            if (!isset($users[$u])) {
                $this->addError($attribute, $u . ' is not a valid Internal User');
            }
        }
    }

    public function authenticate($attribute, $params) {
        if (!$this->hasErrors()) {
            $identity = new NicHandleIdentity($this->internalUser, $this->username,
                $this->password, $this->google_pin);
            if (!$identity->authenticate()) {
                $this->addErrorFromCode($identity->errorCode, $identity->accountLockPeriod);
            }
        }
    }

    private function addErrorFromCode($errorCode, $accountLockPeriod) {
        if (Utility::isInternalNetwork() &&
                $this->internalUser === NicHandleIdentity::NOT_INTERNAL) {
            $this->addError('internalUser', 'Incorrect Internal User');
        } else {
            if ($errorCode === NicHandleIdentity::ERROR_INVALID_CREDENTIALS ||
                    $errorCode === NicHandleIdentity::ERROR_NO_LOGIN_PERMISSION) {
                $this->addError('password', 'Incorrect Account Number (Nic-Handle) or password');
            } else if ($errorCode === NicHandleIdentity::ERROR_LOGIN_LOCKED) {
                $time = null;
                if ($accountLockPeriod > 120) {
                    $time = round($accountLockPeriod / 60, 0, PHP_ROUND_HALF_DOWN) . ' minutes';
                } else {
                    $time = ($accountLockPeriod) . " seconds";
                }
                $this->addError('password',
                    "Your account has been locked for $time due to unsuccessful login attempts");
            } else if ($errorCode === NicHandleIdentity::ERROR_INVALID_PIN) {
                $this->addError('google_pin', 'Invalid PIN');
            } else if ($errorCode === NicHandleIdentity::ERROR_EMPTY_SALT) {
                $this->addError('password', 'Password reset required');
            } else {
                $this->addError('password', 'Unexpected error performing authentication');
            }
        }
    }

}

