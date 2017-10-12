<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestDeleteCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");
require_once("IEDR-API/bus/commands/CreditCard.php");

require_once("IEDR_BPR.php");

class RegistrantTransferBuyRequestDelete_failure_Test extends IEDR_BPR_Test {

    public function testDeleteNonExistentBuyRequest() {
        $deleteCommand = new RegistrantTransferBuyRequestDeleteCommand();
        $deleteCommand->setId(9999);
        $response = CommandProcessor::process($deleteCommand);
        $result = $response->getResult();
        $this->assertEquals($result->getCode(), '2303');
        $this->assertEquals($result->getMsg(), 'Object does not exist');
        $this->assertEquals($result->getReasonCode(), '502');
        $this->assertEquals($result->getReasonMsg(), 'BuyRequest does not exist');
    }

    public function testDeletePassedBuyRequest() {
        $deleteCommand = new RegistrantTransferBuyRequestDeleteCommand();
        $deleteCommand->setId(101);
        $response = CommandProcessor::process($deleteCommand);
        $result = $response->getResult();
        $this->assertEquals($result->getCode(), '2304');
        $this->assertEquals($result->getMsg(), 'Object status prohibits operation');
        $this->assertEquals($result->getReasonCode(), '504');
        $this->assertEquals($result->getReasonMsg(), 'BuyRequest has already been accepted and cannot be modified');
    }

}
