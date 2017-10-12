<?php

/**
 * defines EditTicketModel class
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
 * model for data for the ticket-editing page
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class EditTicketModel extends ViewTicketModel {
    public $domain_name;
    public $expiryDate;
    public $editable;

    /**
     * constructor, sets ticket ID
     *
     * @param integer $id
     *            ticket ID
     * @return void
     * @access public
     */
    public function __construct($id = null) {
        parent::__construct($id);
    }

    /**
     * returns Yii validation rules
     *
     * @return array validation rules
     * @access public
     */
    public function rules() {
        return array(array('id,domainHolder,adminContact_0,techContact,requestersRemark','required'),
            array(
                'id,domainHolder,adminContact_0,adminContact_1,billingContact,techContact,requestersRemark,charityCode',
                'Utf8Validator'),array('requestersRemark','filter','filter' => array('FormModelBase','trimNewlines')),
            array('ipv4Addresses,ipv6Addresses','safe'),
            array('id,nameserversCount','numerical','integerOnly' => true,'min' => 1,'max' => 9999999999),
            array('adminContact_0,adminContact_1,billingContact,techContact','NicHandleValidator'),
            array('domainHolder','length','max' => 255),
            array('requestersRemark','length','max' => 10000),
            array('charityCode','length','max' => 20),
            array('nameservers','NameserverListComplexValidator','dname' => 'domainName'),
            array('adminContact_0,adminContact_1','UniquenessValidator',
                'fields' => array('adminContact_0','adminContact_1'),'label' => 'admin contact'));
    }

    /**
     * copies non-editable data from ticket-object into this instance
     *
     * @param CRSTicketAppService_ticketVO $ticketVO
     *            Ticket object to copy data from
     * @return void
     * @access public
     */
    public function setReadOnlyDataFromObj($ticketVO) {
        $ro_flds = array('id', 'domainName', 'adminStatus', 'hostmastersRemark', 'type', 'techStatus', 'period',
            'paymentType', 'charityCode');
        foreach ($ro_flds as $f) {
            if (isset($ticketVO->$f)) {
                $this->$f = $ticketVO->$f;
            }
        }
        $this->expiryDate = Utility::getTicketExpirationDate($ticketVO->creationDate);
        $this->checkedOutTo = isset($ticketVO->checkedOutTo) ? $ticketVO->checkedOutTo : null;
        $this->editable = empty($this->checkedOutTo) ? "1" : "0";
        $this->billingContact = $ticketVO->operation->billingContactsField->newValue->nicHandle;
        if ($this->type === "Transfer") {
            $this->domainHolder = $ticketVO->operation->domainHolderField->newValue;
        }
    }

    protected function setErrorsFromFailureReasons($obj) {
    }

}

