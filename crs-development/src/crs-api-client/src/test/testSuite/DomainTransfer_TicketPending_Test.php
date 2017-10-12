<?php

include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

include_once("IEDR_BPR.php");

//test ref: 1.6.1.9

class DomainTransferTicketPending extends IEDR_BPR_Test {

    /**
     * @group AmendingDomainFailure
     */

    public function testDomainTransferDomNoExist() {

        $command = new DomainTransferCommand("nv-domain102.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->addNs(new DomainNs("ns.123.ie"));
        $command->addNs(new DomainNs("ns1.123.ie"));

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
