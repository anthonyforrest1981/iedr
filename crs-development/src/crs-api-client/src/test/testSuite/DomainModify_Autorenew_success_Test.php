<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

class DomainModifyARSuccess extends IEDR_BPR_Test

    //test ref: 1.17.1.2
{

    /**
     * @group DomainModifySuccess
     */
    public function testDomainModifyAR() {

        $command = new DomainModifyCommand('example0242.ie');
        $command->setRenewalMode("Autorenew");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1400');
        $this->assertEquals($msg, 'Command completed successfully; no result');
    }

    /**
     * @depends testDomainModifyAR
     * @group DomainModifySuccessDbTest
     */

    public function testDomainModifyARDbTest2() {

        $arr = DBUtils::getState("example0242.ie");

        $state = $arr['State'];

        $this->assertEquals($state, '81');

    }

}
