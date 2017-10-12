<?php

include_once("IEDR-API/bus/commands/DomainNRPCancelCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.11.1.1

class DomainNRPIMCancel extends IEDR_BPR_Test {

    /**
     * @group DomainNRCancelSuccess
     */
    public function testDomainNrpCancel() {

        $command = new DomainNRPCancelCommand();
        $command->addDomain("example0987.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '209');
        $this->assertEquals($ReasonMsg, 'Leaving Voluntary NRP not possible in current domain state.');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }

    /**
     * @depends testDomainNrpCancel
     * @group DomainModifySuccessDbTest
     */

    public function testDomainNRCancelSuccessDbTest2() {

        $arr = array('example0987.ie');

        foreach ($arr as $dom) {
            $arr = DBUtils::getState($dom);
        }

        $state = $arr['State'];

        $this->assertEquals($state, '18');

    }

}
