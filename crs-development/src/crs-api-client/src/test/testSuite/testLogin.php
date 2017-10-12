<?php

$user = "XBC189-IEDR";

$command = new LoginCommand();
$command->setUsername($user);
$command->setPassword("password");

$response = CommandProcessor::process($command);
$result = $response->getResult();
$code = $result->getCode();
$msg = $result->getMsg();

$this->assertEquals($code, '1000');
$this->assertEquals($msg, 'Command completed successfully');
