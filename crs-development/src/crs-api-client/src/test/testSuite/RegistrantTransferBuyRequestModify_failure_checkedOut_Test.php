<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestModifyCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");

require_once("IEDR_BPR.php");

class BuyRequestModify_failure_checkedOut_Test extends IEDR_BPR_Test {

    const BUYREQUESTID = 101;

    /**
     * @group RegistrantTransferBuyRequestModifyFailure
     */
    public function testModifyBuyRequest() {
        $command = $this->getBasicBuyRequestCommand(self::BUYREQUESTID);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2304');
        $this->assertEquals($msg, 'Object status prohibits operation');
        $this->assertEquals($result->getReasonCode(), 503);
        $this->assertEquals($result->getReasonMsg(), 'BuyRequest is currently being processed by a Hostmaster');
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
