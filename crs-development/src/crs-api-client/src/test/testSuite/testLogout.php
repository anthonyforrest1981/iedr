<?php

$command = new LogoutCommand();
$response = CommandProcessor::process($command);
$result = $response->getResult();
$code = $result->getCode();
$msg = $result->getMsg();
$ReasonCode = $result->getReasonCode();
$ReasonMsg = $result->getReasonMsg();

$this->assertEquals($code, '1500');
$this->assertEquals($msg, 'Command completed successfully; ending session');
