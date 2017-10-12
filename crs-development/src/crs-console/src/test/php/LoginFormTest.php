<?php

class LoginFormTest extends PHPUnit_Framework_TestCase {

    protected function setUp() {
        $_SERVER['REMOTE_ADDR'] = '0.0.0.0';
        Yii::app()->params['internal_network'] = array('exact_matches' => array(),'regex_matches' => array());
    }

    protected function tearDown() {
        unset($_SERVER['REMOTE_ADDR']);
        unset(Yii::app()->params['internal_network']);
    }
    private $utf8FieldsToValidate = array('username','password','internalUser');

    public function testUtf8() {
        $model = new LoginForm();
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

}