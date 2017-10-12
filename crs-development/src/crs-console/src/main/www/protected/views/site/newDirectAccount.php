<?php
if ($confirmation) {
    $pg = 'Confirm Your Account Details';
} else {
    $pg = 'Create New Account';
}
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#Nichandle_Details_email[type='text']").idnTooltip({
            type: "Email address"
        });
        $("#tfa-hint").tooltip({
            content: 'For more information about two factor authentication, please click '
                + '<a href="https://www.iedr.ie" target="_blank">here</a>.'
        });
        $("#Direct_Account_Details textarea").each(function(i, element) {
            $(element).css("height", "0px");
            $(element).css("height", element.scrollHeight + 16 + "px");
        });
    });
</script>
<h2><?php echo $pg; ?></h2>
<div class="form">
    <?php
    $countryAjaxOpts = array(
        'ajax' => array(
            'type' => 'POST',
            'url' => CController::createUrl('site/dynamiccounties'),
            'success' => 'js:function(html) {
                $("#Nichandle_Details_countyId").html(html).selectric("refresh");
            }'));
    if ($model->error != null) {
        echo WSAPIError::getErrorsNotEmpty($model->error);
    } else {
        $currentStep = $confirmation ? 1 : 0;
        $this->widget('application.widgets.progressBar.ProgressBarWidget',
            array('steps' => array('Enter Details', 'Confirm'), 'currentStep' => $currentStep,
                'htmlOptions' => array('style' => 'margin-bottom: 20px')));

        $form = $this->beginWidget('CActiveForm', array(
            'id' => 'Direct_Account_Details',
            'enableAjaxValidation' => true,
            'clientOptions' => array(
                'validateOnSubmit' => true)));
        $form->focus = array($model,'name');

        function echoLabel($confirmation, $form, $model, $name, $labelParams) {
            if ($confirmation) {
                echo $form->label($model, $name, $labelParams); echo $form->error($model, $name);
            } else {
                echo $form->labelEx($model, $name, $labelParams); echo $form->error($model, $name);
            }
        }

        function echoField($confirmation, $form, $model, $name, $fieldType, $fieldParams) {
            if ($confirmation) {
                echo $form->hiddenField($model, $name);
                echo encode($model->$name);
            } else {
                echo $form->$fieldType($model, $name, $fieldParams);
            }
        }

        function echoTextField($confirmation, $form, $model, $name, $fieldParams) {
            echoField($confirmation, $form, $model, $name, 'textField', $fieldParams);
        }

        function echoTextArea($confirmation, $form, $model, $name, $fieldParams) {
            echoField($confirmation, $form, $model, $name, 'textArea', $fieldParams);
        }

        function echoDropDownList($confirmation, $form, $model, $name, $options, $fieldParams = null) {
            if ($confirmation) {
                echo $form->hiddenField($model, $name);
                echo encode($options[$model->$name]);
            } else {
                echo $form->dropDownList($model, $name, $options, $fieldParams);
            }
        }
    ?>

    <?php if (!$confirmation) { ?>
    <p class="note">
        Please fill in the form below.<br>
        <font color="red">Note:</font> Fields marked with an asterisk (*) must be completed.
    </p>
    <?php } ?>
    <table class="formfields">
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'name'); ?></td>
            <td>
                <div class="row">
                    <?php echoTextField($confirmation, $form, $model, 'name',
                        array('maxlength'=>'', 'autocomplete'=>'off')); ?>
                </div>
            </td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'email'); ?></td>
            <td>
                <div class="row idn-tooltip-container">
                    <?php echoTextField($confirmation, $form, $model, 'email', array('autocomplete'=>'off'));?>
                </div>
            </td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'phones'); ?></td>
            <td><div class="row"><?php echoTextArea($confirmation, $form, $model,'phones', array()); ?></div></td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'companyName'); ?></td>
            <td>
                <div class="row">
                    <?php echoTextField($confirmation, $form, $model,'companyName',
                        array('maxlength'=>'', 'autocomplete'=>'off')); ?>
                </div>
            </td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'address'); ?></td>
            <td><div class="row"><?php echoTextArea($confirmation, $form, $model,'address', array()); ?></div></td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'countyId'); ?></td>
            <td><div class="row"><?php echoDropDownList($confirmation, $form, $model, 'countyId',
                    getCountyOptions($model->countryId), array('class' => 'expand'));?></div></td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $model, 'countryId'); ?></td>
            <td><div class="row"><?php echoDropDownList($confirmation, $form, $model, 'countryId',
                    getCountryOptions(), array_merge($countryAjaxOpts, array('class' => 'expand'))); ?></div></td>
        </tr>
    <?php if (!$confirmation) {?>
        <tr>
            <td><?php echoLabel($confirmation, $form, $passwordModel, 'new_password'); ?></td>
            <td>
                <div class="row">
                    <?php echo $form->passwordField($passwordModel, 'new_password',
                        array('maxlength'=>'', 'autocomplete'=>'off')); ?>
                </div>
            </td>
        </tr>
        <tr>
            <td><?php echoLabel($confirmation, $form, $passwordModel, 'repeat_new_password'); ?></td>
            <td>
                <div class="row">
                    <?php echo $form->passwordField($passwordModel, 'repeat_new_password',
                        array('maxlength'=>'', 'autocomplete'=>'off')); ?>
                </div>
            </td>
        </tr>
    <?php } ?>
        <tr>
            <td>
                <?php echoLabel($confirmation, $form, $passwordModel, 'useTwoFactorAuthentication',
                    array('class' => 'inline')); ?>
                <?php if (!$confirmation) { ?>
                    <img id="tfa-hint" style="vertical-align: middle" src="images/tooltipicon.png"/>
                <?php } ?>
                <?php echo $form->error($passwordModel, 'useTwoFactorAuthentication'); ?>
            </td>
            <td><div class="row">
                <?php if ($confirmation) {
                    echo ($passwordModel->useTwoFactorAuthentication ? 'Yes' : 'No');
                    echo $form->hiddenField($passwordModel, 'new_password');
                    echo $form->hiddenField($passwordModel, 'repeat_new_password');
                    echo $form->hiddenField($passwordModel, 'useTwoFactorAuthentication');
                } else {
                    echo $form->checkBox($passwordModel, 'useTwoFactorAuthentication');
                } ?>
            </div></td>
        </tr>
        <tr>
            <td></td>
            <td><div class="row buttons">
                <?php
                if ($confirmation) {
                    echo CHtml::submitButton('Confirm', array('name' => 'action'));
                    echo CHtml::submitButton('Back', array('name' => 'action'));
                } else {
                    echo CHtml::submitButton('Create', array('name' => 'action'));
                }
                ?>
            </div></td>
        </tr>
    </table>
    <?php
        $this->endWidget();
    }
    ?>
</div>
