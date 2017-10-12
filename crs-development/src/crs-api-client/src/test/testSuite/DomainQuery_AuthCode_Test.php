<?php
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.67.1.2
class DomainQueryAuthCode extends IEDR_BPR_Test {

    //Test: DomainQuery test ren date range
    /**
     * @group QuerySuccess
     */
    public function testDomainQuerySuccess() {

        date_default_timezone_set('Europe/Berlin');
        $renewalDateRangeStart = date('Y-m-d', strtotime("-1 month"));
        $renewalDateRangeEnd = date('Y-m-d', strtotime("-1 month"));
        $command = new DomainQueryCommand();
        $command->setAuthCode(TRUE);
        $command->setRenewalDateRangeStart($renewalDateRangeStart);
        $command->setRenewalDateRangeEnd($renewalDateRangeEnd);
        $command->setPage(1);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    public function testDomainQueryForceSuccess() {

        date_default_timezone_set('Europe/Berlin');
        $renewalDateRangeStart = date('Y-m-d', strtotime("+2 month"));
        $renewalDateRangeEnd = date('Y-m-d', strtotime("+2 month"));
        $command = new DomainQueryCommand();
        $command->setAuthCode(TRUE);
        $command->setAuthCodeForceGeneration(TRUE);
        $command->setRenewalDateRangeStart($renewalDateRangeStart);
        $command->setRenewalDateRangeEnd($renewalDateRangeEnd);
        $command->setPage(1);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}

