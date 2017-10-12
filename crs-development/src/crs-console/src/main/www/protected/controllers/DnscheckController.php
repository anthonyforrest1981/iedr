<?php

class DnscheckController extends Controller {

    public function actionCheck() {
        $model = new DnsCheckModel();
        if (isset($_POST['ajax']) && $_POST['ajax'] === 'dnscheck') {
            echo CActiveForm::validate($model);
            Yii::app()->end();
        } else if (Yii::app()->request->isPostRequest && isset($_POST['DnsCheckModel'])) {
            $model->setFromPOST($_POST['DnsCheckModel']);
            $this->performAjaxValidation($model);
            $model->scenario = 'submit';
            if ($model->validate()) {
                $response = $this->verifyDns($model->domainName, $model->getNameserverList(), false);
                if (empty($response)) {
                    Yii::app()->user->setFlash("notice", "DNS Check passed");
                } else if (strpos($response, 'FATAL')) {
                    Yii::app()->user->setFlash("failure", $response);
                } else {
                    Yii::app()->user->setFlash("error", $response);
                }
            }
        }
        $this->render('check', array('model' => $model));
    }

    public function actionVerifyDns() {
        if (!Yii::app()->request->isPostRequest) {
            echo "Sorry, request is malformed and cannot be processed.";
            Yii::app()->end();
        }
        $response = $this->verifyDns($_POST['domains'], $_POST['nameservers'], true);
        echo $response;
    }

    private function verifyDns($domainsFromPost, $nameserversFromPost, $sendError) {
        try {
            if (!isset($domainsFromPost)) {
                return "Domain name cannot be empty.";
            }
            if (!isset($nameserversFromPost)) {
                return "Nameservers cannot be empty.";
            }

            $model = new NameserversModel();
            $nameservers = array();
            $req_ns = is_array($nameserversFromPost) ? $nameserversFromPost : array($nameserversFromPost);
            foreach ($req_ns as $ns) {
                $nameserver = new CRSDomainAppService_nameserverVO();
                $nameserver->name = Utf8Validator::validateAndNormalizeUtf8($ns['name']);
                $nameserver->ipv4Address = Utf8Validator::validateAndNormalizeUtf8($ns['ipv4Address']);
                $nameserver->ipv6Address = Utf8Validator::validateAndNormalizeUtf8($ns['ipv6Address']);
                $nameservers[] = $nameserver;

                $model->nameservers[] = $nameserver->name;
                $model->ipv4[] = $nameserver->ipv4Address;
                $model->ipv6[] = $nameserver->ipv6Address;
            }
            $model->count = count($model->nameservers);
            $domains = array();
            $req_domains = is_array($domainsFromPost) ? $domainsFromPost : array($domainsFromPost);
            foreach ($req_domains as $dom) {
                $normalizedDomainName = is_string($dom) ? Utf8Validator::validateAndNormalizeUtf8($dom) : '';
                if (!HostNameValidator::isValid($normalizedDomainName)) {
                    return "Domain $normalizedDomainName is not a valid hostname";
                }
                $domains[] = $normalizedDomainName;
            }
            $model->domains = $domains;
            if (!$model->validate()) {
                return "Please correct your nameservers";
            }

            $response = null;
            $errors = '';
            $old = ini_get('default_socket_timeout');
            ini_set('default_socket_timeout', '90');
            $validated = CRSCommonAppService_service::validateNameservers($response, $errors,
                    $domains, $nameservers, Yii::app()->user->name, $sendError);
            ini_set('default_socket_timeout', $old);
            if ($validated) {
                return $response->message;
            } else {
                return "Sorry, there were errors while trying to validate nameservers.";
            }
        } catch (Exception $e) {
            Yii::log($e->getMessage(), CLogger::LEVEL_ERROR);
            return "Sorry, request is malformed and cannot be processed.";
        }
    }

}

/**
 * Helper class to trigger nameserver validation in verifyDNS
 */
class NameserversModel extends CFormModel {

    public $count;
    public $domains;
    public $nameservers;
    public $ipv4;
    public $ipv6;

    function init() {
        $this->domains = array();
        $this->nameservers = array();
        $this->ipv4 = array();
        $this->ipv6 = array();
    }

    public function rules()
    {
        return array(
            array('nameservers','NameserverListComplexValidator',
                'dname' => 'domains',
                'ipv4Name' => 'ipv4',
                'ipv6Name' => 'ipv6',
                'nameserversCount' => 'count'),
        );
    }
}
