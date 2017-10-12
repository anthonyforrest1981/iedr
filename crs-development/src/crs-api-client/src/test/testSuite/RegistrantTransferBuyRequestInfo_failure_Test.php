<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestInfoCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");

require_once("IEDR_BPR.php");

class RegistrantTransferBuyRequestInfo_failure_Test extends IEDR_BPR_Test {

    public function testGetNonExistentRequestDetails() {
        $infoCommand = new RegistrantTransferBuyRequestInfoCommand();
        $infoCommand->setId(9999);
        $response = CommandProcessor::process($infoCommand);
        $result = $response->getResult();

        $this->assertEquals($result->getCode(), '2303');
        $this->assertEquals($result->getMsg(), 'Object does not exist');
        $this->assertEquals($result->getReasonCode(), '502');
        $this->assertEquals($result->getReasonMsg(), 'BuyRequest does not exist');
    }

}
