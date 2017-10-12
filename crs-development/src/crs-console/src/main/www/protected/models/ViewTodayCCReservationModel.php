<?php

/**
 * Description of ViewTodayCCReservationModel
 *
 * @author Artur Kielak
 */
class ViewTodayCCReservationModel extends GridModelBase {
    var $errors = '';
    var $domainName = '';
    var $totalResults = 0.0;
    var $totalAmount = 0.0;
    var $totalVat = 0.0;
    var $totalWithVat = 0.0;
    public $columns;
    public $defaultSortColumn = 'PK';

    public function __construct() {
        $this->excludeFromExport = array('A');
        $this->columns = array(
            'PK' => array('label' => 'Domain','resultfield' => 'domainName','type' => 'hidden'),
            'A' => array('label' => 'Domain','resultfield' => 'domainName','criteriafield' => 'domainName',
                'width' => 17),
            'B' => array('label' => 'Reservation Type','width' => 10,'resultfield' => 'operationType',
                'criteriafield' => 'operationType','wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getReservationTypes())),
            'C' => array('label' => 'Duration','resultfield' => 'durationMonths','width' => 6),
            'D' => array('label' => 'Creation Date','resultfield' => 'creationDate','width' => 10),
            'E' => array('label' => 'Fee Amount','resultfield' => 'amount','width' => 10,'type' => 'currency'),
            'F' => array('label' => 'Vat Amount','resultfield' => 'vatAmount','width' => 9,'type' => 'currency'),
            'G' => array('label' => 'Total Amount','resultfield' => 'total','width' => 10,'type' => 'currency'),
            'H' => array('label' => 'Order ID','resultfield' => 'orderId','width' => 12),
            'J' => array('label' => 'Ticket','resultfield' => 'ticketId','width' => 7),
            'K' => array('label' => 'Financial Status','resultfield' => 'financialStatus','width' => 10));
    }

    public function arrayAsColModelSelectOptions($arr) {
        $s = ':(any);';
        $i = 0;
        $n = count($arr);
        foreach ($arr as $k => $v)
            $s .= $k . ':' . $v . (++$i < $n ? ';' : '');
        return $s;
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param object $o
     *            Parameter description (if any) ...
     * @return mixed Return description (if any) ...
     * @access public
     */
    public function setErrors($errors) {
        $this->errors = $errors;
    }

    public function setTotalResults($totalResults) {
        $this->totalResults = $totalResults;
    }

    public function setTotalAmount($totalAmount) {
        $this->totalAmount = $totalAmount;
    }

    public function setTotalVat($totalVat) {
        $this->totalVat = $totalVat;
    }

    public function setTotalWithVat($totalWithVat) {
        $this->totalWithVat = $totalWithVat;
    }

    public function getErrors() {
        return $this->errors;
    }

    public function getTotalResults() {
        return $this->totalResults;
    }

    public function getTotalAmount() {
        return $this->totalAmount;
    }

    public function getTotalVat() {
        return $this->totalVat;
    }

    public function getTotalWithVat() {
        return $this->totalWithVat;
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('PK' => $o->domainName,'A' => $this->createLink($o->domainName, $o->ticketId, $o->ticketExists),
            'B' => $o->operationType,'C' => ($o->durationMonths / 12),'D' => parseXmlDate($o->creationDate),
            'E' => number_format($o->netAmount, 2),'F' => number_format($o->vatAmount, 2),
            'G' => number_format($o->total, 2),'H' => $o->orderId,'J' => $o->ticketId,'K' => $o->financialStatus);
    }

    private function createLink($domainName, $ticketId, $ticketExists) {
        // ticket does not exist - it has been accepted and the domain has been already registered/transferred
        if (!$ticketExists) {
            return CHtml::link($domainName, Yii::app()->createUrl("domains/viewdomain", array('id' => $domainName)));
        } else {
            return CHtml::link($domainName, Yii::app()->createUrl("tickets/viewticket", array('id' => $ticketId)));
        }
    }

    public function getSearchBase() {
        return new CRSPaymentAppService_reservationSearchCriteriaVO();
    }

    public function getSort($sortparams) {
        return parent::getSort($sortparams);
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        $criteria->accountId = Yii::app()->user->id;
        $criteria->paymentMethod = CRSPaymentAppService_paymentMethod::_CC;
        foreach ($searchparams as $key => $value) {
            if (is_object($value) and isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                if ($field != null) {
                    if ($field == 'domainName')
                        $criteria->$field = $value->data;
                }
            }
        }
        Yii::log('SEARCH CRITERIA = ' . print_r($criteria, true) . ' IN ViewTodayCCReservationModel');
        return $criteria;
    }

}
?>
