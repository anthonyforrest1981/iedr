<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

//test ref: 1.17.1.4
class DomainModifyPA extends IEDR_BPR_Test {

    /**
     * @group DomainModifySuccess
     */
    public function testDomainModifyPA_Set_to_AutoRenew() {

    $command = new DomainModifyCommand('applesandbananas3.ie');
    $command->setRenewalMode("Autorenew");

    $response = CommandProcessor::process($command);
    $result = $response->getResult();
    $code = $result->getCode();
    $msg = $result->getMsg();
    $ReasonCode = $result->getReasonCode();
    $ReasonMsg = $result->getReasonMsg();

    $this->assertEquals($ReasonCode, '200');
    $this->assertEquals($ReasonMsg, 'Domain name does not exist');
    $this->assertEquals($code, /*'1400'*/
        '2303');
    $this->assertEquals($msg, /*'Command completed successfully; no result'*/
        'Object does not exist');
}

    /**
     * @depends testDomainModifyPA_Set_to_AutoRenew
     * @group DomainModifySuccessDbTest
     */

    /*public function testDomainModifyPA_Set_to_AutoRenewDbTest()
    {
    $arr = DBUtils::getState("applesandbananas3.ie");
    $state=$arr['State'];
    $this->assertEquals($state, '87');
    }*/

}
