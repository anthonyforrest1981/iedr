<?php

class CreditCardFormSegmentModelTestUtil extends PHPUnit_Framework_TestCase {

    private static function _testRequired($model) {
        $fieldsRequired = array('creditcardno','cardholder','exp_year','exp_month','cardtype','cvn');
        if ($model->scenario === 'topup')
            $fieldsRequired[] = 'euros_amount';
        foreach ($fieldsRequired as $field) {
            $model->$field = "";
            $model->validate();
            $errorMessage = $model->getError($field);
            self::assertTrue(isset($errorMessage));
            $labelArray = $model->attributeLabels();
            self::assertEquals($labelArray[$field] . " cannot be blank.", $errorMessage);
        }
    }

    private static function _testNormalize($model) {
        $model->cardholder = "To-be-norma\xCC\x84li\xCC\xA3zed";
        $model->cardtype = "VI\xCC\x84SA";
        $model->validate();
        $normalizedCardholder = "To-be-norm\xC4\x81l\xE1\xBB\x8Bzed";
        $normalizedCardtype = "V\xC4\xAASA";
        self::assertEquals($normalizedCardholder, $model->cardholder);
        self::assertEquals($normalizedCardtype, $model->cardtype);
    }

    private function _testValidate4Bytes($model) {
        $model->cardholder = "\xF0\x90\x8D\x88";
        $model->cardtype = "VIS\xF0\x90\x8D\x88";
        $model->validate();
        $cardholderErrorMessage = $model->getError('cardholder');
        $cardtypeErrorMessage = $model->getError('cardholder');
        self::assertTrue(isset($cardholderErrorMessage));
        self::assertTrue(isset($cardtypeErrorMessage));
        self::assertEquals("Forbidden UTF-8 character", $cardholderErrorMessage);
        self::assertEquals("Forbidden UTF-8 character", $cardtypeErrorMessage);
    }

    private static function _testExpYear($model) {
        $model->exp_year = "AA";
        $model->validate();
        $errorMessage = $model->getError('exp_year');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Expiration Year must be an integer.", $errorMessage);
        $year = date('y', time());
        $model->exp_year = $year;
        $model->validate();
        $errorMessage = $model->getError('exp_year');
        self::assertFalse(isset($errorMessage));
        $model->exp_year = $year + 15;
        $model->validate();
        $errorMessage = $model->getError('exp_year');
        self::assertFalse(isset($errorMessage));
        $model->exp_year = $year - 1;
        $model->validate();
        $errorMessage = $model->getError('exp_year');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Expiration Year is too small (minimum is " . $year . ").", $errorMessage);
        $model->exp_year = $year + 16;
        $model->validate();
        $errorMessage = $model->getError('exp_year');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Expiration Year is too big (maximum is " . ($year + 15) . ").", $errorMessage);
    }

    private static function _testExpMonth($model) {
        $model->exp_month = "AA";
        $model->validate();
        $errorMessage = $model->getError('exp_month');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Expiration Month must be an integer.", $errorMessage);
        $model->exp_month = 1;
        $model->validate();
        $errorMessage = $model->getError('exp_month');
        self::assertFalse(isset($errorMessage));
        $model->exp_month = 12;
        $model->validate();
        $errorMessage = $model->getError('exp_month');
        self::assertFalse(isset($errorMessage));
        $model->exp_month = 0;
        $model->validate();
        $errorMessage = $model->getError('exp_month');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Expiration Month is too small (minimum is 1).", $errorMessage);
        $model->exp_month = 13;
        $model->validate();
        $errorMessage = $model->getError('exp_month');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Expiration Month is too big (maximum is 12).", $errorMessage);
    }

    private static function _testCvn($model) {
        $validCvns = array("001","999");
        $invalidCvns = array("AAA","1","99","1000");
        foreach ($validCvns as $value) {
            $model->cvn = $value;
            $model->validate();
            $errorMessage = $model->getError('cvn');
            self::assertFalse(isset($errorMessage));
        }
        foreach ($invalidCvns as $value) {
            $model->cvn = $value;
            $model->validate();
            $errorMessage = $model->getError('cvn');
            self::assertTrue(isset($errorMessage));
            self::assertEquals("Card Verification Number must be a three digit number", $errorMessage);
        }
    }

    private static function _testCardNo($model) {
        $model->creditcardno = "AAAA";
        $model->validate();
        $errorMessage = $model->getError('creditcardno');
        self::assertTrue(isset($errorMessage));
        self::assertEquals("Card Number is not a valid CreditCard number", $errorMessage);
    }

    private static function _testCardHolder($model) {
        $bad_card_holders = array("Zażółć",
            // Latin-1 extended punctuations
            "¡","¢","£","¤","¥","¦","§","¨","©","«","¬","®","¯","°","±","´","¶","·","¸","»","¼","½","¾","¿","×","÷",
            // couple of non-Latin-1 punctuations
            "‐","‒","—","―","‘","’","‚","‛","“","”","„","‟","•","․","‥","…","‧","′","″","‴","‵","‶","‷","‹","›","‼","‽",
            "‾","⁇","⁈","⁓","⁎");
        foreach ($bad_card_holders as $holder) {
            $model->cardholder = $holder;
            $model->validate();
            $errorMessage = $model->getError('cardholder');
            self::assertTrue(isset($errorMessage), "Holder $holder should be considered bad");
            self::assertEquals("Card Holder is invalid.", $errorMessage);
        }
        $good_holders = array("Eóin O' Leary","François Hollande","Mußter Där","John Smith",
            "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞß","àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ","!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
        foreach ($good_holders as $holder) {
            $model->cardholder = $holder;
            $model->validate();
            $errorMessage = $model->getError('cardholder');
            self::assertFalse(isset($errorMessage), "Holder $holder should be considered OK");
        }
    }

    public static function _testModel($model) {
        self::_testRequired($model);
        self::_testNormalize($model);
        self::_testValidate4Bytes($model);
        self::_testCardHolder($model);
        self::_testExpYear($model);
        self::_testExpMonth($model);
        self::_testCvn($model);
        self::_testCardNo($model);
    }

}
