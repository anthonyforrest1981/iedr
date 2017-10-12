<?php

/**
 * Description of Account_failed_transactionsController
 *
 * @author Artur Kielak
 */
class Account_failed_transactionsController extends AccountsGridController {

    public function actionMenu() {
        $at_model = new FailedTransactionModel();
        if (Yii::app()->request->isPostRequest and isset($_POST['AllTicketsModel'])) {
            $at_model->attributes = $_POST['AllTicketsModel'];
        }
        Utility::writeActionToSession('account_failed_transactions/menu');
        $this->render('menu', array('model' => $at_model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        CRSTicketAppService_service::find($result, $this->backend_errors, Yii::app()->user->authenticatedUser,
            $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_failed_transactionsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Failed transactions";
    }

}
?>
