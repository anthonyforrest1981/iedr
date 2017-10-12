<?php

include_once("IEDR-API/bus/commands/DomainNRPCancelCommand.php");

include_once("IEDR_BPR_DER1.php");

class DomainNRPCancel extends IEDR_BPR_DER1_Test {

    /**
     * @group DomainNRCancelSuccess
     */
    public function testDomainNrpCancel() {

        $command = new DomainNRPCancelCommand();
        $command->addDomain("mydomain0001.ie");

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

        $arr = array('mydomain0001.ie');

        foreach ($arr as $dom) {
            $arr = DBUtils::getState($dom);
        }

        $state = $arr[State];

        $this->assertEquals($state, '17');

    }

}
