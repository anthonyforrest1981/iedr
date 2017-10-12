<?php
require_once('IEDR-API/bus/commands/RegistrantTransferBuyRequestQueryCommand.php');
require_once('IEDR-API/bus/commands/RegistrantTransferBuyRequestInfoCommand.php');

require_once('IEDR_BPR_II.php');

class RegistrantTransferBuyRequestQuery_success_Test extends IEDR_BPR_Test_II {

    public function testBuyRequestQuery() {
        $domainName = 'nv-domain0105.ie';
        $command = new RegistrantTransferBuyRequestQueryCommand();
        $command->setDomainName($domainName);
        $requests = $this->checkQueryResults($command, '1', '1', 1);
        $request = $requests[0];
        $this->assertEquals($request->getId(), 201);
        $this->assertEquals($request->getDomainName(), $domainName);
        $this->assertEquals($request->getHolder()->getName(), 'New Test Holder');
        $this->assertEquals($request->getHolder()->getRemark(), 'Test Remark');
        $this->assertEquals($request->getCrDate(), date("Y-m-d", strtotime('-14 days')));
    }

    public function testBuyRequestQueryNoDomainName() {
        $command = new RegistrantTransferBuyRequestQueryCommand();

        $requests = $this->checkQueryResults($command, '1', '1', 2);
        $this->assertEquals($requests[0]->getDomainName(), 'nv-domain0105.ie');
        $this->assertEquals($requests[1]->getDomainName(), 'nv-domain0106.ie');
    }

    public function testBuyRequestQueryNoResults() {
        $domainName = 'nv-domain0001.ie';
        $command = new RegistrantTransferBuyRequestQueryCommand();
        $command->setDomainName($domainName);

        $this->checkQueryResults($command, '1', '0', 0);
    }

    public function testBuyRequestQueryNotOwnDomain() {
        $domainName = 'example0604.ie';
        $command = new RegistrantTransferBuyRequestQueryCommand();
        $command->setDomainName($domainName);

        $this->checkQueryResults($command, '1', '0', 0);
    }

    public function testBuyRequestQueryWithPage() {
        $command = new RegistrantTransferBuyRequestQueryCommand();
        $command->setPage(2);

        $this->checkQueryResults($command, '2', '1', 0);
    }

    private function checkQueryResults($command, $page, $totalPages, $results) {
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $this->assertEquals($result->getCode(), '1000');
        $this->assertEquals($result->getMsg(), 'Command completed successfully');

        $this->assertEquals($response->getPage(), $page);
        $this->assertEquals($response->getTotalPages(), $totalPages);
        $this->assertEquals(count($response->getRequests()), $results);
        return $response->getRequests();
    }

}
