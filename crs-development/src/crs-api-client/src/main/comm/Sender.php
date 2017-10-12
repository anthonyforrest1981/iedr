<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("SenderException.php");

class Sender {

  static private $session;

  function Sender() {
    $this->init();
  }

  function init() {
    $config = ConfigFactory::getConfig();
    $url = $config->getAttribute("url");
    $cookieFile = $config->getAttribute("cookie_file");

    if (!self::$session) {
      self::$session = curl_init();
    }
    #    curl_setopt(self::$session,    CURLOPT_VERBOSE, 1);
    curl_setopt(self::$session, CURLOPT_URL, $url);
    curl_setopt(self::$session, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt(self::$session, CURLOPT_COOKIEJAR, $cookieFile);
    curl_setopt(self::$session, CURLOPT_COOKIEFILE, $cookieFile);
    curl_setopt(self::$session, CURLOPT_ENCODING, "");
    curl_setopt(self::$session, CURLOPT_HEADER, 0);
    curl_setopt(self::$session, CURLOPT_POST, 1);

    $ssl = $config->getAttribute("ssl_connection");
    if ($ssl) {
      $certFile = $config->getAttribute("ssl_cert");
      $caCertFile = $config->getAttribute("ssl_cacert");
      $strongVerify = $config->getAttribute("ssl_strong_verify");
      curl_setopt(self::$session, CURLOPT_SSLCERT, $certFile);
      if ($strongVerify) {
        curl_setopt(self::$session, CURLOPT_CAINFO, $caCertFile);
        curl_setopt(self::$session, CURLOPT_SSL_VERIFYHOST, 2);
        curl_setopt(self::$session, CURLOPT_SSL_VERIFYPEER, TRUE);
      } else {
        curl_setopt(self::$session, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt(self::$session, CURLOPT_SSL_VERIFYPEER, 0);
      }
    }

  }

  function send($command) {
    if (!self::$session) {
      $this->init();
    }
    $response = FALSE;
    $retries = 10;
    while (--$retries && !$response) {
      curl_setopt(self::$session, CURLOPT_POSTFIELDS, array('content' => $command));
      $response = curl_exec(self::$session);
    }
    if (!$response) {
      $error = curl_error(self::$session);
      echo "\nERROR: <" . $error . ">\n";
      throw new SenderException($error);
    }

    return $response;
  }

}
