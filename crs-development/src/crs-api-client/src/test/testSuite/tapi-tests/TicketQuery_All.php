<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/TicketQueryCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new TicketQueryCommand();
$command->setQueryType("all");
// other types of query
//$command->setQueryType("registrations");
//$command->setQueryType("modifications");
//$command->setQueryType("deletions");
$command->setPage(1);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
