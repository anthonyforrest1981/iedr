<?php
include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/bus/commands/TicketHolder.php");
include_once("IEDR_BPR.php");

class TransferTicketModify extends IEDR_BPR_Test {

    public function testDomainCreateForTest() {

        $command = new DomainTransferCommand("nv-domain0125.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->setDefaults("true");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    public function testTicketModifyHolder() {

        $command = new TicketModifyCommand("nv-domain0125.ie");
        $holder = new TicketHolder();
        $holder->setName("Another Test Holder");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '179');
        $this->assertEquals($ReasonMsg, 'Holder modification is not allowed');

        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

}