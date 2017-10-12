<?php

require_once("IEDR_BPR.php");
require_once("IEDR-API/bus/commands/DomainCreateCommand.php");
require_once("IEDR-API/bus/commands/DomainNs.php");
require_once("IEDR-API/bus/commands/DomainHolder.php");
require_once("IEDR-API/bus/commands/DomainPayFromDeposit.php");

class DomainCreate_DepositWithAutorenew_Test extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateWithAutorenew() {

        $command = new DomainCreateCommand("new-example-0102.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->setUnitPeriod("y", "1");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns2.onet.pl"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Company");
        $command->setHolder($holder);
        $payFromDeposit = new DomainPayFromDeposit();
        $payFromDeposit->setAutorenewEnabled(TRUE);
        $command->setPayFromDeposit($payFromDeposit);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainCreateWithAutorenew
     * @group CreateTicketSuccessDbTest
     */
    public function testDomainCreateWithGlueDbTest() {

        $ticket = DBUtils::getTicketData("new-example-0102.ie", "1");

        $domainName = $ticket['D_Name'];
        $ticketType = $ticket['T_Type'];
        $autorenew  = $ticket['autorenew'];

        $this->assertEquals($domainName, 'new-example-0102.ie');
        $this->assertEquals($ticketType, 'Registration');
        $this->assertEquals($autorenew, 'YES');
    }
}
