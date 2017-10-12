<?php
$pg = 'Apply for a Domain (Details)';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
$disclaimerUrl = "'" . Yii::app()->params['secondary_market_disclaimer_url'] . "'";
$disclaimerUrl = $disclaimerUrl === "''" ? "null" : $disclaimerUrl;
?>
<script type="text/javascript" src="js/dialog.js"></script>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        var disclaimerCheckbox = $("#RegistrantTransferPurchaseDetailsModel_acceptDisclaimer");
        if (disclaimerCheckbox.is(":checked")) {
            return;
        }
        $.CRS.dialog({
            url: <?php echo $disclaimerUrl ?>,
            acceptOptions: {
                text: "I agree",
                callback: function() {
                    disclaimerCheckbox.attr('checked', true);
                }
            },
            cancelOptions: {
                text: "I don't agree",
                callback: function() {
                    window.history.back();
                },
                closeOnClick: false
            },
            failureCallback: function(dialog) {
                alert("We experienced an error. Please try again. If the problem persists, contact us.");
                window.history.back();
            }
        });
    });
    $(document).ready(function() {
        $("#RegistrantTransferPurchaseDetailsModel_domainName").idnTooltip({
            type: "Domain"
        });
        $("#Nichandle_Details_email").idnTooltip({
            type: "Email"
        });
    });
</script>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
<?php
$countryAjaxOpts = array(
    'ajax' => array('type' => 'POST', 'url' => CController::createUrl('site/dynamiccounties'),
        'success' => 'js:function(html) {$("#Nichandle_Details_countyId").html(html).selectric("refresh");}'));
$form = $this->beginWidget('CActiveForm',
    array('id' => 'RegistrantTransferPurchaseDetailsForm',
    'method' => 'post',
    'enableAjaxValidation' => true,
    'clientOptions' => array('validateOnSubmit' => true),
    'htmlOptions' => array('enctype' => 'multipart/form-data')));
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
        <tr>
            <td><?php echo $form->labelEx($model, 'domainHolder'); echo $form->error($model, 'domainHolder'); ?></td>
            <td><div class="row"><?php echo $form->textField($model, 'domainHolder'); ?></div></td>
        </tr>
        <?php
        $this->widget('application.widgets.ownerType.OwnerTypeWidget',
            array('form' => $form, 'model' => $model, 'paymentForRegistration' => false));
        ?>
        <tr>
            <td><?php echo $form->labelEx($model, 'remarks'); echo $form->error($model, 'remarks'); ?>
                <br>eg. RBN/CRO No., CTM Number, links to supporting information etc
            </td>
            <td><div class="row"><?php echo $form->textArea($model, 'remarks', array('rows' => 3,'cols' => 50)); ?></div></td>
        </tr>
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
            <td><label>Admin Contact Info</label></td>
            <td></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'name'); echo $form->error($model->nicHandleDetails, 'name'); ?></td>
            <td><div class="row"><?php echo $form->textField($model->nicHandleDetails, 'name', array('size'=>'31', 'maxlength'=>'')); ?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'email'); echo $form->error($model->nicHandleDetails, 'email'); ?></td>
            <td><div class="row idn-tooltip-container"><?php echo $form->textField($model->nicHandleDetails, 'email', array('size'=>'31')); ?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'phones'); echo $form->error($model->nicHandleDetails, 'phones'); ?></td>
            <td><div class="row"><?php echo $form->textArea($model->nicHandleDetails, 'phones', array('rows'=>4, 'cols'=>45)); ?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'faxes'); echo $form->error($model->nicHandleDetails, 'faxes');?></td>
            <td><div class="row"><?php echo $form->textArea($model->nicHandleDetails, 'faxes', array('rows'=>4, 'cols'=>45));?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'companyName'); echo $form->error($model->nicHandleDetails, 'companyName'); ?></td>
            <td><div class="row"><?php echo $form->textField($model->nicHandleDetails, 'companyName', array('size'=>'31', 'maxlength'=>'')); ?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'address'); echo $form->error($model->nicHandleDetails, 'address'); ?></td>
            <td><div class="row"><?php echo $form->textArea($model->nicHandleDetails, 'address', array('rows'=>4, 'cols'=>45)); ?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'countryId'); echo $form->error($model->nicHandleDetails, 'countryId'); ?></td>
            <td><div class="row"><?php echo $form->dropDownList($model->nicHandleDetails, 'countryId',
                getCountryOptions(), array_merge($countryAjaxOpts, array('class' => 'expand'))); ?></div></td>
        </tr>
        <tr class="admin-info">
            <td><?php echo $form->labelEx($model->nicHandleDetails, 'countyId'); echo $form->error($model->nicHandleDetails, 'countyId'); ?></td>
            <td><div class="row"><?php echo $form->dropDownList($model->nicHandleDetails, 'countyId',
                getCountyOptions($model->nicHandleDetails->countryId), array('class' => 'expand')); ?></div></td>
        </tr>
        <?php $this->widget('application.widgets.payment.PaymentWidget', array('form' => $form, 'model' => $model, 'paymentTypes' => Utility::getRequestPaymentTypes())); ?>
        <?php if ($uploaderModel->hasErrors()): ?>
        <tr>
            <td colspan="2">
            <?php echo $form->errorSummary($uploaderModel); ?>
            </td>
        </tr>
        <?php endif; ?>
        <tr>
            <td colspan="2">
            <?php
            $this->widget('application.widgets.documentUploader.DocumentUploaderWidget',
                array('uploaderModel' => $uploaderModel));
            ?>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'acceptTnc'); echo $form->error($model, 'acceptTnc'); ?></td>
            <td><div class="row"><?php echo $form->checkBox($model, 'acceptTnc'); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'acceptDisclaimer'); echo $form->error($model, 'acceptDisclaimer'); ?></td>
            <td><div class="row"><?php echo $form->checkBox($model, 'acceptDisclaimer'); ?></div></td>
        </tr>
    </table>
    <div class="row buttons"><?php echo CHtml::submitButton('Apply'); ?></div>
<?php
$this->endWidget();
?>
</div>