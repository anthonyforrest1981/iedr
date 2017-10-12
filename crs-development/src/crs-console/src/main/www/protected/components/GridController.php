<?php

/**
 * file which defines GridController class
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
 * Base Controller for supporting handling grid views.
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
class GridController extends ReturningController {

    public function getSelectionModelWithCard($command) {
        switch ($command) {
            case 'voluntary':
            case 'removefromvoluntary':
            case 'paydeposit':
            case 'autorenew':
            case 'noautorenew':
            case 'authcodedownload':
                return $this->getSelectionModel(true);
                break;
            default:
                return $this->getSelectionModel();
                break;
        }
    }

    /**
     * Confirmation action, presents view of a list of selected items with ticked checkboxes and proceed/cancel buttons.
     *
     * Initially renders confirmation-view with deserialised grid-selection model;
     * On submit of confirmed items, invokes command action of derived class with model specified by derived class
     *
     * @return void
     * @access public
     * @see grid/confirm_action.php, GridSelectionModel
     */
    public function actionConfirmaction() {
        /*
         * receive POST data from Confirm page, with :
         * 1) command
         * 2) domain list
         * 3) (if need) renewal period per domain
         * 4) (if need) payment card details
         */
        // $model = isset($_GET['id']) ? self::safeDeserializeObj($_GET['id']) : null;
        $model = self::safeDeserializeObj(Yii::app()->user->getState('rap'));
        if ($model == null) {
            $this->redirectToHomePage();
        } else if ($model != null) {
            $selectionModel = null;
            $selectionModel = $this->getSelectionModelWithCard($model->command);
            if (isset($_POST['CSVFile'])) {
                $this->exportCSVFile($_POST['GridSelectionModel']);
            } else if (!isset($_POST[$this->getSelectionModelName()])) {
                $selectionModel->copyFromOther($model);
                $this->render($this->getViewBase() . 'confirm_action', array('model' => $selectionModel));
            } else {
                $selectionModel->setFromPOST($_POST[$this->getSelectionModelName()]);
                $selectionModel->removeUncheckedDomains();
                $this->performAjaxValidation($selectionModel);
                $bool = $selectionModel->validate();
                if ($bool) {
                    Yii::log('VALID OK');
                    $this->execCommand($selectionModel);
                    if ($this->backend_errors != null) {
                        if (is_array($this->backend_errors)) {
                            foreach ($this->backend_errors as $err) {
                                $selectionModel->addError('domainlist', WSAPIError::getErrorsNotEmpty($err));
                            }
                        } else {
                            $selectionModel->addError('domainlist', WSAPIError::getErrorsNotEmpty($this->backend_errors));
                            Yii::log('show results ERRORS', 'debug', 'GridController::actionConfirmaction()');
                        }
                    } else
                        Yii::log('show results OK', 'debug', 'GridController::actionConfirmaction()');
                    $this->render($this->getViewBase() . '/results', array('model' => $selectionModel));
                    // $this->render('/accountsgrid/results', array('model' => $selectionModel));
                } else {
                    Yii::log('VALID NOT OK');
                    $this->render($this->getViewBase() . 'confirm_action', array('model' => $selectionModel));
                }
            }
        } else {
            Yii::log('model undefined!');
        }
    }

    private function exportCSVFile($p) {
        $this->exportFile('csv', 'Authcodes_', array('Domain name','Authcode','Expiration Date'), $p['commandresults']['exportData']);
    }

    /**
     * Sets correct headers and prints out csv or excel (BIFF8) format. Does not end Yii application,
     * so caller should make sure that no output is printed after this method returns.
     * @param $fileType either "csv" or "excel" - method does not check it again
     * @param $filePrefix prefix for filename, will be concatenated with user NicHandle and current date to form
     *                    final filename
     * @param $heads array of strings - names for column headers
     * @param $exportData exported data as array of rows, each row an array of hashmap of values.
     */
    private function exportFile($fileType, $filePrefix, $heads, $exportData) {
        $p = null;
        $filename = $filePrefix . Yii::app()->user->name . '-' . date('Y-m-d');
        $objPHPExcel = new PHPExcel();
        $objPHPExcel->getProperties()->setTitle($filename);
        $phpSheet = $objPHPExcel->getActiveSheet();
        foreach ($heads as $colIdx => $headValue) {
            $v = htmlspecialchars_decode($headValue, ENT_QUOTES);
            $phpSheet->setCellValueByColumnAndRow($colIdx, 1, $v);
        }
        foreach ($exportData as $rowIdx => $rowData) {
            $colIdx = 0;
            foreach ($rowData as $cellVal) {
                $v = htmlspecialchars_decode($cellVal, ENT_QUOTES);
                $phpSheet->setCellValueByColumnAndRow($colIdx++, $rowIdx + 2, $v);
            }
        }
        ob_clean();
        header('Pragma: public');
        header('Expires: 0');
        header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
        $writer = null;
        switch ($fileType) {
            case 'csv':
                header('Content-Type: text/csv;charset=utf-8');
                header('Content-Disposition: attachment; filename=' . $filename . '.csv');
                $writer = PHPExcel_IOFactory::createWriter($objPHPExcel, 'CSV');
                $writer->setUseBOM(true);
                break;
            case 'excel':
                header('Content-Type: application/vnd.ms-excel');
                header('Content-Disposition: attachment;filename=' . $filename . '.xls');
                $writer = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel5');
                break;
        }
        $writer->save('php://output');
    }

    /**
     * Shared common functionality for jqGrid display
     *
     * Common functionality for jqGrid display, including CSV and Excel data export,
     * JSON encoding of grid data for ajax loading, pagination, and handling of searching and sorting parameters
     *
     * @return void
     * @access public
     * @throws CException thrown if function called with @GridModelBase lacking a default sort column
     * @see GridSelectionModel
     */
    public function actionGetgriddata() {
        $model = self::getModelFromQuery();
        if (isset($_GET['oper']) and $model != null and is_object($model)) {
            if (!isset($model->defaultSortColumn)) {
                Yii::log('Model must have defaultSortColumn : ' . print_r($model, true), 'error', 'GridController::actionGetgriddata()');
                throw new CException('Model must have defaultSortColumn set to a model-column-name with unique value');
            }
            Yii::log('oper=' . $_GET['oper'], 'debug', 'GridController::actionGetgriddata():' . __LINE__);
            switch ($_GET['oper']) {
                case 'csv':
                case 'excel':
                    $data = $this->getDataReq($model, $_GET);
                    $excluded = isset($model->excludeFromExport) ? $model->excludeFromExport : null;
                    $exportData = $this->getWithExclusions($data['rows'], $excluded);
                    $heads = array();
                    foreach ($model->columns as $id => $col) {
                        if ($excluded == null || !in_array($id, $excluded)) {
                            $heads[] = $col['label'];
                        }
                    }
                    $this->exportFile($_GET['oper'], $this->getExportFilenameTag(), $heads, $exportData);
                    break;
                // case 'pdf': some other time :)
                case 'grid':
                    $data = $this->getDataReq($model, $_GET, /* paging= */ true);
                    $modelColIDs = array_keys($model->columns);
                    if (is_array($data) && array_key_exists('rows', $data)) {
                        if (count($data['rows'])) {
                            foreach ($data['rows'] as $k => $r) {
                                $rowArray = array();
                                $rowArray['id'] = $r[$model->defaultSortColumn];
                                $rowArray['cell'] = array();
                                foreach ($modelColIDs as $colID)
                                    array_push($rowArray['cell'], $r[$colID]);
                                $data['rows'][$k] = $rowArray;
                            }
                        }
                    } else {
                        Yii::log('GRID NO DATA');
                    }
                    header('Content-Type: text/x-json;charset=utf-8');
                    echo json_encode($data, JSON_HEX_QUOT | JSON_HEX_TAG | JSON_HEX_AMP | JSON_HEX_APOS);
                    break;
                default:
                    echo 'Unrecognised "oper"';
                    break;
            }
            Yii::app()->end();
        } else {
            Yii::log('ERR, request=' . print_r($_REQUEST, true), 'debug', 'GridController::actionGetgriddata():' . __LINE__);
            $this->render('/site/error', 'invalid request');
        }
    }

    private function getWithExclusions($data, $exclusions) {
        if ($exclusions != null && sizeof($exclusions) > 0) {
            foreach ($data as &$d) {
                foreach ($exclusions as $e) {
                    unset($d[$e]);
                }
            }
        }
        return $data;
    }

    /**
     * prefix for download-data file-name
     *
     * @return string filename prefix, to be used by used browser to name the downloaded file
     * @access protected
     */
    protected function getExportFilenameTag() {
        // called from actionGetgriddata(), to set exported filename (xls and csv)
        return 'GridData_';
    }

    /**
     * get new instance of grid selection model
     *
     * @return object new instance of grid selection model
     * @access protected
     */
    protected function getSelectionModel($isVoluntary = false) {
        // called from actionConfirmaction()
        return new GridSelectionModel($isVoluntary);
    }

    /**
     * get name of selection model
     *
     * @return string name of selection model
     * @access protected
     */
    protected function getSelectionModelName() {
        // called from actionConfirmaction()
        return 'GridSelectionModel';
    }

    protected function setStatusDomain($domainName, &$model, $action, $operationErrors) {
        $result = null;
        $viewErrors = '';
        CRSDomainAppService_service::view($result, $viewErrors, Yii::app()->user->authenticatedUser, $domainName);
        if ($result != null) {
            $model->commandresults[$action][] = $result->domain;
            if (count($operationErrors) == 0) {
                switch ($action) {
                    case 'autorenew':
                    case 'noautorenew':
                        $model->commandresults['result'][] = $result->domain->dsmState->renewalMode;
                        break;
                    case 'voluntary':
                    case 'removefromvoluntary':
                        $model->commandresults['result'][] = $result->domain->dsmState->shortNrpStatus;
                        break;
                }
            } else {
                $model->commandresults['result'][] = '<span class="errorMessage">' . WSAPIError::getErrors($operationErrors) . '</span>';
            }
        } else {
            $model->commandresults['result'][] = '<span class="errorMessage">' . WSAPIError::getErrors($viewErrors) . '</span>';
        }
    }

    protected function execCommand($model) {
        Yii::log('execCommand in Grid ' . print_r($model->command, true));
        $user = Yii::app()->user->authenticatedUser;
        $domains = mb_split(",", $model->domainlist);
        switch ($model->command) {
            case 'autorenew':
                $domains = GridUtility::getDomainsWithRenewalMode($model);
                foreach ($domains as $domain => $renewalMode) {
                    $errors = null;
                    CRSDomainAppService_service::modifyRenewalMode($errors, $user, $domain, $renewalMode);
                    if (!isset($errors)) {
                        Yii::log('AUTO RENEW SUCCESSFUL ' . $domain);
                    } else {
                        Yii::log('AUTO RENEW FAILED ' . $domain . ' ' . print_r($errors, true));
                    }
                    $this->setStatusDomain($domain, $model, 'autorenew', $errors);
                }
                break;
            case 'noautorenew':
                $remark = 'UNSET AUTORENEW';
                foreach ($domains as $domain) {
                    $ns->name = $domain;
                    $result = null;
                    $errors = null;
                    CRSDomainAppService_service::modifyRenewalMode($errors, $user, $domain, 'NoAutorenew');
                    if (!isset($errors)) {
                        Yii::log('UNSET AUTO RENEW SUCCESSFUL');
                    } else {
                        Yii::log('UNSET AUTO RENEW FAILED' . print_r($this->backend_errors, true));
                    }
                    $this->setStatusDomain($domain, $model, 'noautorenew', $errors);
                }
                break;
            case 'authcodedownload':
                foreach ($domains as $domain) {
                    $authcode = null;
                    $errors = null;
                    $model->commandresults['authcodedownload'][] = $domain;
                    CRSCommonAppService_service::generateOrProlongAuthCode($authcode, $errors, $user, $domain);
                    if (!isset($errors)) {
                        $model->commandresults['exportData'][] = array('A' => $domain,'B' => $authcode->authCode,
                            'C' => parseXmlDate($authcode->validUntil));
                        $model->commandresults['result'][] = 'OK';
                        Yii::log('AUTHCODE DOWNLOAD SUCCESSFUL ' . $domain);
                    } else {
                        $model->commandresults['result'][] = '<span class="errorMessage">' . $errors . '</span>';
                        Yii::log('AUTHCODE DOWNLOAD FAILED ' . $domain . ' ' . print_r($errors, true));
                    }
                }
                break;
            default:
                $model->addError('domainlist', "Not a valid operation.");
                Yii::log('UNDEFINED COMMAND= ' . var_export($model, true));
                break;
        }
        if (count($this->backend_errors))
            Yii::log('command "' . $model->command . '" done, backend_errors=' . print_r($this->backend_errors, true), 'debug', 'GridController::execCommand()');
    }

    /**
     * shows confirmation page
     *
     * called from derived-class command-button-action handlers, e.g. MyGridController::ActionConfirm_mycommand()
     *
     * @return void
     * @access protected
     * @see grid/confirm_action.php, GridSelectionModel
     */
    protected function showConfirmPage() {
        Yii::log('showConfirmPage()!');
        // Must add new command type in new modules
        $commandTypes = array('paydeposit','payonline','voluntary','autorenew','noautorenew','authcodedownload');
        // Must add new link if we use payments in new modules.
        $possiblesUrl = array('/index.php?r=accounts_renewpay_advpay/current_newandrenewals',
            '/index.php?r=accounts_autorenew/autorenew','/index.php?r=accounts_renewpay_currinv/current_newandrenewals',
            '/index.php?r=accounts_renewpay_currinv/transfers_paycurrent',
            '/index.php?r=accounts_renewpay_advpay/transfers_pia','/index.php?r=accounts_msd/recent',
            '/index.php?r=accounts_due_for_renewal/dueforrenewal','/index.php?r=accounts_reports_deleted/deleted',
            '/index.php?r=accounts_renewpay_advpay/confirmaction','/index.php?r=accounts_nrp/confirm_action',
            '/index.php?r=accounts_nrp/confirmaction','/index.php?r=domainreports/alldomains');
        Yii::log('POST IN SHOW CONFIRM ' . print_r($_POST, true));
        Yii::log('GET IN SHOW CONFIRM ' . print_r($_GET, true));
        $rows = array();
        $domains = array();
        if (isset($_POST['gridaction'])) {
            $model = null;
            if (isset($_POST['gridaction']['domainlist'])) {
                $rows = mb_split(",", $_POST['gridaction']['domainlist']);
                for ($i = 0; $i < count($rows); $i++) {
                    $tmp = mb_split("~", $rows[$i]);
                    $domains[] = $tmp[0];
                }
                $_POST['gridaction']['domainlist'] = implode(",", $domains);
            }
            Yii::log('domainlist= ' . print_r($rows, true));
            Yii::log('command= ' . print_r($_POST['gridaction']['command'], true));
            switch ($_POST['gridaction']['command']) {
                case 'voluntary':
                case 'paydeposit':
                case 'autorenew':
                case 'noautorenew':
                case 'removefromvoluntary':
                case 'authcodedownload':
                    $model = $this->getSelectionModel(true);
                    break;
                default:
                    $model = $this->getSelectionModel(false);
                    break;
            }
            $model->setFromPOST($_POST['gridaction']);
            $model->defaultPeriods = implode(",", $rows);
            // Check command type
            $checkCommand = false;
            if (isset($_POST['gridaction']['command'])) {
                for ($i = 0; $i < count($commandTypes); $i++) {
                    if ($commandTypes[$i] == $_POST['gridaction']['command']) {
                        $checkCommand = true;
                        break;
                    }
                }
            }
            if (false == $checkCommand) {
                Yii::log('Undefined command type', 'error', '' . $_POST['gridaction']['command']);
                $this->redirect(self::whitelistReturnUrl($model->returnurl), true);
            }
            // Check domain list
            $checkDomain = false;
            if (isset($_POST['gridaction']['domainlist'])) {
                if (mb_strlen($_POST['gridaction']['domainlist'])) {
                    // some domain
                    $domain = mb_split(",", $_POST['gridaction']['domainlist']);
                    if (count($domain)) {
                        for ($j = 0; $j < count($domain); $j++) {
                            $item = mb_split(".ie", $domain[$j]);
                            if (count($item))
                                $checkDomain = true;
                        }
                    } else {
                        // one domain
                        $item = mb_split(".ie", $domain[0]);
                        if (count($item))
                            $checkDomain = true;
                    }
                }
            }
            if (false == $checkDomain) {
                Yii::log('Undefined domain list ', 'error', '' . $_POST['gridaction']['domainlist']);
                $this->redirect(self::whitelistReturnUrl($model->returnurl), true);
            }
            // Check do
            $checkDo = false;
            for ($i = 0; $i < count($commandTypes); $i++) {
                if (isset($_POST['gridaction']['do_' . $commandTypes[$i]])) {
                    $checkDo = true;
                    break;
                }
            }
            if (false == $checkDo) {
                Yii::log('Undefined action ', 'error', '');
                $this->redirect(self::whitelistReturnUrl($model->returnurl), true);
            }
            // Check possible url
            // $checkUrl = false;
            // if (isset($_POST['gridaction']['returnurl'])) {
            // for ($i = 0; $i < count($possiblesUrl); $i++) {
            // if ($possiblesUrl[$i] == $_POST['gridaction']['returnurl']) {
            // $checkUrl = true;
            // break;
            // }
            // }
            // }
            // if (false == $checkUrl) {
            // Yii::log('Impossible url to accept ', 'error', '' . $_POST['gridaction']['returnurl']);
            // $this->redirect($model->returnurl, true);
            // }
            $this->processGridActionCommand($model);
            $this->redirectAfterPost('confirmaction', $model);
        } else {
            Yii::log('not a POST["gridaction"] request', 'error', 'GridController::showConfirmPage()');
            $this->redirectToHomePage();
        }
    }

    /**
     * make any changes to a model before displaying confirmation page
     *
     * called from showConfirmPage()
     *
     * @param object $model
     *            grid selection model instance
     * @return void
     * @access protected
     */
    protected function processGridActionCommand($model) {
        $model->needCreditCard = 0;
    }

    /**
     * get base for view to render
     *
     * @return string controller component of view-path to render
     * @access protected
     */
    protected function getViewBase() {
        // called from Grid::actionConfirmaction(), to make url for $this->render()
        return '/grid/';
    }

    /**
     * Extract search and sort parameters from request GET parameters; return Array.
     *
     * Called from actionGetgriddata() when fetching JSON grid data.
     * Extracts jqGrid url parameters and forwards them to model functions to produce criteria for data retieval
     *
     * @param mixed $model
     *            grid model instance, which further processes the search/sort parameters
     * @param array $params
     *            http request parameters array
     * @param boolean $paging
     *            whether pagination parameters are needed
     * @return mixed data array returned by call to @getData
     * @access protected
     */
    protected function getDataReq($model, $params/* $_GET */, $paging = false) {
        Yii::log('GET DATA REQ: ' . print_r($params, true));
        $perpage = $paging ? $params['rows'] : 999999999999;
        $pagenum = $paging ? $params['page'] : 1; // page is 1-based
        $sortparams = array('col' => $params['sidx'],'ord' => $params['sord']);
        $searchparams = array();
        if (isset($params['_search']) and $params['_search'] == 'true') {
            // TODO: handle date filter as a date range
            if (isset($params['filters'])) {
                $filters = $params['filters'];
                if (mb_strlen($filters) > 0 and mb_strpos($filters, 'groupOp') >= 0) {
                    $filter = json_decode(urldecode($filters));
                    if (is_object($filter) and $filter->rules)
                        $searchparams = $filter->rules;
                }
            } else {
                // new jqGrid4 search mode, where a col-id contains a filter value
                // iterate over model column names, check if there's a filter value
                foreach ($model->columns as $idx => $col) {
                    if (isset($params[$idx]) and mb_strlen($params[$idx]) > 0) {
                        $o = new stdClass();
                        $o->field = $idx; // 'field' ?
                        $o->data = $params[$idx]; // search string
                        $searchparams[] = $o;
                    }
                }
            }
        }
        try {
            $criteria = $model->getSearch($searchparams);
        } catch (Exception $e) {
            Yii::log('DATA RETURN EMPTY - ' . $e->getMessage());
            return $this->getEmptyData();
        }
        $sort = $model->getSort($sortparams);
        $data = $this->getDataForPage($model, $criteria, $sort, $perpage, $pagenum);
        if ($data == null) {
            Yii::log('DATA RETURN NULL');
        } else {
            Yii::log('DATA RETURN IS NOT NULL= ' . print_r($data, true));
        }
        return $data;
    }

    protected function getEmptyData($pagenum = 1) {
        return array('records' => 0,'page' => $pagenum,'total' => 0,'rows' => array());
    }

    /**
     * Runs backend query for data with the supplied parameters, returns an array
     *
     * @param object $model
     *            model instance, to create row-array from returned backend data
     * @param CRSDomainAppService_plainDomainSearchCriteriaVO $criteria
     *            search criteria
     * @param CRSDomainAppService_sortCriterion $sort
     *            sort criteria
     * @param integer $perpage
     *            number of rows per page wanted
     * @param integer $pagenum
     *            which page of paginated data, used to calculate row offset
     * @return array array of data, structured like : array('records'=>X,'page'=>Y,'total'=>Z,'rows'=>array( ... ));
     * @access protected
     */
    protected function getDataForPage($model, $criteria, $sort, $perpage, $pagenum) {
        // Get Grid data with search and sort parameters; return Array.
        // Called from actionGetgriddata()->getDataReq() when fetching JSON grid data
        // paging
        $pagedata = $this->getEmptyData($pagenum);
        $offset = $perpage * ($pagenum - 1);
        $limit = $perpage;
        // API SOAP call
        Yii::log($this->getSearchName() . ': search criteria = ' . print_r($criteria, true));
        try {
            $result = $this->getData($model, $criteria, $offset, $limit, $sort);
        } catch (Exception $e) {
            return $pagedata;
        }
        if ($result == null) {
            Yii::log($this->getSearchName() . ': RESULT IS NULL');
            return $pagedata;
        }
        if ($result->totalResults == 0) {
            Yii::log($this->getSearchName() . ': NO RESULT FOUND');
            return $pagedata;
        }
        $count = 0;
        if (isset($result->totalResults) && is_integer($result->totalResults)) {
            $count = $result->totalResults;
        } else {
            Yii::log($this->getSearchName() . ': ERROR RELATED WITH TOTAL_RESULTS');
        }
        $total = ceil($count / $perpage);
        if ($pagenum > $total) {
            $pagenum = $total;
        }
        $pagedata['total'] = $total;
        $pagedata['page'] = $pagenum;
        $pagedata['records'] = $count;
        if (isset($result->results)) {
            if (is_array($result->results)) {
                foreach ($result->results as $k => $o)
                    $pagedata['rows'][] = $model->addResults($o);
            } else if (is_object($result->results)) {
                $pagedata['rows'][] = $model->addResults($result->results);
            }
        }
        return $pagedata;
    }

    protected function getSearchName() {
        return "GET DATA";
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        return null;
    }

    function redirectAfterPost($params, $model) {
        Yii::app()->user->setState('rap', self::safeSerializeObj($model));
        $this->redirect(array($params));
    }

}
