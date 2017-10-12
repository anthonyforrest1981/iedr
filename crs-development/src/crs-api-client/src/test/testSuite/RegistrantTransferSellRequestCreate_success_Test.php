<?php
require_once("IEDR-API/bus/commands/CreditCard.php");
require_once("IEDR-API/bus/commands/RegistrantTransferSellRequestCreateCommand.php");
require_once("IEDR_BPR.php");

class SellRequestCreate extends IEDR_BPR_Test {

    public function testSellRequestCreateCC() {

        $command = new RegistrantTransferSellRequestCreateCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("Test Holder");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->setDomainName('example0604.ie');
        $command->setAuthcode('RTNT400JHB1O');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }

    public function testSellRequestCreateADP() {

        $command = new RegistrantTransferSellRequestCreateCommand();
        $command->setDomainName('example0607.ie');
        $command->setAuthcode('RT5S7E9Y9JJO');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }

}