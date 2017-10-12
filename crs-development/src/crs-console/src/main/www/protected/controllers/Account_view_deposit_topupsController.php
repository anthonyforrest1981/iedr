<?php

/**
 * Description of Account_view_deposit_topupsController
 *
 * @author Artur Kielak
 */
class Account_view_deposit_topupsController extends AccountsGridController {

    public function setDeposits(&$model) {
        $viewResult = null;
        $viewErrors = '';
        $user = Yii::app()->user->authenticatedUser;
        CRSPaymentAppService_service::viewDeposit($viewResult, $viewErrors, $user);
        if ($viewResult != null) {
            if (!isset($viewErrors)) {
                $model->depositBalance = $viewResult->closeBal;
                $model->depositReservation = $viewResult->reservedFunds;
                $model->availableBalance = $viewResult->availableFunds;
            } else {
                Yii::log('topup deposit count error ' . print_r($viewErrors, true));
            }
        } else {
            Yii::log('topup deposit result is null.');
        }
    }

    public function actionMenu() {
        $params = array();
        if (isset($_REQUEST['days'])) {
            $params = array('days' => $_REQUEST['days']);
        }
        $model = new DepositTopUpHistoryModel();
        $model->searchParams = $params;
        if (!$model->validate()) {
            $model->resetSearchParams();
        }
        $this->setDeposits($model);
        $this->render('menu', array('model' => $model));
    }

    public function actionGetGridDataTopUpHistory() {
        $this->actionGetgriddata();
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = new CRSPaymentAppService_depositTopUpSearchResultVO();
        CRSPaymentAppService_service::getTopUpHistory($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria['fromDate'], $criteria['toDate'], $offset, $limit);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_view_deposit_topupsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Top-up history";
    }

}
?>
