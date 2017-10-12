<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

class AccountPayReservationPending extends IEDR_BPR_Test {

    public function testAccountPayReservationPending() {

        $command = new AccountPayCommand();
        $command->addDomain('example0610.ie');
        $command->setPeriod("2");
        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $command = new AccountPayCommand();
        $command->addDomain('example0610.ie');
        $command->setPeriod("1");
        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '823');
        $this->assertEquals($ReasonMsg, 'Payment is pending');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');
    }

}