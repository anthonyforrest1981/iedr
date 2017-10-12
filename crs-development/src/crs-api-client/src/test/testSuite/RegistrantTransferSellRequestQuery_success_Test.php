<?php
require_once("IEDR-API/bus/commands/RegistrantTransferSellRequestQueryCommand.php");
require_once("IEDR_BPR_II.php");

class SellRequestQuery extends IEDR_BPR_Test_II {

    public function testSellRequestQuery() {

        $command = new RegistrantTransferSellRequestQueryCommand();
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $requests = $response->getRequests();
        $req1 = $requests[0];
        $req2 = $requests[1];
        $this->assertEquals($req1->getDomainName(), 'nv-domain0105.ie');
        $this->assertEquals($req1->getHolder()->getName(), 'New Test Holder');
        $this->assertEquals($req1->getCreationDate(), date("Y-m-d", strtotime('-1 days')));
        $this->assertEquals($req1->getCompletionDate(), date("Y-m-d", strtotime('+2 days')));
        $this->assertEquals($req2->getDomainName(), 'nv-domain0106.ie');
        $this->assertEquals($req2->getHolder()->getName(), 'New Test Holder');
        $this->assertEquals($req2->getCreationDate(), date("Y-m-d", strtotime('-1 days')));
        $this->assertEquals($req2->getCompletionDate(), date("Y-m-d", strtotime('+2 days')));
    }

}