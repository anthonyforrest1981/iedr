<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

//test ref: 1.17.1.10
class DomainModifyNsSuccess extends IEDR_BPR_Test {

    /**
     * @group AmendingDomainSuccess
     */
    public function testDomainModifyNsSuccess() {
        $domainName = "example0934.ie";

        $command = new DomainModifyCommand();
        $command->setName($domainName);
        $command->addNssRem(new DomainNs("ns1.abc1.ie"));
        $command->addNssRem(new DomainNs("ns2.abc1.ie"));
        $command->addNssRem(new DomainNs("ns3.abc1.ie"));
        $command->addNssAdd(new DomainNs("ns1.example0005.ie")); //NOTE: GLUE is allowed only for subordinate hosts of domain
        $command->addNssAdd(new DomainNs("ns2.example0005.ie")); //NOTE: GLUE is allowed only for subordinate hosts of domain

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1400');
        $this->assertEquals($msg, 'Command completed successfully; no result');
    }

    /**
     * @depends testDomainModifyNSSucess
     * @group DomainModifySuccessDbTest
     */
    public function testDomainModifyNsSuccessDbTest2() {
        $arr = DBUtils::getDNS("example0934.ie");
        $dns = $arr['dns1'];
        $this->assertEquals($dns, 'ns2.example0005.ie');
    }

}
