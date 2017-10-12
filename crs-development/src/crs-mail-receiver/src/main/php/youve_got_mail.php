<?php

error_reporting(E_ALL);

require_once('MimeMailParser.class.php');

$VERSION = "1.0";
$HELP = <<<EOH
USAGE:
  $argv[0] <options>

DESCRIPTION:
Script reads a single email message from STDIN, saves attachments to a predefined
folder and messages java web service endpoint with email message and attachment
filenames to let java handle the email

OPTIONS:
  -u=username
    *required* username to log into web service. If none passed script will check
    env for variable \$UPLOADER_USERNAME.
  -p=password
    *required* password to log into web service. If none passed script will check
    env for variable \$UPLOADER_PASSWORD.
  -h=host
    *required* host of webservices. If none passed script will check env for
    variable \$UPLOADER_HOST.
  -d=dir
    *required* directory to which attachments should be saved. If none passed script
    will check env for variable \$UPLOADER_DIR.
  --debug
    enable logging debug info. If none passed script will check env for variable
    \$UPLOADER_DEBUG. The default value is false.
  --help
    display this help
  --version
    display version info

EOH;

function print_help() {
  global $HELP;
  fwrite(STDOUT,$HELP);
}

function print_version() {
  global $VERSION;
  fwrite(STDOUT, "Version $VERSION\n");
}

$opts = getopt('u:p:h:d:', array('help', 'version', 'debug'));

if (isset($opts['help'])) {
  print_help();
  exit(0);
}
if (isset($opts['version'])) {
  print_version();
  exit(0);
}

$DEFAULT_USERNAME = getenv('UPLOADER_USERNAME');
$DEFAULT_PASSWORD = getenv('UPLOADER_PASSWORD');
$DEFAULT_HOST = getenv('UPLOADER_HOST');
$DEFAULT_DIR = getenv('UPLOADER_DIR');
$DEFAULT_DEBUG = getenv('UPLOADER_DEBUG') && getenv('UPLOADER_DEBUG') !== 'false';

$user = isset($opts['u']) ? $opts['u'] : $DEFAULT_USERNAME;
$pass = isset($opts['p']) ? $opts['p'] : $DEFAULT_PASSWORD;
$host = isset($opts['h']) ? $opts['h'] : $DEFAULT_HOST;
$dir = isset($opts['d']) ? $opts['d'] : $DEFAULT_DIR;
$debug = isset($opts['debug']) ? true : $DEFAULT_DEBUG;

function logError($message) {
  $ext = extension_loaded('posix');
  if ($ext && posix_isatty(STDERR)) {
    fwrite(STDERR, $message . "\n");
    fflush(STDERR);
  }
  syslog(LOG_ERR, $message);
}

function logDebug($message) {
  global $debug;
  if ($debug) {
    logError($message);
  }
}

function check($varname) {
  global $$varname;
  if (empty($$varname)) {
    logError("Missing $varname");
    return true;
  }
  return false;
}

function getEmailAddress($string) {
  foreach(preg_split('/[\pZ]/u', $string) as $part) {
    $part = preg_replace('/^[\pZ]*<|>[\pZ]*$/u', '', $part); // sometime emails are presented e.g. Kuba Bogaczewicz <jb@rdprojekt.pl>
    if (filter_var($part, FILTER_VALIDATE_EMAIL)) {
      return $part;
    }
  }
  return null;
}

function getCharset($content_type) {
    $content_type = mb_decode_mimeheader($content_type);
    $result = null;
    if (!empty($content_type)) {
        $matches = null;
        preg_match('/charset="?([a-z0-9_-]+)"?;?/ui', $content_type, $matches);
        if (is_array($matches) && isset($matches[1])) {
            $result = $matches[1];
        }
    }
    return $result;
}

$errors_count = 0;
$errors_count += check('user');
$errors_count += check('pass');
$errors_count += check('host');
$errors_count += check('dir');
if ($errors_count) {
  if ($errors_count == 4) {
    print_help();
    exit(0);
  } else {
    exit(1);
  }
}

$authUser = NULL;
try {
  $AuthWS = new SoapClient($host.'/crs-web-services/CRSAuthenticationService?wsdl');
  $userWS = $AuthWS->authenticate(array(
    'username' => $user,
    'password' => $pass,
    'remoteAddress' =>'127.0.0.1',
    'pin' => 0,
  ));
  $authUser = $userWS->return;
} catch (Exception $e) {
  logError("Error while authenticating: " . $e->getMessage() . "\n");
  exit(1);
}

mb_internal_encoding("UTF-8");

$Parser = new MimeMailParser();
$Parser->setStream(STDIN);

$from = mb_decode_mimeheader($Parser->getHeader('from'));
$subject = mb_decode_mimeheader($Parser->getHeader('subject'));
$replyTo = mb_decode_mimeheader($Parser->getHeader('reply-to'));
$replyTo = empty($replyTo) ? $from : $replyTo;
$textPartType = 'text';
$text = null;
$messageHeaders = $Parser->getMessageBodyHeaders($textPartType);
if ($messageHeaders === false) {
    $textPartType = 'html';
    $messageHeaders = $Parser->getMessageBodyHeaders($textPartType);
}
if ($messageHeaders !== false) {
    $textContentType = $messageHeaders['content-type'];
    $charset = getCharset($textContentType);
    $text = $Parser->getMessageBody($textPartType);
}
if (!empty($charset) && mb_check_encoding($text, $charset)) {
    $text = mb_convert_encoding($text, "utf-8", $charset);
} else if ($text === null) {
    logDebug("Cannot find email's content, looks empty. Only saving attachments.");
    $text = '';
} else if (mb_check_encoding($text, "utf-8")) {
    logDebug("Email's content was in unknown encoding, but passes as utf-8, assuming utf-8.\n\tFrom: $from\n\tContent: $text");
} else {
    logError("Content of email is in unknown encoding, continuing with empty content.\n\tFrom: $from");
    $text = '';
}
$attachments = $Parser->getAttachments();
$replyAddress = getEmailAddress($replyTo);
$fromAddress = getEmailAddress($from);

if (empty($replyAddress)) {
  logError("Unknown return address, looked for in reply-to and from headers. Aborting");
  exit(1);
}

$files = array();
foreach ($attachments as $attachment) {
  if ($attachment->getContentDisposition() != 'attachment')
    continue;
  $content = $attachment->getContent();
  $sha = substr(sha1($content), 0, 6);
  $source = str_replace('@', '.', $fromAddress);
  $decoded_name = mb_decode_mimeheader($attachment->getFilename());
  $orig_filename = preg_replace('/[^\w\s\d._\(\)-]/u', '', pathinfo($decoded_name, PATHINFO_FILENAME));
  $uploadFilename = implode('_', array($source, $orig_filename, $sha));
  $ext = pathinfo($decoded_name, PATHINFO_EXTENSION);
  $filename = $uploadFilename . '.' . $ext;
  $i = 0;
  while(file_exists($dir . '/' . $filename)) {
    $filename = $uploadFilename . '-' . ++$i . '.' . $ext;
  }
  file_put_contents($dir . '/' . $filename, $content);
  chmod($dir . '/' . $filename, 0644);
  $uploadFilename = new stdClass();
  $uploadFilename->filesystemName = $filename;
  $uploadFilename->userFilename = $decoded_name;
  array_push($files, $uploadFilename);
}

try {
  $DocsWS = new SoapClient($host.'/crs-web-services/CRSDocumentAppService?wsdl');
  $DocsWS->handleMailUpload(array(
    'user' => $authUser,
    'replyTo' => $replyAddress,
    'content' => $text,
    'attachments' => $files));
} catch (Exception $e) {
  logError("Error while sending documents " . $e->getMessage() . "\n");
  exit(1);
}
