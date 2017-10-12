<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

//test ref: 1.17.1.10
class DomainModifyDnsGlueSuccess extends IEDR_BPR_Test {

    /**
     * @group AmendingDomainSuccess
     */
    public function testDomainModifyDnsGlueSucess() {
        $domainName = "example1001.ie";

        $command = new DomainModifyCommand();
        $command->setName($domainName);
        $command->addNssRem(new DomainNs("ns1.example1001.ie"));
        $command->addNssAdd(new DomainNs("ns4.example1001.ie", "127.1.1.1", "::5"));

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals('1400', $code);
        $this->assertEquals('Command completed successfully; no result', $msg);
    }

    /**
     * @depends testDomainModifyDnsGlueSucess
     * @group DomainModifySuccessDbTest
     */
    public function testDomainModifyDnsGlueSucessDbCheck() {
        $nameservers = DBUtils::getAllDNS("example1001.ie");
        $this->assertCount(3, $nameservers);

        $nameserver = $nameservers[0];
        $this->assertEquals('ns2.example1001.ie', $nameserver['name']);
        $this->assertEquals('127.0.0.2', $nameserver['ipv4']);
        $this->assertEquals('::4', $nameserver['ipv6']);

        $nameserver = $nameservers[1];
        $this->assertEquals('ns3.example1001.ie', $nameserver['name']);
        $this->assertEquals('127.0.0.3', $nameserver['ipv4']);
        $this->assertEquals('::4', $nameserver['ipv6']);

        $nameserver = $nameservers[2];
        $this->assertEquals('ns4.example1001.ie', $nameserver['name']);
        $this->assertEquals('127.1.1.1', $nameserver['ipv4']);
        $this->assertEquals('::5', $nameserver['ipv6']);

    }

}
