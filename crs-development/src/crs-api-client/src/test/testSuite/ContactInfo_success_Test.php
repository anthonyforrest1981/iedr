<?php
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

//test ref: other

class ContactInfo extends IEDR_BPR_Test {

    /**
     * @group ContactInfoSuccess
     */
    public function testContactInfoSuccess() {

        $command = new ContactInfoCommand("XBC189-IEDR");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        //todo: add db test here
    }
}
