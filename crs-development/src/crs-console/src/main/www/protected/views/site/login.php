<?php
$this->pageTitle = Yii::app()->name . ' - Login';
?>
<script type="text/javascript" src="js/dialog.js"></script>
<script type="text/javascript" src="js/server-time.js"></script>
<script type="text/javascript" src="js/tfa-validation.js"></script>
<script type="text/javascript">
$.CRS.serverTime.initClock(<?php echo $model->serverTime->hours . ', ' . $model->serverTime->minutes .
    ', ' . $model->serverTime->seconds; ?>);
$(document).ready(function() {
    $(".server-time").html($.CRS.serverTime.getTime());
    $.CRS.serverTime.onChange(function(formattedTime) {
        $(".server-time").html(formattedTime);
    });
});
</script>
<?php
if (Yii::app()->user->hasFlash('success')) {
    Utility::printFlashSuccess(Yii::app()->user->getFlash('success'));
}
?>
<h1>Login</h1>
<p>Please enter the required details below:</p>
<div class="form">
<?php

$form = $this->beginWidget('CActiveForm', array(
    'id' => 'login-form',
    'enableAjaxValidation' => true,
    'clientOptions' => array(
        'validateOnSubmit' => true,
        'validateOnChange' => false,
        'beforeValidate' => 'js:function(form) {
            var pinFormContainer = $("#pin-form-container");
            $.CRS.tfaValidation.clonePinFromDialog(pinFormContainer);
            return true;
        }',
        'afterValidate' => 'js:function(form, data, hasError) {
            var googlePinId = "' . CHtml::activeId($model, 'google_pin') . '";
            if (googlePinId in data && Object.keys(data).length === 1) {
                var pinFormContainer = $("#pin-form-container");
                if ($.CRS.tfaValidation.isDialogOpen()) {
                    pinFormContainer.find(".errorMessage").css("visibility", "visible");
                } else {
                    pinFormContainer.find(".errorMessage").css("visibility", "hidden");
                }
                $.CRS.tfaValidation.clonePinToDialog(pinFormContainer, form, "Login", function(dialog) {
                    dialog.find("#" + googlePinId).select();
                }, function() {
                    $("#" + googlePinId).val("");
                });
                return false;
            } else {
                $.CRS.tfaValidation.cleanDialog();
                $("#" + googlePinId).val("");
                return !hasError;
            }
        }'
    )));
$form->focus = array($model, 'username');
?>
    <p class="note">
        Fields with <span class="required">*</span> are required.
    </p>

<?php
if (Utility::isInternalNetwork()) {
    ?>
    <div class="row">
        <?php echo $form->labelEx($model, 'internalUser'); ?>
        <?php echo $form->dropdownList($model, 'internalUser', Utility::getIEDRUsers()); ?>
        <?php echo $form->error($model, 'internalUser'); ?>
    </div>
<?php
} else {
    $model->internalUser = NicHandleIdentity::NOT_INTERNAL;
    echo $form->hiddenField($model, 'internalUser');
}
?>
    <div class="row">
        <?php echo $form->labelEx($model, 'username'); ?>
        <?php echo $form->textField($model, 'username', array('class' => 'showupprcase', 'autocomplete' => 'off')); ?>
        <?php echo $form->error($model, 'username'); ?>
    </div>
    <div class="row">
        <?php echo $form->labelEx($model, 'password'); ?>
        <?php echo $form->passwordField($model, 'password', array('maxlength' => 16, 'autocomplete' => 'off')); ?>
        <?php echo $form->error($model, 'password'); ?>
    </div>
    <div id="pin-form-container" style="display: none">
        <div class="form" style="width: 600px; padding: 10px">
            <p>As your account has been setup with extra security features, please use the G Auth program to get your pin.</p>
            <p>Further information on how to get this application and your pin can be found <a href="https://www.iedr.ie/wp-content/uploads/2014/04/Console-Login-Instructions.pdf" target="_blank">here</a>.</p>
            <p>If you are having difficulties with logging in, please contact us on 01-2365400.</p>
            <?php echo $form->labelEx($model, 'google_pin'); ?>
            <?php echo $form->textField($model, 'google_pin', array('class' => 'showupprcase', 'autocomplete' => 'off')); ?>
            <?php echo $form->error($model, 'google_pin'); ?>
            <p class="last-paragraph">Server time is: <span class="server-time"></span></p>
        </div>
    </div>
    <div class="row buttons">
        <?php echo CHtml::submitButton('Login'); ?>
    </div>
<?php $this->endWidget(); ?>
</div>
