<?php
include_once("IEDR-API/bus/commands/ContactModifyCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.51.1.2
//This should not be permitted
class ContactModifyAdressandPhone extends IEDR_BPR_Test {

    /**
     * @group ContactModifySuccess
     */
    public function testContactModifySuccess() {

        $command = new ContactModifyCommand("XBC189-IEDR");

        $command->setCompanyName("Edit 1 Limited");
        $command->setAddress("Updated Address 1 ");
        $command->setCountry("Ireland");
        $command->setCounty("Co. Limerick");
        $command->setVoice("0863955775");
        $command->setFax("1234567");
        $command->setEmail("ciaraEdit@iedr.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }

    public function testContactModifyNoCounty() {

        $command = new ContactModifyCommand("XOE552-IEDR");

        $command->setCompanyName("Edit 2 Limited");
        $command->setAddress("Updated Address 2");
        $command->setCountry("Italy");
        $command->setVoice("0863955775");
        $command->setFax("1234567");
        $command->setEmail("ciaraEdit@iedr.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }
}
