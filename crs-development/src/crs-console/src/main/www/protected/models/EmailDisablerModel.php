<?php

class EmailDisablerModel extends GridModelBase {
    public $defaultSortColumn = 'PK';
    public $disabledValuesByEmailId = array();
    public $successSubmitMessage = '';
    public $errorSubmitMessage = '';
    public $errorDisplayMessage = '';
    public $viewDataGrouped = array();
    public $columns = array('PK' => array('label' => 'Email ID','width' => 5,'type' => 'eID'),
        'B' => array('label' => 'Subject','width' => 25,'type' => 'string'),
        'C' => array('label' => 'External Recipients','criteriafield' => null,'width' => 20,
            'type' => 'externalRecipients'),
        'D' => array('label' => "Reason email can\'t be disabled",'width' => 20,'type' => 'string'),
        'E' => array('label' => 'Current Status','width' => 10,'type' => 'disabledStatus'));

    public function getSearch($searchparams) {
        return;
    }

    protected function getExportFilenameTag() {
        return 'EmailDisablerModel_';
    }

    public function addResults($o) {
        $et = $o->emailTemplate;
        $this->normalizeEmailTemplate($et);
        $toList = implode(', ', $et->toList);
        $ccList = implode(', ', $et->ccList);
        $bccList = implode(', ', $et->bccList);
        return array('PK' => encode($et->id),'B' => encode($et->subject),
            'C' => array('to' => encode($toList),'cc' => encode($ccList),'bcc' => encode($bccList)),
            'D' => encode($et->sendReason),
            'E' => array('eId' => encode($et->id),'disabled' => encode($o->disabled),
                'suppressible' => encode($et->suppressible),
                'nonSuppressibleReason' => encode($et->nonSuppressibleReason)));
    }
    // Helps normalize stdClass that is received from SOAP into something that resembles EmailTemplate, deals only with optional fields
    private function normalizeEmailTemplate(&$et) {
        $arrayFields = array('toList','ccList','bccList');
        foreach ($arrayFields as $field) {
            if (!isset($et->$field)) {
                $et->$field = array();
            } else if (!is_array($et->$field)) {
                $et->$field = array($et->$field);
            }
        }
        if (!isset($et->sendReason)) {
            $et->sendReason = '';
        }
        if (!isset($et->nonSuppressibleReason)) {
            $et->nonSuppressibleReason = '';
        }
    }

}
