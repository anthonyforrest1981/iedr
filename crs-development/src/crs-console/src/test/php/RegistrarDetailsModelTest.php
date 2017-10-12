<?php

class RegistrarDetailsModelTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('techContact','emailInvoiceFormat');

    public function testUtf8() {
        $model = new RegistrarDetailsModel();
        $model->nameserversCount = 0;
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
        NameserverListValidationTestUtil::_testUtf8NoIp($model, 'nameservers', 'nameserversCount');
    }

    public function testLetterCaseFilter() {
        $model = new RegistrarDetailsModel();
        $model->techContact = 'aa11-iedr';
        $model->validate();
        $this->assertEquals('AA11-IEDR', $model->techContact);
    }

    public function testTechContactValidation() {
        $model = new RegistrarDetailsModel();
        $model->techContact = 'AA11-IEDR';
        $model->validate();
        $errorMessage = $model->getError('techContact');
        self::assertEmpty($errorMessage);
        $model->techContact = 'XY999-IEDR';
        $model->validate();
        $errorMessage = $model->getError('techContact');
        self::assertTrue(isset($errorMessage));
        self::assertEquals('Tech Contact is not an existing Account', $errorMessage);
        $model->techContact = 'NOT-VALID-IEDR';
        $model->validate();
        $errorMessage = $model->getError('techContact');
        self::assertTrue(isset($errorMessage));
        self::assertEquals('Tech Contact is not a valid Account Number', $errorMessage);
    }

}
