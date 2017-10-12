<?php

class ProgressBarWidget extends CWidget {

    public $steps;
    public $currentStep;
    public $htmlOptions;

    public function init() {}

    public function run() {
        $this->render('application.widgets.progressBar.view');
    }

}
