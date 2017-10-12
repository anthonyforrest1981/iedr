<?php

class TicketsController extends GridController {
    const SESSION_KEY_DOCUMENT_UPLOAD_RESULT = 'document_upload_result_for_ticket_';

    /**
     * render ticket grid, showing tickets created in the last N days ('days' defaults to 100, set in protected/config/main.php)
     *
     * @return void
     * @access public
     * @see main.php, AllTicketsModel
     */
    public function actionMain() {
        $model = new AllTicketsModel();
        Yii::log(print_r($_POST, true), 'debug', 'TicketsController::actionMain()');
        if (Yii::app()->request->isPostRequest && isset($_POST['AllTicketsModel'])) {
            $model->attributes = $_POST['AllTicketsModel'];
        } else {
            if (isset($_POST['days'])) {
                $model->days = $_POST['days'];
            }
        }
        Yii::log(print_r($model, true), 'debug', 'TicketsController::actionMain()');
        Utility::writeActionToSession('tickets/main');
        $this->render('/tickets/main', array('model' => $model));
    }

    /**
     * render ticket-view page, conditionally displaying the edit button
     *
     * @return void
     * @access public
     * @see ViewTicketModel, viewticket.php
     */
    public function actionViewticket() {
        $vt_model = new ViewTicketModel();
        $vt_model->attributes = $_GET;
        $uploadResult = null;
        if ($vt_model->validate()) {
            $ticketVO = $this->getTicket($vt_model->id);
            if ($ticketVO != null) {
                $vt_model->fillFromObject($ticketVO, $this->backend_errors);
            } else {
                $vt_model->errors = $this->backend_errors;
            }
            $uploadResultSessionKey = self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT . $vt_model->id;
            if (AuthOnlyBaseController::isSavedInSession($uploadResultSessionKey)) {
                $uploadResult = AuthOnlyBaseController::safeDeserializeObjFromFromSession($uploadResultSessionKey);
                Yii::app()->user->setState($uploadResultSessionKey, NULL);
            }
        } else {
            $vt_model->errors = 'Invalid Ticket ID Specified';
            $this->redirect('index.php?r=tickets/main', true);
        }
        $this->render('/tickets/viewticket', array('model' => $vt_model,
            'uploadResult' => new UploaderResultModelWrapper($uploadResult)));
    }

    /**
     * render ticket edit page, and handlt ticket update data posted (redirects back to ticket view on success).
     *
     * @return void
     * @access public
     * @see editticket.php, EditTicketModel, viewticket.php
     */
    public function actionEditticket() {
        $et_model = new EditTicketModel();
        $uploaderModel = new DocumentsModel();
        $uploaderModel->scenario = DocumentsModel::DOMAIN_UPLOAD_SCENARIO;
        $ticketVO = null;
        $nictype = '';
        $returndata = '';
        if (isset($_POST['NicSearchModel']) and isset($_POST['NicSearchModel']['returningData'])) {
            $returndata = $_POST['NicSearchModel']['returningData'];
            $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            $nictype = $returndata['nictype'];
            $dd_model->$nictype = $returndata['nic'];
            $et_model = $dd_model;
            $retunurl = $_POST['NicSearchModel']['returningData']['returnurl'];
            $ids = mb_split('id=', $retunurl);
            $id = $ids[1];
            try {
                $id = Utf8Validator::validateAndNormalizeUtf8($id);
                $ticketVO = $this->getTicket($id);
            } catch (Exception $e) {
                Yii::log("ticketId in returningData not a valid utf8 string", CLogger::LEVEL_ERROR);
                $this->redirect(array('tickets/main'));
            }
            $et_model->setReadOnlyDataFromObj($ticketVO);
        } else if (isset($_POST['Nichandle_Details']) and isset($_POST['Nichandle_Details']['returningData'])) {
            $returndata = $_POST['Nichandle_Details']['returningData'];
            $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            if (!empty($returndata['nic'])) {
                $nictype = $returndata['nictype'];
                $dd_model->$nictype = $returndata['nic'];
            }
            $et_model = $dd_model;
            $retunurl = $_POST['Nichandle_Details']['returningData']['returnurl'];
            $ids = mb_split('id=', $retunurl);
            $id = $ids[1];
            try {
                $id = Utf8Validator::validateAndNormalizeUtf8($id);
                $ticketVO = $this->getTicket($id);
            } catch (Exception $e) {
                Yii::log("ticketId in returningData not a valid utf8 string", CLogger::LEVEL_ERROR);
                $this->redirect(array('tickets/main'));
            }
            $et_model->setReadOnlyDataFromObj($ticketVO);
        } else if (Yii::app()->request->isPostRequest) {
            try {
                $ticketIdFromPost = $_POST['EditTicketModel']['id'];
                $ticketVO = $this->getTicket(Utf8Validator::validateAndNormalizeUtf8($ticketIdFromPost));
                $formWasEditable = $_POST['EditTicketModel']['editable'];
                $editAttemptInError = false;
                $ticketEditable = empty($ticketVO->checkedOutTo);
                if ($formWasEditable) {
                    if ($ticketEditable) {
                        // posted data: update ticket if valid
                        $ticketVO->operation->adminContactsField = array();
                        $ticketVO->operation->adminContactsField[0]->newValue->nicHandle = mb_strtoupper($_POST['EditTicketModel']['adminContact_0']);
                        $ticketVO->operation->adminContactsField[1]->newValue->nicHandle = mb_strtoupper($_POST['EditTicketModel']['adminContact_1']);
                        $ticketVO->operation->techContactsField->newValue->nicHandle = mb_strtoupper($_POST['EditTicketModel']['techContact']);
                        if ($ticketVO->type != "Transfer") {
                            $ticketVO->operation->domainHolderField->newValue = $_POST['EditTicketModel']['domainHolder'];
                        }
                        $ticketVO->operation->nameservers = array();
                        $count = $_POST['EditTicketModel']['nameserversCount'];
                        for ($i = 0; $i < $count; $i++) {
                            $ticketVO->operation->nameservers[$i]->name->newValue = $_POST['EditTicketModel']['nameservers'][$i];
                            $ticketVO->operation->nameservers[$i]->ipv4Address->newValue = isset($_POST['EditTicketModel']['ipv4Addresses'][$i]) ? $_POST['EditTicketModel']['ipv4Addresses'][$i] : null;
                            $ticketVO->operation->nameservers[$i]->ipv6Address->newValue = isset($_POST['EditTicketModel']['ipv6Addresses'][$i]) ? $_POST['EditTicketModel']['ipv6Addresses'][$i] : null;
                        }
                    } else {
                        $editAttemptInError = true;
                    }
                }
                $et_model->fillFromObject($ticketVO, $this->backend_errors);
                if (isset($_POST['EditTicketModel']['requestersRemark']))
                    $et_model->requestersRemark = $ticketVO->requestersRemark = $_POST['EditTicketModel']['requestersRemark'];
                $et_model->setReadOnlyDataFromObj($ticketVO);
                $this->performAjaxValidation($et_model);
                $purpose = NULL;
                // "Registration" and "Modification" are strings from DomainOperationType (java enum)
                if ($et_model->type == "Registration") {
                    $purpose = CRSDocumentAppService_uploadPurposeVO::_REGISTRATION;
                } else if ($et_model->type == "Modification") {
                    $purpose = CRSDocumentAppService_uploadPurposeVO::_MODIFICATION;
                }
                // eager check for validity - we want to always check both ticket and document models
                $valid = $ticketEditable ? $et_model->validate() : true;
                if (!is_null($purpose) && Utility::isLoggedInAs($et_model->billingContact)) {
                    $uploaderModel->attributes = $_POST;
                    $uploaderModel->setAllowedDomains($et_model->domainName);
                    $valid = $uploaderModel->validate() && $valid;
                }
                if ($valid) {
                    $ticketUpdated = $ticketEditable && $this->doUpdateTicket($ticketVO, $et_model);
                    if (!$ticketEditable || $ticketUpdated) {
                        if ($uploaderModel->isSetup()) {
                            $uploadResult = $uploaderModel->uploadDocuments($purpose);
                            $uploadResultSessionKey = self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT . $et_model->id;
                            AuthOnlyBaseController::safeSerializeAndStoreObjInSession($uploadResult, $uploadResultSessionKey);
                        }
                        Yii::app()->user->setFlash('state', 'modified');
                        Yii::app()->user->setFlash('ticketUpdate', $ticketUpdated ? "done" : ($editAttemptInError ? "error" : ""));
                        $this->redirect(array('tickets/viewticket','id' => $et_model->id));
                    }
                }
            } catch (Exception $e) {
                Yii::log("Cannot get ticket because ticket id $ticketIdFromPost not a valid utf8 string", CLogger::LEVEL_ERROR);
                // ajax must be handled manually, pretend it's ok since there is no field to mark as invalid visible in the form
                if (isset($_POST['ajax'])) {
                    echo '[]';
                    Yii::app()->end();
                } else {
                    $this->redirect(array('tickets/main'));
                }
            }
        } else {
            // must be a GET to display edit form
            $ticketId = $_GET['id'];
            if (empty($ticketId) || !is_numeric($ticketId) || !$ticketId > 0) {
                Yii::log("Missing or non-numeric ticket id", CLogger::LEVEL_WARNING);
                $this->redirect(array('tickets/main'));
            }
            $ticketVO = $this->getTicket($ticketId);
            $ticketEditable = empty($ticketVO->checkedOutTo);
            $et_model->fillFromObject($ticketVO, $this->backend_errors);
            $et_model->editable = $ticketEditable ? "1" : "0";
        }
        if ($this->backend_errors != null) {
            $et_model->errors = WSAPIError::getErrorsNotEmpty($this->backend_errors);
        }
        if ($nictype == 'techContact') {
            $_SESSION['techNic'] = $returndata['nic'];
            $_SESSION['adminNic0'] = $et_model->adminContact_0;
            $_SESSION['adminNic1'] = $et_model->adminContact_1;
        } else if ($nictype == 'adminContact_0') {
            $_SESSION['techNic'] = $et_model->techContact;
            $_SESSION['adminNic0'] = $returndata['nic'];
            $_SESSION['adminNic1'] = $et_model->adminContact_1;
        } else if ($nictype == 'adminContact_1') {
            $_SESSION['techNic'] = $et_model->techContact;
            $_SESSION['adminNic0'] = $et_model->adminContact_0;
            $_SESSION['adminNic1'] = $returndata['nic'];
        }
        $this->render('/tickets/editticket', array('model' => $et_model,'uploaderModel' => $uploaderModel));
    }

    /**
     * handles ticket delete; afterwards, diplays ticket grid with message (but probably should redirect instead)
     *
     * @return void
     * @access public
     * @see main.php, AllTicketsModel
     */
    public function actionDeleteticket() {
        $model = new AllTicketsModel();
        /* if not update dates */
        if (!array_key_exists('yt0', $_POST)) {
            if (array_key_exists('domainName', $_GET) && array_key_exists('id', $_GET)) {
                $domainName = encode($_GET['domainName']);
                $ticketId = $_GET['id'];
                try {
                    $ticketId = Utf8Validator::validateAndNormalizeUtf8($ticketId);
                    if ($this->doDeleteTicket($ticketId)) {
                        $model->message = "Success: Ticket for domain $domainName cancelled";
                    } else {
                        $model->message = "Error : Ticket not canceled for domain $domainName, please check your selection, and try again.";
                    }
                } catch (Exception $e) {
                    Yii::log("Ticket number $ticketId is not a valid utf8 string", CLogger::LEVEL_ERROR);
                    $model->message = "Error : Unknown ticket number";
                }
            } else {
                $model->message = "Error : Domain name not set, please check your selection, and try again.";
            }
        }
        $this->render('/tickets/main', array('model' => $model));
    }

    /**
     * performs ticket cancel action
     */
    protected function doDeleteTicket($ticketId) {
        CRSCommonAppService_service::cancel($this->backend_errors, Yii::app()->user->authenticatedUser, $ticketId);
        Yii::log('backe= ' . print_r($this->backend_errors, true));
        return ($this->backend_errors == null);
    }

    /**
     * Perform ticket update
     *
     * Ticket update via CRS-WS-API must:
     *
     * - Fetch ticket in 'edit' mode
     *
     * - 'Checkout' ticket, to lock it for updates
     *
     * - reformat data changes as flattened domain-operations array {@link create_ticket_operations_from_submitted_data}
     *
     * - Update ticket data
     *
     * - 'Checkin' ticket, to unlock
     *
     * @param CRSTicketAppService_ticketVO $ticketVO
     *            Ticket data from CRS-WS-API
     * @param EditTicketModel $et_model
     *            ticket edit data
     * @return boolean true on success
     * @access protected
     */
    protected function doUpdateTicket($ticketVO, $et_model) {
        global $admin_sts_wsapi_strings;
        // proceed to update ticket
        $ticket_id = $et_model->id;
        $user = Yii::app()->user->authenticatedUser;
        // attempt to fetch for edit (possibly not authorized)
        $tmp = new EditTicketModel();
        $tmp = $ticketVO;
        CRSTicketAppService_service::edit($ticketVO, $this->backend_errors, $user, $ticket_id);
        if ($this->backend_errors == null) {
            $ticketEdit = $this->create_ticket_edit_from_submitted_data($tmp, $et_model);
            CRSTicketAppService_service::update($this->backend_errors, $user, $ticket_id, $ticketEdit, $et_model->requestersRemark, $et_model->clikPaid);
            if (count($this->backend_errors) != 0) {
                $this->backend_errors .= ': failed update';
                return false;
            }
            return true;
        }
        $this->backend_errors .= ': failed edit';
        return false;
    }

    /**
     * transforms the supplied objects into an array of changes
     *
     * @param object $ticketVO
     *            Ticket object as fetch frmo CRS-WS-API with Ticket-ID
     * @param object $edit_ticket_model
     *            Vaildated Web Form Data of Ticket changes
     * @return array array of data from merging ticket-object with web form ticket changes
     * @access protected
     */
    protected function create_ticket_edit_from_submitted_data($ticketVO, $edit_ticket_model) {
        $ticketEdit = array();
        // add change operations : iterate over usual change-fields
        if (isset($ticketVO->charityCode)) {
            $ticketEdit['charityCode'] = $edit_ticket_model->charityCode;
        }
        if (isset($ticketVO->requestersRemark)) {
            $ticketEdit['requestersRemark'] = $ticketVO->requestersRemark;
        }
        $opsarr = array('domainHolder','domainName','resellerAccount');
        foreach ($opsarr as $op) {
            $fld = $op . 'Field';
            $val = isset($edit_ticket_model->$op) ? $edit_ticket_model->$op : $ticketVO->operation->$fld->newValue;
            if ($val != null and $val != '') {
                $ticketEdit[$fld] = $val;
            }
        }
        // tech and billing contacts
        $opsarr = array('techContact','billingContact');
        foreach ($opsarr as $op) {
            $fld = $op . 'sField';
            $val = isset($edit_ticket_model->$op) ? $edit_ticket_model->$op : $ticketVO->operation->$fld->newValue->nicHandle;
            if ($val != null and $val != '')
                $ticketEdit[$fld][0] = $val;
        }
        // add change operations : iterate over admin contacts
        if (!is_array($ticketVO->operation->adminContactsField)) {
            $t = $ticketVO->operation->adminContactsField;
            $ticketVO->operation->adminContactsField = array();
            $ticketVO->operation->adminContactsField[0] = $t;
            $ticketVO->operation->adminContactsField[1] = $t;
        }
        $ticketEdit['adminContactsField'] = array();
        foreach (array(0,1) as $x) {
            $op = $ticketVO->operation->adminContactsField[$x];
            $fld = 'adminContact_' . $x;
            $val = isset($edit_ticket_model->$fld) ? $edit_ticket_model->$fld : $op->newValue->nicHandle;
            $ticketEdit['adminContactsField'][$x] = $val;
        }
        // add change operations : iterate over domains
        if (!is_array($ticketVO->operation->nameservers)) {
            $t = $ticketVO->operation->nameservers;
            $ticketVO->operation->nameservers = array();
        }
        foreach ($ticketVO->operation->nameservers as $x => $value) {
            // name
            $val_n = $edit_ticket_model->nameservers[$x];
            // ipv4 address
            $val_ipv4 = $edit_ticket_model->ipv4Addresses[$x];
            // ipv6 address
            $val_ipv6 = $edit_ticket_model->ipv6Addresses[$x];
            $ticketEditNameserver = array('name' => $val_n);
            if ($val_ipv4) {
                $ticketEditNameserver['ipv4Address'] = $val_ipv4;
            }
            if ($val_ipv6) {
                $ticketEditNameserver['ipv6Address'] = $val_ipv6;
            }
            $ticketEdit['nameservers'][] = $ticketEditNameserver;
        }
        Yii::log(print_r($ticketEdit, true), 'debug', 'TicketsController::create_ticket_operations_from_submitted_data()');
        return $ticketEdit;
    }

    /**
     * Fetches Ticket object from backend, for the given Ticket ID
     *
     * @param integer $id
     *            Ticket ID
     * @return CRSTicketAppService_ticketVO Ticket data from CRS-WS-API
     * @access protected
     */
    protected function getTicket($id) {
        Yii::log('getTicket(id=' . $id . ')', 'debug', 'TicketsController::getTicket()');
        $result = null;
        CRSTicketAppService_service::view($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $id);
        if (count($this->backend_errors) == 0) {
            Yii::log(print_r($result, true), 'warning', 'TicketsController::getTicket()' . __LINE__);
        } else
            Yii::log(print_r($this->backend_errors, true), 'error', 'TicketsController::getTicket() : ERROR:' . __LINE__);
        return $result;
    }

    public function actionEditticketserialized() {
        // return serialized domain-details form state to ajax requester ; doesn't have to be valid
        $returns = '';
        if (Yii::app()->request->isPostRequest && isset($_POST['EditTicketModel'])) {
            $dd_model = new EditTicketModel();
            $dd_model->attributes = $_POST['EditTicketModel'];
            Yii::app()->request->csrfTokenName = $_POST['YII_CSRF_TOKEN'];
            $returns = AuthOnlyBaseController::safeSerializeObj($dd_model);
        } else {
            Yii::log('got ?!?! :' . print_r($_REQUEST, true), 'debug', 'TicketController::actionTicketdetailsserialized()');
        }
        echo $returns;
    }

    protected function getSearchName() {
        return "TICKETS";
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSTicketAppService_service::find($result, $this->backend_errors, Yii::app()->user->authenticatedUser,
            $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error', 'TicketsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

}
