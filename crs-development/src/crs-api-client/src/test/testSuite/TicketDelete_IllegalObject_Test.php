<?php

include_once("IEDR-API/bus/commands/TicketDeleteCommand.php");
include_once("IEDR_BPR.php");

class TicketDeleteNotAuthorised extends IEDR_BPR_Test {

    //Test: Attempt to delete a ticket belonging to another registrar

    /**
     * @group DeleteTicketFailure
     */
    public function testTicketDeleteTicketOnOtherAcc() {
        $domain = "hot-s-pot.ie";
        $command = new TicketDeleteCommand("$domain");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();
        //TODO db test
        $this->assertEquals($ReasonCode, '702');
        $this->assertEquals($ReasonMsg, 'Ticket is managed by another registrar');
        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');
    }

}
