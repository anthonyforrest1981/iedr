<?php
/*
 * Copyright (C) 2007 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/AccountDepositFundsCommand.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new AccountDepositFundsCommand();
$command->setValue(100);
$creditCard = new CreditCard();
$creditCard->setCardHolder("John Doe");
$creditCard->setCardNumber("4263971921001307");
$creditCard->setExpiryDate("2015-01");
$creditCard->setCardType("VISA");
$command->setCreditCard($creditCard);
$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
