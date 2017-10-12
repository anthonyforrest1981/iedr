<?php
$pg = 'Sell a Domain (Details)';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#RegistrantTransferSaleDetailsModel_domainName").idnTooltip({
            type: "Domain"
        });
    });
</script>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
<?php
$form = $this->beginWidget('CActiveForm', array('id' => 'RegistrantTransferSaleDetailsForm',
    'enableAjaxValidation' => true, 'clientOptions' => array('validateOnSubmit' => true)));
?>
    <p class="note">
        Please fill in the form below.<font color="red"><br>Note:</font>
        Fields marked with an asterisk (*) must be completed.
    </p>
    <table class="formfields">
        <tr>
            <td><?php echo $form->labelEx($model, 'domainName'); echo $form->error($model, 'domainName'); ?></td>
            <td>
                <div class="row idn-tooltip-container">
                    <?php echo $form->textField($model, 'domainName', array('disabled' => 'disabled'));
                        echo $form->hiddenField($model, 'domainName');
                    ?>
                </div>
            </td>
        </tr>
        <?php $this->widget('application.widgets.payment.PaymentWidget', array('form' => $form, 'model' => $model,
            'paymentTypes' => Utility::getRequestPaymentTypes())); ?>
        <tr>
            <td><div font-size="0.7em"><strong>Transaction Summary:</strong></div></td>
            <td>
                <table class="gridtable" style="display: hidden">
                    <tr>
                        <td class="gridtablecell">Fee:</td>
                        <td class="gridtablecell"><?php echo Utility::currencyAmount($model->payment->fee); ?></td>
                    </tr>
                    <tr>
                        <td class="gridtablecell">Vat:</td>
                        <td class="gridtablecell"><?php echo Utility::currencyAmount($model->payment->vat); ?></td>
                    </tr>
                    <tr>
                        <td class="gridtablecell">Total:</td>
                        <td class="gridtablecell"><?php echo Utility::currencyAmount($model->payment->total); ?></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'authcode'); echo $form->error($model, 'authcode'); ?></td>
            <td><div class="row"><?php echo $form->textField($model, 'authcode'); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'acceptTnc'); echo $form->error($model, 'acceptTnc'); ?></td>
            <td><div class="row"><?php echo $form->checkBox($model, 'acceptTnc'); ?></div></td>
        </tr>
    </table>
    <?php echo $form->hiddenField($model, 'submit'); ?>
    <div class="row buttons"><?php echo CHtml::submitButton('Submit'); ?></div>
<?php
$this->endWidget();
?>
</div>
<script type="text/javascript">

    function showMessage() {
        <?php if (!$model->authcodeCorrect) {?>
                alert("The authcode you entered does not match any buy request");
        <?php } else if ($model->submit) {?>
                var countdownPeriod = <?php echo Utility::getApplicationConfiguration()->secondaryMarketCountdownPeriod ?>;
                var periodWithSuffix = countdownPeriod + (countdownPeriod == 1 ? " day" : " days")
                if (confirm("***Please note that the domain will now be irreversibly locked for "
                        + periodWithSuffix + " if you proceed***.\n\n"
                        + "During this time, edits to the domain registration will not be possible. "
                        + "The new holder will be able to edit the domain at the 3pm activation time, "
                        + periodWithSuffix + " from now.\n\n"
                        + "Payment for the required fee will be taken once the domain enters the " + countdownPeriod
                        + "-day countdown.\n\n"
                        + "Are you sure you want to proceed?")) {
                    $('#RegistrantTransferSaleDetailsForm').submit();
                } else {
                    $('#RegistrantTransferSaleDetailsModel_submit').val("");
                }
        <?php }?>
    }

    $(document).ready(function() {
        showMessage();
    })
</script>
