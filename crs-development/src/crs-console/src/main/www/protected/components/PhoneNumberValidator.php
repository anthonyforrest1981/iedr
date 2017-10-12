<?php

/**
 * defines PhoneNumberValidator class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
/**
 * Validates A numeric phone of fax number
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 * @see References to other sections (if any)...
 */
class PhoneNumberValidator extends CValidator {
    /**
     * regex for validating phone numbers
     *
     * @var string
     * @access public
     * @static
     *
     */
    private $phoneNumberRegex = '/^[0-9() \-\+]+$/u';
    private $minLength = 4;
    private $maxLength = 25;

    /**
     * parse input string into array of lines
     */
    private function parsePhoneNumbersIntoArray($model, $attribute) {
        return mb_split("\n", $model->trimNewlines($model->$attribute));
    }

    /**
     * for a given array of lines, remove elements that are empty strings
     */
    private function parseAndTrimPhones(&$a, $t) {
        foreach ($a as $n => $p) {
            $tp = Utility::mb_trim($p);
            if (mb_strlen($tp) > 0)
                $a[$n] = $tp;
            else
                unset($a[$n]);
        }
    }

    protected function validateAttribute($model, $attribute) {
        $attribarray = $attribute . '_array';
        $model->$attribarray = $this->parsePhoneNumbersIntoArray($model, $attribute);
        $this->parseAndTrimPhones($model->$attribarray, $model->$attribute);
        Yii::log($attribute . ' ' . print_r($model->$attribarray, true), CLogger::LEVEL_INFO);
        foreach ($model->$attribarray as $n => $p) {
            $field = 'Phone';
            if ($attribute === 'faxes') {
                $field = 'Fax';
            }
            $msg = $this->validateNumber($p, $field);
            if (isset($msg)) {
                $model->addError($attribute, $msg);
                Yii::log($msg, CLogger::LEVEL_WARNING);
            }
        }
    }

    private function validateNumber($number, $field) {
        if (!(preg_match($this->phoneNumberRegex, $number) === 1)) {
            return '' . encode($number) . ' is not a valid ' . $field . ' Number (allowed charaters: "0-9 +-()")';
        }
        if (mb_strlen($number) < $this->minLength) {
            return '' . encode($number) . " is too short (minimum is " . $this->minLength . " characters)";
        }
        if (mb_strlen($number) > $this->maxLength) {
            return '' . encode($number) . " is too long (maximum is " . $this->maxLength . " characters)";
        }
        return null;
    }

}

