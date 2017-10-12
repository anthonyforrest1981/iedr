<script type="text/javascript">
    function paymentTypeChanged(paymentType) {
        var ccElement = $("#ccRow");
        switch (paymentType.value) {
            case 'CC' :
                ccElement.show();
                break;
            case 'ADP':
            case 'ADP_AUTORENEW':
                ccElement.hide();
                break;
        }
    }
</script>
<?php if (isset($this->model->charityPaymentForced) && $this->model->charityPaymentForced) { ?>
    <tr id="paymentWidget">
        <td><?php echo $this->form->labelEx($this->model, 'paymentType'); echo $this->form->error($this->model, 'paymentType'); ?></td>
        <td><div class="row"><?php echo "Charity";?></div></td>
    </tr>
<?php } else { ?>
    <tr id="paymentWidget">
        <td><?php echo $this->form->hiddenField($this->model, 'paymentType'); echo $this->form->labelEx($this->model, 'paymentType'); echo $this->form->error($this->model, 'paymentType');?></td>
        <td class="payment_type_box">
            <div class="row">
                <?php echo $this->form->radioButtonList($this->model, 'paymentType', $this->paymentTypes , array('onchange' => 'javascript:paymentTypeChanged(this)'));?>
                <?php if (array_key_exists('CC', $this->paymentTypes)) { ?>
                    <div id="ccRow" style="margin-top: 10px; <?php if($this->model->paymentType != 'CC') echo 'display: none'; ?>">
                        <?php $this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array('form' => $this->form, 'model' => $this->model)); ?>

                    </div>
                <?php } ?>
            </div>
        </td>
    </tr>
<?php } ?>
