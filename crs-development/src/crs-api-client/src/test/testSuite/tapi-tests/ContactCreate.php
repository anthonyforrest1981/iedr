<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/ContactCreateCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new ContactCreateCommand();
$command->setName("Wesley MacTesterson");
$command->setCompanyName("Big Company");
$command->setAddress("Windsor Terrace");
$command->setCountry("Antarctica");
$command->setVoice("(022) 121-123-111");
$command->setEmail("email@email.test");
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
