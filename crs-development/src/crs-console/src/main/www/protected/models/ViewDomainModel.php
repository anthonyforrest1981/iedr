<?php

/**
 * defines ViewDomainModel class
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
 * Model class for viewing a Domain
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 * @see CRSDomainAppService_extendedDomainInfoVO
 */
class ViewDomainModel extends DynamicModelBase {
    /**
     * is used to store backend error message or exception string, for a view to render
     *
     * @var string
     * @access public
     */
    public $errors;
    /**
     * domain name
     *
     * @var unknown
     * @access public
     */
    public $domain;
    /**
     * is used to messages about backend operations, for a view to render
     *
     * @var array
     * @access public
     */
    public $message = array();
    /**
     * Company name
     *
     * @var string
     * @access public
     */
    public $domain_adminContacts;
    public $domain_adminContacts_companyName;
    public $domain_adminContacts_0_companyName;
    public $domain_adminContacts_1_companyName;
    /**
     * Admin-Contact Country
     *
     * @var string
     * @access public
     */
    public $domain_adminContacts_0_countryName;
    public $domain_adminContacts_1_countryName;
    /**
     * Admin-Contact Email
     *
     * @var string
     * @access public
     */
    public $domain_adminContacts_0_email;
    public $domain_adminContacts_1_email;
    /**
     * Admin-Contact Name
     *
     * @var string
     * @access public
     */
    public $domain_adminContacts_0_name;
    public $domain_adminContacts_1_name;
    /**
     * Admin-Contact Nic Handle
     *
     * @var string
     * @access public
     */
    public $domain_adminContacts_0_nicHandle;
    public $domain_adminContacts_1_nicHandle;
    public $domain_adminContacts_0_nicHandle_orig;
    public $domain_adminContacts_1_nicHandle_orig;
    /**
     * domain authorization code
     *
     * @var unknown
     * @access public
     */
    public $domain_authCode;
    public $domain_authCodeExpirationDate;
    /**
     * Billing-Contact Company Name
     *
     * @var string
     * @access public
     */
    public $domain_billingContacts_companyName;
    /**
     * Billing-Contact Country
     *
     * @var string
     * @access public
     */
    public $domain_billingContacts_countryName;
    /**
     * Billing-Contact Email
     *
     * @var string
     * @access public
     */
    public $domain_billingContacts_email;
    /**
     * Billing-Contact Name
     *
     * @var string
     * @access public
     */
    public $domain_billingContacts_name;
    /**
     * Billing-Contact Nic Handle
     *
     * @var string
     * @access public
     */
    public $domain_billingContacts_nicHandle;
    /**
     * Last Change Date
     *
     * @var string
     * @access public
     */
    public $domain_changeDate;
    /**
     * ? unused
     *
     * @var boolean
     * @access public
     */
    public $domain_clikPaid;
    /**
     * Creator-Contact Comany-Name
     *
     * @var string
     * @access public
     */
    public $domain_creator_companyName;
    /**
     * Creator-Contact Country
     *
     * @var string
     * @access public
     */
    public $domain_creator_countryName;
    /**
     * Creator-Contact Email
     *
     * @var string
     * @access public
     */
    public $domain_creator_email;
    /**
     * Creator-Contact Name
     *
     * @var string
     * @access public
     */
    public $domain_creator_name;
    /**
     * Creator-Contact Nic Handle
     *
     * @var string
     * @access public
     */
    public $domain_creator_nicHandle;
    /**
     * ? unused
     *
     * @var integer
     * @access public
     */
    public $domain_dateRoll;
    /**
     * Domain Status Date
     *
     * @var string
     * @access public
     */
    public $domain_domainStatusDate;
    /**
     * Domain Holder Name
     *
     * @var string
     * @access public
     */
    public $domain_holder;
    /**
     * Locked Status
     *
     * @var boolean
     * @access public
     */
    public $domain_lockedStatus;
    /**
     * Domain Name
     *
     * @var string
     * @access public
     */
    public $domain_name;
    /**
     * Nameservers
     *
     * @var array
     * @access public
     */
    public $domain_nameservers = array();
    /**
     * Nameserver IPv4 Addresses
     *
     * @var array
     * @access public
     */
    public $domain_ipv4Addresses = array();
    /**
     * Nameserver IPv6 Addresses
     *
     * @var array
     * @access public
     */
    public $domain_ipv6Addresses = array();
    /**
     * Number of nameservers
     *
     * @var int
     * @access public
     */
    public $domain_nameserver_count;
    public $maxRows;
    public $minRows;
    /**
     * Domain registration Date
     *
     * @var string
     * @access public
     */
    public $domain_registrationDate;
    /**
     * Remark
     *
     * @var string
     * @access public
     */
    public $domain_remark;
    /**
     * Renewal Date
     *
     * @var string
     * @access public
     */
    public $domain_renewalDate;
    /**
     * Reseller Account Has-Agreement-Signed
     *
     * @var boolean
     * @access public
     */
    public $domain_resellerAccount_agreementSigned;
    /**
     * Reseller Account Billing Contact (Nic Handle)
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_billingContact;
    /**
     * Reseller Account Last-Changed-Date
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_changeDate;
    /**
     * Reseller Account Creation Date
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_creationDate;
    /**
     * Reseller Account ID
     *
     * @var integer
     * @access public
     */
    public $domain_resellerAccount_id;
    /**
     * Reseller Account Name
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_name;
    /**
     * Reseller Account Remark
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_remark;
    /**
     * Reseller Account Status (Active/Suspended/Deleted)
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_status;
    /**
     * Reseller Account Status Change Date
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_statusChangeDate;
    /**
     * Reseller Account Can-Edit-Tickets
     *
     * @var boolean
     * @access public
     */
    public $domain_resellerAccount_ticketEdit;
    /**
     * Reseller Account web address
     *
     * @var string
     * @access public
     */
    public $domain_resellerAccount_webAddress;
    /**
     * Reseller Account Company Name
     *
     * @var string
     * @access public
     */
    public $domain_techContacts_companyName;
    /**
     * Tech-Contact Country
     *
     * @var string
     * @access public
     */
    public $domain_techContacts_countryName;
    /**
     * Tech-Contact Email
     *
     * @var string
     * @access public
     */
    public $domain_techContacts_email;
    /**
     * Tech-Contact Name
     *
     * @var string
     * @access public
     */
    public $domain_techContacts_name;
    /**
     * Tech-Contact Nic-Handle
     *
     * @var string
     * @access public
     */
    public $domain_techContacts_nicHandle;
    public $domain_techContacts_nicHandle_orig;
    /**
     * has Pending Tickets
     *
     * @var boolean
     * @access public
     */
    public $tickets;
    /**
     * has (recent) documents
     *
     * type=xs:boolean, (NOT NULL), min=1
     *
     * @var boolean
     * @access public
     */
    public $documents;
    /**
     * related domain names, not used
     *
     * @var unknown
     * @access public
     */
    public $relatedDomainNames; // type=xs:string, (null), min=0, max=unbounded
    /**
     * Pending Domain Names
     *
     * @var unknown
     * @access public
     */
    public $pendingDomainNames; // type=xs:string, (null), min=0, max=unbounded
    /**
     * DSM State
     *
     * @access public
     */
    public $domain_dsmState_customerType;
    public $domain_dsmState_nrpStatus;
    public $domain_dsmState_renewalMode;
    public $domain_dsmState_holderType;
    public $domain_dsmState_locked;
    public $domain_dsmState_secondaryMarketStatus;
    public $domain_billingStatus;
    public $domain_dsmState_voluntaryNRP = 0;
    public $domain_dsmState_nrp = 0;
    public $domain_dsmState_removeFromVoluntaryNRPPossible = 0;
    public $domain_dsmState_enterVoluntaryNRPPossible = 0;
    public $list;
    public $period;
    public $periodType;
    public $charitycode;
    public $paymentType;
    public $rules;
    public $domain_suspensionDate;
    public $domain_deletionDate;
    public $domain_lockingDate;
    public $domain_lockingRenewalDate;
    public $isActive = false;
    public $fullNRPStatus;
    public $authCodeMessage = false;

    /**
     * Constructor, initialises domain name
     *
     * @param string $name
     *            Domain Name
     * @return void
     * @access public
     */
    public function getNameServerById($id) {
        return $this->domain_nameservers[$id];
    }

    public function getStringNameServerById($id) {
        return "domain_nameservers[$id]";
    }

    public function __construct($name = null) {
        parent::__construct();
        $this->domain_name = $name;
        $this->val_rules = array();
        $this->rules = $this->val_rules;
        $appConfig = Utility::getApplicationConfiguration();
        $this->maxRows = $appConfig->nameserversMaxCount;
        $this->minRows = $appConfig->nameserversMinCount;
    }

    /**
     * copies attributes from an object instance into this model
     *
     * @param CRSDomainAppService_extendedDomainInfoVO $obj
     *            CRSDomainAppService_extendedDomainInfoVO instance to copy data from
     * @param string $errs
     *            error string from domain retrieval, copied into model for view to display
     * @return void
     * @access public
     */
    public function fillFromObject($obj, $errs) {
        $flds = array('tickets','documents','relatedDomainNames','pendingDomainNames');
        foreach ($flds as $f) {
            if (isset($this->$f) and isset($obj->$f)) {
                Yii::log('this-> ' . print_r($this->$f, true));
                $this->$f = $obj->$f;
            }
        }
        if (isset($obj->domain) and $obj->domain != null) {
            $d = $obj->domain;
            addToObjectAsProperties($this, 'domain', $d);
            if (isset($d->adminContacts) && is_object($d->adminContacts)) {
                $this->domain_adminContacts_0_companyName = $d->adminContacts->companyName;
                $this->domain_adminContacts_0_countryName = $d->adminContacts->countryName;
                $this->domain_adminContacts_0_name = $d->adminContacts->name;
                $this->domain_adminContacts_0_email = $d->adminContacts->email;
                $this->domain_adminContacts_0_nicHandle = $d->adminContacts->nicHandle;
            } else if (isset($d->adminContacts) && is_array($d->adminContacts)) {
                $this->domain_adminContacts_0_companyName = $d->adminContacts[0]['companyName'];
                $this->domain_adminContacts_0_countryName = $d->adminContacts[0]['countryName'];
                $this->domain_adminContacts_0_name = $d->adminContacts[0]['name'];
                $this->domain_adminContacts_0_email = $d->adminContacts[0]['email'];
                $this->domain_adminContacts_0_nicHandle = $d->adminContacts[0]['nicHandle'];
                $this->domain_adminContacts_1_companyName = $d->adminContacts[1]['companyName'];
                $this->domain_adminContacts_1_countryName = $d->adminContacts[1]['countryName'];
                $this->domain_adminContacts_1_name = $d->adminContacts[1]['name'];
                $this->domain_adminContacts_1_email = $d->adminContacts[1]['email'];
                $this->domain_adminContacts_1_nicHandle = $d->adminContacts[1]['nicHandle'];
                $this->domain_adminContacts_1_nicHandle_orig = $d->adminContacts[1]['nicHandle'];
            }
            if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'shortNrpStatus')) {
                $this->domain_dsmState_nrpStatus = $d->dsmState->shortNrpStatus;
            }
            if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'nrpStatus')) {
                $this->fullNRPStatus = $d->dsmState->nrpStatus;
            }
            if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'removeFromVoluntaryNRPPossible')) {
                $this->domain_dsmState_removeFromVoluntaryNRPPossible = $d->dsmState->removeFromVoluntaryNRPPossible;
            }
            if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'enterVoluntaryNRPPossible')) {
                $this->domain_dsmState_enterVoluntaryNRPPossible = $d->dsmState->enterVoluntaryNRPPossible;
            }
            if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'secondaryMarketStatus')) {
                $this->domain_dsmState_secondaryMarketStatus = $d->dsmState->secondaryMarketStatus;
            }
            if (property_exists($d, 'changeDate')) {
                $this->domain_changeDate = parseXmlDate($d->changeDate);
            }
            if (property_exists($d, 'domainStatusDate')) {
                $this->domain_domainStatusDate = parseXmlDate($d->domainStatusDate);
            }
            if (property_exists($d, 'dsmState') && property_exists($d->dsmState, 'renewalMode')) {
                $this->domain_dsmState_renewalMode = $d->dsmState->renewalMode;
            }
            if (property_exists($d, 'registrationDate')) {
                $this->domain_registrationDate = parseXmlDate($d->registrationDate);
            }
            if (property_exists($d, 'renewalDate')) {
                $this->domain_renewalDate = parseXmlDate($d->renewalDate);
            }
            if (property_exists($d, 'suspensionDate')) {
                $this->domain_suspensionDate = parseXmlDate($d->suspensionDate);
            }
            if (property_exists($d, 'deletionDate')) {
                $this->domain_deletionDate = parseXmlDate($d->deletionDate);
            }
            if (property_exists($d, 'lockingDate')) {
                $this->domain_lockingDate = parseXmlDate($d->lockingDate);
            }
            if (property_exists($d, 'lockingRenewalDate')) {
                $this->domain_lockingRenewalDate = parseXmlDate($d->lockingRenewalDate);
            }
            if (property_exists($d, 'authCodeExpirationDate')) {
                $this->domain_authCodeExpirationDate = parseXmlDate($d->authCodeExpirationDate);
            }
            if (isset($d->resellerAccount)) {
                $r = $d->resellerAccount;
                if (isset($r->changeDate))
                    $this->domain_resellerAccount_changeDate = parseXmlDate($r->changeDate);
                if (isset($r->creationDate))
                    $this->domain_resellerAccount_creationDate = parseXmlDate($r->creationDate);
                if (isset($r->statusChangeDate))
                    $this->domain_resellerAccount_statusChangeDate = parseXmlDate($r->statusChangeDate);
            }
            if (isset($d->nameservers)) {
                $this->domain_nameserver_count = count($d->nameservers);
                foreach ($d->nameservers as $i => $value) {
                    $this->domain_nameservers[$i] = $value->name;
                    $this->domain_ipv4Addresses[$i] = property_exists($value, 'ipv4Address') ? $value->ipv4Address : "";
                    $this->domain_ipv6Addresses[$i] = property_exists($value, 'ipv6Address') ? $value->ipv6Address : "";
                }
            }
            if (isset($d->adminContacts)) {
                if (is_object($d->adminContacts)) {
                    $this->domain_adminContacts = 1;
                } else if (is_array($d->adminContacts)) {
                    $this->domain_adminContacts = count($d->adminContacts);
                }
            }
            $this->domain_remark = ''; // no remark in view
        }
        $this->errors = $errs;
        Yii::log($this->domain_name . ' -> ' . $this->errors, 'debug', 'ViewDomainModel::fillFromObject()');
        Yii::log($this->domain_adminContacts_0_nicHandle_orig . ' -> ' . $this->errors, 'debug', 'ViewDomainModel::fillFromObject()');
        Yii::log($this->domain_dsmState_nrpStatus . ' -> ' . $this->errors, 'debug', 'ViewDomainModel::fillFromObject()');
    }

    /**
     * Yii Form validation Rules
     *
     * @return array validation rules
     * @access public
     */
    public function rules() {
        $this->rules = array(
            array(
                'domain_name, domain_holder, domain_adminContacts_0_nicHandle, domain_techContacts_nicHandle',
                'required'),
            array(
                'domain_name, domain_holder, domain_dsmState_renewalMode, domain_adminContacts_0_nicHandle, domain_adminContacts_1_nicHandle, domain_techContacts_nicHandle, domain_remark',
                'Utf8Validator'),
            array('domain_name','IEDomainValidator','skipOnError' => true),
            array(
                'domain_adminContacts_0_nicHandle, domain_adminContacts_1_nicHandle, domain_billingContacts_nicHandle, domain_creator_nicHandle, domain_techContacts_nicHandle',
                'NicHandleValidator'),
            array(
                'domain_adminContacts_0_name, domain_adminContacts_0_email, domain_adminContacts_0_companyName, domain_adminContacts_0_countryName,'
                . 'domain_adminContacts_1_name, domain_adminContacts_1_email, domain_adminContacts_1_companyName, domain_adminContacts_1_countryName,'
                . 'domain_techContacts_name, domain_techContacts_email, domain_techContacts_companyName, domain_techContacts_countryName',
                'length','max' => 255),
            array('domain_holder','length','max' => 255),
            array('domain_remark','length','max' => 500),
            array('domain_nameservers','NameserverListComplexValidator','dname' => 'domain_name',
                'ipv4Name' => 'domain_ipv4Addresses','ipv6Name' => 'domain_ipv6Addresses',
                'nameserversCount' => 'domain_nameserver_count'),
            array('domain_adminContacts_0_nicHandle, domain_adminContacts_1_nicHandle','UniquenessValidator',
                'fields' => array('domain_adminContacts_0_nicHandle','domain_adminContacts_1_nicHandle'),
                'label' => 'admin contact'));
        $this->val_rules = array_merge($this->rules, $this->val_rules);
        return $this->rules;
    }

    public function setFromArray($array) {
        if (array_key_exists('domain_name', $array) && isset($array['domain_name'])) {
            $this->domain_name = $array['domain_name'];
        }
        if (array_key_exists('domain_adminContacts_0_nicHandle', $array) && isset($array['domain_adminContacts_0_nicHandle'])) {
            $this->domain_adminContacts_0_nicHandle = $array['domain_adminContacts_0_nicHandle'];
        }
        if (array_key_exists('domain_adminContacts_1_nicHandle', $array) && isset($array['domain_adminContacts_1_nicHandle']))
            $this->domain_adminContacts_1_nicHandle = $array['domain_adminContacts_1_nicHandle'];
        if (array_key_exists('domain_techContacts_nicHandle', $array) && isset($array['domain_techContacts_nicHandle'])) {
            $this->domain_techContacts_nicHandle = $array['domain_techContacts_nicHandle'];
        }
        if (array_key_exists('domain_holder', $array) && isset($array['domain_holder'])) {
            $this->domain_holder = $array['domain_holder'];
        }
        if (array_key_exists('domain_nameserver_count', $array) && isset($array['domain_nameserver_count']))
            $this->domain_nameserver_count = $array['domain_nameserver_count'];
        if (array_key_exists('domain_nameservers', $array) && isset($array['domain_nameservers']) && is_array($array['domain_nameservers'])) {
            foreach ($array['domain_nameservers'] as $i => $value) {
                $this->domain_nameservers[$i] = $array['domain_nameservers'][$i];
            }
        }
        if (array_key_exists('domain_ipv4Addresses', $array) && isset($array['domain_ipv4Addresses']) && is_array($array['domain_ipv4Addresses'])) {
            foreach ($array['domain_ipv4Addresses'] as $i => $value) {
                $this->domain_ipv4Addresses[$i] = $array['domain_ipv4Addresses'][$i];
            }
        }
        if (array_key_exists('domain_ipv6Addresses', $array) && isset($array['domain_ipv6Addresses']) && is_array($array['domain_ipv6Addresses'])) {
            foreach ($array['domain_ipv6Addresses'] as $i => $value) {
                $this->domain_ipv6Addresses[$i] = $array['domain_ipv6Addresses'][$i];
            }
        }
        if (array_key_exists('domain_dsmState_renewalMode', $array) && isset($array['domain_dsmState_renewalMode'])) {
            $this->domain_dsmState_renewalMode = $array['domain_dsmState_renewalMode'];
        }
        if (array_key_exists('domain_remark', $array) && isset($array['domain_remark'])) {
            $this->domain_remark = $array['domain_remark'];
        }
    }

    public function attributeLabels() {
        $this->labels = array(
            'tickets' => 'Has Tickets',
            'documents' => 'Has Documents',
            'relatedDomainNames' => 'Related Domains',
            'pendingDomainNames' => 'Pending Domains',
            'domain_adminContacts_0_companyName' => 'Admin Contact companyName',
            'domain_adminContacts_0_country' => 'Admin Contact country',
            'domain_adminContacts_0_email' => 'Admin Contact email',
            'domain_adminContacts_0_name' => 'Admin Contact name',
            'domain_adminContacts_0_nicHandle' => 'Admin Contact 1',
            'domain_adminContacts_1_companyName' => 'Admin Contact companyName',
            'domain_adminContacts_1_country' => 'Admin Contact country',
            'domain_adminContacts_1_email' => 'Admin Contact email',
            'domain_adminContacts_1_name' => 'Admin Contact name',
            'domain_adminContacts_1_nicHandle' => 'Admin Contact 2',
            'domain_billingContacts_companyName' => 'Billing Contact companyName',
            'domain_billingContacts_country' => 'Billing Contact country',
            'domain_billingContacts_email' => 'Billing Contact email',
            'domain_billingContacts_name' => 'Billing Contact name',
            'domain_billingContacts_nicHandle' => 'Billing Contact nicHandle',
            'domain_changeDate' => 'Change Date',
            'domain_clikPaid' => 'ClikPaid',
            'domain_creator_companyName' => 'Creator companyName',
            'domain_creator_country' => 'Creator country',
            'domain_creator_email' => 'Creator email',
            'domain_creator_name' => 'Creator name',
            'domain_creator_nicHandle' => 'Creator nicHandle',
            'domain_dateRoll' => 'Renewal Rollover',
            'domain_deletionDate' => 'Deletion Date',
            'domain_domainStatusDate' => 'Domain Status Date',
            'domain_holder' => 'Holder',
            'domain_lockedStatus' => 'Locked Status',
            'domain_lockingDate' => 'Locking Date',
            'domain_lockingRenewalDate' => 'Locking Renewal Date',
            'domain_name' => 'Domain Name',
            'domain_registrationDate' => 'Registration Date',
            'domain_remark' => 'Remark',
            'domain_renewalDate' => 'Renewal Date',
            'domain_resellerAccount_agreementSigned' => 'Reseller Account agreement Signed',
            'domain_resellerAccount_billingContact' => 'Reseller Account Billing Contact',
            'domain_resellerAccount_changeDate' => 'Reseller Account Change Date',
            'domain_resellerAccount_creationDate' => 'Reseller Account Creation Date',
            'domain_resellerAccount_id' => 'Reseller Account ID',
            'domain_resellerAccount_name' => 'Reseller Account Name',
            'domain_resellerAccount_remark' => 'Reseller Account Remark',
            'domain_resellerAccount_status' => 'Reseller Account Status',
            'domain_resellerAccount_statusChangeDate' => 'Reseller Account Status Change Date',
            'domain_resellerAccount_ticketEdit' => 'Reseller Account Ticket Edit',
            'domain_resellerAccount_webAddress' => 'Reseller Account Web Address',
            'domain_suspensionDate' => 'Suspension Date',
            'domain_techContacts_companyName' => 'Tech Contact companyName',
            'domain_techContacts_country' => 'Tech Contact country',
            'domain_techContacts_email' => 'Tech Contact email',
            'domain_techContacts_name' => 'Tech Contact name',
            'domain_techContacts_nicHandle' => 'Technical Contact',
            'domain_dsmState_customerType' => 'Customer Type',
            'domain_dsmState_nrpStatus' => 'NRP Status',
            'domain_dsmState_renewalMode' => 'Renewal Mode',
            'domain_dsmState_holderType' => 'Holder Type',
            'domain_dsmState_locked' => 'Locked',
            'domain_dsmState_secondaryMarketStatus' => 'Secondary Market Status',
        );
        return $this->labels;
    }

    public function setOriginalContacts($tech, $admin0, $admin1) {
        $this->domain_techContacts_nicHandle_orig = $tech;
        $this->domain_adminContacts_0_nicHandle_orig = $admin0;
        $this->domain_adminContacts_1_nicHandle_orig = $admin1;
    }

    public function canBeModified() {
        return !$this->inPostRegAudit() && !$this->isLocked() && !$this->isTransferPending()
            && !$this->sellRequestExists();
    }

    public function isLocked() {
        return isset($this->domain_dsmState_locked) && $this->domain_dsmState_locked;
    }

    public function inPostRegAudit() {
        return $this->fullNRPStatus == 'PostTransactionAudit';
    }

    public function isTransferPending() {
        return $this->fullNRPStatus == 'TransferPendingActive'; // only active ?
    }

    public function sellRequestExists() {
        return $this->domain_dsmState_secondaryMarketStatus ==
            CRSDomainAppService_secondaryMarketStatus::_SellRequestRegistered;
    }

    public function inNrpProcess() {
        return $this->domain_dsmState_nrp;
    }

    public function inVnrpProcess() {
        return $this->domain_dsmState_voluntaryNRP;
    }

    public function overrideModifiedFields($dd_model, $isDirect, $nicrole) {
        if (isset($dd_model)) {
            $rw_fields = array('domain_holder', 'domain_nameserver_count', 'domain_nameservers', 'domain_ipv4Addresses',
                'domain_ipv6Addresses', 'domain_remark');
            if (!$isDirect) {
                array_push($rw_fields, 'domain_dsmState_renewalMode');
            }
            foreach (array('domain_adminContacts_0', 'domain_adminContacts_1', 'domain_techContacts') as $contact) {
                $nicHandleField = $contact . '_nicHandle';
                if ($nicrole != $nicHandleField || !isset($dd_model->$nicHandleField)) {
                    array_push($rw_fields, $contact . '_nicHandle', $contact . '_name', $contact . '_email',
                        $contact . '_companyName', $contact . '_countryName');
                }
            }
            foreach ($rw_fields as $f) {
                $this->$f = $dd_model->$f;
            }
        }
    }

}
?>
