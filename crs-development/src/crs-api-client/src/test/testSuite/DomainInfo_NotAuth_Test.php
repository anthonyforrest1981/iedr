<?php
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainInfoNotAuth extends IEDR_BPR_Test {

    //test ref: 1.66.1.2

    //Test: DomainInfo domain doesnt exist
    /**
     * @group InfoFailure
     */
    public function testDomainInfoNotAuth() {
        $dom = 'nv-domain0100.ie';
        $command = new DomainInfoCommand("$dom");
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
