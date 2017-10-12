<?php

class LockeddomainsController extends GridController {

    public function actionView() {
        $model = new LockedDomainsModel();
        Utility::writeActionToSession('domainreports/lockeddomains');
        $this->render('/domainreports/lockeddomains', array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        CRSDomainAppService_service::findPlainDomains($result, $this->backend_errors, $user, $criteria, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'LockeddomainsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Locked Domains";
    }

}