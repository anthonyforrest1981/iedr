<?php

class RegistranttransferbuyrequestsController extends GridController {

    public function actionView() {
        $model = new RegistrantTransferViewBuyRequestsModel();
        if (Yii::app()->request->isPostRequest && isset($_POST['RegistrantTransferViewBuyRequestsModel'])) {
            $model->attributes = $_POST['RegistrantTransferViewBuyRequestsModel'];
            $model->validate();
        }
        $this->render('/registranttransfer/viewbuyrequests', array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSSecondaryMarketAppService_service::findOwnBuyRequests($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sortCriteria);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'RegistranttransferbuyrequestsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Registrant Transfer Buy Requests";
    }

}
