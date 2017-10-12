<?php
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.67.1.1

class DomainQueryTransferandRenPeriod extends IEDR_BPR_Test {

    //Test: DomainQuery test ren date range
    /**
     * @group QuerySuccess
     */
    public function testDomainQuerySuccess() {
        $renewalDateRangeStart = '2013-07-01';
        $renewalDateRangeEnd = '2013-07-30';
        $renewalStatus = 'Active';
        $contact = 'XBC189-IEDR';
        $contactType = 'tech';

        $command = new DomainQueryCommand();
        $command->setTransfer(TransferQuery::outbound("2007-05-01", "2020-01-01"));
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1400');
        $this->assertEquals($msg, 'Command completed successfully; no result');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
