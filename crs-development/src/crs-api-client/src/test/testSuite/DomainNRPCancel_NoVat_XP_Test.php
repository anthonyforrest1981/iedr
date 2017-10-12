<?php

include_once("IEDR-API/bus/commands/DomainNRPCancelCommand.php");

include_once("IEDR_BPR_II.php");

class DomainNRPCancelXNV498 extends IEDR_BPR_Test_II {

    /**
     * @group DomainNRCancelSuccess
     */
    public function testDomainNrpCancel() {

        $command = new DomainNRPCancelCommand();
        $command->addDomain("nv-domain1000.ie");

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

        $arr = array('nv-domain1000.ie');

        foreach ($arr as $dom) {
            $arr = DBUtils::getState($dom);
        }

        $state = $arr['State'];

        $this->assertEquals($state, '390');

    }

}
