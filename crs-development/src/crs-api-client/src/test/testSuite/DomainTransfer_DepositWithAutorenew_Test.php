<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
require_once("IEDR-API/bus/commands/DomainPayFromDeposit.php");
include_once("IEDR_BPR.php");

class DomainTransfer_DepositWithAutorenew_Test extends IEDR_BPR_Test {

    //test ref: 1.6.1.1
    /**
     * @group DomainTransferSuccess
     */
    public function testDomainTransferWithAutorenew() {

        $command = new DomainTransferCommand("nv-domain0999.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $command->setPeriodWithUnit("y", "1");
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
     * @depends testDomainTransferWithAutorenew
     * @group DomainTransferSuccess
     */
    public function testDomainTransferDbTest() {
        $ticket = DBUtils::getTicketData("nv-domain0999.ie", "1");

        $domainName = $ticket['D_Name'];
        $ticketType = $ticket['T_Type'];
        $autorenew  = $ticket['autorenew'];

        $this->assertEquals($domainName, 'nv-domain0999.ie');
        $this->assertEquals($ticketType, 'Transfer');
        $this->assertEquals($autorenew, 'YES');
    }
}
