<?php
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");
include_once("IEDR_BPR_DER1.php");

class DomainNRPSuccess1 extends IEDR_BPR_DER1_Test {

    /**
     * @group DomainNRPSuccess
     */
    public function testDomainNRPSuccess1() {

        $command = new DomainNRPCommand();
        $command->addDomain("mydomain0001.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainNRPSuccess1
     * @group DomainNRPSuccessDbTest
     */

    public function testDomainNRPCharityDbTest2() {
        $dom = 'mydomain0001.ie';
        $arr = DBUtils::getState($dom);

        $state = $arr[State];

        $this->assertEquals($state, '20');
    }
}
