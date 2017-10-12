<?php

class Nichandle_DetailsTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('companyName','email','address','phones','faxes','name','vatNo');

    public function testUtf8() {
        $model = new Nichandle_Details();
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
    }

    public function testTrimNewLines() {
        $model = new Nichandle_Details();
        $untrimmedValue = " \n \n\nValue\n\nwith\n new lines \n";
        $trimmedValue = "Value\r\nwith\r\nnew lines";
        $model->address = $untrimmedValue;
        $model->validate();
        self::assertEquals($trimmedValue, $model->address);
    }

    public function testTrimPhonesArray() {
        $model = new Nichandle_Details();
        $phonesInput = " 123123123  \n  456456456 \n  \n789789789";
        $phones = array("123123123","456456456","789789789");
        $model->phones = $phonesInput;
        $model->faxes = $phonesInput;
        $model->validate();
        self::assertEquals($phones, $model->phones_array);
        self::assertEquals($phones, $model->faxes_array);
    }

}