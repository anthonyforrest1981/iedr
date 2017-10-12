<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR_BPR.php");

class AccountPayFailure extends IEDR_BPR_Test {

    public function testAccountEmptyCardNumber() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('example0070.ie');
        $command->addDomain('example0082.ie');
        $command->addDomain('example0108.ie');
        $command->addDomain('example0124.ie');
        $command->setPeriod('1');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '805');
        $this->assertEquals($ReasonMsg, 'Account cardNumber is a mandatory field');
        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');

    }

    public function testAccountEmptyCardHolder() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('example0070.ie');
        $command->addDomain('example0082.ie');
        $command->addDomain('example0108.ie');
        $command->addDomain('example0124.ie');
        $command->setPeriod('1');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '804');
        $this->assertEquals($ReasonMsg, 'Account cardHolder is a mandatory field');
        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');

    }

    public function testAccountEmptyCardType() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $command->setCreditCard($creditCard);
        $command->addDomain('example0070.ie');
        $command->addDomain('example0082.ie');
        $command->addDomain('example0108.ie');
        $command->addDomain('example0124.ie');
        $command->setPeriod('1');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '807');
        $this->assertEquals($ReasonMsg, 'Account cardType is a mandatory field');
        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');

    }

    public function testAccountEmptyExpiryDate() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('example0070.ie');
        $command->addDomain('example0082.ie');
        $command->addDomain('example0108.ie');
        $command->addDomain('example0124.ie');
        $command->setPeriod('1');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '806');
        $this->assertEquals($ReasonMsg, 'Account expiryDate is a mandatory field');
        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');

    }

}
