<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainCheckCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new LoginCommand();
$command->setUsername("NTG1-IEDR");
$command->setPassword("1q2w3e");

$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

$command = new DomainCheckCommand();
$command->addDomain("test.ie");
$command->addDomain("dfdssdf.com");
$command->addDomain("iedr.ie");
$command->addDomain("16thman.ie");
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

$command = new LogoutCommand();
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";