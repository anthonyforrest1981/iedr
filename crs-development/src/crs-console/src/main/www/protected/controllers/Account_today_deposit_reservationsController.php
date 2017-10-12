<?php

/**
 * Description of Account_today_deposit_reservationsController
 *
 * @author Artur Kielak
 */
class Account_today_deposit_reservationsController extends AccountsGridController {

    public function cleanModel(&$model) {
        $model->setTotalResults(number_format(0));
        $model->setTotalVat(number_format(0, 2));
        $model->setTotalWithVat(number_format(0, 2));
        $model->setTotalAmount(number_format(0, 2));
    }

    public function actionMenu() {
        $model = new ViewTodayDepositReservationModel();
        $this->cleanModel($model);
        $result = null;
        CRSPaymentAppService_service::getNotSettledReservationsTotals($result, $this->backend_errors, Yii::app()->user->authenticatedUser, 'true');
        if ($result != null) {
            if (count($this->backend_errors) == 0) {
                $model->setTotalResults($result->totalResults);
                $model->setTotalVat($result->totalVat);
                $model->setTotalWithVat($result->totalWithVat);
                $model->setTotalAmount($result->totalAmount);
            } else {
                Yii::log('Some errors= ' . print_r($this->backend_errors, true));
                $model->setErrors($this->backend_errors);
            }
        } else {
            Yii::log('Result is null in actionReady()   ' . print_r($this->backend_errors, true));
            $model->setErrors('null');
        }
        Utility::writeActionToSession('account_today_deposit_reservations/menu');
        $this->render('menu', array('model' => $model));
    }

    public function actionGetgriddataDepositBalanceReady() {
        return $this->actionGetgriddata();
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSPaymentAppService_service::getNotSettledReservations($result, $this->backend_errors,
            Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_today_deposit_reservationsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Today deposit reservations";
    }

}
?>
