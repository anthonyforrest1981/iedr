<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$domainName = "example0673.ie";

$command = new DomainInfoCommand($domainName);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
show_domain_delegation($response);

$nss = $response->getNss();

echo "adding new delegation...\n";
$command = new DomainModifyCommand();
$command->setName($domainName);
$command->addNssRem(new DomainNs("ns1.abc1.ie"));
$command->addNssAdd(new DomainNs("ns1.example0005.ie")); //NOTE: GLUE is allowed only for subordinate hosts of domain
$command->addNssRem(new DomainNs("ns2.abc1.ie"));
$command->addNssAdd(new DomainNs("ns2.example0005.ie")); //NOTE: insert here new GLUE, remove GLUE, or insert old GLUE
$command->addNssRem(new DomainNs("ns3.abc1.ie"));
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
//TO CHANGE GLUE you must Rem NS and Add the same NS with new GLUE

$command = new DomainInfoCommand($domainName);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
show_domain_delegation($response);

// Usefull function for show delegations
function show_domain_delegation($infoResponse) {

    $nss = $infoResponse->getNss();
    if (!$nss || count($nss) == 0) {
        echo "Domain " . $infoResponse->getName() . " has not delegations.\n";
    }

    echo "Domain " . $infoResponse->getName() . " has following delegations:\n";
    foreach ($nss as $ns) {
        echo "\t" . $ns->getName();
        if ($ns->getIp()) {
            echo "\t" . $ns->getIp();
        }
        echo "\n";
    }
}
