package pl.nask.crs.api.validation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.app.GenericValidationException;

public class ValidationHelperTest {

    @DataProvider
    public static Object[][] badCardHolders() {
        return new Object[][] {
                {"Zażółć"},
                // Latin-1 extended punctuation
                {"¡"}, {"¢"}, {"£"}, {"¤"}, {"¥"}, {"¦"}, {"§"}, {"¨"}, {"©"}, {"«"}, {"¬"}, {"®"}, {"¯"}, {"°"},
                {"±"}, {"´"}, {"¶"}, {"·"}, {"¸"}, {"»"}, {"¼"}, {"½"}, {"¾"}, {"¿"}, {"×"},
                {"÷"},
                // couple of non-Latin-1 punctuations that user might end up writing (e.g. by copy-pasting from a word processor)
                {"‐"}, {"‒"}, {"—"}, {"―"}, {"‘"}, {"’"}, {"‚"}, {"‛"}, {"“"}, {"”"}, {"„"}, {"‟"}, {"•"}, {"․"},
                {"‥"}, {"…"}, {"‧"}, {"′"}, {"″"}, {"‴"}, {"‵"}, {"‶"}, {"‷"}, {"‹"}, {"›"}, {"‼"}, {"‽"}, {"‾"},
                {"⁇"}, {"⁈"}, {"⁓"}, {"\u2055"}, {"⁎"},};
    }

    @DataProvider
    public static Object[][] goodCardHolders() {
        return new Object[][] { {"Eóin O' Leary"}, {"François Hollande"}, {"Mußter Där"}, {"John Smith"}, {"El Niño"},
                {"Æthër Þ ÇÁØ"}, {"ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞß"},
                {"àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ"},
                // Latin-1 standard punctuation
                {"!"}, {"\""}, {"#"}, {"$"}, {"%"}, {"&"}, {"'"}, {"("}, {")"}, {"*"}, {"+"}, {","}, {"-"}, {"."},
                {"/"}, {":"}, {";"}, {"<"}, {"="}, {">"}, {"?"}, {"@"}, {"["}, {"\\"}, {"]"}, {"^"}, {"_"}, {"`"},
                {"{"}, {"|"}, {"}"}, {"~"},};
    }

    @Test(dataProvider = "badCardHolders", expectedExceptions = GenericValidationException.class)
    public void creditCardHolderBadCharacterTest(String cardHolder) throws GenericValidationException {
        CreditCardVO testData = prepareCreditCardVO();
        testData.setCardHolderName(cardHolder);
        ValidationHelper.validate(testData);
    }

    @Test(dataProvider = "goodCardHolders")
    public void creditCardHolderAllowedCharactersTest(String cardHolder) throws GenericValidationException {
        CreditCardVO testData = prepareCreditCardVO();
        testData.setCardHolderName(cardHolder);
        ValidationHelper.validate(testData);
    }

    private CreditCardVO prepareCreditCardVO() {
        CreditCardVO testData = new CreditCardVO();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("MMyy"); // Just the year, with 2 digits
        String expdate = sdf.format(cal.getTime());
        testData.setCardExpDate(expdate);
        testData.setCardHolderName("Card holder");
        testData.setCardNumber("1234 1234 1234 1234");
        testData.setCardType("VISA");
        testData.setCvnNumber("123");
        testData.setCvnPresenceIndicator(1);
        return testData;
    }
}
