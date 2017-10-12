<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/AccountPayOnlineCommand.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");

ConfigFactory::setConfig(new ClientClassConfig());

$command = new AccountPayOnlineCommand();
$command->setTest("false");
$command->addDomain("example0964.ie");
$command->addDomain("example0856.ie");
$command->addDomain("example0374.ie");
$creditCard = new CreditCard();
$creditCard->setCardHolder("John Smith");
$creditCard->setCardNumber("4263971921001307");
$creditCard->setExpiryDate("2014-08");
$creditCard->setCardType("VISA");
$command->setCreditCard($creditCard);
$command->setPayType("msdUnPaid");

$response = CommandProcessor::process($command);
$result = $response->getResult();
echo get_class($command) . " " . $result->getCode() . " " . $result->getMsg() . "\n";

var_dump($response);
