<?php
define('SUPPORTED_INTERNATIONAL_LETTERS_DEF', 'áéíóú');
define('REGEX_CHAR_GROUP_DEF', '[a-z0-9' . SUPPORTED_INTERNATIONAL_LETTERS_DEF . ']');
define('IE_DOMAIN_PATTERN_DEF', '/^(' . REGEX_CHAR_GROUP_DEF . '+[-]+)*' . REGEX_CHAR_GROUP_DEF . '+\.ie$/u');
define('TWO_LETTER_IE_DOMAIN_DEF', '/^[a-z' . SUPPORTED_INTERNATIONAL_LETTERS_DEF . ']{2}\.ie$/u');
define('ONE_CHAR_IE_DOMAIN_DEF', '/^' . REGEX_CHAR_GROUP_DEF . '\.ie$/u');

/**
 * Yii validator for IE domains.
 * Can be used with attributes that are a single domain (a string)
 * or an array of domains (array of strings).
 */
class IEDomainValidator extends CValidator {
    const RESULT_OK = 1;
    const RESULT_NOT_A_DOMAIN = 2;
    const RESULT_NOT_A_IEDR_DOMAIN = 3;
    const RESULT_TWO_LETTER_DOMAIN = 4;
    const RESULT_ONE_CHAR_DOMAIN = 5;
    const RESULT_LOOKS_LIKE_PUNYCODE = 6;
    const RESULT_HAS_UPPERCASE_LETTERS = 7;
    const IE_DOMAIN_PATTERN = IE_DOMAIN_PATTERN_DEF;
    const TWO_LETTER_IE_DOMAIN = TWO_LETTER_IE_DOMAIN_DEF;
    const ONE_CHAR_IE_DOMAIN = ONE_CHAR_IE_DOMAIN_DEF;
    /**
     * For a new domain always enforce one- and two-letter settings
     * For already registered domain (e.g.
     * editing a domain) this check
     * cannot run or else such a domain cannot be edited.
     */
    private $checkAsANewDomain = false;

    public function setCheckAsANewDomain($check) {
        $this->checkAsANewDomain = $check;
    }

    protected function validateAttribute($model, $attribute) {
        $domains = $model->$attribute;
        if (!is_array($domains)) {
            $domains = array($domains);
        }
        $errors = array();
        foreach ($domains as $domain) {
            $safeDomain = CHtml::encode($domain);
            switch ($this->validateDomain($domain, $this->checkAsANewDomain)) {
                case self::RESULT_NOT_A_DOMAIN:
                    $errors[] = "$safeDomain is not a valid domain address";
                    break;
                case self::RESULT_NOT_A_IEDR_DOMAIN:
                case self::RESULT_LOOKS_LIKE_PUNYCODE:
                    $errors[] = "$safeDomain is not a valid IEDR domain address";
                    break;
                case self::RESULT_TWO_LETTER_DOMAIN:
                    $errors[] = "Two-letter domain addresses like $safeDomain are not allowed";
                    break;
                case self::RESULT_ONE_CHAR_DOMAIN:
                    $errors[] = "One character domain addresses like $safeDomain are not allowed";
                    break;
                case self::RESULT_HAS_UPPERCASE_LETTERS:
                    $errors[] = "Uppercase letters are not allowed in $safeDomain";
                    break;
            }
        }
        if (!empty($errors)) {
            $model->addError($attribute, implode("<br/>", $errors));
        }
    }

    /**
     * Helper method to validate a single domain.
     * Should not be called explicitly.
     *
     * This method is public only because PHP 5.3 does not allow to use self inside closures
     * (see method isValid), so any call from a closure can use only public methods / properties.
     */
    public static function validateDomain($domain, $checkAsANewDomain) {
        if (!HostNameValidator::isValid($domain))
            return self::RESULT_NOT_A_DOMAIN;
        if (mb_strtolower($domain) !== $domain)
            return self::RESULT_HAS_UPPERCASE_LETTERS;
        if (preg_match(self::IE_DOMAIN_PATTERN, $domain) !== 1)
            return self::RESULT_NOT_A_IEDR_DOMAIN;
        $isIdn = self::isIdn($domain);
        if ($isIdn && $checkAsANewDomain && !Utility::getApplicationConfiguration()->idnDomainAllowed)
            return self::RESULT_NOT_A_IEDR_DOMAIN;
        if ($checkAsANewDomain && !Utility::getApplicationConfiguration()->oneOrTwoLetterDomainAllowed) {
            if (preg_match(self::TWO_LETTER_IE_DOMAIN, $domain) === 1)
                return self::RESULT_TWO_LETTER_DOMAIN;
            if (preg_match(self::ONE_CHAR_IE_DOMAIN, $domain) === 1)
                return self::RESULT_ONE_CHAR_DOMAIN;
        }
        if (mb_substr($domain, 2, 2) === '--')
            return self::RESULT_LOOKS_LIKE_PUNYCODE;
        return self::RESULT_OK;
    }

    /**
     * Checks if all provided domains are valid IE domains.
     *
     * @param $domains a
     *            single domain or an array of domains
     * @param $checkAsANewDomain if
     *            one- and two-letter domain checks should be done
     * @return TRUE iff all provided domains are legal, FALSE otherwise
     */
    public static function isValid($domains, $checkAsANewDomain = false) {
        if (!is_array($domains))
            $domains = array($domains);
        return array_reduce($domains, function ($acc, $domain) use($checkAsANewDomain) {
            return $acc && IEDomainValidator::validateDomain($domain, $checkAsANewDomain) === IEDomainValidator::RESULT_OK;
        }, true);
    }

    private static function isIdn($domain) {
        return $domain != idn_to_ascii($domain);
    }

}
