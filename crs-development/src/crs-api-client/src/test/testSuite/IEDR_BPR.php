<?php

include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("DBUtils.php");

ConfigFactory::setConfig(new ClientClassConfig());

class IEDR_BPR_Test extends PHPUnit_Framework_TestCase {

    public static function setUpBeforeClass() {

        $user = "XBC189-IEDR";

        $command = new LoginCommand();
        $command->setUsername($user);
        $command->setPassword("Passw0rd!");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        self::assertEquals($code, '1000');
        self::assertEquals($msg, 'Command completed successfully');

    }

    public static function tearDownAfterClass() {

        $command = new LogoutCommand();
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        self::assertEquals($code, '1500');
        self::assertEquals($msg, 'Command completed successfully; ending session');

    }
}
