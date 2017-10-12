<?php
$pg = 'View Buy Request';
if ($editing) {
    $pg = 'Edit Buy Request';
}
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#RegistrantTransferBuyRequestDetailsModel textarea").each(function(i, element) {
            $(element).css("height", "0px");
            $(element).css("height", element.scrollHeight + 4 + "px");
        });
    });
    $(document).ready(function() {
        $("#RegistrantTransferBuyRequestDetailsModel_domainName").idnTooltip({
            type: "Domain"
        });
        $("#Nichandle_Details_email").idnTooltip({
            type: "Email"
        });
    });
</script>
<?php

$countryAjaxOpts = array(
    'ajax' => array('type' => 'POST', 'url' => CController::createUrl('site/dynamiccounties'),
        'success' => 'js:function(html) {$("#Nichandle_Details_countyId").html(html).selectric("refresh");}'));

function displayFieldCell($model, $form, $attrName, $fieldType, $idn, $fieldReadonly) {
    $cssClass = $idn ? 'row idn-tooltip-container' : 'row';
    $options = array('class' => 'long-field');
    if ($fieldReadonly) {
        $options['readonly'] = 'readonly';
    }
    echo '<td>';
    if ($fieldReadonly) {
        echo $form->label($model, $attrName);
    } else {
        echo $form->labelEx($model, $attrName);
    }
    echo $form->error($model, $attrName);
    echo '</td>';
    echo '<td><div class="' . $cssClass . '">' .
        $form->$fieldType($model, $attrName, $options) . '</div></td>';
}

function displayActionLinks($controller, $model, $editing) {
    $editRequestUrl = $controller->createUrl('registranttransfer/editbuyrequest',
        array('id' => $model->id));
    $cancelRequestUrl = $controller->createUrl('registranttransfer/cancelbuyrequest',
        array('id' => $model->id));
    $shouldDisplayLinks = !$editing && $model->uploadEnabled;
    if ($shouldDisplayLinks) {
        echo '<tr><td colspan="2">';
        echo '<a href="' . $editRequestUrl . '"/>Edit Request</a><br/>';
        if ($model->requestEditable) {
            $cancelConfirmation = 'Are you sure you want to cancel the request?';
            echo '<a href="' . $cancelRequestUrl . '" onclick="return confirm(\'' .
                $cancelConfirmation . '\');">Cancel Request</a><br/>';
        }
        echo '</td></tr>';
    }
}

?>
<div class="form">
    <?php
    $viewRequestsListUrl = $this->createUrl('registranttransferbuyrequests/view');
    $viewRequestUrl = $this->createUrl('registranttransfer/viewbuyrequest',
        array('id' => $model->id));
    $formReadonly = !($model->requestEditable && $editing);

    if ($editing) {
        echo CHtml::link('Back to view', $viewRequestUrl, array('class' => 'button'));
    } else {
        echo CHtml::link('Back to results', $viewRequestsListUrl, array('class' => 'button'));
    }
    echo '<br/><br/>';
    if (Yii::app()->user->hasFlash('success')) {
        Utility::printFlashSuccess(Yii::app()->user->getFlash('success'));
    }
    if (Yii::app()->user->hasFlash('error')) {
        Utility::printFlashError(Yii::app()->user->getFlash('error'));
    }
    if (Yii::app()->user->hasFlash('notice')) {
        Utility::printFlashNotice(Yii::app()->user->getFlash('notice'));
    }

    $form = $this->beginWidget('CActiveForm',
        array('id' => 'RegistrantTransferBuyRequestDetailsModel',
            'method' => 'post',
            'enableAjaxValidation' => true,
            'clientOptions' => array('validateOnSubmit' => true),
            'htmlOptions' => array('enctype' => 'multipart/form-data')));

    echo $form->hiddenField($model, 'requestEditable');

    ?>
    <table>
        <tbody>
            <?php displayActionLinks($this, $model, $editing) ?>
            <tr>
                <?php displayFieldCell($model, $form, 'domainName', 'textField', true, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'id', 'textField', false, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'creationDate', 'textField', false, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'domainHolder', 'textField', false, $formReadonly); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'remarks', 'textArea', false, $formReadonly); ?>
            </tr>
            <tr>
                <td><label>Admin Contact Info</label></td>
                <td></td>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model->nicHandleDetails, $form, 'name', 'textField', false, $formReadonly); ?>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model->nicHandleDetails, $form, 'email', 'textField', true, $formReadonly); ?>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model->nicHandleDetails, $form, 'phones', 'textArea', false, $formReadonly); ?>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model->nicHandleDetails, $form, 'faxes', 'textArea', false, $formReadonly); ?>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model->nicHandleDetails, $form, 'companyName', 'textField', false, $formReadonly); ?>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model->nicHandleDetails, $form, 'address', 'textArea', false, $formReadonly); ?>
            </tr>
            <?php if ($formReadonly) { ?>
            <tr class="admin-info">
                <?php displayFieldCell($model, $form, 'adminCountryName', 'textField', false, $formReadonly); ?>
            </tr>
            <tr class="admin-info">
                <?php displayFieldCell($model, $form, 'adminCountyName', 'textField', false, $formReadonly); ?>
            </tr>
            <?php } else { ?>
            <tr class="admin-info">
                <td><?php echo $form->labelEx($model->nicHandleDetails, 'countryId'); echo $form->error($model->nicHandleDetails, 'countryId'); ?></td>
                <td><div class="row"><?php echo $form->dropDownList($model->nicHandleDetails, 'countryId', getCountryOptions(), $countryAjaxOpts); ?></div></td>
            </tr>
            <tr class="admin-info">
                <td><?php echo $form->labelEx($model->nicHandleDetails, 'countyId'); echo $form->error($model->nicHandleDetails, 'countyId'); ?></td>
                <td><div class="row"><?php echo $form->dropDownList($model->nicHandleDetails, 'countyId', getCountyOptions($model->nicHandleDetails->countryId)); ?></div></td>
            </tr>
            <?php } ?>
            <tr>
                <?php displayFieldCell($model, $form, 'hostmasterRemarks', 'textArea', false, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'creator', 'textField', false, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'status', 'textField', false, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'changeDate', 'textField', false, true); ?>
            </tr>
            <tr>
                <?php displayFieldCell($model, $form, 'expiryDate', 'textField', false, true); ?>
            </tr>
            <?php if ($editing && $model->uploadEnabled) { ?>
            <?php if ($uploaderModel->hasErrors()) { ?>
            <tr>
                <td colspan="2">
                <?php echo $form->errorSummary($uploaderModel); ?>
                </td>
            </tr>
            <?php } ?>
            <tr>
                <td colspan="2">
                <?php
                $this->widget('application.widgets.documentUploader.DocumentUploaderWidget',
                    array('uploaderModel' => $uploaderModel));
                ?>
                </td>
            </tr>
            <?php } ?>
            <?php if ($editing) { ?>
            <tr>
                <td colspan="2"><?php echo CHtml::submitButton('Update Request'); ?></td>
            </tr>
            <?php } ?>
        </tbody>
    </table>
    <?php $this->endWidget(); ?>
</div>
