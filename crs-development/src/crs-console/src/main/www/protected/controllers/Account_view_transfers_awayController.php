<?php

/**
 * Description of Account_view_transfers_awayController
 *
 * @author Artur Kielak
 */
class Account_view_transfers_awayController extends GridController {
    public $records = 0;

    public function setCountRecords(&$model, $criteria = null) {
        $this->records = 0;
        $result = null;
        $model->setSearchCriteria();
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->transferFrom = $model->transferFrom;
        $criteria->transferTo = $model->transferTo;
        CRSDomainAppService_service::findTransferredAwayDomains($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, 1000, 0, null);
        if (count($this->backend_errors) == 0 && $result != null) {
            $this->records = $result->totalResults;
            $model->totalrows = $this->records;
        } else {
            $model->totalrows = 0;
            Yii::log('Problem with get records . ' . print_r($this->backend_errors, true));
        }
    }

    public function setAttributtes(&$model, $attr) {
        $this->performAjaxValidation($model);
        if (Yii::app()->request->isPostRequest and isset($_POST[$attr])) {
            $model->attributes = $_POST[$attr];
            if (!$model->validate()) {
                $model->resetDays();
            }
            $model->setSearchCriteria();
            Yii::log(print_r($model, true), 'debug', 'Accounts_payhist_crednotesController::setAttributtes()');
        }
    }

    public function actionGetgriddata_away() {
        return $this->actionGetgriddata();
    }

    public function actionMenu() {
        $model = new TransfersAwayModel();
        $this->setAttributtes($model, 'TransfersAwayModel');
        $this->setCountRecords($model);
        $this->render('menu', array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSDomainAppService_service::findTransferredAwayDomains($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_view_transfers_awayController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Transfers Away";
    }

}
?>
