<?php
$pg = 'Sell a Domain';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
    <?php

    $form = $this->beginWidget('CActiveForm', array('id' => 'SellRequestForm', 'enableAjaxValidation' => true));
    $form->focus = array($model, 'domainName');
    ?>
    <p>To complete the transfer of a .ie domain registration to a new holder, please enter the domain name below</p>

    <p>Please ensure you have your buyer’s authcode to hand.</p>

    <p class="strong"><font color="red">Warning:</font> Please make sure you include the correct authcode.</p>

    <p class="strong">Once the request has been submitted with any valid authcode, the domain will be irreversibly locked for
    <?php $countdownPeriod = Utility::getApplicationConfiguration()->secondaryMarketCountdownPeriod;
    echo $countdownPeriod . ($countdownPeriod == 1 ? ' day,' : ' days,'); ?>
    during which time no edits can be made.</p>
    <div class="row">
        <?php echo $form->error($model, 'domainName'); ?>
        <?php echo $form->labelEx($model, 'domainName'); ?>
        <?php echo $form->textField($model, 'domainName'); ?>
    </div>
    <div class="row buttons"><?php echo CHtml::submitButton('Sell'); ?></div>
    <?php $this->endWidget(); ?>
</div>