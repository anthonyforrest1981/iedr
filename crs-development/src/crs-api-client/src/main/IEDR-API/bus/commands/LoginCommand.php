<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("CommandObject.php");

class LoginCommand extends CommandObject {

    private $username;
    private $password;
    private $newPW = NULL;

    function LoginCommand($username = '', $password = '', $newPW = NULL) {
        parent::__construct();
        $this->commandType = self::$COMMAND_LOGIN;
        $this->username = $username;
        $this->password = $password;
        $this->newPW = $newPW;
    }

    function getUsername() {
        return $this->username;
    }

    function setUsername($username) {
        $this->username = $username;
    }

    function getPassword() {
        return $this->password;
    }

    function setPassword($password) {
        $this->password = $password;
    }

    function getNewPW() {
        return $this->newPW;
    }

    function setNewPW($newPW) {
        $this->newPW = $newPW;
    }
}
