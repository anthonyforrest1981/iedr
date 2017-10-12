<?php

/**
 * Description of Account_view_payment_historyController
 *
 * @author Artur Kielak
 */
class Account_view_payment_historyController extends AccountsGridController {

    public function actionMenu() {
        $model = new ViewPaymentHistoryModel();
        Utility::writeActionToSession('account_view_payment_history/menu');
        $this->render("menu", array('model' => $model));
    }

    public function actionPaymentView() {
        if (array_key_exists('id', $_GET) && isset($_GET['id'])) {
            $model = new ViewSinglePaymentModel($_GET['id']);
            $this->render("view_single_payment", array('model' => $model));
        } else {
            $this->redirect("index.php?r=account_view_payment_history/menu");
        }
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        $user = Yii::app()->user->authenticatedUser;
        CRSPaymentAppService_service::findHistoricalTransactions($result, $this->backend_errors, $user, $criteria,
            $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_view_payment_historyController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Payment history";
    }

}
?>
