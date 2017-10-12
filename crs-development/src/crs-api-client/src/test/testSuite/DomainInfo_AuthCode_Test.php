<?php
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainInfoAuthCode extends IEDR_BPR_Test {

    //Test: AuthCode test
    /**
     * @group InfoSuccess
     */
    public function testDomainInfoSuccess() {
        $dom = 'example0314.ie';
        $command = new DomainInfoCommand("$dom");
        $command->setAuthCode(TRUE);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $authcode = $response->getAuthCode();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $this->assertEquals($authcode, '68OM3LQV7C3F');
    }

    //Test: AuthCode force generation test
    /**
     * @group InfoSuccess
     */
    public function testDomainInfoForceSuccess() {
        $dom = 'example0286.ie';
        $command = new DomainInfoCommand("$dom");
        $command->setAuthCode(TRUE);
        $command->setAuthCodeForceGeneration(TRUE);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
