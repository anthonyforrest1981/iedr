<?php
require_once("IEDR-API/bus/commands/CreditCard.php");
require_once("IEDR-API/bus/commands/RegistrantTransferSellRequestCreateCommand.php");
require_once("IEDR_BPR.php");

class SellRequestCreateFailure extends IEDR_BPR_Test {

    public function testNonExistentDomain() {

        $command = new RegistrantTransferSellRequestCreateCommand();
        $command->setDomainName('nonexistent.ie');
        $command->setAuthcode('RT5S7E9Y9JJO');

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

    public function testDomainInNRP() {

        $command = new RegistrantTransferSellRequestCreateCommand();
        $command->setDomainName('example0609.ie');
        $command->setAuthcode('RTD94LTWZTC0');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '821');
        $this->assertEquals($ReasonMsg, 'Domain illegal state for operation');
        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');

    }

    public function testInvalidAuthcode() {

        $command = new RegistrantTransferSellRequestCreateCommand();
        $command->setDomainName('example0608.ie');
        $command->setAuthcode('INVALIDAUTHC');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '280');
        $this->assertEquals($ReasonMsg, 'Invalid authcode');
        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');

    }

    public function testSellRequestExists() {

        $command = new RegistrantTransferSellRequestCreateCommand();
        $command->setDomainName('example0605.ie');
        $command->setAuthcode('RTZCRVHNGN1B');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '501');
        $this->assertEquals($ReasonMsg, 'Sell request exists');
        $this->assertEquals($code, '2302');
        $this->assertEquals($msg, 'Object exists');

    }
}
