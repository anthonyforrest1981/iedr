<?php
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainInfoNotReg extends IEDR_BPR_Test {

    //test ref: 1.66.1.3

    //Test: DomainInfo domain doesnt exist
    /**
     * @group InfoFailure
     */
    public function testDomainInfoDomainNotExist() {
        $dom = 'domain-name-is-not-registered.ie';
        $command = new DomainInfoCommand("$dom");
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '200');
        $this->assertEquals($ReasonMsg, 'Domain name does not exist');
        $this->assertEquals($code, '2303');
        $this->assertEquals($msg, 'Object does not exist');
    }

}
