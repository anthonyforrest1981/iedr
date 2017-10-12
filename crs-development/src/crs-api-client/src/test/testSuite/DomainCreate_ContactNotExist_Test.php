<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

//test ref: 1.1.1.10
class DomainCreate2 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateContactNotExist() {

        $command = new DomainCreateCommand("nodomain.ie");
        $command->addAdmin("NONE1-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1new.ie"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Company");
        $holder->setRemark("Bla bla bla");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '170');
        $this->assertEquals($ReasonMsg, 'Contact ID does not exist');

        $this->assertEquals($code, '2003');
        $this->assertEquals($msg, 'Parameter value range error');
    }

}
