<?php

class ChangePasswordFormTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('old_password','new_password','repeat_new_password');

    public function testUtf8() {
        $model = new ChangePasswordForm();
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

}