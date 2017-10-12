<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

//test ref: 1.1.1.9
class DomainReg5 extends IEDR_BPR_Test {

    public function testDomainCreateEmptyOwnerType() {
        $command = new DomainCreateCommand("nodomain-cc.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1new.ie"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setRemark("Bla bla bla");
        $holder->setType("");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '181');
        $this->assertEquals($ReasonMsg, 'Holder type is a mandatory field');
        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');
    }

    public function testDomainCreateNonExistentOwnerType() {
        $command = new DomainCreateCommand("nodomain-cc.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1new.ie"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setRemark("Bla bla bla");
        $holder->setType("Non-existent Owner Type");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '182');
        $this->assertEquals($ReasonMsg, 'Holder type does not exist');
        $this->assertEquals($code, '2003');
        $this->assertEquals($msg, 'Parameter value range error');
    }

}
