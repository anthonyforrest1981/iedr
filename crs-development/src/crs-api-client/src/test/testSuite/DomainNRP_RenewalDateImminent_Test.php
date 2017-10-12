<?php
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");
include_once("IEDR_BPR.php");

class DomainNRPRenewalDateImminent extends IEDR_BPR_Test {

    //test ref: 1.10.1.11

    /**
     * @group DomainNRPSuccess
     */
    public function testDomainNRPRenewalDateImminent() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0547.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainNRPRenewalDateImminent
     * @group DomainNRPSuccessDbTest
     */

    public function testDomainNRPRenewalDateImminentDbTest2() {

        $arr = DBUtils::getState("example0547.ie");

        $state = $arr['State'];

        $this->assertEquals($state, '20');

    }

}
