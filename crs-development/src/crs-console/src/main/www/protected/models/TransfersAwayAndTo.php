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
abstract class TransfersAwayAndTo extends GridModelBase {
    var $transferFrom = '';
    var $transferTo = '';
    /**
     * Transfers in the last DEFAULT_DAYS will be displayed for the first time and
     * every time user enters incorrect value for number of days.
     */
    const DEFAULT_DAYS = 30;
    /**
     * Number of days for which we display transfers.
     *
     * @var integer
     * @access public
     */
    var $days = self::DEFAULT_DAYS;
    /**
     * Description for public
     *
     * @var string
     * @access public
     */
    public $defaultSortColumn = 'A';
    /**
     * Description for var
     *
     * @var array
     * @access public
     */
    var $columns = array('A' => 'initialized later',
        'B' => array('resultfield' => 'holder', 'criteriafield' => 'domainHolder',
            'sortby' => 'holder', 'label' => 'Holder', 'width' => 60, 'type' => 'string'),
        'C' => array('resultfield' => 'transferDate', 'criteriafield' => 'transferDate',
            'label' => 'Transfer Date', 'width' => 20, 'type' => 'date'),
        'D' => array('resultfield' => 'registrationDate', 'criteriafield' => 'registrationDate',
            'label' => 'Registration Date', 'width' => 20, 'type' => 'date'),
        'E' => array('resultfield' => 'renewalDate','criteriafield' => 'renewalDate',
            'label' => 'Renewal Date', 'width' => 20, 'type' => null));

    public function resetDays() {
        $this->days = self::DEFAULT_DAYS;
    }

    public function __construct() {
        $to = date_create();
        $from = date_modify($to, '-' . $this->days . ' day');
        $to = date_create();
        $this->transferFrom = date_format($from, 'Y-m-d');
        $this->transferTo = date_format($to, 'Y-m-d');
        $this->initDomainField();
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
     * @param array $o
     *            Parameter description (if any) ...
     * @return mixed Return description (if any) ...
     * @access public
     */
    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        return array('A' => cleanString(encode($o->name)),'B' => cleanString(encode($o->holder)),
            'C' => parseXmlDate($o->transferDate),'D' => parseXmlDateWithDefault($o->registrationDate),
            'E' => parseXmlDateWithDefault($o->renewalDate));
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return string Return description (if any) ...
     * @access protected
     */
    protected function getExportFilenameTag() {
        return 'TransfersAwayAndTo_';
    }

    public function setSearchCriteria() {
        $to = date_create();
        $from = date_modify($to, '-' . $this->days . ' day');
        $to = date_create();
        $this->transferFrom = date_format($from, 'Y-m-d');
        $this->transferTo = date_format($to, 'Y-m-d');
    }

    public function getSearchBase() {
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->transferFrom = $this->transferFrom;
        $criteria->transferTo = $this->transferTo;
        return $criteria;
    }

    abstract protected function initDomainField();

}

