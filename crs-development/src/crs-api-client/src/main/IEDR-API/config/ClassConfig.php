<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("IConfig.php");
include_once("ConfigException.php");

class ClassConfig implements IConfig {

    protected $configArray = array();

    function getAttribute($key) {
        if (isset($this->configArray[$key])) {
            return $this->configArray[$key];
        }
        throw new ConfigException("Can't find attribute '$key' in " . get_class($this) . ".");
    }

    function addAttribute($key, $val) {
        $this->configArray[$key] = $val;
    }
}
