<?php
$pg = 'Single Payment Search';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$this->widget('application.widgets.jqGrid.jqGridWidget', array('gridId' => 'singlePayment',
    'caption' => $pg . ' for \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('account_single_payment_search/menu', array('model' => $model_serialised)),
    'datasource' => $this->createUrl('account_single_payment_search/getgriddata', array('model' => $model_serialised)),
    'model' => $model,'searching' => true,'sorting' => true));
?>
