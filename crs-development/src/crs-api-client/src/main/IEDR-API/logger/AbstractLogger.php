<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("LoggerException.php");
include_once("ILogger.php");

abstract class AbstractLogger implements ILogger {

    static private $levels = array("ERROR" => 1, "WARN" => 2, "INFO" => 3, "DEBUG" => 4);
    private $level;
    private $levelName;

    protected function AbstractLogger($logLevel) {
        $this->levelName = $logLevel;
        if (!isset(AbstractLogger::$levels[$logLevel])) {
            throw new LoggerException("Unknown logLevel: $logLevel");
        }
        $this->level = (int)AbstractLogger::$levels[$logLevel];
    }

    function error($string) {
        if ($this->level >= AbstractLogger::$levels["ERROR"]) {
            $this->doLog($string, "ERROR");
        }
    }

    private function doLog($string, $level) {

        $string = "[" . date("d-m-Y H:i:s") . "] " . $level . " $string";

        $this->log($string);

    }

    abstract protected function log($string);

    function debug($string) {
        if ($this->level >= AbstractLogger::$levels["DEBUG"]) {
            $this->doLog($string, "DEBUG");
        }
    }

    function warn($string) {
        if ($this->level >= AbstractLogger::$levels["WARN"]) {
            $this->doLog($string, "WARN");
        }
    }

    function info($string) {
        if ($this->level >= AbstractLogger::$levels["INFO"]) {
            $this->doLog($string, "INFO");
        }
    }
}
