<?php

/**
 * defines SiteController class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */
/**
 * This controller class handles general web app actions, including index, error, login, logout
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class SiteController extends Controller {
    public $message;

    /**
     * defines actions to use CViewAction
     *
     * @access public
     */
    public function actions() {
        if ($this->checkTimeout()) {
            $this->redirect('index.php?r=site/logout');
        }
        if (!isset(Yii::app()->user->authenticatedUser)) {
            Yii::log('WITHOUT LOGIN');
            $this->redirect('index.php?r=site/login');
        }
        return array(
            // page action renders "static" pages stored under 'protected/views/site/pages'
            // They can be accessed via: index.php?r=site/page&view=FileName
            'page' => array('class' => 'CViewAction','basePath' => 'application.views.site.pages'));
    }

    private function checkTimeout() {
        try {
            CRSAuthenticationService_service::isUserSwitched($result, $this->backend_errors, Yii::app()->user->authenticatedUser);
            if (isset($this->backend_errors)) {
                return true;
            }
            return false;
        } catch (Exception $e) {
            return true;
        }
    }

    /**
     * This is the default 'index' action that is invoked
     * when an action is not explicitly requested by users.
     *
     * Renders the view file 'protected/views/site/index.php'
     * using the default layout 'protected/views/layouts/main.php'
     *
     * @access public
     * @see views/site/index.php
     */
    public function actionIndex() {
        $this->render('index');
    }

    private function getArgument($name) {
        if (isset($_GET) && isset($_GET[$name])) {
            $value = $_GET[$name];
            try {
                return Utf8Validator::validateAndNormalizeUtf8($value);
            } catch (Exception $e) {
                Yii::log("Argument $value is not a valid UTF-8 string");
                throw new Exception("Error: Not a valid UTF-8 string");
            }
        } else {
            throw new Exception("Error: Argument is not set");
        }
    }

    private function getDomainList() {
        return mb_split(",", $this->getArgument('list'));
    }

    private function getVerificationMessage($result) {
        if (is_array($result)) {
            $invalidDomains = implode("~", $result);
            return $invalidDomains;
        } else if (is_string($result)) {
            return $result;
        }
    }

    public function actionValiddomains() {
        try {
            $errors = '';
            $result = null;
            CRSDomainAppService_service::checkPayAvailable($result, $errors, Yii::app()->user->authenticatedUser, $this->getDomainList());
            if (count($errors) == 0 && $result != null) {
                echo CHtml::encode($this->getVerificationMessage($result));
            } else if (count($errors) == 0) {
                echo "ok";
            } else if (mb_strpos($errors, "Permission denied") !== false) {
                echo "Permission denied while pre-validating payment for the domains. One of the selected domains is not owned by the user.";
            } else {
                echo "Error while pre-validating payment for the domains";
            }
        } catch (Exception $e) {
            echo CHtml::encode($e->getMessage());
        }
    }

    public function actionVerifynotlocked() {
        try {
            $errors = '';
            $result = null;
            CRSDomainAppService_service::checkModificationAvailable($result, $errors, Yii::app()->user->authenticatedUser, $this->getDomainList(), $this->getArgument('renewal'));
            if (count($errors) == 0 && $result != null) {
                echo CHtml::encode($this->getVerificationMessage($result));
            } else if (count($errors) == 0) {
                echo "ok";
            } else {
                echo "Error while validating operation";
            }
        } catch (Exception $e) {
            echo CHtml::encode($e->getMessage());
        }
    }

    public function actionIssesion() {
        $result = null;
        if (!isset(Yii::app()->user->authenticatedUser)) {
            // just skip
        } else {
            // TODO used dedicated method instead!!!
            CRSAuthenticationService_service::isUserSwitched($result, $this->backend_errors, Yii::app()->user->authenticatedUser);
            if (isset($this->backend_errors)) {
                echo "sessiontimeout";
            } else {
                $permission = Utility::getPermission();
                if ($permission == UserPermissionLevel::UNDEFINED) {
                    echo "nopermission";
                }
            }
        }
    }

    /**
     * This is the action to handle external exceptions.
     *
     * @access public
     */
    public function actionError() {
        $error = Yii::app()->errorHandler->error;
        if ($error) {
            if (Yii::app()->request->isAjaxRequest)
                echo 'Error during ajax request. Check logs for details';
            else
                $this->render('error', $error);
        }
    }

    /**
     * Displays account summary iframe, 'protected/views/site/summaryframe.php'
     *
     * @access public
     * @see summaryframe.php
     */
    public function actionSummaryFrame() {
        $this->renderpartial('summaryframe');
    }

    /**
     * Account summary IFrame fetches several numbers via Ajax calls, which are all handled by this action function.
     *
     * This function outputs formatted numbers for the account summary display
     *
     * @access public
     * @see summaryframe.php
     */
    public function actionSummary() {
        if (!isset(Yii::app()->user->name)) {
            Yii::log('actionSummary Yii::app()->user->name no set');
        }
        if (!isset($_GET['id'])) {
            Yii::log('actionSummary $_GET[id] no set');
        }
        if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
            switch ($_GET['id']) {
                case 'DepositBalance':
                    echo number_format(getDepositBalance(Yii::app()->user->name), 2);
                    break;
            }
        }
    }

    /**
     * Displays the contact page (unused)
     * $user = Yii::app()->user->authenticatedUser;
     *
     * @access public
     * @see contact.php, ContactForm
     */
    public function actionContact() {
        $model = new ContactForm();
        if (isset($_POST['ContactForm'])) {
            $model->attributes = $_POST['ContactForm'];
            if ($model->validate()) {
                $headers = "From: {$model->email}\r\nReply-To: {$model->email}";
                mb_send_mail(Yii::app()->params['adminEmail'], $model->subject, $model->body, $headers);
                Yii::app()->user->setFlash('contact', 'Thank you for contacting us. We will respond to you as soon as possible.');
                $this->refresh();
            }
        }
        $this->render('contact', array('model' => $model));
    }

    /**
     * Displays the login page, or handles a login request - on success, redirecting to originating url, if any
     *
     * @access public
     * @see LoginForm, login.php, NicHandleIdentity
     */
    public function actionLogin() {
        if (!Utility::isTomcatExist()) {
            $returnMessage = new stdclass();
            $returnMessage->message = 'Could not connect to the backend server, please try again later.';
            $serializedMessage = AuthOnlyBaseController::safeSerializeObj($returnMessage);
            $this->redirect(array('index','message' => $serializedMessage));
        }
        $model = new LoginForm();
        if (isset($_POST['ajax']) && $_POST['ajax'] === 'login-form') {
            echo CActiveForm::validate($model);
            Yii::app()->end();
        } else if (Yii::app()->request->isPostRequest && isset(Yii::app()->user->authenticatedUser)) {
            if (Yii::app()->user->authenticatedUser->passwordChangeRequired) {
                $this->redirect('/index.php?r=site/changePassword');
            } else {
                $this->redirect(Yii::app()->user->returnUrl);
            }
        }
        CRSInfo_service::getServerTime($model->serverTime, $errs);
        $this->render('login', array('model' => $model));
    }

    /**
     * Changes the password of the logged in user.
     *
     * @access public
     * @see changePassword.php, ChangePasswordForm
     */
    public function actionChangePassword() {
        $model = new ChangePasswordForm();
        // Yii::log('POST CHANGES '.print_r($_POST,true));
        if (array_key_exists('yt0', $_POST) && $_POST['yt0'] == "Change Password") {
            $model->attributes = $_POST['ChangePasswordForm'];
            if ($model->validate()) {
                $user = Yii::app()->user->authenticatedUser;
                $nicHandle = Yii::app()->user->name;
                CRSNicHandleAppService_service::changePassword($errs, $user, $nicHandle, $model->old_password, $model->new_password, $model->repeat_new_password);
                if (count($errs) == 0) {
                    Yii::app()->user->authenticatedUser->passwordChangeRequired = false;
                    $this->redirect(Yii::app()->user->returnUrl);
                } else {
                    $model->addError('old_password', 'Old password does not match stored password.');
                }
            }
        }
        $this->render('changePassword', array('model' => $model));
    }

    /**
     * Logs out the current user and redirect to homepage.
     *
     * @access public
     */
    public function actionLogout() {
        if (isset(Yii::app()->user->authenticatedUser)) {
            CRSAuthenticationService_service::logout($this->backend_errors, Yii::app()->user->authenticatedUser);
        }
        Yii::app()->user->logout();
        $this->redirect('/index.php?r=site/login');
    }

    public function actionNewDirectAccount() {
        $nhdModel = new Nichandle_Details();
        $passwordModel = new NewPassword();
        $showConfirmationScreen = false;
        if (Yii::app()->request->isAjaxRequest) {
            $this->performTabularAjaxValidation(array($passwordModel, $nhdModel));
        } else if (Yii::app()->request->isPostRequest && isset($_POST['Nichandle_Details']) &&
                isset($_POST['NewPassword'])) {
            $nhdModel->attributes = $_POST['Nichandle_Details'];
            $passwordModel->attributes = $_POST['NewPassword'];
            $action = $_POST['action'];
            $showConfirmationScreen = ($action == 'Create');
            $createAccount = ($action == 'Confirm');
            $validateData = $showConfirmationScreen || $createAccount;
            if ($validateData && $nhdModel->validate() && $passwordModel->validate()) {
                if ($createAccount) {
                    $authenticatedNewNicHandle = $this->createAccount($nhdModel, $passwordModel);
                    if ($this->backend_errors == null) {
                        $this->logInAsCreatedNicHandle($authenticatedNewNicHandle);
                    } else {
                        $nhdModel->error = $this->backend_errors;
                    }
                }
            } else {
                $showConfirmationScreen = false;
            }
        }
        $this->render('newDirectAccount', array('model' => $nhdModel, 'passwordModel' => $passwordModel,
            'confirmation' => $showConfirmationScreen));
    }

    private function logInAsCreatedNicHandle($authenticatedNewNicHandle) {
        $user = $authenticatedNewNicHandle->user;
        $secret = property_exists($authenticatedNewNicHandle, 'secret') ? $authenticatedNewNicHandle->secret : null;
        $identity = new NicHandleIdentity(null, $user->username, null, null);
        $identity->createUserSession($user);
        Yii::app()->user->setFlash('success', $this->getAccountCreationMessages($user, $secret));
        $this->redirectToHomePage();
    }

    private function getAccountCreationMessages($user, $secret) {
        $messages = array('Your account has been successfully created. You are now logged into your IEDR Console account.');
        $messages[] = '';
        $messages[] = 'Your new Account Number (Nic-Handle) is: ' . $user->username;
        if ($secret !== null) {
            $messages[] = 'Your two factor authentication secret is: '. $secret . '. It is extremely important, that'
                . ' you write it down, as without it you won\'t be able to complete the login process.';
        }
        return $messages;
    }

    public function actionTwoFactorAuth() {
        $newSecret = null;
        if (Yii::app()->request->isPostRequest && array_key_exists('yt0', $_POST)) {
            CRSNicHandleAppService_service::changeTfaFlag($newSecret, $this->backend_errors,
                Yii::app()->user->authenticatedUser, $_POST['yt0'] == 'Enable');
        }
        CRSNicHandleAppService_service::isTfaUsed($tfaUsed, $this->backend_errors, Yii::app()->user->authenticatedUser);
        $this->render('twoFactorAuthSetup', array('tfaUsed' => $tfaUsed,'newSecret' => $newSecret));
    }

    private function createAccount($nhd, $passwordModel) {
        $result = null;
        $data = $nhd->getAsObject();
        CRSResellerAppService_service::createDirectAccount($result, $this->backend_errors, $data,
            $passwordModel->new_password, $passwordModel->useTwoFactorAuthentication, Utility::getClientIP());
        return $result;
    }

    public function actionDynamiccounties() {
        Utility::printListOfOptions(getCountyOptions($_POST['Nichandle_Details']['countryId']));
    }

}
