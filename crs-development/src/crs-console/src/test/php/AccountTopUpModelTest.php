<?php

class AccountTopUpModelTest extends PHPUnit_Framework_TestCase {

    public function testModel() {
        $model = new AccountTopUpModel();
        $model->setScenario('topup');
        CreditCardFormSegmentModelTestUtil::_testModel($model);
    }

    public function testAmountBelowLimit() {
        $model = new AccountTopUpModel();
        $model->setScenario('topup');
        $model->euros_amount = 999;
        $model->validate();
        $errorMessage = $model->getError('euros_amount');
        self::assertTrue(isset($errorMessage));
        self::assertEquals('Minimum Transaction is ' . Utility::currencyAmount(1000), $errorMessage);
    }

    public function testAmountOverLimit() {
        $model = new AccountTopUpModel();
        $model->setScenario('topup');
        $model->euros_amount = 100001;
        $model->validate();
        $errorMessage = $model->getError('euros_amount');
        self::assertTrue(isset($errorMessage));
        self::assertEquals('Maximum Transaction is ' . Utility::currencyAmount(100000), $errorMessage);
    }

}