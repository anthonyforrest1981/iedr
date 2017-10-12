<?php

class RegistrantTransferPurchaseModel extends FormModelBase {
    public $domainName;

    public function rules() {
        return array(array('domainName', 'required'),
            array('domainName', 'Utf8Validator'),
            array('domainName', 'filter', 'filter' => array('Utility','mb_trim')),
            array('domainName', 'filter', 'filter' => 'mb_strtolower'),
            array('domainName', 'canDomainBePurchased'));
    }

    public function attributeLabels() {
        return array('domainName' => 'Domain Name');
    }

    public function canDomainBePurchased($attribute, $params) {
        $domainName = $this->$attribute;
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        $errs = '';
        CRSSecondaryMarketAppService_service::canDomainBePurchased($result, $errs, $user, $domainName);
        if (count($errs) != 0) {
            $result = false;
            Yii::log("Error when checking if domain can be purchased: " . $errs);
        }
        if (!$result) {
            $this->addError($attribute,
                encode($domainName) . " does not exist or is in invalid state");
        }
    }

}
