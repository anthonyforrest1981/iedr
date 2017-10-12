<?php

class PasswordResetController extends Controller {

    private static $TOKEN_EXPIRED_ERROR = 'token expired';
    private static $INVALID_PIN_ERROR = 'invalid pin';
    private static $ERROR_MAP;

    public function actionRequest() {
        $model = new ResetPasswordForm();
        if (isset($_POST['ResetPasswordForm'])) {
            $model->setScenario(ResetPasswordForm::tokenRequestScenario);
            $model->attributes = $_POST['ResetPasswordForm'];
            if ($model->validate()) {
                $error = $this->requestResetToken($model->username);
                if (empty($error)) {
                    $this->render('requestConfirmation');
                    return;
                } else {
                    Yii::app()->user->setFlash('error', $error);
                }
            }
        }
        $this->render('request', array('model' => $model));
    }

    private function requestResetToken($username) {
        $error = '';
        CRSNicHandleAppService_service::requestPasswordReset($error, $username, Utility::getClientIP());
        return $this->mapErrorMessage($error);
    }

    public function actionChangePassword() {
        $model = new ResetPasswordForm();
        if (!Yii::app()->request->isPostRequest && isset($_GET['user']) && isset($_GET['token'])) {
            $this->handlePasswordChangeGetRequest($model);
        } else if (Yii::app()->request->isAjaxRequest && isset($_POST['ResetPasswordForm'])) {
            $this->handlePasswordChangeAjaxRequest($model);
        } else if (Yii::app()->request->isPostRequest && isset($_POST['ResetPasswordForm'])) {
            $this->handlePasswordChangePostRequest($model);
        } else {
            $this->redirect(array('passwordReset/request'));
        }
        CRSInfo_service::getServerTime($model->serverTime, $errs);
        $this->render('resetPassword', array('model' => $model));
    }

    private function handlePasswordChangeGetRequest($model) {
        $model->setScenario(ResetPasswordForm::tokenVerificationScenario);
        $model->username = $_GET['user'];
        $model->token = $_GET['token'];
        if (!$model->validate()) {
            Yii::app()->user->setFlash('error', 'There was an error processing your password reset request.');
            $this->redirect(array('passwordReset/request'));
        }
    }

    private function handlePasswordChangeAjaxRequest($model) {
        $model->setScenario(ResetPasswordForm::passwordChangeScenario);
        $model->attributes = $_POST['ResetPasswordForm'];
        if ($model->validate()) {
            $error = $this->changePassword($model->username, $model->token, $model->tfaPin, $model->new_password);
            if ($error === self::$TOKEN_EXPIRED_ERROR) {
                Yii::app()->user->setFlash('error', 'Token not valid, please request a new one.');
                Yii::app()->user->setState('redirect', true);
            } else if ($error === self::$INVALID_PIN_ERROR) {
                $model->addError('tfaPin', 'Invalid PIN');
            } else if (!empty($error)) {
                Yii::app()->user->setFlash('error', 'Unsuccessful password change attempt. ' . $error);
            }
        }
        $this->returnModelErrorsAsAjaxResponse($model);
    }

    private function handlePasswordChangePostRequest($model) {
        if (Yii::app()->user->hasFlash('error')) {
            Yii::app()->user->setFlash('error', Yii::app()->user->getFlash('error'));
            $redirect = Yii::app()->user->getState('redirect');
            Yii::app()->user->setState('redirect', null, null);
            if ($redirect) {
                $this->redirect(array('passwordReset/request'));
            } else {
                $model->token = $_POST['ResetPasswordForm']['token'];
            }
        } else {
            Yii::app()->user->setFlash('success', 'Password changed successfully.');
            $this->redirect(array('site/login'));
        }
    }

    private function changePassword($username, $token, $tfaPin, $password) {
        $error = '';
        CRSNicHandleAppService_service::resetPassword($error, $username, $token, $tfaPin, $password);
        return $this->mapErrorMessage($error);
    }

    private function mapErrorMessage($error) {
        self::initErrorMap();
        foreach (self::$ERROR_MAP as $key => $value) {
            if (mb_stristr($error, $key)) {
                $error = $value;
            }
        }
        return $error;
    }

    private static function initErrorMap() {
        if (self::$ERROR_MAP === null) {
            self::$ERROR_MAP = array(
                'NicHandleNotFoundException' => 'Account not found in the database.',
                'NicHandleEmailException' => 'Error sending email.',
                'PasswordResetTokenExpiredException' => self::$TOKEN_EXPIRED_ERROR,
                'GoogleAuthenticationException' => self::$INVALID_PIN_ERROR,
                'PasswordAlreadyExistsException' => 'New password same as the old one.');
        }
    }

}