<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("FileLogger.php");

class DailyRotatorFileLogger extends FileLogger {

    protected function log($string) {
        if (is_file($this->logFilename)) {
            $stat = stat($this->logFilename);
            if ($stat) {
                $last = strftime("%Y-%m-%d", $stat["mtime"]);
                $act = strftime("%Y-%m-%d");
                if (strcmp($last, $act)) {
                    rename($this->logFilename, $this->logFilename . "." . $last);
                    clearstatcache();
                }
            }
        }
        parent::log($string);
    }

}
