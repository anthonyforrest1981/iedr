<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestModifyCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");

require_once("IEDR_BPR.php");

class BuyRequestModify_success_Test extends IEDR_BPR_Test {

    const BUYREQUESTID = 102;

    /**
     * @group RegistrantTransferBuyRequestModifySuccess
     */
    public function testModifyBuyRequest() {
        $command = $this->getBasicBuyRequestCommand(self::BUYREQUESTID);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testModifyBuyRequest
     * @group RegistrantTransferBuyRequestModifySuccessDb
     */
    public function testDomainCreateWithGlueDbTest() {

        $dbRequest = DBUtils::getBuyRequestData(self::BUYREQUESTID);

        $this->assertEquals($dbRequest['D_Name'], 'example0502.ie');
        $this->assertEquals($dbRequest['Creator_NH'], 'XBC189-IEDR');
        $this->assertEquals($dbRequest['A_Number'], 600);
        $this->assertEquals($dbRequest['D_Holder'], "API Holder");
        $this->assertEquals($dbRequest['Class_Id'], 1);
        $this->assertEquals($dbRequest['Category_Id'], 1);
        $this->assertEquals($dbRequest['Remark'], "API Test remark");
        $this->assertEquals($dbRequest['H_Remark'], "Second hostmaster remark");
        $this->assertEquals($dbRequest['Admin_Name'], "API John Doe");
        $this->assertEquals($dbRequest['Admin_Email'], "api.email@iedr.ie");
        $this->assertEquals($dbRequest['Admin_Co_Name'], "API My Company");
        $this->assertEquals($dbRequest['Admin_Address'], "API Address");
        $this->assertEquals($dbRequest['Admin_Country_Id'], 121);
        $this->assertEquals($dbRequest['Admin_County_Id'], 14);
        $this->assertEquals($dbRequest['Authcode'], null);
        $this->assertEquals($dbRequest['Status'], 'Renew');

        $dbRequestTelecoms = DBUtils::getBuyRequestTelecomData(self::BUYREQUESTID);
        var_dump($dbRequestTelecoms);
        $this->assertEquals(count($dbRequestTelecoms), 2);
        $this->assertEquals($dbRequestTelecoms[0]['Number'], '321321321');
        $this->assertEquals($dbRequestTelecoms[0]['Type'], 'Fax');
        $this->assertEquals($dbRequestTelecoms[1]['Number'], '123123123');
        $this->assertEquals($dbRequestTelecoms[1]['Type'], 'Phone');
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
