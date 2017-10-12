<?php

class EditTicketModelTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('id','domainHolder','adminContact_0','adminContact_1','billingContact',
        'techContact','requestersRemark','charityCode');

    public function testUtf8() {
        $model = new EditTicketModel();
        $model->nameserversCount = 0;
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
        NameserverListValidationTestUtil::_testUtf8($model, 'nameservers', 'nameserversCount', 'domainName', 'ipv4Addresses', 'ipv6Addresses');
    }

    public function testTrimNewLines() {
        $model = new EditTicketModel();
        $untrimmedValue = " \n \n\nValue\n\nwith\n new lines \n";
        $trimmedValue = "Value\r\nwith\r\nnew lines";
        $model->requestersRemark = $untrimmedValue;
        $model->validate();
        self::assertEquals($trimmedValue, $model->requestersRemark);
    }

}