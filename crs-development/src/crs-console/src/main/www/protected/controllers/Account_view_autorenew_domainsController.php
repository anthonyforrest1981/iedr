<?php

/**
 * Description of Accounts_autorenewController
 *
 * @author Artur Kielak
 */
class Account_view_autorenew_domainsController extends GridController {

    public function showConfirmPage() {
        if (isset($_POST['gridaction'])) {
            switch ($_POST['gridaction']['command']) {
                case 'noautorenew':
                    $gs_model = $this->getSelectionModel(true);
                    break;
                default:
                    $gs_model = $this->getSelectionModel();
                    break;
            }
            $gs_model->setFromPOST($_POST['gridaction']);
            $this->processGridActionCommand($gs_model);
            $this->redirectAfterPost('account_view_autorenew_domains/confirmaction', $gs_model);
        } else {
            $this->redirectToHomePage();
        }
    }

    public function actionConfirm_billable() {
        $this->showConfirmPage();
    }

    public function actionMenu() {
        $model = new ARDomainModel();
        Utility::writeActionToSession('account_view_autorenew_domains/menu');
        $this->render('menu', array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $result = null;
        $user = Yii::app()->user->authenticatedUser;
        CRSDomainAppService_service::findPlainDomains($result, $this->backend_errors, $user, $criteria, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_view_autorenew_domainsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Autorenew domains";
    }

}
?>
