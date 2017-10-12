<?php
$pg = 'Apply for a Domain';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
    <?php

    $form = $this->beginWidget('CActiveForm', array('id' => 'BuyRequestForm', 'enableAjaxValidation' => true));
    $form->focus = array($model, 'domainName');
    ?>
    <p>Please enter the domain name in the box below.</p>

    <p>Further information on the Registrant Transfer process and the terms and conditions which apply can be found <a target="_blank" href="https://www.iedr.ie/secondary-market/">here</a>.</p>
    <p class="strong"><font color="red">**Warning: Proceed only if you have contacted the owner of this domain.
    Any payments made on the subsequent screens are strictly non-refundable.**</font></p>
    <div class="row">
        <?php echo $form->error($model, 'domainName'); ?>
        <?php echo $form->labelEx($model, 'domainName'); ?>
        <?php echo $form->textField($model, 'domainName'); ?>
    </div>
    <div class="row buttons"><?php echo CHtml::submitButton('Apply'); ?></div>
    <?php $this->endWidget(); ?>
</div>
