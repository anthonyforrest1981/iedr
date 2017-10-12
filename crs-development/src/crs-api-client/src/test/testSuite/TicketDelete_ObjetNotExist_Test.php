<?php

include_once("IEDR-API/bus/commands/TicketDeleteCommand.php");
include_once("IEDR_BPR.php");

class TicketDeleteNotExsit extends IEDR_BPR_Test {

    //Test: Attempt to delete a ticket that doesnt exist
    /**
     * @group DeleteTicketFailure
     */
    public function testTicketDeleteDomNoExist() {
        $command = new TicketDeleteCommand("no-domain.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '701');
        $this->assertEquals($ReasonMsg, 'Ticket name does not exist');
        $this->assertEquals($code, '2303');
        $this->assertEquals($msg, 'Object does not exist');
    }

}
