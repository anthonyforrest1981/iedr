<?php
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");

include_once("IEDR_BPR_AAE553.php");

//test ref: 1.67.1.1

class DomainQueryTransferAAEandRenPeriod extends IEDR_BPR_AAE_Test {

    //Test: DomainQuery test ren date range
    /**
     * @group QuerySuccess
     */
    public function testDomainQuerySuccess() {
        $renewalDateRangeStart = '2012-07-01';
        $renewalDateRangeEnd = '2013-08-30';
        $renewalStatus = 'Active';
        $contact = 'XBC189-IEDR';
        $contactType = 'tech';

        $command = new DomainQueryCommand();
        $command->setTransfer(TransferQuery::inbound("2007-05-01", "2020-01-01"));
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
