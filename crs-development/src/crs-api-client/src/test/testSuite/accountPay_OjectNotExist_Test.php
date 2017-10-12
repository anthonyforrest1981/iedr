<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");

include_once("IEDR_BPR.php");

//test ref:1.4.1.11

class AccountPayOne extends IEDR_BPR_Test {

    /**
     * @group PaymentSuccess
     */
    public function testAccountPayONE() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate("2014-02");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('no-domain.ie');
        $command->setPeriod('4');

        $response = CommandProcessor::process($command);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '200');
        $this->assertEquals($ReasonMsg, 'Domain name does not exist');
        $this->assertEquals($code, '2303');
        $this->assertEquals($msg, 'Object does not exist');

    }

}
