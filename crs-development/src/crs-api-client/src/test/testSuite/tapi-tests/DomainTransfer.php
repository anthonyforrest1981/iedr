<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new DomainTransferCommand("google.ie");
$command->addTechC("XBC189-IEDR");
$command->addNs(new DomainNs("ns.google.ie", "1.1.1.1"));
$command->addNs(new DomainNs("ns1.123.ie"));
$response = CommandProcessor::process($command);
$result = $response->getResult();
$code = $result->getCode();
$msg = $result->getMsg();

var_dump($response);
