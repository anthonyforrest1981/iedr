<?php
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.67.1.3
class DomainQueryContact extends IEDR_BPR_Test {

    //Test: DomainQuery test ren date range
    /**
     * @group QuerySuccess
     */
    public function testDomainQuerySuccess() {
        $contact = 'XBC189-IEDR';
        $command = new DomainQueryCommand("$contact");
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
