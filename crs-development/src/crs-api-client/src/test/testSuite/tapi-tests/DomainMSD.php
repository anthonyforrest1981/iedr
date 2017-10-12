<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainMsdCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new DomainMsdCommand();
$command->addDomain("example0003.ie");
$command->addDomain("example0002.ie");
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
