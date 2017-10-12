<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("IEDR_BPR.php");

class TicketModifyAuthErrSuccess extends IEDR_BPR_Test {

    /**
     * @group AmendTicketSuccess
     */
    public function testTicketModifyHolderSuccess() {

        $command = new TicketModifyCommand();
        $command->setName("no-vat-myr.ie");
        $command->setStatus("renew");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '702');
        $this->assertEquals($ReasonMsg, 'Ticket is managed by another registrar');
        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');

    }

}
