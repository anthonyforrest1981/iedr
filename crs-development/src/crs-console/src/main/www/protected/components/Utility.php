<?php

class Utility {

    /**
     * round a floating-point number to two decimal places according to IEDR business rules 'half even'
     *
     * Dermot : "below half a penny, you round down. Above half a penny, you round up. At exactly half a penny, you round to the nearest even amount so 76.5p becomes 76p and 77.5p becomes 78, etc."
     *
     * @param unknown $f
     *            decimal value
     * @return integer rounded number
     * @access public
     * @static
     *
     */
    static public function roundAccountingAmount($f) {
        return round($f, 2, PHP_ROUND_HALF_EVEN);
    }

    /**
     * render a currency amount in html format
     *
     * prepends currency symbol, adds thousands separator
     *
     * @param unknown $m
     *            currency amount
     * @param string $curr
     *            currency, defaults to 'EUR'
     * @return mixed html representation of amount
     * @access public
     * @static
     *
     */
    static public function currencyAmount($m, $curr = 'EUR') {
        if ($m == null)
            return 0;
            // use : Utility::currencyAmount($x);
        $csym = '&euro;';
        switch ($curr) {
            case 'USD':
                $csym = '$';
                break;
            case 'GBP':
                $csym = '£';
                break;
            default:
            case 'EUR':
                break;
        }
        return $csym . '&nbsp;' . number_format($m, /*decimals*/
            2, /*dec_point*/
            '.', /*thousands*/
            ',');
    }

    /**
     * returns string representing a date, in YYYY-MM-DD format, being the last calendar day of the current month
     *
     * @return string last day of current month
     * @access public
     * @static
     *
     */
    static public function lastDayOfCurrentMonth() {
        return date('Y-m-t', time());
    }

    /**
     * returns a string being the payment-due-date of the current invoice period
     *
     * currently defined as the last calendar day of the present month
     *
     * @return string payment-due-date...
     * @access public
     * @static
     *
     */
    static public function paymentDueByDate() {
        return Utility::lastDayOfCurrentMonth();
    }

    static public function isTransferPossible($domainName) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        $errs = '';
        CRSCommonAppService_service::isTransferPossible($result, $errs, $user, $domainName);
        if ($result == null || count($errs)) {
            Yii::log('Transfer not possible');
            return false;
        }
        return true;
    }

    /**
     * find whether a domain is available for registration, checking if it exists and has no pending creation ticket
     *
     * @param string $domainName
     *            domain name
     * @return boolean true if the domain can be created
     * @access public
     * @static
     *
     */
    static public function domainIsAvailable($domainName) {
        $errors = '';
        $result = new CRSDomainAppService_domainAvailabilityVO();
        CRSDomainAppService_service::checkAvailability($result, $errors, Yii::app()->user->authenticatedUser, $domainName);
        if ($result != null && count($errors) == 0) {
            return $result->available;
        } else {
            Yii::log('check domain availability failed ' . print_r($errors, true));
            return false;
        }
    }

    /**
     * returns array of IEDR Usernames, for local login widget
     *
     * @return array array of login usernames
     * @access public
     * @static
     *
     */
    static public function getIEDRUsers() {
        $arrayResult = array();
        $users = array();
        CRSPermissionsAppService_service::getInternalUsers($result, $errs);
        if ($result != null) {
            if (count($errs) == 0) {
                if (is_array($result)) {
                    foreach ($result as $user) {
                        $users[] = $user;
                    }
                } else if (is_string($result)) {
                    $users[] = $result;
                }
            }
        }
        if (count($users) == 0) {
            Yii::log('IEDR USERS ARE EMPTY');
        }
        $arrayResult = array(NicHandleIdentity::NOT_INTERNAL => '');
        foreach ($users as $u) {
            $arrayResult[$u] = $u;
        }
        Yii::log('IEDR USERS ' . print_r($arrayResult, true));
        return $arrayResult;
    }

    /**
     * returns boolean whether current request matches rules for determining of the access is from an internal network
     *
     * @param unknown $ipAddr
     *            IP address
     * @return boolean whether current request matches rules for determining of the access is from an internal network
     * @access public
     * @static
     *
     */
    static public function isInternalNetwork($ipAddr = null) {
        if ($ipAddr == null)
            $ipAddr = $_SERVER['REMOTE_ADDR'];
        $internalNet = Yii::app()->params['internal_network'];
        foreach ($internalNet['exact_matches'] as $exactIp)
            if ($ipAddr == $exactIp)
                return true;
        foreach ($internalNet['regex_matches'] as $IpRegex)
            if (preg_match($IpRegex . 'u', $ipAddr) == 1)
                return true;
        return false;
    }
    // Prints the Invoice Summary table for the View History->Invoices reports.
    /**
     * Prints to the output stream the Invoice Summary table for the View History->Invoices reports.
     *
     * @param mixed $model
     *            data model
     * @return void
     * @access public
     * @static
     *
     */
    static public function hasProperty($object, $property) {
        if (property_exists($object, $property)) {
            Yii::log('Has property ' . $property . '.');
            return true;
        } else {
            Yii::log('Has not property ' . $property . '.');
            return false;
        }
    }

    static public function isRegistrar() {
        $permission = Utility::getPermission();
        return ($permission == UserPermissionLevel::REGISTRAR);
    }

    static public function isSuperRegistrar() {
        $permission = Utility::getPermission();
        return ($permission == UserPermissionLevel::SUPER_REGISTRAR);
    }

    static public function isDirect() {
        $permission = Utility::getPermission();
        return ($permission == UserPermissionLevel::DIRECT);
    }

    static public function isLoggedIn() {
        return isset(Yii::app()->user->authenticatedUser);
    }

    static public function isLoggedInAs($name) {
        $lowercaseLoggedin = mb_strtolower(Yii::app()->user->authenticatedUser->username);
        $lowercaseName = mb_strtolower($name);
        return strcmp($lowercaseLoggedin, $lowercaseName) === 0;
    }

    /**
     *
     * @param ViewDomainModel $domain
     */
    static public function isTechContact($domain) {
        return Utility::isLoggedInAs($domain->domain_techContacts_nicHandle_orig);
    }

    /**
     *
     * @param ViewDomainModel $domain
     */
    static public function isAdminContact($domain) {
        return Utility::isLoggedInAs($domain->domain_adminContacts_0_nicHandle_orig) || Utility::isLoggedInAs($domain->domain_adminContacts_1_nicHandle_orig);
    }

    /**
     *
     * @param ViewDomainModel $domain
     */
    static public function isBillingContact($domain) {
        return Utility::isLoggedInAs($domain->domain_billingContacts_nicHandle);
    }

    static public function isAdminOrTech() {
        $permission = Utility::getPermission();
        return $permission == UserPermissionLevel::TAC;
    }

    static public function getPermission() {
        $result = null;
        $errs = array();
        if (isset(Yii::app()->user->authenticatedUser)) {
            $user = Yii::app()->user->authenticatedUser;
            CRSPermissionsAppService_service::getUserLevel($result, $errs, $user);
            if ($result != null) {
                return $result;
            }
        }
        return UserPermissionLevel::UNDEFINED;
    }

    static public function getProductSummaries($type) {
        $pricelist = get_reg_prices();
        return self::getProductSummariesFromPriceList($type, $pricelist);
    }

    static public function getProductSummariesFromPriceList($type, $pricelist) {
        $summaries = array();
        $simplePricelist = self::getSimplePriceList($type, $pricelist);
        foreach ($simplePricelist as $id => $prod) {
            $summary = $prod['duration'] . ' Year €' . $prod['priceWithVat'];
            if ($prod['vatValue'] > 0) {
                $summary .= ' Inc Vat.';
            }
            $summaries[$id] = $summary;
        }
        return $summaries;
    }

    static public function getSimplePriceList($type, $pricelist) {
        $simplePricelist = array();
        foreach ($pricelist[$type] as $id => $prod) {
            $priceWithVat = self::roundAccountingAmount($prod->price + $prod->vatValue);
            $simplePricelist[$id] = array(
                'duration' => $prod->duration,
                'price' => $prod->price,
                'vatValue' => $prod->vatValue,
                'priceWithVat' => $priceWithVat
            );
        }
        return $simplePricelist;
    }

    static public function getRequestPrice($operationType) {
        $result = null;
        $errors = null;
        CRSPaymentAppService_service::getRequestPrice($result, $errors, Yii::app()->user->authenticatedUser, $operationType);
        if (count($errors) == 0) {
            return $result;
        } else {
            Yii::log('getRequestPrice error: ' . print_r($errors, true));
            return new CRSPaymentAppService_paymentVO();
        }
    }

    static public function createSelect($htmlId, $array) {
        if (count($array)) {
            $select = '<select id="period_' . $htmlId . '" disabled="disabled" onchange="periodChanged(\'' . $htmlId . '\');" >';
            foreach ($array as $key => $value) {
                $select .= '<option value=' . $key . '>' . $value . '</option>';
            }
            $select .= '</select>';
            return $select;
        }
        return ' ';
    }

    static private function extensionIsLoaded() {
        if (!extension_loaded('sockets')) {
            Yii::log('Extension sockets not loaded!');
            return false;
        }
        return true;
    }

    static private function isValidPort($port) {
        if (is_numeric($port))
            return true;
        Yii::log('Port is not numeric.');
        return false;
    }

    static private function isValidWsapiSoapUrl() {
        // Valid wsapi url string ('wsapi_soap_url'=>'http://localhost:8080/crs-web-services-0.10.0-Sprint16/')
        $url = Yii::app()->params['wsapi_soap_url'];
        if (!isset($url)) {
            Yii::log('Wsapi soap url:' . $url . ' is not set.');
            return false;
        }
        $whitoutProtocol = mb_split('//', $url);
        if (count($whitoutProtocol) != 2) {
            Yii::log('Could not get address and service name form wsapi soap url:' . print_r($url, true));
            return false;
        }
        $whitoutSubdirs = mb_split('/', $whitoutProtocol[1]);
        if (count($whitoutSubdirs) == 0) {
            Yii::log('Could not get address and service name form wsapi soap url:' . print_r($url, true));
            return false;
        }
        $addressAndPort = mb_split(':', $whitoutSubdirs[0]);
        if (count($addressAndPort) != 2) {
            Yii::log('Could not get address and port form wsapi soap url:' . print_r($url, true));
            return false;
        }
        return true;
    }

    static private function getAddressAndPortFromWsapiSoapUrl(&$address, &$port) {
        $url = Yii::app()->params['wsapi_soap_url'];
        $whitoutProtocol = mb_split('//', $url);
        if (count($whitoutProtocol) != 2) {
            Yii::log('Could not get address without protocol from wsapi soap url : ' . print_r($url, true));
            return false;
        }
        $whitoutSubdirs = mb_split('/', $whitoutProtocol[1]);
        if (count($whitoutSubdirs) != 3) {
            Yii::log('Could not get subdirs from : ' . print_r($whitoutProtocol[1], true));
            return false;
        }
        $addressAndPort = mb_split(':', $whitoutSubdirs[0]);
        if (count($addressAndPort) != 2) {
            Yii::log('Could not get address and port from : ' . print_r($whitoutSubdirs[0], true));
            return false;
        }
        $address = $addressAndPort[0];
        $port = $addressAndPort[1];
        return true;
    }

    static public function isTomcatExist() {
        if (!Utility::extensionIsLoaded())
            return false;
        if (!Utility::isValidWsapiSoapUrl())
            return false;
        if (!Utility::getAddressAndPortFromWsapiSoapUrl($address, $port))
            return false;
        if (!Utility::isValidPort($port))
            return false;
        $socket = @socket_create(AF_INET, SOCK_STREAM, 0);
        if ($socket) {
            $isConnect = @socket_connect($socket, $address, $port);
            if ($isConnect === false) {
                Yii::log('Could not connect to Tomcat Server on address ' . $address . ':' . $port . '');
                return false;
            } else {
                @socket_close($socket);
                Yii::log('Connect to Tomcat successful on address ' . $address . ':' . $port . '.');
                return true;
            }
        }
        Yii::log('Could not create socket.');
        return false;
    }

    static public function getNicHandleStatuses($only_active) {
        $statuses = array("Active" => true,"New" => true,"Renew" => true,"Deleted" => !$only_active,
            "Suspended" => !$only_active,"None" => false);
        return $statuses;
    }

    static public function getRequestPaymentTypes() {
        return self::getBasicPaymentTypes();
    }

    static public function getDomainPaymentTypes() {
        $paymentType = self::getBasicPaymentTypes();
        if (!Utility::isDirect()) {
            $paymentType['ADP_AUTORENEW'] = 'Deposit Account with Autorenew enabled';
        }
        return $paymentType;
    }

    static private function getBasicPaymentTypes() {
        $paymentType = array();
        $paymentType['CC'] = 'Credit Card';
        if (!Utility::isDirect()) {
            $paymentType['ADP'] = 'Deposit Account';
        }
        return $paymentType;
    }

    static public function getEmailInvoiceTypes() {
        return array(CRSNicHandleAppService_emailInvoiceFormat::_XML => 'XML',
            CRSNicHandleAppService_emailInvoiceFormat::_PDF => 'PDF',
            CRSNicHandleAppService_emailInvoiceFormat::_BOTH => 'BOTH')
        // CRSNicHandleAppService_emailInvoiceFormat::_NONE => 'NONE',
        ;
    }

    static private function getRestrictedFieldNames() {
        return array('cardholder','creditcardno','cardtype','exp_month','exp_year','cvn','new_password',
            'repeat_new_password');
    }

    static public function hideRestrictedFieldsInModel($modelArray) {
        $restrictedFields = Utility::getRestrictedFieldNames();
        if (is_array($modelArray)) {
            foreach ($restrictedFields as $field) {
                if (isset($modelArray[$field])) {
                    $modelArray[$field] = '######';
                }
            }
        }
        return $modelArray;
    }

    static public function hideRestrictedFields($post) {
        if (is_array($post)) {
            foreach ($post as $modelName => $value) {
                $post[$modelName] = Utility::hideRestrictedFieldsInModel($post[$modelName]);
            }
        }
        return $post;
    }

    static public function getApplicationConfiguration() {
        static $result = null;
        if ($result != null)
            return $result;
        $errs = '';
        CRSCommonAppService_service::getApplicationConfiguration($result, $errs);
        if (count($errs)) {
            $result = null;
            Yii::log(print_r($errs));
            throw new CException('Cannot get application configuration');
        }
        return $result;
    }

    static public function getTicketExpirationDate($creationDate) {
        $period = Utility::getApplicationConfiguration()->ticketExpirationPeriod;
        return date('Y-m-d', strtotime($creationDate . ' + ' . $period . ' days'));
    }

    static public function getBuyRequestExpirationDate($request) {
        if ($request->status == CRSSecondaryMarketAppService_buyRequestStatus::_PASSED) {
            return Utility::getBuyRequestAuthcodeExpirationDate($request->authcodeCreationDate);
        } else {
            return Utility::getTicketExpirationDate($request->creationDate);
        }
    }

    static public function getBuyRequestAuthcodeExpirationDate($authcodeCreationDate) {
        $period = Utility::getApplicationConfiguration()->secondaryMarketAuthcodeExpirationPeriod;
        return date('Y-m-d', strtotime($authcodeCreationDate . ' + ' . $period . ' days'));
    }

    static public function getSellRequestCompletionDate($creationDate) {
        $period = Utility::getApplicationConfiguration()->secondaryMarketCountdownPeriod;
        return date('Y-m-d', strtotime($creationDate . ' + ' . $period . ' days'));
    }

    static public function writeActionToSession($returnAction) {
        Yii::app()->session['returnAction'] = $returnAction;
    }

    static public function printFlashSuccess(&$message) {
        Utility::printDiv($message, "flash-success");
    }

    static public function printFlashNotice(&$message) {
        Utility::printDiv($message, "flash-notice");
    }

    static public function printFlashError(&$message) {
        Utility::printDiv($message, "flash-error");
    }

    static private function printDiv(&$message, $divClass) {
        if (!empty($message)) {
            echo "<div class =\"$divClass\">";
            if (is_array($message)) {
                echo implode("<br>", $message);
            } else {
                echo $message;
            }
            echo "</div>";
            $message = "";
        }
    }

    static public function isGlueRequired($domainNames, $dnsHostname) {
        if (!is_array($domainNames)) {
            $domainNames = array($domainNames);
        }
        $asciiDnsHostname = idn_to_ascii(self::getNormalizedHostname($dnsHostname));
        if ($asciiDnsHostname === false) {
            return false;
        }
        foreach ($domainNames as $dName) {
            $asciiDomainname = idn_to_ascii(self::getNormalizedHostname($dName));
            if ($asciiDomainname === false) {
                continue;
            }
            if ($asciiDnsHostname === $asciiDomainname) {
                return true;
            }
            if (Utility::mb_endsWith(mb_strtolower($asciiDnsHostname), '.' . mb_strtolower($asciiDomainname))) {
                return true;
            }
        }
        return false;
    }

    static public function mb_trim($str, $characters = '', $onlySpecified = false) {
        if (!$onlySpecified) {
            $characters .= '\s\pZ';
        }
        return self::mb_rltrim($str, $characters, $characters);
    }

    static public function mb_rtrim($str, $characters = '\s\pZ') {
        return self::mb_rltrim($str, '', $characters);
    }

    static private function mb_rltrim($str, $startingCharacters, $endingCharacters) {
        if (!$str) return $str;
        $rules = array();
        if (!empty($startingCharacters)) {
            $rules[] = "^[$startingCharacters]+";
        }
        if (!empty($endingCharacters)) {
            $rules[] = "[$endingCharacters]+$";
        }
        if (empty($rules)) return $str;
        return preg_replace('/' . join('|', $rules). '/u', '', $str);
    }

    static public function mb_str_split($str) {
        if (!is_string($str))
            return array();
        return preg_split('/(?<!^)(?!$)/u', $str);
    }

    static public function mb_endsWith($haystack, $needle) {
        // search forward starting from end minus needle length characters
        return $needle === "" || (($temp = mb_strlen($haystack) - mb_strlen($needle)) >= 0 && mb_strpos($haystack, $needle, $temp) !== FALSE);
    }

    public static function getNormalizedHostname($hostname) {
        $length = mb_strlen($hostname);
        if (mb_substr($hostname, $length - 1) === '.') {
            $hostname = mb_substr($hostname, 0, $length - 1);
        }
        $hostname = mb_strtolower($hostname);

        return $hostname;
    }

    static public function printListOfOptions($list, $selected_id = null) {
        $returnval = '';
        foreach ($list as $id => $name) {
            $returnval .= '<option ';
            if ($id == $selected_id) {
                $returnval .= 'selected="selected" ';
            }
            $returnval .= 'value="' . $id . '">' . $name . '</option>';
        }
        echo $returnval;
    }

    /**
     * @return IP address of the user, even if it went through a reverse proxy.
     */
    static public function getClientIP() {
        $ipAddress = NULL;
        if (isset($_SERVER['HTTP_X_FORWARDED_FOR'])) {
            $addresses = explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']);
            $ipAddress = $addresses[0];
        } else {
            $ipAddress = $_SERVER['REMOTE_ADDR'];
        }
        return $ipAddress;
    }
}
