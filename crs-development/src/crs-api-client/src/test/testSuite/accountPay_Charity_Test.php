<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR_BPR.php");

//test ref: 1.4.1.12

class AccountPayChar extends IEDR_BPR_Test {

    /**
     * @group PaymentSuccess
     */
    public function testAccountPayChar() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate("2013-12");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('example0581.ie');
        $command->setPeriod('4');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '817');
        $this->assertEquals($ReasonMsg, 'Incorrect domain state for reactivation');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }

}
