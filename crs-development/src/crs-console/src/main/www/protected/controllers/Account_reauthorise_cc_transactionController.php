<?php

class Account_reauthorise_cc_transactionController extends AccountsGridController {

    public function actionMenu() {
        $model = new ReauthorizeCCTransactionModel();
        Utility::writeActionToSession('account_reauthorise_cc_transaction/menu');
        $this->render("menu", array('model' => $model));
    }

    public function actionGetgriddata_re() {
        $this->exportFileName = 'Invoice_';
        $this->actionGetgriddata();
    }

    public function actionReauthorise() {
        $model = new ReauthorizeCCTransactionPayModel();
        $model->setScenario(CreditCardFormSegmentModel::$without_amount_scenario);
        $transactionId = '';
        if (!isset($_GET['id'])) {
            $this->redirect('account_reauthorise_cc_transaction/menu');
        } else {
            $transactionId = $_GET['id'];
        }
        $this->performAjaxValidation($model);
        try {
            $transactionId = Utf8Validator::validateAndNormalizeUtf8($transactionId);
            if (Yii::app()->request->isPostRequest && isset($_POST['ReauthorizeCCTransactionPayModel'])) {
                $model->setFromPOST($_POST['ReauthorizeCCTransactionPayModel']);
                if ($model->validate()) {
                    $result = null;
                    $errs = '';
                    $creditCard = CreditCardFormSegmentModel::asWSAPICreditCardObject($model);
                    CRSCommonAppService_service::reauthoriseTransaction($result, $errs, Yii::app()->user->authenticatedUser, $transactionId, $creditCard);
                    if ($result != null) {
                        Yii::log('RE RESULT IS NOT NULL ');
                        $model->message = 'TRANSACTION_OK';
                    } else {
                        Yii::log('RE RESULT IS NULL ' . print_r($errs, true));
                        $model->error = $errs;
                    }
                }
            }
        } catch (Exception $e) {
            // transactionId should
            Yii::log('TransactionId does not pass utf8 validation, looks like data tampering. Exact exception message: ' . $e->getMessage(), CLogger::LEVEL_ERROR);
            $model->error = 'Bad transaction id';
        }
        $this->render("reauthorise", array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        CRSPaymentAppService_service::getTransactionToReauthorise($result, $this->backend_errors, $user, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_reauthorise_cc_transactionController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Reauthorise CC transaction";
    }

}
?>
