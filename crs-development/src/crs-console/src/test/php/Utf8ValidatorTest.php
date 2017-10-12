<?php

class Utf8ValidatorTest extends PHPUnit_Framework_TestCase {

    public function testNormalize() {
        $validator = new Utf8Validator();
        $model = new MockedFormModel();
        $model->attrib1 = "a\xCC\x84ttr";
        $validator->validateAttribute($model, 'attrib1');
        $errorMessage = $model->getError('attrib1');
        $this->assertFalse(isset($errorMessage));
        $this->assertEquals("\xC4\x81ttr", $model->attrib1);
    }

    public function testValidateNonUtf8() {
        $validator = new Utf8Validator();
        $model = new MockedFormModel();
        $model->attrib1 = Utf8ValidationTestUtil::$NON_UTF8;
        $validator->validateAttribute($model, 'attrib1');
        $this->assertTrue(isset($model->errors['attrib1']));
        $this->assertEquals("Not a valid UTF-8", $model->getError('attrib1'));
    }

    public function testValidate4Bytes() {
        $validator = new Utf8Validator();
        $model = new MockedFormModel();
        $model->attrib1 = Utf8ValidationTestUtil::$FOUR_BYTES;
        $validator->validateAttribute($model, 'attrib1');
        $this->assertTrue(isset($model->errors['attrib1']));
        $this->assertEquals("Forbidden UTF-8 character", $model->getError('attrib1'));
    }

}
