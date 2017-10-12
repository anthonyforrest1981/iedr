<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

//test ref: 1.1.1.4

class DomainReg15 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateDomAlreadyExist() {
        $command = new DomainCreateCommand("new-example-000027.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1-new.ie"));
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

        $this->assertEquals($ReasonCode, '156');
        $this->assertEquals($ReasonMsg, 'Domain name exists or pending registration');
        $this->assertEquals($code, '2302');
        $this->assertEquals($msg, 'Object exists');
    }

}
