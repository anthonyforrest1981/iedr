<?php

class Domains_Creation_DetailsTest extends PHPUnit_Framework_TestCase {
    // U - field value automatically set to uppercase, L - to lower case, N - case unchanged
    private $utf8FieldsToValidate = array('holder' => 'N','registration_period' => 'N',
        'remarks' => 'N','admin_contact_nic_1' => 'U','admin_contact_nic_2' => 'U','tech_contact' => 'U',
        'paymentType' => 'N');

    public function testUtf8() {
        $model = $this->getModel();
        Utf8ValidationTestUtil::_testNormalizeWithCaseMode($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, array_keys($this->utf8FieldsToValidate));
        Utf8ValidationTestUtil::_testValidate4Bytes($model, array_keys($this->utf8FieldsToValidate));
        NameserverListValidationTestUtil::_testUtf8($model, 'nameservers', 'nameserversCount', 'domainlist', 'ipv4Addresses', 'ipv6Addresses');
    }

    public function testLetterCaseFilter() {
        $model = $this->getModel();
        $model->admin_contact_nic_1 = 'aa11-iedr';
        $model->admin_contact_nic_2 = 'aa11-iedr';
        $model->tech_contact = 'aa11-iedr';
        $model->validate();
        $this->assertEquals('AA11-IEDR', $model->admin_contact_nic_1);
        $this->assertEquals('AA11-IEDR', $model->admin_contact_nic_2);
        $this->assertEquals('AA11-IEDR', $model->tech_contact);
    }

    private function getModel() {
        $model = new Domains_Creation_Details();
        // no nameservers by default
        $model->nameserversCount = 0;
        // credit card rules off by default
        $model->setupRules(array('paymentType' => ''));
        return $model;
    }

}
