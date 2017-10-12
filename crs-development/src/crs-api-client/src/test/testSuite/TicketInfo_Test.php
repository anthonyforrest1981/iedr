<?php

include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("IEDR_BPR.php");

//test ref: 1.68.1.3, 1.68.1.1, 1.68.1.2

class TicketInfo extends IEDR_BPR_Test {

    //Test: Attempt TicketInfo on a ticket that doesnt exist
    /**
     * @group InfoFailure
     */
    public function testTicketInfoDomNoExist() {
        $command = new TicketInfoCommand("no-domain.ie");

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

    //Test: Attempt TicketInfo on a ticket belonging to another registrar
    /**
     * @group InfoFailure
     */

    public function testTicketInfoTicketOnOtherAcc() {
        $domain = "hot-s-pot.ie";
        $command = new TicketInfoCommand("$domain");

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
