package pl.nask.crs.commons.utils;

import org.testng.annotations.Test;

import pl.nask.crs.nichandle.AbstractContextAwareTest;

public class EmailValidatorTest extends AbstractContextAwareTest {

    @Test
    public void correctEmailTest() throws Exception {
        EmailValidator.validateEmail("email@aaa.xxx");
    }

    @Test
    public void correctNonAsciiEmailTest() throws Exception {
        EmailValidator.validateEmail("email@ąęśćń.xxx");
    }

    @Test
    public void correctStrangeUserEmailTest() throws Exception {
        EmailValidator.validateEmail("!#$%&'*+/=?^_`{|}~@aaa.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void noEmailTest() throws Exception {
        EmailValidator.validateEmail(null);
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void emptyEmailTest() throws Exception {
        EmailValidator.validateEmail("");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void noAtInEmailTest() throws Exception {
        EmailValidator.validateEmail("email.aaa.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void twoAtsInEmailTest() throws Exception {
        EmailValidator.validateEmail("e@mail@aaa.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void emptyUserSegmentEmailTest() throws Exception {
        EmailValidator.validateEmail("@aaa.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void nonAsciiUserSegmentEmailTest() throws Exception {
        EmailValidator.validateEmail("éire@aaa.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void emptyDomainSegmentEmailTest() throws Exception {
        EmailValidator.validateEmail("email.aaa@");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void emailTooLongInEmailTest() throws Exception {
        EmailValidator
                .validateEmail("email@aaa.bbb.ccc.ddd.eee.fff.ggg.hhh.iii.jjj.kkk.lll.mmm.nnn.ooo.ppp.qqq.rrr.sss.ttt.uuu.vvv.www.xxx.yyy.zzz.aaa.bbb.ccc.ddd.eee.fff.ggg.hhh.iii.jjj.kkk.lll.mmm.nnn.ooo.ppp.qqq.rrr.sss.ttt.uuu.vvv.www.xxx.yyy.zzz.aaa.bbb.ccc.ddd.eee.fff.ggg.hhh.iii.jjj.ie");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void punycodeTooLongInEmailTest() throws Exception {
        EmailValidator
                .validateEmail("email@ááá.ééé.ííí.óóó.úúú.ááá.ééé.ííí.óóó.úúú.ááá.ééé.ííí.óóó.úúú.ááá.ééé.ííí.óóó.úúú.ááá.ééé.ííí.óóó.úúú.ie");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void labelTooLongInEmailTest() throws Exception {
        EmailValidator.validateEmail("email@aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrssstttuuuvvvwww.xxx");
    }

    // Unicode < 63, Punycode > 63
    @Test(expectedExceptions = InvalidEmailException.class)
    public void nonAsciiLabelTooLongInEmailTest() throws Exception {
        EmailValidator.validateEmail("email@āăąćĉċčďđēĕėęěĝğġģĥħĩīĭįıĳĵķĺļľŀ.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void userSegmentTooLongTest() throws Exception {
        EmailValidator
                .validateEmail("emaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaail@aaa.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void emptyLabelInEmailTest() throws Exception {
        EmailValidator.validateEmail("email@aaa..xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void incorrectCharInEmailTest() throws Exception {
        EmailValidator.validateEmail("email@aaa,bbb.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void misplacedHypenInEmailTest() throws Exception {
        EmailValidator.validateEmail("email@aaa.-bbb.xxx");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void incorrectTopLevelDomainInEmailTest() throws Exception {
        EmailValidator.validateEmail("email@aaa.kom");
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void secondMonkeyAtTheEndTest() throws Exception {
        EmailValidator.validateEmail("email@aaa.xxx@");
    }
}
