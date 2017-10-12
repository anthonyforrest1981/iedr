<?php

/**
 * defines NichandlesController class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
/**
 * Short description for class
 *
 * Long description (if any) ...
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
class NichandlesController extends ReturningController {

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return void
     * @access public
     * @see viewnichandle.php, Nichandle_Details
     */
    public function actionViewnichandle() {
        $model = new Nichandle_Details();
        if (array_key_exists('id', $_GET) && isset($_GET['id'])) {
            $nic = $_GET['id'];
        } else {
            $this->redirectToHomePage();
        }
        try {
            $nic = Utf8Validator::validateAndNormalizeUtf8($nic);
        } catch (Exception $e) {
            Yii::log("View nic handle failed due to nic handle $nic not passing as a valid utf8 string. " . $e->getMessage(), CLogger::LEVEL_ERROR);
            $this->redirectToHomePage();
        }
        $this->fillNichandleDetailsModel($model, $nic);
        $this->render('viewnichandle', array('model' => $model));
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return void
     * @access public
     * @see editnichandle.php, Nichandle_Details
     */
    public function actionEditnichandle() {
        $nic = '';
        if (isset($_GET['id'])) {
            $nic = $_GET['id'];
        }
        $nhd_model = new Nichandle_Details();
        try {
            $nhv = new NicHandleSyntaxValidator();
            $nic = Utf8Validator::validateAndNormalizeUtf8($nic);
            if ($nhv->validateValue($nic)) {
                $nhd_model->nicHandleId = $nic;
                $this->performAjaxValidation($nhd_model);
                if (Yii::app()->request->isPostRequest and isset($_POST['Nichandle_Details'])) {
                    $nhd_model->attributes = $_POST['Nichandle_Details'];
                    if ($nhd_model->validate()) {
                        if ($this->saveNic($nhd_model))
                            $nhd_model->message = 'NIC_UPDATED';
                        else
                            $nhd_model->error = $this->backend_errors;
                    }
                } else {
                    // display Nic Handle for Edit
                    $this->fillNichandleDetailsModel($nhd_model, $nic);
                }
            } else {
                $nhd_model->error = 'Account Number \'' . encode($nic) . '\' is invalid';
            }
        } catch (Exception $e) {
            Yii::log("Nic handle $nic is invalid utf8 string", CLogger::LEVEL_ERROR);
            $nhd_model->error = 'Account Number \'' . encode($nic) . '\' is invalid';
        }
        $this->render('editnichandle', array('model' => $nhd_model));
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return void
     * @access public
     * @see Nichandle_Details, createnichandle.php
     */
    public function actionCreatenichandle() {
        Yii::log('enter ; ' . print_r($_REQUEST, true), 'debug', 'NichandlesController::actionCreatenichandle()');
        $nhd_model = new Nichandle_Details();
        $this->performAjaxValidation($nhd_model);
        if (Yii::app()->request->isPostRequest and isset($_POST['Nichandle_Details'])) {
            Yii::log('Is POST', 'debug', 'NichandlesController::actionCreatenichandle()');
            $nhd_model->attributes = $_POST['Nichandle_Details'];
            if ($nhd_model->validate()) {
                Yii::log('Validates', 'debug', 'NichandlesController::actionCreatenichandle()');
                $aNic = $this->createNic($nhd_model);
                if ($this->backend_errors == null) {
                    $nhd_model->nicHandleId = $aNic;
                    Yii::log('Created NIC ' . $nhd_model->nicHandleId, 'debug', 'NichandlesController::actionCreatenichandle()');
                    $nhd_model->message = 'NIC_CREATED';
                    // also set value for returning to previous form
                    $nhd_model->returningData['nic'] = $nhd_model->nicHandleId;
                } else {
                    $nhd_model->error = $this->backend_errors;
                }
            } else {
                Yii::log('Validation Errors', 'debug', 'NichandlesController::actionCreatenichandle()');
            }
        }
        $this->render('createnichandle', array('model' => $nhd_model));
    }

    // Non-Action Functions
    // Use this to find the complete details of a nichandle. (inc phone+fax)
    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param unknown $nichandle
     *            Parameter description (if any) ...
     * @return unknown Return description (if any) ...
     * @access protected
     */
    protected function getNicHandleDetails($nichandle) {
        $result = null; // CRSNicHandleAppService_nicHandleVO
        $res = CRSNicHandleAppService_service::get($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $nichandle);
        if ($result != null) {
            if (count($this->backend_errors) == 0) {
                Yii::log('NIC: ' . print_r($result, true), 'debug', 'NichandlesController::getNicHandleDetails() ');
            } else {
                Yii::log('ERR: ' . print_r($this->backend_errors, true), 'error', 'NichandlesController::getNicHandleDetails()');
            }
        } else {
            Yii::log('RESULT IS NULL NichandlesController::getNicHandleDetails()');
        }
        return $result;
    }

    protected function fillNichandleDetailsModel($model, $nic) {
        $nichandleVO = $this->getNicHandleDetails($nic);
        if ($nichandleVO != null) {
            if ($nic != Yii::app()->user->name) {
                $model->scenario = null;
            }
            $model->fillFromObject($nichandleVO, $this->backend_errors);
            $result = null;
            CRSNicHandleAppService_service::canBeABillingContact($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $nic);
            $model->canBeABillingContact = $result === true;
        } else {
            $nhd_model->error = 'Account \'' . encode($nic) . '\' not found';
        }
    }
    // @param $nhd_model - the nic details to be converted to a nicHandleEditVO and then saved.
    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param object $nhd_model
     *            Parameter description (if any) ...
     * @return boolean Return description (if any) ...
     * @access protected
     */
    protected function saveNic($nhd_model) {
        $data = $nhd_model->getAsObject();
        $remark = 'Updating NicHandle via CRS-WS-API';
        CRSNicHandleAppService_service::save($this->backend_errors, Yii::app()->user->authenticatedUser, $nhd_model->nicHandleId, $data, 'Updating NicHandle via CRS-WS-API');
        Yii::log('errors="' . print_r($this->backend_errors, true) . '"', 'debug', 'NichandlesController::saveNic()');
        return ($this->backend_errors == null) ? true : false;
    }
    // Returns a new nic based on the form details.
    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param object $nhd_model
     *            Parameter description (if any) ...
     * @return unknown Return description (if any) ...
     * @access protected
     */
    protected function createNic($nhd_model) {
        Yii::log('createNic', 'debug', 'NichandlesController::createNic()');
        $result = null;
        // prepare data
        $data = $nhd_model->getAsObject();
        // attempt create
        CRSNicHandleAppService_service::create($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $data, 'Creating NicHandle via CRS-WS-API');
        Yii::log('result=' . print_r($result, true), 'debug', 'NichandlesController::createNic()');
        Yii::log('errors=' . print_r($this->backend_errors, true), 'debug', 'NichandlesController::createNic()');
        return $result;
    }

}
