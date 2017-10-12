<?php

class Yii extends YiiBase {
    private static $appInstance = null;

    public static function app() {
        if (!isset(self::$appInstance)) {
            self::$appInstance = new MockedApplication();
        }
        return self::$appInstance;
    }

    public static function import($alias, $forceInclude = false) {
        return $alias;
    }

    public static function log($message) {}

}
