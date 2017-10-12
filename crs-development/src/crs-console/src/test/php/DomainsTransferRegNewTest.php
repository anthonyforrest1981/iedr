<?php

class DomainsTransferRegNewTest extends PHPUnit_Framework_TestCase {

    public function testUtf8() {
        $model = new DomainsTransferRegNew();
        Utf8ValidationTestUtil::_testNormalizeSingle($model, 'domain_name');
        Utf8ValidationTestUtil::_testValidateNonUtf8Single($model, 'domain_name');
        Utf8ValidationTestUtil::_testValidate4BytesSingle($model, 'domain_name');
    }

    public function testLetterCaseFilter() {
        $model = new DomainsTransferRegNew();
        $model->domain_name = 'DOMAIN-NAME.IE';
        $model->validate();
        $this->assertEquals('domain-name.ie', $model->domain_name);
    }

}