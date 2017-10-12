<?php

class RegistrantTransferSaleModel extends FormModelBase {

    public $domainName;

    public function rules() {
        return array(array('domainName', 'required'),
            array('domainName', 'Utf8Validator'),
            array('domainName', 'filter', 'filter' => array('Utility','mb_trim')),
            array('domainName', 'filter', 'filter' => 'mb_strtolower'),
            array('domainName', 'canDomainBeSold'));
    }

    public function attributeLabels() {
        return array('domainName' => 'Domain Name');
    }

    public function canDomainBeSold($attribute, $params) {
        $domainName = $this->$attribute;
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        $errs = '';
        CRSSecondaryMarketAppService_service::canDomainBeSold($result, $errs, $user, $domainName);
        if (!$result) {
            $this->addError($attribute, encode($domainName) .
                " does not exist, is in invalid state or has no buy requests registered");
        }
    }

}