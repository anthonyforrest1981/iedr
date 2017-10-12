<?php

class PhoneNumberValidatorTest extends PHPUnit_Framework_TestCase {

    public function testPhonesAndFaxes() {
        $this->_testForField('phones', 'Phone');
        $this->_testForField('faxes', 'Fax');
    }

    private function _testForField($fieldName, $fieldLabel) {
        $this->verifyCorrect($fieldName, '(+48) 601 22-33-44');
        // length = 25
        $this->verifyCorrect($fieldName, '0123456789012345678901234');
        $this->verifyIncorrect($fieldName, '01234*', ' is not a valid ' . $fieldLabel . ' Number (allowed charaters: "0-9 +-()")');
        $this->verifyIncorrect($fieldName, '012', ' is too short (minimum is 4 characters)');
        // length = 26
        $this->verifyIncorrect($fieldName, '01234567890123456789012345', ' is too long (maximum is 25 characters)');
    }

    private function verifyCorrect($fieldName, $value) {
        $model = new Nichandle_Details();
        $model->$fieldName = $value;
        $model->validate();
        $errorMessage = $model->getError($fieldName);
        self::assertFalse(isset($errorMessage));
    }

    private function verifyIncorrect($fieldName, $value, $expectedMessageSuffix) {
        $model = new Nichandle_Details();
        $model->$fieldName = $value;
        $model->validate();
        $errorMessage = $model->getError($fieldName);
        self::assertTrue(isset($errorMessage), "Phone/fax number: $value should be considered incorrect");
        self::assertEquals($errorMessage, $value . $expectedMessageSuffix);
    }

}