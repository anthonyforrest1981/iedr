<?php
$pg = 'Locked Domains';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array('gridId' => 'lockedDomains',
    'caption' => 'Locked Domains registered with \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('lockeddomains/view', array('model' => $model_serialised)),
    'datasource' => $this->createUrl('lockeddomains/getgriddata', array('model' => $model_serialised)),'model' => $model,
    'searching' => true,'sorting' => true));
