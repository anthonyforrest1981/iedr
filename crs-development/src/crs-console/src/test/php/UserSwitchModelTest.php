<?php

class UserSwitchModelTest extends PHPUnit_Framework_TestCase {

    protected function setUp() {
        $_SERVER['REQUEST_URI'] = '0.0.0.0';
    }

    protected function tearDown() {
        unset($_SERVER['REQUEST_URI']);
    }
    private $utf8FieldsToValidate = array('switchUser');

    public function testUtf8() {
        $model = new UserSwitchModel();
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

}