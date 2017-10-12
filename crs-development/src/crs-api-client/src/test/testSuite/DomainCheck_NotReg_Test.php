<?php
include_once("IEDR-API/bus/commands/DomainCheckCommand.php");

include_once("IEDR_BPR.php");

class DomainCheckSuccess extends IEDR_BPR_Test {

    //test ref: 1.65.1.1

    //Test: DomainCheck valid domain
    /**
     * @group InfoSuccess
     */
    public function testDomainInfoSuccess() {
        $command = new DomainCheckCommand();
        $command->addDomain("test.ie");
        $command->addDomain("dfdssdf.com");
        $command->addDomain("iedr.ie");
        $command->addDomain("16thman.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    //TODO: add assertion for each repsonce type (ie. avail, registered etc)

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
