<?php

class Account_view_invoice_historyController extends AccountsGridController {

    public function actionMenu() {
        $model = new AllInvoiceModel();
        if (isset($_REQUEST['AllInvoiceModel']['year']) && isset($_REQUEST['AllInvoiceModel']['month'])) {
            $model->year = $_REQUEST['AllInvoiceModel']['year'];
            $model->month = $_REQUEST['AllInvoiceModel']['month'];
        }
        Utility::writeActionToSession('account_view_invoice_history/menu');
        $this->render("menu", array('model' => $model));
    }

    public function actionInvoiceview() {
        $result = null;
        if (isset($_GET['id'])) {
            $errors = array();
            $user = Yii::app()->user->authenticatedUser;
            $id = $_GET['id'];
            try {
                $id = Utf8Validator::validateAndNormalizeUtf8($id);
                CRSPaymentAppService_service::getInvoiceInfo($result, $errors, $user, $id);
                if ($result != null) {
                    if (count($errors) == 0) {
                        Yii::log('GET INVOICE = ' . print_r($result, true));
                    } else {
                        Yii::log('GET INVOICE ERRORS');
                    }
                } else {
                    Yii::log('GET INVOICE ' . $id . ' RESULT IS NULL ' . print_r($errors, true));
                }
            } catch (Exception $e) {
                Yii::log('GET INVOICE failed due to invalid invoice id ' . $id . '. Exact exception message: ' . $e->getMessage(), CLogger::LEVEL_ERROR);
            }
        }
        $this->render("invoiceview", array('result' => $result));
    }

    public function actionConfirm() {
        $this->showConfirmPage();
    }

    public function actionGetgriddata_invoices() {
        $this->exportFileName = 'Invoice_';
        $this->actionGetgriddata();
    }

    public function actionSendemail() {
        $number = $_GET['number'];
        $errs = array();
        $user = Yii::app()->user->authenticatedUser;
        try {
            $number = Utf8Validator::validateAndNormalizeUtf8($number);
            CRSPaymentAppService_service::sendEmailWithInvoices($errs, $user, $number);
            if (count($errs) == 0) {
                Yii::log('Send email successful.');
                echo "sendemail";
            } else {
                Yii::log('Send email failure ' . print_r($errs, true));
                echo "sendemailfailure";
            }
        } catch (Exception $e) {
            Yii::log('Send email failure due to bad utf8 in invoice number ' . $number . '. Exact exception message: ' . $e->getMessage(), CLogger::LEVEL_ERROR);
            echo "sendemailfailure";
        }
    }

    function actionDownload() {
        try {
            $type = Utf8Validator::validateAndNormalizeUtf8($_GET['type']);
            $number = Utf8Validator::validateAndNormalizeUtf8($_GET['number']);
            $wsapi_soap_url = Yii::app()->params['wsapi_soap_url'];
            $username = Yii::app()->user->authenticatedUser->username;
            $token = Yii::app()->user->authenticatedUser->authenticationToken;
            $url = $wsapi_soap_url . "view-invoice/invoice?userName=" . $username . "&token=" . $token . "&invoiceNumber=" . $number . "&fileType=" . $type;
            $ch = curl_init($url);
            curl_setopt($ch, CURLOPT_HEADER, true);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($ch, CURLOPT_BINARYTRANSFER, 1);
            $result = curl_exec($ch);
            list ($headers, $content) = mb_split("\r\n\r\n", $result, 2);
            Yii::log($headers, 'DEBUG', 'curl headers');
            foreach (mb_split("\r\n", $headers) as $hdr) {
                if (mb_stristr($hdr, 'content'))
                    header($hdr);
            }
            echo $content;
            curl_close($ch);
        } catch (Exception $e) {
            Yii::log("Cannot download invoice due to utf8 errors in type $type or number $number. Exact exception message: " . $e->getMessage(), CLogger::LEVEL_ERROR);
            echo "Cannot download invoice";
        }
    }

    public function actionGetgriddata_menu() {
        return $this->actionGetgriddata_invoices();
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        $user = Yii::app()->user->authenticatedUser;
        CRSPaymentAppService_service::findInvoices($result, $this->backend_errors, $user, $criteria, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0 && $result != null) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_view_invoice_historyController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Ivoice history";
    }

}
?>
