<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestCreateCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");
require_once("IEDR-API/bus/commands/CreditCard.php");

require_once("IEDR_BPR.php");

class RegistrantTransferBuyRequestCreate_success_Test extends IEDR_BPR_Test {

    public function testCreateBuyRequestDepositSuccess() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    public function testCreateBuyRequestCreditCardSuccess() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->setCreditCard($this->getCreditCard());

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    private function getBasicBuyRequestCommand($domainName) {
        $command = new RegistrantTransferBuyRequestCreateCommand();
        $command->setDomainName($domainName);
        $holder = new IeapiHolder();
        $holder->setName("John Doe");
        $holder->setType("Blogger/Other");
        $holder->setRemark("Holder remarks");
        $command->setHolder($holder);
        $contact = new IeapiContact();
        $contact->setName("John Doe");
        $contact->setCompanyName("My Company");
        $contact->setAddress("Address");
        $contact->setCounty("Co. Dublin");
        $contact->setCountry("Ireland");
        $contact->setVoice("123123123");
        $contact->setFax("321321321");
        $contact->setEmail("email@iedr.ie");
        $command->setContact($contact);
        return $command;
    }

    private function getCreditCard() {
        $creditCard = new CreditCard();
        $creditCard->setCardHolder("John Doe");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $creditCard->setCvnNumber("123");
        return $creditCard;
    }

}
