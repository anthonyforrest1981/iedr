package pl.nask.crs.commons;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class AuthcodeTest {

    @Test
    public void isValidTest() {
        assertTrue(AuthcodeGenerator.isValid("THIS0IS1A2TEST2"));
        assertFalse(AuthcodeGenerator.isValid("THIS0IS1A2TEST1"));
        assertFalse(AuthcodeGenerator.isValid(""));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void isValidBadCharacterTest() {
        AuthcodeGenerator.isValid("aa");
    }

    @Test
    public void generateValidateTest() {
        String in = AuthcodeGenerator.generateValidated(10);
        assertNotNull(in);
        assertEquals(in.length(), 10);
        assertTrue(AuthcodeGenerator.isValid(in));
    }

    @Test
    public void generateRandom() {
        String in = AuthcodeGenerator.generateRandom(10);
        assertNotNull(in);
        assertEquals(in.length(), 10);
    }

}
