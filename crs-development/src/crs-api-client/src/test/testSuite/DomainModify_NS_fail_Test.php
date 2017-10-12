<?php
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainInfo extends IEDR_BPR_Test {

    //Test: DomainInfo domain doesnt exist
    /**
     * @group InfoFailure
     */
    public function testDomainInfoDomainNotExist() {
        $dom = 'no-domain.ie';
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


    //Test: DomainInfo: Domain on other account
    /**
     * @group InfoFailure
     */
    public function testDomainInfoDomainOtherAcc() {

        $dom = 'nv-domain0001.ie';

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

    //Test: DomainInfo successfull valid domain example0231.ie
    /**
     * @group InfoSuccess
     */
    public function testDomainInfo() {
        $dom = 'example0231.ie';

        $command = new DomainInfoCommand("$dom");
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

}
