<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/bus/commands/TicketHolder.php");
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("IEDR_BPR.php");

//test ref: 1.1.1.22
class DomainReg15 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateForTest() {

        $command = new DomainCreateCommand("new-example-0101.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example.ie"));
        $command->addNs(new DomainNs("ns2.example.ie"));
        $command->setUnitPeriod("y", "1");
        $holder = new DomainHolder();
        $holder->setName("IEDR Test Holder");
        $holder->setType("Company");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    // Ticket Holder tests
    public function testTicketModifyHolderRemarkTooLong() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $holder = new TicketHolder();
        $holder->setName("IEDR Test Holder");
        $remark = str_repeat("a", 10001);
        $holder->setRemark($remark);
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '176');
        $this->assertEquals($ReasonMsg, 'Holder remark is too long');

        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    public function testTicketModifyHolderEmptyName() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $holder = new TicketHolder();
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '151');
        $this->assertEquals($ReasonMsg, 'Domain Holder is a mandatory field');

        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    // Ticket Contact tests

    public function testTicketModifyTooFewAdminContacts() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("XBC189-IEDR");
        $command->addAdminCRem($contact);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '165');
        $this->assertEquals($ReasonMsg, 'Too few administrative contacts');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyTooManyAdminContacts() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("XOE550-IEDR");
        $command->addAdminCAdd($contact);
        $contact = new TicketContact("XOE551-IEDR");
        $command->addAdminCAdd($contact);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '166');
        $this->assertEquals($ReasonMsg, 'Too many administrative contacts');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyTooFewTechContacts() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("XBC189-IEDR");
        $command->addTechCRem($contact);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '167');
        $this->assertEquals($ReasonMsg, 'Too few technical contacts');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyTooManyTechContacts() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("XOE550-IEDR");
        $command->addTechCAdd($contact);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '168');
        $this->assertEquals($ReasonMsg, 'Too many technical contacts');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyContactSyntaxError() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("XOE550-IDR");
        $command->addAdminCAdd($contact);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '169');
        $this->assertEquals($ReasonMsg, 'Contact ID syntax error');

        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    public function testTicketModifyContactLowercase() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("xoe550-iedr");
        $command->addAdminCAdd($contact);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '169');
        $this->assertEquals($ReasonMsg, 'Contact ID syntax error');

        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    public function testTicketModifyContactNotFound() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $contact = new TicketContact("NOT-EXISTS-IEDR");
        $command->addAdminCAdd($contact);

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

    // Ticket Nameserver Tests

    public function testTicketModifyTooFewNameservers() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $ns = new TicketNs("ns2.example.ie");
        $command->addNssRem($ns);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '159');
        $this->assertEquals($ReasonMsg, 'Too few nameservers');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyTooManyNameservers() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $ns = new TicketNs("ns3.example.ie");
        $command->addNssAdd($ns);
        $ns = new TicketNs("ns4.example.ie");
        $command->addNssAdd($ns);
        $ns = new TicketNs("ns5.example.ie");
        $command->addNssAdd($ns);
        $ns = new TicketNs("ns6.example.ie");
        $command->addNssAdd($ns);
        $ns = new TicketNs("ns7.example.ie");
        $command->addNssAdd($ns);
        $ns = new TicketNs("ns8.example.ie");
        $command->addNssAdd($ns);

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

    public function testTicketModifyNameserverSyntaxError() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $ns = new TicketNs("ns2.example@ie");
        $command->addNssAdd($ns);

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

    public function testTicketModifyGlueRequired() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $ns = new TicketNs("ns.new-example-0101.ie");
        $command->addNssAdd($ns);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '175');
        $this->assertEquals($ReasonMsg, 'Glue record is required');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyGlueNotAllowed() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $ns = new TicketNs("ns3.example.ie");
        $ns->setIp("192.168.0.1");
        $command->addNssAdd($ns);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '164');
        $this->assertEquals($ReasonMsg, 'Glue record is not allowed');

        $this->assertEquals($code, '2308');
        $this->assertEquals($msg, 'Data management policy violation');
    }

    public function testTicketModifyIpSyntaxError() {

        $command = new TicketModifyCommand("new-example-0101.ie");
        $ns = new TicketNs("ns.new-example-0101.ie");
        $ns->setIp("192.168.0.256");
        $command->addNssAdd($ns);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '163');
        $this->assertEquals($ReasonMsg, 'IP address syntax error');

        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

}
