<!-- START protected/views/domains/viewdomain.php -->
<?php
/**
 * View page for Domains - View Domain
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see ViewDomainModel, DomainsController::actionViewDomain()
 */
$pg = 'View Domain';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php printBreadcrumbTrail($pg); ?></h2>
<?php

function nicHandleWidget($controller, $nic_type, $contactType, $showFull) {
    $urlDDS = $controller->createUrl('domains/domaindetailsserialized');
    $form = 'ViewDomainForm';
    $params = "'$nic_type','$form','$urlDDS'";
    $contactType = "'$contactType'";
    echo '<div id=' . $contactType . ' style="display:none;">';
    echo '<a href="#" id="nicWidgetNew_' . $nic_type . '" onclick="getDataAndPostForm(\'new\',' . $params . ');">New</a>';
    if ($showFull) {
        echo ' / <a href="#" id="nicWidgetFnd_' . $nic_type . '" onclick="return getDataAndPostForm(\'find\',' . $params . ');">Find</a>';
        echo ' / <a href="#" id="nicWidgetEdt_' . $nic_type . '" onclick="return getDataAndPostForm(\'edit\',' . $params . ');">Edit</a>';
        echo ' / <a href="#" id="nicWidgetDel_' . $nic_type . '" onclick="removeNic(' . $contactType . ');">Delete</a>';
    }
    echo '</div>';
}

?>
<form method="post" id="hid">
    <input type="hidden" id="tok" name="YII_CSRF_TOKEN">
    <input type="hidden" id="data" name="formdata">
    <input type="hidden" id="mode" name="mode">
    <input type="hidden" id="nictype" name="nictype">
    <input type="hidden" id="nic" name="nic">
    <input type="hidden" id="returnurl" name="returnurl" value="index.php?r=domains/viewdomain">
    <input type="hidden" id="modify" value="0">
</form>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script>
    function removeNic(param) {
        if(confirm('Are you sure you want to remove this contact?')) {
            var fieldsToClear = ['nicHandle', 'name', 'email', 'companyName', 'countryName'];
            var fieldPrefix = null;
            switch(param) {
                case 'tech':
                    fieldPrefix = 'ViewDomainModel_domain_techContacts_';
                    break;
                case 'adm1':
                    fieldPrefix = 'ViewDomainModel_domain_adminContacts_0_';
                    break;
                case 'adm2':
                    fieldPrefix = 'ViewDomainModel_domain_adminContacts_1_';
                    break;
            }
            if (fieldPrefix !== null) {
                $.each(fieldsToClear, function(i, field) {
                    $('#' + fieldPrefix + field).updateValue('');
                });
            }
        }
    }

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

    function getDataAndPostForm(mode,nictype,srcf,serializeUrl) {
        // this fn called on click (link or button) does:
        // (1) send form to server, gets serialized model back
        // (2) sets up hidden form in this page
        // (2) submits the form, navigating away to the next page
        if (uploaderDocumentsPresentWarning !== null && !confirm("Leaving this page will cause resetting some values:\n" + uploaderDocumentsPresentWarning)) {
            return false;
        }

        var domain = document.getElementById('ViewDomainModel_domain_name').value;
        var u = document.getElementById('returnurl').value;
        var url = u + '&id=' + domain;
        document.getElementById('returnurl').value = url;

        var dispatchUrl;
        switch(mode) {
            case "new":
                dispatchUrl = 'index.php?r=nichandles/createnichandle&from=1';
                break;
            case "find":
                dispatchUrl = 'index.php?r=nicsearch/namesearch&from=1';
                break;
            case "edit":
                var theNic = $("#ViewDomainModel_" + nictype).val();
                dispatchUrl = 'index.php?r=nichandles/editnichandle&id=' + theNic + '&from=1';
                $("#hid #nic").val(theNic);
                break;
        }
        // (1) post form ('srcf') to server, retrieve serialized model via ajax
        $.post(serializeUrl, $("#"+srcf).serialize(), function(ajaxdata) {
            // (2) set up form
            $("#hid").attr("action",dispatchUrl);
            $("#hid #data").val(ajaxdata); // the serialized form data
            $("#hid #mode").val(mode); // new/find/edit
            $("#hid #nictype").val(nictype); // which nic role for the mode (for updating the correct field, on return to this page)

            // Yii doesn't set an ID for the token hidden-input, so find by name
            var itok = $('#'+srcf+' input[name="YII_CSRF_TOKEN"]')[0];
            // set hidform token value
            var hitok = $("#hid #tok")[0];
            $(hitok).val($(itok).val());
            // (2) post form with serialized model
            $("#hid").submit();
        });
        turnOffFormProtection();
    }

    function addNameserver(){
        var nameServers = document.getElementById('nameServersCount');
        if(nameServers != null) {
            var num = parseInt(nameServers.innerHTML) + 1;
            var object = document.getElementById('newnameservers' + num);
            if(object != null) {
                object.style.visibility="visible";
                nameServers.innerHTML = (num);
                document.getElementById('ViewDomainModel_nsc').value = num;
            }
        }
    }

    function groupReadOnly(is, disableRenewalMode) {
        var disabledFields = '#ViewDomainModel_domain_techContacts_nicHandle'
                + ',#ViewDomainModel_domain_adminContacts_0_nicHandle'
                + ',#ViewDomainModel_domain_adminContacts_1_nicHandle'
                + ',#ViewDomainModel_domain_holder';
        var displayedFields = '#ViewDomainModel_domain_remark'
                + ',#remark_label'
                + ',#tech'
                + ',#adm1'
                + ',#adm2'
                + ',#ns_add_buttton'
                + ',#ns_ver_buttton';
        var antiDisplayedFields = '#enter_to_nrp'
                + ',#remove_from_nrp';
        var selects = '#ViewDomainModel_domain_dsmState_renewalMode';

        var count = parseInt($('#ns_count').val());
        for(var i = 0; i < count; i++) {
            disabledFields += ',#ns_' + i + ',#ipv4_' + i + ',#ipv6_' + i;
            displayedFields += ',#ns_del_buttton_'+ i;
        }

        if (disableRenewalMode) {
            $('#ViewDomainModel_domain_dsmState_renewalMode').attr('disabled', 'disabled');
        } else {
            disabledFields += ',#ViewDomainModel_domain_dsmState_renewalMode';
        }

        if (is) {
            $(disabledFields).attr('disabled', 'disabled');
            $(displayedFields).hide();
            $(antiDisplayedFields).show();
            $('#modify').val('1');
        } else {
            $(disabledFields).removeAttr('disabled');
            $(displayedFields).show();
            $(antiDisplayedFields).hide();
            $('#modify').val('0');
        }

        $(selects).each(function() {
            if ($(this).data('selectric')) {
                $(this).selectric('refresh');
            }
        });
    }

    function beforeValidate(disableRenewalMode) {
        document.getElementById('ViewDomainModel_domain_remark').value = '';
        document.getElementById('con').style.display = 'none';
        document.getElementById('mod').style.display = 'block';
        $("#document_uploader").show();

        var o = document.getElementById('enter_to_nrp');
        if(o == null) {
            var oo = document.getElementById('remove_from_nrp');
            if (oo != null) {
                oo.style.display = 'none';
            }
        } else {
            o.style.display = 'none';
        }

        document.getElementById('modify').value = '1';
        groupReadOnly(false, disableRenewalMode);
        return true;
    }

    function setMessage(obj) {
        var index = obj.selectedIndex;
        if(index == 3) {
            alert('You can only change domains with state Autorenew and RenewOnce.');
            obj.selectedIndex = 0;
        }
    }

    $(document).ready(function() {
        $(window).keydown(function(event){
            if(event.keyCode == 13) {
                event.preventDefault();
                return false;
            }
        });
        $("#ViewDomainModel_domain_name").idnTooltip({
            type: "Domain"
        });
        var emailFields = "#ViewDomainModel_domain_adminContacts_0_email," +
            "#ViewDomainModel_domain_adminContacts_1_email," +
            "#ViewDomainModel_domain_techContacts_email";
        $(emailFields).idnTooltip({
            type: "Email address"
        });
    });


</script>
<div class="form">
<?php
if ($model->message) {
    $messages = implode("<br>", $model->message);
    Utility::printFlashNotice($messages);
    $model->message = "";
}
if (isset(Yii::app()->session['returnAction'])) {
    $form = $this->beginWidget('CActiveForm', array('action' => $this->createUrl(Yii::app()->session['returnAction'])));
    echo CHtml::submitButton('Back to results');
    $this->endWidget();
}
echo "<br>";
$form = $this->beginWidget('CActiveForm', array('id' => 'ViewDomainForm','enableAjaxValidation' => true,
    'htmlOptions' => array('enctype' => 'multipart/form-data')));
// readonly
$roOpts = array('readonly' => 'readonly');
$roOpts['size'] = 25;
$roEnabled = array('enabled' => 'enabled');
$roEnabled['size'] = 25;
$roOptsSmall = $roOpts;
$roOptsSmall['size'] = 12;
$roEnabledSmall = array('enabled' => 'enabled');
$roEnabledSmall['size'] = 12;

?>
   <table border="1" class="formfields">
        <tbody>
            <tr>
                <td><?php
echo $form->label($model, 'domain_name');
                echo $form->error($model, 'domain_name');
                ?></td>
                <td><div class="row idn-tooltip-container"><?php echo $form->textField($model, 'domain_name', $roOpts); ?></div></td>
                <td><?php
echo $form->label($model, 'domain_dsmState_nrpStatus');
                echo $form->error($model, 'domain_dsmState_nrpStatus');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_dsmState_nrpStatus', $roOpts); ?></div></td>
            </tr>
            <tr>
                <td><?php
echo $form->label($model, 'domain_registrationDate');
                echo $form->error($model, 'domain_registrationDate');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_registrationDate', $roOpts); ?></div></td>
                <td><?php
echo $form->label($model, 'domain_renewalDate');
                echo $form->error($model, 'domain_renewalDate');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_renewalDate', $roOpts); ?></div></td>
            </tr>
            <tr>
                <td><?php
echo $form->label($model, 'domain_suspensionDate');
                echo $form->error($model, 'domain_suspensionDate');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_suspensionDate', $roOpts); ?></div></td>
                <td><?php
echo $form->label($model, 'domain_deletionDate');
                echo $form->error($model, 'domain_deletionDate');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_deletionDate', $roOpts); ?></div></td>
            </tr>
            <tr>
                <td><?php
echo $form->label($model, 'domain_lockingDate');
                echo $form->error($model, 'domain_lockingDate');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_lockingDate', $roOpts); ?></div></td>
                <td><?php
echo $form->label($model, 'domain_lockingRenewalDate');
                echo $form->error($model, 'domain_lockingRenewalDate');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_lockingRenewalDate', $roOpts); ?></div></td>
            </tr>
            <tr>
                <td><?php
echo $form->label($model, 'domain_holder');
                echo $form->error($model, 'domain_holder');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_holder', $roEnabled + array('maxlength'=>'')); ?></div></td>
                <td><?php
echo $form->label($model, 'domain_dsmState_holderType');
                echo $form->error($model, 'domain_dsmState_holderType');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_dsmState_holderType', $roOpts); ?></div></td>
            </tr>
            <tr>
                <td><?php
echo $form->label($model, 'domain_dsmState_customerType');
                echo $form->error($model, 'domain_dsmState_customerType');
                ?></td>
                <td><div class="row"><?php echo $form->textField($model, 'domain_dsmState_customerType', $roOpts); ?></div></td>
                <td><?php
echo $form->label($model, 'domain_dsmState_renewalMode');
                echo $form->error($model, 'domain_dsmState_renewalMode');
                ?></td>
                <td><div class="row"><?php
                echo $form->dropDownList($model, 'domain_dsmState_renewalMode',
                    array('Autorenew' => 'Autorenew', 'RenewOnce' => 'RenewOnce','NoAutorenew' => 'NoAutorenew'),
                    array('onchange' => 'javascript:setMessage(this)', 'class' => 'expand'));
                ?></div></td>
            </tr>
            <tr>
                <td colspan="4">
                <?php
                $domainStateNRP = $model->domain_dsmState_nrpStatus;
                $isRemovableNRP = $model->domain_dsmState_removeFromVoluntaryNRPPossible;
                $isEnterableNRP = $model->domain_dsmState_enterVoluntaryNRPPossible;
                $this->widget('application.widgets.nameservers.nameserversWidget', array('form' => $form,'model' => $model,
                    'nameservers' => 'domain_nameservers','ipv4Addresses' => 'domain_ipv4Addresses',
                    'ipv6Addresses' => 'domain_ipv6Addresses','nameserversCount' => 'domain_nameserver_count',
                    'domainId' => 'ViewDomainModel_domain_name'));
                ?>
                </td>
            </tr>
            <tr>
            <?php
            $lockedStr = "";
            $model->domain_lockedStatus ? $lockedStr = "True" : $lockedStr = "False";
            $fullNicHandleWidget = Utility::isBillingContact($model) || Utility::isDirect() || Utility::isRegistrar() || Utility::isSuperRegistrar();
            $showNicHandleWidget = $fullNicHandleWidget || Utility::isAdminContact($model);
            ?>
                <td><div id='remark_label'><?php echo $form->label($model, 'domain_remark'); ?> <?php echo $form->error($model, 'domain_remark'); ?> </div>
                </td>
                <td colspan="3"><div class="row"> <?php echo $form->textArea($model,'domain_remark',array('size' => 80, 'cols' => 50, 'rows'=>4));?> </div>
                </td>
            </tr>
            <tr>
                <td colspan="4" bgcolor="#E4F5F0" />
            </tr>
            <tr>
                <td colspan="4">
                    <table>
                        <tr>
                            <td></td>
                            <td>Account Number</td>
                            <td>Name</td>
                            <td>Email</td>
                            <td>Company</td>
                            <td>Country</td>
                        </tr>
                        <tr>
                            <td><?php "<a href=index.php?r=nichandles/viewnichandle&id=" . $model->domain_adminContacts_0_nicHandle . ">Admin1:</a>"; ?>Admin1:</td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_0_nicHandle'); ?><div
                                    class="row"><?php
echo $form->textField($model, 'domain_adminContacts_0_nicHandle', $roEnabledSmall);
                                    if ($showNicHandleWidget)
                                        nicHandleWidget($this, 'domain_adminContacts_0_nicHandle', 'adm1', $fullNicHandleWidget);
                                    ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_0_name'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_adminContacts_0_name', $roOptsSmall); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_0_email'); ?><div
                                    class="row idn-tooltip-container"><?php echo $form->textField($model, 'domain_adminContacts_0_email', $roOpts); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_0_companyName'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_adminContacts_0_companyName', $roOptsSmall); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_0_countryName'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_adminContacts_0_countryName', $roOptsSmall); ?></div></td>
                        </tr>
                        <tr>
                            <td><?php "<a href=index.php?r=nichandles/viewnichandle&id=" . $model->domain_adminContacts_1_nicHandle . ">Admin2:</a>"; ?>Admin2:</td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_1_nicHandle'); ?><div
                                    class="row"><?php
echo $form->textField($model, 'domain_adminContacts_1_nicHandle', $roEnabledSmall);
                                    if ($showNicHandleWidget)
                                        nicHandleWidget($this, 'domain_adminContacts_1_nicHandle', 'adm2', $fullNicHandleWidget);
                                    ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_1_name'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_adminContacts_1_name', $roOptsSmall); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_1_email'); ?><div
                                    class="row idn-tooltip-container"><?php echo $form->textField($model, 'domain_adminContacts_1_email', $roOpts); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_1_companyName'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_adminContacts_1_companyName', $roOptsSmall); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_adminContacts_1_countryName'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_adminContacts_1_countryName', $roOptsSmall); ?></div></td>
                        </tr>
                        <tr>
                            <td><?php "<a href=index.php?r=nichandles/viewnichandle&id=" . $model->domain_techContacts_nicHandle . ">Tech:</a>"; ?>Tech:</td>
                            <td><?php echo $form->error($model, 'domain_techContacts_nicHandle'); ?><div
                                    class="row"><?php
echo $form->textField($model, 'domain_techContacts_nicHandle', $roEnabledSmall);
                                    if ($showNicHandleWidget)
                                        nicHandleWidget($this, 'domain_techContacts_nicHandle', 'tech', $fullNicHandleWidget);
                                    ?></div></td>
                            <td><?php echo $form->error($model, 'domain_techContacts_name'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_techContacts_name', $roOptsSmall); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_techContacts_email'); ?><div
                                    class="row idn-tooltip-container"><?php echo $form->textField($model, 'domain_techContacts_email', $roOpts); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_techContacts_companyName'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_techContacts_companyName', $roOptsSmall); ?></div></td>
                            <td><?php echo $form->error($model, 'domain_techContacts_countryName'); ?><div
                                    class="row"><?php echo $form->textField($model, 'domain_techContacts_countryName', $roOptsSmall); ?></div></td>
                        </tr>
                    </table>
                </td>
            <tr>
         <?php if (Utility::isBillingContact($model)): ?>
             <?php if ($uploaderModel->hasErrors()): ?>
            <tr>
                <td colspan="4">
                 <?php echo $form->errorSummary($uploaderModel); ?>
                 </td>
            </tr>
             <?php endif; ?>
             <tr id="document_uploader" style="display:<?php echo ($model->isActive) ? 'block;' : 'none;'; ?>;">
                <td colspan="4">
                 <?php
            $this->widget('application.widgets.documentUploader.DocumentUploaderWidget', array(
                'uploaderModel' => $uploaderModel,'domains' => array($model->domain_name)));
            ?>
                 </td>
            </tr>
         <?php endif; ?>
      </tbody>
    </table>

   <?php
$checkVolNRPUserPermissions = Utility::isBillingContact($model);
$checkModifyDomainUserPermissions = $checkVolNRPUserPermissions || Utility::isAdminContact($model);
$status = false;
if (($isRemovableNRP == '1' || $isRemovableNRP || $isRemovableNRP == true) && $checkVolNRPUserPermissions) {
    $status = false;
    echo CHtml::submitButton('Remove from Voluntary NRP', array('id' => 'remove_from_nrp',
        'onclick' => 'turnOffFormProtection()'));
} else if (($isEnterableNRP == '1' || $isEnterableNRP || $isEnterableNRP == true) && $checkVolNRPUserPermissions) {
    $status = true;
    echo CHtml::submitButton('Enter Domain to Voluntary NRP', array('id' => 'enter_to_nrp',
        'onclick' => 'turnOffFormProtection()'));
}
if (($model->canBeModified()) && $checkModifyDomainUserPermissions) {
    ?>
      <div class="row buttons" style="display:<?php echo ($model->isActive) ? 'block;' : 'none;'; ?>;" id="mod"><?php echo CHtml::submitButton('Submit changes',array('id' => 'submitButton', 'onclick' => 'turnOffFormProtection()')); ?></div>
    <div class="row buttons" style="display:<?php echo (!$model->isActive) ? 'block;' : 'none;'; ?>;" id="con">
          <?php
    if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
        echo CHtml::button('Modify Domain', array('onclick' => 'js:beforeValidate(false)','id' => 'mod'));
    } else {
        echo CHtml::button('Modify Domain', array('onclick' => 'js:beforeValidate(true)','id' => 'mod'));
    }
    if (Utility::isBillingContact($model)) {
        echo CHtml::submitButton('Show authcode');
    }
    echo CHtml::submitButton('Send authcode by email');
    ?>
      </div>
   <?php
}
?>

   <?php
echo $form->hiddenField($model, 'domain_name');
$this->endWidget();
?>

</div>
<script>
    <?php
    if (!$model->isActive) {
        ?>
         groupReadOnly(true);
    <?php
    } else {
        if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
            ?>
            groupReadOnly(false, false);
    <?php
        } else {
            ?>
            groupReadOnly(false, true);
    <?php
        }
    }
    ?>

    protectFormFields(
            new Array(
                "ViewDomainModel_domain_holder",
                "ViewDomainModel_domain_dsmState_renewalMode",
                "ViewDomainModel_domain_techContacts_nicHandle",
                "ViewDomainModel_domain_adminContacts_1_nicHandle",
                "ViewDomainModel_domain_adminContacts_0_nicHandle"
                ));

    function protectFormFields(fieldList) {
        $("#ViewDomainForm").data("protectedFields", fieldList);
        for(field in fieldList) {
            obj = $("#" + fieldList[field]);
            obj.data("oldValue", obj.val());
        }
        $("#ViewDomainForm").data("dataProtectionFunc", window.setInterval(checkFormForModifications, 100));
    }

    function turnOffFormProtection() {
        window.clearInterval($("#ViewDomainForm").data("dataProtectionFunc"));
        window.onbeforeunload = null;
    }

    <?php if ($model->authCodeMessage): ?>

    $(document).ready(function() {
        var authCode = '<?php echo $model->domain_authCode?>';
        var authCodeExpDate = '<?php echo $model->domain_authCodeExpirationDate?>';
        alert("The authcode is: " + authCode + "\n" +
            "This is valid until: " + authCodeExpDate + "\n");
    });

    <?php endif; ?>

    function checkFormForModifications() {
        fieldList = $("#ViewDomainForm").data("protectedFields");
        changed = false;
        for(field in fieldList) {
            obj = $("#" + fieldList[field]);
            if (obj.data("oldValue") != obj.val()) {
                changed = true;
            } else {
            }
        }

        if (changed) {
            window.onbeforeunload = confirmExit;
        } else {
            window.onbeforeunload = null;
        }
    }

    function confirmExit() {
        return "You modifications will be lost";
    }
</script>
<!-- END protected/views/domains/viewdomain.php -->
