<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

//test ref: 1.17.1.5
class DomainModifyLocked extends IEDR_BPR_Test {

    /**
     * @group DomainModifyFail
     */
    public function testDomainModifyLocked() {

        $command = new DomainModifyCommand('example0025.ie');
        $command->addTechCAdd("XOE550-IEDR");
        $command->addTechCRem("XBC189-IEDR");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '210');
        $this->assertEquals($ReasonMsg, 'Modify not possible in current domain state');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }

    public function testDomainModifyWithSellRequest() {

        $command = new DomainModifyCommand('example0606.ie');
        $command->addTechCAdd("XOE550-IEDR");
        $command->addTechCRem("XBC189-IEDR");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '210');
        $this->assertEquals($ReasonMsg, 'Modify not possible in current domain state');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }
}
