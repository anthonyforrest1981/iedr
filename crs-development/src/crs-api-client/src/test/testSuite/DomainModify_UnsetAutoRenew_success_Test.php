<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

//test ref: 1.17.1.3
class DomainModifyUnsetRMSuccess extends IEDR_BPR_Test {

    /**
     * @group DomainModifySuccess
     */
    public function testDomainModifyUnsetRM() {

        $command = new DomainModifyCommand('example0031.ie');
        $command->setRenewalMode("NoAutorenew");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1400');
        $this->assertEquals($msg, 'Command completed successfully; no result');
    }

    /**
     * @depends testDomainModifyUnsetRM
     * @group DomainModifySuccessDbTest
     */

    public function testDomainModifyUnsetRMDbTest2() {

        $arr = DBUtils::getState("example0031.ie");

        $state = $arr['State'];

        $this->assertEquals($state, '17');

    }

}
