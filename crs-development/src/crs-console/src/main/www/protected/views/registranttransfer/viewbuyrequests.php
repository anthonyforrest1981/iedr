<?php
$pg = 'View Your Buy Requests';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
if (Yii::app()->user->hasFlash('success')) {
    Utility::printFlashSuccess(Yii::app()->user->getFlash('success'));
}
if (Yii::app()->user->hasFlash('error')) {
    Utility::printFlashError(Yii::app()->user->getFlash('error'));
}
echo '<div class="form">';
$form = $this->beginWidget('CActiveForm', array('id' => 'ViewBuyRequests', 'enableAjaxValidation' => true));
echo '<div class="row"><h2>';
echo $form->error($model, 'days');
echo 'Buy requests created in the last';
echo $form->textField($model, 'days', array('size' => 3));
echo ' days ';
echo CHtml::submitButton('Update');
echo '</h2></div>';
$this->endWidget();
echo '</div>';
$serializedModel = AuthOnlyBaseController::safeSerializeObj($model);
$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
    'gridId' => 'buyRequests',
    'caption' => 'Requests for \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('registranttransferbuyrequests/view', array('model' => $serializedModel)),
    'datasource' => $this->createUrl('registranttransferbuyrequests/getgriddata', array('model' => $serializedModel)),
    'model' => $model,
    'searching' => true,
    'sorting' => true,
    'allowAll' => true));
?>
