<?php

class RegexValidator extends CValidator {
    var $regex = '/^.*$/u';
    var $invalid_msg = 'invalid';

    public function validateValue($value) {
        return (preg_match($this->regex, $value) === 1) ? true : false;
    }

    protected function addErrMsgToModel($model, $attribute) {
        $lbls = $model->attributeLabels();
        $lbl = $lbls[$attribute];
        $model->addError($attribute, Yii::t('yii', $lbl . ' ' . $this->invalid_msg));
    }

    protected function validateAttribute($model, $attribute) {
        // from CValidator
        if ($model && $attribute) {
            $value = $model->$attribute;
            if (is_array($value)) {
                // BUG arrays not handled!! assume ok ...
                return;
            }
            // null values implicitly allowed
            if ($value != null && !$this->validateValue($value))
                $this->addErrMsgToModel($model, $attribute);
        }
    }

}

