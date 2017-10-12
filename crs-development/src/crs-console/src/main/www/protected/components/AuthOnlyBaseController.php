<?php

/**
 * file which defines AuthOnlyBaseController class
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
 * Base Controller for actions and views which must have authenticated sessions in order to be accesses
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 * @see References to other sections (if any)...
 */
class AuthOnlyBaseController extends Controller {

    /**
     * String for error display ; use for backend errors during request cycle
     *
     * @var string
     * @access protected
     */
    /**
     * Yii access control mechanism
     *
     * @return array
     * @access public
     * @see http://www.yiiframework.com/doc/api/1.1/CController#filters-detail
     */
    public function filters() {
        return array('accessControl');
    }

    /**
     * Yii Access control rules
     *
     * Forbid all non-(GET/POST) requests ; return 'Access Denied' for unauthenticated sessions ; permit GET/POST for authenticated sessions only.
     *
     * @return array of array
     * @access public
     * @see http://www.yiiframework.com/doc/api/1.1/CController#accessRules-detail
     */
    public function accessRules() {
        $qry = $_SERVER['REQUEST_METHOD'] . ' ' . $_SERVER['REQUEST_URI'] . ' : ' . $_SERVER['QUERY_STRING'];
        $copy = Utility::hideRestrictedFields($_POST);
        $str = BR . '##########################################################################################################' . BR . $qry . BR;
        self::log($str . print_r($_GET, true) . BR . print_r($copy, true), 'debug', 'AuthOnlyBaseController::accessRules(Get/Post)');
        $result = null;
        $errs = array();
        if (!isset(Yii::app()->user->authenticatedUser)) {
            Yii::log('WITHOUT LOGIN');
            $this->redirect('index.php?r=site/login');
        }
        CRSPermissionsAppService_service::getUserLevel($result, $errs, Yii::app()->user->authenticatedUser);
        if ($result != null) {
            if ($result === UserPermissionLevel::UNDEFINED) {
                Yii::log('DONT HAVE ACCESS');
                $this->redirectToHomePage();
            }
        } else {
            Yii::log('DONT HAVE ACCESS: ' . $errs);
            $this->redirectToHomePage();
        }
        $result = null;
        CRSAuthenticationService_service::isUserSwitched($result, $this->backend_errors, Yii::app()->user->authenticatedUser);
        if (isset($this->backend_errors)) {
            Yii::log('BACKEDND SESSION= ' . print_r($this->backend_errors, true));
            $this->redirectToHomePage();
        } else {
            return array(
                array('deny','users' => array('*'), // all
                    'verbs' => array('HEAD','PUT','DELETE','TRACE','CONNECT','OPTIONS'),'message' => 'Not Permitted.'),
                array('deny','users' => array('?'), // not authenticated
'verbs' => array('GET','POST'),'message' => 'Access Denied, Not logged in'),
                array('allow','users' => array('@'), // authenticated
'verbs' => array('GET','POST')));
        }
    }

    /**
     * Deserialises an object or array
     *
     * Deserialises an object or array, detecting (with mini header strings like 'W?W=')
     * different decoding steps:
     * <ul>
     * <li> : urldecode
     * <li> 'WBW' : base64
     * <li> 'WKW' : ({@link Yii::app()->getSecurityManager()->decrypt http://www.yiiframework.com/doc/api/1.1/CSecurityManager#decrypt})
     * <li> 'WZW' : gzuncompress
     * <li> 'WSW' : unserialize
     * </ul>
     *
     *
     * @param string $str
     *            output of @safeSerializeObj
     * @return object an instantiated object instance, or null
     * @access public
     * @static
     *
     */
    public static function safeDeserializeObj($str) {
        // whether the data arrives url-encoded or not is unpredictable : test and proceed with caution ...
        // test : php -r "print_r(unserialize(mb_substr(gzuncompress(mb_substr(base64_decode(mb_substr(urldecode(mb_substr('$STR',4)),4)),4)),48)));" ; echo
        $obj = null;
        $data = $str;
        self::log('INSTR=' . $data, 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        $data = urldecode($data);
        if (mb_strpos($data, 'WUW=') === 0) {
            $data = urldecode(mb_substr($data, 4));
            // self::log('URLDECOD2={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        }
        if (mb_strpos($data, 'WBW=') === 0) {
            $data = base64_decode(mb_substr($data, 4));
            // self::log('64DECOD(UU)={'.mb_strlen($data).'}='.urlencode($data), 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        }
        if (mb_strpos($data, 'WKW=') === 0) {
            $data = Yii::app()->getSecurityManager()->decrypt(mb_substr($data, 4));
            // self::log('UNHASH={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        }
        if (extension_loaded('zlib') and mb_strpos($data, 'WZW=') === 0) {
            $data = gzuncompress(mb_substr($data, 4));
            // self::log('UNZIP={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        }
        if (mb_strpos($data, 'WAW=') === 0) {
            $data = base64_decode(mb_substr($data, 4));
            // self::log('64DECOD(UU)A={'.mb_strlen($data).'}='.urlencode($data), 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        }
        if (mb_strpos($data, 'WSW=') === 0) {
            $obj = unserialize(mb_substr($data, 4));
            // self::log('DESER={'.mb_strlen($data).'}='.print_r($obj,true), 'debug', 'AuthOnlyBaseController::safeDeserializeObj()');
        }
        return $obj;
    }

    /**
     * Serializes an object or array
     *
     * @param mixed $obj
     *            object instance, or array, to serialize
     * @return string serialized input
     * @access public
     * @throws CException error thrown if the input is null
     * @static
     *
     * @see safeDeserializeObj
     */
    public static function safeSerializeObj($obj, $url = true) {
        if ($obj instanceof CModel)
            $obj->unSetValidators(); // avoid serializing unneeded data
        if (!is_object($obj) and !is_array($obj))
            throw new CException('attempted to serialize an non-object or array');
            // self::log(print_r($obj, true));
        $data = 'WSW=' . serialize($obj);
        // self::log('SER={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeSerializeObj()');
        // must make sure, that zlib will get valid characters in the input
        if ($url) {
            $data = 'WAW=' . base64_encode($data);
            // self::log('B64A={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeSerializeObj()');
            if (extension_loaded('zlib'))
                $data = 'WZW=' . gzcompress($data); // zlib unreliable: often errors when uncompressing (?!?!)
            $data = 'WKW=' . Yii::app()->getSecurityManager()->encrypt($data);
            // self::log('HASH={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeSerializeObj()');
            $data = 'WBW=' . base64_encode($data);
            // self::log('B64={'.mb_strlen($data).'}='.$data, 'debug', 'AuthOnlyBaseController::safeSerializeObj()');
            return urlencode($data);
        } else {
            return $data;
        }
    }

    /**
     * Serializes an object or array and stores it in the session.
     *
     * @param mixed $obj
     *            object instance, or array, to serialize
     * @param
     *            string the name of the session variable to use to store the serialized value
     * @access public
     * @throws CException error thrown if the input is null
     * @static
     *
     * @see safeDeserializeObjFromSession
     * @see safeSerializeObj
     */
    public static function safeSerializeAndStoreObjInSession($obj, $varName = 'rap') {
        self::log("Serializing into session var:" . $varName, 'debug', 'AuthOnlyBaseController::safeSerializeAndStoreObjInSession()');
        $val = self::safeSerializeObj($obj, false);
        Yii::app()->user->setState($varName, $val);
    }

    /**
     *
     * @param string $varName
     *            the variable the serialized object is stored in the session
     * @see safeDeserializeObj
     * @see safeSerializeAndStoreObjInSession
     * @return object an instantiated object instance, or null
     * @static
     *
     */
    public static function safeDeserializeObjFromFromSession($varName = 'rap') {
        $serialized = Yii::app()->user->getState($varName);
        // Yii::app()->user->setState(null );
        self::log("Deserializing session var:" . $varName, 'debug', 'AuthOnlyBaseController::safeDeserializeObjFromSession()');
        return self::safeDeserializeObj($serialized);
    }

    public static function isSavedInSession($varName) {
        return Yii::app()->user->hasState($varName);
    }

    /**
     * Convenience function to return html string for a button that opens the given URL
     *
     * @param string $url
     *            url
     * @return string html button in a form
     * @access public
     * @static
     *
     */
    public static function returnToUrlButton($url) {
        $url = self::whitelistReturnUrl($url);
        return CHtml::link('Return', $url, array('class' => 'button'));
    }

    /**
     * gets and deserialises a model from http request parameters
     *
     * @return mixed model instance
     * @access public
     * @static
     *
     */
    public static function getModelFromQuery() {
        if (isset($_GET['model']))
            return self::safeDeserializeObj($_GET['model']);
        else if (isset($_POST['model']))
            return self::safeDeserializeObj($_POST['model']);
        else {
            // for some inexplicable reason, the 'model' param is in QUERY_STRING but sometimes missing from '_GET'
            $qs = $_SERVER['QUERY_STRING'];
            $qa = mb_split('&', $qs);
            $qm = null;
            foreach ($qa as $qad)
                if (mb_strpos($qad, 'model=') === 0) {
                    $qm = mb_substr($qad, mb_strlen('model='));
                    break;
                }
            return self::safeDeserializeObj($qm);
        }
    }

    public static function whitelistReturnUrl($url) {
        if (is_string($url) and !preg_match('/^\/?index.php/', $url)) {
            $url = 'index.php';
        }
        return $url;
    }

}

