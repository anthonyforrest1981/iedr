<?php

class ViewDomainModeTest extends PHPUnit_Framework_TestCase {
    private $utf8FieldsToValidate = array('domain_name','domain_holder','domain_dsmState_renewalMode',
        'domain_adminContacts_0_nicHandle','domain_adminContacts_1_nicHandle','domain_techContacts_nicHandle',
        'domain_remark');

    public function testUtf8() {
        $model = new ViewDomainModel();
        // nameservers not verified within two initial tests
        $model->nameserversCount = 0;
        Utf8ValidationTestUtil::_testNormalize($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidateNonUtf8($model, $this->utf8FieldsToValidate);
        Utf8ValidationTestUtil::_testValidate4Bytes($model, $this->utf8FieldsToValidate);
        NameserverListValidationTestUtil::_testUtf8($model, 'domain_nameservers', 'domain_nameserver_count',
            'domain_name', 'domain_ipv4Addresses', 'domain_ipv6Addresses');
    }

}