<?php
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");
include_once("IEDR_BPR.php");

//test ref: 1.10.1.8

class DomainNRPSuccess1 extends IEDR_BPR_Test {

    /**
     * @group DomainNRPSuccess
     */
    public function testDomainNRPSuccess1() {

        $command = new DomainNRPCommand();
        $command->addDomain("iedr.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '201');
        $this->assertEquals($ReasonMsg, 'Domain is managed by another registrar');
        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');

    }

}
