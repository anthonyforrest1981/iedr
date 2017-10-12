<?php
/*
 *
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 *
 * query for domain transfers example
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainQueryCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new DomainQueryCommand();

// inbound transfers with date range
$command->setTransfer(TransferQuery::inbound("2007-05-01", "2020-01-01"));
$command->setPage(1);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
// var_dump($response);

// outbound transfers with date range
$command->setTransfer(TransferQuery::outbound("2007-05-01", "2020-01-01"));
$command->setPage(1);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
// var_dump($response);
