<?php

class RecaptchaWidget extends CWidget {

    public $form;
    public $model;
    public $recaptchaResponseField;

    public function init() {}

    public function run() {
        $this->render('application.widgets.recaptcha.view');
    }

}
