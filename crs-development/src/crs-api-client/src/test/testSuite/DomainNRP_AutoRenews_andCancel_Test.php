<?php

include_once("IEDR-API/bus/commands/DomainNRPCancelCommand.php");
include_once("IEDR-API/bus/commands/DomainNRPCommand.php");

include_once("IEDR_BPR.php");

class DomainNRPAutorenewandCancel extends IEDR_BPR_Test {

    //test ref: 1.10.1.9 and 1.10.1.10

    /**
     * @group DomainNRPSuccess
     */

    //TEST ONE: ADD DOMAIN TO NRP from AUTORENEW STATE

    public function testDomainAutoRenew() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0156.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainAutoRenew
     * @group DomainNRPSuccessDbTest
     */

    public function testDomainNRPAutoRenewDbTest2() {
        $arr = DBUtils::getState("example0156.ie");
        $state = $arr['State'];
        $this->assertEquals($state, '20');
    }

    //TEST TWO: ADD DOMAIN to NRP from RENEW ONCE STATE

    public function testDomainRenewOnce() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0168.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainRenewOnce
     * @group DomainNRPSuccessDbTest
     */

    public function testDomainNRPRenewOnceDbTest2() {
        $arr = DBUtils::getState("example0168.ie");
        $state = $arr['State'];
        $this->assertEquals($state, '20');

    }

    //test ref: 1.10.1.6
    //TEST THREE: ADD DOMAIN TO NRP FROM CHAR STATE
    /**
     * @group DomainNRPSuccess
     */
    public function testDomainNRPSuccess() {

        $command = new DomainNRPCommand();
        $command->addDomain("example0283.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainNRPSuccess
     * @group DomainNRPSuccessDbTest
     */

    public function testDomainNRPCharityDbTest2() {
        $arr = array('example0283.ie');
        foreach ($arr as $dom) {
            $arr = DBUtils::getState("$dom");
        }

        $state = $arr['State'];

        $this->assertEquals($state, '116');

    }


    //test ref: 1.11.1.2, 1.11.1.3, 1.11.1.4, 1.11.1.5
    //TEST FOUR: Cancel NRP for these domains

    /**
     * @depends testDomainNRPSuccess
     * @depends testDomainAutoRenew
     * @depends testDomainRenewOnce
     * @group DomainNRCancelSuccess
     */
    public function testDomainNrpCancelAutoRenews() {

        $command = new DomainNRPCancelCommand();
        $command->addDomain("example0283.ie");
        $command->addDomain("example0156.ie");
        $command->addDomain("example0168.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainNrpCancelAutoRenews
     * @group DomainModifySuccessDbTest
     */

    public function testDomainNRCancelSuccessDbTest2() {
        $arr = DBUtils::getState('example0283.ie');
        $state = $arr['State'];
        $this->assertEquals($state, '113');
    }

    public function testDomainNRCancelSuccessDbTest4() {
        $arr = DBUtils::getState('example0156.ie');
        $state = $arr['State'];
        $this->assertEquals($state, '17');
    }

    public function testDomainNRCancelSuccessDbTest5() {
        $arr = DBUtils::getState('example0168.ie');
        $state = $arr['State'];
        $this->assertEquals($state, '17');
    }

}
