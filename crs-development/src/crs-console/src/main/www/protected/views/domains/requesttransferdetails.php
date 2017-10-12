<?php
$pg = 'Request Transfer Details';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;

function nicHandleWidget($controller, $nic_type, $inpIdNic2edit = 'LeftBlank') {
    $urlDDS = $controller->createUrl('domains/domaindetailsserialized');
    $form = 'RequestTransferDetailsForm';
    $params = "'$nic_type','$form','$urlDDS','$inpIdNic2edit'";
    echo '<a href="#" id="nicWidgetNew_' . $nic_type . '" onclick="getDataAndPostForm(\'new\',' . $params . ');">New</a> / ';
    echo '<a href="#" id="nicWidgetFnd_' . $nic_type . '" onclick="getDataAndPostForm(\'find\',' . $params . ');">Find</a> / ';
    echo '<a href="#" id="nicWidgetEdt_' . $nic_type . '" onclick="getDataAndPostForm(\'edit\',' . $params . ');">Edit</a>';
}
$pricelist = get_reg_prices();
$productSummaries = Utility::getProductSummariesFromPriceList('REN', $pricelist);
$simplePricelist = Utility::getSimplePriceList('REN', $pricelist);
?>
<form method="post" id="hid">
    <input type="hidden" id="tok" name="YII_CSRF_TOKEN">
    <input type="hidden" id="data" name="formdata">
    <input type="hidden" id="mode" name="mode">
    <input type="hidden" id="nictype" name="nictype">
    <input type="hidden" id="nic" name="nic">
    <input type="hidden" id="returnurl" name="returnurl" value="index.php?r=domains/requesttransferdetails">
</form>
<script><?php include_once 'protected/widgets/js/common.js'; ?></script>
<script>
function getDataAndPostForm(mode,nictype,srcf,serializeUrl,editNIC) {
    // this fn called on click (link or button) does:
    // (1) send form to server, gets serialized model back
    // (2) sets up hidden form in this page
    // (3) submits the form, navigating away to the next page

    var dispatchUrl;
    switch(mode) {
        case "new": dispatchUrl = 'index.php?r=nichandles/createnichandle&from=1'; break;
        case "find": dispatchUrl = 'index.php?r=nicsearch/namesearch&from=1'; break;
        case "edit":
            var theNic = $('#'+editNIC).val();
         if($('#'+editNIC).val().length == 0) {
           return 0;
         }
            dispatchUrl = 'index.php?r=nichandles/editnichandle&id='+theNic+'&from=1';
            $("#hid #nic").val(theNic);
            break;
        }
    // (1) post form ('srcf') to server, retrieve serialized model via ajax
    $.post(serializeUrl, $("#"+srcf).serialize(), function(ajaxdata) {
        // (2) set up form
        $("#hid").attr("action",dispatchUrl);
        $("#hid #data").val(ajaxdata); // the serialised form data
        $("#hid #mode").val(mode); // new/find/edit
        $("#hid #nictype").val(nictype); // which nic role for the mode (for updating the correct field, on return to this page)
        // Yii doesn't set an ID for the token hidden-input, so find by name
        var itok = $('#'+srcf+' input[name="YII_CSRF_TOKEN"]')[0];
        // set hidform token value
        var hitok = $("#hid #tok")[0];
        $(hitok).val($(itok).val());
        // (3) post form with serialized model
        $("#hid").submit();
        });
    }

</script>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
<?php
$form = $this->beginWidget('CActiveForm', array('id' => 'RequestTransferDetailsForm','enableAjaxValidation' => true,
    'clientOptions' => array('validateOnSubmit' => true,
        'afterValidateAttribute' => 'js:function(form, attribute, data, hasError){
                            applyErrorForField(form, attribute, data, hasError);
                        }')));
$form->focus = array($model,'holder');
?>
    <p class="note">
        Please fill in the form below. <br />
        <font color="red">Note:</font> Fields marked with an asterisk
        (*) must be completed.
    </p>
    <table border="1" class="formfields">
        <tbody>
            <tr>
                <td><?php
// (available/valid) domains to be registered
                echo $form->labelEx($model, 'domain_name');
                echo $form->error($model, 'domain_name');
                ?></td>
                <td>
                    <div class="row"><?php
                    echo $form->dropDownList($model, 'domain_name', array($model->domain_name => $model->domain_name),
                        array('id' => 'domain_name', 'class' => 'expand'));
                    ?></div>
                </td>
            </tr>
            <tr>
                <td><?php
                echo $form->labelEx($model, 'authcode');
                echo $form->error($model, 'authcode');
                ?></td>
                <td>
                    <div class="row"><?php
                    echo $form->textField($model, 'authcode');
                    echo $form->hiddenField($model, 'retries');
                    echo $form->hiddenField($model, 'authcode_failures', array('id' => 'authcode_failures'));
                    ?></div>
                </td>
            </tr>
            <?php if (!$model->charityPaymentForced) {?>
            <tr>
                <td><?php
// registration period [] =1-year
                echo $form->labelEx($model, 'renewalPeriod');
                echo $form->error($model, 'renewalPeriod');
                ?></td>
                <td>
                    <div class="row"><?php
                echo $form->dropDownList($model, 'renewalPeriod', $productSummaries, array(
                    'onchange' => 'javascript:setupHiddenPeriod(this);', 'value' => 1, 'class' => 'expand'));
                ?></div>
                </td>
            </tr>
            <tr>
                <td><div font-size="0.7em">
                        <strong>Transaction Summary:</strong>
                    </div></td>
                <td>
                    <div class="row">
                        <div id="productsCodesWithFees"
                            style="display: none"><?php echo json_encode($simplePricelist); ?></div>
                        <table class="gridtable">
                            <tr>
                                <td class="gridtablecell">Fee:</td>
                                <td class="gridtablecell">
                                    <div id="feeVal"></div>
                                </td>
                            </tr>
                            <tr>
                                <td class="gridtablecell">Vat:</td>
                                <td class="gridtablecell">
                                    <div id="vatVal"></div>
                                </td>
                            </tr>
                            <tr>
                                <td class="gridtablecell">Total:</td>
                                <td class="gridtablecell">
                                    <div id="totalVal"></div>
                                </td>
                            </tr>
                        </table>
  <?php } ?>
               </div>
                </td>
            </tr>
            <tr>
                <td><?php
// admin contact 1 (nic) {must be from domain-holder organisation}
                echo $form->labelEx($model, 'admin_contact_nic_1');
                echo $form->error($model, 'admin_contact_nic_1');
                ?></td>
                <td>
                    <div class="row"><?php
                    echo $form->textField($model, 'admin_contact_nic_1', array('class' => 'showupprcase'));
                    nicHandleWidget($this, 'admin_contact_nic_1', 'DomainsTransferDetails_admin_contact_nic_1');
                    ?></div>
                </td>
            </tr>
            <tr>
                <td><?php
// admin contact 2 (nic)
                echo $form->labelEx($model, 'admin_contact_nic_2');
                echo $form->error($model, 'admin_contact_nic_2');
                ?></td>
                <td>
                    <div class="row"><?php
                    echo $form->textField($model, 'admin_contact_nic_2', array('class' => 'showupprcase'));
                    nicHandleWidget($this, 'admin_contact_nic_2', 'DomainsTransferDetails_admin_contact_nic_2');
                    ?></div>
                </td>
            </tr>
            <tr>
                <td><?php
// tech contact (nic)
                echo $form->labelEx($model, 'tech_contact');
                echo $form->error($model, 'tech_contact');
                ?></td>
                <td>
                    <div class="row"><?php
                    echo $form->textField($model, 'tech_contact', array('class' => 'showupprcase'));
                    nicHandleWidget($this, 'tech_contact', 'DomainsTransferDetails_tech_contact');
                    ?></div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                <?php
                $this->widget('application.widgets.nameservers.nameserversWidget',
                    array('form' => $form,'model' => $model, 'domainId' => 'domain_name'));
                ?>
                </td>
            </tr>
            <?php
            $this->widget('application.widgets.payment.PaymentWidget',
                array('form' => $form, 'model' => $model, 'paymentTypes' => Utility::getDomainPaymentTypes()));
            ?>
            <tr>
                <td><?php
// accept-t-and-c (checkbox)
                echo $form->labelEx($model, 'accept_tnc');
                echo $form->error($model, 'accept_tnc');
                ?></td>
                <td>
                    <div class="row"><?php
                    echo $form->checkBox($model, 'accept_tnc');
                    ?></div>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="row buttons"><?php
    echo CHtml::submitButton('Request Transfer Domain(s)');
    ?></div>
<?php $this->endWidget(); ?>
</div>
<script type="text/javascript">

   function alertAuthcodeFailure() {
      <?php if ($model->message_displayed) { ?>
         alert('The AuthCode you provided does not correspond with our records. Please check your information and resubmit your transfer request.\n' +
               'Hint: The AuthCode is case sensitive.\n' +
               'Please ensure there are no spaces before or after the AuthCode.');
      <?php } ?>
   }

   alertAuthcodeFailure();

   function setupHiddenPeriodTypes(obj) {
     var periodType = document.getElementById('DomainsTransferDetails_list');
     if(periodType != null) {
        var index = obj.selectedIndex;
        periodType.value = obj.options[index].innerHTML;
     }
   }

   function setupHiddenPeriod(obj) {
        refreshSummary();
   }

   function SummaryClass(obj) {
        this.tFee = 0;
        this.tVat = 0;
        this.tTotal = 0;
        this.add = function(fee, vat) {
            this.tFee += parseFloat(fee) ;
            this.tVat += parseFloat(vat) ;
            this.tTotal = (this.tFee + this.tVat) ;
        }
        this.sub = function(fee, vat) {
            this.tFee -= parseFloat(fee);
            this.tVat -= parseFloat(vat);
            this.tTotal = this.tFee + this.tVat;
        }
    }

    function currencyFormatter(num) {
        /* render a number in html with currency symbol, 2 decimal places, thousands separators, and half-even rounding (http://www.diycalculator.com/sp-round.shtml#A5) */
        csym = "&euro;";
        if (num == null) return csym + ' 0.00';
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num))
            num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * 100 + 0.50000000001);
        cents = num % 100;
        num = Math.floor(num / 100).toString();
        if (cents < 10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
            num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
        return (((sign) ? '' : '-') + csym + ' ' + num + '.' + cents);
    }

    function refreshSummary() {
        var select = document.getElementById('DomainsTransferDetails_renewalPeriod');
        var index = select.selectedIndex;
        var period = select.options[index].value;
        var summary = new SummaryClass();
        if (document.getElementById('productsCodesWithFees') != null) {
           var productsWithFeesArray = JSON.parse(document.getElementById('productsCodesWithFees').innerHTML);
           if (period) {
               var product = productsWithFeesArray[period];
               summary.add(product.price, product.vatValue);
           }
        }
        var o = document.getElementById('DomainsTransferDetails_renewalPeriod');
        document.getElementById('feeVal').innerHTML = currencyFormatter(summary.tFee);
        document.getElementById('vatVal').innerHTML = currencyFormatter(summary.tVat);
        document.getElementById('totalVal').innerHTML = currencyFormatter(summary.tTotal);
    }
    refreshSummary();
</script>
