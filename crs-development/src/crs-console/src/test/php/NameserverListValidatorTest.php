<?php

class NameserverListValidatorTest extends PHPUnit_Framework_TestCase {

    public function testNormalize() {
        $validator = new NameserverListValidator();
        $validator->nameserversCount = 'attrib2';
        $model = new MockedFormModel();
        NameserverListValidationTestUtil::initNameservers($model, 'attrib1', 'attrib2', 7, "exa\xCC\x84mple.ie");
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkValues($model, 'attrib1', 7, "ex\xC4\x81mple.ie");
    }

    public function testValidateNonUtf8() {
        $validator = new NameserverListValidator();
        $validator->nameserversCount = 'attrib2';
        $model = new MockedFormModel();
        NameserverListValidationTestUtil::initNameservers($model, 'attrib1', 'attrib2', 7, "ex" . Utf8ValidationTestUtil::$NON_UTF8 . "mple.ie");
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkErrors($model, 'attrib1', 7, "Not a valid UTF-8");
    }

    public function testValidate4Bytes() {
        $validator = new NameserverListValidator();
        $validator->nameserversCount = 'attrib2';
        $model = new MockedFormModel();
        NameserverListValidationTestUtil::initNameservers($model, 'attrib1', 'attrib2', 7, "ex" . Utf8ValidationTestUtil::$FOUR_BYTES . "mple.ie");
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkErrors($model, 'attrib1', 7, "Forbidden UTF-8 character");
    }

    public function testLetterCaseFilter() {
        $validator = new NameserverListValidator();
        $validator->nameserversCount = 'attrib2';
        $model = new MockedFormModel();
        NameserverListValidationTestUtil::initNameservers($model, 'attrib1', 'attrib2', 7, "EXAMPLE.IE", "NS");
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkValues($model, 'attrib1', 7, "EXAMPLE.IE", "NS");
    }

    public function testSuffixedDotFilter() {
        $validator = new NameserverListValidator();
        $validator->nameserversCount = 'attrib2';
        $model = new MockedFormModel();
        NameserverListValidationTestUtil::initNameservers($model, 'attrib1', 'attrib2', 7, "example.ie.");
        $validator->validateAttribute($model, 'attrib1');
        NameserverListValidationTestUtil::checkValues($model, 'attrib1', 7, "example.ie.");
    }

    public function testDetectingDoubleNameservers() {
        $validator = CValidator::createValidator("NameserverListValidator", null, "attrib1", array(
            "nameserversCount" => "attrib2"));
        $model = new MockedFormModel();
        $failingPairs = array(
            // IDN cases
            array("ns.dómáín.ie","ns.xn--dmn-fla4d7a.ie"),array("ns.xn--dmn-fla4d7a.ie","ns.dómáín.ie"),
            array("ns.dómáín.ie","ns.dómáín.ie"),array("ns.xn--dmn-fla4d7a.ie","ns.xn--dmn-fla4d7a.ie"),
            // normalized domain
            array("example.ie.","example.ie"),array("EXAMPLE.IE","example.ie"));
        foreach ($failingPairs as $failingPair) {
            $model->clearErrors();
            $model->attrib1 = $failingPair;
            $model->attrib2 = 2;
            $validator->validate($model, 'attrib1');
            $errors = $model->getErrors();
            self::assertCount(2, $errors);
            self::assertEquals("Nameserver 1 name duplicates 2", $errors['attrib1[0]'][0]);
            self::assertEquals("Nameserver 2 name duplicates 1", $errors['attrib1[1]'][0]);
        }
        $okPairs = array(array("ns.domáín.ie","ns.dómáín.ie"),array("ns.domáín.ie","ns.xn--dmn-fla4d7a.ie"),
            array("xn--domn-7na6e.ie","ns.dómáín.ie"),array("xn--domn-7na6e.ie","ns.xn--dmn-fla4d7a.ie"));
        foreach ($okPairs as $failingPair) {
            $model->clearErrors();
            $model->attrib1 = $failingPair;
            $model->attrib2 = 2;
            $validator->validate($model, 'attrib1');
            $errors = $model->getErrors();
            self::assertEmpty($errors);
        }
    }

    public function testDNSNameLength() {
        $validator = CValidator::createValidator("NameserverListValidator", null, "attrib1", array("nameserversCount" => "attrib2"));
        $model = new MockedFormModel();
        $model->clearErrors();
        $model->attrib1 = array(
            // 254 characters in max length segments, should fail
            "63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie",
            "domain.ie"
        );
        $model->attrib2 = 2;
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertCount(1, $errors);
        self::assertEquals("Nameserver 1 is not a valid host name", $errors['attrib1[0]'][0]);

        $model->clearErrors();
        $model->attrib1 = array(
            //correct case, 253 characters without trailing dot, 254 with it
            "63-character-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie",
            "63-kharacter-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-website.63-characters-is-the-longest-possible-domain-name-for-a-web.ie.",
            "domain.ie"
        );
        $model->attrib2 = 2;
        $validator->validate($model, 'attrib1');
        $errors = $model->getErrors();
        self::assertEmpty($errors);
    }
}
