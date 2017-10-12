<?php

class Account_current_nrp_statusesController extends AccountsGridController {

    public function __construct() {
        $this->exportFileName = 'CurrentNRPStatuses_';
    }

    public function actionConfirm() {
        $this->showConfirmPage();
    }

    public function getSelectionModel($isVoluntary = false) {
        return new CurrentNRPStatusesSelectionModel($isVoluntary);
    }

    public function getSelectionModelName() {
        return 'CurrentNRPStatusesSelectionModel';
    }

    public function actionMain() {
        $this->render('account_current_nrp_statuses/main');
    }

    public function actionMenu() {
        if (isset($_POST['CurrentNRPStatusesModel']['contact_type'])) {
            $model = new CurrentNRPStatusesModel($_POST['CurrentNRPStatusesModel']['contact_type']);
        } else {
            $model = new CurrentNRPStatusesModel();
        }
        $errors = '';
        $result = null;
        $criteria = new CRSDomainAppService_domainCountForContactSearchCriteriaVO();
        $criteria->activeFlag = false;
        CRSDomainAppService_service::findDomainCountForContact($result, $errors, Yii::app()->user->authenticatedUser, $criteria);
        if (count($errors) == 0 && $result != null) {
            $model->setSummary($result);
        } else {
            Yii::log(print_r($errors), 'error');
        }
        Utility::writeActionToSession('account_current_nrp_statuses/menu');
        $this->render('account_current_nrp_statuses/menu', array('model' => $model));
    }

    public function actionGetgriddata_menu() {
        $this->actionGetgriddata();
    }

    public function actionGetgriddata_deletions() {
        $this->exportFileName = 'CurrentNRPStatuses_';
        $this->actionGetgriddata();
    }

    protected function showConfirmPage() {
        Yii::log('SHOW CONFIGRM PAGE= ' . print_r($_POST, true) . '   ' . print_r($_GET, true));
        $rows = array();
        $domains = array();
        if (isset($_POST['gridactionpay']['domainlist'])) {
            $rows = mb_split(",", $_POST['gridactionpay']['domainlist']);
            for ($i = 0; $i < count($rows); $i++) {
                $tmp = mb_split("~", $rows[$i]);
                $domains[] = $tmp[0];
            }
            $_POST['gridactionpay']['domainlist'] = implode(",", $domains);
        }
        Yii::log('domainlist= ' . print_r($rows, true));
        $postedForm = isset($_POST['gridactionpay']) ? $_POST['gridactionpay'] : (isset($_POST['gridactionnopay']) ? $_POST['gridactionnopay'] : null);
        if ($postedForm != null) {
            switch ($postedForm['command']) {
                case 'removefromvoluntary':
                    $model = $this->getSelectionModel(true);
                    break;
                default:
                    $model = $this->getSelectionModel();
                    break;
            }
            $model->setFromPOST($postedForm);
            $which = isset($_POST['gridactionpay']) ? 'gridactionpay' : 'gridactionnopay';
            $model->needCreditCard = ($model->command == 'payonline') ? 1 : 0;
            $model->defaultPeriods = implode(",", $rows);
            $this->redirectAfterPost('account_current_nrp_statuses/confirmaction', $model);
        } else {
            $this->redirectToHomePage();
        }
    }

    public function getViewBase() {
        return '/account_current_nrp_statuses/';
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSDomainAppService_service::findExtendedDomains($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0 && $result != null) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_current_nrp_statusesController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Current NRP Statuses";
    }

}

