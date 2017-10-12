<?php

class AllInvoiceModel extends GridModelBase {
    public $year;
    public $month;
    public $defaultSortColumn = 'A';
    var $paymentMethod = array('ADP' => 'Deposit account','CC' => 'Credit Card','DEB' => 'Debit Card');
    public $columns = array();

    public function arrayAsColModelSelectOptions($arr) {
        $s = ':(any);';
        $i = 0;
        $n = count($arr);
        foreach ($arr as $k => $v)
            $s .= $k . ':' . $v . (++$i < $n ? ';' : '');
        return $s;
    }

    private function createLink($number, $type) {
        $username = Yii::app()->user->authenticatedUser->username;
        $token = Yii::app()->user->authenticatedUser->authenticationToken;
        $url = Yii::app()->params['wsapi_soap_url'];
        // $link = "<a href='" . $url . "/view-invoice/invoice?userName=" . $username . "&token=" . $token . "&invoiceNumber=" . $number . "&fileType=" . $type . "'>" . $type . "</a>";
        $link = CHtml::link($type, Yii::app()->createUrl("account_view_invoice_history/download", array(
            'number' => $number,'type' => $type)));
        return $link;
    }

    private function getLink($number) {
        $result = null;
        $errs = array();
        $user = Yii::app()->user->authenticatedUser;
        CRSPermissionsAppService_service::getUserLevel($result, $errs, $user);
        if ($result != null) {
            switch ($result) {
                case UserPermissionLevel::DIRECT:
                case UserPermissionLevel::REGISTRAR:
                case UserPermissionLevel::SUPER_REGISTRAR:
                    $xml = $this->createLink($number, "xml");
                    $pdf = $this->createLink($number, "pdf");
                    return ($xml . "  " . $pdf);
                    break;
            }
        }
    }

    private function createLinkEmail($invoiceNumber) {
        $n = "'$invoiceNumber'";
        $link = '<a href="#" onclick="sendEmail(' . $n . ');">Send</a>';
        return $link;
    }

    public function __construct() {
        $this->year = date("Y");
        $this->month = intval(date("m"));
        parent::__construct();
        $this->columns = array(
            'A' => array('label' => 'Settlement Date','resultfield' => 'settlementDate',
                'criteriafield' => 'settlementDateLike','width' => 30,'type' => 'string'),
            'O' => array('label' => 'Payment Method','width' => 20,'resultfield' => 'paymentMethod',
                'criteriafield' => 'paymentMethod','wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions($this->paymentMethod)),
            'B' => array('label' => 'Invoice Number','resultfield' => 'invoiceNumber',
                'criteriafield' => 'invoiceNumber','width' => 23,'type' => 'string',
                'link' => 'account_view_invoice_history/invoiceview'),
            'C' => array('label' => 'Invoice Date','resultfield' => 'invoiceDate','criteriafield' => 'invoiceDateLike',
                'width' => 20,'type' => 'string'),
            'F' => array('label' => 'ex VAT','resultfield' => 'totalNetAmount','width' => 15,'type' => 'currency'),
            'G' => array('label' => 'VAT','resultfield' => 'totalVatAmount','width' => 15,'type' => 'currency'),
            'H' => array('resultfield' => 'totalCost','label' => 'Total','width' => 20,'type' => 'currency'),
            'I' => array('label' => 'Documents','width' => 30),
            'J' => array('label' => 'Email','width' => 30));
        $this->excludeFromExport = array('I', 'J');
    }

    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        $paymentType = '';
        switch ($o->paymentMethod) {
            case 'ADP':
                $paymentType = 'Deposit account';
                break;
            case 'CC':
                $paymentType = 'Credit Card';
                break;
            case 'DEB':
                $paymentType = 'Debit Card';
                break;
            default:
                $paymentType = null;
        }
        return array('A' => parseXmlDate($o->settlementDate),'O' => $paymentType,'B' => $o->invoiceNumber,
            'C' => parseXmlDate($o->invoiceDate),'F' => number_format($o->totalNetAmount, 2, '.', ' '),
            'G' => number_format($o->totalVatAmount, 2, '.', ' '),'H' => number_format($o->totalCost, 2, '.', ' '),
            'I' => "" . $this->getLink($o->invoiceNumber),'J' => '' . $this->createLinkEmail($o->invoiceNumber));
    }

    public function getSearchBase() {
        $criteria = new CRSPaymentAppService_plainInvoiceSearchCriteriaVO();
        $criteria->billingNH = Yii::app()->user->authenticatedUser->username;
        $monthStart = strtotime($this->year . '-' . $this->month . '-01');
        $criteria->settledFrom = date("Y-m-d", $monthStart);
        // t in date() format returns the number of days in the given month.
        $criteria->settledTo = date("Y-m-t", $monthStart);
        return $criteria;
    }

}

