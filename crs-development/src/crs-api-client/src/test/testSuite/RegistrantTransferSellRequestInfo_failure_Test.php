<?php
require_once("IEDR-API/bus/commands/RegistrantTransferSellRequestInfoCommand.php");
require_once("IEDR_BPR.php");

class SellRequestInfoFailure extends IEDR_BPR_Test {

    public function testSellRequestInfo() {

        $command = new RegistrantTransferSellRequestInfoCommand(-1);
        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '500');
        $this->assertEquals($ReasonMsg, 'Sell request does not exist');
        $this->assertEquals($code, '2303');
        $this->assertEquals($msg, 'Object does not exist');

    }

}