<!-- START protected/views/domainreports/alldomains.php -->
<?php
/**
 * View page for Domain Reports - All Domains
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see AllDomainsModel, DomainreportsController::actionAlldomains(), jqGridWidget
 */
$pg = 'All Domains';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
if (Utility::isAdminOrTech()) {
    $roles = array('ADMIN' => "Admin Contact",'TECH' => "Tech Contact");
} else {
    $roles = array('BILLING' => "Billing Contact",'ADMIN' => "Admin Contact",'TECH' => "Tech Contact");
}
echo "<table class='gridtable'>
        <tr>
            <td class='gridtablecell'><strong>Contact Type</strong></td>
            <td class='gridtablecell'><strong>Total Domains</strong></td>
        </tr>";
foreach ($roles as $key => $name) {
    echo "<tr>
            <td class='gridtablecell'>" . $name . "</td>
            <td class='gridtablecell'>" . $model->domainCountForContact[$key] . "</td>
        </tr>";
}
echo "</table>";
$form = $this->beginWidget('CActiveForm', array('id' => 'AllDomains'));
echo '<div style="line-height: 1.8em"> Current contact type: ' . $roles[$model->contact_type] . '<br>';
echo 'Switch to: ';
echo $form->dropDownList($model, 'contact_type', $roles) . ' ';
echo CHtml::submitButton('Update') . '</div><br>';
$this->endWidget();
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$level = $_SESSION['lastLevel'];
Yii::log('USER LEVEL: ' . print_r($level, true));
if ($level == UserPermissionLevel::REGISTRAR || $level == UserPermissionLevel::SUPER_REGISTRAR) {
    $this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array('pk_column_index' => 0,
        'actionurl' => $this->createUrl('domainreports/confirm'),
        'returnurl' => $this->createUrl('domainreports/alldomains'),'formid' => 'gridaction',
        'commandlist' => array('autorenew' => 'Set %n Domains Autorenew',
            'authcodedownload' => 'Download Authcodes for %n Domains')));
    echo '<br/>';
}
$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array('gridId' => 'allDomains',
    'caption' => 'All Domains registered with \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('domainreports/alldomains', array('model' => $model_serialised)),
    'datasource' => $this->createUrl('domainreports/getgriddata', array('model' => $model_serialised)),'model' => $model,
    'selections' => ($level == UserPermissionLevel::REGISTRAR || $level == UserPermissionLevel::SUPER_REGISTRAR) ? true : false,
    'searching' => true,'sorting' => true));
?>
<!-- END protected/views/domainreports/alldomains.php -->
