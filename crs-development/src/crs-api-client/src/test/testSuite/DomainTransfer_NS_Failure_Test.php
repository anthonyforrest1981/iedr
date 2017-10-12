<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

//test ref: 1.6.1.13
class DomainTransferNsFailure extends IEDR_BPR_Test {

    public function testGlueRequired() {
        $command = new DomainTransferCommand("nv-domain0998.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->addNs(new DomainNs("ns.nv-domain0998.ie"));
        $command->addNs(new DomainNs("ns1.123.ie"));
        $command->setPeriodWithUnit("y", "5");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '175');
        $this->assertEquals($ReasonMsg, 'Glue record is required');
        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testEmptyNs() {
        $command = new DomainTransferCommand("nv-domain0998.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.dns.ie"));
        $command->addNs(new DomainNs(""));
        $command->setPeriodWithUnit("y", "5");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '162');
        $this->assertEquals($ReasonMsg, 'Nameserver name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

}
