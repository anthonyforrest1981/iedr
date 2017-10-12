<?php

class Utf8ValidationTestUtil extends PHPUnit_Framework_TestCase {
    public static $UNNORMALIZED = "To-be-norma\xCC\x84li\xCC\xA3zed";
    public static $NORMALIZED = "To-be-norm\xC4\x81l\xE1\xBB\x8Bzed";
    public static $FOUR_BYTES = "\xF0\x90\x8D\x88";
    public static $NON_UTF8 = "E\xA0";

    public static function _testNormalizeWithCaseMode($model, $utf8FieldsToValidateWithCaseMode, $value = null, $normalized = null) {
        $value = isset($value) ? $value : self::$UNNORMALIZED;
        $normalized = isset($normalized) ? $normalized : self::$NORMALIZED;
        foreach ($utf8FieldsToValidateWithCaseMode as $field => $case) {
            $model->$field = $value;
        }
        $model->validate();
        foreach ($utf8FieldsToValidateWithCaseMode as $field => $case) {
            $str = $normalized;
            if ($case === 'U') {
                $str = mb_strtoupper($str);
            } else if ($case === 'L') {
                $str = mb_strtolower($str);
            }
            self::assertEquals($str, $model->$field);
        }
    }

    public static function _testNormalize($model, $utf8FieldsToValidate, $value = null, $normalized = null) {
        $value = isset($value) ? $value : self::$UNNORMALIZED;
        $normalized = isset($normalized) ? $normalized : self::$NORMALIZED;
        $utf8FieldsToValidateWithCaseMode = array();
        foreach ($utf8FieldsToValidateWithCaseMode as $field) {
            $utf8FieldsToValidateWithCaseMode[$field] = 'N';
        }
        self::_testNormalizeWithCaseMode($model, $utf8FieldsToValidateWithCaseMode, $value, $normalized);
    }

    public static function _testNormalizeSingle($model, $field, $value = null, $normalized = null) {
        self::_testNormalize($model, array($field), $value, $normalized);
    }

    public static function _testValidate($model, $utf8FieldsToValidate, $value, $message) {
        foreach ($utf8FieldsToValidate as $field) {
            $model->$field = $value;
        }
        $model->validate();
        foreach ($utf8FieldsToValidate as $field) {
            $errorMessage = $model->getError($field);
            self::assertTrue(isset($errorMessage), "Error should be detected in field: " . $field);
            self::assertEquals($message, $errorMessage);
        }
    }

    public static function _testValidateSingle($model, $field, $value, $message) {
        self::_testValidate($model, array($field), $value, $message);
    }

    public static function _testValidateNonUtf8($model, $utf8FieldsToValidate) {
        self::_testValidate($model, $utf8FieldsToValidate, self::$NON_UTF8, "Not a valid UTF-8");
    }

    public static function _testValidateNonUtf8Single($model, $field) {
        self::_testValidateSingle($model, $field, self::$NON_UTF8, "Not a valid UTF-8");
    }

    public static function _testValidate4Bytes($model, $utf8FieldsToValidate) {
        self::_testValidate($model, $utf8FieldsToValidate, self::$FOUR_BYTES, "Forbidden UTF-8 character");
    }

    public static function _testValidate4BytesSingle($model, $field) {
        self::_testValidateSingle($model, $field, self::$FOUR_BYTES, "Forbidden UTF-8 character");
    }

}
