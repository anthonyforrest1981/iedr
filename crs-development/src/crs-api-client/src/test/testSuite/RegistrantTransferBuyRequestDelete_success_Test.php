<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestDeleteCommand.php");
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestInfoCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");
require_once("IEDR-API/bus/commands/CreditCard.php");

require_once("IEDR_BPR.php");

class RegistrantTransferBuyRequestDelete_success_Test extends IEDR_BPR_Test {

    public function testDeleteBuyRequestSuccess() {
        $requestId = 103;

        $deleteCommand = new RegistrantTransferBuyRequestDeleteCommand();
        $deleteCommand->setId($requestId);
        $response = CommandProcessor::process($deleteCommand);
        $result = $response->getResult();
        $this->assertEquals($result->getCode(), '1000');
        $this->assertEquals($result->getMsg(), 'Command completed successfully');

        $infoCommand = new RegistrantTransferBuyRequestInfoCommand();
        $infoCommand->setId($requestId);
        $response = CommandProcessor::process($infoCommand);
        $result = $response->getResult();
        $this->assertEquals($result->getCode(), '1000');
        $this->assertEquals($result->getMsg(), 'Command completed successfully');
        $this->assertEquals($response->getStatus(), "Cancelled");
    }

}
