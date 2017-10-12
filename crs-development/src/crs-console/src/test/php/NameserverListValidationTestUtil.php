<?php

class NameserverListValidationTestUtil extends PHPUnit_Framework_TestCase {

    private static function _testNormalize($model, $nameserverField, $nameserverCountField, $count = 7, $value = "exa\xCC\x84mple.ie", $normalized = "ex\xC4\x81mple.ie") {
        NameserverListValidationTestUtil::initNameservers($model, $nameserverField, $nameserverCountField, $count, $value);
        $model->validate();
        NameserverListValidationTestUtil::checkValues($model, $nameserverField, $count, $normalized);
    }

    private static function _testNormalizeEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count = 7, $value = "exa\xCC\x84mple.ie", $normalized = "ex\xC4\x81mple.ie") {
        NameserverListValidationTestUtil::initNameserversWithIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count, $value, "", "");
        $model->validate();
        NameserverListValidationTestUtil::checkValues($model, $nameserverField, $count, $normalized);
    }

    private static function _testNormalizeIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field, $count = 7, $domainName = 'nameserver-test.ie', $ipv4Value = "10.10.1.a\xCC\x84", $ipv6Value = "FE\xCC\x8F80::0202:B3FF:FE\xCC\xA31E:8329", $ipv4Normalized = "10.10.1.\xC4\x81", $ipv6Normalized = "F\xC8\x8480::0202:B3FF:F\xE1\xBA\xB81E:8329") {
        $model->$domainNameField = $domainName;
        NameserverListValidationTestUtil::initNameserversWithIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count, $domainName, $ipv4Value, $ipv6Value);
        $model->validate();
        NameserverListValidationTestUtil::checkValuesIp($model, $ipv4Field, $ipv6Field, $count, $ipv4Normalized, $ipv6Normalized);
    }

    private static function _testValidate($model, $nameserverField, $nameserverCountField, $value, $message, $count) {
        NameserverListValidationTestUtil::initNameservers($model, $nameserverField, $nameserverCountField, $count, $value);
        $model->validate();
        NameserverListValidationTestUtil::checkErrors($model, $nameserverField, $count, $message);
    }

    private static function _testValidateEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $value, $message, $count) {
        NameserverListValidationTestUtil::initNameserversWithIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count, $value, "", "");
        $model->validate();
        NameserverListValidationTestUtil::checkErrors($model, $nameserverField, $count, $message);
    }

    private static function _testValidateIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field, $domainName, $ipv4Value, $ipv6Value, $messageIpv4, $messageIpv6, $count) {
        $model->$domainNameField = $domainName;
        NameserverListValidationTestUtil::initNameserversWithIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count, $domainName, $ipv4Value, $ipv6Value);
        $model->validate();
        NameserverListValidationTestUtil::checkErrorsIp($model, $ipv4Field, $ipv6Field, $count, $messageIpv4, $messageIpv6);
    }

    private static function _testValidateNonUtf8($model, $nameserverField, $nameserverCountField, $count = 7, $value = null) {
        $value = $value === null ? "ex" . Utf8ValidationTestUtil::$NON_UTF8 . "mple.ie" : $value;
        self::_testValidate($model, $nameserverField, $nameserverCountField, $value, "Not a valid UTF-8", $count);
    }

    private static function _testValidateNonUtf8EmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count = 7, $value = null) {
        $value = $value === null ? "ex" . Utf8ValidationTestUtil::$NON_UTF8 . "mple.ie" : $value;
        self::_testValidateEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $value, "Not a valid UTF-8", $count);
    }

    private static function _testValidateNonUtf8Ip($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field, $count = 7, $domainName = 'nameserver-test.ie', $ipv4Value = null, $ipv6Value = null) {
        $ipv4Value = $ipv4Value === null ? "10.10.1." . Utf8ValidationTestUtil::$NON_UTF8 : $ipv4Value;
        $ipv6Value = $ipv6Value === null ? "1FE80::0202:B3FF:FE1E:8329" . Utf8ValidationTestUtil::$NON_UTF8 : $ipv6Value;
        self::_testValidateIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field, $domainName, $ipv4Value, $ipv6Value, "Not a valid UTF-8", "Not a valid UTF-8", $count);
    }

    private static function _testValidate4Bytes($model, $nameserverField, $nameserverCountField, $count = 7, $value = null) {
        $value = $value === null ? "ex" . Utf8ValidationTestUtil::$FOUR_BYTES . "mple.ie" : $value;
        self::_testValidate($model, $nameserverField, $nameserverCountField, $value, "Forbidden UTF-8 character", $count);
    }

    private static function _testValidate4BytesEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count = 7, $value = null) {
        $value = $value === null ? "ex" . Utf8ValidationTestUtil::$FOUR_BYTES . "mple.ie" : $value;
        self::_testValidateEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $value, "Forbidden UTF-8 character", $count);
    }

    private static function _testValidate4BytesIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field, $count = 7, $domainName = 'nameserver-test.ie', $ipv4Value = null, $ipv6Value = null) {
        $ipv4Value = $ipv4Value === null ? "10.10.1." . Utf8ValidationTestUtil::$FOUR_BYTES : $ipv4Value;
        $ipv6Value = $ipv6Value === null ? "FE80::0202:B3FF:FE1E:8329" . Utf8ValidationTestUtil::$FOUR_BYTES : $ipv6Value;
        self::_testValidateIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field, $domainName, $ipv4Value, $ipv6Value, "Forbidden UTF-8 character", "Forbidden UTF-8 character", $count);
    }

    public static function _testUtf8NoIp($model, $nameserverField, $nameserverCountField) {
        self::_testNormalize($model, $nameserverField, $nameserverCountField);
        self::_testValidateNonUtf8($model, $nameserverField, $nameserverCountField);
        self::_testValidate4Bytes($model, $nameserverField, $nameserverCountField);
    }

    public static function _testUtf8($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field) {
        self::_testNormalizeEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field);
        self::_testValidateNonUtf8EmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field);
        self::_testValidate4BytesEmptyIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field);
        self::_testNormalizeIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field);
        self::_testValidateNonUtf8Ip($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field);
        self::_testValidate4BytesIp($model, $nameserverField, $nameserverCountField, $domainNameField, $ipv4Field, $ipv6Field);
    }

    public static function initNameservers($model, $nameserverField, $nameserverCountField, $count, $hostName, $prefix = "ns") {
        $nameservers = array();
        for ($i = 0; $i < $count; $i++) {
            $nameservers[$i] = $prefix . $i . "." . $hostName;
        }
        $model->$nameserverField = $nameservers;
        $model->$nameserverCountField = $count;
    }

    public static function initNameserversWithIp($model, $nameserverField, $nameserverCountField, $ipv4Field, $ipv6Field, $count, $hostName, $ipv4, $ipv6, $prefix = "ns") {
        $nameservers = array();
        $ipv4Table = array();
        $ipv6Table = array();
        for ($i = 0; $i < $count; $i++) {
            $nameservers[$i] = $prefix . $i . "." . $hostName;
            $ipv4Table[$i] = $ipv4;
            $ipv6Table[$i] = $ipv6;
        }
        $model->$nameserverField = $nameservers;
        $model->$nameserverCountField = $count;
        $model->$ipv4Field = $ipv4Table;
        $model->$ipv6Field = $ipv6Table;
    }

    public static function checkValues($model, $nameserverField, $count, $value, $prefix = "ns") {
        $nameservers = $model->$nameserverField;
        for ($i = 0; $i < $count; $i++) {
            self::assertEquals($prefix . $i . "." . $value, $nameservers[$i]);
        }
    }

    public static function checkValuesIp($model, $ipv4Field, $ipv6Field, $count, $valueIpv4, $valueIpv6) {
        $ipv4 = $model->$ipv4Field;
        $ipv6 = $model->$ipv6Field;
        for ($i = 0; $i < $count; $i++) {
            self::assertEquals($valueIpv4, $ipv4[$i]);
            self::assertEquals($valueIpv6, $ipv6[$i]);
        }
    }

    public static function checkErrors($model, $nameserverField, $count, $message) {
        for ($i = 0; $i < $count; $i++) {
            $fieldName = $nameserverField . "[$i]";
            $errorMessage = $model->getError($fieldName);
            self::assertTrue(isset($errorMessage));
            self::assertEquals($message, $errorMessage);
        }
    }

    public static function checkErrorsIp($model, $ipv4Field, $ipv6Field, $count, $ipv4Message, $ipv6Message) {
        for ($i = 0; $i < $count; $i++) {
            $ipv4Name = $ipv4Field . "[$i]";
            $ipv6Name = $ipv6Field . "[$i]";
            $ipv4ErrorMessage = $model->getError($ipv4Name);
            $ipv6ErrorMessage = $model->getError($ipv6Name);
            self::assertTrue(isset($ipv4ErrorMessage));
            self::assertTrue(isset($ipv6ErrorMessage));
            self::assertEquals($ipv4Message, $ipv4ErrorMessage);
            self::assertEquals($ipv6Message, $ipv6ErrorMessage);
        }
    }

}