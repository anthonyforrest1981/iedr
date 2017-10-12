<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

class DomainModifyDnsGlueSuccess extends IEDR_BPR_Test {

    /**
     * @group AmendingDomainFailure
     */
    public function testDomainModifyDnsRemoveNonexistingDNS() {
        $domainName = "example1001.ie";

        $command = new DomainModifyCommand();
        $command->setName($domainName);
        $command->addNssRem(new DomainNs("ns1.example100.ie"));

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $reasonCode = $result->getReasonCode();
        $reasonMsg = $result->getReasonMsg();

        $this->assertEquals('2308', $code);
        $this->assertEquals('254', $reasonCode);
        $this->assertEquals('Data management policy violation', $msg);
        $this->assertEquals('Domain is not delegated to the host to remove', $reasonMsg);
    }

    /**
     * @group AmendingDomainFailure
     */
    public function testDomainModifyDnsAddAlreadyExistingDNS() {
        $domainName = "example1001.ie";

        $command = new DomainModifyCommand();
        $command->setName($domainName);
        $command->addNssAdd(new DomainNs("ns1.example1001.ie"));

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $reasonCode = $result->getReasonCode();
        $reasonMsg = $result->getReasonMsg();

        $this->assertEquals('2308', $code);
        $this->assertEquals('253', $reasonCode);
        $this->assertEquals('Data management policy violation', $msg);
        $this->assertEquals('Domain is already delegated to the host to add', $reasonMsg);
    }

}
