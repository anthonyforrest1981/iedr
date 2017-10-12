<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR_BPR.php");

//test ref: 1.1.1.20
class DomainRegInvalidNS extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */

    public function testDomainCreateInvalidNS() {
        $command = new DomainCreateCommand("nodomain.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdx"));
        $command->addNs(new DomainNs("ns2.yy"));
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

        $this->assertEquals($ReasonCode, '162');
        $this->assertEquals($ReasonMsg, 'Nameserver name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

    public function testDomainCreateEmptyNS() {
        $command = new DomainCreateCommand("nodomain.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.dns.ie"));
        $command->addNs(new DomainNs(""));
        $holder = new DomainHolder();
        $holder->setName("Holder");
        $holder->setType("Company");
        $holder->setRemark("Remark");
        $command->setHolder($holder);

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
