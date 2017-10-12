<?php

class HostNameValidatorTest extends PHPUnit_Framework_TestCase {

    public function testCorrectDomainName() {
        $this->shouldBeCorrect("correct.domain.ie");
        $this->shouldFail("correct.domain.iee");
        $this->shouldBeCorrect("zażółć.gęslą.jaźń.pl");
        $this->shouldFail("zażółć.pe");
        $this->shouldBeCorrect("a.ie");
        $this->shouldBeCorrect("aáàcçeéèiíìoóòuúù.ie");
        $this->shouldBeCorrect("xn--za-6ja4f8n1l.xn--gsl-kpa1h.xn--ja-4qa9s.pl");
        $this->shouldFail("xn--za-6ja4f8n1l.pe");
        $this->shouldBeCorrect("xn--aceiou-itah0dlk2bm6eo2hqa.ie");
        $this->shouldFail("aáàbcçdeéèfghiíìjklmnoóòprstquúùwxyzaáàbcçdeéèfghiíìjklmnoóòprstquúùwxyz.ie");
        $this->shouldBeCorrect("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabc.ie");
        $this->shouldFail("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcd.ie");
        $this->shouldBeCorrect("ábcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdef.ie");
        $this->shouldFail("ábcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefg.ie");
        $this->shouldBeCorrect("xn-almost-punycode.ie");
        $this->shouldFail("-startswithadash.ie");
        $this->shouldFail("endswithadash-.ie");
        $this->shouldFail("<script>alert(document.cookie)</script>");
        $this->shouldBeCorrect("domain.ie.");
        $this->shouldFail("domain.ie..");
    }

    private function shouldBeCorrect($domainName) {
        $errorMessage = ValidatorTestUtil::testDomain($domainName, "HostNameValidator");
        self::assertFalse(isset($errorMessage), "$domainName should be considered correct");
    }

    private function shouldFail($domainName) {
        $safeDomainName = htmlspecialchars($domainName, ENT_QUOTES, "UTF-8");
        $errorMessage = ValidatorTestUtil::testDomain($domainName, "HostNameValidator");
        self::assertTrue(isset($errorMessage), "$domainName should be considered incorrect");
        self::assertEquals($errorMessage, $safeDomainName . " is not a valid domain address", "$domainName is incorrect, but with bad error message");
    }

}
