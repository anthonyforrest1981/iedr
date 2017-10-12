<?php
include_once("IEDR-API/bus/commands/ContactModifyCommand.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");

include_once("IEDR_BPR.php");

class ContactModifyCountryFailure extends IEDR_BPR_Test {

    private function getCommand($countryName, $countyName) {
        $command = new ContactModifyCommand("XBC189-IEDR");

        $command->setCompanyName("Edit 1 Limited");
        $command->setAddress("Updated Address 1 ");
        $command->setCountry($countryName);
        $command->setCounty($countyName);
        $command->setVoice("0863955775");
        $command->setFax("1234567");
        $command->setEmail("ciaraEdit@iedr.ie");

        return $command;
    }

    public function testEmptyCountry() {
        $response = CommandProcessor::process($this->getCommand(null, null));
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '403');
        $this->assertEquals($ReasonMsg, 'Contact country is mandatory field');
        $this->assertEquals($code, '2002');
        $this->assertEquals($msg, 'Required parameter missing');
    }

    public function testCountryDoesNotExist() {
        $response = CommandProcessor::process($this->getCommand("Non-existent", null));
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '406');
        $this->assertEquals($ReasonMsg, 'Country does not exist');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    public function testCountyDoesNotExist() {
        $response = CommandProcessor::process($this->getCommand("Ireland", "Non-existent"));
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '407');
        $this->assertEquals($ReasonMsg, 'County does not exist');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    public function testCountyRequired() {
        $response = CommandProcessor::process($this->getCommand("Ireland", "N/A"));
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '409');
        $this->assertEquals($ReasonMsg, 'County is required for the country');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }

    public function testCountyDoesNotMatch() {
        $response = CommandProcessor::process($this->getCommand("Ireland", "Alaska"));
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '408');
        $this->assertEquals($ReasonMsg, 'County does not match country');
        $this->assertEquals($code, '2102');
        $this->assertEquals($msg, 'Parameter value policy error');
    }
}