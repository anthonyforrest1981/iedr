<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("ILogger.php");
include_once("AbstractLogger.php");

class SyslogLogger extends AbstractLogger {

    public function __construct($logLevel) {
        parent::AbstractLogger($logLevel);
    }
    protected function log($string) {
        syslog(LOG_ERR, $string);

        return NULL;
    }
}
