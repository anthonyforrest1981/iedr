<?php

include_once("IEDR-API/bus/commands/DomainNRPCancelCommand.php");

include_once("IEDR_BPR.php");

class DomainNRPCancelSuccess extends IEDR_BPR_Test {

    /**
     * @group DomainNRCancelSuccess
     */
    public function testDomainNrpCancel() {

        $command = new DomainNRPCancelCommand();
        $command->addDomain("example0315.ie");
        $command->addDomain("example0317.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainNrpCancel
     * @group DomainModifySuccessDbTest
     */

    public function testDomainNRCancelSuccessDbTest2() {

        $arr = array('example0317.ie', 'example0315.ie');

        foreach ($arr as $dom) {
            $arr = DBUtils::getState($dom);
        }

        $state = $arr['State'];

        $this->assertEquals($state, '17');

    }

}
