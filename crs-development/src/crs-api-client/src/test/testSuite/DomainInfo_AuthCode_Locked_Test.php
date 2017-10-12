<?php
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainInfoAuthCodeLocked extends IEDR_BPR_Test {

    //Test: AuthCode force generation test
    /**
     * @group InfoSuccess
     */
    public function testAuthCodeForLockedDomain() {
        $dom = 'example0006.ie';
        $command = new DomainInfoCommand("$dom");
        $command->setAuthCode(TRUE);
        $command->setAuthCodeForceGeneration(TRUE);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '283');
        $this->assertEquals($ReasonMsg, 'Cannot generate authcode for the domain');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');
    }

    public function testAuthCodeForDomainWithSellRequest() {
        $dom = 'example0606.ie';
        $command = new DomainInfoCommand("$dom");
        $command->setAuthCode(TRUE);
        $command->setAuthCodeForceGeneration(TRUE);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '283');
        $this->assertEquals($ReasonMsg, 'Cannot generate authcode for the domain');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');
    }

}
