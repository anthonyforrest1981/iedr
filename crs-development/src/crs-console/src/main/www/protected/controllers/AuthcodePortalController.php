<?php

class AuthcodePortalController extends Controller {

    public function actionRequest() {
        $model = new AuthcodePortalModel();
        if (isset($_POST['ajax']) && $_POST['ajax'] === 'authcodePortal') {
            echo CActiveForm::validate($model);
            Yii::app()->end();
        } else if (Yii::app()->request->isPostRequest && isset($_POST['AuthcodePortalModel'])) {
            $model->setFromPOST($_POST['AuthcodePortalModel']);
            $model->scenario = 'submit';
            if ($model->validate()) {
                $domain = $model->domain_name;
                $email = $model->email;
                $error = null;
                CRSCommonAppService_service::sendAuthCodeFromPortal($error, $domain, $email);
                if (isset($error)) {
                    // TODO WSAPIError.php should be used instead, but its API makes no sense in this use case
                    if (mb_strpos($error, 'DomainNotFoundException') !== false) {
                        $model->message = "Unfortunately, the domain name entered does not exist. "
                                . "If you require further assistance, please contact our Registration Services team on 01-2365400.";
                    } else if (mb_strpos($error, 'AuthCodePortalEmailException') !== false) {
                        $model->message = "Unfortunately, the email address entered is not listed "
                                . "on the contact details for this domain. If you require further assistance, "
                                . "please contact our Registration Services team on 01-2365400.";
                    } else if (mb_strpos($error, 'AuthCodePortalLimitException') !== false) {
                        $model->message = "Unfortunately, the daily limit of generations has been reached. "
                                . "Please try again tomorrow.";
                    } else {
                        $model->message = $error;
                    }
                } else {
                    $model->clear();
                    $model->message = "Your authcode has been sent to your email address";
                }
            }
        }
        $this->render('request', array('model' => $model));
    }

}