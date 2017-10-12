<?php

class PasswordStrengthValidator extends CValidator {

    function validateAttribute($object, $attribute) {
        $passwd = $object->$attribute;
        $obligatory = array('/[a-z]+/u','/[A-Z]+/u','/[0-9]+/u','/[^a-zA-Z0-9]+/u');
        $forbidden = '/[^a-zA-Z0-9:_\-.#@|!$%&*+\/]+/u';
        $errorMessage = "Password must contain an uppercase letter, a lowercase letter, a digit and a non-alphanumeric [:_-.#@|!$%&*+/]. Other characters are not allowed.";
        foreach ($obligatory as $r) {
            if (0 == preg_match($r, $passwd)) {
                $object->addError($attribute, $errorMessage);
                return;
            }
        }
        if (1 == preg_match($forbidden, $passwd)) {
            $object->addError($attribute, $errorMessage);
        }
    }

}
?>