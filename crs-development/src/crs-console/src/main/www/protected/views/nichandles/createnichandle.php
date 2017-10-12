<!-- START protected/views/nichandles/createnichandle.php -->
<?php
/**
 * View page for Nic-Handle creation
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see Nichandle_Details, NichandlesController::actionCreatenichandle(), NichandlesController::actionDynamiccounties(), ReturningController::outputReturningFormHtml()
 */
$pg = 'Create Account';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#Nichandle_Details_email").idnTooltip({
            type: "Email address"
        });
    });
</script>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
<?php

/**
 * creates html link to nic-handle view page with
 *
 * @param object $cntrlr
 *            Controller instance
 * @param string $nic
 *            Nic-Handles to create view-link to
 * @return string html link to nic-handle view page
 */
function makeViewNicLink($cntrlr, $nic) {
    return '<a id="' . $nic . '" href="' . $cntrlr->createUrl('nichandles/viewnichandle', array('id' => $nic)) . '">' . $nic . '</a>';
}
$countryAjaxOpts = array(
    'ajax' => array('type' => 'POST', // request type
'url' => CController::createUrl('site/dynamiccounties'), // url to call.
        'success' => 'js:function(html) {$("#Nichandle_Details_countyId").html(html).selectric("refresh");}'));
if ($model->error != null) {
    echo '<div><span class="error">' . WSAPIError::getErrorsNotEmpty($model->error) . '</span></div><br>';
} else {
    $this->outputReturningFormHtml($model, 'NicSearchModel_returningData_nic', 'Return to Form');
}
if ($model->message != null) {
    if ($model->message == 'NIC_CREATED') {
        echo '<span class="error" id="nic_created_message">Account Created: ' . makeViewNicLink($this, $model->nicHandleId) . '</span>';
        if (isset($_GET['from'])) {
            ?>
               <script>
                 var returnToDetails = document.getElementsByName('yt0');
                 returnToDetails[0].click();
               </script>
             <?php
        }
    }
} else {
    $form = $this->beginWidget('CActiveForm', array('id' => 'Nichandle_Details','enableAjaxValidation' => true));
    $form->focus = array($model,'name');
    foreach ($model->getReturningDataNames() as $v) {
        echo $form->hiddenField($model, 'returningData[' . $v . ']');
    }
    ?>
<p class="note">
        Please fill in the form below.<font color="red"><br>Note:</font>
        Fields marked with an asterisk (*) must be completed.
    </p>
    <table class="formfields">
        <tr>
            <td><?php echo $form->labelEx($model,'name'); echo $form->error($model,'name'); ?></td>
            <td><div class="row"><?php echo $form->textField($model,'name', array('size'=>'31', 'maxlength'=>'')); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model,'email'); echo $form->error($model,'email'); ?></td>
            <td><div class="row idn-tooltip-container"><?php echo $form->textField($model,'email', array('size'=>'31')); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model,'phones'); echo $form->error($model,'phones'); ?></td>
            <td><div class="row"><?php echo $form->textArea($model,'phones', array('rows'=>4, 'cols'=>45)); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model,'companyName'); echo $form->error($model,'companyName'); ?></td>
            <td><div class="row"><?php echo $form->textField($model,'companyName', array('size'=>'31', 'maxlength'=>'')); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model,'address'); echo $form->error($model,'address'); ?></td>
            <td><div class="row"><?php echo $form->textArea($model,'address', array('rows'=>4, 'cols'=>45)); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model,'countryId'); echo $form->error($model,'countryId'); ?></td>
            <td><div class="row"><?php echo $form->dropDownList($model,'countryId', getCountryOptions(),
                array_merge($countryAjaxOpts, array('class' => 'expand'))); ?></div></td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model,'countyId'); echo $form->error($model,'countyId'); ?></td>
            <td><div class="row"><?php echo $form->dropDownList($model,'countyId', getCountyOptions($model->countryId),
                array('class' => 'expand')); ?></div></td>
        </tr>
        <tr>
            <td></td>
            <td><div class="row buttons"><?php echo CHtml::submitButton('Create'); ?></div></td>
        </tr>
    </table>
<?php
   $this->endWidget();
}
?>
</tbody>
    </table>
</div>
<!-- END protected/views/nichandles/createnichandle.php -->
