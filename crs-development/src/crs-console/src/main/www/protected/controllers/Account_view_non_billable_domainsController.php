<?php

/**
 * Description of Account_view_none_billable_domainsController
 *
 * @author Artur Kielak
 */
class Account_view_non_billable_domainsController extends GridController {

    public function actionMenu() {
        $model = new NoBillableDomainModel();
        Utility::writeActionToSession('account_view_non_billable_domains/menu');
        $this->render('menu', array('model' => $model));
    }

    protected function getData($model, $criteria, $offset, $limit, $sort) {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        CRSDomainAppService_service::findPlainDomains($result, $this->backend_errors, $user, $criteria, $offset,
            $limit, $sort);
        if (count($this->backend_errors) > 0) {
            Yii::log(print_r($this->backend_errors, true), 'error',
                'Account_view_non_billable_domainsController::getData() : ERROR:' . __LINE__);
            throw new Exception();
        }
        return $result;
    }

    protected function getSearchName() {
        return "Non-Billable domains";
    }

}
?>
