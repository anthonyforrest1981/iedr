<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/config/ConfigException.php");

include_once("FileLogger.php");
include_once("SyslogLogger.php");

class LoggerFactory {

    static private $loggers = array(
        "TYPE_FILE_LOGGER"     => 1,
        "TYPE_DATABASE_LOGGER" => 2,
        "TYPE_SOCKET_LOGGER"   => 3,
        "TYPE_SYSLOG_LOGGER"   => 4);

    static private $instance;

    static function getLogger() {

        if (isset(LoggerFactory::$instance)) {
            return LoggerFactory::$instance;
        }

        try {
            $config = ConfigFactory::getConfig();
            $type = LoggerFactory::$loggers[$config->getAttribute("loggerType")];
            switch ($type) {
                case 1:
                    LoggerFactory::$instance =
                        new FileLogger($config->getAttribute("logFile"),
                            $config->getAttribute("logLevel"));
                    break;
                case 2:
                case 3:
                default:
                    LoggerFactory::$instance = new SyslogLogger($config->getAttribute("logLevel"));
            }
        } catch (ConfigException $ex) {
            LoggerFactory::$instance = new SyslogLogger("INFO");
            LoggerFactory::$instance->error($ex->getMessage());
        }

        return LoggerFactory::$instance;
    }
}
