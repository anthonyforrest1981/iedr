<?php

class RegistranttransfersellrequestsController extends GridController {

    public function actionView() {
        $model = new RegistrantTransferViewSellRequestsModel();
        if (Yii::app()->request->isPostRequest && isset($_POST['RegistrantTransferViewSellRequestsModel'])) {
            $model->attributes = $_POST['RegistrantTransferViewSellRequestsModel'];
            $model->validate();
        }
        $this->render('/registranttransfer/viewsellrequests', array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSSecondaryMarketAppService_service::findOwnSellRequests($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sortCriteria);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'RegistranttransfersellrequestsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Registrant Transfer Sell Requests";
    }

}
