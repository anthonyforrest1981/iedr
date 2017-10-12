<?php

include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("DBUtils.php");

ConfigFactory::setConfig(new ClientClassConfig());

class MyTest extends PHPUnit_Framework_TestCase {

    //Test: Attempt Login with wrong password and ensure that appropriate error is thrown.
    /**
     * @group Login
     */
    public function testLogInWrongPass() {
        $ntg = "XBC189-IEDR";

        $command = new LoginCommand();
        $command->setUsername($ntg);
        $command->setPassword("passworddkfjhsdkfsdkjhfskdjhf");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2100');
        $this->assertEquals($msg, 'Authentication error');
    }

    //Test: Attempt to change the login password using a password that is deemed 'too easy'
    /**
     * @group Login
     */
    public function testLoginChangePasswordFail1() {

        $ntg = "XBC189-IEDR";

        $command = new LoginCommand();
        $command->setUsername($ntg);
        $command->setPassword("Passw0rd!");
        $command->setNewPW("Ninet33n82");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '51');
        $this->assertEquals($ReasonMsg, 'New password too easy');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    //Test: Attempt to change the login password, when the original password is incorrect
    /**
     * @group Login
     */
    public function testLoginChangePasswordFail() {

        $ntg = "XBC189-IEDR";

        $command = new LoginCommand();
        $command->setUsername($ntg);
        $command->setPassword("Passw0r");
        $command->setNewPW("Ninet33n82!");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2100');
        $this->assertEquals($msg, 'Authentication error');
    }

    //Test: Successfully change Password
    /**
     * @group Login
     */
    public function testLoginChangePasswordSuccess() {
        $ntg = "XBC189-IEDR";

        $command = new LoginCommand();
        $command->setUsername($ntg);
        $command->setPassword("Passw0rd!");
        $command->setNewPW("Ninet33n82!");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }

    //Test: LogOut XBC189-IEDR in order to ensure that new password has been applied correctly
    /**
     * @group LogOut
     */
    public function testLogOutSuccess() {

        $command = new LogoutCommand();

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();
        $this->assertEquals($code, '1500');
        $this->assertEquals($msg, 'Command completed successfully; ending session');
    }

}
