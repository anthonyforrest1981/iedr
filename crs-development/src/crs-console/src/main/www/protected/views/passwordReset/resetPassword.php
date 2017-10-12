<?php
$this->pageTitle = Yii::app()->name . ' - Reset Password';
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

<div class="form">
    <?php
    $form = $this->beginWidget('CActiveForm',
        array(
            'id' => 'ResetPassword',
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
                    var googlePinId = "' . CHtml::activeId($model, 'tfaPin') . '";
                    if (googlePinId in data && Object.keys(data).length === 1) {
                        var pinFormContainer = $("#pin-form-container");
                        if ($.CRS.tfaValidation.isDialogOpen()) {
                            pinFormContainer.find(".errorMessage").css("visibility", "visible");
                        } else {
                            pinFormContainer.find(".errorMessage").css("visibility", "hidden");
                        }
                        $.CRS.tfaValidation.clonePinToDialog(pinFormContainer, form, "Proceed", function(dialog) {
                            dialog.find("#" + googlePinId).select();
                        }, function() {
                            $("#" + googlePinId).val("");
                        });
                        return false;
                    } else {
                        $.CRS.tfaValidation.cleanDialog();
                        return !hasError;
                    }
                }'
        )));
    if (Yii::app()->user->hasFlash('error')) {
        Utility::printFlashError(Yii::app()->user->getFlash('error'));
    }
    $form->focus = array($model,'new_password');
    ?>

    <center>
        <h2>Reset Password</h2>
    </center>
    <p><strong>To finish resetting your password, please enter your new password below, and ensure it meets our
        guidelines:</strong></p>
    <p>Your new password must include at least one:</p>
    <ul>
        <li>uppercase letter</li>
        <li>lowercase letter</li>
        <li>digit</li>
        <li>character from the set <font color="#009880">:_-.#@|!$%&*+/</font></li>
    </ul>
    <div class="row">
        <?php echo $form->hiddenField($model,'username'); ?>
        <?php echo $form->hiddenField($model,'token'); ?>
    </div>
    <div class="row">
      <?php echo $form->labelEx($model,'new_password'); ?>
      <?php echo $form->passwordField($model,'new_password', array('maxlength'=>'', 'autocomplete'=>'off')); ?>
      <?php echo $form->error($model,'new_password'); ?>
    </div>
    <div class="row">
      <?php echo $form->labelEx($model,'repeat_new_password'); ?>
      <?php echo $form->passwordField($model,'repeat_new_password', array('maxlength'=>'', 'autocomplete'=>'off')); ?>
      <?php echo $form->error($model,'repeat_new_password'); ?>
    </div>
    <div id="pin-form-container" style="display: none">
        <div class="form" style="width: 600px; padding: 10px">
            <p>As your account has been setup with extra security features, please use the G Auth program to get your pin.</p>
            <p>Further information on how to get this application and your pin can be found <a
                href="https://www.iedr.ie/wp-content/uploads/2014/04/Console-Login-Instructions.pdf"
                target="_blank">here</a>.</p>
            <p>If you are having difficulties with resetting your password, please contact us on 01-2365400.</p>
            <?php echo $form->labelEx($model, 'tfaPin'); ?>
            <?php echo $form->textField($model, 'tfaPin', array('class' => 'showupprcase', 'autocomplete' => 'off')); ?>
            <?php echo $form->error($model, 'tfaPin'); ?>
            <p class="last-paragraph">Server time is: <span class="server-time"></span></p>
        </div>
    </div>
    <div class="row buttons">
    <?php
    echo CHtml::submitButton('Change Password');
    ?>
    </div>

    <?php $this->endWidget(); ?>
</div>