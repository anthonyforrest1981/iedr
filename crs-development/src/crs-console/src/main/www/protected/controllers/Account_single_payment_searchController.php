<?php

/**
 * Description of Account_single_payment_searchController
 *
 * @author Artur Kielak
 */
class Account_single_payment_searchController extends AccountsGridController {

    public function actionMenu() {
        Yii::log('POST= ' . print_r($_POST, true));
        Yii::log('GET= ' . print_r($_GET, true));
        $model = new ViewSinglePaymentSearchModel();
        $this->render("menu", array('model' => $model));
    }

    protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        CRSPaymentAppService_service::findHistoricalReservations($result, $this->backend_errors, $user, $criteria,
            $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_single_payment_searchController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Single payment";
    }

}
?>
