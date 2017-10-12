<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new DomainCreateCommand("fdfdfd2.ie");
$command->addAdmin("NTG1-IEDR");
$command->addTech("NTG1-IEDR");
$command->addNs(new DomainNs("ns1.onet.pl"));
$command->addNs(new DomainNs("ns1.fdfdfd2.ie", "192.168.1.1"));
$holder = new DomainHolder();
$holder->setName("Ala Kowalska");
$holder->setType("Blogger/Other");
$holder->setRemark("Bla bla bla");
$command->setHolder($holder);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
