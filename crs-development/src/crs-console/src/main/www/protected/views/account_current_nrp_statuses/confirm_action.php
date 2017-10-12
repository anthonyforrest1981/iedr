<!-- START protected/views/accounts_msd/confirm_action.php -->
<?php
/**
 * View page for Accounts MSD - Action Confirmation
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see Account_current_nrp_statusesController::showConfirmPage(), Account_current_nrp_statusesController::actionConfirm(), CurrentNRPStatusesSelectionModel
 */
// NRP CONFIRM
$this->pageTitle = Yii::app()->name . ' - Confirm Action';
$isVoluntary = false;
$cmdString = '';
switch ($model->command) {
    case 'paydeposit':
        $cmdString = 'Pay From Deposit';
        break;
    case 'payonline':
        $cmdString = 'Pay Online';
        break;
    case 'reactivate':
        $cmdString = 'Reactivate Without Payment';
        break;
    case 'removefromvoluntary':
        $cmdString = 'Remove from NRP';
        $isVoluntary = true;
        break;
}
$pricelist = get_reg_prices();
$productSummaries = Utility::getProductSummariesFromPriceList('REN', $pricelist);
$simplePricelist = Utility::getSimplePriceList('REN', $pricelist);
?>
<script>
   function getModel() {
      return '<?php echo get_class($model); ?>';
   }
</script>
<h2><?php echo printBreadcrumbTrail('account_current_nrp_statuses/Confirm Action'); ?></h2>
<h4>
    Please confirm you would like to <b><i><?php echo $cmdString; ?></i></b>
    for these domain(s):
</h4>
<?php
echo '<div class="form">';
$submitButtonId = 'proceedSubmitButton';
$submitButtonDefaultLabel = 'Proceed';
$form = $this->beginWidget('CActiveForm', array('id' => 'ConfirmAction','enableAjaxValidation' => true,
    'htmlOptions' => array('onsubmit' => "disableSubmit('$submitButtonId')"),
    'clientOptions' => array('validateOnSubmit' => true,
        'afterValidate' => "js:function(form, data, hasError){
                            var checkOK = checkSelected();
                            if (hasError || !checkOK) {
                                enableSubmit('$submitButtonId', '$submitButtonDefaultLabel');
                                return false;
                            } else return true;
                        }",
        'afterValidateAttribute' => "js:function(form, attribute, data, hasError){
                            applyErrorForField(form, attribute, data, hasError);
                        }")));
echo $form->hiddenField($model, 'domainlist');
echo $form->hiddenField($model, 'command');
echo $form->hiddenField($model, 'returnurl');
echo $form->hiddenField($model, 'needCreditCard');
echo $form->hiddenField($model, 'renewalDateType');
echo $form->hiddenField($model, 'period', array('value' => '1'));
foreach (array('domainlist','command') as $f) {
    $e = $form->error($model, $f);
    if ($e)
        echo '<span class="error">' . $e . '</span><br>';
}
$this->widget('application.widgets.js.JavaScriptUtils', array('model' => $model));
echo '<table class="formfields gridtable">';
foreach ($model->domainListToArray() as $key => $value) {
    $prefixDomain = DynamicDomainListModel::domainToVarPrefix($value);
    echo '<tr><td class="gridtablecell"><div class="row">';
    echo $form->error($model, $prefixDomain . '_confirmed');
    echo $form->checkBox($model, $prefixDomain . '_confirmed', array('checked' => true,
        'onclick' => 'javascript:setup(this,"' . $value . '");'));
    echo '</div></td><td class="gridtablecell"><div class="row">';
    echo $form->labelEx($model, $prefixDomain . '_confirmed');
    echo '</div></td>';
    if (!$isVoluntary) {
        echo '<td class="gridtablecell">' . $form->dropDownList($model, 'list_' . $value, $productSummaries, array(
            'onchange' => 'javascript:setup(this,"' . $value . '");')) . '</td></tr>';
    }
}
echo '</table>';
if (!$isVoluntary) {
    echo "<div>Transaction Summary:</div>";
    echo "<div id=\"productsCodesWithFees\" style=\"display: none\">" . json_encode($simplePricelist) . "</div><br>";
    echo "<table class=\"gridtable\">";
    echo "<tr>";
    echo "<td class=\"gridtablecell\"><strong>Domain</strong></td>";
    echo "<td class=\"gridtablecell\"><strong>Fee</strong></td>";
    echo "<td class=\"gridtablecell\"><strong>Vat</strong></td>";
    echo "<td class=\"gridtablecell\"><strong>Total</strong></td>";
    echo "</tr>";
    foreach ($model->domainListToArray() as $key => $value) {
        echo "<tr>";
        echo "<td class=\"gridtablecell\"> <div id=\"domain\">$value</div> <div style=\"display:none\" id=\"period_$value\"></div> </td>";
        echo "<td class=\"gridtablecell\"> <div id=\"fee_$value\"></div></td>";
        echo "<td class=\"gridtablecell\"> <div id=\"vat_$value\"></div></td>";
        echo "<td class=\"gridtablecell\"> <div id=\"total_$value\"></div></td>";
        echo "</tr>";
    }
    echo "<tr>";
    echo "<td class=\"gridtablecell\"><strong>Totals</strong></td>";
    echo "<td class=\"gridtablecell\"> <div id=\"totalfee\">  </div></td>";
    echo "<td class=\"gridtablecell\"> <div id=\"totalvat\">  </div></td>";
    echo "<td class=\"gridtablecell\"> <div id=\"totaltotal\"> </div></td>";
    echo "</tr>";
    echo "</table>";
    if ($model->needCreditCard == 1) {
        $this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array('form' => $form,
            'model' => $model));
    }
}
echo '</div>';
echo '<div class="row buttons">' . CHtml::submitButton($submitButtonDefaultLabel, array('id' => $submitButtonId)) . '</div><br>';
$this->endWidget();
echo AuthOnlyBaseController::returnToUrlButton($model->returnurl);
?>
<script>

<?php
if (!$isVoluntary) {
    foreach ($model->domainListToArray() as $key => $value) {
        echo 'refreshSummary("' . $value . '");';
        echo 'setDefaults("' . $model->defaultPeriods . '"  , "' . $value . '");';
    }
}
?>
</script>
