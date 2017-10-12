<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

//test ref: 1.1.1.14

class DomainRegCharityAttemptMYRException extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateDomAlreadyExist() {
        $command = new DomainCreateCommand("no-domain123.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1-new.ie"));
        $command->setUnitPeriod("y", "10");
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Charity");
        $holder->setRemark("Bla bla bla");
        $command->setHolder($holder);
        $command->setCharity("CHY12345");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '158');
        $this->assertEquals($ReasonMsg, 'Period value is not admissible');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

}
