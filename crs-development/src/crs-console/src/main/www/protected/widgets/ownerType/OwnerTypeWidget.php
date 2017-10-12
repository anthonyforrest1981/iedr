<?php

class OwnerTypeWidget extends CWidget {

    public $form;
    public $model;
    public $paymentForRegistration;

    public function init() {}

    public function run() {
        $this->render('application.widgets.ownerType.ownerTypeForm');
    }

}
