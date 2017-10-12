<?php
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

class ContactInfoNotAuth extends IEDR_BPR_Test {

    /**
     * @group ContactInfoFailure
     */
    public function testContactInfoSuccess() {

        $command = new ContactInfoCommand("END1-IEDR");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '452');
        $this->assertEquals($ReasonMsg, 'Contact is managed by another registrar');
        $this->assertEquals($code, '2201');
        $this->assertEquals($msg, 'Authorization error');

        //todo: add db test here
    }
}
