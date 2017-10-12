<?php

class RegistranttransferController extends AuthOnlyBaseController {

    const SESSION_KEY_DOCUMENT_UPLOAD_RESULT = 'document_upload_result_for_buy_request_';

    public function actionApply() {
        $buyModel = new RegistrantTransferPurchaseModel();
        if (Yii::app()->request->isPostRequest) {
            $this->performAjaxValidation($buyModel);
            if (isset($_POST['RegistrantTransferPurchaseModel'])) {
                $buyModel->attributes = $_POST['RegistrantTransferPurchaseModel'];
                if ($buyModel->validate()) {
                    $domain = $buyModel->domainName;
                    AuthOnlyBaseController::safeSerializeAndStoreObjInSession(
                        array('domainName' => $domain), 'buyRequestData');
                    $this->redirect(array('purchasedetails'));
                }
            }
        }
        $this->render('apply', array('model' => $buyModel));
    }

    public function actionPurchaseDetails() {
        $requestData = AuthOnlyBaseController::safeDeserializeObjFromFromSession('buyRequestData');
        $buyModel = new RegistrantTransferPurchaseDetailsModel($requestData['domainName']);
        $uploaderModel = new DocumentsModel();
        $uploaderModel->scenario = DocumentsModel::BUY_REQUEST_UPLOAD_SCENARIO;
        if (Yii::app()->request->isPostRequest) {
            $this->performTabularAjaxValidation(array($buyModel, $buyModel->nicHandleDetails));
            $valid = $this->fillFromPostAndValidatePurchaseModel($buyModel);
            $valid = $this->fillFromPostAndValidateUploaderModel($uploaderModel) && $valid;
            if ($valid) {
                $user = Yii::app()->user->authenticatedUser;
                $newNicHandle = $buyModel->getNewNicHandle();
                $creditCard = $buyModel->getCreditCard();
                $errs = null;
                CRSSecondaryMarketAppService_service::registerBuyRequest($result, $errs, $user,
                    $buyModel->domainName, $buyModel->domainHolder, $buyModel->ownerType, $newNicHandle,
                    $buyModel->paymentType, $creditCard, $buyModel->remarks);
                $resultsModel = new stdClass();
                $resultsModel->domainName = $buyModel->domainName;
                $resultsModel->errors = $errs;
                $resultsModel->requestId = $result;
                $resultsModel->returningData = $buyModel;
                AuthOnlyBaseController::safeSerializeAndStoreObjInSession($resultsModel,
                    'purchaseresult');
                $this->uploadDocuments($uploaderModel, $resultsModel->requestId);
                $this->redirect(array('purchaseresult'));
            }
        } else if (isset($_GET['errorReturnData']) &&
                AuthOnlyBaseController::isSavedInSession('purchaseresult')) {
            $buyModel = AuthOnlyBaseController::safeDeserializeObjFromFromSession(
                'purchaseresult')->returningData;
        }
        $this->render('purchasedetails', array('model' => $buyModel,
            'uploaderModel' => $uploaderModel));
    }

    private function fillFromPostAndValidatePurchaseModel($model) {
        $model->attributes = $_POST['RegistrantTransferPurchaseDetailsModel'];
        if (isset($_POST['Nichandle_Details'])) {
            $model->nicHandleDetails->attributes = $_POST['Nichandle_Details'];
        }
        return $model->validate();
    }

    public function actionPurchaseresult() {
        if (AuthOnlyBaseController::isSavedInSession('purchaseresult')) {
            $model = AuthOnlyBaseController::safeDeserializeObjFromFromSession('purchaseresult');
            $uploadResult = $this->retrieveUploadResultFromSession($model->requestId);
            $this->render('purchaseresult', array('model' => $model,
                'uploadResult' => new UploaderResultModelWrapper($uploadResult)));
        } else {
            $this->redirect('apply');
        }
    }

    public function actionSell() {
        $sellModel = new RegistrantTransferSaleModel();
        if (Yii::app()->request->isPostRequest) {
            $this->performAjaxValidation($sellModel);
            if (isset($_POST['RegistrantTransferSaleModel'])) {
                $sellModel->attributes = $_POST['RegistrantTransferSaleModel'];
                if ($sellModel->validate()) {
                    $domainName = $sellModel->domainName;
                    AuthOnlyBaseController::safeSerializeAndStoreObjInSession(
                        array('domainName' => $domainName), 'sellRequestData');
                    $this->redirect(array('saledetails'));
                }
            }
        }
        $this->render('sell', array('model' => $sellModel));
    }

    public function actionSaledetails() {
        $requestData = AuthOnlyBaseController::safeDeserializeObjFromFromSession('sellRequestData');
        $model = new RegistrantTransferSaleDetailsModel($requestData['domainName']);
        if (Yii::app()->request->isPostRequest) {
            $this->performAjaxValidation($model);
            if (isset($_POST['RegistrantTransferSaleDetailsModel'])) {
                $model->attributes = $_POST['RegistrantTransferSaleDetailsModel'];
                if ($model->validate()) {
                    $result = null;
                    $errs = '';
                    CRSSecondaryMarketAppService_service::verifyAuthCode($result, $errs,
                        Yii::app()->user->authenticatedUser, $model->domainName, $model->authcode);
                    if (!$result) {
                        $model->authcodeCorrect = false;
                    } else if ($model->submit) {
                        $model->submit = false;
                        $creditCard = CreditCardFormSegmentModel::asWSAPICreditCardObject($model);
                        CRSSecondaryMarketAppService_service::registerSellRequest($result, $errs,
                            Yii::app()->user->authenticatedUser, $model->domainName, $model->authcode,
                            $model->paymentType, $creditCard);
                        $resultsModel = new stdClass();
                        $resultsModel->domainName = $model->domainName;
                        $resultsModel->errors = $errs;
                        $resultsModel->requestId = $result;
                        $resultsModel->returningData = $model;
                        AuthOnlyBaseController::safeSerializeAndStoreObjInSession($resultsModel,
                            'saleresult');
                        $this->redirect(array('saleresult'));
                    } else {
                        $model->submit = true;
                    }
                }
            }
        } else if (isset($_GET['errorReturnData']) &&
                AuthOnlyBaseController::isSavedInSession('saleresult')) {
            $model = AuthOnlyBaseController::safeDeserializeObjFromFromSession(
                'saleresult')->returningData;
        }
        $this->render('saledetails', array('model' => $model));
    }

    public function actionSaleresult() {
        if (AuthOnlyBaseController::isSavedInSession('saleresult')) {
            $model = AuthOnlyBaseController::safeDeserializeObjFromFromSession('saleresult');
            $this->render('saleresult', array('model' => $model));
        } else {
            $this->redirect('sell');
        }
    }

    public function actionViewbuyrequest() {
        $model = new RegistrantTransferBuyRequestDetailsModel();
        $buyRequest = null;
        if (isset($_GET['id'])) {
            $model->id = $_GET['id'];
        }
        $this->getBuyRequestAndFillModelFromObject($model, false);
        $uploadResult = $this->retrieveUploadResultFromSession($model->id);
        $this->setUploadResultAsFlash($uploadResult);
        $this->render('buyrequestdetails', array('model' => $model, 'editing' => false,
            'uploaderModel' => null));
    }

    public function actionEditbuyrequest() {
        $model = new RegistrantTransferBuyRequestDetailsModel();
        $uploaderModel = new DocumentsModel();
        $uploaderModel->scenario = DocumentsModel::BUY_REQUEST_UPLOAD_SCENARIO;
        if (Yii::app()->request->isPostRequest) {
            $this->handleBuyRequestModificationRequest($model, $uploaderModel);
        } else {
            if (isset($_GET['id'])) {
                $model->id = $_GET['id'];
            }
        }
        $this->getBuyRequestAndFillModelFromObject($model, true);
        if ($model->checkedOut) {
            $notice = array('Request cannot be edited right now as it is being processed by IEDR.',
                'You can still upload new documents regarding this request.');
            Yii::app()->user->setFlash('notice', $notice);
        }
        $this->render('buyrequestdetails', array('model' => $model, 'editing' => true,
            'uploaderModel' => $uploaderModel));
    }

    public function actionCancelbuyrequest() {
        $model = new RegistrantTransferBuyRequestDetailsModel();
        if (isset($_GET['id'])) {
            $model->id = $_GET['id'];
        }
        if ($model->validate()) {
            $user = Yii::app()->user;
            $backendErrors = null;
            CRSSecondaryMarketAppService_service::cancelBuyRequest($backendErrors,
                $user->authenticatedUser, $model->id);
            if ($backendErrors) {
                $user->setFlash('error', WSAPIError::getErrorsNotEmpty($backendErrors));
            } else {
                $user->setFlash('success', 'Request cancelled successfully');
                $this->redirect(array('/registranttransferbuyrequests/view'));
            }
        }
        $this->redirect(array('viewbuyrequest', 'id' => $model->id));
    }

    private function handleBuyRequestModificationRequest($model, $uploaderModel) {
        $model->scenario = 'editing';
        $this->performTabularAjaxValidation(array($model, $model->nicHandleDetails));

        $this->fillFromPostBasicFieldsOfDetailsModel($model);
        $valid = $model->requestEditable ?
            $this->fillFromPostAndValidateDetailsModel($model) : true;
        $valid = $this->fillFromPostAndValidateUploaderModel($uploaderModel) && $valid;
        if ($valid) {
            if ($model->requestEditable) {
                $backendErrors = $this->modifyBuyRequest($model);
                if ($backendErrors) {
                    Yii::app()->user->setFlash('error',
                        WSAPIError::getErrorsNotEmpty($backendErrors));
                    return;
                }
                Yii::app()->user->setFlash('success', 'Request modified successfully');
            }
            $this->uploadDocuments($uploaderModel, $model->id);
            $this->redirect(array('viewbuyrequest', 'id' => $model->id));
        }
    }

    private function fillFromPostBasicFieldsOfDetailsModel($model) {
        if (isset($_POST['RegistrantTransferBuyRequestDetailsModel']['id'])) {
            $model->id = $_POST['RegistrantTransferBuyRequestDetailsModel']['id'];
        }
        if (isset($_POST['RegistrantTransferBuyRequestDetailsModel']['requestEditable'])) {
            $model->requestEditable =
                $_POST['RegistrantTransferBuyRequestDetailsModel']['requestEditable'];
        }
    }

    private function fillFromPostAndValidateDetailsModel($model) {
        $model->attributes = $_POST['RegistrantTransferBuyRequestDetailsModel'];
        if (isset($_POST['Nichandle_Details'])) {
            $model->nicHandleDetails->attributes = $_POST['Nichandle_Details'];
        }
        return $model->validate();
    }

    private function fillFromPostAndValidateUploaderModel($uploaderModel) {
        $uploaderModel->attributes = $_POST;
        return $uploaderModel->validate();
    }

    private function modifyBuyRequest($model) {
        $newNicHandle = $model->getNewNicHandle();
        $backendErrors = null;
        CRSSecondaryMarketAppService_service::modifyBuyRequest($backendErrors, Yii::app()->user->authenticatedUser,
            $model->id, $model->domainHolder, $newNicHandle, $model->remarks);
        return $backendErrors;
    }

    private function uploadDocuments($uploaderModel, $buyRequestId) {
        $uploaderModel->buyRequestId = $buyRequestId;
        if ($uploaderModel->isSetup()) {
            $purpose = CRSDocumentAppService_uploadPurposeVO::_BUY_REQUEST;
            $uploadResult = $uploaderModel->uploadDocuments($purpose);
            AuthOnlyBaseController::safeSerializeAndStoreObjInSession($uploadResult,
                self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT . $buyRequestId);
        }
    }

    private function retrieveUploadResultFromSession($buyRequestId) {
        $uploadResult = null;
        $uploadResultSessionKey = self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT . $buyRequestId;
        if (AuthOnlyBaseController::isSavedInSession($uploadResultSessionKey)) {
            $uploadResult =
                AuthOnlyBaseController::safeDeserializeObjFromFromSession($uploadResultSessionKey);
            Yii::app()->user->setState($uploadResultSessionKey, NULL);
        }
        return $uploadResult;
    }

    private function setUploadResultAsFlash($uploadResult) {
        $uploadResultWrapper = new UploaderResultModelWrapper($uploadResult);
        if (!$uploadResultWrapper->isEmpty()) {
            if ($uploadResultWrapper->hasErrors()) {
                $this->addFlash('error', $uploadResultWrapper->getErrorSummary());
            } else {
                $this->addFlash('success', $uploadResultWrapper->getSuccessSummary());
            }
        }
    }

    private function addFlash($key, $value) {
        if (Yii::app()->user->hasFlash($key)) {
            $currentFlash = Yii::app()->user->getFlash($key);
            if (is_array($currentFlash)) {
                $extendedFlash = array_merge($currentFlash, array($value));
            } else {
                $extendedFlash = array($currentFlash, $value);
            }
            Yii::app()->user->setFlash($key, $extendedFlash);
        } else {
            Yii::app()->user->setFlash($key, $value);
        }
    }

    private function getBuyRequestAndFillModelFromObject($model, $forEdition) {
        $buyRequest = null;
        $model->scenario = null;
        if ($model->validate()) {
            $buyRequest = $this->getBuyRequest($model->id);
            $model->scenario = $forEdition ? 'editing' : null;
        }
        if ($buyRequest === null) {
            $this->revalidateFlashes();
            $this->redirect('index.php?r=registranttransferbuyrequests/view');
        }
        $model->fillFromObject($buyRequest, $forEdition);
    }

    private function getBuyRequest($buyRequestId) {
        $result = null;
        $errors = null;
        CRSSecondaryMarketAppService_service::getBuyRequest($result, $errors,
            Yii::app()->user->authenticatedUser, $buyRequestId);
        if ($errors) {
            return null;
        } else {
            return $result;
        }
    }

    private function revalidateFlashes() {
        // This step is necessary if there is a chain of redirects and we don't want to lose the
        // flash messages.
        $this->revalidateFlash('success');
        $this->revalidateFlash('error');
    }

    private function revalidateFlash($key) {
        if (Yii::app()->user->hasFlash($key)) {
            Yii::app()->user->setFlash($key, Yii::app()->user->getFlash($key));
        }
    }

}
