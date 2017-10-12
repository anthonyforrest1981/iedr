<?php

class UserSwitchModel extends CFormModel {
    /**
     * username (i.e., Nic Handle) to switch to
     */
    public $switchUser;
    /**
     * URL to return to after switching user
     */
    public $returnUrl;

    /**
     * constructor sets default username, return url
     */
    public function __construct() {
        $this->switchUser = Yii::app()->user->nicHandle->nicHandleId;
        $this->returnUrl = $_SERVER['REQUEST_URI'];
    }

    /**
     * returns URL to return to
     *
     * @return string URL to return to
     * @access public
     */
    public function getSwitchActionUrl() {
        $actionId = Yii::app()->getController()->getAction()->getId();
        if ($actionId === 'index') {
            // this one handles situation, where actionId == index since the ?r=site/index part may not been added here
            return '/index.php?r=site/switchuser';
        } else {
            return preg_replace('/\/' . $actionId . '/u', '/switchuser', $this->returnUrl);
        }
    }

    public function rules() {
        return array(array('switchUser, returnUrl','required'), array('switchUser','Utf8Validator'),
            array('switchUser','filter','filter' => 'mb_strtoupper'),array('switchUser','validateSwitchUser'))
        // array('switchUser', 'NicHandleValidator'), // no: would cause error in trying to get logged-in user as part of cache key
        ;
    }

    public function attributeLabels() {
        if (Utility::isDirect()) {
            return array('switchUser' => 'Direct');
        } else {
            return array('switchUser' => 'Registrar');
        }
    }

    /**
     * validate user-to-switch-to, must be in list of switchable-to registrars
     *
     * @param string $attrib
     *            model attribute-name to validate
     * @param array $params
     *            validation parameters (ignored)
     */
    public function validateSwitchUser($attrib, $params) {
        if (!Utility::isDirect()) {
            $this->validateSwitchRegistrarUser($attrib, $params);
        }
    }

    public function validateSwitchRegistrarUser($attrib, $params) {
        $username = $this->$attrib;
        $usrs = getRegistrarsForLogin();
        if (!isset($usrs[$username])) {
            $this->addError($attrib, $username . ' is not a valid Registrar');
        }
    }

}
