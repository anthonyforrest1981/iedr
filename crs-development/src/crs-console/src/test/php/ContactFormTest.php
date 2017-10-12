<?php

class ContactFormTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('name','email','subject','body');

    public function testUtf8() {
        $model = new ContactForm();
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

}