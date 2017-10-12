<?php

/**
 * Validates if all the fields in an array have unique values (if not empty)
 */
class UniquenessValidator extends CValidator {
    var $fields = array();
    var $label = 'field';

    public function validateAttribute($model, $attribute) {
        for ($i = 0; $i < count($this->fields); $i++) {
            $fieldname = $this->fields[$i];
            for ($j = $i + 1; $j < count($this->fields); $j++) {
                $compared_fieldname = $this->fields[$j];
                if (isset($model->$fieldname) && isset($model->$compared_fieldname) && !empty($model->$fieldname) && !empty($model->$compared_fieldname)) {
                    if ($model->$fieldname == $model->$compared_fieldname) {
                        $model->addError($fieldname, 'Duplicated ' . $this->label);
                        $model->addError($compared_fieldname, 'Duplicated ' . $this->label);
                    }
                }
            }
        }
    }

}
?>