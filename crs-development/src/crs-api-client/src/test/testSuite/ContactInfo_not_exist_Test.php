<?php
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

class ContactInfoNotExist extends IEDR_BPR_Test {

    /**
     * @group ContactInfoSuccess
     */
    public function testContactInfoSuccess() {

        $command = new ContactInfoCommand("ZZE550-IEDR");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '451');
        $this->assertEquals($ReasonMsg, 'Contact ID does not exist');
        $this->assertEquals($code, '2303');
        $this->assertEquals($msg, 'Object does not exist');

        //todo: add db test here
    }
}
