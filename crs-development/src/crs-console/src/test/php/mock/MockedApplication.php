<?php

class MockedApplication extends CApplication {
    public $controller;
    public $session;
    public $user;

    public function __construct() {
        $this->user = new MockedWebUser();
        $this->session = new CHttpSession();
    }

    public function processRequest() {}

}