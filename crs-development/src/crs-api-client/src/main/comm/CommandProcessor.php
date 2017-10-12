<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("comm/Sender.php");
include_once("CommandProcessorException.php");
include_once("AuthenticationException.php");
include_once("IEDR-API/parsers/ApiXmlParser.php");
include_once("IEDR-API/config/ConfigFactory.php");

class CommandProcessor {

    function process($command) {
        $response = self::internalProcess($command);
        $result = $response->getResult();
        if ($result->getCode() == 2001) {
            $config = ConfigFactory::getConfig();
            $autoLogin = $config->getAttribute("autologin");
            if ($autoLogin) {
                $login = $config->getAttribute("username");
                $password = $config->getAttribute("password");
                CommandProcessor::login($login, $password);
            }
            $response = self::internalProcess($command);
        }

        return $response;
    }

    private function internalProcess($command) {
        if (!$command->getTid()) {
            $command->setTid(self::generateTid());
        }

        $config = ConfigFactory::getConfig();
        $debugXml = $config->getAttribute("debugXml");
        $debug = $config->getAttribute("debug");

        if ($debug) {
            print_r($command);
        }

        $commandXml = ApiXmlParser::toXml($command);

        if ($debugXml) {
            print_r($commandXml);
        }

        $sender = new Sender();
        $responseXml = $sender->send($commandXml);

        if ($debugXml) {
            print_r($responseXml);
        }

        $response = ApiXmlParser::fromXml($responseXml);

        if ($debug) {
            print_r($response);
        }

        return $response;
    }

    private static function generateTid() {
        return hash('md5', microtime());
    }

    function login($login, $password) {
        include_once("IEDR-API/bus/commands/LoginCommand.php");
        $command = new LoginCommand();
        $command->setUsername($login);
        $command->setPassword($password);
        $resp = self::internalProcess($command);
        $result = $resp->getResult();
        $resCode = $result->getCode();

        if ($resCode == 2100) {
            throw new AuthenticationException();
        }

        if ($resCode != 1000 && $resCode != 2001) {
            throw new CommandProcessorException("Can't login: " . $result->getMsg());
        }
    }
}
