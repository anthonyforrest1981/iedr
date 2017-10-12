<?php
include_once("IEDR-API/bus/commands/ContactCreateCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

class ContactModify extends IEDR_BPR_Test {

    /**
     * @group ContactCreateSuccess
     */
    public function testContactCreateSuccess() {
        $command = new ContactCreateCommand();
        $command->setName("Daniel Matlock");
        $command->setCompanyName("Ladbrokes Betting &amp; Gaming Ltd");
        $command->setAddress("Imperial HouseRayners Lane");
        $command->setCounty("Middlesex");
        $command->setCountry("United Kingdom");
        $command->setVoice("+44.2085155139");
        $command->setFax("+44.2085155139");
        $command->setEmail("domains@ladbrokes.com");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
        //todo: add db test here
    }

    public function testContactCreateDuplicateTelecoms() {
        $command = new ContactCreateCommand();
        $command->setName("Daniel Matlock");
        $command->setCompanyName("Ladbrokes Betting &amp; Gaming Ltd");
        $command->setAddress("Imperial HouseRayners Lane");
        $command->setCounty("Middlesex");
        $command->setCountry("United Kingdom");
        $command->setVoice("+44.2085155139,+44.2085155139");
        $command->setFax("+44.2085155139,+44.2085155139");
        $command->setEmail("domains@ladbrokes.com");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
        //todo: add full db test here

        $telecoms = DBUtils::getTelecoms($response->getId());
        $phones = $telecoms['phones'];
        $faxes = $telecoms['faxes'];
        $this->assertCount(1, $phones);
        $this->assertCount(1, $faxes);
        $this->assertEquals('Fax', $faxes[0]['Type']);
        $this->assertEquals('+44.2085155139', $faxes[0]['Phone']);
        $this->assertEquals('0', $faxes[0]['Order']);
        $this->assertEquals('Phone', $phones[0]['Type']);
        $this->assertEquals('+44.2085155139', $phones[0]['Phone']);
        $this->assertEquals('0', $phones[0]['Order']);
    }
}
