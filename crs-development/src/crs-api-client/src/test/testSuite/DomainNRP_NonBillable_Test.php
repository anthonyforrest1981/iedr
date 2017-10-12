<?php
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");
include_once("IEDR_BPR.php");

class DomainNRPNonBillable extends IEDR_BPR_Test {

    //test ref: 1.10.1.12

    /**
     * @group DomainNRPSuccess
     */
    public function testDomainNRPNonBillable() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0305.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainNRPNonBillable
     * @group DomainNRPSuccessDbTest
     */

    public function testDomainNRPNonBillableDbTest2() {

        $arr = DBUtils::getState("example0305.ie");

        $state = $arr['State'];

        $this->assertEquals($state, '308');

    }

}
