<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

class DomainTransferFailNoDomain extends IEDR_BPR_Test {

    /**
     * @group DomainTransferFail
     */
    public function testDomainTransfer() {

        $command = new DomainTransferCommand("no-domain.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->addNs(new DomainNs("ns.nv-example1000.ie", "1.1.1.1"));
        $command->addNs(new DomainNs("ns1.123.ie"));
        $command->setPeriodWithUnit("y", "5");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '200');
        $this->assertEquals($ReasonMsg, 'Domain name does not exist');
        $this->assertEquals($code, '2303');
        $this->assertEquals($msg, 'Object does not exist');

    }

}
