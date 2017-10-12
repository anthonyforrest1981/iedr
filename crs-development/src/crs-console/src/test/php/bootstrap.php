<?php
include_once ('AutoLoader.php');
// Load console sources
$appPath = realpath(dirname(__FILE__) . '/../../main/www');
AutoLoader::registerDirectory($appPath);
// Load YII sources (this has to go before test directory, so we can mock yii classes in tests)
AutoLoader::registerDirectory(getenv('YII_DIR'));
// Load test classes
AutoLoader::registerDirectory(realpath(dirname(__FILE__)));
// Require non-class files
require_once ($appPath . '/protected/wsapi/wsapi_base.php');
require_once ($appPath . '/protected/db/country_county.php');
require_once ($appPath . '/protected/db/owner_types.php');
require_once ($appPath . '/protected/db/registrars_for_internal_login.php');
// Setup
date_default_timezone_set('Europe/Dublin');
defined('YII_DEBUG') or define('YII_DEBUG', false);
session_save_path('/tmp');

