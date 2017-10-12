<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR_II.php");

//test ref: 1.6.1.5

class DomainTransferLocked extends IEDR_BPR_Test_II {

    /**
     * @group DomainTransferFail
     */
    public function testDomainTransfer() {

        $command = new DomainTransferCommand("example0027.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XNV498-IEDR");
        $command->addNs(new DomainNs("ns.nv-domain1000.ie"));
        $command->addNs(new DomainNs("ns1.123.ie"));
        $command->setPeriodWithUnit("y", "1");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '821');
        $this->assertEquals($ReasonMsg, 'Domain illegal state for operation');

        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }

}
