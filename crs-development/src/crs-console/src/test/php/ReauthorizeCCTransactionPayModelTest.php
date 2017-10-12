<?php

class ReauthorizeCCTransactionPayModelTest extends PHPUnit_Framework_TestCase {

    public function testModel() {
        $model = new ReauthorizeCCTransactionPayModel();
        $model->setScenario(CreditCardFormSegmentModel::$without_amount_scenario);
        CreditCardFormSegmentModelTestUtil::_testModel($model);
    }

}