<?php
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.67.1.6

class DomainQueryByNRP extends IEDR_BPR_Test {

    //Test: DomainQuery by NRP status
    /**
     * @group QuerySuccess
     */
    public function testDomainQuerySuccess() {
        // domains in NRP state

        $command = new DomainQueryCommand();
        $command->setDomainStatus('nrp');
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
