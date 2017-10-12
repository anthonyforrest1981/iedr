<?php
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainInfoLockedSuccess extends IEDR_BPR_Test {

    //test ref: 1.66.1.1

    //Test: DomainInfo valid domain
    /**
     * @group InfoSuccess
     */
    public function testDomainInfoSuccess() {
        $dom = 'example0006.ie';
        $command = new DomainInfoCommand("$dom");
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    //TODO: db test here - need to update the php schema for new status included in repsonce
}
