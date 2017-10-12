<!-- START protected/views/nichandles/viewnichandle.php -->
<?php
/**
 * View page for Nic-Handle View
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see Nichandle_Details, NichandlesController::actionViewnichandle(), ReturningController::outputReturningFormHtml()
 */
$pg = 'View Account';
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
    $url_edit_nic = $this->createUrl('nichandles/editnichandle', array('id' => $model->nicHandleId));
    $url_change_password_nic = $this->createUrl('site/changePassword');
    $url_two_factor_auth = $this->createUrl('/site/twoFactorAuth');
    $url_edit_registrar_default_values = $this->createUrl('registrar_details/viewregistrar');
    $this->outputReturningFormHtml($model, 'NicSearchModel_returningData_nic', 'Return to Form');
    $form = $this->beginWidget('CActiveForm', array('id' => 'Nichandle_Details','enableAjaxValidation' => false));
    ?>
    <p class="note">
        Please fill in the form below.<font color="red"><br>Note:</font>
        Fields marked with an asterisk (*) must be completed.
    </p>
    <a href="<?php echo $url_edit_nic; ?>" />Edit Account</a><br>
    </a>
    <?php
    if ($model->nicHandleId == Yii::app()->user->name) {
        ?>
        <a href="<?php echo $url_change_password_nic; ?>" />Change
    Password</a><br>
        <?php
        if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
            ?>
            <a href="<?php echo $url_edit_registrar_default_values; ?>" />Edit
    Registrar Default Values</a><br>
            <?php
        }
        ?>
        <a href="<?php echo $url_two_factor_auth; ?>" />Two Factor
    Authentication</a><br>
        <?php
    }
    ?>
    <br>
    <table class="formfields">
        <tr>
            <td><?php echo $form->labelEx($model, 'nicHandleId'); echo $form->error($model, 'nicHandleId'); ?></td>
            <td>
                <div class="row"><?php echo $form->textField($model, 'nicHandleId', array('disabled' => 'disabled', 'size' => '31')); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'name'); echo $form->error($model, 'name'); ?></td>
            <td>
                <div class="row"><?php echo $form->textField($model, 'name', array('disabled' => 'disabled', 'size' => '31')); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'email'); echo $form->error($model, 'email'); ?></td>
            <td>
                <div class="row idn-tooltip-container"><?php echo $form->textField($model, 'email', array('disabled' => 'disabled', 'size' => '31'));?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'phones'); echo $form->error($model, 'phones'); ?></td>
            <td>
                <div class="row"><?php echo $form->textArea($model, 'phones', array('disabled' => 'disabled', 'rows' => 4, 'cols' => 45)); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'faxes'); echo $form->error($model, 'faxes'); ?></td>
            <td>
                <div class="row"><?php echo $form->textArea($model, 'faxes', array('disabled' => 'disabled', 'rows' => 4, 'cols' => 45)); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'companyName'); echo $form->error($model, 'companyName'); ?></td>
            <td>
                <div class="row"><?php echo $form->textField($model, 'companyName', array('disabled' => 'disabled', 'size' => '31')); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'address'); echo $form->error($model, 'address'); ?></td>
            <td>
                <div class="row"><?php echo $form->textArea($model, 'address', array('disabled' => 'disabled', 'rows' => 4, 'cols' => 45)); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'countryId'); echo $form->error($model, 'countryId'); ?></td>
            <td>
                <div class="row"><?php echo $form->dropDownList($model, 'countryId', getCountryOptions(),
                    array('disabled' => 'disabled', 'rows' => 4, 'cols' => 45, 'class' => 'expand')); ?></div>
            </td>
        </tr>
        <tr>
            <td><?php echo $form->labelEx($model, 'countyId'); echo $form->error($model, 'countyId'); ?></td>
            <td>
                <div class="row"><?php echo $form->dropDownList($model, 'countyId', getCountyOptions($model->countryId),
                    array('disabled' => 'disabled', 'rows' => 4, 'cols' => 45, 'class' => 'expand')); ?></div>
            </td>
        </tr>
        <?php if ($model->canBeABillingContact) { ?>
            <tr>
            <td><?php echo $form->labelEx($model, 'vatNo'); echo $form->error($model, 'vatNo'); ?></td>
            <td>
                <div class="row"><?php echo $form->textField($model, 'vatNo', array('disabled' => 'disabled', 'size' => '31')); ?></div>
            </td>
        </tr>
        <?php } ?>
        </table>
    <?php
    $this->endWidget();
    ?>
    </tbody>
    </table>
</div>
<!-- END protected/views/nichandles/viewnichandle.php -->
