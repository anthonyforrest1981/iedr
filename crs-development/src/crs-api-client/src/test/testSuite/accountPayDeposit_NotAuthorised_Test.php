<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");

include_once("IEDR_BPR.php");

class AccountPayDeposit1 extends IEDR_BPR_Test {

    /**
     * @group PaymentSuccess
     */
    public function testAccountPay10() {

        $command = new AccountPayCommand();

        $command->addDomain('nv-domain0079.ie');
        $command->setPeriod("1");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '201');
        $this->assertEquals($ReasonMsg, 'Domain is managed by another registrar');
        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');

    }

}
