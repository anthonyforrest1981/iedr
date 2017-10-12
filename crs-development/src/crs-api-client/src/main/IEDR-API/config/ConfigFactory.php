<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

include_once("ConfigException.php");

class ConfigFactory {

    static private $instance = NULL;

    static function getConfig($type = 1) {

        if (ConfigFactory::$instance != NULL) {
            return ConfigFactory::$instance;
        }

        throw new ConfigException("Config doesn't initialized.");
    }

    static function setConfig(&$config) {
        ConfigFactory::$instance = $config;
    }

}
