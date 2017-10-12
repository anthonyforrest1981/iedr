<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestCreateCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");
require_once("IEDR-API/bus/commands/CreditCard.php");

require_once("IEDR_BPR.php");

class RegistrantTransferBuyRequestCreate_failure_Test extends IEDR_BPR_Test {

    public function testCreateBuyRequestNotOwnDomain() {
        $command = $this->getBasicBuyRequestCommand("futuredomena.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');
        $this->assertEquals($result->getReasonCode(), '201');
        $this->assertEquals($result->getReasonMsg(), 'Domain is managed by another registrar');
    }

    public function testCreateBuyRequestHolderNameTooLong() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->getHolder()->setName(str_repeat("a", 256));

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
        $this->assertEquals($result->getReasonCode(), '178');
        $this->assertEquals($result->getReasonMsg(), 'Holder name is too long');
    }

    public function testCreateBuyRequestEmptyOwnerType() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->getHolder()->setType("");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');
        $this->assertEquals($result->getReasonCode(), '181');
        $this->assertEquals($result->getReasonMsg(), 'Holder type is a mandatory field');
    }

    public function testCreateBuyRequestNonExistentOwnerType() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->getHolder()->setType("Non-existent owner type");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2003');
        $this->assertEquals($msg, 'Parameter value range error');
        $this->assertEquals($result->getReasonCode(), '182');
        $this->assertEquals($result->getReasonMsg(), 'Holder type does not exist');
    }

    public function testCreateBuyRequestCharityOwnerType() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->getHolder()->setType("Charity");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
        $this->assertEquals($result->getReasonCode(), '183');
        $this->assertEquals($result->getReasonMsg(), 'Charity holder type is not allowed');
    }

    public function testCreateBuyRequestHolderRemarkMandatory() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->getHolder()->setRemark("");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');
        $this->assertEquals($result->getReasonCode(), '180');
        $this->assertEquals($result->getReasonMsg(), 'Holder remark is a mandatory field');
    }

    public function testCreateBuyRequestInvalidEmailAddress() {
        $command = $this->getBasicBuyRequestCommand("example0001.ie");
        $command->getContact()->setEmail("invalid@email@address.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
        $this->assertEquals($result->getReasonCode(), '411');
        $this->assertEquals($result->getReasonMsg(), 'Contact email is invalid');
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

}
