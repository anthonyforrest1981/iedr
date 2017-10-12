<?php

/**
 * Short description for file
 *
 * Long description (if any) ...
 *
 * PHP version 5
 *
 * New Registration Console (C) IEDR 2011
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
/**
 * Short description for class
 *
 * Long description (if any) ...
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 * @see References to other sections (if any)...
 */
class NewDomainsModel extends GridModelBase {
    // "AAE553-IEDR have registered 2168 domains over the last 150 days or 14.45 per day."
    /**
     * Description for var
     *
     * @var number
     * @access public
     */
    var $days;
    public $defaultSortColumn = 'A';
    public $columns = array();

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param integer $days
     *            Parameter description (if any) ...
     * @return void
     * @access public
     */
    public function __construct($days = 30) {
        $this->days = $days;
        $this->columns = array(
            'A' => array('resultfield' => 'name', 'criteriafield' => 'domainName',
                'label' => 'Domain Name', 'width' => 24, 'link' => 'domains/viewdomain',
                'type' => 'string'),
            'B' => array('resultfield' => 'holder', 'criteriafield' => 'domainHolder',
                'label' => 'Holder', 'width' => 24, 'type' => 'string'),
            'C' => array('resultfield' => 'dsmNrpStatus', 'criteriafield' => 'shortNRPStatus',
                'label' => 'Status', 'width' => 16, 'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getAllShortStatuses())),
            'D' => array('resultfield' => 'registrationDate', 'criteriafield' => 'registrationDate',
                'label' => 'Registration Date', 'width' => 18, 'type' => 'datefilter'),
            'E' => array('resultfield' => 'renewalDate', 'criteriafield' => 'renewalDate',
                'label' => 'Renewal Date', 'width' => 18, 'type' => 'datefilter'));
    }

    public function getSearch($searchparams) {
        $criteria = parent::getSearch($searchparams);
        if (isset($criteria->accountId))
            $criteria->accountId = null;
        foreach ($searchparams as $key => $value) {
            if (is_object($value) && isset($value->field)) {
                $field = $this->columns[$value->field]['criteriafield'];
                if ($field != null) {
                    switch ($field) {
                        case 'registrationDate':
                            $dates = mb_split(" ", $value->data);
                            $criteria->registrationFrom = $dates[0];
                            $criteria->registrationTo = $dates[1];
                            unset($criteria->$field);
                            break;
                        case 'renewalDate':
                            $dates = mb_split(" ", $value->data);
                            $criteria->renewalFrom = $dates[0];
                            $criteria->renewalTo = $dates[1];
                            unset($criteria->$field);
                            break;
                    }
                }
            }
        }
        Yii::log('SEARCH CRITERIA = ' . print_r($criteria, true) . ' IN NewDomainsModel');
        return $criteria;
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return object Return description (if any) ...
     * @access public
     */
    public function getSearchBase() {
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->accountId = Yii::app()->user->id;
        $criteria->registrationFrom = date('Y-m-d', time() - (86400 * $this->days));
        $criteria->registrationTo = date('Y-m-d', time() + 86400);
        return $criteria;
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return mixed Return description (if any) ...
     * @access public
     */
    public function rules() {
        return array(array('days','required'),array('days','length','min' => 1,'max' => 4),
            array('days','numerical','min' => 1,'max' => 9999,'integerOnly' => true));
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return mixed Return description (if any) ...
     * @access public
     */
    public function attributeLabels() {
        return array('days' => 'Days');
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param array $searchparams
     *            Parameter description (if any) ...
     * @return object Return description (if any) ...
     * @access public
     */
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
    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        $dsm = $o->dsmState->shortNrpStatus;
        return array('A' => encode($o->name), 'B' => cleanString(encode($o->holder)), 'C' => $dsm,
            'D' => parseXmlDate($o->registrationDate), 'E' => parseXmlDate($o->renewalDate));
    }

}

