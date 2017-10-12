<?php
$this->pageTitle = Yii::app()->name . ' - Request Password Change';
if (Yii::app()->user->hasFlash('error')) {
    Utility::printFlashError(Yii::app()->user->getFlash('error'));
}
?>
<div class="form">
    <?php
    $form = $this->beginWidget('CActiveForm', array('id' => 'PasswordResetRequest', 'enableAjaxValidation' => false));
    $form->focus = array($model, 'username');
    ?>

    <center>
        <h2>Request Password Change</h2>
    </center>
    <div class="row">
        <?php echo $form->labelEx($model, 'username'); ?>
        <?php echo $form->textField($model, 'username', array('class' => 'showupprcase', 'autocomplete' => 'off')); ?>
        <?php echo $form->error($model, 'username'); ?>
    </div>
    <div class="row buttons">
       <?php echo CHtml::submitButton('Reset Password'); ?>
    </div>
    <?php $this->endWidget(); ?>
</div>