<?php

/**
 * Email is assumed valid if there is one at-sign,
 * and the trimmed email has a non-zero-length string left of the at-sign,
 * and right of the at-sign is a valid hostname
 */
class EmailAddrValidator extends CValidator {
    const LOG_CATEGORY = "application.validation.email";
    // regex used to validate the part before the '@' in email address
    const EMAIL_USER_REGEX = '/^[a-z0-9!#$%&\'*+\/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&\'*+\/=?^_`{|}~-]+)*$/iu';

    protected function validateAttribute($model, $attribute) {
        $email = $model->$attribute;
        $lbls = $model->attributeLabels();
        $lbl = $lbls[$attribute];
        if (mb_strlen($email) > 254) {
            $model->addError($attribute, $lbl . " is too long (maximum is 254 characters)");
            return;
        }
        if (mb_substr($email, 0, 1) === '@') {
            $model->addError($attribute, $lbl . " must not start with \"@\"");
            return;
        }
        $parts = mb_split('@', Utility::mb_trim($email));
        // only one @ in string ?
        if (count($parts) != 2) {
            $model->addError($attribute, $lbl . " must have exactly one \"@\"");
            return;
        }
        $userSegmentLength = mb_strlen($parts[0]);
        if ($userSegmentLength > 64) {
            $model->addError($attribute, $lbl . " has too long string before \"@\" (64 characters allowed)");
            return;
        }
        // check part left of @ is ascii-only
        if (preg_match(self::EMAIL_USER_REGEX, $parts[0]) !== 1) {
            $model->addError($attribute, $lbl . " has illegal characters before \"@\"");
            return;
        }
        try {
            $asciiDomain = HostNameValidator::getAsciiCanonical($parts[1]);
            // limit for length of a punycode email form is 254, user segment is not affected by punycode transforamtion
            // so basic user segment + transformed domain segment must not be longer that 253 (one character for @)
            if ($userSegmentLength + mb_strlen($asciiDomain) > 253) {
                $model->addError($attribute, $lbl . "'s punycode form is too long (maximum is 254 characters)");
                return;
            }
            if (!HostNameValidator::isValidAsciiCanonical($asciiDomain)) {
                $model->addError($attribute, $lbl . " has illegal domain segment (after \"@\")");
                return;
            }
        } catch (Exception $e) {
            $model->addError($attribute, $lbl . " has illegal domain segment (after \"@\")");
            return;
        }
    }

}
