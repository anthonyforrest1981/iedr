<?php

/**
 * Description of Account_deleted_domain_reportController
 *
 * @author artur
 */
class Account_deleted_domain_reportController extends GridController {
    public $records = 0;

    public function actionMenu() {
        $model = new DeletedDomainReportModel();
        Yii::log('ACTION get menu');
        $this->render('menu', array('model' => $model));
    }

    public function actionGetgriddatadeleted() {
        Yii::log('ACTION GET DAta');
        return $this->actionGetgriddata();
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSDomainAppService_service::findDeletedDomains($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_deleted_domain_reportController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Deleted domains";
    }

}
?>
