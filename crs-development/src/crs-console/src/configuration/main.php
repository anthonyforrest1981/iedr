<?php
/**
 * Main configuration for for Yii
 *
 * This is the main Web application configuration. Any writable CWebApplication properties can be configured here.
 * Configures Yii application parameters, including logging, database access, NASK CRS-WS Url, caching, admin email,
 * etc.
 */
// uncomment the following to define a path alias:
// Yii::setPathOfAlias('local', 'path/to/local-folder');
return array(
    'basePath' => '/opt/crs/console/www/protected',
    'runtimePath' => '/var/iedr/console',
    'name' => 'IEDR Registrar\'s Console',
    'preload' => array('log'), // preloading 'log' component
    'import' => array('application.models.*', 'application.components.*'), // autoloading model and component classes
    'modules' => array(),
    // application components
    'components' => array(
        // 'allowAutoLogin' => true = "data stored in session" = session serialized into cookie data, stored on client
        'user' => array('authTimeout' => (3600 * 6), 'allowAutoLogin' => false, 'loginUrl' => array('site/login')),
        'errorHandler' => array('errorAction' => 'site/error'), // use 'site/error' action to display errors
        'log' => array(
            'class' => 'CLogRouter',
            'routes' => array(
            // array('class'=>'CWebLogRoute',), // uncomment to show log messages on web pages
                array('class' => 'CFileLogRoute', 'levels' => 'error, warning, info, debug'))),
        'request' => array(
            'enableCsrfValidation' => true,
            'csrfCookie' => array('httpOnly' => true),
            'enableCookieValidation' => true,
            'hostInfo' => (isset($_SERVER['HTTP_X_FORWARDED_HOST']) ?
                'https://' . $_SERVER['HTTP_X_FORWARDED_HOST'] :
                (isset($_SERVER['HTTPS']) ? 'https://' : 'http://') . $_SERVER['HTTP_HOST'])),
        'session' => array('cookieParams' => array('httponly' => true)), // lower case required to work
        // not installed in RHEL5 (no memcache; no apc)
        'cache' => array('class' => 'CFileCache'),
        'clientScript' => array(
            'scriptMap' => array(
                'jquery.js' => 'jqGrid4/js/jquery-1.6.1.src.js',
                'jquery.yii.js' => 'jqGrid4/js/jquery-1.6.1.src.js'))),
    // application-level parameters that can be accessed using Yii::app()->params['paramName']
    'params' => array(
        'adminEmail' => 'webmaster@domainregistry.ie', // this is used in contact page
        'wsapi_soap_url' => 'http://localhost:8080/crs-web-services/',
        'ckdnsPath' => '/opt/crs/tools/ckdns',
        'uploader_share_dir' => '/tmp',
        'internal_network' => array(
            'exact_matches' => array(
                '83.71.193.115'), // proxy
            'regex_matches' => array(
                // internal non-routable networks
                '/^10.10./',
                '/^192.168./')),
        'bulk_operation_limit' => 250,
        'secondary_market_disclaimer_url' =>
            'https://www.iedr.ie/secondary-market-acknowledgement-and-disclaimer-ajax/',
        'recaptcha_verification_url' => 'https://www.google.com/recaptcha/api/siteverify',
        'recaptcha_site_key' => '',
        'recaptcha_secret_key' => '',
        'proxyServerUri' => '' // full URI including protocol and port
    ),
);

