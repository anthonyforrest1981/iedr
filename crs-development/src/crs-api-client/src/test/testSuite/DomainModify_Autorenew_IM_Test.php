<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

class DomainModifyAutoRenewIM extends IEDR_BPR_Test

    //test ref: 1.17.1.2
{

    /**
     * @group DomainModifySuccess
     */
    public function testDomainModifyAutoRenewIM() {

        $command = new DomainModifyCommand('example0118.ie');
        $command->setRenewalMode("Autorenew");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        /*$this->assertEquals($ReasonCode, '210');
                      $this->assertEquals($ReasonMsg, 'Modify not possible in current domain state');*/
        $this->assertEquals($code, '1400'/*'2304'*/);
        $this->assertEquals($msg,
            'Command completed successfully; no result'/*'Object status prohibits operation'*/);

    }

    /**
     * @depends testDomainModifyAutoRenewIM
     * @group DomainModifySuccessDbTest
     */

    public function testDomainModifyAutoRenewIMDbTest2() {

        $arr = DBUtils::getState("example0118.ie");

        $state = $arr['State'];

        $this->assertEquals($state, /*'18'*/
            '82');

    }

}
