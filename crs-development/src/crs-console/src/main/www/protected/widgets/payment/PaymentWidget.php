<?php

class PaymentWidget extends CWidget {

    public $form;
    public $model;
    public $paymentTypes;

    public function init() {}

    public function run() {
        $this->render('application.widgets.payment.paymentForm');
    }

}
