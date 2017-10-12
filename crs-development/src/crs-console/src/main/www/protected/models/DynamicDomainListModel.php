<?php

class DynamicDomainListModel extends DynamicModelBase {
    public $domainlist;
    protected $_domainlistArray;
    public $domainlistWithSummary;

    public function __construct() {
        parent::__construct();
        $this->val_rules = array(array('domainlist'));
        $this->attr_labels = array('domainlist' => 'Domains to be Processed');
    }

    private function addDomainToList($d) {
        if (IEDomainValidator::isValid($d))
            $this->_domainlistArray[$d] = $d;
        else
            Yii::log('Invalid domain : "' . $d . '"', 'error', 'DynamicDomainListModel::addDomainToList()' . __LINE__);
    }

    public function setDomainListFromArray($a) {
        $this->domainlist = implode(',', $a);
        $this->_domainlistArray = null;
    }

    public function domainListToArray() {
        // turn posted comma-separated list of domains into an array
        if ($this->_domainlistArray == null or !is_array($this->_domainlistArray)) {
            $this->_domainlistArray = array();
            $s = $this->domainlist;
            if ($s != null and mb_strlen($s) >= 4/*min valid domain name len*/) {
                if (!(mb_strpos($s, ',') === false))
                    foreach (mb_split(',', $s) as $k => $v)
                        $this->addDomainToList($v);
                else
                    $this->addDomainToList($s);
            }
            // else : null, empty or invalid string - ignore
        }
        sort($this->_domainlistArray);
        return $this->_domainlistArray;
    }

    public static function domainToVarPrefix($domain) {
        return 'domlist_' . str_replace('.ie', '', $domain);
    }

    public function setFromPOST($p) {
        // first copy the GridSelectionModel data from POST (including 'domainlist')
        parent::setFromPOST($p);
        // then create all the dynamic fields from the 'domainlist'
        $this->createDomainConfirmList();
        // then again set the dynamically-created (incl. checkbox) fields from the POST
        parent::setFromPOST($p);
        // remove from list any domains that were unchecked
    }

    public function copyFromOther($o) {
        parent::copyFromOther($o);
        // then create all the dynamic fields from the 'domainlist'
        $this->createDomainConfirmList();
    }

    public function removeUncheckedDomains() {
        // remove domains from list(s) that are un-checked
        if ($this->domainlist != null) {
            $to_remove = array();
            foreach ($this->domainListToArray() as $k => $v) {
                $mbrnam = DynamicDomainListModel::domainToVarPrefix($v);
                $mbr_conf = $mbrnam . '_confirmed';
                // test checkbox state
                if (isset($this->$mbr_conf) && ($this->$mbr_conf) != null) {
                    if ($this->$mbr_conf != 1) {
                        $to_remove[$k] = $v;
                    }
                }
            }
            Yii::log('removing domains : ' . print_r($to_remove, true), 'debug', 'DynamicDomainListModel::removeUncheckedDomains()');
            $this->setDomainListFromArray(array_diff($this->domainListToArray(), $to_remove));
            Yii::log('remaining domains : ' . print_r($this->domainlist, true), 'debug', 'DynamicDomainListModel::removeUncheckedDomains()');
        } else
            Yii::log('domainlist not set', 'error', 'DynamicDomainListModel::removeUncheckedDomains()');
    }

    public function createDomainConfirmList() {
        // add model data members - to represent one checkbox and dropdownlist _per_domain_ - from a list of domains
        if ($this->domainlist != null) {
            foreach ($this->domainListToArray() as $k => $v) {
                $mbrnam = DynamicDomainListModel::domainToVarPrefix($v);
                // 'confirmed' for confirm checkbox
                $mbr_conf = $mbrnam . '_confirmed';
                $this->$mbr_conf = true;
                $this->addValRule($mbr_conf, 'boolean');
                $this->attr_labels[$mbr_conf] = $v;
                Yii::log('labels= ', print_r($mbr_conf, true));
            }
        } else
            Yii::log('domainlist WAS NULL .. ' . print_r($this, true), 'error', 'DynamicDomainListModel::createDomainConfirmList()');
    }

}
