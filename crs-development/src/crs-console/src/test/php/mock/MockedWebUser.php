<?php

class MockedWebUser {
    public $authenticatedUser;
    public $name;
    public $nicHandle;
    public $ticketEdit;

    public function __construct() {
        $this->nicHandle = new MockedNicHandle();
        $this->authenticatedUser = new MockedAuthenticatedUser();
    }

}