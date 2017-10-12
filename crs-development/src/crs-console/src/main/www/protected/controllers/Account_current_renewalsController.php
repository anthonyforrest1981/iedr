<?php

class Account_current_renewalsController extends AccountsGridController {

    public function actionMenu() {
        $model = new CurrentRenewalsModel();
        if (isset($_REQUEST['CurrentRenewalsModel']['renewalDateType'])) {
            $model->renewalDateType = $_REQUEST['CurrentRenewalsModel']['renewalDateType'];
        }
        Utility::writeActionToSession('account_current_renewals/menu');
        $this->render('menu', array('model' => $model));
    }

    public function getSelectionModel($isVoluntary = false) {
        return new CurrentRenewalsSelectionModel($isVoluntary);
    }

    public function getSelectionModelName() {
        return 'CurrentRenewalsSelectionModel';
    }

    public function setType_CurrRnR() {
        $this->exportFileName = 'CurrentRenewalsModel_';
    }

    public function actionConfirm_currnr() {
        $this->setType_CurrRnR();
        $this->showConfirmPage();
    }

    public function actionConfirm() {
        $this->showConfirmPage();
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
            if ($postedForm['command'] == 'payonline') {
                $model = $this->getSelectionModel();
            } else {
                $model = $this->getSelectionModel(true);
            }
            $model->setFromPOST($postedForm);
            $model->needCreditCard = ($model->command == 'payonline') ? 1 : 0;
            $model->defaultPeriods = implode(",", $rows);
            $this->redirectAfterPost('account_current_renewals/confirmaction', $model);
        } else {
            $this->redirectToHomePage();
        }
    }

    public function getViewBase() {
        return '/account_current_renewals/';
    }

    public function actionGetgriddatacurrrnr() {
        $this->setType_CurrRnR();
        return $this->actionGetgriddata();
    }

    function lastDateOfMonth($Month, $Year = -1) {
        if ($Year < 0)
            $Year = 0 + date("Y");
        $aMonth = mktime(0, 0, 0, $Month, 1, $Year);
        $NumOfDay = 0 + date("t", $aMonth);
        $LastDayOfMonth = mktime(0, 0, 0, $Month, $NumOfDay, $Year);
        return $LastDayOfMonth;
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSDomainAppService_service::findDomainsForCurrentRenewal($result, $this->backend_errors,
                Yii::app()->user->authenticatedUser, $model->renewalDateType, $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_current_renewalsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Current renewals";
    }

}
?>
