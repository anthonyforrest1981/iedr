<?php

class MockedAppConfig {
    public static $ONE_TWO_LETTER_ALLOWED = false;
    public static $IDN_DOMAIN_ALLOWED = false;
    public $nameserversMinCount = 2;
    public $nameserversMaxCount = 7;
    public $depositMinLimit = 1000;
    public $depositMaxLimit = 100000;
    public $authCodeFailureLimit = 3;
    public $topLevelDomains = array('ie','pl','xxx');

    public function __get($attribute_name) {
        switch ($attribute_name) {
            case 'oneOrTwoLetterDomainAllowed':
                return self::$ONE_TWO_LETTER_ALLOWED;
            case 'idnDomainAllowed':
                return self::$IDN_DOMAIN_ALLOWED;
            default:
                throw new Exception("Unknown attribute name " . $attribute_name);
        }
    }

}
