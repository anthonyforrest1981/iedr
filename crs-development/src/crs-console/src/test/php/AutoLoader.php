<?php

class AutoLoader {
    private static $classNames = array();

    public static function registerDirectory($dirName) {
        foreach (new DirectoryIterator($dirName) as $file) {
            if ($file->isDir() && !$file->isLink() && !$file->isDot()) {
                self::registerDirectory($file->getPathname());
            } elseif (substr($file->getFilename(), -4) === '.php') {
                $className = substr($file->getFilename(), 0, -4);
                AutoLoader::registerClass($className, $file->getPathname());
            }
        }
    }

    public static function registerClass($className, $fileName) {
        if (!isset(AutoLoader::$classNames[$className])) {
            AutoLoader::$classNames[$className] = $fileName;
        }
    }

    public static function loadClass($className) {
        if (isset(AutoLoader::$classNames[$className])) {
            require_once (AutoLoader::$classNames[$className]);
        }
    }

}
spl_autoload_register(array('AutoLoader','loadClass'));
