<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("ClassConfig.php");

class FileConfig extends ClassConfig {

    private $filename;

    function FileConfig($filename) {

        $this->filename = $filename;

        $file = fopen($filename, "r");
        $data = "";
        if ($file) {
            while (!feof($file)) {
                $data .= fread($file, 1024);
            }
            fclose($file);
            $this->parseConfigFile($data);
        }
    }

    function parseConfigFile($data) {
        foreach (explode("\n", $data) as $line) {
            $a = explode("#", $line, 2);
            $line = $a[0];
            $a = explode("=", $line, 2);
            $a[0] = trim($a[0]);
            $a[1] = trim($a[1]);
            if ($a[0] != "") {
                $this->addAttribute($a[0], $a[1]);
            }
        }
    }

    function write() {
        $data = "";
        ksort($this->configArray);
        foreach ($this->configArray as $key => $val) {
            $data .= "$key=$val\n";
        }
        $file = fopen($this->filename, "w");
        if ($file) {
            fwrite($file, $data);
            fclose($file);
        }
    }
}
