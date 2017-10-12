<?php

class DnsController extends AuthOnlyBaseController {

    public function actionDnsupdate() {
        $model = new DNS_BulkMod();
        if (Yii::app()->request->isPostRequest && isset($_POST['DNS_BulkMod'])) {
            $model->attributes = $_POST['DNS_BulkMod'];
            $this->performAjaxValidation($model);
            if ($model->validate()) {
                $this->saveDNS($model);
            }
            $this->render('dnsupdate', array('model' => $model));
        } elseif (isset($_REQUEST['gridaction']['domainlist'])) {
            $model->setdomainlist($_REQUEST['gridaction']['domainlist']);
            if (!$model->isDomainListValid()) {
                Yii::app()->user->setFlash('error', 'Invalid domain list supplied.');
                $this->redirect(array('nsreports/dnsserversearch'), true);
            } else {
                $this->render('dnsupdate', array('model' => $model));
            }
        } else {
            $this->redirect(array('nsreports/dnsserversearch'), true);
        }
    }

    public function saveDNS($model) {
        $nameservers = array();
        for ($i = 0; $i < $model->nameserversCount; $i++) {
            $nameserver = new CRSDomainAppService_nameserverVO();
            $nameserver->name = $model->getNsName($i);
            $nameservers[] = $nameserver;
        }
        $hostmasterRemark = 'DNS Update via CRS-WS-API';
        $errors = '';
        CRSCommonAppService_service::modifyNameservers($errors, Yii::app()->user->authenticatedUser, $model->domainlist, $nameservers, $hostmasterRemark);
        if (count($errors) == 0) {
            Yii::app()->user->setFlash('success', 'DNS Modified successfully.');
        } else {
            Yii::app()->user->setFlash('error', 'Modify DNS failure <br>' . str_ireplace("\n", "<br/>", str_ireplace('SOAP Exception : NameserversValidationException : ', '', $errors)));
        }
    }

}
