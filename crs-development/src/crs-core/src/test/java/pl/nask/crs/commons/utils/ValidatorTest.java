package pl.nask.crs.commons.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;

public class ValidatorTest {

    @Test
    public void assertNotEmptyShouldFailForNullString() {
        try {
            Validator.assertNotEmpty((String) null, "valueName");
            Assert.fail("Expected to fail with IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertNotEmptyShouldFailForNullCollection() {
        try {
            Validator.assertNotEmpty((Collection<?>) null, "valueName");
            Assert.fail("Expected to fail with IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertNotEmptyShouldFailForEmptyString() {
        try {
            Validator.assertNotEmpty("", "valueName");
            Assert.fail("Expected to fail with IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertNotEmptyShouldFailForEmptyCollection() {
        try {
            Validator.assertNotEmpty(Collections.EMPTY_SET, "valueName");
            Assert.fail("Expected to fail with IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertNotEmptyShouldFailForWhitespaces() {
        try {
            Validator.assertNotEmpty("  \n\t", "valueName");
            Assert.fail("Expected to fail with IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertNotEmptyShouldNotFailForNotEmptyString() {
        Validator.assertNotEmpty("a string", "valueName");
    }

    @Test
    public void assertNotEmptyShouldNotFailForNotEmptyCollection() {
        Validator.assertNotEmpty(Collections.singleton("a"), "valueName");
    }

    @Test
    public void isEmptyShouldBeTrueForNullString() {
        Assert.assertTrue(Validator.isEmpty((String) null));
    }

    @Test
    public void isEmptyShouldBeTrueForNullCollection() {
        Assert.assertTrue(Validator.isEmpty((Collection<?>) null));
    }

    @Test
    public void isEmptyShouldBeTrueForEmptyString() {
        Assert.assertTrue(Validator.isEmpty(""));
    }

    @Test
    public void isEmptyShouldBeTrueForEmptyCollection() {
        Assert.assertTrue(Validator.isEmpty(Collections.EMPTY_SET));
    }

    @Test
    public void isEmptyShouldBeTrueForWhitespaces() {
        Assert.assertTrue(Validator.isEmpty("  \n\t"));
    }

    @Test
    public void isEmptyShouldBeFalseForNotEmptyString() {
        Assert.assertFalse(Validator.isEmpty("a string"));
    }

    @Test
    public void isEmptyShouldBeFalseForNotEmptyCollection() {
        Assert.assertFalse(Validator.isEmpty(Collections.singleton("a")));
    }

    private void assertExceptionMessage(IllegalArgumentException e, String paramName) {
        Assert.assertTrue(e.getMessage().contains(paramName),
                "Exception message should contain info about the validated param name");
    }

    @Test
    public void assertNotNullShouldFailForNullValue() {
        try {
            Validator.assertNotNull(null, "valueName");
            Assert.fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertNotNullShouldNotFailForNonNullValue() {
        Validator.assertNotNull("aaa", "valueName");
    }

    @Test
    public void assertTrueShouldFailForFalseValue() {
        try {
            Validator.assertTrue(false, "valueName");
            Assert.fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertExceptionMessage(e, "valueName");
        }
    }

    @Test
    public void assertTrueShouldNotFailForTrueValue() {
        Validator.assertTrue(true, "valueName");
    }

    @Test
    public void hasDuplicatesShouldReturnFalseIfCollectionElementsAreUnique() {
        Assert.assertFalse(Validator.hasDuplicates(Arrays.asList("a", "b", "c", "d")));
    }

    @Test
    public void hasDuplicatesShouldReturnTrueIfCollectionHasDuplicatedElements() {
        Assert.assertTrue(Validator.hasDuplicates(Arrays.asList("a", "b", "c", "b")));
    }

    @Test
    public void getDuplicatesShouldReturnNullIfCollectionElementsAreUnique() {
        Assert.assertNull(Validator.getDuplicates(Arrays.asList("a", "b", "c", "d")));
    }

    @Test
    public void getDuplicatesShouldReturnDuplicatedElementIfCollectionHasDuplicatedElements() {
        Assert.assertEquals("b", Validator.getDuplicates(Arrays.asList("a", "b", "c", "b")));
    }

    @Test
    public void isEqualShouldBeTrueForNullArguments() {
        Assert.assertTrue(Validator.isEqual(null, null));
    }

    @Test
    public void isEqualShouldBeTrueForEqualArguments() {
        Assert.assertTrue(Validator.isEqual("a", "a"));
    }

    @Test
    public void isEqualShouldBeFalseIfOneArgumentIsNull() {
        Assert.assertFalse(Validator.isEqual("a", null));
        Assert.assertFalse(Validator.isEqual(null, "a"));
    }

    @Test
    public void isEqualShouldBeFalseIfArgumentsAreNotEqual() {
        Assert.assertFalse(Validator.isEqual("a", "b"));
    }

    @Test
    public void stringIncluding3byteUtf8ShouldBeValid() throws Exception {
        String str = "\u20AC"; // €
        String normalized = Validator.getNormalizedAndValidatedUtf8(str);
        Assert.assertEquals(str, normalized);
    }

    @Test(expectedExceptions = IncorrectUtf8FormatException.class)
    public void stringIncluding4byteUtf8ShouldNotBeValid() throws Exception {
        // instead of "\u10348" (not recognized by Java) - an exaple of 4-byte UTF-8
        String str = new String(Character.toChars(Integer.parseInt("10348", 16)));
        Validator.getNormalizedAndValidatedUtf8(str);
    }

    @Test
    public void stringIncludingUtf8InNfdShouldBeNormalized() throws Exception {
        String str = "A" + "\u0308"; // umlaut
        String normalized = Validator.getNormalizedAndValidatedUtf8(str);
        String expected = "\u00C4"; // A with umlaut
        Assert.assertEquals(expected, normalized);
    }

    @Test
    public void stringIncludingUtf8InNfcShouldRemainUnchanged() throws Exception {
        String str = "\u00C4"; // A with umlaut
        String normalized = Validator.getNormalizedAndValidatedUtf8(str);
        Assert.assertEquals(str, normalized);
    }

    private void verifyForbiddenChar(char ch) {
        String str = "" + ch;
        try {
            Validator.getNormalizedAndValidatedUtf8(str);
            Assert.fail("String including #" + (int) ch + " should not be valid");
        } catch (IncorrectUtf8FormatException e) {
            return;
        }
    }

    @Test
    public void stringContainingNonPrintableCharShouldNotBeValid() throws Exception {
        // ASCII codes of chars that are ISO controllers and are not whitespaces: 0 to 8; 14 to 27 and 127
        // validation should fail for a string containing any of them
        for (int code = 0; code <= 8; code++) {
            verifyForbiddenChar((char) code);
        }
        for (int code = 14; code <= 27; code++) {
            verifyForbiddenChar((char) code);
        }
        verifyForbiddenChar((char) 127);
        // Unicode block: SPECIALS
        for (char ch = '\ufff0'; ch != (char) 0; ch++) {
            verifyForbiddenChar(ch);
        }
    }

    @Test
    public void stringContainingWhitespaceShouldBeValid() throws Exception {
        // full list of ASCII codes of chars that are ISO controllers and are whitespaces
        // validation should pass for a string containing them
        int[] isoControllersIgnorable = {9, 10, 11, 12, 13, 28, 29, 30, 31};
        for (int code : isoControllersIgnorable) {
            String str = new String(Character.toChars(code));
            Validator.getNormalizedAndValidatedUtf8(str);
        }
    }

}
