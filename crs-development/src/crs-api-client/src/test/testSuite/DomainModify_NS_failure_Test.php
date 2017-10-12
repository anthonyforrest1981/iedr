<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

class DomainModifyNSFailure extends IEDR_BPR_Test {

    public function testDomainModifyEmptyNS() {
        $domainName = "example0788.ie";

        $command = new DomainModifyCommand();
        $command->setName($domainName);
        $command->addNssRem(new DomainNs("ns1.abc1.ie"));
        $command->addNssRem(new DomainNs("ns2.abc1.ie"));
        $command->addNssRem(new DomainNs("ns3.abc1.ie"));
        $command->addNssAdd(new DomainNs("ns1.dns.ie"));
        $command->addNssAdd(new DomainNs(""));

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '162');
        $this->assertEquals($ReasonMsg, 'Nameserver name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

}
