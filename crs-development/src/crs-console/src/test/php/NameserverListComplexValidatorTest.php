<?php

class NameserverListComplexValidatorTest extends PHPUnit_Framework_TestCase {

    public function testNormalize() {
        $validator = new NameserverListComplexValidator();
        $validator->nameserversCount = 'attrib2';
        $validator->dname = 'attrib3';
        $validator->ipv4Name = 'attrib4';
        $validator->ipv6Name = 'attrib5';
        $model = new MockedFormModel();
        $model->attrib3 = 'nameserver-test.ie';
        NameserverListValidationTestUtil::initNameserversWithIp($model, 'attrib1', 'attrib2', 'attrib4', 'attrib5', 7, "nameserver-test.ie", "10.10.1.a\xCC\x84", "FE\xCC\x8F80::0202:B3FF:FE\xCC\xA31E:8329");
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkValuesIp($model, 'attrib4', 'attrib5', 7, "10.10.1.\xC4\x81", "F\xC8\x8480::0202:B3FF:F\xE1\xBA\xB81E:8329");
    }

    public function testValidateNonUtf8() {
        $validator = new NameserverListComplexValidator();
        $validator->nameserversCount = 'attrib2';
        $validator->dname = 'attrib3';
        $validator->ipv4Name = 'attrib4';
        $validator->ipv6Name = 'attrib5';
        $model = new MockedFormModel();
        $model->attrib3 = 'nameserver-test.ie';
        NameserverListValidationTestUtil::initNameserversWithIp($model, 'attrib1', 'attrib2', 'attrib4', 'attrib5', 7, "nameserver-test.ie", "10.10.1." . Utf8ValidationTestUtil::$NON_UTF8, "FE80::0202:B3FF:FE1E:8329" . Utf8ValidationTestUtil::$NON_UTF8);
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkErrorsIp($model, 'attrib4', 'attrib5', 7, "Not a valid UTF-8", "Not a valid UTF-8");
    }

    public function testValidate4Bytes() {
        $validator = new NameserverListComplexValidator();
        $validator->nameserversCount = 'attrib2';
        $validator->dname = 'attrib3';
        $validator->ipv4Name = 'attrib4';
        $validator->ipv6Name = 'attrib5';
        $model = new MockedFormModel();
        $model->attrib3 = 'nameserver-test.ie';
        NameserverListValidationTestUtil::initNameserversWithIp($model, 'attrib1', 'attrib2', 'attrib4', 'attrib5', 7, "nameserver-test.ie", "10.10.1." . Utf8ValidationTestUtil::$FOUR_BYTES, "FE80::0202:B3FF:FE1E:8329" . Utf8ValidationTestUtil::$FOUR_BYTES);
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkErrorsIp($model, 'attrib4', 'attrib5', 7, "Forbidden UTF-8 character", "Forbidden UTF-8 character");
    }

    public function testDetectingGlue() {
        $validator = CValidator::createValidator("NameserverListComplexValidator", null, "attrib1", array(
            "nameserversCount" => "attrib2","ipv4Name" => "attrib3","ipv6Name" => "attrib4","dname" => "attrib5"));
        $model = $this->getMockedNameserver("domain.ie", "ns.dómáín.ie", "1.2.3.4", "::1");
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertCount(2, $errors);
        self::assertEquals("Glue is not allowed", $errors['attrib3[0]'][0]);
        self::assertEquals("Glue is not allowed", $errors['attrib4[0]'][0]);
        $model = $this->getMockedNameserver("domain.ie", "ns.xn--dmn-fla4d7a.ie", "1.2.3.4", "::1");
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertCount(2, $errors);
        self::assertEquals("Glue is not allowed", $errors['attrib3[0]'][0]);
        self::assertEquals("Glue is not allowed", $errors['attrib4[0]'][0]);
        $model = $this->getMockedNameserver("dómáín.ie", "ns.dómáín.ie");
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertCount(1, $errors);
        self::assertEquals("Nameserver 1 must have glue defined", $errors['attrib1[0]'][0]);
        $model = $this->getMockedNameserver("dómáín.ie", "ns.dómáín.ie.");
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertCount(1, $errors);
        self::assertEquals("Nameserver 1 must have glue defined", $errors['attrib1[0]'][0]);
        $model = $this->getMockedNameserver("dómáín.ie", "ns.xn--dmn-fla4d7a.ie");
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertCount(1, $errors);
        self::assertEquals("Nameserver 1 must have glue defined", $errors['attrib1[0]'][0]);
    }

    private function getMockedNameserver($domain, $ns, $ipv4 = '', $ipv6 = '') {
        $model = new MockedFormModel();
        $model->attrib1 = array($ns,'tmp.domain.nameserver.ie');
        $model->attrib2 = 2;
        $model->attrib3 = array($ipv4);
        $model->attrib4 = array($ipv6);
        $model->attrib5 = $domain;
        return $model;
    }

}
