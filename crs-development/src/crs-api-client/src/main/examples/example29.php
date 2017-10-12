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

$domainName = "pizzaonline.ie";

$command = new DomainInfoCommand($domainName);
$command->setFillNsAddr6(TRUE);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
show_domain_delegation($response);

$nss = $response->getNss();

echo "adding new delegation...\n";
$command = new DomainModifyCommand();
$command->setName($domainName);
$command->addNssAdd(new DomainNs("nah.pizzaonline.ie", "127.0.0.1",
    "::f")); //NOTE: GLUE is allowed only for subordinate hosts of domain
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
//IF you would like change all delegations, then Rem all old NS
//and Add newest one

$command = new DomainInfoCommand($domainName);
$command->setFillNsAddr6(TRUE);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
show_domain_delegation($response);
die();
echo "changing delegation.(add/rem/chg GLUE)..\n";
$command = new DomainModifyCommand();
$command->setName($domainName);
$command->addNssRem(new DomainNs("nah.1direct.ie"));
$command->addNssAdd(new DomainNs("nah.1direct.ie",
    "127.0.0.2")); //NOTE: insert here new GLUE, remove GLUE, or insert old GLUE
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
//TO CHANGE GLUE you must Rem NS and Add the same NS with new GLUE

$command = new DomainInfoCommand($domainName);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
show_domain_delegation($response);

echo "removing delegation...\n";
$command = new DomainModifyCommand();
$command->setName($domainName);
$command->addNssRem(new DomainNs("nah.1direct.ie"));
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
//TO Rem NS you do not need give a IP, only name of NS is important!

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
        if ($ns->getIpv6()) {
            echo "\t" . $ns->getIpv6();
        }
        echo "\n";
    }
}
