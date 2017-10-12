<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

//test ref: 1.1.1.19, 1.1.1.6, 1.1.1.7, 1.1.1.8

class DomainReg7 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateInvalidFormat() {

        $command = new DomainCreateCommand("nodomain.com");
        $command->addAdmin("END1-IEDR");
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

        $this->assertEquals($ReasonCode, '155');
        $this->assertEquals($ReasonMsg, 'Domain name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateInvalidFormat1() {

        $command = new DomainCreateCommand("no_domain.ie");
        $command->addAdmin("END1-IEDR");
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

        $this->assertEquals($ReasonCode, '155');
        $this->assertEquals($ReasonMsg, 'Domain name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateInvalidFormat2() {

        $command = new DomainCreateCommand("hp.ie");
        $command->addAdmin("END1-IEDR");
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

        $this->assertEquals($ReasonCode, '155');
        $this->assertEquals($ReasonMsg, 'Domain name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateInvalidFormat3() {

        $command = new DomainCreateCommand("-hello-.ie");
        $command->addAdmin("END1-IEDR");
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

        $this->assertEquals($ReasonCode, '155');
        $this->assertEquals($ReasonMsg, 'Domain name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateInvalidFormat4() {

        $command =
            new DomainCreateCommand("hdjkslkdjhfjksjjdklskdlkasasdddasdasdasdasdasdasdasdasdasdalkjsadlkjlkjlkjlkjlkj.ie");
        $command->addAdmin("END1-IEDR");
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

        $this->assertEquals($ReasonCode, '155');
        $this->assertEquals($ReasonMsg, 'Domain name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

}
