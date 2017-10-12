<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestModifyCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");

require_once("IEDR_BPR.php");

class BuyRequestModify_failure_holderTooLong_Test extends IEDR_BPR_Test {

    const BUYREQUESTID = 102;

    /**
     * @group RegistrantTransferBuyRequestModifyFailure
     */
    public function testModifyBuyRequest() {
        $command = $this->getBasicBuyRequestCommand(self::BUYREQUESTID);
        $command->getHolder()->setName(str_repeat("This is a very long holder name.", 8));

        $response = CommandProcessor::process($command);
        var_dump($response);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
        $this->assertEquals($result->getReasonCode(), 178);
        $this->assertEquals($result->getReasonMsg(), 'Holder name is too long');
    }

    private function getBasicBuyRequestCommand($buyRequestId) {
        $command = new RegistrantTransferBuyRequestModifyCommand();
        $command->setBuyRequestId($buyRequestId);
        $holder = new IeapiHolder();
        $holder->setName("API Holder");
        $holder->setRemark("API Test remark");
        $command->setHolder($holder);
        $contact = new IeapiContact();
        $contact->setName("API John Doe");
        $contact->setCompanyName("API My Company");
        $contact->setAddress("API Address");
        $contact->setCounty("Co. Dublin");
        $contact->setCountry("Ireland");
        $contact->setVoice("123123123");
        $contact->setFax("321321321");
        $contact->setEmail("api.email@iedr.ie");
        $command->setContact($contact);
        return $command;
    }

}
