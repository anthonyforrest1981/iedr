<?php

/**
 * Description of AccountDepositModel
 *
 * @author Artur Kielak
 */
class AccountDepositModel extends GridModelBase {
    public $url;

    public function __construct() {
        $this->defaultSortColumn = 'A';
        $this->defaultSortDirection = 'desc';
    }
    public $columns = array(
        'A' => array('label' => 'Date', 'width' => 25, 'resultfield' => 'id',
            'criteriafield' => 'transactionDate', 'type' => 'datefilter'),
        'PK' => array('label' => 'Payment Type', 'resultfield' => 'transactionType',
            'criteriafield' => null, 'width' => 20),
        'B' => array('label' => 'Remark', 'resultfield' => 'remark', 'criteriafield' => null,
            'width' => 20, 'sorting' => false),
        'C' => array('label' => 'Order ID', 'resultfield' => 'orderId', 'criteriafield' => null,
            'width' => 20),
        'D' => array('label' => 'Total Amount', 'resultfield' => 'transactionAmount',
            'criteriafield' => null, 'width' => 20, 'type' => 'currency'),
        'E' => array('label' => 'Deposit Balance', 'resultfield' => 'closeBal',
            'criteriafield' => null, 'width' => 20, 'type' => 'currency'));

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('A' => parseXmlDate($o->transactionDate),'PK' => $o->transactionType,
            'B' => isset($o->remark) ? $o->remark : '','C' => isset($o->orderId) ? $o->orderId : '',
            'D' => isset($o->transactionAmount) ? number_format($o->transactionAmount, 2) : '',
            'E' => isset($o->closeBal) ? number_format($o->closeBal, 2) : '');
    }

    public function getSearchBase() {
        return new CRSPaymentAppService_depositSearchCriteriaVO();
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        $criteria->nicHandleId = Yii::app()->user->authenticatedUser->username;
        foreach ($searchparams as $key => $value) {
            if (is_object($value) && isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                if ($field != null) {
                    switch ($field) {
                        case 'transactionDate':
                            $dates = mb_split(" ", $value->data);
                            $criteria->transactionDateFrom = $dates[0];
                            $criteria->transactionDateTo = $dates[1];
                            unset($criteria->$field);
                            break;
                    }
                }
            }
        }
        return $criteria;
    }

}
?>
