<?php

/**
 * defines Nichandle_Details class
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
 * Model class for Nic-Handle, used to create/edit/view NicHandles
 *
 * MULTI-PHONE EXAMPLE : http://localhost/newregcon/index.php?r=nichandles/viewnichandle&id=AKG659-IEDR
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class Nichandle_Details extends ReturningModelBase {
    /**
     * nichandle ID; type=xs:string, (NOT NULL), min=0
     *
     * @var string
     * @access public
     */
    public $nicHandleId;
    /**
     * nichandle name, type=xs:string, (NOT NULL), min=0
     *
     * @var string
     * @access public
     */
    public $name;
    /**
     * company name, type=xs:string, (NOT NULL), min=0
     *
     * @var string
     * @access public
     */
    public $companyName;
    /**
     * address, type=xs:string, (NOT NULL), min=0
     *
     * @var string
     * @access public
     */
    public $address;
    /**
     * phones as array
     *
     * @var array
     * @access public
     */
    public $phones_array;
    /**
     * newline-separated list of phone numbers, type=xs:string, (null), min=0, max=unbounded
     *
     * @var unknown
     * @access public
     */
    public $phones;
    /**
     * array of fax numbers
     *
     * @var array
     * @access public
     */
    public $faxes_array;
    /**
     * newline-separated list of fax numbers
     *
     * @var string
     * @access public
     */
    public $faxes; // type=xs:string, (null), min=0, max=unbounded
    /**
     * address county; type=xs:string, (NOT NULL), min=0
     *
     * @var int
     * @access public
     */
    public $countyId;
    /**
     * address country; type=xs:string, (NOT NULL), min=0
     *
     * @var int
     * @access public
     */
    public $countryId;
    /**
     * email; type=xs:string, (NOT NULL), min=0
     *
     * @var string
     * @access public
     */
    public $email;
    /**
     * Indicates if nic handle can be a billing contact, that is if they can make a payment for domain.
     *
     * @var boolean
     * @access public
     */
    public $canBeABillingContact;
    /**
     * VAT no; type=xs:string, (NOT NULL), min=0
     *
     * @var string
     * @access public
     */
    public $vatNo;
    /**
     * array of field-names which can be copied unmodified (all except phones and faxes)
     *
     * @var array
     * @access public
     */
    public $nonPhone_fieldList = array('nicHandleId', 'address', 'companyName', 'countryId',
        'countyId', 'email', 'name', 'vatNo');

    /**
     * constructor, sets defaults
     *
     * @return void
     * @access public
     */
    function __construct() {
        parent::__construct();
        // some defaults:
        $this->countryId = getCountryByName('Ireland');
        $this->countyId = getCountyByName($this->countryId, 'Co. Dublin');
        $this->faxes_array = array();
        $this->phones_array = array();
        $this->scenario = 'nameEditable';
    }

    /**
     * returns Yii validation rules
     *
     * @return array validation rules
     * @access public
     */
    public function rules() {
        $rules = array(array('email, address, countryId, countyId, phones', 'required'),
            array('companyName, email, address, phones, faxes, name, vatNo', 'Utf8Validator'),
            array('address', 'filter', 'filter' => array($this, 'trimNewLines')),
            array('phones,faxes', 'PhoneNumberValidator'),
            array('name, companyName, address', 'length', 'max' => 255),
            array('email', 'EmailAddrValidator'),
            array('countryId', 'validateCountry'),
            array('countyId', 'validateCounty'),
            array('returningData', 'safe'),
            array('name', 'required', 'on' => 'nameEditable')
        );
        return $rules;
    }

    /**
     * returns array of field labels
     *
     * @return array field labels
     * @access public
     */
    public function attributeLabels() {
        return array('nicHandleId' => 'Account Number', 'name' => 'Name',
            'companyName' => 'Company Name', 'address' => 'Address', 'email' => 'Email',
            'countryId' => 'Country', 'countyId' => 'County', 'phones' => 'Phone Number(s): Mobile or Landline',
            'faxes' => 'Fax Number(s)', 'vatNo' => 'VAT Number');
    }

    /**
     * returns array of fieldnames to be returned in a returning-data form
     *
     * fields mode, nictype, and nic will be returned to the caller
     *
     * @return array array of fieldnames to be returned in a returning-data form
     * @access public
     * @see ReturningController, ReturningController::outputReturningFormHtml()
     */
    function getReturningDataNames() {
        return array_merge(parent::getReturningDataNames(), array('mode', 'nictype', 'nic'));
    }

    function validateCountry($attrib, $params) {
        $countries = getCountryOptions();
        if (!isset($countries[$this->$attrib])) {
            $this->addError($attrib, "Invalid country name.");
        }
    }

    function validateCounty($attrib, $params) {
        $counties = getCountyOptions($this->countryId);
        if (!isset($counties[$this->$attrib])) {
            $this->addError($attrib, "Invalid county for chosen country.");
        }
    }

    /**
     * sets phone/fax strings and array attributes from data in supplied CRS-WS-API object
     *
     * @param
     *            array &$a model array member, will be overwritten with new array
     * @param CRSNicHandleAppService_nicHandleEditVO $obj
     *            CRS-WS-API object or copy data from
     * @param string $om
     *            name of phones attribute, either 'phones' of 'faxes'
     * @return void
     * @access protected
     */
    protected function fillPhonesArrayFromObject(&$a, $obj, $om) {
        $a = array();
        if (isset($obj->$om))
            $a = array_merge((array) ($obj->$om), $a);
        $this->$om = implode($a, "\n");
    }

    /**
     * copies data from a CRS-WS-API NicHandle object into this instance
     *
     * @param object $obj
     *            Parameter description (if any) ...
     * @param unknown $errs
     *            Parameter description (if any) ...
     * @return void
     * @access public
     */
    public function fillFromObject($obj, $errs) {
        $this->error = $errs;
        foreach ($this->nonPhone_fieldList as $f) {
            if (isset($obj->$f)) {
                $this->$f = $obj->$f;
            }
        }
        $this->countryId = $obj->country->id;
        $this->countyId = $obj->county->id;
        $this->fillPhonesArrayFromObject($this->phones_array, $obj, 'phones');
        $this->fillPhonesArrayFromObject($this->faxes_array, $obj, 'faxes');
    }

    /**
     * returns this instance's data copied into newly-created CRS-WS-API object
     *
     * @return CRSNicHandleAppService_nicHandleEditVO this instance's data copied into newly-created CRS-WS-API object
     * @access public
     */
    public function getAsObject() {
        $nhvo = new CRSNicHandleAppService_nicHandleEditVO();
        $nhvo->accountNumber = Yii::app()->user->id;
        foreach ($this->nonPhone_fieldList as $f)
            $nhvo->$f = $this->$f;
        $nhvo->phones = $this->phones_array;
        $nhvo->faxes = $this->faxes_array;
        return $nhvo;
    }

}

