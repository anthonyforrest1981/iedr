<?php

/**
 * defines NicHandleValidator class
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
 * Validates a NicHandle, that it matches syntax and has an account number
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 * @see NicHandleSyntaxValidator
 */
class NicHandleValidator extends NicHandleSyntaxValidator {

    /**
     * Validator that returns true if the model-attribute both has an Account-ID returned by the CRS-WS API
     * call CRSNicHandleAppService:get().
     *
     * @param CModel $model
     *            model containing data to validate
     * @param string $attribute
     *            name of model attribute containing NicHandle to validate
     * @access protected
     */
    protected function validateAttribute($model, $attribute) {
        $value = $model->$attribute;
        if (empty($value)) {
            return;
        }
        parent::validateAttribute($model, $attribute);
        $errorMessage = $model->getError($attribute);
        if (isset($errorMessage)) {
            return;
        } else if (!$this->NICExists($value)) {
            $lbls = $model->attributeLabels();
            $lbl = $lbls[$attribute];
            $model->addError($attribute, Yii::t('yii', $lbl . ' is not an existing Account'));
        }
    }

    /**
     * Gets an account ID for a given NicHandle, from the CRS-WS Backend
     *
     * @param string $nic
     *            Nic Handle to search for
     * @return integer Account ID, if found, otherwise -1
     * @access protected
     */
    protected function _NICExists($nic) {
        $result = null; // CRSNicHandleAppService_nicHandleVO
        $errs = null;
        try {
            CRSNicHandleAppService_service::get($result, $errs, Yii::app()->user->authenticatedUser, $nic);
            if (count($errs) != 0) {
                Yii::log('ERRS=' . $errs, 'error', 'NicHandleValidator::_NICExists(' . $nic . ') [err]');
            } else {
                Yii::log('STATUS=' . $result->status, 'debug', 'NicHandleValidator::_NICExists(' . $nic . ') [res]');
            }
        } catch (Exception $e) {
            Yii::log(print_r($e, true), 'error', 'NicHandleValidator::_NICExists(' . $nic . ') [EX]');
        }
        return count($errs) == 0 ? $result->status : "None";
    }

    /**
     * Gets an account ID for a given NicHandle, returning a cached value of found, otherwise calling {@link _NICExists}
     *
     * @param string $nic
     *            - Nic Handle to search for
     * @return integer Account ID, if found, otherwise -1
     * @access protected
     */
    protected function NICExists($nic) {
        $statuses = Utility::getNicHandleStatuses(true);
        $status = "None";
        // cache is global to application (all session/users), so use a cache key that contains the logged-in user,
        // to avoid returning a cached value to another user
        $ckey = 'NICExistsFor_' . Yii::app()->user->authenticatedUser->username . '_' . $nic;
        if (Yii::app()->cache != null) {
            $cached_status = Yii::app()->cache->get($ckey);
            if ($cached_status && array_key_exists($cached_status, $statuses)) {
                Yii::log('cache hit for ' . $ckey, 'debug', 'NicHandleValidator::NICExists(' . $nic . ')');
                $status = $cached_status;
            } else {
                Yii::log('cache miss for ' . $ckey, 'debug', 'NicHandleValidator::NICExists(' . $nic . ')');
                $status = $this->_NICExists($nic);
                Yii::app()->cache->set($ckey, $status, 60);
            }
        } else {
            Yii::log('no caching', 'debug', 'NicHandleValidator::NICExists(' . $nic . ')');
            $status = $this->_NICExists($nic);
        }
        if (!in_array($status, $statuses)) {
            return false;
        } else {
            return $statuses[$status];
        }
    }

}
