<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new AccountPayCommand();
$command->setTest("false");
$command->addDomain("dom1.ie");
$command->addDomain("dom2.ie");
$command->addDomain("dom3.ie");
$creditCard = new CreditCard();
$creditCard->setCardHolder("John Smith");
$creditCard->setCardNumber("4263971921006107");
$creditCard->setExpiryDate("2010-08");
$creditCard->setCardType("VISA");
$command->setCreditCard($creditCard);

$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
