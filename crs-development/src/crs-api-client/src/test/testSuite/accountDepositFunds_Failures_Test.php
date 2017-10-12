<?php
include_once("IEDR-API/bus/commands/AccountDepositFundsCommand.php");
include_once("IEDR-API/bus/responses/AccountDepositFundsDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR_BPR.php");

class AccountDepositCheck extends IEDR_BPR_Test

    //test ref: 1.24.1.2, 1.24.1.3, 1.24.1.4

{

    //Test: Attempt to top up a deposit account with a declined card
    /**
     * @group PaymentDepFailure
     */
    public function testDepositFundsDeclinedCard() {

        $command = new AccountDepositFundsCommand();
        $command->setValue(1250.12);
        $creditCard = new CreditCard();
        $creditCard->setCardHolder("John Doe");
        $creditCard->setCardNumber("4000126842489127");
        $creditCard->setExpiryDate("2014-12");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '809');
        $this->assertEquals($ReasonMsg,
            'Error processing payment due due to bad data or bad format of payment request');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }




    //Test: Attempt to top up deposit account with a value higher than permitted
    //TODO: set $min and $max from getRates
    /**
     * @group PaymentDepFailure
     */
    public function testDepositFundsValuetooHigh() {
        $command = new AccountDepositFundsCommand();
        $command->setValue(125000000000.00);
        $creditCard = new CreditCard();
        $creditCard->setCardHolder("John Doe");
        $creditCard->setCardNumber("4263971021001307");
        $creditCard->setExpiryDate("2011-12");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '815');
        $this->assertEquals($ReasonMsg, 'Deposit value too high');
        $this->assertEquals($code, '2003');
        $this->assertEquals($msg, 'Parameter value range error');
    }





    //Test: Attempt to top up deposit account with a value lower than permitted
    //TODO: set $min and $max from getRates
    /**
     * @group PaymentDepFailure
     */
    public function testDepositFundsValuetooLow() {
        $command = new AccountDepositFundsCommand();
        $command->setValue(100001.00);
        $creditCard = new CreditCard();
        $creditCard->setCardHolder("John Doe");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate("2014-12");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '815');
        $this->assertEquals($ReasonMsg, 'Deposit value too high');
        $this->assertEquals($code, '2003');
        $this->assertEquals($msg, 'Parameter value range error');
    }

}
