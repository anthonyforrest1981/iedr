<?php
/*
 *
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 *
 * query for domain with Active or NRP status
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new DomainQueryCommand();

// domains in Active state
$command->setDomainStatus('active');
$command->setPage(1);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
// var_dump($response);

// domains in NRP state
$command->setDomainStatus('nrp');
$command->setPage(1);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
// var_dump($response);
