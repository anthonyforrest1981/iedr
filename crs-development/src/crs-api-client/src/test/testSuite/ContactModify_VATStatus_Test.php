<?php
include_once("IEDR-API/bus/commands/ContactModifyCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.51.1.2
//This should not be permitted
class ContactModifyVatStatus extends IEDR_BPR_Test {

    /**
     * @group ContactModifySuccess
     */
    public function testContactModifySuccess() {

        $command = new ContactModifyCommand("XOE550-IEDR");

        $command->setCompanyName("Edit 1 Limited");
        $command->setAddress("Updated Address 1 ");
        $command->setCountry("Italy");
        $command->setCounty("n/a");
        $command->setVoice("0863955775");
        $command->setFax("1234567");
        $command->setEmail("ciaraEdit@iedr.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '410');
        $this->assertEquals($ReasonMsg, 'Country change forbidden');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');

    }
}
