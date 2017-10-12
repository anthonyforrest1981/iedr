<?php

class NewdomainsController extends GridController {

    public function actionView() {
        $nd_model = new NewDomainsModel();
        $this->performAjaxValidation($nd_model);
        if (Yii::app()->request->isPostRequest and isset($_POST['NewDomainsModel'])) {
            $nd_model->attributes = $_POST['NewDomainsModel'];
        }
        Yii::log(print_r($nd_model, true), 'debug', 'DomainreportsController::actionNewdomains()');
        Utility::writeActionToSession('domainreports/newdomains');
        $this->render('/domainreports/newdomains', array('model' => $nd_model));
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

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        CRSDomainAppService_service::findPlainDomains($result, $this->backend_errors, $user, $criteria, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'NewdomainsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "New Domains";
    }

}