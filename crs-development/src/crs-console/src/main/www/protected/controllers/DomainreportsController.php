<?php

class DomainreportsController extends GridController {

    public function actionMenu() {
        $this->render('menu');
    }

    public function actionAlldomains() {
        if (isset($_POST['AllDomainsModel']['contact_type'])) {
            $model = new AllDomainsModel($_POST['AllDomainsModel']['contact_type']);
        } else {
            $model = new AllDomainsModel();
        }
        $result = null;
        $errors = '';
        $criteria = new CRSDomainAppService_domainCountForContactSearchCriteriaVO();
        CRSDomainAppService_service::findDomainCountForContact($result, $errors, Yii::app()->user->authenticatedUser, $criteria);
        if (count($errors) == 0 && $result != null) {
            $model->setSummary($result);
        } else {
            Yii::log(print_r($errors), 'error');
        }
        Utility::writeActionToSession('domainreports/alldomains');
        $this->render('alldomains', array('model' => $model));
    }

    public function actionConfirm() {
        $this->showConfirmPage();
    }

    public function showConfirmPage() {
        if (isset($_POST['gridaction'])) {
            $model = $this->getSelectionModel();
            $model->setFromPOST($_POST['gridaction']);
            $this->processGridActionCommand($model);
            $this->redirectAfterPost('confirmaction', $model);
        } else {
            $this->redirectToHomePage();
        }
    }

    public function actionConfirm_billable() {
        $this->showConfirmPage();
    }

    public function getExportFilenameTag() {
        return 'DomainReports_';
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        CRSDomainAppService_service::findDomains($result, $this->backend_errors, $user, $criteria, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'DomainreportsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Domains";
    }

}
