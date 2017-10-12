<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR_II.php");

//test ref: 1.6.1.5

class DomainTransferAuthcode extends IEDR_BPR_Test_II {

    /**
     * @group DomainTransferFail
     */
    public function testDomainTransferNoAuthCode() {

        $command = new DomainTransferCommand("example0015.ie");
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

        $this->assertEquals($ReasonCode, '281');
        $this->assertEquals($ReasonMsg, 'Authcode is mandatory for transfers');

        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');

    }

    /**
     * @group DomainTransferFail
     */
    public function testDomainTransferWrongAuthCode() {

        $command = new DomainTransferCommand("example0015.ie");
        $command->setAuthcode("ABC123456789");
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

        $this->assertEquals($ReasonCode, '280');
        $this->assertEquals($ReasonMsg, 'Invalid authcode');

        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');

    }

}
