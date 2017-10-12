<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR_BPR.php");

//test ref: 1.1.1.16
class DomainReg8 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */

    public function testDomainCreateInvalidNS() {
        $command = new DomainCreateCommand("nodomain.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.yy"));
        $command->addNs(new DomainNs("ns1.yy.ie"));
        $command->addNs(new DomainNs("ns1.yy.com"));
        $command->addNs(new DomainNs("ns1.yy.eu"));
        $command->addNs(new DomainNs("ns2.yy"));
        $command->addNs(new DomainNs("ns2.yy.ie"));
        $command->addNs(new DomainNs("ns2.yy.com"));
        $command->addNs(new DomainNs("ns2.yy.eu"));
        $holder = new DomainHolder();
        $holder->setName("mary gun");
        $holder->setType("Company");
        $holder->setRemark("Bla bla bla");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '160');
        $this->assertEquals($ReasonMsg, 'Too many nameservers');
        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');

    }

}
