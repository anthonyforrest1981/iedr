<?php

class UtilityTest extends PHPUnit_Framework_TestCase {

    public function testCurrencyAmount() {
        $this->assertEquals(0, Utility::currencyAmount(null));
        $this->assertEquals('&euro;&nbsp;1.00', Utility::currencyAmount(1));
        $this->assertEquals('&euro;&nbsp;1.00', Utility::currencyAmount(1, "EUR"));
        $this->assertEquals('$&nbsp;1.00', Utility::currencyAmount(1, "USD"));
        $this->assertEquals('£&nbsp;1.00', Utility::currencyAmount(1, "GBP"));
        $this->assertEquals('$&nbsp;1.99', Utility::currencyAmount(1.99, "USD"));
        $this->assertEquals('$&nbsp;1.99', Utility::currencyAmount(1.991, "USD"));
        $this->assertEquals('$&nbsp;1,000.01', Utility::currencyAmount(1000.01, "USD"));
        $this->assertEquals('$&nbsp;1,000,000.01', Utility::currencyAmount(1000000.01, "USD"));
    }

    public function testMbTrim() {
        // Latin Extended-A
        $this->assertEquals("Ă", Utility::mb_trim("  Ă   "));
        // Latin Extended-B
        $this->assertEquals("Ȧ", Utility::mb_trim("  Ȧ   "));
        // emoji, tab and non-breakable space
        $this->assertEquals("😸", Utility::mb_trim("   😸\t "));
        // check custom characters
        $this->assertEquals("Ă", Utility::mb_trim(" , Ă ; ::", ',:;'));
        // check only custom characters
        $this->assertEquals(" Ă; ", Utility::mb_trim(",, Ă; ,", ',', true));
    }

    public function testMbRTrim() {
        // Latin Extended-A
        $this->assertEquals("  Ă", Utility::mb_rtrim("  Ă   "));
        // Latin Extended-B
        $this->assertEquals("  Ȧ", Utility::mb_rtrim("  Ȧ   "));
        // emoji, tab and non-breakable space
        $this->assertEquals("   😸", Utility::mb_rtrim("   😸\t "));
        // check custom characters
        $this->assertEquals(" , Ă ; ", Utility::mb_rtrim(" , Ă ; ::", ',:;'));
        // check only custom characters
        $this->assertEquals(",, Ă; ", Utility::mb_rtrim(",, Ă; ,", ','));
    }

    public function testMbStrSplit() {
        $this->assertEquals(array("Ě","Ɯ","😄","🚀"), Utility::mb_str_split("ĚƜ😄🚀"));
    }

    public function testGlueRequired() {
        $this->assertTrue(Utility::isGlueRequired("dómáín.ie", "ns.dómáín.ie"));
        $this->assertTrue(Utility::isGlueRequired("xn--dmn-fla4d7a.ie", "ns.dómáín.ie"));
        $this->assertTrue(Utility::isGlueRequired("dómáín.ie", "ns.xn--dmn-fla4d7a.ie"));
        $this->assertTrue(Utility::isGlueRequired("xn--dmn-fla4d7a.ie", "ns.xn--dmn-fla4d7a.ie"));
        $this->assertFalse(Utility::isGlueRequired("domáín.ie", "ns.dómáín.ie"));
        $this->assertFalse(Utility::isGlueRequired("xn--dmn-fla4d7a.ie", "ns.domáín.ie"));
        $this->assertFalse(Utility::isGlueRequired("domáín.ie", "ns.xn--dmn-fla4d7a.ie"));
        $this->assertFalse(Utility::isGlueRequired("xn--domn-7na6e.ie", "ns.xn--dmn-fla4d7a.ie"));
        $this->assertTrue(Utility::isGlueRequired("Dómáín.ie", "ns.dómáín.ie"));
        $this->assertTrue(Utility::isGlueRequired("Dómáín.ie", "ns.Dómáín.ie"));
        $this->assertFalse(Utility::isGlueRequired("Domáín.ie", "ns.dómáín.ie"));
        $this->assertFalse(Utility::isGlueRequired("domáín.ie", "ns.Dómáín.ie"));
    }

}
