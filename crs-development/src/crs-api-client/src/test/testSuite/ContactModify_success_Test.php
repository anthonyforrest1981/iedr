<?php
include_once("IEDR-API/bus/commands/ContactModifyCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

//test ref: 1.51.1.3

class ContactModifySuccessfully extends IEDR_BPR_Test {

    /**
     * @group ContactModifySuccess
     */
    public function testContactModifySuccess() {

        $command = new ContactModifyCommand("XOE550-IEDR");

        $command->setCompanyName("Edit 1 Limited");
        $command->setAddress("Updated Address 1,
Address lIne 2 ");
        $command->setCountry("Ireland");
        $command->setCounty("Co. Dublin");
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

    public function testContactModifyDuplicateTelecoms() {
        $nicHandle = "XOE550-IEDR";
        $command = new ContactModifyCommand($nicHandle);

        $command->setCompanyName("Edit 1 Limited");
        $command->setAddress("Updated Address 1, Address lIne 2 ");
        $command->setCountry("Ireland");
        $command->setCounty("Co. Dublin");
        $command->setVoice("0863955775, 0863955775");
        $command->setFax("1234567, 1234567");
        $command->setEmail("ciaraEdit@iedr.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $telecoms = DBUtils::getTelecoms($nicHandle);
        $phones = $telecoms['phones'];
        $faxes = $telecoms['faxes'];
        $this->assertCount(1, $phones);
        $this->assertCount(1, $faxes);
        $this->assertEquals('Fax', $faxes[0]['Type']);
        $this->assertEquals('1234567', $faxes[0]['Phone']);
        $this->assertEquals('0', $faxes[0]['Order']);
        $this->assertEquals('Phone', $phones[0]['Type']);
        $this->assertEquals('0863955775', $phones[0]['Phone']);
        $this->assertEquals('0', $phones[0]['Order']);
    }
}
