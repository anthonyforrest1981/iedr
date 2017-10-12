<?php
require_once("IEDR-API/bus/commands/RegistrantTransferBuyRequestInfoCommand.php");
require_once("IEDR-API/bus/commands/IeapiContact.php");
require_once("IEDR-API/bus/commands/IeapiHolder.php");

require_once("IEDR_BPR.php");

class RegistrantTransferBuyRequestInfo_success_Test extends IEDR_BPR_Test {

    public function testGetBuyRequestDetailsSuccess() {
        $requestId = 101;
        $infoCommand = new RegistrantTransferBuyRequestInfoCommand();
        $infoCommand->setId($requestId);
        $response = CommandProcessor::process($infoCommand);
        $result = $response->getResult();

        $this->assertEquals($result->getCode(), '1000');
        $this->assertEquals($result->getMsg(), 'Command completed successfully');

        $this->assertEquals($response->getId(), $requestId);
        $this->assertEquals($response->getDomainName(), 'example0501.ie');
        $this->assertEquals($response->getHolder()->getName(), 'First holder');
        $this->assertEquals($response->getHolder()->getRemark(), 'First remark');
        $this->assertEquals($response->getCrDate(), date("Y-m-d", strtotime('-20 days')));
        $this->assertEquals($response->getContact()->getName(), 'Admin1 name');
        $this->assertEquals($response->getContact()->getCompanyName(), 'Test1 Inc.');
        $this->assertEquals($response->getContact()->getAddress(), 'Test Street 1');
        $this->assertEquals($response->getContact()->getCounty(), 'Co. Antrim');
        $this->assertEquals($response->getContact()->getCountry(), 'Northern Ireland');
        $this->assertEquals($response->getContact()->getVoice(), '123456789');
        $this->assertEquals($response->getContact()->getFax(), '');
        $this->assertEquals($response->getContact()->getEmail(), 'a@a.ie');
        $this->assertEquals($response->getStatus(), "Passed");
    }

}
