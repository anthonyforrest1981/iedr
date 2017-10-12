<!-- START protected/views/domainreports/charity.php -->
<?php
/**
 * View page for Domain Reports - Charity Domains
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see DomainreportsController::actionCharity(), CharityDomainModel, jqGridWidget
 */
$pg = 'View Non-Billable Domains';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array('gridId' => 'nonBillableDomains',
    'caption' => 'All Charity Domains registered with \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('account_view_charity_domains/menu', array('model' => $model_serialised)),
    'datasource' => $this->createUrl('account_view_charity_domains/getgriddata', array('model' => $model_serialised)),
    'model' => $model));
?>
<!-- END protected/views/domainreports/charity.php -->
