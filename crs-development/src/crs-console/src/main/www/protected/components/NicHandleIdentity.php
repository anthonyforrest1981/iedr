<?php
/**
 * defines NicHandleIdentity class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */
/**
 * define symbol for sessino attribute 'authenticated'
 */
define('AUTHENTICATED', 'authenticated');
/**
 * define symbol for sessino attribute 'authenticatedUser'
 */
define('AUTHENTICATED_USER_VO', 'authenticatedUser');
/**
 * define symbol for sessino attribute 'nicHandle'
 */
define('NIC_DETAILS', 'nicHandle');
/**
 * define symbol for sessino attribute 'resellerDefaults'
 */
define('RESELLER_DEFAULTS', 'resellerDefaults');
/**
 * define symbol for sessino attribute 'agreementSigned'
 */
define('AGREEMENTSIGNED', 'agreementSigned');
/**
 * define symbol for sessino attribute 'ticketEdit'
 */
define('TICKETEDIT', 'ticketEdit');
/**
 * define symbol for sessino attribute 'lastInvoiceDate'
 */
define('LAST_INVOICE_DATE', 'lastInvoiceDate');
/**
 * define symbol for session attribute 'ResselerAccountName'
 */
define('ACCOUNT_NAME', 'accountName');

/**
 * Extends Yii CUserIdentity to authenticate with CRS-WS API Authentication etc
 *
 * Authenticates via CRS-WS API, also fetches User and Account data
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class NicHandleIdentity extends CUserIdentity {
    private $errorMap = array(
        "InvalidUsernameOrPasswordException" => self::ERROR_INVALID_CREDENTIALS,
        "Login Locked" => self::ERROR_LOGIN_LOCKED,
        "NoLoginPermissionException" => self::ERROR_NO_LOGIN_PERMISSION,
        "GoogleAuthenticationException" => self::ERROR_INVALID_PIN,
        "salt cannot be empty" => self::ERROR_EMPTY_SALT);
    const ERROR_INVALID_CREDENTIALS = 1001;
    const ERROR_LOGIN_LOCKED = 1002;
    const ERROR_NO_LOGIN_PERMISSION = 1003;
    const ERROR_INVALID_PIN = 1004;
    const ERROR_EMPTY_SALT = 1005;
    const ERROR_UNKNOWN = -1;

    function __construct($superUserName, $userName, $userPass, $google_pin = null) {
        parent::__construct(Utility::mb_trim($userName), $userPass);
        $this->superUserName = Utility::mb_trim($superUserName);
        $this->google_pin = $google_pin;
    }
    /**
     * string indicating user is not an internal user
     *
     * @var string
     * @access public
     * @static
     *
     */
    const NOT_INTERNAL = 'NOT_INTERNAL';
    private $superUserName;
    /**
     * Logged-in users Nic-Handle Account ID
     *
     * @var integer
     * @access private
     */
    private $_id;
    public $google_pin;
    /**
     * number of seconds the account will remain locked
     *
     * @var unknown
     */
    public $accountLockPeriod = 0;

    /**
     * returns Logged-in users Nic-Handle Account ID
     *
     * @return integer Logged-in users Nic-Handle Account ID
     * @access public
     */
    public function getId() {
        return $this->_id;
    }

    /**
     * Attempt authentication
     *
     * clears Yii Application Identity, and all user-related data, then attempts to authenticate via the backend CRS-WS API.
     *
     * @return boolean true if authentication succeeded
     * @access public
     */
    public function authenticate() {
        $this->errorCode = self::ERROR_INVALID_CREDENTIALS;
        $this->_id = null;
        Yii::app()->user->setId(null);
        $this->clearUserData();
        $this->setState(AUTHENTICATED, false);
        if (!isset($this->username) || !mb_strlen($this->username)) {
            return false;
        }
        if (!isset($this->password) || !mb_strlen($this->password)) {
            return false;
        }
        $authenticationSuccessful = false;
        if (Utility::isInternalNetwork()) {
            $authenticatedUser = $this->CRSAuthenticationAndSwitchFunction($this->superUserName, $this->username,
                $this->password);
        } else {
            $authenticatedUser = $this->CRSAuthenticationFunction($this->username, $this->password, $this->google_pin);
        }
        if ($authenticatedUser === null) {
            $this->mapErrorMessage();
            $this->handleLoginLocked();
            return false;
        } else {
            $this->createUserSession($authenticatedUser);
            return true;
        }
    }

    public function createUserSession($authenticatedUser) {
        $this->setUserData($authenticatedUser);
        $this->setState(AUTHENTICATED, true);
        // 0 means "till the user closes the browser".
        $sessionDuration = 0;
        Yii::app()->user->login($this, $sessionDuration);
    }

    private function handleLoginLocked() {
        if ($this->errorCode == self::ERROR_LOGIN_LOCKED) {
            $exploded = mb_split(':', $this->errorMessage);
            if (count($exploded) == 4) {
                $this->accountLockPeriod = $exploded[3];
                Yii::log("LockPeriod (s): " . $this->accountLockPeriod, 'INFO', 'NicHandleIdentity::authenticate()');
            } else {
                Yii::log("Unknown LoginLockException message format: " . $this->errorMessage, 'WARN', 'NicHandleIdentity::authenticate()');
            }
        }
    }

    protected function mapErrorMessage() {
        $this->errorCode = self::ERROR_UNKNOWN;
        foreach ($this->errorMap as $key => $value) {
            if (mb_stristr($this->errorMessage, $key)) {
                $this->errorCode = $value;
            }
        }
    }

    /**
     * returns true if the current user has permission to switch to another registrar's account - for certain internal users only
     *
     * @todo this implementation is incomplete - always returns true
     * @return boolean true
     * @access public
     * @static
     *
     */
    public static function canSwitchAccounts() {
        return true;
    }

    /**
     * switch to another registrar's account
     *
     * This function is called as an alternative to authentication, in the special case of user-switching.
     *
     * The switch is perform by calling CRS API methods and setting new user token as the current token.
     */
    public function switchToAccount() {
        $auvo = null;
        $switched = false;
        $actual = Yii::app()->user->authenticatedUser;
        CRSAuthenticationService_service::isUserSwitched($switched, $this->errorMessage, $actual);
        if ($switched) {
            CRSAuthenticationService_service::unswitch($actual, $this->errorMessage, $actual);
            if (count($this->errorMessage) != 0) {
                Yii::log('UN SWITCH ERROR= ' . print_r($this->errorMessage, true));
                // return false;
            }
        }
        Yii::log('name= ' . print_r($this->getName(), true));
        CRSAuthenticationService_service::switchUser($auvo, $this->errorMessage, $actual, $this->getName());
        if (count($this->errorMessage) != 0) {
            Yii::log('SWITCH ERROR= ' . print_r($this->errorMessage, true));
            // return false;
        }
        $this->clearUserData();
        $this->createUserSession($auvo);
        Yii::log('SWITCH USER= ' . print_r($auvo->username, true));
        return true;
    }

    /**
     * Authenticate login credentials via the CRS-WS API.
     */
    protected function CRSAuthenticationFunction($nichandle, $nicpass, $pin) {
        $result = null; // CRSDomainAppService_authenticatedUserVO
        $ipAddress = Utility::getClientIP();
        Yii::log($ipAddress, 'DEBUG', 'CRSAuthenticationFunction');
        CRSAuthenticationService_service::authenticate($result, $this->errorMessage, $nichandle, $nicpass, $ipAddress, $pin);
        if ($result != null && count($this->errorMessage) == 0) {
            Yii::log('Authenticate result = ' . print_r($result, true));
            return $result;
        } else {
            Yii::log('Authenticate error = ' . print_r($this->errorMessage, true));
        }
        return null;
    }

    protected function CRSAuthenticationAndSwitchFunction($superUserName, $userName, $userPass) {
        $result = null;
        CRSAuthenticationService_service::authenticateAndSwitchUser($result, $this->errorMessage, $superUserName,
            $userName, $userPass, Utility::getClientIP());
        if ($result != null && count($this->errorMessage) == 0) {
            return $result;
        } else {
            Yii::log('CRSAuthenticationAndSwitchFunction error = ' . print_r($this->errorMessage, true));
        }
        return null;
    }

    /**
     * Clears user-specific data
     *
     * @return void
     * @access protected
     */
    protected function clearUserData() {
        // these values are accessed elsewhere like '$x = Yii::app()->user->resellerDefaults;'
        foreach (array(AGREEMENTSIGNED, AUTHENTICATED_USER_VO, LAST_INVOICE_DATE,NIC_DETAILS, RESELLER_DEFAULTS,
                TICKETEDIT, ACCOUNT_NAME) as $X) {
            $this->clearState($X);
        }
    }

    /**
     * reads and stores user-specific data
     *
     * @param authenticatedUserVO $auvo
     *            contains CRS-WS API username and authentication token
     * @return void
     * @access public
     */
    public function setUserData($auvo) {
        $this->setState(AUTHENTICATED_USER_VO, $auvo);
        $nicdetails = $this->getNICDetails($auvo);
        if ($nicdetails != null) {
            $this->_id = $nicdetails->accountId;
            $this->setState(NIC_DETAILS, $nicdetails);
        }
        $this->setState(RESELLER_DEFAULTS, $this->getResellerDefaults($auvo));
        $this->getResellerAccountDetails($auvo, $this->_id);
        Yii::log('set (LAST_INVOICE_DATE) ' . LAST_INVOICE_DATE . '=', 'debug', 'NicHandleIdentity::getNICDetails()');
        Yii::log('set (reseller name) ' . ACCOUNT_NAME . '=' . $this->accountName, 'debug', 'NicHandleIdentity::getNICDetails()');
    }

    /**
     * gets reseller defaults
     *
     * @param authenticatedUserVO $user
     *            authentication token
     * @return CRSNicHandleAppService_resellerDefaultsVO reseller defaults
     * @access protected
     */
    protected function getResellerDefaults($auvo) {
        $nichandle = $auvo->username;
        $result = null; // CRSNicHandleAppService_resellerDefaultsVO
        $errs = null;
        CRSNicHandleAppService_service::getDefaults($result, $errs, $auvo, $nichandle);
        Yii::log(print_r($result, true), 'debug', 'NicHandleIdentity::getResellerDefaults()');
        return $result;
    }

    /**
     * gets nic-handle details
     *
     * @param authenticatedUserVO $auvo
     *            authentication token
     * @param string $nic
     *            nic handle
     * @return CRSNicHandleAppService_nicHandleVO nichandle data
     * @access protected
     */
    protected function getNICDetails($auvo, $nic = null) {
        $result = null; // CRSNicHandleAppService_nicHandleVO
        $errs = null;
        Yii::log('auvo= ' . print_r($auvo, true));
        Yii::log('nic= ' . print_r($nic, true));
        CRSNicHandleAppService_service::get($result, $errs, $auvo, $nic == null ? $auvo->username : $nic);
        if ($result != null) {
            if (count($errs) == 0) {
                Yii::log("CRSNicHandleAppService_service::get ok " . print_r($errs, true));
            } else {
                Yii::log("Errors in CRSNicHandleAppService_service::get " . print_r($errs, true));
            }
        } else {
            Yii::log("CRSNicHandleAppService_service::get is null");
        }
        Yii::log(print_r($result, true), 'debug', 'NicHandleIdentity::getNICDetails()');
        return $result;
    }

    /**
     * gets, via CRS-WS API, some account details for given account ID.
     *
     * Details fetched :
     * - agreementSigned: 0 or 1
     * - ticketEdit: 0 or 1
     *
     * @param integer $accid
     *            Account ID
     * @return void
     * @access protected
     */
    protected function getResellerAccountDetails($auvo, $accid) {
        $result = null;
        $errs = null;
        CRSResellerAppService_service::get($result, $errs, $auvo, $accid);
        if ($errs == null) {
            Yii::log(print_r($result, true), 'debug', 'NicHandleIdentity::getResellerAccount()');
            $this->setState(AGREEMENTSIGNED, $result->agreementSigned);
            $this->setState(TICKETEDIT, $result->ticketEdit);
            $this->setState(ACCOUNT_NAME, $result->name);
        } else {
            $this->setState(TICKETEDIT, false);
            $this->setState(AGREEMENTSIGNED, false);
        }
    }

    /**
     * gets last invoice date from CRS-WS API
     *
     * CRS-WS currently returns the last calendar day of the previous calendar month
     *
     * @param authenticatedUserVO $auvo
     *            authentication token
     * @return string date in string YYYY-MM-DD format
     * @access protected
     */
    protected function getLastInvoiceDate($auvo) {
        $result = null;
        $errs = null;
        /* DEPRECATED */
        // CRSPaymentAppService_service::getLastInvoiceDate($result,$errs, $auvo);
        if ($errs == null) {
            // Yii::log(print_r($result,true), 'debug', 'NicHandleIdentity::getLastInvoiceDate()');
            return parseXmlDate($result);
        }
        return null;
    }

    public function getAccountName() {
        return $this->getState(ACCOUNT_NAME);
    }

}
