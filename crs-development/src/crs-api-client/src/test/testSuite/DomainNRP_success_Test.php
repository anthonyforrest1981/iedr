<?php
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");
include_once("IEDR_BPR.php");

class DomainNRPSuccess2 extends IEDR_BPR_Test {

    //test ref: 1.10.1.1

    /**
     * @group DomainNRPSuccess
     */
    public function testDomainNRPSuccess1() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0024.ie");
        $command->addDomain("example0057.ie");
        $command->addDomain("example0792.ie");
        $command->addDomain("example0847.ie");
        $command->addDomain("example0136.ie");
        $command->addDomain("example0217.ie");
        $command->addDomain("example0219.ie");

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
        $arr = array('example0024.ie', 'example0057.ie', 'example0792.ie', 'example0847.ie',
                'example0136.ie', 'example0217.ie', 'example0219.ie');
        foreach ($arr as $dom) {
            $arr = DBUtils::getState("$dom");
        }

        $state = $arr['State'];

        $this->assertEquals($state, '20');

    }
}
