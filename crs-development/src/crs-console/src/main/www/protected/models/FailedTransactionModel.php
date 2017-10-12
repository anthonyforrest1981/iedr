<?php

/**
 * defines AllTicketsModel class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */
/**
 * model for jqGrid view of all pending tickets for an account
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class FailedTransactionModel extends GridModelBase {
    /**
     * show tickets changed since 'days' ago
     *
     * @var integer
     * @access public
     */
    var $days;
    /**
     * default sort column-index for grid data
     *
     * @var string
     * @access public
     */
    public $defaultSortColumn = 'A';
    /**
     * Column model for jqGrid
     *
     * @var array
     * @access public
     */
    var $columns = array(
        'A' => array('resultfield' => 'id','criteriafield' => null,'label' => 'Ticket Number','width' => 35,
            'link' => 'tickets/viewticket','type' => 'int'),
        'B' => array('resultfield' => 'creationDate','criteriafield' => 'creationDate',
            'label' => 'Ticket Creation Date','width' => 90),
        'C' => array('resultfield' => 'domainName','criteriafield' => 'domainName','label' => 'Domain Name',
            'width' => 90,'type' => 'string'),
        'D' => array('resultfield' => 'type','criteriafield' => 'type','label' => 'Ticket Type','width' => 60));

    /**
     * returns default search criteria object
     *
     * @return CRSTicketAppService_ticketSearchCriteriaVO default search criteria object
     * @access public
     */
    public function getSearchBase() {
        // override to set the filter
        $criteria = new CRSTicketAppService_ticketSearchCriteriaVO();
        $criteria->accountId = Yii::app()->user->id;
        $criteria->financialStatus = CRSTicketAppService_financialStatus::_STALLED;
        $criteria->to = date('Ymd');
        return $criteria;
    }

    public function getSort($sortparams) {
        $sc = new CRSTicketAppService_sortCriterion();
        $sc->ascending = true; // type=xs:boolean, (NOT NULL), min=1
        if ($sortparams['ord'] == "desc") {
            $sc->ascending = false;
        }
        if (is_null($this->columns[$sortparams['col']]['criteriafield'])) {
            $sc->sortBy = 'id'; // type=xs:string, (NOT NULL), min=0
        } else {
            $sc->sortBy = $this->columns[$sortparams['col']]['criteriafield'];
        }
        return $sc;
    }

    /**
     * returns fromatted ticket object data as an array, suitable for base class jqGrid-data handling
     *
     * @param CRSTicketAppService_ticketVO $o
     *            Ticket object
     * @return array array-format fromatted ticket data
     * @access public
     */
    public function addResults($o) {
        ModelUtility::hasProperty($this->columns, $o);
        $d = new DateTime($o->creationDate);
        return array('A' => $o->id,'B' => parseXmlDate($o->creationDate),'C' => $o->domainName,'D' => $o->type);
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
        return array(array('days','numerical'),array('days','length','max' => 3));
    }

}

