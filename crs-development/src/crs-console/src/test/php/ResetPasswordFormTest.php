<?php

class ResetPasswordFormTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('username','token','new_password','repeat_new_password');

    public function testUtf8() {
        $model = new ResetPasswordForm();
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

    public function testLetterCaseFilter() {
        $model = new ResetPasswordForm();
        $model->setScenario(ResetPasswordForm::tokenRequestScenario);
        $model->username = 'aa11-iedr';
        $model->validate();
        $this->assertEquals('AA11-IEDR', $model->username);
    }

}