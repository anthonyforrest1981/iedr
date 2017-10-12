<?php

/**
 * file which defines AccountsGridController class
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
 * Specialised GridController for handling grids with invoices and account-payment related actions
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 */
class AccountsGridController extends GridController {
    /**
     * filename to use with grid data export functions in current request
     *
     * @var string
     * @access protected
     */
    protected $exportFileName;

    /**
     * action which renders menu
     *
     * @return void
     * @access public
     */
    public function actionMenu() {
        $this->render('menu');
    }

    /**
     * get base path for for view to display ; views/accountsgrid/ contains shared view confirm_action.php and results.php
     *
     * @return string Return description (if any) ...
     * @access protected
     */
    protected function getViewBase() {
        return '/accountsgrid/';
    }

    /**
     * Override of GridController function called before rendering confirm page; sets model data according to request type
     *
     * @param object $gs_model
     *            model for current request
     * @return void
     * @access protected
     */
    protected function processGridActionCommand($model) {
        parent::processGridActionCommand($model);
        switch ($model->command) {
            case 'payonline':
                $model->needCreditCard = 1;
                break;
        }
    }

    /**
     * command execution after confirmation-accept, makes calls to back-end systems
     *
     * makes calls to back-end payment or msd functions for selected invoices or domains, depending on command chosen
     *
     * @param mixed $model
     *            model for current request
     * @return void
     * @access protected
     */
    protected function execCommand($model) {
        $model->commandresults = array();
        $domains = array();
        $this->backend_errors = array();
        $result = null;
        $user = Yii::app()->user->authenticatedUser;
        foreach ($model->domainListToArray() as $domain) {
            $domains[] = $domain;
        }
        Yii::log('ACCOUNTS GRID CONTROLLER execCommand ' . print_r($model->command, true));
        switch ($model->command) {
            case 'paydeposit':
                $domains = GridUtility::getDomainsWithPeriod($model);
                CRSPaymentAppService_service::payForDomainRenewal($result, $this->backend_errors, $user, $domains, 'ADP', null);
                if ($result != null) {
                    Yii::log('PAYDEPOSIT NOT NULL ' . print_r($result, true));
                    $model->commandresults['paydeposit'] = $result;
                } else {
                    Yii::log('PAYDEPOSIT IS NULL' . print_r($this->backend_errors, true));
                    $model->commandresults['ERROR'] = WSAPIError::getErrors($this->backend_errors);
                }
                break;
            case 'payonline':
                $creditCard = CreditCardFormSegmentModel::asWSAPICreditCardObject($model);
                $domains = GridUtility::getDomainsWithPeriod($model);
                CRSPaymentAppService_service::payForDomainRenewal($result, $this->backend_errors, $user, $domains, CRSPaymentAppService_paymentMethod::_CC, $creditCard);
                if ($result != null) {
                    Yii::log('PAYONLINE NOT NULL ' . print_r($result, true));
                    $model->commandresults['payonline'] = $result;
                } else {
                    Yii::log('PAYONLINE IS NULL ' . print_r($this->backend_errors, true));
                    $model->commandresults['ERROR'] = WSAPIError::getErrors($this->backend_errors);
                }
                break;
            case 'voluntary':
                foreach ($domains as $domain) {
                    CRSDomainAppService_service::enterVoluntaryNRP($this->backend_errors, $user, $domain);
                    if (count($this->backend_errors) == 0) {
                        Yii::log('ENTER VOLUNTARY NRP SUCCESSFUL');
                    } else {
                        Yii::log('ENTER VOLUNTARY NRP NOT SUCCESSFUL ' . print_r($this->backend_errors, true));
                    }
                    $this->setStatusDomain($domain, $model, 'voluntary', $this->backend_errors);
                    $this->backend_errors = '';
                }
                break;
            case 'removefromvoluntary':
                Yii::log('removefromvoluntary command ' . print_r($domains, true));
                foreach ($domains as $domainName) {
                    CRSDomainAppService_service::removeFromVoluntaryNRP($this->backend_errors, Yii::app()->user->authenticatedUser, $domainName);
                    if (count($this->backend_errors) == 0) {
                        Yii::log("REMOVEFROMVOLUNTARY SUCCESSFULL " . $domainName);
                    } else {
                        Yii::log("REMOVEFROMVOLUNTARY NOT SUCCESSFULL " . $domainName . " " . print_r($this->backend_errors, true));
                    }
                    $this->setStatusDomain($domainName, $model, 'removefromvoluntary', $this->backend_errors);
                    $this->backend_errors = '';
                }
                break;
            case 'autorenew':
                $domains = GridUtility::getDomainsWithRenewalMode($model);
                Yii::log('MODEL AUTO RENEW= ' . print_r($domains, true));
                foreach ($domains as $domain => $renewalMode) {
                    CRSDomainAppService_service::modifyRenewalMode($this->backend_errors, $user, $domain, $renewalMode);
                    if (count($this->backend_errors) == 0) {
                        Yii::log('AUTO RENEW SUCCESSFULL ' . $domain);
                    } else {
                        Yii::log('AUTO RENEW NOT SUCCESSFULL' . $domain . '  ' . print_r($this->backend_errors, true));
                    }
                    $this->setStatusDomain($domain, $model, 'autorenew', $this->backend_errors);
                    $this->backend_errors = '';
                }
                break;
            default:
                $model->addError('domainlist', "Not a valid operation.");
                Yii::log('DEFAULT OPTION!!!');
                break;
        }
    }

    /**
     * returns export filename prefix for grid data export
     *
     * @return string filename prefix
     * @access protected
     * @see actionGetgriddata
     */
    protected function getExportFilenameTag() {
        return $this->exportFileName;
    }

}
