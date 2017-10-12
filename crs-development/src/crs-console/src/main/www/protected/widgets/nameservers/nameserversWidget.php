<?php

class nameserversWidget extends CWidget {
    /**
     * Model instance containing nameserver data
     *
     * @var unknown
     * @access public
     */
    var $model = null;
    /**
     * Form instance that the widget is used in
     *
     * @var unknown
     * @access public
     */
    var $form = null;
    /**
     * Name of variable in the model that contains nameserver array
     *
     * @var string
     * @access public
     */
    var $nameservers = 'nameservers';
    /**
     * Name of variable in the model that contains IPv4 address array
     * (if empty IPv4 addresses will not be displayed)
     *
     * @var string
     * @access public
     */
    var $ipv4Addresses = 'ipv4Addresses';
    /**
     * Name of variable in the model that contains IPv6 address array
     * (if empty IPv6 addresses will not be displayed)
     *
     * @var string
     * @access public
     */
    var $ipv6Addresses = 'ipv6Addresses';
    /**
     * Name of variable in the model that contains number of nameservers
     *
     * @var string
     * @access public
     */
    var $nameserversCount = 'nameserversCount';
    /**
     * Id of the field in the form in which the domain names are kept
     *
     * @var string
     * @access public
     */
    var $domainId = 'domainname';
    /**
     * If true - domain names are kept in a listbox and may be selected partially
     * If false - there is a single domain name or they are kept as a string separated with commas
     *
     * @var boolean
     * @access public
     */
    var $domainsAsArray = false;
    /**
     * If true - item labels are displayed
     *
     * @var boolean
     * @access public
     */
    var $labels = true;
    /**
     * If true - items are preceded by a piece of blank space on the left
     *
     * @var boolean
     * @access public
     */
    var $indent = false;
    /**
     * If true - fields are disabled and the buttons are hidden
     *
     * @var boolean
     * @access public
     */
    var $disabled = false;
    /**
     * If true - fields are readonly and the buttons are hidden
     *
     * @var boolean
     * @access public
     */
    var $readonly = false;
    /**
     * If true - DNS Verification button is displayed
     *
     * @var boolean
     * @access public
     */
    var $dnsCheck = true;

    public function run() {
        $this->render('application.widgets.nameservers.view', array('model' => $this->model,'form' => $this->form,
            'nameservers' => $this->nameservers,'ipv4Addresses' => $this->ipv4Addresses,
            'ipv6Addresses' => $this->ipv6Addresses,'nameserversCount' => $this->nameserversCount,
            'domainId' => $this->domainId,'domainsAsArray' => $this->domainsAsArray,'indent' => $this->indent,
            'labels' => $this->labels,'disabled' => $this->disabled,'readonly' => $this->readonly,
            'dnsCheck' => $this->dnsCheck));
    }

}
