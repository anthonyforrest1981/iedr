<?php
$pg = 'View Your Sell Requests';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php $countdownPeriod = Utility::getApplicationConfiguration()->secondaryMarketCountdownPeriod; ?>
<p>Once a domain has entered the Registrant Transfer
<?php echo $countdownPeriod; ?>-day countdown period,
no modifications can be made to the domain registration. The domain can be modified at the 3pm activation time,
<?php echo $countdownPeriod . ($countdownPeriod == 1 ? " day " : " days ") ?>
after submission.<p>

<?php
echo '<div class="form">';
$serializedModel = AuthOnlyBaseController::safeSerializeObj($model);
$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
    'gridId' => 'sellRequests',
    'caption' => 'Requests for \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('registranttransfersellrequests/view', array('model' => $serializedModel)),
    'datasource' => $this->createUrl('registranttransfersellrequests/getgriddata', array('model' => $serializedModel)),
    'model' => $model,
    'searching' => true,
    'sorting' => true,
    'allowAll' => true));
?>
