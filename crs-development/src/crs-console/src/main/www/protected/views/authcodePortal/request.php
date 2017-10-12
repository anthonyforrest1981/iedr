<script type="text/javascript" src="js/tooltip.js"></script>
<script>
$(document).ready(function() {
    $("#AuthcodePortalModel_email").tooltip({
        content: 'This is the email address on our records for the admin contact for the domain. '
            + 'Having difficulty? <a href="https://www.iedr.ie/contact-us/" target="_blank">Contact us</a>.'
    });
});

</script>
<?php if ($model->message) { ?>
<script type="text/javascript">
    $(document).ready(function() {
        alert("<?php echo $model->message ?>");
    });
</script>
<?php } ?>
<div class="form">
<?php
$form = $this->beginWidget('CActiveForm',
    array('id' => 'authcodePortal',
        'enableAjaxValidation' => true,
        'clientOptions' => array(
            'validateOnSubmit' => true,
            'validateOnChange' => true
    )));
?>

    <center>
        <h2>Authcode Portal</h2>
    </center>
    <p>This transfer authcode is used to move the management of your domain to a new Registrar.</p>
    <p>To issue the authcode by email, please fill in the details below:</p>
    <p class="note">
        Fields with <span class="required">*</span> are required.
    </p>
    <table>
        <tr>
            <td style="width: 30%">
                <?php echo $form->labelEx($model, 'domain_name'); ?>
                <?php echo $form->error($model, 'domain_name'); ?>
            </td>
            <td>
                <?php echo $form->textField($model, 'domain_name', array('size'=>30, 'maxlength'=>60)); ?>
            </td>
        </tr>
        <tr>
            <td>
                <?php echo $form->labelEx($model, 'email'); ?>
                <?php echo $form->error($model, 'email'); ?>
            </td>
            <td>
                <?php echo $form->textField($model, 'email', array('size'=>30, 'maxlength'=>60)); ?>
            </td>
        </tr>
    </table>
    <?php
        $this->widget('application.widgets.recaptcha.RecaptchaWidget', array('form' => $form,
            'model' => $model, 'recaptchaResponseField' => 'recaptchaResponse'));
    ?>
    <br/>
    <div class="row buttons">
      <?php echo CHtml::submitButton('Submit'); ?>
    </div>

<?php $this->endWidget(); ?>
</div>
