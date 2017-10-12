<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

//test ref: 1.1.1.12

class DomainRegCharityException extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateDomAlreadyExist() {
        $command = new DomainCreateCommand("no-domain.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1-new.ie"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Charity");
        $holder->setRemark("Bla bla bla");
        $command->setHolder($holder);
        $command->setCharity("Our chy number is : CHY12345");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '275');
        $this->assertEquals($ReasonMsg, 'Charity code too long');
        $this->assertEquals($code, '2003');
        $this->assertEquals($msg, 'Parameter value range error');
    }

}
