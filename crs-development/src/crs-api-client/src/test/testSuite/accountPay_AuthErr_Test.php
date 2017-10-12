<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");

//test ref: 1.4.1.10

include_once("IEDR_BPR.php");

class AccountPayAutErr extends IEDR_BPR_Test {

    /*
* @group PaymentSuccess
    */
    public function testAccountPayAuthErr() {
        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate("2014-12");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('nv-domain1000.ie');
        $command->setPeriod('4');

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
