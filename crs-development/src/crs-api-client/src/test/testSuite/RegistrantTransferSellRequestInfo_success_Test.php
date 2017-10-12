<?php
require_once("IEDR-API/bus/commands/RegistrantTransferSellRequestInfoCommand.php");
require_once("IEDR_BPR_II.php");

class SellRequestInfoSuccess extends IEDR_BPR_Test_II {

    public function testSellRequestInfo() {

        $command = new RegistrantTransferSellRequestInfoCommand('201');
        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $this->assertEquals($response->getId(), '201');
        $this->assertEquals($response->getDomainName(), 'nv-domain0105.ie');
        $this->assertEquals($response->getHolder()->getName(), 'New Test Holder');
        $this->assertEquals($response->getCreationDate(), date("Y-m-d", strtotime('-1 days')));
        $this->assertEquals($response->getCompletionDate(), date("Y-m-d", strtotime('+2 days')));
        $this->assertEquals($response->getContact()->getName(), 'New Admin');
        $this->assertEquals($response->getContact()->getCompanyName(), 'Test Inc.');
        $this->assertEquals($response->getContact()->getAddress(), 'Test Street 1');
        $this->assertEquals($response->getContact()->getCounty(), 'Co. Antrim');
        $this->assertEquals($response->getContact()->getCountry(), 'Northern Ireland');
        $this->assertEquals($response->getContact()->getVoice(), '+35312345345');
        $this->assertEquals($response->getContact()->getFax(), '');
        $this->assertEquals($response->getContact()->getEmail(), 'email@iedr.ie');

    }

}
