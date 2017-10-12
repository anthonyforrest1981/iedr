<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

class DomainModifyR1Success extends IEDR_BPR_Test

    //test ref: 1.17.1.2

{

    /**
     * @group DomainModifySuccess
     */
    public function testDomainModifyR1() {

        $command = new DomainModifyCommand('example0242.ie');
        $command->setRenewalMode("RenewOnce");
        $command->addTechCAdd("XOE550-IEDR");
        $command->addTechCRem("XBC189-IEDR");
        $command->addNssRem(new DomainNs("ns1.abc1.ie"));
        $command->addNssRem(new DomainNs("ns2.abc1.ie"));
        $command->addNssRem(new DomainNs("ns3.abc1.ie"));
        $command->addNssAdd(new DomainNs("ns1.example0005.ie")); //NOTE: GLUE is allowed only for subordinate hosts of domain
        $command->addNssAdd(new DomainNs("ns2.example0005.ie")); //NOTE: GLUE is allowed only for subordinate hosts of domain

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainModifyR1
     * @group DomainModifySuccessDbTest
     */

    public function testDomainModifyR1DbTest2() {

        $arr = DBUtils::getState("example0242.ie");

        $state = $arr['State'];

        $this->assertEquals($state, '49');

    }

}
