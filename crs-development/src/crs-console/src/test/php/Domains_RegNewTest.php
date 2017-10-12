<?php

class Domains_RegNewTest extends PHPUnit_Framework_TestCase {

    public function testUtf8() {
        $model = new Domains_RegNew();
        Utf8ValidationTestUtil::_testNormalizeSingle($model, 'domain_names', Utf8ValidationTestUtil::$UNNORMALIZED . "1\n" . Utf8ValidationTestUtil::$UNNORMALIZED . "2", array(
            Utf8ValidationTestUtil::$NORMALIZED . "1",Utf8ValidationTestUtil::$NORMALIZED . "2"));
        Utf8ValidationTestUtil::_testValidateSingle($model, 'domain_names', Utf8ValidationTestUtil::$NON_UTF8 . "\n" . Utf8ValidationTestUtil::$NON_UTF8, "Not a valid UTF-8");
        Utf8ValidationTestUtil::_testValidateSingle($model, 'domain_names', Utf8ValidationTestUtil::$FOUR_BYTES . "\n" . Utf8ValidationTestUtil::$FOUR_BYTES, "Forbidden UTF-8 character");
    }

}