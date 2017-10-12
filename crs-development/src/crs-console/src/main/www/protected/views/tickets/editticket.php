<!-- START protected/views/tickets/editticket.php -->
<?php
/**
 * View page for Ticket Edit
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see EditTicketModel, TicketsController::actionEditticket()
 */
$pg = 'Edit Ticket';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
$textfield_width = 60;
$textarea_height = 3;
?>
<form method="post" id="hid">
    <input type="hidden" id="tok" name="YII_CSRF_TOKEN">
    <input type="hidden" id="data" name="formdata">
    <input type="hidden" id="mode" name="mode">
    <input type="hidden" id="nictype" name="nictype">
    <input type="hidden" id="nic" name="nic">
    <input type="hidden" id="returnurl" name="returnurl" value="index.php?r=tickets/editticket">
</form>
<?php

function nicHandleWidget($controller, $nic_type, $inpIdNic2edit = 'LeftBlank', $showFull) {
    $urlDDS = $controller->createUrl('tickets/editticketserialized');
    $form = 'EditTicketModel';
    $it = Yii::app()->request->csrfToken;
    $params = "'$nic_type','$form','$urlDDS','$inpIdNic2edit','$it' ";
    echo '<a href="#" id="nicWidgetNew_' . $nic_type . '" onclick="getDataAndPostForm(\'new\',' . $params . ');">New</a> ';
    if ($showFull) {
        echo ' / <a href="#" id="nicWidgetFnd_' . $nic_type . '" onclick="return getDataAndPostForm(\'find\',' . $params . ');">Find</a> / ';
        echo '<a href="#" id="nicWidgetEdt_' . $nic_type . '" onclick="return getDataAndPostForm(\'edit\',' . $params . ');">Edit</a>';
    }
}

function regularTextField($form, $model, $name, $width, $editable, $idn, $htmlOptions = array()) {
    echo '<tr><td>' . $form->label($model, $name);
    if ($editable) {
        echo $form->error($model, $name);
        $fieldOptions = array('size' => $width);
    } else {
        $fieldOptions = array('size' => $width,'disabled' => 'disabled');
    }
    $fieldOptions += $htmlOptions;
    $cssClass = $idn ? 'row idn-tooltip-container' : 'row';
    echo '</td><td><div class="' . $cssClass . '">' . $form->textField($model, $name, $fieldOptions) . '</div></td></tr>';
}
?>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script type="text/javascript">

    var uploaderDocumentsPresentWarning = null;
    $(document).bind("uploaderFileCountChange", function(event, documentCount) {
        if (documentCount >0) {
            var docMsg = "";
            if (documentCount > 1) {
                docMsg = "are " + documentCount + " documents";
            } else {
                docMsg = "is " + documentCount + " document";
            }
            uploaderDocumentsPresentWarning = "There " + docMsg + " selected for upload";
        } else {
            uploaderDocumentsPresentWarning = null;
        }
    });

    function getDataAndPostForm(mode, nictype, srcf, serializeUrl, editNIC, it) {
        // this fn called on click (link or button) does:
        // (1) send form to server, gets serialized model back
        // (2) sets up hidden form in this page
        // (3) submits the form, navigating away to the next page
        if (uploaderDocumentsPresentWarning !== null && !confirm("Leaving this page will cause resetting some values:\n" + uploaderDocumentsPresentWarning)) {
            return false;
        }

        var dispatchUrl;
        switch (mode) {
            case "new":
                dispatchUrl = 'index.php?r=nichandles/createnichandle&from=1';
                break;
            case "find":
                dispatchUrl = 'index.php?r=nicsearch/namesearch&from=1';
                break;
            case "edit":

                var theNic = document.getElementById('EditTicketModel_' + nictype).value;
                dispatchUrl = 'index.php?r=nichandles/editnichandle&id=' + theNic + '&from=1';
                $("#hid #nic").val(theNic);
                break;
        }
        // (1) post form ('srcf') to server, retrieve serialized model via ajax
        //serializeUrl = 'tickets/main';
        $.post(serializeUrl, $("#" + srcf).serialize(), function (ajaxdata) {
            $("#hid").attr("action", dispatchUrl);
            $("#hid #data").val(ajaxdata); // the serialised form data
            $("#hid #mode").val(mode); // new/find/edit
            $("#hid #nictype").val(nictype); // which nic role for the mode (for updating the correct field, on return to this page)

            // Yii doesn't set an ID for the token hidden-input, so find by name
            var url = document.getElementById('returnurl').value;
            var ticket = document.getElementById('EditTicketModel_id').value;
            document.getElementById('returnurl').value = (url + '&id=' + ticket);
            // set hidform token value
            var hitok = $("#hid #tok")[0];
            $(hitok).val(it);
            // (3) post form with serialized model
            $("#hid").submit();
        });
    }

    $(document).ready(function() {
        $("#EditTicketModel_domainName").idnTooltip({
            type: "Domain"
        });
    });
</script>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
    <?php
    if ($model->errors != null)
        echo '<span class="error">' . print_r($model->errors, true) . '</span>';
    else {
        $formEditable = empty($model->checkedOutTo);
        $holderEditable = $formEditable && ($model->type != "Transfer");
        $canUploadDocuments = isset($uploaderModel) && Utility::isLoggedInAs($model->billingContact)
                && ($model->type == 'Registration' || $model->type == 'Modification');
        if (!$formEditable) {
            $message = 'Ticket cannot be edited right now as it\'s being processed by IEDR.';
            if ($canUploadDocuments) {
                $message .= '<br/>You can still upload new documents regarding this ticket';
            }
            Utility::printFlashNotice($message);
        }
        $form = $this->beginWidget('CActiveForm', array('id' => 'EditTicketModel','method' => 'post',
            'enableAjaxValidation' => true,
            'clientOptions' => array('validateOnSubmit' => $formEditable),
            'htmlOptions' => array('enctype' => 'multipart/form-data')));
        echo '<table border="1" class="formfields"><tbody>';
        echo $form->hiddenField($model, 'id');
        echo $form->hiddenField($model, 'editable');
        regularTextField($form, $model, 'domainName', $textfield_width, false, true);
        echo '<tr><td>' . $form->label($model, 'hostmastersRemark') . '</td><td>' . $form->textArea($model, 'hostmastersRemark', array(
            'cols' => $textfield_width,'rows' => $textarea_height,'disabled' => 'disabled')) . '</td></tr>';
        $ro_flds = array('type','expiryDate','adminStatus','techStatus','financialStatus','billingContact','period',
            'paymentType','charityCode');
        foreach ($ro_flds as $ro_fld_model_name) {
            regularTextField($form, $model, $ro_fld_model_name, $textfield_width, false, false);
        }
        $remarkOptions = array('cols' => $textfield_width,'rows' => $textarea_height);
        if (!$formEditable) {
            $remarkOptions['disabled'] = 'disabled';
        }
        $contactOptions = $formEditable ? array() : array('disabled' => 'disabled');
        $fullNicHandleWidget = Utility::isDirect() || Utility::isRegistrar() || Utility::isSuperRegistrar();
        regularTextField($form, $model, 'domainHolder', $textfield_width, $holderEditable, false, array('maxlength' => ''));
        echo '<tr><td>' . $form->label($model, 'requestersRemark') . $form->error($model, 'requestersRemark')
            . '</td><td><div class="row">' . $form->textArea($model, 'requestersRemark', $remarkOptions) . '</div></td></tr>';
        echo '<tr><td colspan="2">';
        $this->widget('application.widgets.nameservers.nameserversWidget',
            array('form' => $form, 'model' => $model,'domainId' => 'EditTicketModel_domainName','disabled' => !$formEditable));
        echo '</td></tr>';
        foreach (array('adminContact_0','adminContact_1','techContact') as $rw_fld_model_name) {
            echo '<tr><td>';
            echo $form->label($model, $rw_fld_model_name);
            echo $form->error($model, $rw_fld_model_name) . '</td><td><div class="row">';
            echo $form->textField($model, $rw_fld_model_name, $contactOptions) . ' ';
            if ($formEditable) {
                nicHandleWidget($this, $rw_fld_model_name, $rw_fld_model_name, $fullNicHandleWidget);
            }
            echo '</div></td></tr>';
        }
        // Transfer is string defined in DomainOperationType java enum, passed as string to php
        if ($canUploadDocuments) {
            if ($uploaderModel->hasErrors()) {
                echo "<tr><td colspan=\"2\">";
                echo $form->errorSummary($uploaderModel);
                echo "</td></tr>";
            }
            echo "<tr><td colspan=\"2\">";
            $this->widget('application.widgets.documentUploader.DocumentUploaderWidget', array(
                'uploaderModel' => $uploaderModel,'domains' => array($model->domainName)));
            echo "</td></tr>";
        }
        if ($formEditable || $canUploadDocuments) {
            echo '<tr><td colspan="2">' . CHtml::submitButton('Update Ticket') . '</td></tr>';
        }
        echo '</tbody></table>';
        $this->endWidget();
    }
    ?>
</div>
<!-- END protected/views/tickets/editticket.php -->
