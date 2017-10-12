<?php

/**
 * defines CheckboxOnValidator class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */
/**
 * extends RegexValidator to check if checkbox-values are clicked
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class CheckboxOnValidator extends RegexValidator {

    /**
     * constructor, sets up regex and error message
     *
     * @return void
     * @access public
     */
    public function __construct() {
        $this->regex = '/^[1ony]+$/iu';
        $this->invalid_msg = 'must be on';
    }

    protected function addErrMsgToModel($model, $attribute) {
        $lbls = $model->attributeLabels();
        $lbl = $lbls[$attribute];
        if (isset($this->message)) {
            $model->addError($attribute, Yii::t('yii', $this->message));
        } else {
            $model->addError($attribute, Yii::t('yii', $lbl . ' ' . $this->invalid_msg));
        }
    }

}

