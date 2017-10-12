<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("ILogger.php");
include_once("AbstractLogger.php");
include_once("LoggerException.php");

class FileLogger extends AbstractLogger {

    protected $logFilename;

    function FileLogger($filename, $logLevel) {
        $this->AbstractLogger($logLevel);
        $this->logFilename = $filename;
    }

    protected function log($string) {

        $handler = @fopen($this->logFilename, "a");

        if (!$handler) {
            throw new LoggerException("Can't open to write file " . $this->logFilename);
        }

        if (flock($handler, LOCK_EX)) {

            fwrite($handler, "$string\n");
            flock($handler, LOCK_UN);

        } else {
            fclose($handler);
            throw new LoggerException("Couldn't lock log the file '" . $this->logFilename . "'!");
        }

        fclose($handler);
    }
}
