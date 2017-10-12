<?php

/**
 * Description of Account_deposit_statement_of_accountController
 *
 * @author Artur Kielak
 */
class Account_deposit_statement_of_accountController extends AccountsGridController {

    public function actionMenu() {
        $model = new AccountDepositModel();
        $this->render('menu', array('model' => $model));
    }

    public function actionGetgriddata_main() {
        $this->actionGetgriddata();
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        $user = Yii::app()->user->authenticatedUser;
        CRSPaymentAppService_service::findUserHistoricalDeposits($result, $this->backend_errors, $user,
            $criteria, $offset, $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_deposit_statement_of_accountController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Deposit statement";
    }

}
?>