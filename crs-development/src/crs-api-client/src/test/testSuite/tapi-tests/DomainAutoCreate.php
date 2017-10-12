<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainAutocreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new DomainAutocreateCommand("my-autocreate.ie");
$command->addAdmin("XBC189-IEDR");
$command->addTech("XBC189-IEDR");
$command->addNs(new DomainNs("ns1.onet.pl"));
$command->addNs(new DomainNs("ns1.my-autocreate.ie", "192.168.1.1"));
$holder = new DomainHolder();
$holder->setName("Ala Kowalska");
$holder->setType("Company");
$holder->setRemark("Bla bla bla");
$command->setHolder($holder);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
