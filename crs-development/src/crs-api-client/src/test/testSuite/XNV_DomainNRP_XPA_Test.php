<?php
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");
include_once("IEDR_BPR.php");

class DomainNRPinXPA extends IEDR_BPR_Test {

    //test ref: 1.10.1.2

    /**
     * @depends testDomainTransfer
     * @group DomainNRPFail
     */
    public function testDomainXPA() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0120.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '208');
        $this->assertEquals($ReasonMsg, 'Voluntary NRP not possible in current domain state.');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }

}
