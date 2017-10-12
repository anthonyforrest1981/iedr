<?php

class IEDomainValidatorTest extends PHPUnit_Framework_TestCase {

    public function testCorrectDomainName() {
        $this->shouldBeCorrect("domain.ie");
        $this->shouldFail("domain.pl", "domain.pl is not a valid IEDR domain address");
        $this->shouldFail("domain.iie", "domain.iie is not a valid domain address");
        $this->shouldFail("zażółć.ie", "zażółć.ie is not a valid IEDR domain address");
        $this->shouldFail("zażółć.pl", "zażółć.pl is not a valid IEDR domain address");
        $this->shouldFail("a.ie", "One character domain addresses like a.ie are not allowed");
        $this->shouldFail("ae.ie", "Two-letter domain addresses like ae.ie are not allowed");
        $this->shouldBeCorrect("a.ie", array('checkAsANewDomain' => false));
        $this->shouldBeCorrect("ae.ie", array('checkAsANewDomain' => false));
        $this->shouldFail("aáàcçeéèiíìoóòuúù.ie", "aáàcçeéèiíìoóòuúù.ie is not a valid IEDR domain address");
        $this->shouldFail("aáceéiíoóuú.ie", "aáceéiíoóuú.ie is not a valid IEDR domain address");
        $this->shouldBeCorrect("aáceéiíoóuú.ie", array('checkAsANewDomain' => false));
        $this->shouldFail("xn--za-6ja4f8n1l.xn--gsl-kpa1h.xn--ja-4qa9s.ie", "xn--za-6ja4f8n1l.xn--gsl-kpa1h.xn--ja-4qa9s.ie is not a valid IEDR domain address");
        $this->shouldFail("xn--za-6ja4f8n1l.ie", "xn--za-6ja4f8n1l.ie is not a valid IEDR domain address");
        $this->shouldFail("xn--za-6ja4f8n1l.iee", "xn--za-6ja4f8n1l.iee is not a valid domain address");
        $this->shouldBeCorrect("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabc.ie");
        $this->shouldFail("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcd.ie", "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcd.ie is not a valid domain address");
        $this->shouldBeCorrect("a.ie", array('checkAsANewDomain' => false));
        $this->shouldBeCorrect("á.ie", array('checkAsANewDomain' => false));
        $this->shouldBeCorrect("ae.ie", array('checkAsANewDomain' => false));
        $this->shouldBeCorrect("áé.ie", array('checkAsANewDomain' => false));
        MockedAppConfig::$ONE_TWO_LETTER_ALLOWED = true;
        $this->shouldBeCorrect("a.ie");
        $this->shouldFail("á.ie", "á.ie is not a valid IEDR domain address");
        $this->shouldBeCorrect("ae.ie");
        $this->shouldFail("áé.ie", "áé.ie is not a valid IEDR domain address");
        MockedAppConfig::$IDN_DOMAIN_ALLOWED = true;
        $this->shouldBeCorrect("á.ie");
        $this->shouldBeCorrect("áé.ie");
        $this->shouldBeCorrect("aáceéiíoóuú.ie");
        $this->shouldBeCorrect("aáceéiíoóuú.ie", array('checkAsANewDomain' => false));
        $this->shouldFail("aábcdeéfghiíjklmnoóprstquúwxyzaábcdeéfghiíjklmnoóprstquúwxyz.ie", "aábcdeéfghiíjklmnoóprstquúwxyzaábcdeéfghiíjklmnoóprstquúwxyz.ie is not a valid domain address");
        $this->shouldBeCorrect("ábcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdef.ie");
        $this->shouldFail("ábcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefg.ie", "ábcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefg.ie is not a valid domain address");
        $this->shouldFail("<script>alert(document.cookie)</script>", "&lt;script&gt;alert(document.cookie)&lt;/script&gt; is not a valid domain address");
    }

    private function shouldBeCorrect($domainName, $params = array()) {
        $params = array_merge(array('checkAsANewDomain' => true), $params);
        $errorMessage = ValidatorTestUtil::testDomain($domainName, "IEDomainValidator", $params);
        self::assertFalse(isset($errorMessage), "$domainName should be considered correct");
    }

    private function shouldFail($domainName, $message, $params = array()) {
        $params = array_merge(array('checkAsANewDomain' => true), $params);
        $errorMessage = ValidatorTestUtil::testDomain($domainName, "IEDomainValidator", $params);
        self::assertTrue(isset($errorMessage), "$domainName should be considered incorrect");
        self::assertEquals($errorMessage, $message, "$domainName is incorrect, but with bad error message");
    }

}
