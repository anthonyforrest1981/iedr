<?php

class ValidatorTestUtil {

    public static function testDomain($domainName, $validatorName, $validatorParams = array()) {
        $validator = CValidator::createValidator($validatorName, null, "attrib1", $validatorParams);
        $model = new MockedFormModel();
        $model->attrib1 = $domainName;
        $validator->validate($model, 'attrib1');
        return $model->getError('attrib1');
    }

}
