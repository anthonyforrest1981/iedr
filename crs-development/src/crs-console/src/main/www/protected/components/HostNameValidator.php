<?php

/**
 * Validator checking if a given string is a valid domain name.
 */
class HostNameValidator extends CValidator {
    const LOG_CATEGORY = "application.validation.hostname";
    const DOMAIN_REGEX = '/^(([a-z0-9]|[a-z0-9][a-z0-9-]{0,61}[a-z0-9])\.)+([a-z0-9]|[a-z0-9][a-z0-9-]{0,61}[a-z0-9])$/iu';

    protected function validateAttribute($model, $attribute) {
        $domains = $model->$attribute;
        if (!is_array($domains)) {
            $domains = array($domains);
        }
        $errors = array();
        foreach ($domains as $domain) {
            if (!self::isValid($domain)) {
                $safeDomain = CHtml::encode($domain);
                $errors[] = "$safeDomain is not a valid domain address";
            }
        }
        if (!empty($errors)) {
            $model->addError($attribute, implode("<br/>", $errors));
        }
    }

    public static function isValid($domain) {
        try {
            $asciiDomain = self::getAsciiCanonical($domain);
            return self::isValidAsciiCanonical($asciiDomain);
        } catch (Exception $e) {
            return false;
        }
    }

    public static function getAsciiCanonical($domain) {
        if (mb_substr($domain, mb_strlen($domain) - 1) === '.') {
            $domain = mb_substr($domain, 0, mb_strlen($domain) - 1);
        }
        $asciiDomain = idn_to_ascii($domain);
        // idn_to_ascii returns FALSE not only in case of bad UTF-8, but also if produced string would be too long
        if ($asciiDomain === false) {
            Yii::log("Domain $domain failed to convert to punycode, bad characters or length", CLogger::LEVEL_INFO, self::LOG_CATEGORY);
            throw new Exception();
        }
        return $asciiDomain;
    }

    public static function isValidAsciiCanonical($asciiDomain) {
        // idn_to_ascii fails for random 257 limit, not 253 from the spec.
        if (mb_strlen($asciiDomain) > 253) {
            Yii::log("Domain $domain is longer than 253 characters", CLogger::LEVEL_INFO, self::LOG_CATEGORY);
            return false;
        }
        // idn_to_ascii correctly checks segments' lenghts so we don't need to check that, only general number of segments
        $segments = mb_split('\.', $asciiDomain);
        if (count($segments) < 2) {
            Yii::log("Domain $domain has too few segments", CLogger::LEVEL_INFO, self::LOG_CATEGORY);
            return false;
        }
        $tld = mb_strtolower(end($segments));
        if (!in_array($tld, Utility::getApplicationConfiguration()->topLevelDomains)) {
            Yii::log("Unknown TLD \"$tld\" of domain $domain", CLogger::LEVEL_INFO, self::LOG_CATEGORY);
            return false;
        }
        if (preg_match(self::DOMAIN_REGEX, $asciiDomain) !== 1) {
            Yii::log("Domain $domain failed to match to domain regex", CLogger::LEVEL_INFO, self::LOG_CATEGORY);
            return false;
        }
        return true;
    }

}
