<?php

class AuthcodePortalModelTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('domain_name','email');

    public function testUtf8() {
        $model = new AuthcodePortalModel();
        Yii::app()->controller = new AuthcodePortalController('');
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

}