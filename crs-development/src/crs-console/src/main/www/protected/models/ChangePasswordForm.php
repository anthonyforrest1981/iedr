<?php

class ChangePasswordForm extends CFormModel {
    /**
     * Description for public
     *
     * @var string
     * @access public
     */
    public $new_password;
    /**
     * new password
     *
     * @var string
     * @access public
     */
    public $old_password;
    /**
     * new password repeated
     *
     * @var string
     * @access public
     */
    public $repeat_new_password;

    /**
     * Declares the validation rules.
     *
     * Password fields required, old and new passwords must be different
     * Old password must be the existing old password.
     * New and repeat password must be different.
     *
     * @return void
     * @access public
     */
    public function rules() {
        return array(array('old_password, new_password, repeat_new_password','required'),
            array('old_password, new_password, repeat_new_password','Utf8Validator'),
            // old and new password must be different.
            array('oldPassword, new_password','newPasswordMustBeDifferentValidatorFn'),
            // Tests if the new and repeat passwords are the same.
            array('new_password','length','min' => '8','max' => '16'),
            array('new_password','PasswordStrengthValidator'),
            array('new_password','matchSuppliedPasswordsValidatorFn'));
    }

    public function attributeLabels() {
        return array(
            'old_password' => 'Old Password',
            'new_password' => 'New Password',
            'repeat_new_password' => 'Repeat New Password'
        );
    }

    /**
     * Function to forbid using the same old and new passwords.
     *
     * @return void
     * @access public
     */
    public function newPasswordMustBeDifferentValidatorFn() {
        // trim is not mutlibyte-aware, but since it looks for characters that are guaranted to be 1-byte and will not corrupt the string
        // I'm leaving it in.
        if (mb_strtolower(Utility::mb_trim($this->old_password)) == mb_strtolower(Utility::mb_trim($this->new_password))) {
            $this->addError('new_password', 'Old and New passwords must not match.');
        }
    }

    /**
     * Function to ensure password, and repeat password match.
     *
     * @return void
     * @access public
     */
    public function matchSuppliedPasswordsValidatorFn() {
        if ($this->new_password != $this->repeat_new_password) {
            $this->addError('repeat_new_password', 'New and repeat passwords must match.');
            $this->addError('new_password', '');
        }
    }

}
