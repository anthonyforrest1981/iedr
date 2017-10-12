<?php
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.67.1.2
class DomainQueryRenRegDateSuccess extends IEDR_BPR_Test {

    //Test: DomainQuery test ren date range
    /**
     * @group QuerySuccess
     */
    public function testDomainQuerySuccess() {

        $renewalDateRangeStart = '2014-11-17';
        $renewalDateRangeEnd = '2014-11-17';
        $command = new DomainQueryCommand();
        $command->setRenewalDateRangeStart($renewalDateRangeStart);
        $command->setRenewalDateRangeEnd($renewalDateRangeEnd);
        $command->setPage(1);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1400');
        $this->assertEquals($msg, 'Command completed successfully; no result');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
