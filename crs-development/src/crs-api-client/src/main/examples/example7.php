<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/ContactInfoCommand.php");
include_once("IEDR-API/bus/commands/ContactModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new ContactInfoCommand("APITEST-IEDR");
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);

$command = new ContactModifyCommand();
$command->setId($response->getId());
$command->setCompanyName($response->getCompanyName());
$command->setAddress($response->getAddress());
$command->setCounty($response->getCounty());
$command->setCountry($response->getCountry());
$command->setVoice($response->getVoice());
$command->setFax("123456789");
$command->setEmail($response->getEmail());

$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";
var_dump($response);
